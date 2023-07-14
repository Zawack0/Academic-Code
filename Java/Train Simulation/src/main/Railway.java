/**
 * Class responsible for the running of the redline. The red line consists of a double linked list of stations and an array of their names
 * most of class builds up to the "simulate" method, which is supposed to proc stations one by one (N to S order) to move a train from their north Q north 
 * then a train from their south Q south. see Known Bugs below
 * 
 * Known Bugs: After some input testing, was able to determine that simulate is causing departure to be suppressed. Can't quite figure out why. Worth noting
 * is that the code as a whole can correctly initiate stations and their data fields, there is likely just one small logic flaw that I was unable to find
 * causing people to "never leave", and therefore messing up the output. 
 * 
 * @author Connor Zawacki
 * connorzawacki@brandeis.edu
 * March 4th, 2022
 * COSI 21A PA1
 */
package main;

public class Railway {

	public DoubleLinkedList<Station> railway;
	public String[] stationNames;
	public int index = 0;
	public final int MAX_ENTRIES = 18;
	public int simulated = 1;
	
	public Railway() {
		railway = new DoubleLinkedList<Station>();
		stationNames = new String[MAX_ENTRIES];
	}
	
	public void addStation(Station s) {
		railway.insert(s);
		stationNames[index]=s.name;
		index++;
		
	}
	
	/**
	 * given the names of 2 stations, determines which is more north
	 * @param s1 is the name of one station to be compared
	 * @param s2 is the name of the other
	 * @return the name of the station farther north
	 * ASSSUMES that both strings passed are names of stations in railway
	 * runs in linear time (scales linearly with size of doublelinkedlist railway
	 */
	public String moreNorth(String s1, String s2) {
		if (s1.equals(s2)) {
			return s1;
		}
		Node<Station> v=railway.head;
		boolean flag = true;
		int local1 = -1;
		int local2 = -1;
		while(flag==true){
			if (v.Data.name.equals(s1)) {
				local1 = v.local;
			}
			if (v.Data.name.equals(s2)) {
				local2 = v.local;
			}
			v=v.Next;
			if (local1 !=-1 && local2!=-1) {
				flag = false;
			}
		}
		if (local1<local2) {
			return s1;
		}
		else {
			return s2;
		}
	}
	/**
	 * sets rider direction and adds them to appropriate railway
	 * @param r
	 * runs in linear time because of the get and setRiderDirection methods (O(n), scales linearly with size of the railway doubly linked list)
	 */
	public void addRider(Rider r) {
		setRiderDirection(r);
		Station finder = new Station(r.getStarting()); /**allows us to use the get method on the linked list to obtain the correct station*/
		railway.get(finder).addRider(r);
		
	}
	
	/**
	 * adds train to appropriate station based on starting station
	 * @param t is the train to be added
	 * needs to traverse the doubly linked list with the get method, so runs in linear time
	 */
	public void addTrain(Train t) {
		Station finder = new Station(t.local); /**allows us to use the get method on the linked list*/
		railway.get(finder).addTrain(t);
	}
	
	/**
	 * Sets the rider's direction based off starting and destination stations
	 * @param r 
	 * ASSUMES rider's destination and start are both in the station doubly linked list, and are different
	 * runs in linear time, (O(n), scales linearly with size of the railway doubly linked list)
	 */
	public void setRiderDirection(Rider r) {
		if (moreNorth(r.getStarting(), r.getDestination()).equals(r.getStarting())) {
			r.setDirection(false);
		}
		else {
			r.setDirection(true);
		}
	}
	
	/**
	 * runs a single "simulation" of the Railway, from north to south, logs events and stations, moving and disembarking passengers along the way
	 * @returns a string log of activity representing each station, train movement, and disembarked passengers.
	 * Runs in O(n*(x*y)) where n is the railway double linked list size, x is the number of eligible passengers and y is TOTAL_PASSENGERS
	 */
	public String simulate() {
		String log=" ------ "+simulated+" ------ \n";
		Node<Station> v = railway.getFirst();
		Train T;
		while (v!=null) {/**O(n) where n is size of railway linked list*/
			Station current = v.getData();
			log+=v.toString()+"\n\n";
				if(current.northBoundTrains.size()!=0&&current.name.equals("Alewife")!=true){
					T = current.northBoardTrain(); /** runtime x based on number of eligible passengers*/
					T.updateStation(v.getPrev().Data.name);
					v.getPrev().Data.addTrain(T);
					log+=T.local+" Disembarking Passengers:\n"+T.toString()+"\n\n";
				}
				if(current.southBoundTrains.size()!=0&&current.name.equals("Braintree")!=true){
					T = current.southBoardTrain();
					T.updateStation(v.getNext().Data.name);
					v.getNext().Data.addTrain(T);
					log+=T.toString()+"\n\n";
				}
			
				if (current.name.equals("Braintree")&&current.southBoundTrains.size()!=0) {
					current.moveTrainSouthToNorth();
				}
				else {
					if (current.name.equals("Alewife")&&current.northBoundTrains.size()!=0) {
						current.moveTrainNorthToSouth();
					}
				}
			
			
			v=v.getNext();
		} /** While loop is nx + ny, making it O(max(nx + ny)) */
		simulated++;
		return log;
	}
	
	@Override
	/**
	 * provides a string listing all names of stations that are a part of the railway
	 * runs in linear time, scaling linearly with "index" (scales linearly with number of names in list)
	 */
	public String toString() {
		String dummy = "The follwoing stations are in the railway:\n";
		for (int i=0; i<index; i++) {
			dummy +=stationNames[i]+"\n";
		}
		return dummy;
	}
}
