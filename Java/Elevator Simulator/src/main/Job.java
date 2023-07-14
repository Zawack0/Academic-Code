/**
 * Job class is useful to represent a request to the elevator including a person and that person's target floor.
 * Fairly simplistic in my personal implementation
 * Known Bugs: None
 * 
 * @author Connor Zawacki
 * connorzawacki@brandeis.edu
 * January 27, 2022
 * COSI 21A PA0
 */
package main;

public class Job {
	public Person person;
	public Floor Target;
	
	public Job(Person patron, Floor targetfloor) {
		person = patron;
		Target = targetfloor;
	}
	
	public String toString() {
		return(person.fname + " " + person.lname + " wants to go to floor " + Target.floornumber);
	}
}