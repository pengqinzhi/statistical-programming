//Qinzhi Peng, qinzhip
package exam3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Dictionary {
	public final static String DICTIONARY = "SampleDictionary.txt";
	List<Word> wordList = new ArrayList<>();
	Map<String, Word> singleMap = new HashMap<>();
	Map<String, List<Word>> multiMap = new HashMap<>();
	
	//DO NOT CHANGE MAIN METHOD
	public static void main(String[] args) {
		Dictionary dictionary = new Dictionary();
		Scanner input = new Scanner(System.in);
		dictionary.loadWordList();
		dictionary.loadSingleMap();
		dictionary.loadMultiMap();
		
		System.out.println("Enter search word");
		String searchWord = input.nextLine();
		
		System.out.println("------------WordList Search-----------");
		dictionary.searchWordList(searchWord);
		System.out.println("\n------------SingleMap Search-----------");
		dictionary.searchSingleMap(searchWord);
		System.out.println("\n------------MultiMap Search-----------");
		dictionary.searchMultiMap(searchWord);
		input.close();
	}
	
	//DO NOT CHANGE THIS METHOD
	/**loadWordList() reads the txt file. For each line, it invokes 
	 * getWord() method that returns a Word object. This object is then
	 * added to the arrayList wordList
	 */
	void loadWordList() {
		String wordString = null;
		try {
			Scanner input = new Scanner(new File(DICTIONARY));
			while (input.hasNextLine()) {
				wordString = input.nextLine();
				if (wordString.length() == 0) continue;
				wordList.add(getWord(wordString));
			}
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	//DO NOT CHANGE THIS METHOD
	/** getWord() is a helper method to extract a word and its meaning from 
	 * a line of text. 
	 * It takes a wordString and splits it on "(". The first
	 * element after split is the word, and rest are elements of its meaning. 
	 * So it uses first element to initialize 'word' of Word, and rest to 
	 * initialize 'meaning' of Word. As '(' may occur anywhere in the 
	 * 'meaning', the split string is put back together by putting
	 * '(' in front of each piece.  
	 * @param wordString
	 * @return
	 */
	Word getWord(String wordString) {
		String[] splits = wordString.split("\\(");  //split on (
		String word = null;
		StringBuilder wordMeaningString = new StringBuilder();
		if (splits[0].length() >0) 
			word = splits[0].trim();  //get the first string as it is the word
		for (int i = 1; i < splits.length; i++) {
			wordMeaningString.append("(" + splits[i]); //put back rest of the string together
		}
		return new Word(word, wordMeaningString.toString());
	}
	

	/** loadSingleMap() takes each word from
	 * wordList and loads it into singleMap with key being
	 * the Word's word in lowercase, and its value being the whole 
	 * Word object.
	 */
	void loadSingleMap() {
		for (Word w : wordList) {
			singleMap.put(w.word.toLowerCase(), w);
		}
	}

	/**loadMultiMap() takes each word from wordList and loads it 
	 * into multiMap with key being the Word's word in lowercase, and 
	 * its value being a list of all Word objects for that word. 
	 */
	void loadMultiMap() {
		List<Word> words;
		List<String> name;
		for (Word w : wordList) {
			words = new ArrayList<>();
			name = new ArrayList<>();

			if (! name.contains(w.word.toLowerCase())) {
				name.add(w.word.toLowerCase());
				for (Word w2 : wordList) {
					if (w2.word.equalsIgnoreCase(w.word)) {
						words.add(w2);
					}
				}
			}
			multiMap.put(w.word.toLowerCase(), words);
		}
	}

	/** searchWordList() takes a searchWord String and and searches for it in wordList.
	 * If found, it prints all its meanings. Else it prints 'Sorry! word not found!'
	 * It also returns a list of meanings, if found. Else it returns null.
	 * @param searchWord
	 */
	List<String> searchWordList(String searchWord) {
		List <String> meanings = new ArrayList<>();
		for (Word w : wordList) {
			if (w.word.equalsIgnoreCase(searchWord)) {
				meanings.add(w.meaning);
				System.out.println(w.word + " " + w.meaning);
			}	
		}
		
		if (meanings.isEmpty()) {
			System.out.println("Sorry! " + searchWord +" not found!");
			return null;
		}
		
		return meanings;
	}
	
	/** searchSingleMap() takes a searchWord String and searches for it in singleMap.
	 * If found, it prints its meaning. Else it prints 'Sorry! word not found!'
	 * It also returns the meaning string, if found, or else it returns null. 
	 * @param searchWord
	 */
	String searchSingleMap(String searchWord) {
		String meaning = null;
		for (Map.Entry<String, Word> entry : singleMap.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(searchWord)) {
				meaning = entry.getValue().meaning;
				System.out.println(entry.getValue().word + " " + meaning);
			}
		}
		
		if (meaning == null) {
			System.out.println("Sorry! " + searchWord +" not found!");
			return null;
		}
		
		return meaning;
	}
	
	/** searchMultiMap() takes a searchWord String and searches for it in multiMap. 
	 * If found, it prints all its meanings. Else it prints 'Sorry! word not found!'
	 * It also returns a list of meanings, if found. Else it returns null.
	 * @param searchWord
	 */
	List<String> searchMultiMap(String searchWord) {
		List <String> meanings = new ArrayList<>();
		
		for (Map.Entry<String, List<Word>> entry : multiMap.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(searchWord)) {
				for (Word w : entry.getValue()) {
					meanings.add(w.meaning);
					System.out.println(w.word + " " + w.meaning);
				}
			}
		}
		
		if (meanings.isEmpty()) {
			System.out.println("Sorry! " + searchWord +" not found!");
			return null;
		}
		
		return meanings;
	}
}
