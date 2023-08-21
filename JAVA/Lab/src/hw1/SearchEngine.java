//Qinzhi Peng; qinzhip

package hw1;

public class SearchEngine {

	/**searchTitle() takes a searchString and array of cases,
	 * searches for cases with searchString in their title,
	 * and if found, returns them in another array of cases.
	 * If no match is found, it returns null.
	 * Search is case-insensitive
	 * @param searchString
	 * @param cases
	 * @return
	 */
	Case [] searchTitle(String searchString, Case[] cases) {
		int Count = 0;										//Record the number of searchString appearing in cases array
		StringBuilder searchIndex = new StringBuilder();	//Store the index of searchString appearing in cases array
		for (int i = 0; i < cases.length; i++) {
			if (cases[i].caseTitle.toLowerCase().contains(searchString.toLowerCase())) {			//Search for searchString with case-insensitive
				Count += 1;
				searchIndex.append(i + ",");
			}
		}

		Case[] searchCases = new Case[Count];				//Initialize an array to store the content of searchString
		String[] index = searchIndex.toString().split(",");
		for (int j = 0; j < searchCases.length; j++) {
			searchCases[j] = cases[Integer.parseInt(index[j])];
		}

		if (searchCases.length != 0) 
			return searchCases;

		return null;
	}

	/**searchYear() takes year in YYYY format as search string,
	 * searches for cases that have the same year in their date,
	 * and returns them in another array of cases.
	 * If not found, it returns null.
	 * @param year
	 * @param cases
	 * ·ÖÀà@return
	 */
	Case[] searchYear(String year, Case[] cases) {
		int Count = 0;										//The same method as above		
		StringBuilder searchIndex = new StringBuilder();	
		for (int i = 0; i < cases.length; i++) {
			if (Integer.toString(cases[i].getYear()).equals(year)) {			
				Count += 1;
				searchIndex.append(i + ",");
			}
		}

		Case[] searchCases = new Case[Count];				
		String[] index = searchIndex.toString().split(",");
		for (int j = 0; j < searchCases.length; j++) {
			searchCases[j] = cases[Integer.parseInt(index[j])];
		}

		if (searchCases.length != 0) 
			return searchCases;

		return null;
	}

	/**searchCaseNumber() takes a caseNumber,
	 * searches for those cases that contain that caseNumber, 
	 * and returns an array of cases that match the search.
	 * If not found, it returns null.
	 * Search is case-insensitive.
	 * @param caseNumber
	 * @param cases
	 * @return
	 */
	Case[] searchCaseNumber(String caseNumber, Case[] cases) {
		int Count = 0;										
		StringBuilder searchIndex = new StringBuilder();	
		for (int i = 0; i < cases.length; i++) {
			if (cases[i].caseNumber == null) 
				break;

			if (cases[i].caseNumber.toLowerCase().contains(caseNumber.toLowerCase())) {			
				Count += 1;
				searchIndex.append(i + ",");
			}
		}

		Case[] searchCases = new Case[Count];				
		String[] index = searchIndex.toString().split(",");
		for (int j = 0; j < searchCases.length; j++) {
			searchCases[j] = cases[Integer.parseInt(index[j])];
		}

		if (searchCases.length != 0) 
			return searchCases;

		return null;
	}
}
