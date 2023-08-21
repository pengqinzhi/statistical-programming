//Qinzhi Peng, qinzhip
package hw2;

public class CaseReaderFactory {

	public CaseReader createReader(String filename) {
		CaseReader caseReader = null;
		String extension = filename.substring(filename.length() - 3);

		/** choose csvReader or tsvReader */
		switch (extension) {
		case "csv":
			caseReader = new CSVCaseReader(filename);
			break;
		case "tsv":
			caseReader = new TSVCaseReader(filename);
			break;
		}

		return caseReader;
	}

}
