//Qinzhi Peng, qinzhip
package finals;

public class ImpatientVehicle extends Vehicle {
	
	static final int Q_TOO_LONG_LENGTH = 5;
	
	@Override
	boolean joinVehicleQ() {
		if (Road.vehicleQ.size() >= Q_TOO_LONG_LENGTH) {   
			System.out.printf("********* Red: ImpatientVehicle %d exiting. Q length %d\n", this.id, Road.vehicleQ.size());
			return false;
		} else {
			Road.vehicleQ.offer(this);
			return true;
		}			
	}

}
