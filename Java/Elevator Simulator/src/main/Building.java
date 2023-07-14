/**
 * Building class is one of the only 2 classes interacted with by client (simulation.java)
 * Holds an instance of elevator and an array of floors in addition to useful int counter variables and an array of potential jobs
 * important to note is the defaultJobs variable. In order to avoid the efficiency of "dynamically expanding" arrays in java, 
 * defaultJobs was constructed in order to provide an array size for several arrays used 
 * Known Bugs: None
 * 
 * @author Connor Zawacki
 * connorzawacki@brandeis.edu
 * January 27, 2022
 * COSI 21A PA0
 */
package main;

public class Building {

	public Floor[] floors;
	public int peoplewaiting; /** integer representing the number of people waiting for the elevator */
	public Job[] waiting; /** jobs of those waiting for the elevator */
	public Elevator elevator;
	public int Numfloors;
	
	/** for the sake of efficiency, number of total jobs should be specified to avoid having to create a new job array for every new job*/
	/** that being said, I did include some logic in the rare case that 50 isn't large enough */
	public static int defaultJobs = 50; 
	
	/**
	 * the default constructor
	 * makes use of default Jobs so as to not have to create a new array for every job
	 * @param numFloors the total number of floors for the building
	 */
	public Building(int numFloors) {
		floors = new Floor[numFloors + 1]; /** floor 0 is the lobby */
		for (int i=0; i<=numFloors; i++) { /** initializes all floors with enough floor space to hold up to all patrons */
			floors[i] = new Floor(i,defaultJobs);
		}
		Numfloors = numFloors;
		elevator = new Elevator(this);
		waiting = new Job[defaultJobs];
	}
	
	/**
	 * Purpose is to ensure no illegal requests are attempted. also updates the "trapped" variable if applicable (see person class)
	 * Increments people waiting in the case where a person is not trapped
	 * @param person
	 * @param floor
	 * @return
	 */
	public boolean enterElevatorRequest(Person person, int floor) {
		if (floor>Numfloors||floor<=0){
			person.trapped = true;
			this.enterFloor(person, 0);
			return false;
		}
		if (peoplewaiting==waiting.length){ /** horribly ineffecient way of expanding the waiting array. Luckily it only ever comes up if you que up more than 50 jobs before starting the elevator*/
			Job[] transfer = waiting;
			waiting = new Job[peoplewaiting*2];
			for (int j = 0; j<peoplewaiting; j++) {
				waiting[j]=transfer[j];
			}
		}
		if (this.isFull()==true) {
			waiting[peoplewaiting]=new Job(person, floors[floor]);
			peoplewaiting++;
			return true;
		}
		waiting[peoplewaiting]=new Job(person, floors[floor]);
		peoplewaiting++;
		return true;
	}
	

	/**
	 * purpose is to process all current possible jobs. Does so in a first come first serve basis
	 * by filling the elevator from those who are waiting, processing their jobs, 
	 */
	public void startElevator() {
		boolean flag = false;
		int counter = 0;
		if (peoplewaiting==0) {
			flag = true;
		}
		while (flag==false) {
			if (this.isFull()==false) {
				elevator.createJob(waiting[counter].person, waiting[counter].Target.floornumber); /**worth noting we don't actually have to remove someone from the waiting array because new people will be created over them in an index according to peoplewaiting counter*/
				peoplewaiting--;
				elevator.currentOccupants++;
				counter++;
			}
			else {
				elevator.processAllJobs();
			}
			if (peoplewaiting==0) {
				if (elevator.currentOccupants!=0) {
					elevator.processAllJobs();
				}
				flag=true;
			}
		}
	}
	
	/**
	 * Removes person from elevator then
	 * Saves a reference of the given person to the provided floor
	 * @param person the person getting off the elevator
	 * @param floor number of the floor the person is going to
	 */
	public void enterFloor(Person person, int floor) {
		floors[floor].enterFloor(person);
	}
	
	/**
	 * Method that tests to see if the elevator can accept additional occupants
	 * @return true if elevator is at max capacity and false if its not
	 */
	public boolean isFull() {
		if (elevator.currentOccupants>=elevator.maxOccupants) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String toString() {
		int totalpatrons = elevator.currentOccupants;
		for (int i =0; i<=floors.length; i++) {
			totalpatrons += floors[i].occupantNum;
		}
		return("This building has a total of "+ totalpatrons + " occupants spread across " + Numfloors + " floors.");
	}
}
