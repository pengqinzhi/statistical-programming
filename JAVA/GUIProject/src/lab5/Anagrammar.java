// Qinzhi Peng; qinzhip

package lab5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;

public class Anagrammar {
	String[] words; // stores all words read from words.txt
	boolean isInDictionary; // true if the clue word exists in words.txt
	boolean hasAnagrams; // true if the clue word has anagrams
	String[] anagramArray; // stores all anagrams of clue-word, if found

	/**
	 * loadWords method reads the file and loads all words into the words[] array
	 */
	void loadWords(String filename) {
		StringBuilder fileContent = new StringBuilder();
		Scanner fileScanner = null;
		File file = new File(filename);
		try {
			fileScanner = new Scanner(file);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (fileScanner.useDelimiter("\n").hasNext()) {
			fileContent.append(fileScanner.nextLine() + "\n");
		}
		words = fileContent.toString().split("\n");
		fileScanner.close();
	}

	/**
	 * findAnagrams method traverses the words array and looks for anagrams of the
	 * clue. While doing so, if the clue-word itself is found in the words array, it
	 * sets the isInDictionary to true. If it finds any anagram of the clue, it sets
	 * the hasAnagram to true. It loads the anagram into the anagramArray. If no
	 * anagrams found, then anagramArray remains an array with size 0.
	 */
	void findAnagrams(String clue) {
		isInDictionary = false;
		hasAnagrams = false;
		StringBuilder anagramContent = new StringBuilder();
		anagramArray = new String[0];
		
		for (String word : words) {
			if (clue.equalsIgnoreCase(word)) {
				isInDictionary = true;
			}

			char[] clueArray = clue.toCharArray();
			char[] wordArray = word.toCharArray();
			Arrays.sort(clueArray);
			Arrays.sort(wordArray);
			String sortedClue = new String(clueArray);
			String sortedWord = new String(wordArray);

			if (sortedClue.equalsIgnoreCase(sortedWord) && !clue.equalsIgnoreCase(word)) {
				hasAnagrams = true;
				anagramContent.append(word + "\t");
			}
		}

		if (anagramContent.length() > 0) {
			anagramArray = anagramContent.toString().split("\t");
		}

	}

}
