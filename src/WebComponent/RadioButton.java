package WebComponent;

import java.util.ArrayList;

import org.openqa.selenium.WebElement;


public class RadioButton extends WebComponentSelect{
	private static int idCounter = 0;
	
	public RadioButton(WebElement e, String xp) {
		super(e, xp, idCounter++);
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
	
	@Override
	public String toString(){
		return "RadioButton" + id;
	}
}

