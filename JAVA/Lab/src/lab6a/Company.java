package lab6a;

public class Company implements Comparable<Company>{
	static int overallHiredCount;
	int rank;
	String name;
	int hiredCount;

	@Override
	public int compareTo(Company c) {
		return this.rank - c.rank;
	}
	
}
