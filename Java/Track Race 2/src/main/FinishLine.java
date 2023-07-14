package main;

/**
 * Finish line class is mostly responsible for ending a race. Incidentally keeps track of what place they are in with the index of the finished array.
 * @author Connor Zawacki
 * I purposefully omitted a .equals method because there is never an instance where more than one finish line should be constructed
 */
public class FinishLine {
	private Car[] finished_car;
	int Racers;

	/**
	 * constructor for finish line
	 * @param drivers is important for testing when the race is finished. 
	 */
	public FinishLine(int car_drivers, TrackLoggerC logger) {
		finished_car = new Car[car_drivers];
		Racers = car_drivers;
	}
	
	
	/**
	 * enters a Car into the finished racers array
	 * tests for the first null value, and places the car into it, this allows for the index to represent the "place" they finished in
	 * @param car
	 */
	public void enterFinishLine(Car car) { 
		for (int i = 0; i<finished_car.length; i++) {
			if (finished_car[i]==null) {
				finished_car[i] = car;
				return;
			}
		}
	}
	

	 /**
	  * Checks if vehicle has already finished the race. useful for tick 
	  * @param vehicle
	  * @return true if vehicle is in the finished array
	  */
	public boolean IsFinished(Car car) {
		for (int i = 0; i<finished_car.length; i++) {
			if (car.equals(finished_car[i])) {
				return true;
			}
		}
		return false;
	}
	

	
	/**
	 * The check to see if the finished array is full. If it is, all racers have completed the race
	 * @return true if race is over
	 */
	public boolean finished() {
		int finishers = 0;
		for (int i = 0; i<finished_car.length; i++) {
			if (finished_car[i]!=null) {
				finishers++;
			}
		}
		if (finishers == Racers) {
			return true;
		}
		return false;
	}
	
	/**
	 * returns a string representation of the FinishLine
	 * not used in PA, but its good practice to create a toString method
	 */
	public String toString() {
		String return_str = "The following are in the pit:";
		for (int i = 0; i<finished_car.length; i++) {
			if (finished_car[i]!=null) {
				return_str += (finished_car[i].toString() + " ");
			}
		}
		return return_str;
	}
}

