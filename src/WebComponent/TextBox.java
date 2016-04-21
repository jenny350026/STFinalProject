package WebComponent;

import java.util.ArrayList;

import org.openqa.selenium.WebElement;

public class TextBox extends WebComponent{
	private static int idCounter= 0;
	
	private String content;

	public TextBox(WebElement e, String xp) {
		super(e, xp, idCounter++);
	}
	
	public TextBox(TextBox bt){
		super(bt.element, bt.xpath, idCounter++);
	}
	
	public void setContent(String c){
		content = c;
	}

	@Override
	public ArrayList<String> testAction() {
		// TODO Auto-generated method stub
		ArrayList<String> strings = new ArrayList<String>();
		strings.add("WebElement " + toString() + " = driver.findElement(By.xpath(\"" + xpath + "\"));");
		strings.add(toString() + ".sendKeys(\"" + content + "\");");
		return strings;
	}
	
	@Override
	public String toString(){
		return "textbox" + id;
	}
}
