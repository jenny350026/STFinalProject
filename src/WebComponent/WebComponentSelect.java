package WebComponent;

import java.util.ArrayList;
import java.util.List;

public abstract class WebComponentSelect extends WebComponent {
	protected List<Option> options = new ArrayList<>();
	
	public WebComponentSelect() {
		super();
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

}
