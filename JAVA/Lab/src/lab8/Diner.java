//qinzhip, Qinzhi Peng
package lab8;

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Diner implements Runnable {
	final int GUEST_DELAY = 50; // max lapsed time between guest arrivals
	final int DINER_DURATION = 1000; // max time for which Diner will remain open
	static boolean isOpen = true; // indicator

	static Queue<Guest> guestQ = new LinkedList<>();
	Kitchen kitchen = new Kitchen(guestQ);
	long startTime, endTime;
	int maxQLength, guestsEntered;

	/**
	 * main() method should instantiate the Diner, put kitchen on new thread,
	 * capture start time, start the kitchen, and then invoke its own run() method.
	 * It should then wait for kitchen to join back, after which it should
	 * printReport() and checkAssertions()
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Diner diner = new Diner();
		Thread t1 = new Thread(diner);
		Thread t2 = new Thread(diner.kitchen);
		t1.start();
		t2.start();

		diner.startTime = System.currentTimeMillis();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			diner.printReport();
			diner.checkAssertions();
		}

	}

	/**
	 * run() method runs a loop which isOpen is true and kitchen's isUnderStock is
	 * false. In this loop, it creates new guests, adds then to guestQ, increments
	 * guestsEntered, and then goes to sleep randomly between 1 to GUEST_DELAY It
	 * checks if the time since it started is equal to greater than DINER_DURATION.
	 * If yes, it sets isOpen to false.
	 */
	@Override
	public void run() {
		Random rand = new Random();
		try {
			while (isOpen && kitchen.isUnderStock == false) {
				Guest g = new Guest();
				synchronized (guestQ) {
					guestQ.offer(g);
					maxQLength = Math.max(maxQLength, guestQ.size());
				}

				guestsEntered++;
				Thread.sleep(rand.nextInt(GUEST_DELAY) + 1);

				endTime = System.currentTimeMillis();
				if (endTime - startTime >= DINER_DURATION) {
					isOpen = false;
				}

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	// do not change this method
	void printReport() {
		System.out.println("-------------- Guests--------------");
		System.out.printf("%-25s%5d%n", "Total guests entered:", guestsEntered);
		System.out.printf("%-25s%5d%n", "Total guests served:", kitchen.guestsServed);
		System.out.printf("%-25s%5d%n", "Guests declined service:", guestQ.size());
		System.out.println("--------- Kitchen -----------");
		System.out.printf("%-25s%5d%n", "Meals left:", kitchen.currentStock);
		System.out.printf("%-25s%s%n", "Closing status", (kitchen.isUnderStock) ? "Under stock" : "Overstock");
		System.out.println("-------------- Diner -------------- ");
		System.out.printf("%-25s%5d%n", "Max Q length", maxQLength);
		System.out.printf("%-25s%,d ms%n", "Diner was open for: ", endTime - startTime);
		System.out.printf("%-25s$%,5d%n", "Income:", kitchen.income);
		System.out.println("-----------------------------------");
	}

	// do not change this method
	void checkAssertions() {
		// following statements will check final numbers and throw assertion error when
		// incorrect
		if (!kitchen.isUnderStock)
			assertTrue(endTime - startTime > 1000);
		if (kitchen.isUnderStock)
			assertTrue(kitchen.currentStock <= 4);
		if (endTime - startTime < 1000)
			assertTrue(kitchen.isUnderStock);
	}
}
