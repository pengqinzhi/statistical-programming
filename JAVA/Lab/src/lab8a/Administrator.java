package lab8a;

public class Administrator implements Runnable {
	static int totalWelcomeCount; // total students welcomed by both admins
	int adminWelcomeCount; // students welcomed by individual admin
	int adminTalkTime; // cumulative talk time by each admin

	/**
	 * run() simulates the event in which the admin polls the studentsQ, and then
	 * talks to the student for student.studentTalkTime. It adds this time to
	 * adminTalkTime and also increments adminWelcomeCount and totalWelcomeCount For
	 * each student, it prints the output as shown.
	 */
	@Override
	public void run() {

		while (totalWelcomeCount < Mixer.MAX_STUDENTS) {
			try {
				CMUStudent s;
				synchronized (Mixer.studentsQ) {
					s = Mixer.studentsQ.poll();
					if (s != null) {
						if (Thread.currentThread().getName().equals("Sean")) {
							System.out.printf("Sean talking to Student %d for %dms\n", s.id, s.studentTalkTime);
						} else {
							System.out.printf("    Andy talking to Student %d for %dms\n", s.id, s.studentTalkTime);
						}
					}
				}

				if (s != null) {
					Thread.sleep(s.studentTalkTime);

					this.adminWelcomeCount++;
					this.adminTalkTime += s.studentTalkTime;
					totalWelcomeCount++;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
