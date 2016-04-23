package CodeGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import WebComponent.DropDown;
import WebComponent.TextBox;
import WebComponent.WebComponent;

public class InputGenerator {
	static List<ArrayList<WebComponent>> generateInput(List<WebComponent> components){
		List<ArrayList<WebComponent>> generatedInputs = new ArrayList<ArrayList<WebComponent>>();
		for(int i = 0; i < 3; ++i){
			ArrayList<WebComponent> newTest = new ArrayList<WebComponent>();
			for(WebComponent wc : components){
				if(wc instanceof TextBox){
					TextBox tb = new TextBox((TextBox)wc);
					tb.setContent(""+i);
					newTest.add(tb);
				}
				else if(wc instanceof DropDown){
					DropDown dd = new DropDown((DropDown) wc);
					dd.select(dd.getOptions().size()-1);
					newTest.add(dd);					
				}
				//else .. add actions for other types
			}	
			generatedInputs.add(newTest);
		}
		return generatedInputs;
		
	}
}
