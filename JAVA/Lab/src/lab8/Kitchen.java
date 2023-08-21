//qinzhip, Qinzhi Peng
package lab8;

import java.util.Queue;
import java.util.Random;

public class Kitchen implements Runnable{
	final int MEAL_RATE = 6;  		//$ per meal
	final int MAX_COOK_TIME = 7;  	//max time to cook one meal
	final int MIN_COOK_TIME = 1;  	//min time to cook one meal
	final int OPENING_STOCK = 175;	
	final int MIN_STOCK = 4;
	int currentStock; 	
	boolean  isUnderStock;
	int guestsServed, income;
	Queue<Guest> guestQ;

	Kitchen (Queue<Guest> guestQ) {
		this.guestQ = guestQ;
	}

	
	/** run() loops while Diner isOpen and kitchen is not under stock. 
	 * In this loop, it polls guestQ, and if there is a guest,
	 * then invokes its placeOrder().
	 * Based on the number of meals returned by placeOrder(),
	 * the kitchen updates currentStock and income. 
	 * If currentStock falls at or below MIN_STOCK, then isUnderStock
	 * is set to true.
	 * Each meal costs MEAL_RATE.
	 * The kitchen sleeps anywhere from MIN_COOK_TIME to MAX_COOK_TIME
	 * to simulate cooking time. 
	 * It also increments guestsServed as needed. 
	 */
	@Override
	public void run() {
		Random rand = new Random();
		currentStock = OPENING_STOCK;
		while(Diner.isOpen && isUnderStock == false) {			
			try {
				Guest g;
				synchronized(Diner.guestQ) {
					g = Diner.guestQ.poll();
				}
				
				if (g != null) {
					int meals = g.placeOrder();
					currentStock -= meals;
					income += meals * MEAL_RATE;
					
					if (currentStock <= MIN_STOCK) {
						isUnderStock = true;
					} else {
						isUnderStock = false;
					}
					
					Thread.sleep(rand.nextInt(MAX_COOK_TIME - MIN_COOK_TIME + 1) + MIN_COOK_TIME);
					guestsServed ++;
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		}
		
		
	}
}
