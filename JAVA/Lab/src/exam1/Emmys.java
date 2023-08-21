// Qinzhi Peng qinzhip
package exam1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Emmys {

	String[] filedata; // used to store rows of data read from the data file

	String[] series; // names of series that received awards
	String[] networks; // names of networks e.g. HBO on which series was aired
	int[][] awardTable; // table that has rows for series and columns for networks.
						// Each cell [i,j] will show number of awards for i'th series on j'th network
						// i and j are as per their respective indices in series and networks arrays.

	// do not change this method
	public static void main(String[] args) {
		Emmys emmys = new Emmys();
		emmys.filedata = emmys.loadData("awards.csv");
		emmys.networks = emmys.getNetworks();
		emmys.series = emmys.getSeries();
		emmys.awardTable = emmys.buildAwardTable();
		emmys.printAwardTable();
	}

	/**
	 * loadData() takes file name and reads each row as an element in an array of
	 * Strings. It returns that array.
	 * 
	 * @param filename
	 * @return
	 */
	String[] loadData(String filename) {
		StringBuilder fileContent = new StringBuilder();
		try (Scanner fileScanner = new Scanner(new File(filename));) {
			while (fileScanner.hasNextLine()) {
				fileContent.append(fileScanner.nextLine().trim() + "\n");
			}

			filedata = fileContent.toString().split("\n");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return filedata;
	}

	/**
	 * getNetworks returns an array of unique network names read from filedata
	 * 
	 * @return
	 */
	String[] getNetworks() {
		StringBuilder uniquenetwork = new StringBuilder();
		for (int i = 0; i < filedata.length; i++) {
			for (int j = 0; j < filedata[i].length(); j++) {
				String[] line = filedata[i].split(",");
				if (!uniquenetwork.toString().contains(line[4])) {
					uniquenetwork.append(line[4] + ",");
				}
			}

		}
		String[] net;
		net = uniquenetwork.toString().split(",");
		String[] result = new String[net.length];
		for (int i = 0; i < net.length; i++) {
			result[i] = net[i].trim();
		}
		return result;
	}

	/**
	 * getSeries returns an array of unique series names read from filedata
	 * 
	 * @return
	 */
	String[] getSeries() {
		StringBuilder uniqueseries = new StringBuilder();

		for (int i = 0; i < filedata.length; i++) {
			for (int j = 0; j < filedata[i].length(); j++) {
				String[] line = filedata[i].split(",");
				if (!uniqueseries.toString().contains(line[3])) {
					uniqueseries.append(line[3] + ",");
				}
			}
			series = uniqueseries.toString().split(",");
		}
		return series;

	}

	/**
	 * buildWardTable() builds a matrix of total awards for a series on a network.
	 * The rows represent the series and columns represent the network.
	 * 
	 * @return
	 */
	int[][] buildAwardTable() {
		awardTable = new int[series.length][networks.length];
		for (int i = 0; i < filedata.length; i++) {
			String[] words = filedata[i].split(",");
			for (int j = 0; j < series.length; j++) {
				String serie = words[3].trim();
				if (serie.equalsIgnoreCase(series[j])) {
					for (int k = 0; k < networks.length; k++) {
						String network = words[4].trim();
						if (network.equalsIgnoreCase(networks[k])) {		
							awardTable[j][k]++;
						}
					}
				}
			}
		}
		return awardTable;
	}

	// do not change this method
	void printAwardTable() {
		System.out.println("************* EMMY Award Winners 2021 *************");
		System.out.printf("%45s", " ");
		for (String s : networks)
			System.out.printf("%10s\t", s); // print top row of networks
		System.out.println();
		int count = 0;
		for (int i = 0; i < awardTable.length; i++) { // for each series
			System.out.printf("%2d. %-45s", ++count, series[i]); // print series name
			for (int j = 0; j < awardTable[i].length; j++) { // print no. of awards
				System.out.printf("%s\t\t", (awardTable[i][j] == 0 ? "-" : awardTable[i][j]));
			}
			System.out.println();
		}
	}
}
