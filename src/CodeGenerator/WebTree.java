package CodeGenerator;

import java.util.ArrayList;
import java.util.List;

import WebComponent.WebComponent;
import WebComponent.WebComponentSelect;

public class WebTree {
	public class WebNode{
		private List<WebNode> next;
		private List<WebComponent> elements;
		private WebComponentSelect select;
		
		public WebNode(){
			next = new ArrayList<WebNode>();
			elements = new ArrayList<WebComponent>();
			select = null;
		}
		
		public void addNext(WebNode nextNode){
			next.add(nextNode);
		}
		
		public void addElement(WebComponent elem){
			elements.add(elem);
		}
		
		public void setSelect(WebComponentSelect s){
			select = s;
		}
		
		public WebComponentSelect getSelect(){
			return select;
		}
	}
	
	// TODO add functions to build tree
}
