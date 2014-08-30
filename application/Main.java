package application;
	
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	private Stage primaryStage;
	private BorderPane baseLayout;
	private MainViewController controller;
	
	private ArrayList<String> titles;
	private ArrayList<String> wordsToIgnore;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("KWIC");
			initBaseLayout();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void initBaseLayout() {
		try {
			//load layout fxml file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("MainView.fxml"));
			baseLayout = (BorderPane)loader.load();
			
			//setup main view controller
			controller = loader.getController();
			controller.setMain(this);
			
			Scene scene = new Scene(baseLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void setTitleFile(File file) {
		InputParser parser = new InputParser(file);
		titles = parser.parseInput(false);
		readyToProcess();
	}
	
	public void setWordsFile(File file) {
		InputParser parser = new InputParser(file);
		wordsToIgnore = parser.parseInput(true);
		readyToProcess();
	}
	
	private void readyToProcess() {
		if (titles != null && !titles.isEmpty() && wordsToIgnore != null && !wordsToIgnore.isEmpty()) {
			KWICProcessor processor = new KWICProcessor(titles, wordsToIgnore);
			ArrayList<String> output = processor.getKeyWordsInContext();
			controller.displayOutput(output);
		}
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
		
	}
}
