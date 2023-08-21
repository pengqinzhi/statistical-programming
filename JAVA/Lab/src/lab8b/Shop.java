package lab8b;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Shop implements Runnable{
	
	static List<Queue<Customer>> customerQ = new ArrayList<>(); 
	static boolean isShopOpen = true;  //starts as true. Set to false when all customers served
	static int maxCustomer; //Maximum number of customers created
	static int countersNum;
	static int customersServed; //Incremented after serving each customer
	static int processingTime;	//Time for SalesCounter to process one sale-item
	static long totalQueueTime; //Incremented  after serving each customer
	
	List<SalesCounter> salesCounters = new ArrayList<>(); //instances of SalesCounter
	Thread[] salesCounterThreads; //threads to run the salesCounters 
	int customerGapTime;	//interval between customer arrivals
	
	/** setupCounters() takes user inputs, creates SalesCounter object, 
	 * assigns it to salesCounterThread, and starts it 
	 **/
	void setupCounters() {
		Scanner input = new Scanner(System.in);
		System.out.println("How many counters to open?");
		countersNum = input.nextInt();
		input.nextLine();
		System.out.println("Sales item processing time?");
		processingTime = input.nextInt();
		input.nextLine();
		System.out.println("Max customer count?");
		maxCustomer = input.nextInt();
		input.nextLine();
		System.out.println("Customer gap time?");
		customerGapTime = input.nextInt();
		input.close();
		
		salesCounterThreads = new Thread[countersNum];
		for (int i = 0; i < countersNum; i++) {
			customerQ.add(new LinkedList<>());
			SalesCounter couter = new SalesCounter();
			salesCounters.add(couter);
			salesCounterThreads[i] = new Thread(couter);
			salesCounterThreads[i].start();
		}
	}

	/** joinQueue() adds customer c to customeQ, 
	 * Prints the message "SalesCounter0: CustomerX joined with Y items. Q length:Z". 
	 * Initialize c’s enqueueTime to current time
	 */
	public void joinQueue(Customer c) {
		int i = 0;
		int minQ = customerQ.get(0).size();
		for (Queue<Customer> q : customerQ) {
			if (q.size() < minQ) {
				minQ = q.size();
				i = customerQ.indexOf(q);
			}
		}
		
		customerQ.get(i).offer(c);
		String space = ThreadMart.spacer(i);
		System.out.printf("%sSalesCounter%d: Customer%d joined with %d items. Q length:%d\n", space, salesCounters.get(i).id, c.id, c.itemsBought, customerQ.get(i).size());
		c.enqueueTime = System.currentTimeMillis();
	}

	/** run() invokes setupCounters(), and runs the following 
	 * as long as CustomersServed < maxCustomer 
	 * - 	If Customer.count < maxCustomer, create new customer and pass it to joinQueue() 
	 * -	Sleep for customerGapTime 
	 * -	Set isShopOpen to false 
	 * -	Wait for salesCounterThread to join 
	 * */
	@Override
	public void run() {
		setupCounters();

		try {
			while (customersServed < maxCustomer) {
				if (Customer.count < maxCustomer) {
					Customer c = new Customer();
					synchronized (customerQ) {
						joinQueue(c);
					}
				}

				Thread.sleep(customerGapTime);

				if (customersServed == maxCustomer) 
					isShopOpen = false;
				
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
