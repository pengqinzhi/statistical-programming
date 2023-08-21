//Qinzhi Peng; qinzhip
package lab4d;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class EnglishQuiz extends Quiz {
	
	String wordStrings[];
	int questionindex;
	static int score;
	static int count;
	
	
	EnglishQuiz (String filename) {
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
		wordStrings = fileContent.toString().split("\n");
		fileScanner.close();
	}
	
	@Override
	void createQuestion() {
		Random rand = new Random();
		questionindex = rand.nextInt(wordStrings.length);
		questionString = wordStrings[questionindex].split(":")[1];

	}

	@Override
	void createAnswer() {
		answerString = wordStrings[questionindex].split(":")[0];

	}

	@Override
	boolean checkAnswer(String answer) {
		if (answer.equalsIgnoreCase(answerString)) {
			Quiz.score ++;
			Quiz.count ++;
			EnglishQuiz.score ++;
			EnglishQuiz.count ++;
			return true;
		}
		Quiz.count ++;
		EnglishQuiz.count ++;
		return false;
		}
		
	

}
