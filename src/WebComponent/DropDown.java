package WebComponent;

import java.util.ArrayList;

public class DropDown extends WebComponentSelect{
	
	public DropDown(){
		super();
	}
	
	@Override
	public ArrayList<String> testAction() {
		return null;
	}
	
//	@Override

//	public void select(){
//		// TODO add implementations
//		// Select dd = new Select(e);
//		// e.selectByIndex(0);

	public String toString(){
		return "dropdown" + id + " with " + options.size() + " options";

	}

}
