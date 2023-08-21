package lab8b;

public class SalesCounter implements Runnable {
	int id; 			//Unique sequential identifier for each sales counter
	static int count;	//Counts SalesCounter objects created so far

	
	/**SalesCounter() increments count, initializes id  */
	SalesCounter() {
		id = count;
		count++;
	}
	
	/** run() runs while isShopOpen is true. It does the following: 
	 * -	Poll next customer 
	 * -	Print the message: "Salescounter0: CustomerX served. Q length: Y " 
	 * -	Sleep for (processingTime x itemsBought) by Customer 
	 * -	Assign current time to Customer’s dequeueTime 
	 * -	Shop.totalQueueTime += dequeueTime – enqueueTime 
	 * -	Increment Shop.customersServed 
	 * */
	@Override
	public void run() {
		String space = null;
		while (Shop.isShopOpen) {
			Customer c;
			synchronized (Shop.customerQ) {
				c = Shop.customerQ.get(this.id).poll();
			}

			if (c != null) {
				space = ThreadMart.spacer(this.id);
				System.out.printf("%sSalescounter%d: Customer%d served. Q length: %d\n", space, this.id, c.id,
						Shop.customerQ.get(this.id).size());
				
				try {
					Thread.sleep(Shop.processingTime * c.itemsBought);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				c.dequeueTime = System.currentTimeMillis();
				Shop.totalQueueTime += (c.dequeueTime - c.enqueueTime);
				Shop.customersServed++;
			}
		}
	}
	
}
