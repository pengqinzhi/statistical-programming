//Qinzhi Peng, qinzhip
package hw2;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Case implements Comparable <Case> {
	
	private final StringProperty caseDate;
	private final StringProperty caseTitle;
	private final StringProperty caseType;
	private final StringProperty caseNumber;
	private final StringProperty caseLink;
	private final StringProperty caseCategory;
	private final StringProperty caseNotes;
	
	public Case (String caseDate, String caseTitle, String caseType, String caseNumber, String caseLink, String caseCategory, String caseNotes) {
		this.caseDate = new SimpleStringProperty(caseDate);
		this.caseTitle = new SimpleStringProperty(caseTitle);
		this.caseType = new SimpleStringProperty(caseType);
		this.caseNumber = new SimpleStringProperty(caseNumber);
		this.caseLink = new SimpleStringProperty(caseLink);
		this.caseCategory = new SimpleStringProperty(caseCategory);
		this.caseNotes = new SimpleStringProperty(caseNotes);
	}

	public final String getCaseDate() {
		return caseDate.get();
	}

	public final void setCaseDate(String caseDate) {
		this.caseDate.set(caseDate);
	}

	public final StringProperty caseDateProperty() {
		return caseDate;
	}
	
	public final String getCaseTitle() {
		return caseTitle.get();
	}

	public final void setCaseTitle(String caseTitle) {
		this.caseTitle.set(caseTitle);
	}

	public final StringProperty caseTitleProperty() {
		return caseTitle;
	}
	
	public final String getCaseTpye() {
		return caseType.get();
	}

	public final void setCaseTpye(String caseType) {
		this.caseType.set(caseType);
	}

	public final StringProperty caseTypeProperty() {
		return caseType;
	}
	
	public final String getCaseNumber() {
		return caseNumber.get();
	}

	public final void setCaseNumber(String caseNumber) {
		this.caseNumber.set(caseNumber);
	}

	public final StringProperty caseNumberProperty() {
		return caseNumber;
	}
	
	public final String getCaseLink() {
		return caseLink.get();
	}
	
	public final void setCaseLink(String caseLink) {
		this.caseLink.set(caseLink);
	}
	
	public final StringProperty caseLinkProperty() {
		return caseLink;
	}
	
	public final String getCaseCategory() {
		return caseCategory.get();
	}
	
	public final void setCaseCategory(String caseCategory) {
		this.caseCategory.set(caseCategory);
	}
	
	public final StringProperty caseCategoryProperty() {
		return caseCategory;
	}
	
	public final String getCaseNotes() {
		return caseNotes.get();
	}
	
	public final void setCaseNotes(String caseNotes) {
		this.caseNotes.set(caseNotes);
	}
	
	public final StringProperty caseNotesProperty() {
		return caseNotes;
	}
	
	@Override
    public int compareTo(Case c) {
        return c.caseDate.get().compareTo(this.caseDate.get());
    }
	
	@Override
	public String toString() {
		return caseNumber.get();
	}
	
}
