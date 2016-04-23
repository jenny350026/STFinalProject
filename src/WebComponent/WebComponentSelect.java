package WebComponent;

import java.util.ArrayList;
import java.util.List;

public abstract class WebComponentSelect extends WebComponent {
	protected List<Option> options = new ArrayList<Option>();
	protected int selected;
	
	public WebComponentSelect() {
		super();
	}
	
	public WebComponentSelect(WebComponentSelect wcs){
		options = new ArrayList<Option>(wcs.options);
	}

	public void addOption(Option elem){
		if(options==null){
			options = new ArrayList<Option>();
		}
		options.add(elem);
	}

	public List<Option> getOptions(){
		return options;
	}

	public void select(int n){
		selected = n;
	}

}
