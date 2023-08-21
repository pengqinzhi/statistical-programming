//Qinzhi Peng; qinzhip

package hw1;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class CCModel {
	Case[] cases;
	String[] fileData;

	/**loadData() takes filename as a parameter,
	 * reads the file and loads all 
	 * data as a String for each row in 
	 * fileData[] array
	 * @param filename
	 */
	void loadData(String filename) {
		String[] oriData;
		StringBuilder fileContent = new StringBuilder();
		File file = new File(filename);
		Scanner fileScanner = null;
		try {																//Create a Scanner to Read file
			fileScanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (fileScanner.useDelimiter("\t").hasNext()) {				//Store the content from fileScanner into the fileContent
			fileContent.append(fileScanner.next() + "---");
		}

		oriData = fileContent.toString().trim().split("\n");
		fileData = new String[oriData.length - 1];
		for (int i = 0; i < fileData.length; i++) {                        //Delete the last blank row
			fileData[i] = oriData[i].trim();
		}
		fileScanner.close();
	}

	/**loadCases() uses the data stored in fileData array
	 * and creates Case objects for each row.
	 * These cases are loaded into the cases array.
	 * Note that you may have to traverse the fileData array twice
	 * to be able to initialize the cases array's size.
	 */
	void loadCases() {
		String caseDate; 
		String caseTitle;
		String caseType;
		String caseNumber;
		cases = new Case[fileData.length];

		for (int i = 0; i < fileData.length; i++) {
			String[] dataRow = fileData[i].trim().split("---");
			caseDate = dataRow[0].trim();									//Assign the caseDate

			if (dataRow[1].contains("(Federal)")) {							//Assign the caseTitle and caseType, and classify them by regex
				caseTitle = dataRow[1].replaceAll("\\(Federal\\)", "").trim();
				caseType = "Federal";
			} else if (dataRow[1].contains("(Administrative)")) {
				caseTitle = dataRow[1].replaceAll("\\(Administrative\\)", "").trim();
				caseType = "Administrative";
			} else {
				caseTitle = dataRow[1].trim();
				caseType = null;
			}

			if (dataRow.length != 2) {										//Assign the caseNumber	
				caseNumber = dataRow[2].trim();
			} else {
				caseNumber = null;
			}

			Case newCase = new Case(caseDate, caseTitle, caseType, caseNumber);		//Create a new case object
			cases[i] = newCase;														//Store the case object in the cases array
		}
	}

}
