/**
 * Simulation class is a sand box of sorts for running an elevator in a building. Done by creating a building and people and then alternating between 
 * starting the elevator and having more people enter the elevator in different combinations
 * Known Bugs: None
 * 
 * @author Connor Zawacki
 * connorzawacki@brandeis.edu
 * January 27, 2022
 * COSI 21A PA0
 */
package main;

public class Simulation {

	/**
	 * Tests several cases for the elevator, including starting it with no people, starting it with more than max occupants,
	 * starting it with only 1 person, and starting it with 60 people in the building
	 * @param args default
	 */
	public static void main(String[] args) {
		Object s = 2;
		System.out.println(s instanceof String);
		/**
		Person John = new Person("John", "Doe");
		Person Bob = new Person("Bob", "Guy");
		Person Jane = new Person("Jane", "Smith");
		Person Jake = new Person("Jake", "Smith");
		Person Emma = new Person("Emma", "Johanson");
		
		Building Hotel = new Building(7);
		
		Hotel.startElevator();
		John.enterBuilding(Hotel, 3);
		Hotel.startElevator();
		Bob.enterBuilding(Hotel, 2);
		Jane.enterBuilding(Hotel, 7);
		Jake.enterBuilding(Hotel, 7);
		Emma.enterBuilding(Hotel, 1);
		Hotel.startElevator();
		
		Person[] people = new Person[60];
		for (int i= 0; i<people.length; i++) {
			people[i] = new Person("a", "b");
			int floor = (int) (Math.random() * (Hotel.Numfloors));
			people[i].enterBuilding(Hotel, floor);
		}
		Hotel.startElevator();
		*/
	}
}
