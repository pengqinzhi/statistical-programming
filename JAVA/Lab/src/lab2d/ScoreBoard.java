// Qinzhi Peng qinzhip
package lab2d;

import java.util.Scanner;
import java.io.*;

public class ScoreBoard {
	StringBuilder fileContent = new StringBuilder();
	int[] scoreSums;
	double[] scoreAverages;
	int grandTotal;
	double grandAverage;

	// DO NOT not change this method
	/**
	 * initiates the program and runs all other methods in a sequence
	 **/
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		ScoreBoard sb = new ScoreBoard();
		sb.readFile("Scores.txt");
		sb.computeScores();
		sb.printReport();
		input.close();
	}

	// complete this method
	/**
	 * readFile() method reads the file data into fileContent. It preserves the
	 * line-breaks.
	 * 
	 * @param fileName
	 */
	public void readFile(String fileName) {
		File file = new File(fileName);	
		Scanner fileScanner = null;   //??
		try {
			fileScanner = new Scanner(file);
			while (fileScanner.hasNextLine()) {
				fileContent.append(fileScanner.nextLine() + "\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		fileScanner.close();

	}

	// complete this method
	/**
	 * computeScores() takes the fileContent and calculates four things 1. For each
	 * player, the total score in scoreSums array 2. For each player, the average
	 * score in scoreAverages array 3. The grand total of all scores in grandTotal
	 * 4. The grand average of all scores in grandAverage
	 */
	public void computeScores() {
		String[] number_arr = fileContent.toString().trim().split("\n");
		String[][] number = new String[number_arr.length][];
		scoreSums = new int[number_arr.length];
		scoreAverages = new double[number_arr.length];
		for (int i = 0; i < number_arr.length; i++) {
			number[i] = number_arr[i].split(",");
		}
		for (int i = 0; i < number_arr.length; i++) {
			for (int j = 0; j < number[i].length; j++) {
				scoreSums[i] += Integer.parseInt(number[i][j]);
			}
			grandTotal += scoreSums[i];
			scoreAverages[i] = (double) scoreSums[i] / number[i].length;

		}
		grandAverage += (double) grandTotal / scoreSums.length;

	}

	// complete this method
	/**
	 * printReport() prints the output as shown in the problem statement
	 */
	public void printReport() {
		// write your code here
		for (int i = 0; i < scoreSums.length; i++) {
			System.out.printf("Player %d. Total score = %d Average score = %.2f\n", i + 1, scoreSums[i],
					scoreAverages[i]);
		}
		System.out.printf("Grand total score: %d. Grand average score: %.2f", grandTotal, grandAverage);
	}
}
