package lab6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class OscarAnalyst {

	List<Nomination> nominations;
	List<Actor> actors;

	Scanner input = new Scanner(System.in);

	// do not change this method
	public static void main(String[] args) {
		OscarAnalyst oscarAnalyst = new OscarAnalyst();
		oscarAnalyst.loadNominations("Oscar.txt");
		oscarAnalyst.loadActors();
		oscarAnalyst.analyze();
	}

	// complete this method to handle input/output
	void analyze() {
		int choice = 0;

		System.out.println("*** Welcome to Oscar Analyzer ***");
		System.out.println("1. Print Actor Nomination Counts");
		System.out.println("2. Search Movie Nominations");
		System.out.println("3. Exit");

		System.out.println("Enter your choice: ");
		choice = input.nextInt();
		input.nextLine(); // clear buffer
		switch (choice) {
		case 1:
			printActorsReport();
			break;
			
		case 2:
			System.out.println("Enter search string");
			String searchString = input.nextLine();
			List<Nomination> oscar = searchMovies(searchString);
			Collections.sort(oscar);
			System.out.printf("%d movies found\n", oscar.size());
			if (oscar.size() != 0) {
				for (int i = 0; i < oscar.size(); i++) {
					System.out.printf("%d. %s: %s nominated for %s role by %s\n", i + 1, oscar.get(i).year,
							oscar.get(i).movie, oscar.get(i).type, oscar.get(i).actor);
				}
			}
			break;
			
		case 3:
			System.exit(0);
			break;
		default:
			break;
		}
	}

	/**
	 * loadNominations() reads data from file, creates Nomination instances for each
	 * row of data, and then populates the nominations ArrayList
	 * 
	 * @param filename
	 */
	void loadNominations(String filename) {
		nominations = new ArrayList<>();
		String[] List = null;
		Scanner fileScanner = null;
		String newYear;
		String newType;
		String newActor;
		String newMovie;
		String newRole;
		Nomination newN;

		File file = new File(filename);
		try {
			fileScanner = new Scanner(file);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (fileScanner.useDelimiter("\n").hasNext()) {
			List = fileScanner.nextLine().split(",");
			newYear = List[0].trim();
			newType = List[1].trim();
			newActor = List[2].trim();
			newMovie = List[3].trim();
			newRole = List[4].trim();
			newN = new Nomination(newYear, newType, newActor, newMovie, newRole);
			nominations.add(newN);
		}
		fileScanner.close();

	}

	/**
	 * loadActors() iterates over nominations list, creates Actor instances for the
	 * actor in a particular Nomination instance, and then populates that Actor's
	 * nominations with the Nomination instances. So for every actor that was
	 * nominated, there is one Actor instance in actors list. This instance has the
	 * list of nominations for that actor
	 */
	void loadActors() {
		actors = new ArrayList<>();
		ArrayList<String> namelist = new ArrayList<>();

		for (Nomination n : nominations) {
			if (! namelist.contains(n.actor.toLowerCase())) {
				namelist.add(n.actor.toLowerCase());
				actors.add(new Actor(n.actor));
			}
		}

		for (Nomination n : nominations) {
			int index = namelist.indexOf(n.actor.toLowerCase());
			actors.get(index).awards.add(n);
		}

	}

	/**
	 * printActorsReport() prints list of Actors sorted in the decreasing order of
	 * number of awards. For actors that got the same number of awards, they are
	 * sorted by name
	 */
	void printActorsReport() {
		Collections.sort(actors, new ActorComparator());
		for (int i = 0; i < actors.size(); i++) {
			System.out.printf("%2d. %-25s %d\n", i + 1, actors.get(i).name, actors.get(i).awards.size());
		}
	}

	/**
	 * searchMovies() returns a list of Oscar instances whose movie names contain
	 * the searchString. The search is case-insensitive
	 * 
	 * @param searchString
	 * @return
	 */
	List<Nomination> searchMovies(String searchString) {
		List<Nomination> oscar = new ArrayList<>();
		for (Nomination n : nominations) {
			if (n.movie.toLowerCase().contains(searchString.toLowerCase())) {
				oscar.add(n);
			}
		}

		return oscar;
	}

	public class ActorComparator implements Comparator<Actor> {
		@Override
		public int compare(Actor a1, Actor a2) {
			if (a2.awards.size() == a1.awards.size()) {
				return a1.name.compareTo(a2.name);
			}

			return a2.awards.size() - a1.awards.size();
		}
	}

}
