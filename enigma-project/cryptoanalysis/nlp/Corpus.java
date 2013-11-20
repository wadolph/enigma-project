package nlp;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

// This class represents a corpus (word database) containing unigram, bigram, and trigram words.
// Counts of each type of gram are kept for future statistical use.
// PriorityQueues are also provided with sorts by word count.
// It is intended that all words are first loaded into HashMaps (fast lookup with counts),
// and then sortDatabase() is called to load priority queues with the entered words in descending word count order.
// Then each call to get(n-gram)TestQueue() returns a shallow copy of each queue for word processing.

public class Corpus {
	// Statistic databases.
	private Map<String, Integer> unigramTable;
	private Map<String, Integer> bigramTable;
	private Map<String, Integer> trigramTable;
	
	// Sorted databases.
	private PriorityQueue<String> unigramQueue;
	private PriorityQueue<String> bigramQueue;
	private PriorityQueue<String> trigramQueue;
	
	private int unigramCount;
	private int bigramCount;
	private int trigramCount;
	
	// Constructor.
	public Corpus() {
		unigramTable = new HashMap<String, Integer>();
		bigramTable = new HashMap<String, Integer>();
		trigramTable = new HashMap<String, Integer>();
		
		unigramQueue = new PriorityQueue<String>(11, new GramComparator(unigramTable));
		bigramQueue = new PriorityQueue<String>(11, new GramComparator(bigramTable));
		trigramQueue = new PriorityQueue<String>(11, new GramComparator(trigramTable));
	}
	
	public int getUnigramCount() {
		return unigramCount;
	}
	
	public int getBigramCount() {
		return bigramCount;
	}
	
	public int getTrigramCount() {
		return trigramCount;
	}
	
	// Returns shallow copy of test queue.
	public PriorityQueue<String> getUnigramTestQueue() {
		PriorityQueue<String> result = new PriorityQueue<String>(unigramTable.size(), new GramComparator(unigramTable));
		result.addAll(unigramTable.keySet());
		
		return result;
	}
	
	// Returns shallow copy of test queue.
	public PriorityQueue<String> getBigramTestQueue() {
		PriorityQueue<String> result = new PriorityQueue<String>(bigramTable.size(), new GramComparator(bigramTable));
		result.addAll(bigramTable.keySet());
		
		return result;
	}
	
	// Returns shallow copy of test queue.
	public PriorityQueue<String> getTrigramTestQueue() {
		PriorityQueue<String> result = new PriorityQueue<String>(trigramTable.size(), new GramComparator(trigramTable));
		result.addAll(trigramTable.keySet());
		
		return result;
	}
	
	public void addUnigram(String word) {
		if (unigramTable.containsKey(word)) {
			int count = unigramTable.get(word);
			unigramTable.put(word, count + 1);
		}
		else {
			unigramTable.put(word, 1);
		}
		
		unigramCount++;
	}
	
	public void addBigram(String phrase) {
		if (bigramTable.containsKey(phrase)) {
			int count = bigramTable.get(phrase);
			bigramTable.put(phrase, count + 1);
		}
		else {
			bigramTable.put(phrase, 1);
		}
		
		bigramCount++;
	}

	public void addTrigram(String phrase) {
		if (trigramTable.containsKey(phrase)) {
			int count = trigramTable.get(phrase);
			trigramTable.put(phrase, count + 1);
		}
		else {
			trigramTable.put(phrase, 1);
		}
		
		trigramCount++;
	}
	
	// This method is intended to be called after all words are sorted.
	public void sortDatabase() {
		unigramQueue.clear();
		bigramQueue.clear();
		trigramQueue.clear();
		
		for (String unigram : unigramTable.keySet()) {
			unigramQueue.add(unigram);
		}
		
		for (String bigram : bigramTable.keySet()) {
			bigramQueue.add(bigram);
		}
		
		for (String trigram : trigramTable.keySet()) {
			trigramQueue.add(trigram);
		}
	}
	
	// Test a series of words split by whitespace for unigram matches.
	public boolean testString(String test) {
		String[] words = test.split("\\s");
		
		for (int index = 0; index < words.length; index++) {
			if (!unigramTable.keySet().contains(words[index].toLowerCase()))
				return false;
		}
		
		return true;
	}
	
	// Debug method to show statistics.
	public String printTestStatistics() {
		String result = "";
		String[] buffer = new String[10];
		
		result = result + "\nTop Ten Unigrams:\n";
		
		for (int index = 0; index < 10; index++) {
			String word = unigramQueue.remove();
			buffer[index] = word;
			
			double percent = (double)(unigramTable.get(word)) / unigramCount * 100.0;
			
			result = result + "#" + (index + 1) + ": " + word + " - " + percent + "%\n";
		}
		
		for (int index = 0; index < 10; index++) {
			unigramQueue.add(buffer[index]);
		}
		
		result = result + "\nTop Ten Bigrams:\n";

		for (int index = 0; index < 10; index++) {
			String word = bigramQueue.remove();
			buffer[index] = word;
			
			double percent = (double)(bigramTable.get(word)) / bigramCount * 100.0;
			
			result = result + "#" + (index + 1) + ": " + word + " - " + percent + "%\n";
		}
		
		for (int index = 0; index < 10; index++) {
			bigramQueue.add(buffer[index]);
		}
		
		result = result + "\nTop Ten Trigrams:\n";
		
		for (int index = 0; index < 10; index++) {
			String word = trigramQueue.remove();
			buffer[index] = word;
			
			double percent = (double)(trigramTable.get(word)) / trigramCount * 100.0;
			
			result = result + "#" + (index + 1) + ": " + word + " - " + percent + "%\n";
		}
		
		for (int index = 0; index < 10; index++) {
			trigramQueue.add(buffer[index]);
		}
		
		return result;
	}
	
	// Inner class to sort database words in descending count order.
	public class GramComparator implements Comparator<String> {
		private Map<String, Integer> table;
		
		public GramComparator(Map<String, Integer> database) {
			table = database;
		}
		
		public int compare(String arg0, String arg1) {
			if (table.get(arg0) < table.get(arg1))
				return 1;
			else if (table.get(arg0) > table.get(arg1))
				return -1; 
			else
				return 0;
		}
	}
}