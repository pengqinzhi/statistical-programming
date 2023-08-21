package lab4a;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class University {
	String[] rosterStrings;
	public Student[] students;

	//DO NOT CHANGE THIS METHOD
	public void loadRosterStrings() {
		Scanner fileContent = null;
		StringBuilder rosterData = new StringBuilder();
		try {
			fileContent = new Scanner (new File("Roster.csv"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (fileContent.hasNextLine()) {
			rosterData.append(fileContent.nextLine() + "\n");
		}
		rosterStrings = rosterData.toString().split("\n"); 
	}

	/**loadStudents() method takes rosterStrings data and creates a new Student object
	 * from data in each string. It uses 'Section' data to decide which type of 
	 * Student to create. For example, if Section is 'A', then it creates StudentA object. 
	 * It initializes instance variables of Student object from rosterString data after splitting
	 * each String on ',' as delimiter. 
	 * Hint: Use charAt(index) method to get Section name
	 */

	public void loadStudents(){
		students = new Student[rosterStrings.length];
		
		for (int i = 0; i < rosterStrings.length; i++) {
			String[] studentInfo = rosterStrings[i].split(",");
			char section = studentInfo[0].charAt(0);
			String firstName = studentInfo[1];
			String lastName = studentInfo[2];
			double income = Double.valueOf(studentInfo[3]);
			switch  (section) {
			case 'A' : 
				students[i] = new StudentA(section, firstName, lastName, income);
				break;
			case 'B' :
				students[i] = new StudentB(section, firstName, lastName, income);
				break;
			case 'C' :
				students[i] = new StudentC(section, firstName, lastName, income);
				break;
			}
		}	
	}
	
}
