package CodeGenerator;

import java.util.ArrayList;
import java.util.List;

import WebComponent.WebComponent;
import WebComponent.WebComponentSelect;

public class WebNode{
	private List<WebComponent> elements;
	private List<WebComponent> selects;//private WebComponentSelect select;
	private List<WebNode> next;
	
	public WebNode(){
		elements = new ArrayList<WebComponent>();
		selects = new ArrayList<WebComponent>();
		next = new ArrayList<WebNode>();
	}
	
	//elements
	public void addElement(WebComponent elem){
		elements.add(elem);
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
	
	public List<WebNode> getNext(){
		return next;
	}

}
