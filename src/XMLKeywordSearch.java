import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class XMLKeywordSearch extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Model model = Model.getInstance();
		View view = new View();
		Controller controller = new Controller(model, view, primaryStage);

		primaryStage.setScene(view.getScene());
		primaryStage.setTitle("IMDB Visualiser");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}