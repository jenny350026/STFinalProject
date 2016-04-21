package WebComponent;


import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

public abstract class WebComponentSelect<WebComponent>{// extends WebComponent{
//	public WebComponentSelect(WebElement e, String xp, int id) {
//		super(e, xp, id);
//	}

	public abstract void select();
	
	protected List<DropDownOption> options;
	
	public abstract void addOption(WebComponent elem);
	
	public abstract List<WebComponent> getOptions();

	public ArrayList<String> testAction() {
		// TODO Auto-generated method stub
		return null;
	}
}
