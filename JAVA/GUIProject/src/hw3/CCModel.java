//Qinzhi Peng, qinzhip
package hw3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class CCModel {

	ObservableList<Case> caseList = FXCollections.observableArrayList(); // a list of case objects
	ObservableMap<String, Case> caseMap = FXCollections.observableHashMap(); // map with caseNumber as key and Case as
																				// value
	ObservableMap<String, List<Case>> yearMap = FXCollections.observableHashMap(); // map with each year as a key and a
																					// list of all cases dated in that
																					// year as value.
	ObservableList<String> yearList = FXCollections.observableArrayList(); // list of years to populate the yearComboBox
																			// in ccView

	/**
	 * readCases() performs the following functions: It creates an instance of
	 * CaseReaderFactory, invokes its createReader() method by passing the filename
	 * to it, and invokes the caseReader's readCases() method. The caseList returned
	 * by readCases() is sorted in the order of caseDate for initial display in
	 * caseTableView. Finally, it loads caseMap with cases in caseList. This caseMap
	 * will be used to make sure that no duplicate cases are added to data
	 * 
	 * @param filename
	 */
	void readCases(String filename) {
		CaseReaderFactory caseReader = new CaseReaderFactory();
		List<Case> caseArrayList = caseReader.createReader(filename).readCases();
		Collections.sort(caseArrayList); // sort the caseList in the order of caseDate
		caseList = FXCollections.observableArrayList(caseArrayList);

		for (Case c : caseList) {
			caseMap.put(c.getCaseNumber(), c);
		}
	}

	/**
	 * writeCases() writes caseList elements in a TSV file. If the write is
	 * successful, it returns true. In case of IOException, it returns false.
	 */
	boolean writeCases(String filename) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename));) {
			StringBuilder fileContent = new StringBuilder();
			for (Case c : caseList) {
				fileContent.append(String.format(c.getCaseDate() + " \t" + c.getCaseTitle() + " \t" + c.getCaseTpye()
						+ " \t" + c.getCaseNumber() + " \t" + c.getCaseLink() + " \t" + c.getCaseCategory() + " \t"
						+ c.getCaseNotes() + " \n"));
			}
			bw.write(fileContent.toString());
		} catch (IOException e) {
			System.out.println("write falied!");
			return false;
		}
		return true;
	}

	/**
	 * buildYearMapAndList() performs the following functions: 1. It builds yearMap
	 * that will be used for analysis purposes in Cyber Cop 3.0 2. It creates
	 * yearList which will be used to populate yearComboBox in ccView Note that
	 * yearList can be created simply by using the keySet of yearMap.
	 */
	void buildYearMapAndList() {
		String currentYear;
		List<Case> yearCaseList; // a list of all cases dated in the current year

		for (Case c1 : caseList) {
			yearCaseList = new ArrayList<>();
			currentYear = c1.getCaseDate().substring(0, 4);

			if (!yearList.contains(currentYear)) { // if meeting a new year, then add the new year into yearList, and
													// add new key and value into yaerMap
				yearList.add(currentYear);
				for (Case c2 : caseList) {
					if (c2.getCaseDate().substring(0, 4).equalsIgnoreCase(currentYear)) {
						yearCaseList.add(c2);
					}
				}
				yearMap.put(currentYear, yearCaseList);
			}
		}
	}

	/**
	 * searchCases() takes search criteria and iterates through the caseList to find
	 * the matching cases. It returns a list of matching cases.
	 */
	List<Case> searchCases(String title, String caseType, String year, String caseNumber) {
		List<Case> matchingList = new ArrayList<>();
		for (int i = 0; i < caseList.size(); i++) {
			matchingList.add(caseList.get(i));
		}

		// Use the method of exclusion to search cases
		for (Case c : caseList) {
			if (c.getCaseTitle() != null && title != null) {
				if (!c.getCaseTitle().toLowerCase().contains(title.toLowerCase())) {
					matchingList.remove(c);
				}
			}

			if (c.getCaseTpye() != null && caseType != null) {
				if (!c.getCaseTpye().toLowerCase().contains(caseType.toLowerCase())) {
					matchingList.remove(c);
				}
			}

			if (c.getCaseDate() != null && year != null) {
				if (!c.getCaseDate().substring(0, 4).equalsIgnoreCase(year)) {
					matchingList.remove(c);
				}
			}

			if (c.getCaseNumber() != null && caseNumber != null) {
				if (!c.getCaseNumber().toLowerCase().contains(caseNumber.toLowerCase())) {
					matchingList.remove(c);
				}
			}
		}

		return matchingList;
	}

}
