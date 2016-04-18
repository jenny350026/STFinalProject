package WebComponent;

import java.util.ArrayList;

import org.openqa.selenium.WebElement;

public class DropDown extends WebComponentSelect{
	public DropDown(WebElement e, String xp, int id) {
		super(e, xp, id);
	}

	@Override
	public ArrayList<String> testAction() {
		return null;
	}
	
	@Override
	public void select(){
		// TODO add implementations
		// Select dd = new Select(e);
		// e.selectByIndex(0);
	}
}
