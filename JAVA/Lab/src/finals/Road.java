//Qinzhi Peng, qinzhip
package finals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Road implements Runnable {

	static Queue<Vehicle> vehicleQ = new LinkedList<>();
	static int maxVehicles;
	static int vehiclesPassed;
	static int vehiclesExited;
	static int problemPart;
	static long startTime;
	static long endTime;

	public static void main(String[] args) {
		Road road = new Road();
		road.startRoad();
		road.printReport();
		road.checkAssertions();
	}

	void startRoad() {
		Scanner input = new Scanner(System.in);
		System.out.println("Part 1 or 2?");
		problemPart = input.nextInt();
		input.nextLine();
		System.out.println("How many vehicles?");
		maxVehicles = input.nextInt();
		input.nextLine();
		input.close();

		Road road = new Road();
		Traffic traffic = new Traffic();
		TrafficLight trafficLight = new TrafficLight();

		Thread t = new Thread(road);
		Thread t1 = new Thread(traffic);
		Thread t2 = new Thread(trafficLight);

		startTime = System.currentTimeMillis();

		t.start();
		t1.start();
		t2.start();

		try {
			t.join();
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	void printReport() {
		System.out.println("-----------TRAFFIC REPORT---------------------------");
		System.out.printf("The program ran for %d ms\n", endTime - startTime);
		System.out.printf("Max Q length at traffic light was %d\n", Traffic.maxQLength);
		System.out.printf("Final Q length at traffic light was %d\n", vehicleQ.size());
		System.out.printf("Vehicles passed: %d\n", vehiclesPassed);
		System.out.printf("Vehicles exited: %d\n", vehiclesExited);
	}

	void checkAssertions() {
		assertEquals(maxVehicles, vehiclesPassed + vehiclesExited + vehicleQ.size());
		assertTrue(Traffic.maxQLength >= vehicleQ.size());
		assertTrue(Vehicle.vehicleCount == maxVehicles);
	}

	@Override
	public void run() {
		Vehicle v;
		while (Vehicle.vehicleCount < Road.maxVehicles) {
			if (TrafficLight.isGreen == true) {
				synchronized (vehicleQ) {
					v = vehicleQ.poll();

					if (v != null) {
						if (! (v instanceof ImpatientVehicle)) {
							System.out.printf("Green: Vehicle %d passed. Q length: %d\n", v.id, Road.vehicleQ.size());
						} else {
							System.out.printf("Green: ImpatientVehicle %d passed. Q length: %d\n", v.id,
									Road.vehicleQ.size());
						}
						vehiclesPassed++;
					}
				}
			}
		}

		endTime = System.currentTimeMillis();
	}

}
