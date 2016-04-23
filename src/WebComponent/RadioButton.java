package WebComponent;

import java.util.ArrayList;

public class RadioButton extends WebComponentSelect{
	
	public RadioButton() {
		super();
	}

	@Override
	public ArrayList<String> testAction() {
		return null;
	}
	
	@Override
	public String toString(){
		return "radiobutton" + id + " with " + options.size() + " options";
	}
	
}

