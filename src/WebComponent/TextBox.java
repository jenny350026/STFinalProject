package WebComponent;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TextBox extends WebComponent{
	private static int idCounter= 0;

	public TextBox(WebElement e, String xp) {
		super(e, xp, idCounter++);
	}

	@Override
	public ArrayList<String> testAction() {
		// TODO Auto-generated method stub
		ArrayList<String> strings = new ArrayList<String>();
		strings.add("WebElement element" + id + " = driver.findElement(By.xpath(\"" + xpath + "\"));");
		strings.add("element" + id + ".sendKeys(\"00000\");");
		return strings;
	}
	
	@Override
	public String toString(){
		return "textbox" + id;
	}
}
