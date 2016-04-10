package CodeGenerator;
import java.util.ArrayList;

import org.openqa.selenium.WebElement;

abstract class WebComponent {
	protected WebElement element;
	protected String xpath;
	protected int id;
	
	public WebComponent(WebElement element, String xpath, int id){
		this.element = element;
		this.xpath = xpath;
		this.id = id;
	}
	
	public abstract ArrayList<String> testAction();
}
