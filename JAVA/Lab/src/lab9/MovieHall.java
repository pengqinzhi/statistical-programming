//Qinzhi Peng, qinzhip
package lab9;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class MovieHall {

	static int examPart;
	static int maxSeats;
	static final int MIN_TICKETS = 1;
	static final int MAX_TICKETS = 10;
	static Queue<Customer> customerQueue = new LinkedList<>();
	static int balkQueueLength;

	static int ticketProcessingTime;
	static int customerDelay;
	static long startTime;
	static long endTime;

	static QueueManager queueManager;
	static TicketWindow ticketWindow;

	public static void main(String[] args) {
		MovieHall movieHall = new MovieHall();
		movieHall.getInputs();
		movieHall.startThreads();
		movieHall.printReport();
		movieHall.testResults();
	}

	private void getInputs() {
		Scanner input = new Scanner(System.in);
		System.out.println("Part 1 or 2?");
		examPart = input.nextInt();
		input.nextLine();
		System.out.println("Enter single processing time (ms):");
		ticketProcessingTime = input.nextInt();
		input.nextLine();
		System.out.println("Enter max tickets to be sold:");
		maxSeats = input.nextInt();
		input.nextLine();
		System.out.println("Enter max customer delay(ms):");
		customerDelay = input.nextInt();
		input.nextLine();
		
		if (examPart == 2) {
			System.out.println("Enter impatient customer's balk-queue-length:");
			balkQueueLength = input.nextInt();
			input.nextLine();
		}

		input.close();

	}

	private void startThreads() {
		System.out.println("------------- Detailed Customer Report -------------");
		queueManager = new QueueManager(customerDelay);
		ticketWindow = new TicketWindow(ticketProcessingTime);
		Thread t1 = new Thread(queueManager);
		Thread t2 = new Thread(ticketWindow);
		t1.start();
		t2.start();
		startTime = System.currentTimeMillis();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void printReport() {
		System.out.println("------------- Part 1 Report -------------");
		System.out.println("Ticket window open duration             : " + (endTime - startTime) + "ms");
		System.out.println("Total customers                         : " + Customer.customerCount);
		System.out.println("Customers who bought tickets            : " + ticketWindow.customerList.size());
		System.out.println("Customers in queue when window closed   : "
				+ (Customer.customerCount - ticketWindow.customerList.size()));
		System.out.println("                                          ");
		System.out.println("Total tickets sold                      : " + TicketWindow.ticketSoldCount);

		if (examPart == 2) {
			System.out.println("------------- Part 2 Report -------------");
			System.out.println("Impatient customers                 : " + ImpatientCustomer.impatientCustomerCount);
			System.out.println("Customers who balked                : " + queueManager.balkCount);
		}

		System.out.println("------------- Customer Summary Report -------------");
		int i = 1;
		int total = 0;
		Collections.sort(ticketWindow.customerList);
		for (Customer c : ticketWindow.customerList) {
			total += c.numberOfTickets;
			if (!(c instanceof ImpatientCustomer)) {
				System.out.printf("%-3s          Customer%2s bought: %2s tickets.       Cumulative total: %2s\n", i + ".", c.id, c.numberOfTickets, total);
			} else {
				System.out.printf("%-3s ImpatientCustomer%2s bought: %2s tickets.       Cumulative total: %2s\n", i + ".", c.id, c.numberOfTickets, total);
			}
			i++;
		}
	}

	private void testResults() {

		int ticketsSold = 0; // total tickets sold
		int minTickets = MIN_TICKETS, maxTickets = MAX_TICKETS;
		// find the min, max, and total tickets sold from the customerList
		for (Customer c : ticketWindow.customerList) {
			ticketsSold += c.numberOfTickets;
			if (minTickets > c.numberOfTickets)
				minTickets = c.numberOfTickets;
			if (maxTickets < c.numberOfTickets)
				maxTickets = c.numberOfTickets;
		}
		// test whether total customerCount matches the sum of customers in
		// customerList, //customers in customerQueue, and customers who balked
		assertEquals("Total customers", Customer.customerCount,
				ticketWindow.customerList.size() + customerQueue.size() + queueManager.balkCount);

		// test total tickets sold calculated above matches the total count at
		// TicketWindow
		assertEquals("Total tickets sold", ticketsSold, TicketWindow.ticketSoldCount);
		// test that total tickets sold is equal to or more than maxSeats
		// assertTrue(ticketsSold >= maxSeats);
		// test the minTickets calculated above is greater than or equal to MIN_TICKETS
		assertTrue("Min tickets", minTickets >= MIN_TICKETS);
		// test the maxTickets calculated above is less than or equal to MIN_TICKETS
		assertTrue("Max tickets", maxTickets <= MAX_TICKETS);
	}
}
