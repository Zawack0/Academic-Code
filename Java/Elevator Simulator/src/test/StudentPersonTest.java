/**
 * Junit test for the class Person
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

import main.Building;
import main.Person;

class StudentPersonTest {
	Person John = new Person("John", "Doe");
	Person Bob = new Person("Bob", "Guy");
	Person Jane = new Person("Jane", "Smith");
	Building place = new Building(5);
	
	@Test
	void ConstructorTest() {
		assertTrue(John.fname.equals("John"));
		assertTrue(John.lname.equals("Doe"));
		assertTrue(John.trapped==false);

		assertTrue(Bob.fname.equals("Bob"));
		assertTrue(Bob.lname.equals("Guy"));
		assertTrue(Bob.trapped==false);
		
		assertTrue(Jane.fname.equals("Jane"));
		assertTrue(Jane.lname.equals("Smith"));
		assertTrue(Jane.trapped==false);
	}
	

	/**
	 * below method tests enter Building, getLocation, and toString fairly elegantly
	 */
	@Test
	void enterBuildingGetLocationToStringTest() {
		assertFalse(John.enterBuilding(place, 0));
		assertFalse(Bob.enterBuilding(place, -4));
		assertTrue(Jane.enterBuilding(place, 2));
		assertTrue(John.toString().equals("Hello, my name is John Doe and I'd like to be in floor 0. I am currently In Lobby"));
		assertTrue(Bob.toString().equals("Hello, my name is Bob Guy and I'd like to be in floor -4. I am currently In Lobby"));
		assertTrue(Jane.toString().equals("Hello, my name is Jane Smith and I'd like to be in floor 2. I am currently Waiting to be serviced"));

		
	}

}
