package lab6b;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MyFlix {
	List<Movie> moviesList = new ArrayList<>();
	List<Genre> genresList = new ArrayList<>();
	String[] movieDBStrings;

	//do not change this method
	public static void main(String[] args) {
		MyFlix myFlix = new MyFlix();
		myFlix.movieDBStrings = myFlix.readMovieDB("MoviesDB.tsv");
		myFlix.loadMovies();
		myFlix.loadGenres();
		Collections.sort(myFlix.moviesList);
		Collections.sort(myFlix.genresList);
		myFlix.showMenu();
	}

	//do not change this method
	//showMenu() displays the menu for the user to choose from,
	//takes required inputs, and invokes related methods
	void showMenu() {
		Scanner input = new Scanner(System.in);
		int choice = 0;
		System.out.println("*** Welcome to MyFlix ***"); 
		System.out.println("1. Search for a movie");
		System.out.println("2. List of genres");
		System.out.println("3. Exit");
		choice = input.nextInt();
		input.nextLine();
		switch (choice) {
		case 1: {
			System.out.println("Enter the string to search in movie names");
			String searchString = input.nextLine();
			printSearchResults(findMovies(searchString));
			break;
		}
		case 2: {
			printGenreReport();
			break;
		}
		case 3: System.out.println("Bye Bye!"); break;
		default: break;
		}
		input.close();
	}

	//do not change this method
	//readMovieDB reads all data from the MovieDB file
	//and loads each row as a string in movieDBStrings
	String[] readMovieDB(String filename) {
		StringBuilder movies = new StringBuilder();
		try {
			Scanner input = new Scanner(new File(filename));
			while (input.hasNextLine()) {
				movies.append(input.nextLine() + "\n");
			}
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return movies.toString().split("\n");
	}

	//loadMovies use data in movieDBStrings to create Movie objects 
	//and add them to moviesList.
	void loadMovies() {
		for (String m : movieDBStrings) {
			String[] List = m.toString().split("\t");
			moviesList.add(new Movie(List[0].trim(), List[1].trim()));	
		}
		
		int i = 0;
		for (Movie m : moviesList) {
			String[] List = movieDBStrings[i].toString().split("\t");
			for (int j = 0; j < List.length - 2; j++) {	
				m.movieGenres.add(List[j+2].trim());
			}
			i++;
		}
	}

	//loadGenres uses data in moviesList to create Genre objects 
	//and add them to genresList
	void loadGenres() {	
		List<String> name = new ArrayList<>();
		
		for (Movie m : moviesList) {
			for (String mg : m.movieGenres) {
				if (! name.contains(mg)) {
					name.add(mg);
					genresList.add(new Genre(mg));
				}
			}
		}
		
		
		for (Genre g : genresList) {
			for (Movie m : moviesList) {		
				if (m.movieGenres.contains(g.genreName)) {
					g.genreMovies.add(m);
				} 
			}
			
		}
		
	}

	//findMovies() returns a list of Movie objects 
	//that have the searchString in their names
	List<Movie> findMovies(String searchString) {
		List<Movie> finded = new ArrayList<>();
		
		if (searchString.equals(" ")) {
			for (Movie m : moviesList) {			
				finded.add(m);
			}
		}
			
		for (Movie m : moviesList) {
			if (m.movieName.toLowerCase().contains(searchString.toLowerCase())) {
				finded.add(m);
			}
		}
		
		return finded;
	}

	//print the search output. 
	//You may use this printf statement:System.out.printf("%3d. %-50s\tYear: %s\n", ++count, movie.movieName, movie.movieYear);
	void printSearchResults(List<Movie> searchResults) {
		int count = 0;	
		if (searchResults.size() == 0) {
			System.out.println("Sorry! No movie found!");
		}
		
		for (Movie movie : searchResults) {
			System.out.printf("%3d. %-50s\tYear: %s\n", ++count, movie.movieName, movie.movieYear);
		}
		
	}

	//print the genre summary report. 
	//You may use this printf statement:System.out.printf("%3d. %-15s Number of movies: %,6d%n", ++count, genre.genreName, genre.genreMovies.size());
	void printGenreReport() {
		int count = 0;
		for (Genre genre : genresList) {
			System.out.printf("%3d. %-15s Number of movies: %,6d%n", ++count, genre.genreName, genre.genreMovies.size());
		}
	}
}
