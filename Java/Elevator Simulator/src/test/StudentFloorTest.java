/**
 * Junit test for the class Floor
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

class StudentFloorTest {
	Floor Lobby = new Floor(0, 5);
	Floor first = new Floor(1, 2);

	Person John = new Person("John", "Doe");
	Person Bob = new Person("Bob", "Guy");
	Person Jane = new Person("Jane", "Smith");
	Person Jake = new Person("Jake", "Smith");
	Person Emma = new Person("Emma", "Johanson");
	
	@Test
	void Constructorrtest() {
		assertTrue(Lobby.floornumber==0);
		assertTrue(first.occupantNum==0);
		assertTrue(first.occupants.length==2);
	}
	
	/**
	 * tests both enterFloor and toString
	 */
	@Test
	void enterFloortoStringTest(){
		first.enterFloor(Emma);
		first.enterFloor(Jake);
		first.enterFloor(Jane);
		Lobby.enterFloor(Bob);
		Lobby.enterFloor(John);
		
		
		assertTrue(Lobby.toString().equals("Lobby: \nTotal occupants: 2"));
		assertTrue(first.toString().equals("Floor Number: 1\nTotal occupants: 3"));

	}

}
