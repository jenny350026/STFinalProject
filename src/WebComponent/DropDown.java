package WebComponent;

import java.util.ArrayList;

import org.openqa.selenium.WebElement;

public class DropDown extends WebComponentSelect{
	private static int idCounter = 0;
	
	public DropDown(WebElement e, String xp) {
		super(e, xp, idCounter++);
	}

	@Override
	public ArrayList<String> testAction() {
		ArrayList<String> strings = new ArrayList<String>();
		strings.add("WebElement " + toString() + " = driver.findElement(By.xpath(\"" + xpath + "\"));");
		strings.add(toString() + ".click();");
		return strings;
	}
	
	@Override
	public void select(){
		// TODO add implementations
		// Select dd = new Select(e);
		// e.selectByIndex(0);
		
	}
	
	@Override
	public String toString(){
		return "dropdown" + id;
	}
}
