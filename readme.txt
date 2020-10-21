COS80007 - Advanced Java
Assignment 2: XML Keyword Search System

Ngoc Duy Pham (102888457)
Gabriel Andreas (101272020)
Van Anh Tran (102421412)
---------------------------------------

INTRODUCTION
	Our XML Keyword Search System is designed to implement searching of keywords across an XML document. It contains the following features:
	1.	Choose, upload and parse an IMDB XML file.
	2.	Accept a keyword to search for movies by their titles.
	3.	Use various charts to visualise the most correlated keywords and their correlation score with respect to the submitted keyword.
	
DIRECTORY
	The application has 3 main subfolders, including:
	- src: a folder contains Java files.
	- arc: a folder contains a jar file of the application.
	- data: a folder contains XML files to be loaded into the application.
	
INSTRUCTIONS
    1. Run the application
    	- Method 1 (manually): Compile and run the 'XMLKeywordSearch' class (path: src/XMLKeywordSearch.java).
    	- Method 2 (automatically): Double-click to run the 'imdb.jar' file (path: arc/imdb.jar).
	2. In 'Open' page (homepage), load the 'imdb.xml' file in the 'data' folder into the application.
	3. Navigate to 'Search' page by using the menu bar 'Options/Search'. Enter a keyword to search for movies by title.
	4. Navigate to 'Visualisation' page by using the menu bar 'Options/Visualisation'. Choose to view the top correlated keywords and their frequency scores in Bar Chart or Pie Chart.
	
FEATURES
	1. Flow charts of the logical details: included in System Report
		Expected mark: 20
		
	2. UML diagram: included in System Report
		Expected mark: 30
		
	3. Using Model Controller View (MVC)
		We have applied MVC pattern to design our system and implemented three main classes: Model, View, and Controller.
		- The Model class implements the system’s logic
		- The View class represents the application’s UI
		- The Controller class controls the data flow and commands the Model and View classes
		Expected mark: 40
	
	4. Use JavaFX and advanced java features
		- The View class implements various components of JavaFX such as Stage, Scene, MenuBar, Button, FileChooser, Chart, etc.
		- Advanced data structure HashTable is used to compute and store the frequency of all keywords
		- The Interface Comparator is used to sort the keywords
		Expected mark: 80
	
	5. Use event handling
		Event handling is used to handle buttons on click such as 'Choose File', 'Load File', 'Search', etc. and display alerts for warning and confirmation.
		Expected mark: 40
	
	6. Use Graphics2D as far as possible.
		Expected mark: 30
		
	7. Include reasonable documentation according to the Javadoc standards
		Expected mark: 10
		
	8. A formally written word-version report with the clearly numbered screenshots related to the system components.
		Filename: systemreport.pdf
		Expected mark: 40
		
	9 A readme.txt file explaining features completed, expected mark and locating presence of codes from other sources.
		Expected mark: 10