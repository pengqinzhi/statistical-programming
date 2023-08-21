//Qinzhi Peng; qinzhip

package hw1;

public class Case {
	String caseDate; //date in YYYY-mm-dd format
	String caseTitle;
	String caseType;
	String caseNumber;

	Case(String caseDate, String caseTitle, String caseType, String caseNumber) {
		this.caseDate = caseDate;
		this.caseTitle = caseTitle;
		this.caseType = caseType;
		this.caseNumber = caseNumber;
	}

	/** getYear() is an optional method to extract year
	 * from the caseDate. It can be useful 
	 * for printing yearWise summary. 
	 * @return
	 */

	int getYear() {
		String[] date = caseDate.trim().split("-");
		int year = Integer.parseInt(date[0]);
		return year;
	}

}