/**
 * Class representing a station on the Red Line.  has queues for north and south riders and trains,
 * and a station name
 * Known Bugs: None
 * 
 * @author Connor Zawacki
 * connorzawacki@brandeis.edu
 * March 4th, 2022
 * COSI 21A PA1
 */
package main;

public class Station {

	public Queue<Rider> northBoundRiders;
	public Queue<Rider> southBoundRiders;
	public Queue<Train> northBoundTrains;
	public Queue<Train> southBoundTrains;
	public static final int default_qsize1 = 20; /** in the case of the default constructor we need a value for passenger queue sizes.  PA gives us that it will never be >20 */
	public static final int default_qsize2 = 20; /** in the case of the default constructor we need a value for train queue sizes. PA gives us that it will never be >20*/
	public String name;
	
	/**
	 * default constructor for a station that gives it a name and constructs queues for north and south bound trains and passengers
	 * @param name is the name of the station
	 * runs in constant time
	 */
	public Station(String name) {
		this.name = name;
		northBoundRiders = new Queue<Rider>(default_qsize1);
		southBoundRiders = new Queue<Rider>(default_qsize1);
		northBoundTrains = new Queue<Train>(default_qsize2);
		southBoundTrains = new Queue<Train>(default_qsize2);
	}
	
	/**
	 * constructor that allows user to specify size of maxRider and maxTrain queue size
	 * @param name
	 * @param maxRiders
	 * @param maxTrains
	 * runs in constant time
	 */
	public Station(String name, int maxRiders, int maxTrains) {
		this.name = name;
		northBoundRiders = new Queue<Rider>(maxRiders);
		southBoundRiders = new Queue<Rider>(maxRiders);
		northBoundTrains = new Queue<Train>(maxTrains);
		southBoundTrains = new Queue<Train>(maxTrains);
		
	}
	/**
	 * adds a rider to the correct queue, as long as there is space and they are in the right station
	 * @param r is the rider to be added to the queue
	 * @returns false if the rider is in the wrong station or the rider queue is full
	 * run in constant time
	 */
	public boolean addRider(Rider r) { 
		if (r.getStarting().equals(name)) {/** ensures that the rider is in the right station */
			if (r.goingNorth()==true) {
				if (northBoundRiders.isFull()) {
					return false;
				}
				else {
					northBoundRiders.enqueue(r);
				}
			}
			else {
				if (southBoundRiders.isFull()) {
					return false;
				}
				else {
					southBoundRiders.enqueue(r);
				}
			}
		}
		return false;
	}
	
	/**
	 * moves a train to the current station, disembarks all passengers who belong there, and adds train to queue
	 * @param t is the train to be moved and added
	 * @returns a string representing the results of the movement (all disembark passengers)
	 * SHOULD ONLY BE CALLED IF THERE IS ROOM IN THE TRAIN QUEUE
	 * runs in linear time (grows linearly with TOTAL_PASSENGERS field of train class)
	 */
	public String addTrain(Train t) {
		t.updateStation(name);
		String leaving = t.disembarkPassengers();
		if(t.goingNorth()) {
			northBoundTrains.enqueue(t);
		}
		else {
			southBoundTrains.enqueue(t);
		}
		return(name + " Disembarking Passengers: \n" +leaving);
	}
	
	/**
	 * prepares a southbound train of passengers. takes first train in southbound queue, fills it with as many riders as possible, and returns it
	 * @returns the train now ready for movement
	 * runs in linear time (scales linearly with the number of eligible passengers, up to a max of TOTAL_PASSGENGERS MULTIPLIED BY southBoundTrains.size())
	 */
	public Train southBoardTrain() {
		if (southBoundTrains.size()==0) {
			return null;
		}
		Train T=southBoundTrains.front();
		southBoundTrains.dequeue();
		while (southBoundRiders.size()!=0 && T.hasSpaceForPassengers()) {
			Rider R=southBoundRiders.front();
			southBoundRiders.dequeue();
			T.addPassenger(R);
		}
		return T;
	}
	
	/**
	 * prepares a northbound train of passengers. takes first train in northbound queue, fills it with as many riders as possible, and returns it
	 * @returns the train now ready for movement
	 * runs in linear time (scales linearly with the number of eligible passengers, up to a max of TOTAL_PASSGENGERS MULTIPLIED BY southBoundTrains.size())
	 */
	public Train northBoardTrain() {
		if (northBoundTrains.size()==0) {
			return null;
		}
		Train T=northBoundTrains.front();
		northBoundTrains.dequeue();
		while (northBoundRiders.size()!=0 && T.hasSpaceForPassengers()) {
			Rider R=northBoundRiders.front();
			northBoundRiders.dequeue();
			T.addPassenger(R);
		}
		return T;
	}
	
	/**
	 * switches the direction of the train in front of the northBound queue
	 * runs in constant time
	 */
	public void moveTrainNorthToSouth() {
		Train T=northBoundTrains.front();
		northBoundTrains.dequeue();
		southBoundTrains.enqueue(T);
	}
	
	/**
	 * switches the direction of the train in front of the southBound queue
	 * runs in constant time
	 */
	public void moveTrainSouthToNorth() {
		Train T=southBoundTrains.front();
		southBoundTrains.dequeue();
		northBoundTrains.enqueue(T);
	}
	
	@Override
	/**
	 * constructs and returns a string representation of the station
	 * @return a string including name and status of all queues of a station
	 * runs in constant time
	 */
	public String toString() {
		return("Station: " + name + "\n"+northBoundTrains.size()+" north-bound trains waiting\n" + southBoundTrains.size()+" south-bound trains witing\n"+northBoundRiders.size()+" north-bound passengers waiting\n"+southBoundRiders.size()+" south-bound passengers waiting");
	}
	
	/**
	 * a retriever method for the station's name
	 * @returns the stations name
	 * runs in constant time
	 */
	public String stationName() {
		return name;
	}
	
	@Override
	/**
	 * compares two stations by name to see if they are the same
	 * @returns false if the stations have a different name, true if the passed value has OR IS the name of the station in the format NAME:stationname
	 * runs in constant time
	 */
	public boolean equals(Object o) {
		if ((o instanceof String)==true) {
			String compare = (String) o;
			if (compare.equals("NAME:"+this.name)){
				return true;
			}
		}
		if ((o instanceof Station)!=true) {
			return false;
		}
		Station compare = (Station) o;
		return(compare.name.equals(this.name));
	}
}
