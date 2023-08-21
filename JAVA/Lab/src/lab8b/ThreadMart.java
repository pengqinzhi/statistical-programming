package lab8b;

public class ThreadMart {
	
	/** Create a new Shop, 
	 * assign it to a new Thread, and start it. 
	 * Then wait for child thread to join back. 
	 * Finally print the Total wait time and Average wait time as shown.
	 */
	public static void main(String[] args) {
		System.out.println("*** Welcome to ThreadMart ***");
		Shop s = new Shop();
		Thread thread = (new Thread(s));
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.printf("***Total wait time %,d ms. Average wait time %,d ms", Shop.totalQueueTime, Shop.totalQueueTime / Shop.customersServed);
	}
	
	static String spacer(int id) {  //id is the sales counter id
        StringBuilder result = new StringBuilder();
        for (int i=1; i <= id; i++) {
            result.append("\t");
        }
        return result.toString();
    }
	
}
