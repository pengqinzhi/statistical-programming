//Qinzhi Peng, qinzhip
package finals;

public class TrafficLight implements Runnable {

	static final int TRAFFIC_LIGHT_DELAY = 100;
	static boolean isGreen = true;

	@Override
	public void run() {
		try {
			while (Vehicle.vehicleCount < Road.maxVehicles) {
				Thread.sleep(TRAFFIC_LIGHT_DELAY);
				if (isGreen == true) {
					isGreen = false;
				} else {
					isGreen = true;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
