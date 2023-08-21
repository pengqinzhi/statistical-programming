//Qinzhi Peng; qinzhip
package lab4d;
import java.util.Random;

public class MathQuiz extends Quiz {
	static int score;
	static int count;
	int operand1;
	int operand2;
	char operators;
	
	@Override
	void createQuestion() {
		int index;
		Random rand = new Random();
		operand1 = rand.nextInt(10);
		operand2 = rand.nextInt(10);
		index = rand.nextInt(3);
		switch (index) {
		case 0:
			operators = '+';
			break;
		case 1:
			operators = '-';
			break;
		case 2:
			operators = '*';
			break;
		}
		
		questionString = "" + operand1 + operators + operand2;
		
		
	}

	@Override
	void createAnswer() {
		int answer = 0;
		
		switch (operators) {
		case '+':
			answer =operand1 + operand2;
			break;
		case '-':
			answer = operand1 - operand2;
			break;
		case '*':
			answer = operand1 * operand2;
			break;
		}
		answerString = Integer.toString(answer);

	}

	@Override
	boolean checkAnswer(String answer) {
		if (answer.equals(answerString)) {
			Quiz.score ++;
			Quiz.count ++;
			MathQuiz.score ++;
			MathQuiz.count ++;
			return true;
		}
		Quiz.count ++;
		MathQuiz.count ++;
		return false;
	}

}
