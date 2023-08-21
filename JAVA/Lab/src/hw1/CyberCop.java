//Qinzhi Peng; qinzhip

package hw1;

import java.util.Scanner;

public class CyberCop {

	public static final String DATAFILE = "data/FTC-cases-TSV.txt";
	CCModel ccModel = new CCModel();
	SearchEngine searchEngine = new SearchEngine();

	Scanner input = new Scanner(System.in);

	/**main() instantiates CyberCop and then invokes dataManager's loadData
	 * and loadCases() methods
	 * It then invokes showMenu to get user input
	 * @param args
	 */
	//Do not change this method
	public static void main(String[] args) {
		CyberCop cyberCop = new CyberCop();
		cyberCop.ccModel.loadData(DATAFILE);
		cyberCop.ccModel.loadCases();
		cyberCop.showMenu();
	}

	/**showMenu() shows the menu. 
	 * Based on the user choice, it invokes one of the methods:
	 * printSearchResults(), printCaseTypeSummary(), or printYearwiseSummary()
	 * The program exits when user selects Exit option. 
	 * See the hand-out for the expected layout of menu-UI
	 */
	void showMenu() {
		System.out.println("*** Welcome to CyberCop! ***");
		System.out.println("1. Search for cases reported against a company");
		System.out.println("2. Search for cases reported in a year");
		System.out.println("3. Search for a case number");
		System.out.println("4. Print case type summary");
		System.out.println("5. Print year-wise summary");
		System.out.println("6. Exit");

		String choose = input.nextLine().trim();
		System.out.println("--------------------------------------------------------------------------------------------------");
		switch (choose) {										//Invoke methods based on the user choice
		case "1" : 
			System.out.println("Enter search string");
			String searchString1 = input.nextLine().trim();	
			printSearchResults(searchString1, searchEngine.searchTitle(searchString1, ccModel.cases));		//Print the result after implementing the methond in the searchEngine object
			break;
		case "2" : 
			System.out.println("Enter search year as YYYY");
			String searchString2 = input.nextLine().trim();
			printSearchResults(searchString2, searchEngine.searchYear(searchString2, ccModel.cases));
			break;
		case "3" :
			System.out.println("Enter case number");
			String searchString3 = input.nextLine().trim();
			printSearchResults(searchString3, searchEngine.searchCaseNumber(searchString3, ccModel.cases));
			break;
		case "4" :
			printCaseTypeSummary();
			break;
		case "5" :
			printYearwiseSummary();
			break;
		case "6" :
			System.exit(0);
			break;
		}
	}

	/**printSearchResults() takes the searchString and array of cases as input
	 * and prints them out as per the format provided in the handout
	 * @param searchString
	 * @param cases
	 */
	void printSearchResults(String searchString, Case[] cases) {
		System.out.println("--------------------------------------------------------------------------------------------------");
		if (cases == null) {
			System.out.println("Sorry, no search results found for " + searchString);
		} else {
			System.out.println(cases.length + " case(s) found for " + searchString);
			System.out.println("--------------------------------------------------------------------------------------------------");
			System.out.printf("%-104s %-22s %s\n", " #. Last update Case Title", "Case Type", "Case/File Number");
			System.out.println("--------------------------------------------------------------------------------------------------");

			for (int i = 0; i < cases.length; i++) {							//Print output in format, if return is null, then print blank
				if (cases[i].caseTitle.length() > 88) {							//If the caseTitle is too long, then cut it
					cases[i].caseTitle = cases[i].caseTitle.substring(0, 85) + "...";
				}
				if (cases[i].caseType == null && cases[i].caseNumber == null) { 
					System.out.printf("%-3s %s  %-88s %-30s %s\n", Integer.toString(i+1)+".", cases[i].caseDate, cases[i].caseTitle, "", "");
				} else if (cases[i].caseType == null) {
					System.out.printf("%-3s %s  %-88s %-30s %s\n", Integer.toString(i+1)+".", cases[i].caseDate, cases[i].caseTitle, "", cases[i].caseNumber);
				} else if (cases[i].caseNumber == null) {
					System.out.printf("%-3s %s  %-88s %-30s %s\n", Integer.toString(i+1)+".", cases[i].caseDate, cases[i].caseTitle, cases[i].caseType, "");
				} else{
					System.out.printf("%-3s %s  %-88s %-30s %s\n", Integer.toString(i+1)+".", cases[i].caseDate, cases[i].caseTitle,cases[i].caseType, cases[i].caseNumber);
				}
			}
		}
		System.out.println("--------------------------------------------------------------------------------------------------");
	}

	/**printCaseTypeSummary() prints a summary of
	 * number of cases of different types as per the 
	 * format given in the handout.
	 */
	void printCaseTypeSummary() {
		int countAdmin = 0;
		int countFederal = 0;
		int countUnknow = 0;
		for (int i = 0; i < ccModel.cases.length; i++) {						//Count three kinds of case type respectively
			if (ccModel.cases[i].caseType == null) {
				countUnknow += 1;
			} else if (ccModel.cases[i].caseType.equals("Federal")) {
				countFederal += 1;
			} else {
				countAdmin += 1;
			}
		}

		System.out.println("*** Case Type Summary Report ***");
		System.out.println("No. of Administrative cases: " + countAdmin);
		System.out.println("No. of Federal cases: " + countFederal);
		System.out.println("No. of Unknow cases types: " + countUnknow);
		System.out.println("--------------------------------------------------------------------------------------------------");
	}

	/**printYearWiseSummary() prints number of cases in each year
	 * as per the format given in the handout
	 */
	void printYearwiseSummary() {
		int count = 0;
		int colNumber = 1;
		int year;
		int minYear = ccModel.cases[0].getYear();
		int maxYear = ccModel.cases[0].getYear();
		for (int i = 0; i < ccModel.cases.length; i++) {						//Find the max and min year in the filedata
			year = ccModel.cases[i].getYear();
			if (year < minYear) {
				minYear = year;
			} else if (year > maxYear) {
				maxYear = year;
			}
		}

		System.out.println("                                 *** Year-wise Summary Repory ***                                 ");
		System.out.println("                                 *** Number of FTC cases per year ***                             \n");
		int yearRange = maxYear - minYear + 1;
		for (int j = 0; j < yearRange; j++) {									//Traverse all years between max and min year
			int currentYear = maxYear - j;
			for (int k = 0; k < ccModel.cases.length; k++) {					//Traverse the filedata and count
				if (ccModel.cases[k].getYear() == currentYear) {
					count += 1;	
				}
			}

			if (currentYear == minYear) {										//If the end, then print and new line
				System.out.printf("%15d: %2d\n", currentYear, count);
			} else if (colNumber % 5 != 0) {							
				System.out.printf("%15d: %2d", currentYear, count);             
			} else {																
				System.out.printf("%15d: %2d\n", currentYear, count);			//If a row has five column, then new line				
			}
			colNumber += 1;	
			count = 0;															//Recount
		}		
		System.out.println("--------------------------------------------------------------------------------------------------");
	}

}
