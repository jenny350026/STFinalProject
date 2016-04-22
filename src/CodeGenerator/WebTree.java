package CodeGenerator;

import java.util.ArrayList;
import java.util.List;

import WebComponent.WebComponent;
import WebComponent.WebComponentSelect;

public class WebTree {
	
	public WebNode head;
	
	public WebTree(){
		head = new WebNode();
	}
	
	public class WebNode{
		private List<WebComponent> selects; // things that will change UI on click
		private List<WebComponent> elements;
		private List<WebNode> next;
		
		// class invariant : selects.size == next.size
		
		public WebNode(){
			selects = new ArrayList<WebComponent>();
			elements = new ArrayList<WebComponent>();
			next = new ArrayList<WebNode>();
		}
		
		//elements
		public void addElement(WebComponent elem){
			elements.add(elem);
//			System.out.print("adding element");
//			System.out.println(elem);
		}
		
		public List<WebComponent> getElements(){
			return elements;
		}
		
		//selects
		public void addSelect(WebComponent elem){
			selects.add(elem);
		}
		
		public List<WebComponent> getSelects(){
			return selects;
		}
		
		//next
		public void addNext(WebNode nextNode){
			next.add(nextNode);
		}
		
		public void addNext(){
			next.add(new WebNode());
		}
		
		public List<WebNode> getNext(){
			return next;
		}

		public String toString(){
			return toStringHelper(0);
		}
		
		public String toStringHelper(int level){
			String s = "";
			for(int i = 0; i < level; ++i)
				s += "\t";
			s+= elements.size() + " elements in this node: \n";
			for(int i = 0; i < elements.size(); ++i){
				for(int j = 0; j < level; ++j)
					s += "\t";
				s += "element " + i + " " + elements.get(i).toString() + "\n";
			}
			for(int i = 0; i < selects.size(); ++i){
				if(selects.get(i) instanceof WebComponentSelect){
					WebComponentSelect select = (WebComponentSelect) selects.get(i);
					for(int m = 0; m < select.getOptions().size();m++){
						for(int j = 0; j < level; ++j)
							s += "\t";
						s += "select " + m + " " + select.toString() + "\n";
						s += next.get(m).toStringHelper(level+1);
					}
				}else{//may be checkbox, need to be fixed
					for(int j = 0; j < level; ++j)
						s += "\t";
					s += "select " + i + " " + selects.get(i).toString() + "\n";
					s += next.get(i).toStringHelper(level+1);
				}
			}
			return s;
		}

		
	}
	
	public String toString(){
		//System.out.println(head.selects.size());
		return head.toString();
		//return "this is head";
	}
	
	public void traverse(){
		
	}
}
