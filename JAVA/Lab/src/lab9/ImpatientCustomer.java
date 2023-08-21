//Qinzhi Peng, qinzhip
package lab9;

public class ImpatientCustomer extends Customer{
    
	static int impatientCustomerCount;
	
	ImpatientCustomer() {
		impatientCustomerCount ++;	
	}
	
	@Override
	boolean joinQueue(){
		if (MovieHall.customerQueue.size() >= MovieHall.balkQueueLength) {   
			System.out.printf("***ImpateientCustomer%d balked\n", this.id);
			return false;
		} else {
			MovieHall.customerQueue.offer(this);
			System.out.printf("ImpateientCustomer%d joined Q\n", this.id);
		}
		
		return true;
	}
	
}
