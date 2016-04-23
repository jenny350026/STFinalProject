package CodeGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.openqa.selenium.WebElement;

import CodeGenerator.WebTree.WebNode;
import WebComponent.TextBox;
import WebComponent.WebComponent;
import WebComponent.WebComponentSelect;

public class Generator {
	private String address;
	ArrayList<String> toClickAtSetUp;
	//ArrayList<WebComponent> components;
	
	PrintWriter writer;
	private int counter;
	private static String TAG = "Generator : ";
	
	private String imports[] = {
		"org.openqa.selenium.By",
		"org.openqa.selenium.WebDriver",
		"org.openqa.selenium.WebElement",
		"org.openqa.selenium.chrome.*",
		"org.openqa.selenium.interactions.Actions",
		"org.openqa.selenium.support.ui.Select",
		"static org.junit.Assert.*",
		"org.junit.Test",
		"org.junit.Before"
	};
	
	private void printSleep(int indent){
		printFormatted("try {", indent);
		printFormatted("Thread.sleep(1000); // wait", indent+1);
		printFormatted("} catch (InterruptedException ex) {", indent);
		printFormatted("Thread.currentThread().interrupt();", indent+1);
		printFormatted("}", indent);
	}
	
	private void printFormatted(String s){
		printFormatted(s, 0);
	}
	
	private void printFormatted(String s, int indent){
		for(int i = 0; i < indent; ++i){
			writer.print("\t");
		}
		writer.println(s);
	}
	
	private void generatePackage(){
		writer.println("package GeneratedTest;");
		System.out.println(TAG + "generated package");
	}
	
	private void generateImports(){
		for(int i = 0; i < imports.length; ++i)
			writer.println("import " + imports[i] + ";");
		
		System.out.println(TAG + "generated imports");
	}
	
	private void generateSetUp(){
		printFormatted("@Before", 1);
		printFormatted("public void setUp(){", 1);
		printFormatted("System.setProperty(\"webdriver.chrome.driver\", \"lib/chromedriver.exe\");", 2);
		printFormatted("driver = new ChromeDriver();", 2);
		printFormatted("driver.get(\"" + address + "\");", 2);
		
		for(int i = 0; i < toClickAtSetUp.size(); ++i){
			printFormatted("WebElement toClick" + i + " = driver.findElement(By.xpath(\"" + toClickAtSetUp.get(i) +"\"));", 2);
			printFormatted("toClick" + i + ".click();", 2);
			printSleep(2);
		}
		
		printFormatted("}", 1);
		System.out.println(TAG + "generated setup");
		
	}
	
	private void generatePrivates(){
		printFormatted("private WebDriver driver;", 1);
	}
	
	private void generateTests(List<WebComponent> components, Map<WebComponent, Integer> selects){
		System.out.println("NEW TEST");
		
		
		//TODO numTests will depend on the pair-wise code
		printFormatted("@Test", 1);
		printFormatted("public void t" + counter++ + "() {", 1);
		
		for(WebComponent wc : selects.keySet()){
			if(wc instanceof WebComponentSelect){
				System.out.println(wc);
				((WebComponentSelect)wc).select(selects.get(wc));
				ArrayList<String> toPrint = wc.testAction();
				for(int k = 0; k < toPrint.size(); ++k)
					printFormatted(toPrint.get(k), 2);
			}
			//TODO add checkbox
		}
		
		//TODO change actions here
		for(int j = 0; j < components.size(); ++j){
			System.out.println(components.get(j));
			ArrayList<String> toPrint = components.get(j).testAction();
			for(int k = 0; k < toPrint.size(); ++k)
				printFormatted(toPrint.get(k), 2);
		}
		
		printFormatted("}", 1);
		printFormatted("");
	
		System.out.println(TAG + "generated tests");
	}
	
	public Generator(String address, ArrayList<String> toClickAtSetUp){
		this.address = address;
		this.toClickAtSetUp = toClickAtSetUp;
		//components = new ArrayList<WebComponent>();
		
		File file = new File("src/GeneratedTest/DeltaTester.java");
		try {
			writer = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	public void generate(WebTree tree){
		generatePackage();
		writer.println();
		
		generateImports();
		writer.println();
		
		writer.println("public class DeltaTester {");
		
		generatePrivates();
		writer.println();
		generateSetUp();
		writer.println();
		
		// traverse tree...
		traverse(tree.head, new ArrayList<WebComponent>(), new HashMap<WebComponent, Integer>());
		
		writer.println("}");
		writer.close();
	}
	
	public void traverse(WebNode node, List<WebComponent> components, Map<WebComponent, Integer> selects){
		components.addAll(node.getElements());
		if(node.getNext().isEmpty()){ // leaf
			for(ArrayList<WebComponent> list : InputGenerator.generateInput(components))
				generateTests(list, selects);
		}
		
		for(WebComponent s : node.getSelects()){
			if(s instanceof WebComponentSelect){
				for(int i = 0; i < ((WebComponentSelect)s).getOptions().size(); ++i){
					Map<WebComponent, Integer> newSelect = new HashMap<WebComponent, Integer>(selects);
					newSelect.put(s, i);
					traverse(node.getNext().get(i), new ArrayList<WebComponent>(components), newSelect);
				}
			}
			// TODO add checkbox
		}
	}
}
