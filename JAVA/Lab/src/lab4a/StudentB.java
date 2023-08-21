package lab4a;
import java.util.Random;

public class StudentB extends Student {

	static double studentBDonations; 
	static int MAX_MONEY_DONATION  = 1000;
	
	StudentB(char section, String lastName, String firstName, double income) {
		super(section, lastName, firstName, income);
	}
	
	@Override
	void donate() {
		Random rand = new Random();
		donation = rand.nextInt(MAX_MONEY_DONATION) + 1;  		
		Student.totalMoneyDonations += donation;
		StudentB.studentBDonations += donation;
	}

}
