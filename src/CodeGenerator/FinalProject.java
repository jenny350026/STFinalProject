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

public class FinalProject {
	private static WebDriver driver;

	public static String generateString(Random rng, String characters, int length) {
		char[] text = new char[length];
		for (int i = 0; i < length; i++) {
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		}
		return new String(text);
	}

	// source :　https://groups.google.com/forum/#!topic/selenium-users/kvGLSzl_GQE
	public static String getXPath(WebElement element) { 
//        String jscript = "function getPathTo(node) {" + 
//            "  var stack = [];" + 
//            "  while(node.parentNode !== null) {" + 
//            "    stack.unshift(node.tagName);" + 
//            "    node = node.parentNode;" + 
//            "  }" + 
//            "  return stack.join('/');" + 
//            "}" + 
//            "return getPathTo(arguments[0]);"; 
       
        String jscript = "function getElementXPath(elt){" +
                "var path = \"\";" +
                "for (; elt && elt.nodeType == 1; elt = elt.parentNode){" +
                    "idx = getElementIdx(elt);" +
                    "xname = elt.tagName;" +
                    "if (idx > 0){" +
                        "xname += \"[\" + idx + \"]\";" +
                    "}" +
                    "path = \"/\" + xname + path;" +
                "}" + 
                "return path;" +
            "}" +
            "function getElementIdx(elt){" +
                "var count = 1;" +
                "for (var sib = elt.previousSibling; sib ; sib = sib.previousSibling){" +
                    "if(sib.nodeType == 1 && sib.tagName == elt.tagName){" +
                        "count++;" +
                    "}" +
                "}" +
                "return count;" + 
            "}" +
            "return getElementXPath(arguments[0]).toLowerCase();";  
        return (String) ((RemoteWebDriver) driver).executeScript(jscript, element); 
    }
	
	public static void main(String[] args) {
		ArrayList<String> toClickAtSetUp =  new ArrayList<String>();
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

		WebElement myTrips = driver.findElement(By.xpath("/html/body/main/div[1]/div[1]/div/nav/ul/li[1]/a"));
		myTrips.click();
		
		WebElement cardNumber = driver.findElement(
				By.xpath("/html/body/main/div[1]/div[1]/div/nav/ul/li[1]/div/div/div[1]/div/form[1]/div/select"));
		Select dropdown = new Select(cardNumber);
		dropdown.selectByIndex(0);
		
		//dropdowns(newly added)
		List<WebElement> dropdowns = driver.findElements(By.xpath("//form[@class='customFormUI find-mytrips-select']//select"));
		for(int i = 0; i < dropdowns.size(); ++i){
			System.out.println(getXPath(dropdowns.get(i)));
			g.addDropdown(dropdowns.get(i), getXPath(dropdowns.get(i)));
		}
			
		//boxes
		List<WebElement> boxes = driver.findElements(By.xpath("//form[@id='widgetSearchUsingConfirmationNo']//input[@type='text']"));
		for(int i = 0; i < boxes.size(); ++i){
			System.out.println(getXPath(boxes.get(i)));
			//TODO getXPath doesn't seem to be working now
			//it has been fixed now
			g.addTextBox(boxes.get(i), getXPath(boxes.get(i)));
		}
		
		//buttons(newly added)
		List<WebElement> buttons = driver.findElements(By.xpath("//form[@id='widgetSearchUsingConfirmationNo']//button[@type='submit']"));
		for(int i = 0; i < buttons.size(); ++i){
			System.out.println(getXPath(buttons.get(i)));
			g.addButtons(buttons.get(i), getXPath(buttons.get(i)));
		}
		
		try {
			Thread.sleep(1000); // wait
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		
		WebElement bookATrip = driver.findElement(By.xpath("/html/body/main/div[1]/div[1]/div/nav/ul/li[2]/a"));
		bookATrip.click();
		try {
			Thread.sleep(1000); // wait
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		WebElement hotel = driver.findElement(By.xpath("/html/body/main/div[1]/div[1]/div/nav/ul/li[2]/div/div/div/div[1]/ul/li[2]/a"));
		hotel.click();
		WebElement flight = driver.findElement(By.xpath("/html/body/main/div[1]/div[1]/div/nav/ul/li[2]/div/div/div/div[1]/ul/li[1]/a"));
		flight.click();
		
		//radio buttons(newly added)
		List<WebElement> rButtons = driver.findElements(By.id("oneWayRadioBtn"));
		for(int i = 0; i < rButtons.size(); ++i){
			System.out.println(getXPath(rButtons.get(i)));
			g.addRadioButtons(rButtons.get(i), getXPath(rButtons.get(i)));
		}
//		System.out.println(rb.size());
//		System.out.println(rb.get(0).isEnabled());
//		rb.get(0).click();

		g.generate();
/*
				// System.out.println(box.size());

		// // interface-based
		// String rdm0 = RandomStringUtils.randomAlphabetic(5);
		// box.get(0).sendKeys(rdm0);
		// String rdm1 = RandomStringUtils.randomNumeric(5);
		// box.get(1).sendKeys(rdm1);
		// // String rdm2 = RandomStringUtils.randomAscii(5);
		// // box.get(2).sendKeys(rdm2);
		// // String rdm2 = RandomStringUtils.randomAlphanumeric(5);
		// // box.get(2).sendKeys(rdm2);
		// String rdm2 = RandomStringUtils.random(5, ' ', 'a', 'b');
		// box.get(2).sendKeys(rdm2);

		// functionality-based
		String date = Integer.toString(1 + (int) (Math.random() * 11)) + "/"
				+ Integer.toString(1 + (int) (Math.random() * 30)) + "/"
				+ Integer.toString(1980 + (int) (Math.random() * 36));
		box.get(0).sendKeys(date);
		String phone = RandomStringUtils.randomNumeric(3) + '-' + RandomStringUtils.randomNumeric(3) + '-'
				+ RandomStringUtils.randomNumeric(4);
		box.get(1).sendKeys(phone);
		try {
			Thread.sleep(1000); // wait
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		WebElement bookATrip = driver.findElement(By.xpath("/html/body/main/div[1]/div[1]/div/nav/ul/li[2]/a/small"));
		bookATrip.click();
		try {
			Thread.sleep(1000); // wait
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		//pick city from set
		WebElement flight = driver
				.findElement(By.xpath("/html/body/main/div[1]/div[1]/div/nav/ul/li[2]/div/div/div/div[1]/ul/li[1]"));
		flight.click();
		WebElement oneWay = driver.findElement(By.xpath(
				"/html/body/main/div[1]/div[1]/div/nav/ul/li[2]/div/div/div/div[1]/div[1]/div[1]/div[1]/div/form/div[2]/div[1]/div[4]/div/fieldset/label[2]/span"));
		oneWay.click();
		WebElement city = driver.findElement(By.xpath(
				"/html/body/main/div[1]/div[1]/div/nav/ul/li[2]/div/div/div/div[1]/div[1]/div[1]/div[1]/div/form/div[2]/div[1]/div[6]/fieldset[1]/div[1]/div[1]/div[2]/span"));
		city.click();
		List<WebElement> citiesSet = driver.findElements(By.xpath("//ul[@id='stateProvince_ul']/li"));
		WebElement close = driver.findElement(By.xpath("/html/body/div[8]/div/div[2]/div[1]/div[1]/a"));
		close.click();
		WebElement cityInput = driver.findElement(By.xpath(
				"/html/body/main/div[1]/div[1]/div/nav/ul/li[2]/div/div/div/div[1]/div[1]/div[1]/div[1]/div/form/div[2]/div[1]/div[6]/fieldset[1]/div[1]/div[1]/div[2]/input"));
		cityInput.sendKeys(citiesSet.get((int) (Math.random() * citiesSet.size())).getAttribute("data-item"));
*/
	}

}
// WebElement searchBox = driver.findElement(By.xpath(""));
// WebElement searchBox = driver.findElement(By.name("btnK"));
// WebElement searchBox = driver.findElement(By.id("gbqfba"));
// List<WebElement> buttons = driver.findElements(By.tagName("button"));
// for(WebElement e: allInputs){
// if (e.getAttribute("type").equals("text")){
// System.out.println(e.getText().toString()); //text in text box
// }
// }
// WebElement searchBox = driver.findElement(By.className("buttonStyle"));
// WebElement aboutLink = driver.findElement(By.linkText("About Google"));
// WebElement aboutLink = driver.findElement(By.partialLinkText("About"));
// driver.findElement(By.xpath("//a[contains(@href, 'logout')]"));

//// search box, input "hi", then go search it //
// WebElement searchBox =
// driver.findElement(By.xpath("/html/body/div[6]/div/header/div/nav/ul[2]/li[4]/form/div/input[1]"));
// searchBox.sendKeys("hi");
// WebElement searchBoxSummit =
// driver.findElement(By.xpath("/html/body/div[6]/div/header/div/nav/ul[2]/li[4]/form/div/button"));
// searchBoxSummit.click();

//// link: skyteam //
// WebElement skyteam = driver
// .findElement(By.xpath("/html/body/div[6]/div/header/div/div/div/a[2]"));
// skyteam.click();

//// link(mouseover): hotel //
// WebElement link =
// driver.findElement(By.xpath("/html/body/div[6]/div/header/div/nav/ul[2]/li[1]/a"));
// Actions action = new Actions(driver);
// action.moveToElement(link).build().perform();
// action.clickAndHold(link).build().perform();
// action.release(link).build().perform();
// WebElement hotel =
// driver.findElement(By.xpath("/html/body/div[6]/div/header/div/nav/ul[2]/li[1]/div/ul[1]/li[2]/a"));
// hotel.click();

//// checkbox
// WebElement checkbox = driver.findElement(By.xpath(
// "/html/body/main/div[1]/div[1]/div/section/div[1]/div/form/div/div[2]/fieldset/div[2]/div[1]/input"));
// checkbox.click();

//// semi-link: MY TRIPS(dropdown)
// WebElement myTrips =
// driver.findElement(By.xpath("/html/body/main/div[1]/div[1]/div/nav/ul/li[1]/a/h3"));
// myTrips.click();
// WebElement confirmNumber =
// driver.findElement(By.xpath("/html/body/main/div[1]/div[1]/div/nav/ul/li[1]/div/div/div[1]/div/form[1]/div/select"));
// Select dropdown = new Select(confirmNumber);
// dropdown.selectByIndex(1);

//// flightOneWayFrom
// WebElement flightOneWayFrom =
// driver.findElement(By.xpath("/html/body/main/div[1]/div[1]/div/nav/ul/li[2]/a/small"));
// flightOneWayFrom.click();

//// preClick
// WebElement preClick =
// driver.findElement(By.xpath("/html/body/main/div[1]/div[1]/div/nav/ul/li[3]/a/h3"));
// preClick.click();
// try {
// Thread.sleep(1000); // wait
// } catch (InterruptedException ex) {
// Thread.currentThread().interrupt();
// }
// WebElement realClick =
// driver.findElement(By.xpath("/html/body/main/div[1]/div[1]/div/nav/ul/li[2]/a/small"));
// realClick.click();

//// target
// WebElement target =
// driver.findElement(By.xpath("/html/body/main/div[1]/div[1]/div/nav/ul/li[1]/a"));
// target.click();
// WebElement cardNumber = driver.findElement(
// By.xpath("/html/body/main/div[1]/div[1]/div/nav/ul/li[1]/div/div/div[1]/div/form[1]/div/select"));
// Select dropdown = new Select(cardNumber);
// dropdown.selectByIndex(0);
//
// WebElement firstName =
// driver.findElement(By.xpath("/html/body/main/div[1]/div[1]/div/nav/ul/li[1]/div/div/div[1]/div/form[2]/input[2]"));
// firstName.sendKeys("Max");
//
// WebElement lastName =
// driver.findElement(By.xpath("/html/body/main/div[1]/div[1]/div/nav/ul/li[1]/div/div/div[1]/div/form[2]/input[3]"));
// lastName.sendKeys("Zhang");
//
// WebElement confirmNumber =
// driver.findElement(By.xpath("/html/body/main/div[1]/div[1]/div/nav/ul/li[1]/div/div/div[1]/div/form[2]/input[5]"));
// confirmNumber.sendKeys("123456");

// List<WebElement> box =
// driver.findElements(By.xpath("//form[@id='widgetSearchUsingConfirmationNo']/input[0]"));
// List<WebElement> box =
// driver.findElements(By.xpath("//form[@id='widgetSearchUsingConfirmationNo']/input[0]"));