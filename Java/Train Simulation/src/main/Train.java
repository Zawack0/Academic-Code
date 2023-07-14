/**
 * Train.java is a class responsible that the maintenance of groups of Riders on the redline at once. Each train
 * has a certain number of passengers, a direction, and a location
 * Known Bugs: Perhaps a small bug with disembark passengers method where return value does not include all necessary passengers. See also Railway.java
 * 
 * @author Connor Zawacki
 * connorzawacki@brandeis.edu
 * March 4th, 2022
 * COSI 21A PA1
 */
package main;

public class Train {

	public static final int TOTAL_PASSENGERS = 10;
	public Rider[] passengers;
	public int passengerIndex; /**originally was used to index, now mostly used for size*/
	public boolean direction; /** True if north, else false*/
	public String local;
	
	/**
	 * constructor for train class, constructs an empty train given a starting location and direction
	 * @param currentStation is the string name of the station the train starts in
	 * @param direction is 0 for north, 1 for south
	 * runs in constant time
	 */
	public Train(String currentStation, int direction) {
		local = currentStation;
		passengers = new Rider[TOTAL_PASSENGERS];
		passengerIndex = 0;
		if (direction==0){/** direction is 0 if north*/
			this.direction = true;
		}
		else if (direction==1) {
			this.direction = false;
		}
			
	}
	
	/**
	 * a retriever method for direction, returns true if train is going north
	 * @returns current value of direction (true for north, false for south)
	 * runs in constant time
	 */
	public boolean goingNorth() {
		return direction;
	}
	
	/**
	 * reverses train direction from north to south or vice versa
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
	
	/**
	 * calls toString method on all passengers on train
	 * @return a string representation of all current passengers
	 * runs in linear time (scales linearly with TOTAL_PASSENGERS, O(n))
	 */
	public String currentPassengers() {
		String dummy = "";
		for (int i = 0; i<TOTAL_PASSENGERS; i++) {
			if (passengers[i]!=null){
				dummy += passengers[i].toString() + "\n";
			}
		}
		return dummy;
	}
	
	/**
	 * tests if a given rider is able to be added to the current train, and does so if possible
	 * @param r is the rider to be added to the train
	 * @returns false if the rider can NOT be added to the train, and true if the rider was successfully added
	 * runs in linear time
	 */
	public boolean addPassenger(Rider r) {
		if (r==null) {
			return false; /**serves a purpose in station.java*/
		}
		if (this.hasSpaceForPassengers()==true && r.goingNorth()==this.goingNorth() && r.getStarting().equals(this.getStation())) {
			int indexx = -1;
			int i = 0;
			while(indexx==-1) {
				if (passengers[i]==null) {
					indexx=i;
				}
				i++;
			}
			passengers[indexx]=r;
			passengerIndex++;
			return true; /**returns true only if there is space, the direction is correct, and the rider's start location is where the train is located*/
		}
		else {
			return false;
		}

	}
	

	/**
	 * tests if the train has any space left for passengers, and returns the result as a boolean value
	 * @returns false if number of passengers is equal to (or greater than) max passenger value, else true
	 * runs in constant time
	 */
	public boolean hasSpaceForPassengers() {
		if (passengerIndex>=TOTAL_PASSENGERS) {
			return false;
		}
		return true;
	}
	
	/**
	 * removes all passengers from the train whose destination is the train's current location
	 * @returns a string representation of all passengers that were removed from the train
	 * runs in linear time (scales linearly with TOTAL_PASSENGERS)
	 */
	public String disembarkPassengers() {
		String dum = "";
		int i = 0; 
		while (i<TOTAL_PASSENGERS) {
			if (passengers[i]!=null){
				if (passengers[i].getDestination().equals(this.getStation())) {
					String leave = passengers[i].toString();
					dum += leave;
					passengers[i].place=this.getStation();
					passengers[i]=null;
					passengerIndex--;
				}
			}
			i++;
		}
		return dum;
		}
	
	/**
	 * updates the name of the current station the train is at to a new station
	 * @param s is the name of the new station
	 * runs in constant time
	 */
	public void updateStation(String s) {
		local = s;
	}
	
	/**
	 * Retriever method for local
	 * @returns the name of the train's current station
	 * runs in constant time
	 */
	public String getStation() {
		return local;
	}
	
	@Override
	/**
	 * constructs a string representation of the train, including location, direction, and total leaving passengers
	 * @returns the aforementioned string representation of the train
	 * runs in constant time
	 */
	public String toString() {
		String helper;
		if (this.goingNorth()==true) {
			helper = "Northbound";
		}
		else {
			helper = "Southbound";
		}
		return("Direction: "+helper+"\nPassengers: "+disembarkPassengers()+"\nCurrent station: " + this.local+"\n");
	}
}
