package WebComponent;

import java.util.ArrayList;

public class RadioButton extends WebComponentSelect{
	
	public RadioButton() {
		super();
	}

	public RadioButton(RadioButton rb) {
		// TODO Auto-generated constructor stub
		super(rb);
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

