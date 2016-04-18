package WebComponent;


import org.openqa.selenium.WebElement;

public abstract class WebComponentSelect extends WebComponent{
	public WebComponentSelect(WebElement e, String xp, int id) {
		super(e, xp, id);
	}

	public abstract void select();
}
