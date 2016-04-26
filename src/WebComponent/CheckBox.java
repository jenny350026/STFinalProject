package WebComponent;

import java.util.ArrayList;

import org.openqa.selenium.WebElement;

public class CheckBox extends WebComponent{
	private static int idCounter = 0;
	
	private String content;
	
	public CheckBox(WebElement e, String xp) {
		super(e, xp, idCounter++);
		content = "";
	}

	public CheckBox(CheckBox cb) {
		// TODO Auto-generated constructor stub
		super(cb.element, cb.xpath, idCounter++);
		content = "";
	}

	@Override
	public ArrayList<String> testAction() {
		// TODO 
		return null;
	}
	
	public void setContent(String c){
		content = c;
	}
	
	@Override
	public String toString(){
		return "checkbox" + id;
	}
}
