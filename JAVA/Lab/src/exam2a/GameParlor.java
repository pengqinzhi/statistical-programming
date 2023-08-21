package exam2a;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

public class GameParlor {

	WordGame wordGame; // this must hold the instance of WordGame to be played (CountGame or TimeGame).
	String[] words; // will contain words read from the data file
	String filename = "miniwords.txt"; // word file to be used for the sames

	Scanner input = new Scanner(System.in);

	// do not change this method
	public static void main(String[] args) {
		GameParlor game = new GameParlor();
		game.words = game.readWords(game.filename);
		game.runGame();
	}

	/** readWords() reads words from filename and loads into words[] array */
	String[] readWords(String filename) {
		Scanner fileScanner = null;
		File file = new File(filename);
		StringBuilder fileContent = new StringBuilder();
		
		try {
			fileScanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (fileScanner.hasNext()) {
			fileContent.append(fileScanner.next() + ",");
		}
		
		words = fileContent.toString().split(",");
		fileScanner.close();
		
		return words;
	}

	/**
	 * runGame() prints menu options, takes user choice, initializes wordGame with
	 * appropriate WordGame instance, and then invokes play() on it polymorphically.
	 */
	void runGame() {
		String choice = input.nextLine();
		
		if (choice.equals("1")) {
			wordGame = new CountGame(words);
			wordGame.play();
		} else if (choice.equals("2")) {
			wordGame = new TimeGame(words);
			wordGame.play();
		}
		
	}
}
