//Qinzhi Peng, qinzhip
package finals;

public class Vehicle {
	
	static int vehicleCount;
	int id;
	
	Vehicle() {
		vehicleCount++;
		id = vehicleCount;
	}
	
	boolean joinVehicleQ() {
		Road.vehicleQ.offer(this);
		return true;
	}

}
