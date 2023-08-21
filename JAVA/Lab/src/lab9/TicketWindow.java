package lab9;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicketWindow implements Runnable {
	static int ticketSoldCount;
	static boolean isWindowOpen;
	int ticketProcessingTime;
	List<Customer> customerList;

	TicketWindow(int ticketProcessingTime) {
		this.ticketProcessingTime = ticketProcessingTime;
	}

	@Override
	public void run() {
		Random rand = new Random();
		MovieHall.ticketWindow = new TicketWindow(MovieHall.ticketProcessingTime);
		customerList = new ArrayList<>();
		Customer customer = null; 
		while (isWindowOpen) {
			try {
				synchronized(MovieHall.customerQueue) {
					customer = MovieHall.customerQueue.poll();
					ticketSoldCount += customer.numberOfTickets;
				}
				
				System.out.printf("Customer %d bought %d tickets", customer.id, customer.numberOfTickets);
				
				if (customer != null) {
					customerList.add(customer);
				}
				
				if (ticketSoldCount >= MovieHall.maxSeats) {
					isWindowOpen = false;
				}
				
				Thread.sleep(rand.nextInt(MovieHall.ticketProcessingTime * customer.numberOfTickets + 1));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
