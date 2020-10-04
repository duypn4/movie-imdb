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
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class View {

	// Setup view elements.
	
	private Scene scene;
	private GridPane sourcePane, searchPane, chartPane;
	private Label titleLabel, subtitleLabel, creditsLabel, fileLabel, teamLabel;
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
	
	
	// Reusable view styles.
	
	private String dark           = "#404040";
	private String boldStyle      = "-fx-font-size:14px;-fx-font-weight:bold;";
	private String titleStyle     = "-fx-font-size:18px;-fx-font-weight:bold;";
	private String subtitleStyle  = "-fx-padding:0 0 50px 0;";
	private String labelStyle     = "-fx-padding:4px;";
	private String boldLabelStyle = labelStyle + "-fx-font-weight:bold;";
	private String buttonStyle    = "-fx-background-color:#adb2ff;-fx-border-color:" + dark + ";-fx-pref-width:100px;";
	private String disabledButtonStyle = "-fx-background-color:#ffb2b8;-fx-border-color:" + dark + ";-fx-pref-width:100px;";
	private String fieldStyle     = "-fx-background-color:#ffffff;-fx-border-color:" + dark + ";";
	private String boxStyle       = "-fx-padding:5px;-fx-border:1px;-fx-border-color:" + dark + ";";

	
	/* 
	 * Initialise all the views.
	 */
	public View() {
		
		// Initialise the top menu bar/navigation.
		this.initMenu();
		
		
		// Initialise the source page.
		this.initSourcePane();
		
		
		// Initialise the search page.
		this.initSearchPane();
		
		
		// Initialise the chart page.
		this.initChartPane();

		
		// File choose popup.
		chooser = new FileChooser();
		chooser.setTitle("Open File...");

		
		// Stack all the pages.
		StackPane controlPane = new StackPane();
		controlPane.getChildren().addAll(sourcePane, searchPane, chartPane);
		controlPane.setStyle("-fx-background-color:#ffffff;");
		
		
		// Combine the top menu bar with the stacked pages.
		BorderPane mainPane = new BorderPane();
		mainPane.setTop(menuBar);
		mainPane.setCenter(controlPane);

		
		// Put it in into the scene.
		scene = new Scene(mainPane, 700, 500);
	}

	
	/* 
	 * Scene getter.
	 */
	public Scene getScene() {
		return scene;
	}

	
	/* 
	 * Initialise the top menu bar/navigation.
	 */
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
	
	
	/* 
	 * Setup page header texts.
	 */
	private void setupHeader() {
		titleLabel = new Label("XML Keyword Search System");
		titleLabel.setStyle(titleStyle);
		
		subtitleLabel = new Label("A keyword search system over a IMDB XML document.");
		subtitleLabel.setStyle(subtitleStyle);

		teamLabel = new Label("Team:");
		teamLabel.setStyle(boldStyle);
		creditsLabel = new Label("- 123456789 | Duy\n- 101272020 | Gabriel\n- 123456789 | Van Anh");
	}

	
	/* 
	 * Initialise the source page.
	 */
	private void initSourcePane() {
		
		// Setup text.
		setupHeader();
		
		fileLabel = new Label("No file chosen.");
		fileLabel.setStyle(labelStyle);
		
		
		// Setup buttons.
		choose = new Button("Choose File");
		choose.setStyle(buttonStyle);
		
		load = new Button("Load File");
		load.setStyle(disabledButtonStyle);
		load.setDisable(true); // set it disabled at the beginning, since no file. uploaded yet
		
		
		// Setup the textarea to display the file loaded file content.
		xmlArea = new TextArea();
		xmlArea.setEditable(false);
		xmlArea.setPrefSize(600, 300);

		
		// Horizontal box for upload a file components.
		HBox hBox = new HBox(10);
		hBox.getChildren().addAll(choose, fileLabel);

		
		// Grid panel contains all elements in the source panel.
		sourcePane = new GridPane();
		sourcePane.getColumnConstraints().add(new ColumnConstraints(400));
		sourcePane.getColumnConstraints().add(new ColumnConstraints(200));
		sourcePane.setPadding(new Insets(30, 0, 40, 0));
		sourcePane.setAlignment(Pos.CENTER);
		sourcePane.setVgap(10);
		sourcePane.setHgap(20);
		sourcePane.add(titleLabel, 0, 0);
		sourcePane.add(subtitleLabel, 0, 1);
		sourcePane.add(teamLabel, 1, 0);
		sourcePane.add(creditsLabel, 1, 1);
		sourcePane.add(hBox, 0, 2, 2, 1);
		sourcePane.add(load, 0, 3, 2, 1);
		sourcePane.add(xmlArea, 0, 4, 2, 1);
	}

	
	/* 
	 * Initialise the search page.
	 */
	private void initSearchPane() {
		
		// Setup text.
		setupHeader();
		
		Label wordLabel = new Label("Enter keyword here:");
		wordLabel.setStyle(boldLabelStyle);

		
		// Setup fields.
		wordField = new TextField();
		wordField.setPrefWidth(390);
		wordField.setStyle(fieldStyle);
		
		
		// Setup buttons.
		search = new Button("Search");
		search.setStyle(buttonStyle);
		

		// Setup the textarea to display the searched content.
		resultArea = new TextArea();
		resultArea.setEditable(false);
		resultArea.setPrefSize(600, 300);

		
		// Horizontal box for fields.
		HBox hBox = new HBox(10);
		hBox.getChildren().addAll(wordLabel, wordField, search);

		
		// Grid panel contains all elements in the search panel.
		searchPane = new GridPane();
		searchPane.getColumnConstraints().add(new ColumnConstraints(400));
		searchPane.getColumnConstraints().add(new ColumnConstraints(200));
		searchPane.setPadding(new Insets(30, 0, 40, 0));
		searchPane.setAlignment(Pos.CENTER);
		searchPane.setVgap(10);
		searchPane.setHgap(20);
		searchPane.add(titleLabel, 0, 0);
		searchPane.add(subtitleLabel, 0, 1);
		searchPane.add(teamLabel, 1, 0);
		searchPane.add(creditsLabel, 1, 1);
		searchPane.add(hBox, 0, 2, 2, 1);
		searchPane.add(resultArea, 0, 3, 2, 1);
		searchPane.setVisible(false);
	}

	
	/* 
	 * Initialise the chart page.
	 */
	private void initChartPane() {
		
		// Setup text.
		setupHeader();
		
		group1 = new ToggleGroup();
		group2 = new ToggleGroup();

		
		// Setup radio button options.
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
		topBox.setStyle("-fx-pref-width:600px;-fx-padding:5px;");

		
		// Setup charts button options.
		bar = new ToggleButton("Bar Chart");
		bar.setToggleGroup(group2);
		bar.setSelected(true);
		bar.setStyle(buttonStyle);
		pie = new ToggleButton("Pie Chart");
		pie.setToggleGroup(group2);
		pie.setStyle(buttonStyle);
		
		HBox chartBox = new HBox(20);
		chartBox.getChildren().addAll(bar, pie);
		chartBox.setAlignment(Pos.CENTER);
		chartBox.setStyle("-fx-pref-width:600px;-fx-padding:5px;");

		
		// Combine all the options into a box.
		VBox optionsBox = new VBox(20);
		optionsBox.getChildren().addAll(topBox, chartBox);
		optionsBox.setStyle(boxStyle + "-fx-pref-width:600px;");
		
		
		// Grid panel contains all elements in the chart panel.
		chartPane = new GridPane();
		chartPane.getColumnConstraints().add(new ColumnConstraints(400));
		chartPane.getColumnConstraints().add(new ColumnConstraints(200));
		chartPane.setPadding(new Insets(30, 0, 40, 0));
		chartPane.setAlignment(Pos.CENTER);
		chartPane.setVgap(10);
		chartPane.setHgap(20);
		chartPane.add(titleLabel, 0, 0);
		chartPane.add(subtitleLabel, 0, 1);
		chartPane.add(teamLabel, 1, 0);
		chartPane.add(creditsLabel, 1, 1);
		chartPane.add(optionsBox, 0, 2, 2, 1);
		chartPane.setVisible(false);
	}
	
	
	/* 
	 * Open file upload dialog.
	 */
	public File getFile(Stage stage) {
		return chooser.showOpenDialog(stage);
	}

	
	/* 
	 * Get the keywords from wordField.
	 */
	public String getKeyword() {
		return wordField.getText();
	}

	
	/* 
	 * Get selected top chart option.
	 */
	public String getSelectedTop() {
		RadioButton button = (RadioButton) group1.getSelectedToggle();

		return button.getText();
	}

	
	/* 
	 * Get selected chart type option.
	 */
	public String getSelectedChart() {
		ToggleButton button = (ToggleButton) group2.getSelectedToggle();

		return button.getText();
	}

	
	/* 
	 * Switch between pages/panels.
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

	
	/* 
	 * Set the file text from the upload dialog.
	 */
	public void setFileLabel(String text) {
		fileLabel.setText(text);
	}
	
	
	/* 
	 * Enable the load button.
	 */
	public void enableLoadButton() {
		load.setDisable(false);
		load.setStyle(buttonStyle + "-fx-pref-width:100px;");
	}
	
	
	/* 
	 * Disable the load button.
	 */
	public void disableLoadButton() {
		load.setDisable(true);
		load.setStyle(disabledButtonStyle + "-fx-pref-width:100px;");
	}

	
	/* 
	 * Set XML area for the loaded content result.
	 */
	public void setXmlArea(String text) {
		xmlArea.setText(text);
	}

	
	/* 
	 * Set result area for the search result.
	 */
	public void setResultArea(String text) {
		resultArea.setText(text);
	}

	
	/* 
	 * Show alerts/popup.
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

	
	/* 
	 * Calculate and display the charts in the chart page.
	 */
	public void setChart(int top, String type, ArrayList<Entry<String, Integer>> data) {
		chartPane.getChildren().remove(chart);

		// Show a bar chart.
		if (type.equals("Bar Chart")) {
			
			CategoryAxis xAxis = new CategoryAxis();
			int max = data.get(0).getValue();
			NumberAxis yAxis = new NumberAxis(0, max, 1);
			
			
			// Set bar chart labels.
			xAxis.setLabel("Keywords");
			yAxis.setLabel("Frequency Scores");

			
			// Calculate bar chart data.
			Series<String, Integer> series = new Series<String, Integer>();
			
			for (int i = 0; i < top; i++) {
				String key = data.get(i).getKey();
				int value = data.get(i).getValue();

				series.getData().add(new Data<String, Integer>(key, value));
			}

			BarChart<String, Integer> bar = new BarChart(xAxis, yAxis);
			bar.getData().add(series);

			chart = bar;
			
		// Show a pie chart.
		} else if (type.equals("Pie Chart")) {
			
			ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
			
			
			// Calculate the pie chart data.
			for (int i = 0; i < top; i++) {
				String key = data.get(i).getKey();
				int value = data.get(i).getValue();

				list.add(new PieChart.Data(key, value));
			}

			PieChart pie = new PieChart();
			pie.setData(list);

			chart = pie;
		}

		
		// Show the chart.
		chart.setTitle("Top Co-occurring Keywords");
		chartPane.add(chart, 0, 3, 2, 1);
	}

	
	/* 
	 * Add listeners for menu items.
	 */
	public void addPaneListener(EventHandler<ActionEvent> listener) {
		item1.setOnAction(listener);
		item2.setOnAction(listener);
		item3.setOnAction(listener);
	}

	
	/* 
	 * Add a listener after choosing a file from the upload dialog.
	 */
	public void addChooseListener(EventHandler<ActionEvent> listener) {
		choose.setOnAction(listener);
	}

	
	/* 
	 * Add a click listener for the load button.
	 */
	public void addLoadListener(EventHandler<ActionEvent> listener) {
		load.setOnAction(listener);
	}

	
	/* 
	 * Add listeners to search function.
	 */
	public void addSearchListener(EventHandler<ActionEvent> listener) {
		search.setOnAction(listener);
		wordField.setOnAction(listener);
	}

	
	/* 
	 * Add listeners for all chart options.
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