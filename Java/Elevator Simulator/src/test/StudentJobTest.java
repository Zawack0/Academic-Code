/**
 * Junit test for the class Job
 * Known Bugs: None
 * 
 * @author Connor Zawacki
 * connorzawacki@brandeis.edu
 * January 27, 2022
 * COSI 21A PA0
 */
package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.*;

class StudentJobTest {
	Building place = new Building(10);
	Person Jane = new Person("Jane", "Smith");
	Person Jake = new Person("Jake", "Smith");
	Person Emma = new Person("Emma", "Johanson");
	
	Job janeJob = new Job(Jane, place.floors[7]);
	Job jakeJob = new Job(Jake, place.floors[9]);
	Job EmmaJob = new Job(Emma, place.floors[1]);

	
	

	/**
	 * because of how small job class is, all methods are easily handled in a single test
	 * note we don't have to test edge cases such as a job being created with an invalid floor number due to how building class is written
	 */
	@Test
	void JobClasstest() {
		assertTrue(janeJob.toString().equals("Jane Smith wants to go to floor 7"));
		assertTrue(jakeJob.toString().equals("Jake Smith wants to go to floor 9"));
		assertTrue(EmmaJob.toString().equals("Emma Johanson wants to go to floor 1"));
	}

}
