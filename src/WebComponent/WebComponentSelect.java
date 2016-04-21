package WebComponent;

import java.util.List;

public abstract class WebComponentSelect extends WebComponent {
	protected List<Option> options;
	
	public WebComponentSelect() {
		super();
	}

	public void addOption(Option elem){
		options.add(elem);
	}

	public List<Option> getOptions(){
		return options;
	}

}
