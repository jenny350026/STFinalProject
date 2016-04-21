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

import CodeGenerator.WebTree.WebNode;
import WebComponent.CheckBox;
import WebComponent.DropDown;
import WebComponent.DropDownOption;
import WebComponent.RadioButton;
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

	public static void buildTree(WebNode head, List<List<WebElement>> selects, List<List<WebElement>> elements) {
		System.out.println("BEGIN: selects: "+selects.size());
		System.out.println("BEGIN: elements: "+elements.size());
		
		if(selects.isEmpty() && elements.isEmpty()) 
			return;
		
		//options and boxes at this level
		List<List<WebElement>> headSelects = new ArrayList<>();
		List<List<WebElement>> headElements = new ArrayList<>();

		//options and boxes at next(sub) level
		List<List<List<WebElement>>> subSelects = new ArrayList<>();
		List<List<List<WebElement>>> subElements = new ArrayList<>();
		
		//find the elements in this level
		for (int i = 0; i < selects.size() + elements.size(); i++) {
			//deal with selects
			if (i < selects.size()) {
				int breakFlag = 0;
				for (int j = 0; j < selects.size(); j++) {
					List<WebElement> selectArray = new ArrayList<>();
					selectArray = selects.get(j);
					//if this is not displayed, it cannot be the headSelects
					if(selectArray.get(0).isDisplayed()){
						//click all options in this list of list
						for(int k = 0; k < selects.size(); k++){
							for(int m = 0; m < selects.get(k).size() ;m++){
								selects.get(k).get(m).click();
								if (!selectArray.get(0).isDisplayed()) {
									breakFlag = 1;
									break;
								}	
							}
							if(breakFlag == 1){
								break;
							}	
						}
						//add headSelects
						if (breakFlag == 0) {//change
							headSelects.add(selects.get(i));
						}			
					}
				}
			//deal with elements
			} else {
				int breakFlag = 0;
				//for (int j = 0; j < elements.size(); j++) {
					List<WebElement> elementArray = new ArrayList<>();
					elementArray = elements.get(i-selects.size());

					//click all options in this list of list
					for(int k = 0; k < selects.size(); k++){
						for(int m = 0; m < selects.get(k).size() ;m++){
							System.out.println("k: " + k + ", " + "m: " + m);
							selects.get(k).get(m).click();
							if (!elementArray.get(0).isDisplayed()) {
								breakFlag = 1;
								break;
							}	
						}
						if(breakFlag == 1){
							break;
						}	
					}
					//add headElements
					if (breakFlag == 0) {//change
						headElements.add(elements.get(i));
					}			
				//}
			}
		}
		
		System.out.println("AFTER FIRST PICKING: headSelects: "+headSelects.size());
		System.out.println("AFTER FIRST PICKING: headElements: "+headElements.size());
		
		//eliminate selects which can be treated as elements
		for(int i = 0; i < headSelects.size(); i++){
			
			//save the old status of other elements
			List<Boolean> oldStatus = new ArrayList<>();
			for(int j=0;j<selects.size()+elements.size();j++){
				//deal with selects which are not headSelects
				if(j<selects.size()){
					if(!headSelects.contains(selects.get(j))){
						if(selects.get(j).get(0).isDisplayed()){
							oldStatus.add(true);
						}else{
							oldStatus.add(false);
						}
					}
				//deal with elements	
				}else{
					if(elements.get(j-selects.size()).get(0).isDisplayed()){
						oldStatus.add(true);
					}else{
						oldStatus.add(false);
					}
				}
			}
			
			//click to see what happens
			headSelects.get(i).get(0).click();
			
			//save the new status of other elements
			List<Boolean> newStatus = new ArrayList<>();
			for(int j=0;j<selects.size()+elements.size();j++){
				//deal with selects which are not headSelects
				if(j<selects.size()){
					if(!headSelects.contains(selects.get(j))){
						if(selects.get(j).get(0).isDisplayed()){
							newStatus.add(true);
						}else{
							newStatus.add(false);
						}
					}
				//deal with elements	
				}else{
					if(elements.get(j-selects.size()).get(0).isDisplayed()){
						newStatus.add(true);
					}else{
						newStatus.add(false);
					}
				}
			}

			//compare the two statuses to determine if this select is an element
			if(newStatus.contains(oldStatus)&&oldStatus.contains(newStatus)){
				List<WebElement> elem = headSelects.get(i);
				elements.add(elem);
				headSelects.remove(elem);
				i--;
			}
		}
		
		System.out.println("AFTER ELIMINATING: headSelects: "+headSelects.size());
		System.out.println("AFTER ELIMINATING: headElements: "+headElements.size());
		System.out.println("AFTER ELIMINATING: selects: "+selects.size());
		System.out.println("AFTER ELIMINATING: elements: "+elements.size());
		
		// allocate remnant(sub) elements into sub level
		for (int i = 0; i < headSelects.size(); i++) {
			for(int k = 0; k < headSelects.get(i).size(); k++){
				headSelects.get(i).get(k).click();
				List<List<WebElement>> tmpSelectList = new ArrayList<>();
				subSelects.add(tmpSelectList);
				for (int j = 0; j < selects.size(); j++) {
					if (!headSelects.contains(selects.get(j))) {
						if (selects.get(j).get(0).isDisplayed()) {
							subSelects.get(k).add(selects.get(j));
						}
					}
				}
				List<List<WebElement>> tmpElementList = new ArrayList<>();
				subElements.add(tmpElementList);
				for (int j = 0; j < elements.size(); j++) {
					if (!headElements.contains(elements.get(j))) {
						if (elements.get(j).get(0).isDisplayed()) {
							subElements.get(k).add(elements.get(j));
						}
					}
				}
			}
		}
		
		System.out.println("AFTER ALLOCATING: subSelects: "+subSelects.size());
		System.out.println("AFTER ALLOCATING: subElements: "+subElements.size());
		

		
//		//load selects at this level into WebComponent
//		for (int i = 0; i < headSelects.size(); i++) {
//			//need to differentiate variant types 
//			List<WebElement> tmpHeadSelectList = new ArrayList<>();
//			tmpHeadSelectList = headSelects.get(i);
//				//if this select is dropDown
//				if(tmpHeadSelectList.get(0).getTagName().equals("option")){
//					//List<WebComponent> dropDownComponents = new ArrayList<>();
//					DropDown dropdown = new DropDown();
//					for(int j=0;j<tmpHeadSelectList.size();j++){
//						WebElement addElement = tmpHeadSelectList.get(j);
//						dropdown.addOption(new DropDownOption(addElement, getXPath(addElement)));
//						//dropDownComponents.add(new DropDown(addElement, getXPath(addElement))) ;
//					}
//					head.addSelect((WebComponentSelect)dropdown);
//				//if this select is radioButton
//				}
//				else if(tmpHeadSelectList.get(0).getTagName().equals("span")){
//					List<WebComponent> radioButtonComponents = new ArrayList<>();
//					for(int j=0;j<tmpHeadSelectList.size();j++){
//						WebElement addElement = tmpHeadSelectList.get(j);
//						radioButtonComponents.add(new RadioButton(addElement, getXPath(addElement))) ;
//						
//					}
//					head.addSelects(radioButtonComponents);
//				//if this select is checkBox
//				}else if(tmpHeadSelectList.get(0).getAttribute("type").equals("checkbox")){
//					List<WebComponent> checkBoxComponents = new ArrayList<>();
//					for(int j=0;j<tmpHeadSelectList.size();j++){
//						WebElement addElement = tmpHeadSelectList.get(j);
//						checkBoxComponents.add(new CheckBox(addElement, getXPath(addElement))) ;
//						
//					}
//					head.addSelects(checkBoxComponents);
//				}		
//		}
		
//		//load elements at this level into WebComponent
//		for (int i = 0; i < headElements.size(); i++) {
//			List<WebElement> tmpHeadElementList = new ArrayList<>();
//			tmpHeadElementList = headElements.get(i);
//				//if this element is textBox
//				if(tmpHeadElementList.get(0).getAttribute("type").equals("checkbox")){
//					List<WebComponent> textBoxComponents = new ArrayList<>();
//					for(int j=0;j<tmpHeadElementList.size();j++){
//						WebElement addElement = tmpHeadElementList.get(j);
//						textBoxComponents.add(new TextBox(addElement, getXPath(addElement))) ;
//					}
//					head.addElements(textBoxComponents);
//				//if this element is dropDown
//				}else if(tmpHeadElementList.get(0).getTagName().equals("option")){
//					List<WebComponent> dropDownComponents = new ArrayList<>();
//					for(int j=0;j<tmpHeadElementList.size();j++){
//						WebElement addElement = tmpHeadElementList.get(j);
//						dropDownComponents.add(new DropDown(addElement, getXPath(addElement))) ;
//						
//					}
//					head.addElements(dropDownComponents);
//				//if this element is radioButton
//				}else if(tmpHeadElementList.get(0).getTagName().equals("span")){
//					List<WebComponent> radioButtonComponents = new ArrayList<>();
//					for(int j=0;j<tmpHeadElementList.size();j++){
//						WebElement addElement = tmpHeadElementList.get(j);
//						radioButtonComponents.add(new RadioButton(addElement, getXPath(addElement))) ;
//						
//					}
//					head.addElements(radioButtonComponents);
//				//if this element is checkBox
//				}else if(tmpHeadElementList.get(0).getAttribute("type").equals("checkbox")){
//					List<WebComponent> checkBoxComponents = new ArrayList<>();
//					for(int j=0;j<tmpHeadElementList.size();j++){
//						WebElement addElement = tmpHeadElementList.get(j);
//						checkBoxComponents.add(new CheckBox(addElement, getXPath(addElement))) ;
//						
//					}
//					head.addElements(checkBoxComponents);
//				}	
//		}

		//add node and recursion to build the tree
		for (int i = 0; i < headSelects.size(); i++) {
			for(int j = 0;j < headSelects.get(i).size();j++){
				System.out.println("i: " + i + "," + "j: " + j);
				head.addNext();
				buildTree(head.getNext().get(j), subSelects.get(j), subElements.get(j));
			}
		}

	}

	public static void main(String[] args) {
		ArrayList<String> toClickAtSetUp = new ArrayList<String>();
		toClickAtSetUp.add("/html/body/div[1]/div[2]/div/div[2]/div[1]/div[6]");
		toClickAtSetUp.add("/html/body/div[1]/div[2]/div/div[2]/div[1]/div[6]/div[3]/div[2]/div[1]/nav/ul/li[2]/a");
		Generator g = new Generator("http://www.united.com", toClickAtSetUp);

		// TODO change to use HtmlUnitDriver so we don't need to
		// open GUI when generating tests
		System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://www.united.com/");

		try {
			Thread.sleep(1000); // wait
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		
//		//my trips
//		{
//			WebElement MyTrips = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[1]/div[6]"));
//			MyTrips.click();
//			try {
//				Thread.sleep(1000); // wait
//			} catch (InterruptedException ex) {
//				Thread.currentThread().interrupt();
//			}
//			WebElement Car = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[1]/div[6]/div[3]/div[2]/div[1]/nav/ul/li[2]/a"));
//			Car.click();
			
			
//			
//			//options: radiobutton, link, checkbutton
//			//elements: button
//			// build tree
//			WebTree tree = new WebTree();
//			
//			//WebNode head = new WebNode();
//			List<WebElement> dropdowns = driver.findElements(By.xpath("//div[@id='myTripsNav-2']//option"));
//			List<WebElement> boxes = driver.findElements(By.xpath("//div[@id='myTripsNav-2']//input[@type='text']"));
//			//boxes.get(1).sendKeys("hi");
//			//boxes.get(1).click();
//			buildTree(tree.head, dropdowns, boxes);
//			System.out.println(tree);
//			g.generate(tree);
//		}
		
		//my trips in United Airlines
		{
			WebElement MyTrips = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[1]/div[6]"));
			MyTrips.click();
			try {
				Thread.sleep(1000); // wait
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			WebElement Car = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[1]/div[6]/div[3]/div[2]/div[1]/nav/ul/li[2]/a"));
			Car.click();

//			// build tree
//			WebTree tree = new WebTree();
//			
			List<WebElement> dropDowns = driver.findElements(By.xpath("//div[@id='myTripsNav']//select"));
			List<List<WebElement>> dropDownsForTree = new ArrayList<>();
			for(int i=0;i<dropDowns.size();i++){
				String dropdownID = dropDowns.get(i).getAttribute("id");
				List<WebElement> dropdownoptions = driver.findElements(By.xpath("//div[@id='myTripsNav']//select[@id='" + dropdownID + "']//option"));
				dropDownsForTree.add(dropdownoptions);
			}
			System.out.println("dropDownsForTree.size()" + dropDowns.size());
			System.out.println("dropDownsForTree.get(0).size()" + dropDownsForTree.get(0).size());
			System.out.println("dropDownsForTree.get(1).size()" + dropDownsForTree.get(1).size());
			
			List<WebElement> boxes = driver.findElements(By.xpath("//div[@id='myTripsNav']//input[@type='text']"));	
			List<List<WebElement>> boxesForTree = new ArrayList<>();
			for(int i=0;i<boxes.size();i++){
				List<WebElement> box = new ArrayList<>();
				box.add(boxes.get(i));
				boxesForTree.add(box);
			}
//			System.out.println("boxesForTree.size()" + boxes.size());
//			System.out.println("boxesForTree.get(0).size()" + boxes.get(0).size());
			
//			try {
//				Thread.sleep(1000); // wait
//			} catch (InterruptedException ex) {
//				Thread.currentThread().interrupt();
//			}
			
			//buildTree(tree.head, dropDownsForTree, boxesForTree);
			
			//System.out.println(tree);
			//g.generate(tree);
		}
		
//		//book a trip
//		{
////			WebElement bookATrip = driver.findElement(By.xpath("/html/body/main/div[1]/div[1]/div/nav/ul/li[2]/a"));
////			bookATrip.click();
//			
//			WebElement car = driver.findElement(By.xpath("/html/body/main/div[1]/div[1]/div/nav/ul/li[2]/div/div/div/div[1]/ul/li[3]"));
//			car.click();
//			
//			WebTree tree = new WebTree();
//			//List<WebElement> links = driver.findElements(By.xpath("//div[@class='tabContainer']//a[@class='ui-tabs-anchor']"));
//			//System.out.println("links: " + links.size());
//			List<WebElement> dropdowns = driver.findElements(By.xpath("//div[@id='book-air-content']//option"));
//			List<WebElement> radioButtons = driver.findElements(By.xpath("//div[@id='book-air-content']//span[@class='ui-button-text']"));
//			WebElement radioButton = driver.findElement(By.xpath("/html/body/main/div[1]/div[1]/div/nav/ul/li[2]/div/div/div/div[1]/div[1]/div[1]/div[1]/div/form/div[2]/div[1]/div[4]/div/fieldset/label[3]/span"));
//			radioButtons.remove(radioButton);
//			List<WebElement> checkButtons = driver.findElements(By.xpath("//div[@id='book-air-content']//input[@type='checkbox']"));
//			List<WebElement> boxes = driver.findElements(By.xpath("//div[@id='book-air-content']//input[@type='text']"));
//			List<WebElement> buttons = driver.findElements(By.xpath("//div[@id='book-air-content']//button"));
//			
//			System.out.println("dropdowns: " + dropdowns.size());
//			System.out.println("radioButtons: " + radioButtons.size());
//			System.out.println("checkButtons: " + checkButtons.size());
//			System.out.println("boxes: " + boxes.size());
//			System.out.println("buttons: " + buttons.size());
//
//			//generate selects arraylist
//			List<WebElement> selects = new ArrayList<>();
//			for(int i=0;i<dropdowns.size();i++){
//				selects.add(dropdowns.get(i));
//			}
//			for(int i=0;i<radioButtons.size();i++){
//				selects.add(radioButtons.get(i));
//			}
//			for(int i=0;i<checkButtons.size();i++){
//				selects.add(checkButtons.get(i));
//			}
//			
//			//generate elements arraylist
//			List<WebElement> elements = new ArrayList<>();
//			for(int i=0;i<boxes.size();i++){
//				elements.add(boxes.get(i));
//			}
//			
////			buildTree(tree.head, selects, elements);
////			System.out.println(tree);
//		}

		
	}

}
