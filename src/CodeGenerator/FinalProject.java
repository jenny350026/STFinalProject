package CodeGenerator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import WebComponent.DropDown;
import WebComponent.TextBox;
import WebComponent.WebComponent;
import WebComponent.WebComponentSelect;

public class FinalProject {
	private static WebDriver driver;

	public static String generateString(Random rng, String characters, int length) {
		char[] text = new char[length];
		for (int i = 0; i < length; i++) {
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		}
		return new String(text);
	}

	// source
	// :　https://groups.google.com/forum/#!topic/selenium-users/kvGLSzl_GQE
	public static String getXPath(WebElement element) {
		String jscript = "function getElementXPath(elt){" 
				+ "var path = \"\";"
				+ "for (; elt && elt.nodeType == 1; elt = elt.parentNode){" 
					+ "idx = getElementIdx(elt);"
					+ "xname = elt.tagName;" 
					+ "if (idx > 0){" 
						+ "xname += \"[\" + idx + \"]\";" 
						+ "}"
					+ "path = \"/\" + xname + path;" 
					+ "}" 
				+ "return path;" 
				+ "}" 
				
				+ "function getElementIdx(elt){"
				+ "var count = 1;" 
				+ "for (var sib = elt.previousSibling; sib ; sib = sib.previousSibling){"
					+ "if(sib.nodeType == 1 && sib.tagName == elt.tagName){" 
						+ "count++;" 
						+ "}" 
					+ "}" 
					+ "return count;"
				+ "}" 
				+ "return getElementXPath(arguments[0]).toLowerCase();";
		return (String) ((RemoteWebDriver) driver).executeScript(jscript, element);
	}

	public static void buildTree(WebNode head, List<WebElement> dropdowns, List<WebElement> boxes) {
		
		if(head==null || dropdowns.isEmpty() || boxes.isEmpty()) 
			return;
		
		//options and boxes at this level
		List<WebElement> headOptions = new ArrayList<>();
		List<WebElement> headBoxes = new ArrayList<>();

		//options and boxes at next(sub) level
		List<List<WebElement>> subOptions = new ArrayList<>();
		List<List<WebElement>> subBoxes = new ArrayList<>();

		//find the elements in this level
		for (int i = 0; i < dropdowns.size() + boxes.size(); i++) {
			int count = 0;
			if (i < dropdowns.size()) {
				for (int j = 0; j < dropdowns.size(); j++) {
					dropdowns.get(j).click();
					if (dropdowns.get(i).isDisplayed()) {
						count++;
					}
				}
				if (count == dropdowns.size()) {
					headOptions.add(dropdowns.get(i));
				}
			} else {
				for (int j = 0; j < dropdowns.size(); j++) {
					dropdowns.get(j).click();
					if (boxes.get(i - dropdowns.size()).isDisplayed()) {
						count++;
					}
				}
				if (count == dropdowns.size()) {
					headBoxes.add(boxes.get(i - dropdowns.size()));
				}
			}
		}
		
		// allocate remnant elements into sub level
		for (int i = 0; i < headOptions.size(); i++) {
			headOptions.get(i).click();
			subOptions.add(new ArrayList<WebElement>());
			for (int j = 0; j < dropdowns.size(); j++) {
				if (!headOptions.contains(dropdowns.get(j))) {
					if (dropdowns.get(j).isDisplayed()) {
						subOptions.get(i).add(dropdowns.get(j));
					}
				}
			}
			subBoxes.add(new ArrayList<WebElement>());
			for (int j = 0; j < boxes.size(); j++) {
				if (!headBoxes.contains(boxes.get(j))) {
					if (boxes.get(j).isDisplayed()) {
						subBoxes.get(i).add(boxes.get(j));
					}
				}
			}

		}
		
		//load element at this level into WebComponent
		for (int i = 0; i < headBoxes.size(); i++) {
			head.addElement(new TextBox(headBoxes.get(i), getXPath(headBoxes.get(i)), head.getElements().size()));
		}
		for (int i = 0; i < headOptions.size(); i++) {
			head.addSelect(new DropDown(headOptions.get(i), getXPath(headOptions.get(i)), head.getSelects().size()));
			WebNode node = new WebNode();
			head.addNext(node);
		}

		//recursion to build the tree
		for (int i = 0; i < head.getNext().size(); i++) {
			buildSubNode(head.getNext().get(i), subOptions.get(i), subBoxes.get(i));
		}

	}

	public static void main(String[] args) {
		ArrayList<String> toClickAtSetUp = new ArrayList<String>();
		toClickAtSetUp.add("/html/body/main/div[1]/div[1]/div/nav/ul/li[1]/a");
		Generator g = new Generator("http://www.delta.com", toClickAtSetUp);

		// TODO change to use HtmlUnitDriver so we don't need to
		// open GUI when generating tests
		System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://www.delta.com/");

		try {
			Thread.sleep(1000); // wait
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		
		//my trips
		{
			WebElement myTrips = driver.findElement(By.xpath("/html/body/main/div[1]/div[1]/div/nav/ul/li[1]/a"));
			myTrips.click();
			
			//options: radiobutton, link, checkbutton
			//elements: button
			// build tree
			WebNode head = new WebNode();
			List<WebElement> dropdowns = driver.findElements(By.xpath("//div[@class='infoContainer']//option"));
			List<WebElement> boxes = driver.findElements(By.xpath("//div[@class='infoContainer']//input[@type='text']"));
			buildTree(head, dropdowns, boxes);
		}
		
//		//book a trip
//		{
//			WebElement bookATrip = driver.findElement(By.xpath("/html/body/main/div[1]/div[1]/div/nav/ul/li[2]/a"));
//			bookATrip.click();
//			
//			WebNode head = new WebNode();
//			List<WebElement> dropdowns = driver.findElements(By.xpath("//div[@class='tabContainer']//option"));
//			List<WebElement> boxes = driver.findElements(By.xpath("//div[@class='tabContainer']//input[@type='text']"));
//			List<WebElement> radioButtons = driver.findElements(By.xpath("//div[@class='tabContainer']//span"));
//			System.out.println("dropdowns: " + dropdowns.size());
//			System.out.println("boxes: " + boxes.size());
//			System.out.println("radioButtons: " + radioButtons.size());
//		}

		g.generate();
	}

}
