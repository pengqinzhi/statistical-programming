package exam2a;

import java.util.Random;

public abstract class WordGame {

	String puzzleWord; // holds the answer to the puzzle
	String clue; // clue displayed to the player
	boolean isGameOver = false; // will be set to true when user solves the puzzle

	/**
	 * WordGame() constructor that initializes puzzleWord by finding a word randomly
	 * from the words[] array. It also initializes clue with as many dashes as the
	 * length of the puzzleWord.
	 * 
	 * @param words
	 */
	WordGame(String[] words) {
		StringBuilder clueContent = new StringBuilder();
		Random rand = new Random();
		int wordIndex = rand.nextInt(words.length);
		puzzleWord = words[wordIndex];

		for (int i = 0; i < puzzleWord.length(); i++) {
			clueContent.append("-");
		}
		clue = clueContent.toString();

	}

	/**
	 * getNextClue() takes current clue and the next guess made by player. If the
	 * guess exists in puzzleWord, it replaces the dashes in clue with guessed
	 * character at correct positions as in puzzleWord. For example, if puzzleWord
	 * is 'java', the clue is '----', and guess is 'a', then the method should
	 * return '-a-a'
	 * 
	 * It there are no more dashes in the clue, the isGameOver flag is set to true.
	 * 
	 * @param clue
	 * @param guess
	 * @return
	 */
	String getNextClue(String clue, char guess) {
		if (puzzleWord.contains(String.valueOf(guess))) {
			int index = clue.indexOf(guess);
			StringBuilder clueBuilder = null;

			while (index != -1) {	
				clueBuilder = new StringBuilder(clue);
				clueBuilder.setCharAt(index, guess);
				clue = clueBuilder.toString();
				index = clue.indexOf(guess, index + 1);
			}
		}
		
		if (clue.equals(puzzleWord)) {
			isGameOver = true;
		}

		return clue;
	}

	abstract void play(); // to be implemented by child classes

}
