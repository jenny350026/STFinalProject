package WebComponent;

import java.util.ArrayList;

import org.openqa.selenium.WebElement;

public class Option extends WebComponent {

	private static int idCounter = 0;

	public Option(WebElement e, String xp) {
		super(e, xp, idCounter++);
	}

	@Override
	public ArrayList<String> testAction() {
		return null;
	}

	@Override
	public String toString() {
		return "option" + id;
	}
}
