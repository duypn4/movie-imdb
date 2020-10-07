import java.io.File;
import java.util.ArrayList;
import java.util.Map.Entry;
import javafx.event.ActionEvent;
import javafx.scene.control.RadioMenuItem;
import javafx.stage.Stage;

/**
 * COS80007 Assignment 2: The class is a central controller of XML Keyword
 * Search system
 * 
 * @author Ngoc Duy Pham (102888457)
 * @author Gabriel Andreas (101272020)
 * @author Van Anh Tran (123456789)
 * 
 * @version 1.0
 */
public class Controller {

	// view object
	private View view;
	// model object
	private Model model;
	// main stage object
	private Stage stage;
	// source file object
	private File file;
	// flag showing the file loaded
	private boolean loaded;

	/**
	 * Constructor
	 * 
	 * @param model new model object
	 * @param view  new view object
	 * @param stage main stage object
	 */
	public Controller(Model model, View view, Stage stage) {
		this.model = model;
		this.view = view;
		this.stage = stage;

		// register action listener for ui components
		this.view.addPaneListener(e -> switchPanes(e));
		this.view.addChooseListener(e -> selectFile());
		this.view.addLoadListener(e -> loadFile());
		this.view.addSearchListener(e -> searchFile());
		this.view.addTopListener(e -> visualiseData());
	}

	/**
	 * Method to navigate between 3 application perspectives
	 * 
	 * @param e source event object
	 */
	public void switchPanes(ActionEvent e) {
		// get button firing event
		RadioMenuItem item = (RadioMenuItem) e.getSource();

		// check file loaded before going to other perspectives
		if (loaded) {
			String type = item.getText();

			if (type.equals("Visualisation")) {
				visualiseData();
			}
			view.setPane(type);
		} else {
			view.setAlert("Please load an IMDB XML source file first!", false);
			item.setSelected(false);
		}
	}

	/**
	 * Method to choose a XML source file
	 */
	public void selectFile() {
		file = view.getFile(stage);
		loaded = false;

		// check file exist
		if (file != null) {
			String name = file.getName();
			String ext = name.split("\\.")[1];

			// check file type valid
			if (ext.equalsIgnoreCase("xml")) {
				view.setFileLabel(name);
				view.setLoadDisable(false);
			} else {
				view.setAlert("This file format is not supported!", false);
				file = null;
			}
		}

		// reset to default if file not exist
		if (file == null) {
			view.setFileLabel("No file chosen.");
			view.setLoadDisable(true);
		}

		view.setXmlArea("");
	}

	/**
	 * Method to load a XML source file, compute a list of top correlated keywords
	 * from a keyword index
	 */
	public void loadFile() {
		view.setLoadDisable(true);
		// get file content
		String content = model.readFileContent(file);

		// check file empty
		if (content.isEmpty()) {
			view.setAlert("This file is empty, please select another source file!", false);
			view.setFileLabel("No file chosen.");
		} else {
			view.setXmlArea(content);

			// compute index and top-keywords
			model.createIndex(file);
			model.createTopKeywordList();

			loaded = true;
			view.setAlert("This IMDB XML source file loaded successfully...", true);
		}
	}

	/**
	 * Method to search movie based on their title
	 */
	public void searchFile() {
		// get keyword from text field
		String keyword = view.getKeyword().toLowerCase();

		// check keyword not empty then find movie
		if (keyword.isEmpty()) {
			view.setAlert("Please enter your keyword before searching movies!", false);
		} else {
			String results = model.searchMovie(keyword, file);
			view.setResultArea(results);
		}
	}

	/**
	 * Method to visualise top-keywords as charts
	 */
	public void visualiseData() {
		// get number of top-keywords
		String[] token = view.getSelectedTop().split("-");
		int top = Integer.parseInt(token[1]);
		// get chart type
		String type = view.getSelectedChart();
		// get entire top-keywords
		ArrayList<Entry<String, Integer>> data = model.getTops();

		view.setChart(top, type, data);
	}

}