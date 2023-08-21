//Qinzhi Peng, qinzhip
package hw2;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class TSVCaseReader extends CaseReader {

	TSVCaseReader(String filename) {
		super(filename);
	}

	/**readCases uses TSVParser library to parse data file */
	List<Case> readCases() {
		List<Case> caseList = new ArrayList<>();
		String[] List = null;
		File file = new File(filename);
		Scanner fileScanner = null;
		
		try {																//Create a Scanner to Read file
			fileScanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while (fileScanner.useDelimiter("\n").hasNext()) {	                //Store the content from fileScanner into the fileContent
			List = fileScanner.nextLine().split("\t");
			Case c = new Case(List[0].trim(), List[1].trim(), List[2].trim(), List[3].trim(), List[4].trim(), List[5].trim(), List[6].trim());
			caseList.add(c);
		}

		return caseList;
	}
}