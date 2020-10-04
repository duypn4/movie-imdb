import java.io.File;
import java.util.ArrayList;
import java.util.Map.Entry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class View {

	private Scene scene;
	private GridPane sourcePane, searchPane, chartPane;
	private Label fileLabel;
	private Button choose, load, search;
	private ToggleButton bar, pie;
	private TextField wordField;
	private TextArea xmlArea, resultArea;
	private FileChooser chooser;
	private MenuBar menuBar;
	private RadioMenuItem item1, item2, item3;
	private RadioButton top3, top5, top8, top10;
	private ToggleGroup group1, group2;
	private Chart chart;

	public View() {
		this.initMenu();
		this.initSourcePane();
		this.initSearchPane();
		this.initChartPane();

		chooser = new FileChooser();
		chooser.setTitle("Open File...");

		StackPane controlPane = new StackPane();
		controlPane.getChildren().addAll(sourcePane, searchPane, chartPane);

		BorderPane mainPane = new BorderPane();
		mainPane.setTop(menuBar);
		mainPane.setCenter(controlPane);

		scene = new Scene(mainPane, 700, 500);
	}

	public Scene getScene() {
		return scene;
	}

	private void initMenu() {
		item1 = new RadioMenuItem("Open");
		item2 = new RadioMenuItem("Search");
		item3 = new RadioMenuItem("Visualisation");

		ToggleGroup group = new ToggleGroup();
		group.getToggles().addAll(item1, item2, item3);

		Menu menu = new Menu("Options");
		menu.getItems().addAll(item1, item2, item3);

		menuBar = new MenuBar();
		menuBar.getMenus().add(menu);
	}

	private void initSourcePane() {
		fileLabel = new Label("No file chosen");
		choose = new Button("Choose File");
		load = new Button("Load File");
		xmlArea = new TextArea();
		xmlArea.setEditable(false);
		xmlArea.setPrefSize(600, 300);

		HBox hBox = new HBox(10);
		hBox.getChildren().addAll(choose, fileLabel);

		sourcePane = new GridPane();
		sourcePane.setAlignment(Pos.CENTER);
		sourcePane.setVgap(20);
		sourcePane.setHgap(20);
		sourcePane.addRow(0, hBox);
		sourcePane.addRow(1, load);
		sourcePane.addRow(2, xmlArea);
	}

	private void initSearchPane() {
		Label wordLabel = new Label("Enter keyword here:");
		wordField = new TextField();
		search = new Button("Search");
		resultArea = new TextArea();
		resultArea.setEditable(false);
		resultArea.setPrefSize(600, 300);

		HBox hBox = new HBox(10);
		hBox.getChildren().addAll(wordLabel, wordField);

		searchPane = new GridPane();
		searchPane.setAlignment(Pos.CENTER);
		searchPane.setVgap(20);
		searchPane.setHgap(20);
		searchPane.addRow(0, hBox);
		searchPane.addRow(1, search);
		searchPane.addRow(2, resultArea);
		searchPane.setVisible(false);
	}

	private void initChartPane() {
		group1 = new ToggleGroup();
		group2 = new ToggleGroup();

		top3 = new RadioButton("Top-3");
		top3.setToggleGroup(group1);
		top3.setSelected(true);
		top5 = new RadioButton("Top-5");
		top5.setToggleGroup(group1);
		top8 = new RadioButton("Top-8");
		top8.setToggleGroup(group1);
		top10 = new RadioButton("Top-10");
		top10.setToggleGroup(group1);
		HBox topBox = new HBox(20);
		topBox.getChildren().addAll(top3, top5, top8, top10);

		bar = new ToggleButton("Bar Chart");
		bar.setToggleGroup(group2);
		bar.setSelected(true);
		pie = new ToggleButton("Pie Chart");
		pie.setToggleGroup(group2);
		HBox chartBox = new HBox(20);
		chartBox.getChildren().addAll(bar, pie);

		chartPane = new GridPane();
		chartPane.setPadding(new Insets(10, 10, 10, 10));
		chartPane.setAlignment(Pos.CENTER);
		chartPane.setVgap(20);
		chartPane.setHgap(20);
		chartPane.addRow(0, topBox);
		chartPane.addRow(2, chartBox);
		chartPane.setVisible(false);
	}

	public File getFile(Stage stage) {
		return chooser.showOpenDialog(stage);
	}

	public String getKeyword() {
		return wordField.getText();
	}

	public String getSelectedTop() {
		RadioButton button = (RadioButton) group1.getSelectedToggle();

		return button.getText();
	}

	public String getSelectedChart() {
		ToggleButton button = (ToggleButton) group2.getSelectedToggle();

		return button.getText();
	}

	public void setPane(String type) {
		switch (type) {
		case "Open":
			sourcePane.setVisible(true);
			searchPane.setVisible(false);
			chartPane.setVisible(false);
			break;
		case "Search":
			sourcePane.setVisible(false);
			searchPane.setVisible(true);
			chartPane.setVisible(false);
			break;
		case "Visualisation":
			sourcePane.setVisible(false);
			searchPane.setVisible(false);
			chartPane.setVisible(true);
			break;
		}
	}

	public void setFileLabel(String text) {
		fileLabel.setText(text);
	}

	public void setXmlArea(String text) {
		xmlArea.setText(text);
	}

	public void setResultArea(String text) {
		resultArea.setText(text);
	}

	public void setAlert(String message, boolean type) {
		Alert alert = null;

		if (type) {
			alert = new Alert(AlertType.INFORMATION);
		} else {
			alert = new Alert(AlertType.WARNING);
		}
		alert.setTitle("IMDB Visualiser");
		alert.setContentText(message);
		alert.show();
	}

	public void setChart(int top, String type, ArrayList<Entry<String, Integer>> data) {
		chartPane.getChildren().remove(chart);

		if (type.equals("Bar Chart")) {
			CategoryAxis xAxis = new CategoryAxis();
			int max = data.get(0).getValue();
			NumberAxis yAxis = new NumberAxis(0, max, 1);
			xAxis.setLabel("Keywords");
			yAxis.setLabel("Frequency Scores");

			Series<String, Integer> series = new Series<String, Integer>();
			for (int i = 0; i < top; i++) {
				String key = data.get(i).getKey();
				int value = data.get(i).getValue();

				series.getData().add(new Data<String, Integer>(key, value));
			}

			BarChart<String, Integer> bar = new BarChart(xAxis, yAxis);
			bar.getData().add(series);

			chart = bar;
		} else if (type.equals("Pie Chart")) {
			ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
			for (int i = 0; i < top; i++) {
				String key = data.get(i).getKey();
				int value = data.get(i).getValue();

				list.add(new PieChart.Data(key, value));
			}

			PieChart pie = new PieChart();
			pie.setData(list);

			chart = pie;
		}

		chart.setTitle("Top Co-occurring Keywords");
		chartPane.addRow(1, chart);
	}

	public void addPaneListener(EventHandler<ActionEvent> listener) {
		item1.setOnAction(listener);
		item2.setOnAction(listener);
		item3.setOnAction(listener);
	}

	public void addChooseListener(EventHandler<ActionEvent> listener) {
		choose.setOnAction(listener);
	}

	public void addLoadListener(EventHandler<ActionEvent> listener) {
		load.setOnAction(listener);
	}

	public void addSearchListener(EventHandler<ActionEvent> listener) {
		search.setOnAction(listener);
		wordField.setOnAction(listener);
	}

	public void addTopListener(EventHandler<ActionEvent> listener) {
		top3.setOnAction(listener);
		top5.setOnAction(listener);
		top8.setOnAction(listener);
		top10.setOnAction(listener);
		bar.setOnAction(listener);
		pie.setOnAction(listener);
	}

}