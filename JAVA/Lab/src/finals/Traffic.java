//Qinzhi Peng, qinzhip
package finals;

import java.util.Random;

public class Traffic implements Runnable {

	static final int MIN_VEHICLE_DELAY = 5;
	static final int MAX_VEHICLE_DELAY = 10;
	static int maxQLength;

	@Override
	public void run() {
		Vehicle v;
		boolean isJoined;
		Random rand = new Random();

		while (Vehicle.vehicleCount < Road.maxVehicles) {
			if (Road.problemPart == 2 && rand.nextInt(4) == 1) {
				v = new ImpatientVehicle();
			} else {
				v = new Vehicle();
			}
			
			synchronized (Road.vehicleQ) {
				isJoined = v.joinVehicleQ();
				maxQLength = Math.max(maxQLength, Road.vehicleQ.size());
				if (isJoined == false) {
					Road.vehiclesExited++;
				}
			}

			if (TrafficLight.isGreen == false && isJoined == true) {
				if (! (v instanceof ImpatientVehicle)) {
					System.out.printf("    RED: Vehicle %d in Q. Q length %d\n", v.id, Road.vehicleQ.size());	
				} else {
					System.out.printf("    RED: ImpatientVehicle %d in Q. Q length %d\n", v.id, Road.vehicleQ.size());
				}
			}

			try {
				Thread.sleep(rand.nextInt(MAX_VEHICLE_DELAY - MIN_VEHICLE_DELAY + 1) + MIN_VEHICLE_DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
