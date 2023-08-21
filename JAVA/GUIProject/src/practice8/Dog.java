package practice8;

public class Dog extends Pet {
	static int dogCount;
	
	@Override
	String talk() {
		dogCount ++;
		super.petCount ++;
		return "Bark...";
	}

}
