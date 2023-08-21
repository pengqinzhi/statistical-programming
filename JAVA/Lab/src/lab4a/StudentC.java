package lab4a;

import java.util.Random;

public class StudentC extends Student implements Serviceable{
	static double studentCDonations; 
	static int MAX_MONEY_DONATION  = 10;
	static double timeDonation;

	StudentC(char section, String lastName, String firstName, double income) {
		super(section, lastName, firstName, income);
	}

	@Override
	void donate() {
		donation = MAX_MONEY_DONATION;  		
		Student.totalMoneyDonations += donation;
		StudentC.studentCDonations += (double) donation;
	}
	

	public void serve() {
		Random rand = new Random();
		timeDonation = rand.nextInt(Serviceable.MAX_SERVICE_HOURS) + 1;
		Student.totalTimeDonations += timeDonation;
		
	}
	
}
