package lab6;

import java.util.ArrayList;
import java.util.List;


public class Actor implements Comparable <Actor>{
	String name;
	List<Nomination> awards  = new ArrayList <> ();
	
	Actor (String name) {
		this.name = name;
	}
	
	
    @Override
    public int compareTo(Actor a) {
        return this.name.compareTo(a.name);
    }

}
