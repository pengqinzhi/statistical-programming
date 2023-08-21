package lab2b;

import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Friendly {

	String[] persons;
	String[][] personFriends;

	public static void main(String[] args) {
		Friendly friendly = new Friendly();
		friendly.readFriends("friends.txt");
		friendly.getInputOutput();
	}

	// do not change this method
	void getInputOutput() {
		int choice = 0;
		Scanner input = new Scanner(System.in);
		do {
			System.out.println("*** Welcome to Friendly! ***");
			System.out.println("1. Find the number of friends a person has");
			System.out.println("2. Find the number of common friends between two persons");
			System.out.println("3. Find the names of common friends between two persons");
			System.out.println("4. Exit");
			choice = input.nextInt();
			input.nextLine(); // clear the buffer
			switch (choice) {
			case 1: {
				System.out.println("Enter the person's name");
				String name = input.nextLine();
				String[] friends = findFriends(name);
				if (friends != null) {
					System.out.printf("%s has %d friends%n", name, friends.length);
					int count = 0;
					for (String s : friends) {
						System.out.println(++count + ". " + s);
					}
				} else
					System.out.println("Sorry! No friends found!");
				System.out.println("-----------------------------");
				break;
			}
			case 2: {
				System.out.println("Enter first person's name");
				String name1 = input.nextLine();
				System.out.println("Enter second person's name");
				String name2 = input.nextLine();
				System.out.printf("%s and %s have %d common friends%n", name1, name2, countCommonFriends(name1, name2));
				System.out.println("-----------------------------");
				break;
			}
			case 3: {
				System.out.println("Enter first person's name");
				String name1 = input.nextLine();
				System.out.println("Enter second person's name");
				String name2 = input.nextLine();
				String[] commonFriends = findCommonFriends(name1, name2);
				if (commonFriends != null) {
					System.out.printf("%s and %s have %d common friends%n", name1, name2, commonFriends.length);
					int count = 0;
					for (String s : commonFriends) {
						System.out.println(++count + ". " + s);
					}
				} else
					System.out.println("Sorry! No match found!");
				System.out.println("-----------------------------");
				break;
			}
			default:
				System.out.println("Goodbye!");
				break;
			}
		} while (choice != 4);
		input.close();
	}

	/**
	 * readFriends() reads the file with filename to populate persons and
	 * personFriends arrays
	 */
	void readFriends(String filename) {
		// write your code here
		StringBuilder fileContent = new StringBuilder();
		File file = new File(filename);
		Scanner fileScanner = null;
		try {
			fileScanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (fileScanner.useDelimiter("[,:]").hasNext())
			fileContent.append(fileScanner.next() + ",");
		String[] data = fileContent.toString().split("\n");
		// System.out.println(data.length);
		persons = new String[data.length];
		personFriends = new String[data.length][];
		for (int i = 0; i < data.length; i++) {
			String[] line = data[i].replaceAll("\\s+", "").split(",");
			// System.out.println(Arrays.toString(line));
			persons[i] = line[0];
			personFriends[i] = new String[line.length - 1];
			for (int j = 0; j < line.length - 1; j++) {
				personFriends[i][j] = line[j + 1];
				// System.out.println(personFriends[i][j]);
			}

		}

	}

	/**
	 * given a name, returns an array of friends a person has If the name is not
	 * found, it returns null
	 */
	String[] findFriends(String name) {
		// write your code here

		for (int i = 0; i < persons.length; i++) {
			if (persons.toString().contains(name))
				return personFriends[i];
		}

		return null;
	}

	/** given two names, returns how many common friends they have */
	int countCommonFriends(String name1, String name2) {
		// write your code here
		return 0;
	}

	/**
	 * given two names, returns an array of names of common friends. If there are no
	 * common friends, then it returns an empty array, i.e. array of size 0
	 */
	String[] findCommonFriends(String name1, String name2) {
		// write your code here
		return null;
	}

}
