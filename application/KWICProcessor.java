import java.util.*;

class KWICProcessor {
	ArrayList<String> titles;
	ArrayList<String> wordsToIgnore;
	ArrayList<String> keywordsInContext;

	public KWICProcessor(String[] titles, String[] wordsToIgnore) {
		this.titles = new ArrayList<String>(Arrays.asList(titles));
		this.wordsToIgnore = new ArrayList<String>(Arrays.asList(wordsToIgnore));
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
                title = title.substring(0, 1).toUpperCase() + title.substring(1);
				keywordsInContext.add(title);
				title = circularShift(title, first);
			}
		}
	}

	private String circularShift(String title, String first) {
		String rest = title.substring(title.indexOf(" ")).trim();
		return rest + " " + first;
	}

	private void alphabetizeIndex(ArrayList<String> list) {
		Collections.sort(list);
	}
}
