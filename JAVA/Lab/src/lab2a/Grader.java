package lab2a;

import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Grader {

	String[] fileData; // stores each row from file as an array element
	String[] courses; // will have all course codes
	String[][] courseGrades; // each i'th row will have grades for course in courses[i]
	String[] gradeLetters; // will have all possible grade letters across all courses
	int[][] gradeTable; // gradeTable[i][j] will have count of gradeLetters[j] in courses[i]

	Scanner input;

	// do not change this method
	public static void main(String[] args) {
		Grader grader = new Grader();

		grader.loadData("Grades.txt");
		grader.printData();

		grader.loadGrades();
		grader.printGrades();

		grader.loadGradeLetters();
		grader.printGradeLetters();

		grader.buildGradeTable();
		grader.printGradeTable();
	}

	/**
	 * loadData() takes filename as input, reads its content, and loads each row as
	 * a string in fileData array
	 * 
	 * @param filename
	 */
	void loadData(String filename) {
		// write your code here
		StringBuilder fileContent = new StringBuilder();
		File file = new File(filename);
		Scanner fileScanner = null;
		try {
			fileScanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (fileScanner.useDelimiter(",").hasNext()) {
			fileContent.append(fileScanner.next() + ",");
		}
		fileData = fileContent.toString().split("\n");
	}

	/**
	 * loadGrades() reads data from fileData[] array populates courses[] and
	 * courseGrades[][] arrays. Each row in courses[] and courseGrades[][]
	 * represents one course Note that each row in courseGrades[][] has a different
	 * number of elements
	 */
	void loadGrades() {
		// write your code here

		courses = new String[fileData.length];
		courseGrades = new String[fileData.length][];
		for (int i = 0; i < fileData.length; i++) {
			String[] line = fileData[i].replaceAll("\\s+", "").split(",");
			courses[i] = line[0];
			// System.out.println(courses[i]);
			courseGrades[i] = new String[line.length - 1];
			for (int j = 0; j < line.length - 1; j++) {
				courseGrades[i][j] = line[j + 1];
			}
		}

	}

	/**
	 * loadGradeLetters() populates the gradeLetters[] array by scanning the data in
	 * courseGrades[][]. Each grade letter must appear only once in gradeLetters[]
	 */
	void loadGradeLetters() {
		// write your code here
		StringBuilder uniqueletter = new StringBuilder();
		for (int i = 0; i < courses.length; i++) {
			for (int j = 0; j < courseGrades[i].length; j++) {
				if (!uniqueletter.toString().contains(courseGrades[i][j])) {
					uniqueletter.append(courseGrades[i][j] + ",");
					// System.out.println(uniqueletter.toString());
				}
			}
		}
		gradeLetters = uniqueletter.toString().split(",");
	}

	/***
	 * buildGradeTable() scans the data in courseGrades[][] and populates
	 * gradeTable[][]. Each row in grdeTable represents one course and each column
	 * represents a grade letter. Each cell contains the number of students who
	 * scored that grade in that course
	 */
	void buildGradeTable() {
		// write your code here
		gradeTable = new int[courseGrades.length][gradeLetters.length];
		for (int i = 0; i < courseGrades.length; i++) {
			for (int j = 0; j < courseGrades[i].length; j++) {
				for (int k = 0; k < gradeTable.length; k++) {
					if (courseGrades[i][j].equals(gradeLetters[k]))
						gradeTable[i][k] += 1;
				}
			}

		}
	}

	/************************ print methods ********************************/
	/**
	 * printData() prints all the rows as shown in the handout under the heading ***
	 * File data ***
	 */
	void printData() {
		System.out.println("*** File data ***");
		for (int i = 0; i < fileData.length; i++) {
			System.out.println(fileData[i]);
		}
		System.out.println();
	}

	/**
	 * printGrades() prints data from courses[] and courseGrades[][] as shown in the
	 * handout under the heading *** Course grades ***
	 */
	void printGrades() {
		System.out.println("*** Course grades ***");
		for (int i = 0; i < courses.length; i++) {
			System.out.print(courses[i] + " ");
			for (int j = 0; j < courseGrades[i].length; j++) {
				System.out.print(courseGrades[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * printGradeLetters prints letter grades stored in gradeLetters as shown in the
	 * handout under the heading *** Grade letters ***
	 */
	void printGradeLetters() {
		System.out.println("*** Grade letters ***");
		System.out.println(Arrays.toString(gradeLetters));
		System.out.println();
	}

	/**
	 * printGradeTable() prints the gradeTable as shown in the handout under the
	 * heading *** Grade Table ***
	 */
	void printGradeTable() {
		System.out.println("*** Grade Table ***");
		for (int i = 0; i < gradeLetters.length; i++) {
			System.out.print("\t" + gradeLetters[i]);
		}
		System.out.println();
		for (int i = 0; i < courses.length; i++) {
			System.out.print(courses[i] + "\t");
			for (int j = 0; j < gradeTable[i].length; j++) {
				System.out.print(gradeTable[i][j] + "\t");
			}
			System.out.println();
		}
	}
}
