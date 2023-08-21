//Qinzhi Peng, qinzhip
package lab7;

import java.util.List;

public class Artist implements Comparable<Artist>{
	
	String name;
	List<Nomination> nominations;
	
	Artist(String name, List<Nomination> nominations) {
		this.name = name;
		this.nominations = nominations;
	}
	
	@Override
	public int compareTo(Artist a) {
		if (this.nominations.size() == a.nominations.size())
			return this.name.toLowerCase().compareTo(a.name.toLowerCase());
		
		return a.nominations.size() - this.nominations.size();
	}
	
	@Override
	public String toString() {
		String str = this.name + ": " + this.nominations.size();
		return str;		
	}
	
}
