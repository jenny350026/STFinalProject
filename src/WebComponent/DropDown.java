package WebComponent;

import java.util.ArrayList;

public class DropDown extends WebComponentSelect{
	
	public DropDown(){
		super();
	}
	
	@Override
	public ArrayList<String> testAction() {
		ArrayList<String> strings = new ArrayList<String>();
		strings.add("WebElement " + toString() + " = driver.findElement(By.xpath(\"" + xpath + "\"));");
		strings.add(toString() + ".click();");
		return strings;
	}
	
	@Override
	public String toString(){
		return "dropdown" + id + " with " + options.size() + " options";
	}

}
