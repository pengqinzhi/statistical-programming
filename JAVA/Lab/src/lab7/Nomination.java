//Qinzhi Peng, qinzhip
package lab7;

public class Nomination implements Comparable<Nomination> {
	

	String category;
	String title;
	String artist;
	
	Nomination(String category, String title, String artist) {
		this.category =  category;
		this.title = title;
		this.artist = artist;
	}
	
	@Override
	public int compareTo(Nomination a) {
		return this.artist.toLowerCase().compareTo(a.artist.toLowerCase());
	}
	
	@Override
	public String toString() {
		String str = this.artist + ": " + this.category + ": " + this.title;
		return str;		
	}


}
