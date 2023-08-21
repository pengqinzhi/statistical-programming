package practice8;

public class Cat extends Pet {
	static int catCount;	
	
	@Override
	String talk() {
		catCount ++;
		super.petCount ++;
		return "Meow...";
	}

}
