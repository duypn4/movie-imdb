import java.io.File;
import java.util.ArrayList;
import java.util.Map.Entry;
import javafx.event.ActionEvent;
import javafx.scene.control.RadioMenuItem;
import javafx.stage.Stage;

public class Controller {

	private View view;
	private Model model;
	private Stage stage;
	private File file;
	private boolean loaded;

	public Controller(Model model, View view, Stage stage) {
		this.model = model;
		this.view = view;
		this.stage = stage;

		this.view.addPaneListener(e -> switchPanes(e));
		this.view.addChooseListener(e -> selectFile());
		this.view.addLoadListener(e -> loadFile());
		this.view.addSearchListener(e -> searchFile());
		this.view.addTopListener(e -> visualiseData());
	}

	public void switchPanes(ActionEvent e) {
		RadioMenuItem item = (RadioMenuItem) e.getSource();

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

	public void selectFile() {
		file = view.getFile(stage);
		loaded = false;

		if (file != null) {
			String name = file.getName();
			String ext = name.split("\\.")[1];

			if (ext.equalsIgnoreCase("xml")) {
				view.setFileLabel(name);
			} else {
				view.setAlert("This file format is not supported!", false);
				file = null;
			}
		}

		if (file == null) {
			view.setFileLabel("No file chosen");
		}

		view.setXmlArea("");
	}

	public void loadFile() {
		if (!loaded) {
			String content = model.readFileContent(file);
			view.setXmlArea(content);
			view.setAlert("This IMDB XML source file loaded successfully...", true);

			model.createIndex(file);
			model.createTopKeywordList();

			loaded = true;

//			for (Entry<String, Integer> item: model.getTops()) {
//				System.out.println(item.getKey() + ": " + item.getValue());
//			}
		}
	}

	public void searchFile() {
		String keyword = view.getKeyword().toLowerCase();

		if (keyword.isEmpty()) {
			view.setResultArea("Please enter your keyword before searching movies!");
		} else {
			String results = model.searchMovie(keyword, file);
			view.setResultArea(results);
		}
	}

	public void visualiseData() {
		String[] token = view.getSelectedTop().split("-");
		int top = Integer.parseInt(token[1]);
		String type = view.getSelectedChart();

		ArrayList<Entry<String, Integer>> data = model.getTops();

		view.setChart(top, type, data);
	}

}