package WebComponent;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

public class DropDown extends WebComponentSelect<DropDownOption>{
	//private static int idCounter = 0;
	
	private List<DropDownOption> options;
	
//	public DropDown(WebElement e, String xp) {//could be deleted
//		super(e, xp, idCounter++);
//	}
	
	@Override
	public void addOption(DropDownOption elem){
		options.add(elem);
	}
	
	@Override
	public List<DropDownOption> getOptions(){
		return options;
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
	
//	@Override
//	public String toString(){
//		return "dropdown" + id;
//	}

}
