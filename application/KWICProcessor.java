package application;

import java.util.ArrayList;
import java.util.Collections;

class KWICProcessor {
	ArrayList<String> titles;
	ArrayList<String> wordsToIgnore;
	ArrayList<String> keywordsInContext;

	public KWICProcessor(ArrayList<String> titles, ArrayList<String> wordsToIgnore) {
		this.titles = titles;
		this.wordsToIgnore = wordsToIgnore;
		keywordsInContext = new ArrayList<String>();
	}

	public ArrayList<String> getKeyWordsInContext() {
		findAllKeywords();
		alphabetizeIndex(keywordsInContext);
		return keywordsInContext;
	}

	private void findAllKeywords() {
		for (int i = 0; i < titles.size(); i++) {
			findKeywordsInOneTitle(titles.get(i));
		}
	}

	private void findKeywordsInOneTitle(String title) {
		int length = title.split(" ").length;
		for (int i = 0; i < length; i++) {
			String first = title.split(" ")[0];
			int index = wordsToIgnore.indexOf(first.toLowerCase());
			if (index >= 0) {
				// it is a word to be ignored
				title = circularShift(title, first.toLowerCase());
			} else {
				// it is a keyword
				keywordsInContext.add(Character.toUpperCase(title.charAt(0)) + title.substring(1));
				title = circularShift(title, first);
			}
			System.out.println(title);
		}
	}

	private String circularShift(String title, String first) {
		//title contains only one word
		if (title.indexOf(" ") == -1) {
			return title;
		} else {
			String rest = title.substring(title.indexOf(" ")).trim();
			return rest + " " + first;
		}
	}

	private void alphabetizeIndex(ArrayList<String> list) {
		System.out.println("running");
		Collections.sort(list);
		System.out.println("sort finished");
	}
}