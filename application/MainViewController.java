package application;

import java.io.File;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

public class MainViewController {
	private Main main;
	
	@FXML
	private Label selectedTitleName;
	
	@FXML
	private Label selectedWordName;
	
	@FXML
	private Button importTitleButton;
	
	@FXML
	private Button importWordButton;
	
	@FXML
	private TextArea outputTextArea;
	
	public MainViewController() {
		
	}
	
	@FXML
	private void initialize(){
		
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	@FXML
	private void handleImportTitles(){
		FileChooser fChooser = new FileChooser();
		File file = fChooser.showOpenDialog(main.getPrimaryStage());
		if (file != null) {
			updateSelectedSelectedFileName(selectedTitleName, file.getName());
			main.setTitleFile(file);
		}
	}
	
	@FXML
	private void handleImportWords(){
		FileChooser fChooser = new FileChooser();
		File file = fChooser.showOpenDialog(main.getPrimaryStage());
		if (file != null) {
			updateSelectedSelectedFileName(selectedWordName, file.getName());
			main.setWordsFile(file);
		}
	}
	
	private void updateSelectedSelectedFileName(Label label, String name) {
		label.setText(name);
	}
	
	public void displayOutput(ArrayList<String> output) {
		outputTextArea.setText("");
		StringBuilder outputString = new StringBuilder();
		for (String line : output) {
			outputString.append(line + "\n");
		}
		outputTextArea.setText(outputString.toString());
	}
}
