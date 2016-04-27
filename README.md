# STFinalProject
Final Project for Software Testing Spring 2016

## Project SetUp
1. Clone the project into the folder where you would like your Eclipse project to be
2. Open Eclipse, create a new Java project under the folder chosen above, 
   name the project "STFinalProject" (name must be exactly as typed)
3. Right click on 'STFinalProject' under 'Package Explorer' -> Build Path -> Configure BuildPath
4. Under the 'Libraries' tab, click on 'Add External JARs', and select all *.jar files in the 'lib' folder
5. Right click on 'STFinalProject' under 'Configure' -> Convert to Maven Project...
6. Add DataFactory to the maven project, add it as a dependency in the pom.xml file.
Choose Dependencies, click add, in the prompted window, input groupId,artifactId, version as following.
Save and check the xml, following code is added
<dependency>
    <groupId>org.fluttercode.datafactory</groupId>
    <artifactId>datafactory</artifactId>
    <version>0.8</version>
    <type>jar</type>
</dependency>
7. click on Maven -> update Project


## Libraries
Apache Commons Lang -- https://commons.apache.org/proper/commons-lang/download_lang.cgi

Selenium Web Driver -- http://www.seleniumhq.org/

Joda-time -- http://www.joda.org/joda-time/index.html

datafactory -- http://mvnrepository.com/artifact/org.fluttercode.datafactory/datafactory/0.8
