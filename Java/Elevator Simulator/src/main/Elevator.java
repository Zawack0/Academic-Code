/**
 * Elevator class is responsible for the output and moving people to where they need to go
 * Holds an array of current jobs, a reference to the building it is in, and several useful int counter variables
 * Known Bugs: None
 * 
 * @author Connor Zawacki
 * connorzawacki@brandeis.edu
 * January 27, 2022
 * COSI 21A PA0
 */
package main;

public class Elevator {

	/**
	 * This specifies the number of people that can be brought to their floors by an Elevator 
	 * instance at any given time. 
	 * <p>DO NOT REMOVE THIS FIELD</p>
	 * <p>You may edit it. But keep it public.</p>
	 */
	public static int maxOccupants = 3;
	public int currentOccupants; /** a variable to compare to maxOccupants to determine if elevator is full*/
	public Job[] jobs;
	public int local; /** helpful location storage variable */
	public int jobsDone; /** a solution to determine where in the job array the elevator is currently*/
	public Building Building;
	
	public Elevator(Building building) {
		local = 0;
		jobs = new Job[maxOccupants+1];
		jobsDone = 0;
		currentOccupants = 0;
		Building = building;
	}
	
	public void createJob(Person person, int floor) {
		for (int i = 0; i<=jobs.length; i++) {
			if (jobs[i]==null) {
				jobs[i]=new Job(person, Building.floors[floor]);
				return;
			}
		}
	}
	
	/**
	 * processes all jobs in the cue and then returns elevator to lobby
	 * by processing a null job
	 */
	public void processAllJobs() {
		int j = 0;
		boolean flag = false;
		while (flag==false) {
			if (jobs[j]==null) {
				flag=true;
			}
			else {
				processJob(jobs[j]);
				jobs[j]=null;
			}
			j++;
		}
		processJob(null);
	}
	
	/**
	 * Moves elevator to the the applicable floor number
	 * This method does print, but still conforms to the PA requirements because it is only ever called in the processJob method
	 * (I was assured by a TA that this was permitted)
	 * @param target is the floor # that the elevator is moving to
	 */
	public void moveTo(int target) {
		if (local==0) {
			System.out.println("Elevator at Lobby");
		}
		else {
			System.out.println("Elevator at floor " + (local));
		}
		while (local!=target) {
			if (local<target) {
				local++;
				System.out.println("Elevator at floor " + (local));
			}
			else {
				local--;
				if (local==0) {
					System.out.println("Elevator at Lobby");
				}
				else {
					System.out.println("Elevator at floor " + (local));
				}
			}
		}
		
	}
	
	/**
	 * responsible for completing the passed job. Also is the
	 * method responsible for all console output. If null is passed the job moves the elevator to lobby
	 * @param job is the applicable job that needs to be completed
	 */
	public void processJob(Job job) {
		if (job!=null) {
		moveTo(job.Target.floornumber);
		exit(job.person, job.Target.floornumber);
		}
		else {
			moveTo(0);
		}
	}
	
	/**
	 * method that removes a person from the elevator and puts them onto the floor
	 * @param person is the person to be moved
	 * @param floor is the place the person will be moved to
	 */
	public void exit(Person person, int floor) {
		person.location = floor + 1; /** floors are 0 based index with 0 being lobby so +1 is required*/
		currentOccupants -=1; /** 1 person leaves the elevator*/
		Building.enterFloor(person, floor);
	}
	public String toString() {
		return("beep boop i am an elevator. I have completed " + jobsDone + " jobs out of " + jobs.length + "possible jobs today!" );
	}
}