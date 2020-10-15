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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * COS80007 Assignment 2: The class is a view interacting with user through UI
 * control components
 * 
 * @author Ngoc Duy Pham (102888457)
 * @author Gabriel Andreas (101272020)
 * @author Van Anh Tran (123456789)
 *
 * @version 1.0
 */
public class View {

	// UI components
	private Scene scene;
	private Label titleLabel, subtitleLabel, fileLabel, teamLabel;
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
	// UI layouts
	private GridPane sourcePane, searchPane, chartPane;
	// button style
	private String buttonStyle = "-fx-background-color:#adb2ff;-fx-border-color:#404040;-fx-pref-width:100px";

	/**
	 * Constructor
	 */
	public View() {
		this.initMenu();
		this.initSourcePane();
		this.initSearchPane();
		this.initChartPane();

		// create file chooser
		chooser = new FileChooser();
		chooser.setTitle("Open File...");

		// create control pane
		StackPane controlPane = new StackPane();
		controlPane.getChildren().addAll(sourcePane, searchPane, chartPane);
		controlPane.setStyle("-fx-background-color:#ffffff;");

		// create main pane
		BorderPane mainPane = new BorderPane();
		mainPane.setTop(menuBar);
		mainPane.setCenter(controlPane);

		// create scene
		scene = new Scene(mainPane, 700, 500);
	}

	/**
	 * Getter for scene
	 * 
	 * @return main application scene
	 */
	public Scene getScene() {
		return scene;
	}

	/**
	 * Method to create menu bar
	 */
	private void initMenu() {
		// create menu items
		item1 = new RadioMenuItem("Open");
		item2 = new RadioMenuItem("Search");
		item3 = new RadioMenuItem("Visualisation");

		// create toggle group
		ToggleGroup group = new ToggleGroup();
		group.getToggles().addAll(item1, item2, item3);

		// create menu
		Menu menu = new Menu("Options");
		menu.getItems().addAll(item1, item2, item3);

		// create menu bar
		menuBar = new MenuBar();
		menuBar.getMenus().add(menu);
	}

	/**
	 * Method to create scene titles
	 */
	private void initTitle() {
		// create application title
		titleLabel = new Label("XML Keyword Search System");
		titleLabel.setStyle("-fx-font-size:18px;-fx-font-weight:bold");

		// create application subtitle
		subtitleLabel = new Label("A keyword search system over a IMDB XML document.");
		subtitleLabel.setStyle("-fx-padding:0 0 30px 0");

		// create team title
		teamLabel = new Label("Team:\n- 102888457 | Duy\n- 101272020 | Gabriel\n- 123456789 | Van Anh");
	}

	/**
	 * Method to create source pane
	 */
	private void initSourcePane() {
		initTitle();

		// create file label
		fileLabel = new Label("No file chosen.");
		fileLabel.setStyle("-fx-padding:4px");

		// create choose and load buttons
		choose = new Button("Choose File");
		choose.setStyle(buttonStyle);
		load = new Button("Load File");
		load.setStyle(buttonStyle);
		load.setDisable(true);

		// create xml text area
		xmlArea = new TextArea();
		xmlArea.setEditable(false);
		xmlArea.setPrefSize(600, 300);

		// create hbox pane containing choose button and file label
		HBox hBox = new HBox(10);
		hBox.getChildren().addAll(choose, fileLabel);

		// create source pane
		sourcePane = new GridPane();
		sourcePane.getColumnConstraints().add(new ColumnConstraints(400));
		sourcePane.getColumnConstraints().add(new ColumnConstraints(200));
		sourcePane.setPadding(new Insets(30, 0, 40, 0));
		sourcePane.setAlignment(Pos.CENTER);
		sourcePane.setVgap(10);
		sourcePane.setHgap(20);
		sourcePane.add(titleLabel, 0, 0);
		sourcePane.add(subtitleLabel, 0, 1);
		sourcePane.add(teamLabel, 1, 0, 1, 2);
		sourcePane.add(hBox, 0, 2, 2, 1);
		sourcePane.add(load, 0, 3, 2, 1);
		sourcePane.add(xmlArea, 0, 4, 2, 1);
	}

	/**
	 * Method to create search pane
	 */
	private void initSearchPane() {
		initTitle();

		// create keyword label
		Label wordLabel = new Label("Enter keyword here:");
		wordLabel.setStyle("-fx-padding:4px;-fx-font-weight:bold");

		// create keyword text field
		wordField = new TextField();
		wordField.setPrefWidth(378);
		wordField.setStyle("-fx-background-color:#ffffff;-fx-border-color:#404040");

		// create search button
		search = new Button("Search");
		search.setStyle(buttonStyle);

		// create result text area
		resultArea = new TextArea();
		resultArea.setEditable(false);
		resultArea.setPrefSize(600, 300);

		// create hbox pane containing keyword label, keyword text field and search
		// button
		HBox hBox = new HBox(10);
		hBox.getChildren().addAll(wordLabel, wordField, search);

		// create search pane
		searchPane = new GridPane();
		searchPane.getColumnConstraints().add(new ColumnConstraints(400));
		searchPane.getColumnConstraints().add(new ColumnConstraints(200));
		searchPane.setPadding(new Insets(30, 0, 40, 0));
		searchPane.setAlignment(Pos.CENTER);
		searchPane.setVgap(10);
		searchPane.setHgap(20);
		searchPane.add(titleLabel, 0, 0);
		searchPane.add(subtitleLabel, 0, 1);
		searchPane.add(teamLabel, 1, 0, 1, 2);
		searchPane.add(hBox, 0, 2, 2, 1);
		searchPane.add(resultArea, 0, 3, 2, 1);
		searchPane.setVisible(false);
	}

	/**
	 * Method to create chart pane
	 */
	private void initChartPane() {
		// create toggle groups
		group1 = new ToggleGroup();
		group2 = new ToggleGroup();

		// create radio buttons
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
		topBox.setAlignment(Pos.CENTER);

		// create toggle buttons
		bar = new ToggleButton("Bar Chart");
		bar.setToggleGroup(group2);
		bar.setSelected(true);
		pie = new ToggleButton("Pie Chart");
		pie.setToggleGroup(group2);
		HBox chartBox = new HBox(20);
		chartBox.getChildren().addAll(bar, pie);
		chartBox.setAlignment(Pos.CENTER);

		// create chart pane
		chartPane = new GridPane();
		chartPane.setPadding(new Insets(10, 10, 10, 10));
		chartPane.setAlignment(Pos.CENTER);
		chartPane.setVgap(10);
		chartPane.setHgap(20);
		chartPane.add(topBox, 0, 0, 2, 1);
		chartPane.add(chartBox, 0, 2, 2, 1);
		chartPane.setVisible(false);
	}

	/**
	 * Method to get source file from file chooser
	 * 
	 * @param stage main stage object
	 * @return source file object
	 */
	public File getFile(Stage stage) {
		return chooser.showOpenDialog(stage);
	}

	/**
	 * Method to get user keyword from text field
	 * 
	 * @return user keyword string
	 */
	public String getKeyword() {
		return wordField.getText();
	}

	/**
	 * Method to get label of radio buttons
	 * 
	 * @return button label string
	 */
	public String getSelectedTop() {
		RadioButton button = (RadioButton) group1.getSelectedToggle();

		return button.getText();
	}

	/**
	 * Method to get label of toggle buttons
	 * 
	 * @return button label string
	 */
	public String getSelectedChart() {
		// chart type
		String type = "";

		ToggleButton button = (ToggleButton) group2.getSelectedToggle();
		if (button != null) {
			type = button.getText();
		}

		return type;
	}

	/**
	 * Method to set panes visible based on user selection
	 * 
	 * @param type pane type
	 */
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

	/**
	 * Method to set file label showing file name
	 * 
	 * @param text file name string
	 */
	public void setFileLabel(String text) {
		fileLabel.setText(text);
	}

	/**
	 * Method to set load button disable
	 * 
	 * @param off disable flag
	 */
	public void setLoadDisable(boolean off) {
		load.setDisable(off);
	}

	/**
	 * Method to display file content in xml text area
	 * 
	 * @param text file content
	 */
	public void setXmlArea(String text) {
		xmlArea.setText(text);
	}

	/**
	 * Method to display matching movies in result text area
	 * 
	 * @param text result string
	 */
	public void setResultArea(String text) {
		resultArea.setText(text);
	}

	/**
	 * Method to show alerts
	 * 
	 * @param message alert message
	 * @param type    alert type
	 */
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

	/**
	 * Method to display charts of correlated top-keywords
	 * 
	 * @param top  number of top-keywords
	 * @param type chart type
	 * @param data top-keywords list
	 */
	public void setChart(int top, String type, ArrayList<Entry<String, Integer>> data) {
		// remove old chart
		chartPane.getChildren().remove(chart);

		// create bar chart
		if (type.equals("Bar Chart")) {
			// create axes
			CategoryAxis xAxis = new CategoryAxis();
			int max = data.get(0).getValue();
			NumberAxis yAxis = new NumberAxis(0, max, 1);
			xAxis.setLabel("Keywords");
			yAxis.setLabel("Frequency Scores");

			// create data series for bar chart
			Series<String, Integer> series = new Series<String, Integer>();
			for (int i = 0; i < top; i++) {
				String key = data.get(i).getKey();
				int value = data.get(i).getValue();

				series.getData().add(new Data<String, Integer>(key, value));
			}

			// create bar chart
			BarChart<String, Integer> bar = new BarChart(xAxis, yAxis);
			bar.getData().add(series);

			chart = bar;
		} else if (type.equals("Pie Chart")) {
			// create data list for pie chart
			ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
			for (int i = 0; i < top; i++) {
				String key = data.get(i).getKey();
				int value = data.get(i).getValue();

				list.add(new PieChart.Data(key, value));
			}

			// create pie chart
			PieChart pie = new PieChart();
			pie.setData(list);

			chart = pie;
		}

		// add new chart into chart pane
		chart.setTitle("Top Co-occurring Keywords");
		chartPane.add(chart, 0, 1, 2, 1);
	}

	/**
	 * Method to register action listener for menu items
	 * 
	 * @param listener event handler object
	 */
	public void addPaneListener(EventHandler<ActionEvent> listener) {
		item1.setOnAction(listener);
		item2.setOnAction(listener);
		item3.setOnAction(listener);
	}

	/**
	 * Method to register action listener for choose button
	 * 
	 * @param listener event handler object
	 */
	public void addChooseListener(EventHandler<ActionEvent> listener) {
		choose.setOnAction(listener);
	}

	/**
	 * Method to register action listener for load button
	 * 
	 * @param listener event handler object
	 */
	public void addLoadListener(EventHandler<ActionEvent> listener) {
		load.setOnAction(listener);
	}

	/**
	 * Method to register action listener for search button and text field
	 * 
	 * @param listener event handler object
	 */
	public void addSearchListener(EventHandler<ActionEvent> listener) {
		search.setOnAction(listener);
		wordField.setOnAction(listener);
	}

	/**
	 * Method to register action listener for radio buttons and toggle buttons
	 * 
	 * @param listener event handler object
	 */
	public void addTopListener(EventHandler<ActionEvent> listener) {
		top3.setOnAction(listener);
		top5.setOnAction(listener);
		top8.setOnAction(listener);
		top10.setOnAction(listener);
		bar.setOnAction(listener);
		pie.setOnAction(listener);
	}

}