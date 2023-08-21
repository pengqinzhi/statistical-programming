//Qinzhi Peng, qinzhip
package lab7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * GrammyAnalyst takes Grammys.txt to provide two reports and one search
 * functionality
 */
public class GrammyAnalyst {
	/** initialize these member variables with appropriate data structures **/
	List<Nomination> nominations;
	Map<String, List<Nomination>> grammyMap;
	List<Artist> artists;

	public static void main(String[] args) {
		GrammyAnalyst ga = new GrammyAnalyst();
		ga.loadNominations();
		ga.loadGrammyMap();
		System.out.println("*********** Grammy Report ****************");
		ga.printGrammyReport();
		System.out.println("*********** Search Artist ****************");
		System.out.println("Enter artist name");
		Scanner input = new Scanner(System.in);
		String artist = input.nextLine();
		ga.searchGrammys(artist);
		ga.loadArtists();
		System.out.println("*********** Artists Report ****************");
		ga.printArtistsReport();
		input.close();
	}

	/**
	 * loadNominations() reads data from Grammys.txt and populates the nominations
	 * list, where each element is a Nomination object
	 */
	void loadNominations() {
		String[] nominationList = null;
		String newCategory;
		String newTitle;
		String newArtist;
		Nomination newNomination;
		nominations = new ArrayList<>();

		File file = new File("Grammys.txt");
		try (Scanner fileScanner = new Scanner(file)) {
			while (fileScanner.useDelimiter("\n").hasNext()) {
				nominationList = fileScanner.nextLine().split(";");
				newCategory = nominationList[0].trim();
				newTitle = nominationList[1].trim();
				newArtist = nominationList[2].trim();
				newNomination = new Nomination(newCategory, newTitle, newArtist);
				nominations.add(newNomination);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * loadGrammyMap uses artist name in lower case as the key, and a list of all
	 * nominations for that artist as its value. Hint: use 'nominations' list
	 * created in previous method to populate this map.
	 */
	void loadGrammyMap() {
		grammyMap = new HashMap<>();
		List<Nomination> artistNomin = null;
		List<String> name = null;

		for (Nomination n1 : nominations) {
			name = new ArrayList<>();
			artistNomin = new ArrayList<>();

			if (!name.contains(n1.artist.toLowerCase())) {
				name.add(n1.artist.toLowerCase());
				for (Nomination n2 : nominations) {
					if (n2.artist.equalsIgnoreCase(n1.artist)) {
						artistNomin.add(n2);
					}
				}
			}
			grammyMap.put(n1.artist.toLowerCase(), artistNomin);
		}
	}

	/**
	 * loadArtists loads the artists array List. Each Artist object in it should
	 * have artist's name in proper case, i.e., as read from data file, and a list
	 * of nominations for that artist. Hint: use 'grammyMap' created in previous
	 * method to populate this list
	 */
	void loadArtists() {
		artists = new ArrayList<>();
		for (String key : grammyMap.keySet()) {
			artists.add(new Artist(grammyMap.get(key).get(0).artist, grammyMap.get(key)));
		}
	}

	/** printGrammyReport prints report as shown in the handout */
	void printGrammyReport() {
		int index = 1;
		Collections.sort(nominations);
		for (Nomination n : nominations) {
			System.out.println(index + ". " + n);
			index++;
		}
	}

	/** printArtistReport prints report as shown in the handout */
	void printArtistsReport() {
		int index = 1;
		Collections.sort(artists);
		for (Artist a : artists) {
			System.out.println(index + ". " + a);
			index++;
		}
	}

	/**
	 * searchGrammys takes a string as input and makes a case-insensitive search on
	 * grammyMap. If found, it prints data about all nominations as shown in the
	 * handout.
	 */
	void searchGrammys(String artist) {
		int flag = 0;
		for (Nomination n : nominations) {
			if (n.artist.toLowerCase().contains(artist.toLowerCase())) {
				System.out.println(n);
				flag = 1;
			}	
		}
		
		if (flag == 0) {
			System.out.println("Sorry! "+ artist + " not found!");
		}
	}

}
