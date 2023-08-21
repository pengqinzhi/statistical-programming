//qinzhip, Qinzhi Peng
package lab8;

import java.util.Random;

public class Guest {
	static int MAX_MEALS = 4;
	
	int placeOrder() {
		Random rand = new Random();
		int meals = rand.nextInt(MAX_MEALS) + 1;
		return meals;
	}

}
