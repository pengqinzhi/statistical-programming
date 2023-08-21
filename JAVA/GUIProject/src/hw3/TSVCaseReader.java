//Qinzhi Peng, qinzhip
package hw3;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

import mortgagecalculator.InputOutOfRangeException;

import java.util.ArrayList;
import java.util.List;

public class TSVCaseReader extends CaseReader {

	TSVCaseReader(String filename) {
		super(filename);
	}

	/** readCases uses TSVParser library to parse data file */
	List<Case> readCases() {
		List<Case> caseList = new ArrayList<>();
		String[] List = null;
		int errorNum = 0;

		try (Scanner fileScanner = new Scanner(new File(filename))) { // Create a Scanner to Read file
			while (fileScanner.useDelimiter("\n").hasNext()) { // Store the content from fileScanner into the
																// fileContent
				List = fileScanner.nextLine().split("\t");
				String caseDate = List[0].trim();
				String caseTitle = List[1].trim();
				String caseType = List[2].trim();
				String caseNumber = List[3].trim();
				String caseLink = List[4].trim();
				String caseCategory = List[5].trim();
				String caseNotes = List[6].trim();

				if (caseDate.equals("") || caseTitle.equals("") || caseType.equals("") || caseNumber.equals("")) {
					errorNum++;
				} else {
					Case c = new Case(caseDate, caseTitle, caseType, caseNumber, caseLink, caseCategory, caseNotes);
					caseList.add(c);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			if (errorNum != 0)
				throw new DataException(errorNum);
		} catch (DataException e) {
			System.out.println("Data Error!");
		}

		return caseList;
	}
}