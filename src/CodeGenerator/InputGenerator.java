package CodeGenerator;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import WebComponent.DropDown;
import WebComponent.TextBox;
import WebComponent.WebComponent;
import WebComponent.Button;
import WebComponent.CheckBox;
import WebComponent.RadioButton;

public class InputGenerator {
	static List<ArrayList<WebComponent>> generateInput(List<WebComponent> components) throws ParseException{
		List<ArrayList<WebComponent>> generatedInputs = new ArrayList<ArrayList<WebComponent>>();
		Random randomgen = new Random();
		int choice;
		int round = 11;
		for(int i = 0; i < round; ++i){
			ArrayList<WebComponent> newTest = new ArrayList<WebComponent>();
			for(WebComponent wc : components){
				if(wc instanceof TextBox){
					TextBox tb = new TextBox((TextBox)wc);
					if(i < 11)choice = i;
					else choice = randomgen.nextInt(11);
					String str = "";
					switch(choice){
					case 0:
						str = GenerateInput.randomNum(5);
						break;
					case 1:
						str = GenerateInput.randomAlp(8);
						break;
					case 2:
						str = GenerateInput.randomAlpnum(8);
						break;
					case 3:
						str = GenerateInput.password(8, 16, 1, 3, 1);
						break;
					case 4:
						str = GenerateInput.address();
						break;
					case 5:
						str = GenerateInput.cityName();
						break;
					case 6:
						str = GenerateInput.phoneNum(true, 1);
						break;
					case 7:
						str = GenerateInput.time(true, 1);
						break;
					case 8:
						str = GenerateInput.date(true, 0, "4/25/2016","12/31/2016");
						break;
					case 9:
						str = GenerateInput.email();
						break;
					default:
						break;
					}
					tb.setContent(""+str);
					newTest.add(tb);
				}
				else if(wc instanceof DropDown){
					DropDown dd = new DropDown((DropDown) wc);
					if(dd.getOptions().size()>round)round = dd.getOptions().size();
					if(i < dd.getOptions().size())choice = i;
					else choice = randomgen.nextInt(dd.getOptions().size());
					dd.select(choice);
					newTest.add(dd);					
				}
				else if(wc instanceof CheckBox){
					CheckBox cb = new CheckBox((CheckBox) wc);
					if(i < 2)choice = i;
					else choice = randomgen.nextInt(2);
					if(choice == 0)cb.setContent("NotClick");
					else cb.setContent("Click");
					newTest.add(cb);					
				}
				else if(wc instanceof RadioButton){
					RadioButton rb = new RadioButton((RadioButton) wc);
					if(rb.getOptions().size()>round)round = rb.getOptions().size();
					if(i < rb.getOptions().size())choice = i;
					else choice = randomgen.nextInt(rb.getOptions().size());
					rb.select(choice);
					newTest.add(rb);					
				}
				else if(wc instanceof Button){
					Button bt = new Button((Button) wc);
					newTest.add(bt);					
				}
				//else .. add actions for other types
			}	
			generatedInputs.add(newTest);
		}
		return generatedInputs;	
	}
}