//Qinzhi Peng, qinzhip
package lab9;

import java.util.Random;

public class Customer implements Comparable<Customer>{
	
	static int customerCount;
	int id;
	int numberOfTickets;
	
	Customer() {
		customerCount++;
		id = customerCount;
		Random rand = new Random();
		numberOfTickets = rand.nextInt((MovieHall.MAX_TICKETS - MovieHall.MIN_TICKETS + 1)) + MovieHall.MIN_TICKETS;
	}
	
	boolean joinQueue(){
		MovieHall.customerQueue.offer(this);
		System.out.printf("Customer%d joined Q\n", this.id);
		return true;
	}
	
	@Override
	public int compareTo(Customer c) {
		if (this.numberOfTickets == c.numberOfTickets)
			return this.id - c.id;
		
		return c.numberOfTickets - this.numberOfTickets;
	}
	
	

}
