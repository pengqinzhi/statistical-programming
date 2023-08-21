package lab6b;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Movie implements Comparable<Movie>{
	
	String movieName;
	String movieYear;
	List<String> movieGenres  = new ArrayList<>();
	
	Movie (String movieName, String movieYear) {
		this.movieName = movieName;
		this.movieYear = movieYear;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(movieName, movieYear);	
	}
	
	@Override
	public boolean equals(Object o) { 
		if (o == null) return false;  	
		if (this == o) return true;	
		if (this.getClass() != o.getClass()) return false; 
		Movie m = (Movie) o; 
		return this.movieName.equalsIgnoreCase(m.movieName) 
				&& this.movieYear.equalsIgnoreCase(m.movieYear); 
	}
	
	@Override
	public int compareTo(Movie m) {
		return this.movieName.compareTo(m.movieName);
	}

}
