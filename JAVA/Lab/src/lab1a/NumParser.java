//Enter your name and Andrew ID here

package lab1a;

import java.util.Scanner;

public class NumParser {
	double sum, max, min; // to store the results to be printed
	int count; // to count the valid numbers parsed

	/** DO NOT change the main method **/
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the numbers separated by a space or a comma");
		String numbers = input.nextLine();
		NumParser np = new NumParser();
		np.parseCalculate(numbers);
		np.printResults();
		input.close();
	}

	/**
	 * The parseCalculate method takes a string as input parameter and parses out
	 * the valid numbers from it. While parsing, it also calculates sum, max, min,
	 * and count. If it finds any invalid token, it discards it and prints it out as
	 * a discarded token
	 * 
	 * @param numbers
	 */
	void parseCalculate(String numbers) {
		String[] numStr = numbers.split("[\\s,]+");
		int flag = 0;

		for (int i = 0; i < numStr.length; i++) {
			if (!numStr[i].matches("[+-]?[0-9]+[.]?[0-9]*")) {
				System.out.println("Discarded token: " + numStr[i]);
			} else {
				double num = Double.parseDouble(numStr[i]);
				if (flag == 0) {
					max = num;
					min = num;
					flag = 1;
				}

				if (num > max)
					max = num;
				if (num < min)
					min = num;
				sum += num;
				count++;
			}
		}
	}

	/**
	 * The printResult method prints the output as shown in the lab-handout
	 */
	private void printResults() {
		System.out.println("Sum is " + sum);
	}
}
