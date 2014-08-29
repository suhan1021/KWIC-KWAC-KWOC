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