package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class InputParser {
	private static final String ERROR_CANNOT_FIND_FILE = "Error: Can not find %s file.\n";
	private static final String ERROR_CANNOT_READ_FILE = "Error: Can not read %s file.\n";
	
	private File file;
	private ArrayList<String> array;
	
	public InputParser(File file) {
		this.file = file;
	}
	
	public ArrayList<String> parseInput() {
		try {
			array = new ArrayList<>();
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				array.add(line);
				line = reader.readLine();
			}
			reader.close();
			
			return array;
		} catch (FileNotFoundException e) {
			System.err.printf(ERROR_CANNOT_FIND_FILE, file.getName());
		} catch (IOException e) {
			System.err.printf(ERROR_CANNOT_READ_FILE, file.getName());
		}
		
		return null;
	}
}
