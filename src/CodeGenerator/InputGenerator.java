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
		int choice, length;
		int round = 15;
		for(int i = 0; i < round ; ++i){
			ArrayList<WebComponent> newTest = new ArrayList<WebComponent>();
			for(WebComponent wc : components){
				if(wc instanceof TextBox){
					TextBox tb = new TextBox((TextBox)wc);
					if(i < 11)choice = i;
					else choice = randomgen.nextInt(11);
					String str = "";
					switch(choice){
					case 0:
						length = randomgen.nextInt(20)+1;
						str = GenerateInput.randomNum(length);
						break;
					case 1:
						length = randomgen.nextInt(20)+1;
						str = GenerateInput.randomAlp(length);
						break;
					case 2:
						length = randomgen.nextInt(20)+1;
						str = GenerateInput.randomAlpnum(length);
						break;
					case 3:
						length = randomgen.nextInt(10)+8;
						str = GenerateInput.password(8, length, 1, 3, 1);
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
						str = GenerateInput.time(true, 4);
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
					System.out.println("round " + i + " textbox " + "choice " + choice + str);
				}
				else if(wc instanceof DropDown){
					DropDown dd = new DropDown((DropDown) wc);
					if(dd.getOptions().size()>round )round = dd.getOptions().size();
					if(i < dd.getOptions().size())choice = i;
					else choice = randomgen.nextInt(dd.getOptions().size());
					dd.select(choice);
					newTest.add(dd);
					System.out.println("round " + i + " DropDown " + "choice " + choice);
				}
				else if(wc instanceof CheckBox){
					CheckBox cb = new CheckBox((CheckBox) wc);
					if(i < 2)choice = i;
					else choice = randomgen.nextInt(2);
					if(choice == 0)cb.setContent("NotClick");
					else cb.setContent("Click");
					newTest.add(cb);	
					System.out.println("round " + i + " CheckBox " + "choice " + choice);
				}
				else if(wc instanceof RadioButton){
					RadioButton rb = new RadioButton((RadioButton) wc);
					if(rb.getOptions().size()>round)round = rb.getOptions().size();
					if(i < rb.getOptions().size())choice = i;
					else choice = randomgen.nextInt(rb.getOptions().size());
					rb.select(choice);
					newTest.add(rb);
					System.out.println("round " + i + " RadioButton " + "choice " + choice);
				}
				else if(wc instanceof Button){
					Button bt = new Button((Button) wc);
					newTest.add(bt);
					System.out.println("round " + i + " Button");
				}
				//else .. add actions for other types
			}	
			generatedInputs.add(newTest);
		}
		return generatedInputs;	
	}
}