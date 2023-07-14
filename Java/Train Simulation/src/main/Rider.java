/**
 * Rider.java is a class that keeps track of all important data fields related to a single passenger
 * every passenger has an ID, and a current location, a start, a destination, and a direction based on 
 * relative location of destination and starting location.
 * Known Bugs: None
 * 
 * @author Connor Zawacki
 * connorzawacki@brandeis.edu
 * March 4th, 2022
 * COSI 21A PA1
 */
package main;

public class Rider {
	public String ID;
	public String start;
	public String destination;
	public boolean direction; /** direction is true if going north, or false otherwise*/
	public String place;

	public Rider(String riderID, String startingStation, String destinationStation) {
		ID = riderID;
		start = startingStation;
		place= startingStation;
		destination = destinationStation;
	}
	
	/**
	 * sets the direction to based on a given boolean parameter
	 * @param isNorth is true if direction is north, else is false
	 * runs in constant time
	 */
	public void setDirection(boolean isNorth){
		direction = isNorth;
	}
	/**
	 * Retriever method for the starting station
	 * @returns a string representing where the rider starts
	 * runs in constant time
	 */
	public String getStarting() {
		return start;
	}
	
	/**
	 * Retriever method for the destination station
	 * @returns a string representing where the rider wishes to go
	 * runs in constant time
	 */
	public String getDestination() {
		return destination;
	}
	
	/**
	 * Retriever method for the ID
	 * @returns a string representing where the rider's ID
	 * runs in constant time
	 */
	public String getRiderID() {
		return ID;
	}
	
	/**
	 * Essentially a retriever method for the boolean implementation of direction
	 * @returns true if rider is north bound, or false otherwise
	 * runs in constant time
	 */
	public boolean goingNorth() {	
		return direction;
	}
	
	/**
	 * reverses the direction of the rider from north to south or south to north depending on original direction
	 * runs in constant time
	 */
	public void swapDirection() {
		if (direction==false){
			direction = true;
		}
		else {
			direction = false;
		}
	}
	
	@Override
	/**
	 * a generic to string for the rider class
	 * @returns a string representation of a particular rider, including their ID, their start, and their destination
	 */
	public String toString() {
		return("Rider ID: " + ID + ", Comming From: " + start + ", Going To: " + destination);
	}
	
	@Override
	/**
	 * compares Rider to another object via their ID
	 * @returns false if the passed object is not a rider or if the IDs are non matching
	 * runs in constant time
	 */
	public boolean equals(Object s) {
		if ((s instanceof Rider)!=true) {
			return false;
		}
		Rider compare = (Rider) s;
		return(compare.ID.equals(this.ID));
	}
}
