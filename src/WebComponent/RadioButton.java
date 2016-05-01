package WebComponent;

import java.util.ArrayList;

public class RadioButton extends WebComponentSelect{
	
	public RadioButton() {
		super();
	}

	public RadioButton(RadioButton rb) {
		// TODO Auto-generated constructor stub
		super(rb);
	}

	@Override
	public ArrayList<String> testAction() {
		ArrayList<String> strings = new ArrayList<String>();
		WebComponent wc = options.get(selected);
		strings.add("WebElement " + wc.toString() + " = driver.findElement(By.xpath(\"" + wc.xpath + "\"));");
		strings.add(wc.toString() + ".click();");
		return strings; 
	}
	
	@Override
	public String toString(){
		return "radiobutton" + id + " with " + options.size() + " options";
	}
	
}

