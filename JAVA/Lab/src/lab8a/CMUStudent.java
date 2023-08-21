package lab8a;

import java.util.Random;

public class CMUStudent {
	static final int MIN_TALK_TIME = 5;
	static final int MAX_TALK_TIME = 15;
	static int cmuStudentCount;  //total number of students created in the event
	
	int id;  //unique id indicating position in the queue
	int studentTalkTime;

	/** CMUStudent() increments cmuStudentCount, 
	 * initializes id, and
	 * initializes studentTalkTime with a random number 
	 * between MIN_TALK_TIME and MAX_TALK_TIME 
	 */
	CMUStudent(){
		Random rand = new Random();
		cmuStudentCount++;
		id = cmuStudentCount;
		studentTalkTime = rand.nextInt(MAX_TALK_TIME - MIN_TALK_TIME + 1) + MIN_TALK_TIME;
	}

}
