package WebComponent;

import java.util.ArrayList;

import org.openqa.selenium.WebElement;

public class Button extends WebComponent{
	private static int idCounter = 0;
	
	public Button(WebElement e, String xp) {
		super(e, xp, idCounter++);
	}

	public Button(Button bt) {
		// TODO
		super(bt.element, bt.xpath, idCounter++);
	}

	@Override
	public ArrayList<String> testAction() {
		// TODO 
		return null;
	}
	
	@Override
	public String toString(){
		return "button" + id;
	}
}
