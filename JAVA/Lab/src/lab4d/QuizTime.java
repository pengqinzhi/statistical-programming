//Qinzhi Peng; qinzhip
package lab4d;

import java.util.Scanner;

public class QuizTime {

	Quiz[] quizzes = { new MathQuiz(), new EnglishQuiz("EnglishQuizWords.txt") }; // Your program must use these two
																					// objects. No new quiz objects to
																					// be created.
	Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		QuizTime quizTime = new QuizTime();
		quizTime.startQuiz();

	}

	// do not change this method
	void startQuiz() {
		int choice = 0;
		while (true) {
			System.out.println("*** Welcome to QuizTime!***");
			System.out.println("1. Math Quiz");
			System.out.println("2. English Quiz");
			System.out.println("3. Exit");
			System.out.println("Enter choice: ");
			choice = input.nextInt();
			if (choice == 1 || choice == 2) {
				runQuiz(choice);
			} else {
				System.out.println("Your Math score: " + MathQuiz.score + "/" + MathQuiz.count);
				System.out.println("Your English score: " + EnglishQuiz.score + "/" + EnglishQuiz.count);
				System.out.println("Your total score: " + Quiz.score + "/" + (MathQuiz.count + EnglishQuiz.count));
				break;
			}
		}
	}

	/**
	 * runQuiz() uses choice to index into quizzes array, and then invokes
	 * createQuestion() and createAnswer() methods. It then prints the question,
	 * takes user input, invokes checkAnswer() to check answer and then prints
	 * whether the answer is Correct or Incorrect.
	 * 
	 * @param choice
	 */
	void runQuiz(int choice) {
		String answer;
		boolean flag;
		if (choice == 1) {
			((MathQuiz) quizzes[0]).createQuestion();
			((MathQuiz) quizzes[0]).createAnswer();
			System.out.printf("What is: %s\n", quizzes[0].questionString);
			input.nextLine();
			answer = input.nextLine();
			flag = ((MathQuiz) quizzes[0]).checkAnswer(answer);
			if (flag == true) {
				System.out.println("Correct!");
			} else {
				System.out.println("Incorrect!");
			}
			System.out.println("-------------------------------");
		} else

		if (choice == 2) {
			((EnglishQuiz) quizzes[1]).createQuestion();
			((EnglishQuiz) quizzes[1]).createAnswer();
			System.out.printf("What is: %s\n", quizzes[1].questionString);
			input.nextLine();
			answer = input.nextLine();
			flag = ((EnglishQuiz) quizzes[1]).checkAnswer(answer);
			if (flag == true) {
				System.out.println("Correct!");

			} else {
				System.out.println("Incorrect!");
			}
			System.out.println("-------------------------------");
		}

	}
}
