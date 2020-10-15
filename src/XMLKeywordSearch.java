import javafx.application.Application;
import javafx.stage.Stage;

/**
 * COS80007 Assignment 2: The class is a main class of XML Keyword Search system
 * 
 * @author Ngoc Duy Pham (102888457)
 * @author Gabriel Andreas (101272020)
 * @author Van Anh Tran (123456789)
 * 
 * @version 1.0
 */
public class XMLKeywordSearch extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// create all MVC components
		Model model = Model.getInstance();
		View view = new View();
		Controller controller = new Controller(model, view, primaryStage);

		// set main scene and display application window
		primaryStage.setScene(view.getScene());
		primaryStage.setTitle("IMDB Visualiser");
		primaryStage.show();
	}

	/**
	 * Main method of XML Keyword Search system
	 * 
	 * @param args array of string arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}