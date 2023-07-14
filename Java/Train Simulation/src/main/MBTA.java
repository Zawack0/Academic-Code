/**
 * Main class to run simulations of the railway
 * Known Bugs: See Railway.java
 * 
 * @author Connor Zawacki
 * connorzawacki@brandeis.edu
 * March 4th, 2022
 * COSI 21A PA1
 */
package main;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class MBTA {

	public static final int SOUTHBOUND = 1;
	public static final int NORTHBOUND = 0;
	
	static final int TIMES = 6;/** can be changed temporarily for testing, but should be 6*/
	static Railway r;
 
	
	public static void main(String[] args) throws FileNotFoundException {
		r = new Railway();
		initStations("redLine.txt");
		initRiders("riders.txt");
		initTrains("trains.txt");
		runSimulation();
	}
	
	public static void runSimulation() {
		for (int i = 1; i<=TIMES; i++) {
			System.out.println(r.simulate());

		}
	}
	
	public static void initTrains(String trainsFile) throws FileNotFoundException {
		Scanner Trains = new Scanner(new File(trainsFile));
		while (Trains.hasNextLine()) {
			String start = Trains.nextLine();
			String val = Trains.nextLine();
			if (val.equals(String.valueOf(NORTHBOUND))) {
				r.addTrain(new Train(start, NORTHBOUND));
			}
			else if (val.equals(String.valueOf(SOUTHBOUND))) {
				r.addTrain(new Train(start, SOUTHBOUND));
			}
		}
	}
	
	public static void initRiders(String ridersFile)throws FileNotFoundException {
		Scanner Riders = new Scanner(new File(ridersFile));
		while(Riders.hasNextLine()) {
			String ID = Riders.nextLine();
			String start = Riders.nextLine();
			String end = Riders.nextLine();
			r.addRider(new Rider(ID, start, end));
		}
	}
	
	public static void initStations(String stationsFile)throws FileNotFoundException {
		Scanner Stations = new Scanner(new File(stationsFile));
		while(Stations.hasNextLine()) {
			String name = Stations.nextLine();
			r.addStation(new Station(name));
		}
	}
}
