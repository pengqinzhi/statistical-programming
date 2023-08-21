package lab8b;

import java.util.Random;

public class Customer {
	
	static int count; //Counts all customer objects created
	int id; //Unique sequential identifier for each customer object
	long enqueueTime; //timestamp when customer joins customerQ
	long dequeueTime;  //timestamp when customer leaves customerQ
	int itemsBought; //Contains random int from 1 to 10
	
	
	/** Customer() increments count, assigns id, 
	 * sets itemsBought randomly to any number between 1 and 10.
	 **/
	Customer() {
		Random rand = new Random();
		id = count;
		count++;
		itemsBought = rand.nextInt(10) + 1;
	}

}
