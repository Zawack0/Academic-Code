/**
 * Floor class is responsible for storing an array of person occupants and has a number identifying which floor it is
 * Known Bugs: None
 * 
 * @author Connor Zawacki
 * connorzawacki@brandeis.edu
 * January 27, 2022
 * COSI 21A PA0
 */
package main;

public class Floor {
	public Person[] occupants;
	public int occupantNum;
	public int floornumber;
	
	/**
	 * constructor for floor defining its 2 pieces of relevant info
	 * @param FloorNumb 0 based index of floor number
	 * @param FloorSpace /** size of the array of people the floor holds. will likely have null spots on the tail end
	 */
	public Floor(int FloorNumb, int FloorSpace) {
		occupants = new Person[FloorSpace];
		floornumber = FloorNumb;
		occupantNum = 0;
	}
	
	/**
	 * iterates through the occupants of the floor until an empty spot in the array is found, and places the person in the spot
	 * @param person is the person that just entered the floor
	 */
	public void enterFloor(Person person) {
		boolean flag = true;
		int i=0;
		while (flag==true) {
			if (occupantNum==occupants.length) { /** this is a horribly inefficient way of expanding the array, especially since we can't import java.util to just copy it over*/
				Person[] transfer = occupants;
				occupants = new Person[occupantNum*2];
				for (int j = 0; j<occupantNum; j++) {
					occupants[j]=transfer[j];
				}
			}
			if (occupants[i]==null) {
				occupants[i]=person;
				occupantNum++;
				flag=false;
			}
			i++;
		}
	}
	public String toString() {
		if(floornumber==0) {
			return("Lobby: \nTotal occupants: " + occupantNum);
		}
		return("Floor Number: " + floornumber + "\nTotal occupants: " + occupantNum);
	}
}
