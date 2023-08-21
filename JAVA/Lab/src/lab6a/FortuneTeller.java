package lab6a;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


public class FortuneTeller {
	List<Student> students = new ArrayList<>();
	List<Company> companies = new ArrayList<>();

	// DO NOT CHANGE THIS METHOD
	public static void main(String[] args) {
		FortuneTeller fortuneTeller = new FortuneTeller();
		fortuneTeller.loadStudentsList("Fortunes.csv");
		fortuneTeller.loadCompaniesList();
		
		System.out.println("*** Welcome to Fortune Teller! ***");
		System.out.println("1. No. of students hired by Fortune100 Best Employers");
		System.out.println("2. Fortune100 Best Employers by Hired Count");
		System.out.println("3. Employers and Students");
		System.out.println("Please choose an option or any other key to exit");
		Scanner input = new Scanner(System.in);
		int choice = input.nextInt();
		fortuneTeller.printReport(choice);
		input.close();
	}

	void printReport(int choice) {
		switch (choice) {
		case 1: {

			/** Write the appropriate Collections.sort() statement here to sort companies on Rank*/
			Collections.sort(companies);
			System.out.println("*** No. of students hired by Fortune10 Best Employers ***"); 
			System.out.println("--------------------------------------------------------------------");
			System.out.println("Rank. Company\t\t\t\tHired Count");
			System.out.println("--------------------------------------------------------------------");
			for (Company c : companies) {
				System.out.printf("%3d. %-30s: %10d%n", c.rank, c.name, c.hiredCount);
			}
			System.out.println("--------------------------------------------------------------------");
			System.out.printf("Total%43d%n", Company.overallHiredCount);
			System.out.println("====================================================================");
			break;
		}

		case 2: {

			/** Write the appropriate Collections.sort() statement here to sort companies on hiredCount*/
			Collections.sort(companies, new CompanyHiredCountComparator());
			System.out.println("*** Fortune100 Best Employers by Hired Count ***"); 
			System.out.println("--------------------------------------------------------------------");
			System.out.println("Rank. Company\t\t\t\tHired Count");
			System.out.println("--------------------------------------------------------------------");
			for (Company c : companies) {
				System.out.printf("%3d. %-30s: %10d%n", c.rank, c.name, c.hiredCount);
			}
			System.out.println("--------------------------------------------------------------------");
			System.out.printf("Total%43d%n", Company.overallHiredCount);
			System.out.println("====================================================================");

			break;
		}

		case 3: {
			/** Write the appropriate Collections.sort() statement here to sort company and students*/
			Collections.sort(students, new StudentCompanyComparator());
			System.out.println("*** Employers and Students ***");
			System.out.println("--------------------------------------------------------------------");
			int i = 1;
			System.out.printf("#.   %-20s %-20s %-25s %-10s%n", "Employer", "AndrewID", "First name", "Last name" );
			System.out.println("--------------------------------------------------------------------");
			for (Student s: students) {
				System.out.printf("%3d. %-20s %-20s %-25s %-10s%n", i++,  s.companyName, s.andrewID, s.firstName, s.lastName);
			}
			System.out.println("====================================================================");
			break;
		}
		default: break;
		}

	}

	/** loadStudentsList() reads the data from filename 
	 * and loads students arrayList. 
	 */
	void loadStudentsList(String filename) {
		String[] List = null;	
		File file = new File(filename);
		try(Scanner fileScanner = new Scanner(file)) {
			while(fileScanner.useDelimiter("\n").hasNext()) {
				List = fileScanner.nextLine().split(",");
				students.add(new Student(List[0].trim(), List[1].trim(), List[2].trim(), Integer.parseInt(List[3].trim()), List[4].trim()));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}

	/** loadCompaniesList() uses the data stored in students array list
	 * to create companies array list. In this list, each Company object
	 * must have information about the company that has hired some students
	 */
	void loadCompaniesList() {
		List<String> companyName = new ArrayList<>();
		for (Student s : students) {
			Company.overallHiredCount++;
			if (companyName.contains(s.companyName)) {	
				Company c = companies.get(companyName.indexOf(s.companyName));
				c.hiredCount++;			
			} else {
				companyName.add(s.companyName);
				Company c = new Company();
				c.rank = s.companyRank;
				c.name = s.companyName;
				c.hiredCount++;	
				companies.add(c);
			}
		}
	}


	/*********** write your Comparators below this line ***************/
	public class StudentCompanyComparator implements Comparator<Student> {

		@Override
		public int compare(Student s1, Student s2) {
			if (s1.companyName.equalsIgnoreCase(s2.companyName)) {
				return s1.andrewID.compareTo(s2.andrewID);
			}
			
			return s1.companyName.compareTo(s2.companyName);
		}
		
	}
	
	public class CompanyHiredCountComparator implements Comparator<Company> {

		@Override
		public int compare(Company c1, Company c2) {
			if (c1.hiredCount == c2.hiredCount) {
				return c1.name.compareTo(c2.name);
			}
				
			return c2.hiredCount - c1.hiredCount;
		}
		
	}

}
