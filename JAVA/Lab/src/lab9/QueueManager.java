//Qinzhi Peng, qinzhip
package lab9;

import java.util.Random;

public class QueueManager implements Runnable {

	int customerDelay;
	int balkCount;

	QueueManager(int customerDelay) {
		this.customerDelay = customerDelay;
	}

	@Override
	public void run() {
		Random rand = new Random();
		Random randChoice = new Random();
		Customer newCustomer;
		boolean isJoined;

		while (TicketWindow.isWindowOpen) {
			try {
				// choose examPart
				if (MovieHall.examPart == 1) {
					newCustomer = new Customer();
				} else {
					int choice = randChoice.nextInt(2);
					if (choice == 0) {
						newCustomer = new Customer();
					} else {
						newCustomer = new ImpatientCustomer();
					}
				}

				synchronized (MovieHall.customerQueue) {
					isJoined = newCustomer.joinQueue();
					if (isJoined == false) {
						balkCount ++;
					}
				}

				Thread.sleep(rand.nextInt(MovieHall.customerDelay + 1));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
