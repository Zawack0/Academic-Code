/**
 * A person is the second of 2 classes interacted with by the client. Each person has an identifying name and a target location 
 * that they would like to go to as well as a current location.
 * Known Bugs: None
 * 
 * @author Connor Zawacki
 * connorzawacki@brandeis.edu
 * January 27, 2022
 * COSI 21A PA0
 */
package main;

public class Person {
	public String fname;
	public String lname;
	public int location;
	public int targetFloor;
	public boolean trapped = false; /** if a person's target floor isn't in the building, they will stay "trapped" in the lobby*/
	
	/**
	 * Constructor method
	 * @param firstName new person's first name
	 * @param lastName new person's last name
	 */
	public Person(String firstName, String lastName) {
		fname= firstName;
		lname = lastName;		
	}

	/**
	 * method responsible for introducing a person to the lobby elevator
	 * @param building is the building the person is attempting to enter the elevator of
	 * @param floor is the person's target floor
	 * @return true if the elevator has the ability to take them to the target floor and false if it doesn't
	 */
	public boolean enterBuilding(Building building, int floor) {
		location = 0;
		targetFloor=floor;
		boolean request = building.enterElevatorRequest(this, floor);
		if (request==false) {
			return false;
		}
		return true;
	}
	
	/**
	 * Yields a string representation of the person's location depending on their condition
	 * @return "in lobby" if they will stay in lobby forever, "in floor n" if they have been serviced, and "waiting to be serviced" for all other conditions
	 */
	public String getLocation() {
		if (trapped == true) {
			return("In Lobby");
		}
		if (location+1!=targetFloor) {
			return("Waiting to be serviced");
		}
		return("In Floor " + targetFloor);
		}
	
	public String toString() {
		return("Hello, my name is " + fname + ' ' + lname + " and I'd like to be in floor " + targetFloor + ". I am currently " + getLocation());
	}
	}

