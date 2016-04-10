package CodeGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

public class Generator {
	private String address;
	ArrayList<String> toClickAtSetUp;
	ArrayList<WebComponent> components;
	
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
		
		/* doesn't seem necessary right now
		printFormatted("try {", 2);
		printFormatted("Thread.sleep(3000); // wait", 3);
		printFormatted("} catch (InterruptedException ex) {", 2);
		printFormatted("Thread.currentThread().interrupt();", 3);
		printFormatted("}", 2);
		*/
		
		for(int i = 0; i < toClickAtSetUp.size(); ++i){
			printFormatted("WebElement toClick" + i + " = driver.findElement(By.xpath(\"" + toClickAtSetUp.get(i) +"\"));", 2);
			printFormatted("toClick" + i + ".click();", 2);
		}
		
		printFormatted("}", 1);
		System.out.println(TAG + "generated setup");
		
	}
	
	private void generatePrivates(){
		printFormatted("private WebDriver driver;", 1);
	}
	
	private void generateTests(){
		writer.println("public class DeltaTester {");
		
		generatePrivates();
		writer.println();
		generateSetUp();
		writer.println();
		
		//TODO numTests will depend on the pair-wise code
		int numTests = 2;
		for(int i = 0; i < numTests; ++i){
			printFormatted("@Test", 1);
			printFormatted("public void t" + counter++ + "() {", 1);
			
			//TODO change actions here
			for(int j = 0; j < components.size(); ++j){
				ArrayList<String> toPrint = components.get(j).testAction();
				for(int k = 0; k < toPrint.size(); ++k)
					printFormatted(toPrint.get(k), 2);
			}
			
			printFormatted("}", 1);
			printFormatted("");
		}
		writer.println("}");
		System.out.println(TAG + "generated tests");
	}
	
	public Generator(String address, ArrayList<String> toClickAtSetUp){
		this.address = address;
		this.toClickAtSetUp = toClickAtSetUp;
		components = new ArrayList<WebComponent>();
		
		File file = new File("src/GeneratedTest/DeltaTester.java");
		try {
			writer = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addTextBox(WebElement e, String xpath){
		components.add(new TextBox(e, xpath, components.size()));
	}
	
	public void generate(){
		generatePackage();
		writer.println();
		
		generateImports();
		writer.println();
		
		generateTests();
		writer.close();
	}
}
