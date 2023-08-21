package practice8;

public class Bird extends Pet {
	static int birdCount;
	
	@Override
	String talk() {
		birdCount ++;
		super.petCount ++;
		return "Tweet...";
	}

}
