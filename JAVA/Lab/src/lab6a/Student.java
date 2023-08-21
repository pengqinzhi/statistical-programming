package lab6a;

public class Student implements Comparable<Student>{
	String lastName;
	String firstName;
	String andrewID;
	int companyRank;
	String companyName;
	
	Student (String lastName, String firstName, String andrewID, int companyRank, String companyName) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.andrewID = andrewID;
		this.companyRank = companyRank;
		this.companyName = companyName;		
	}

	@Override
	public int compareTo(Student s) {
		return this.andrewID.compareTo(s.andrewID);
	}
		

}
