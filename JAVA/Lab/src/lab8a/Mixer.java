package lab8a;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.LinkedList;
import java.util.Queue;

public class Mixer {
	static Queue <CMUStudent> studentsQ = new LinkedList<>();
	static final int MAX_STUDENTS = 20;
	Administrator admin1 = new Administrator();  //to be named Andy
	Administrator admin2 = new Administrator();  //to be named Sean

	//Do not change this method
	public static void main(String[] args) {
		Mixer mixer = new Mixer();
		mixer.startEvent();
		mixer.checkAssertions();
	}
	
	/**startEvent() populates studentsQ with MAX_STUDENTS
	 * It then creates two threads t1 and t2. 
	 * It names them as Andy and Sean.
	 * It starts the two threads and waits 
	 * for them to join back. 
	 * Finally, it invokes printReport()
	 * 
	 */
	void startEvent() {
		for (int i = 0; i < MAX_STUDENTS; i++) {
			studentsQ.offer(new CMUStudent());
		}
				
		Thread t1 = new Thread(admin1);
		Thread t2 = new Thread(admin2);
		t1.start();
		t2.start();
		t1.setName("Andy");  
		t2.setName("Sean");  
	
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			printReport(t1, t2);
		}
	}
	
	//Do not change this method
	void printReport(Thread t1, Thread t2) {
		System.out.println("\n*** Event Report ***");
		System.out.printf("\nTotal students welcomed by %s: %d%n", t1.getName(), admin1.adminWelcomeCount);
		System.out.printf("Total talk time by %s: %d ms%n", t1.getName(), admin1.adminTalkTime);
		System.out.println("---------------------------------");
		System.out.printf("\nTotal students welcomed by %s: %d%n", t2.getName(), admin2.adminWelcomeCount);
		System.out.printf("Total talk time by %s: %d ms%n", t2.getName(), admin2.adminTalkTime);
		System.out.println("---------------------------------");
		
		System.out.println("\n*** End Report ***");
	}
	
	//Do not change this method
	void checkAssertions() {
		assertEquals(CMUStudent.cmuStudentCount, Administrator.totalWelcomeCount);
		assertEquals(MAX_STUDENTS, admin1.adminWelcomeCount + admin2.adminWelcomeCount);
	}
}
