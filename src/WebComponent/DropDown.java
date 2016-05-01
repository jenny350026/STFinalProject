package WebComponent;

import java.util.ArrayList;

public class DropDown extends WebComponentSelect{
	
	public DropDown(){
		super();
	}
	
	public DropDown(DropDown dd){
		super(dd);
	}
	
	@Override
	public ArrayList<String> testAction() {
		ArrayList<String> strings = new ArrayList<String>();
		WebComponent wc = options.get(selected);
		strings.add("WebElement " + wc.toString() + " = driver.findElement(By.xpath(\"" + wc.xpath + "\"));");
		strings.add(wc.toString() + ".click();");
		return strings; 
	}

	public String toString(){
		return "dropdown " + id + " with " + options.size() + " options";

	}

}
