import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class KWIC {
	private static final String TITLE_INPUT_INSTRUCTION = "Please enter the path/to/file_name containing titles: ";
	private static final String WORD_INPUT_INSTRUCTION = "Please enter the path/to/file_name containing titles: ";
	private static final String ERROR_CANNOT_FIND_FILE = "Error: Can not find %s file.\n";
	private static final String ERROR_CANNOT_READ_FILE = "Error: Can not read %s file.\n";
	private static final String ERROR_CANNOT_WRITE_FILE = "Error: Can not write output.txt.";
	private static final String OUTPUT_FILE_NAME = "output.txt";
	
	private ArrayList<String> titles;
	private ArrayList<String> wordsToIgnore;
	private ArrayList<String> keywordsInContext;
	
	public static void main(String[] args) {
		KWIC kwic = new KWIC();
		
		String titleFilePath = kwic.readInputPath(TITLE_INPUT_INSTRUCTION);
		kwic.parseTitles(titleFilePath);
		
		String wordFilePath = kwic.readInputPath(WORD_INPUT_INSTRUCTION);
		kwic.parseWords(wordFilePath);
		
		//processing
		ArrayList<String> output = kwic.getKeyWordsInContext();
		kwic.displayOutput(output);
	}
	
	private String readInputPath(String instruction) {
		Scanner scan = new Scanner(System.in);
 		System.out.println(instruction);
 		String filePath = scan.next();
 		scan.close();
 		
 		return filePath;
	}
	
	private void parseTitles(String filePath) {
		titles = new ArrayList<>();
		parseInput(filePath, titles);
	}
	
	private void parseWords(String filePath) {
		wordsToIgnore = new ArrayList<>();
		parseInput(filePath, wordsToIgnore);
	}
	
	private void parseInput(String filePath, ArrayList<String> array) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String line = reader.readLine();
			while (line != null) {
				array.add(line);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.err.printf(ERROR_CANNOT_FIND_FILE, filePath);
			System.exit(1);
		} catch (IOException e) {
			System.err.printf(ERROR_CANNOT_READ_FILE, filePath);
			System.exit(2);
		}
	}
	
	private void displayOutput(ArrayList<String> output) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
			for (String line : output) {
				writer.write(line);
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.err.println(ERROR_CANNOT_WRITE_FILE);
			System.exit(3);
		}
	}
	
	public ArrayList<String> getKeyWordsInContext() {
		Collections.sort(wordsToIgnore);
		findAllKeywords();
		return keywordsInContext;
	}

	private void findAllKeywords() {
		// find all keywords in the give list of titles.
		for (int i = 0; i < titles.size(); i++) {
			findKeywordsInOneTitle(titles.get(i));
		}
	}

	private void findKeywordsInOneTitle(String title) {
		// find all keywords in one tile and store in output list.
		int index = 0;
		int length = title.split(" ").length;
		String rest;

		for (int i = 0; i < length; i++) {
			String first = title.split(" ")[0];
			rest = title.substring(title.indexOf(" ")).trim();
			index = Collections
					.binarySearch(wordsToIgnore, first.toLowerCase());
			if (index >= 0
					&& wordsToIgnore.get(index).equals(first.toLowerCase())) {
				// it is a word to be ignored
				title = rest + " " + first.toLowerCase(); 
			} else {
				// it is a keyword
				int pos = Collections.binarySearch(keywordsInContext, title);
				keywordsInContext.add(-pos - 1, title);
				title = rest + " " + first;
			}
		}
	}
}
