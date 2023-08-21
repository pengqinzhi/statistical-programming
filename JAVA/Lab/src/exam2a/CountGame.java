package exam2a;

import java.util.Scanner;
import java.util.Arrays;

public class CountGame extends WordGame {
	static final int MAX_COUNT = 10;

	public CountGame(String[] words) {
		super(words);
	}

	@Override
	void play() {
		for (int count = 0; count < MAX_COUNT; count++) {
			Scanner input = new Scanner(System.in);
			String guessString = input.nextLine();
			char guess = guessString.charAt(0);
			clue = getNextClue(clue, guess);

			char[] clueArray = clue.toCharArray();
			System.out.println(count + 1 + ". " + Arrays.toString(clueArray));
			input.close();

			if (clue.equals(puzzleWord)) {
				isGameOver = true;
			}

			if (isGameOver == true) {
				System.out.println("Great! You got it right in " + count + 1 + " guesses");
				break;
			}
		}
	}

}
