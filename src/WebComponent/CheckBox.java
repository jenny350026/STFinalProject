package WebComponent;

import java.util.ArrayList;

import org.openqa.selenium.WebElement;

public class CheckBox extends WebComponent{
	private static int idCounter = 0;
	
	public CheckBox(WebElement e, String xp) {
		super(e, xp, idCounter++);
	}

	@Override
	public ArrayList<String> testAction() {
		// TODO 
		return null;
	}
	
	@Override
	public String toString(){
		return "checkbox" + id;
	}
}
