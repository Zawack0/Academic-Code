package main;

/**
 * RaceCar class keeps track of the location, damage status, and stats of an individual race car
 * @author Connor Zawacki
 *
 */
public class RaceCar {
	private int speed;
	private int strength;
	private double location;
	private boolean damaged;

	/**
	 * Default race car stats
	 */
	public RaceCar() {
		speed = 40;
		strength = 3;
		damaged = false;
		location = 0.0;
		
	}
	
	/**
	 * constructor for custom race car
	 * to note, cars start out undamaged
	 * @param speed between 30 and 55
	 * @param strength between 2 and 4
	 */
	public RaceCar(int speed, int strength) {
		if (speed > 55) {
			this.speed = 55;
		}
		else if (speed < 30) {
			this.speed = 30;
		}
		else {
			this.speed = speed;
		}
		if (strength > 4) {
			this.strength = 4;
		}
		else if (strength < 2) {
			this.strength = 2;
		}
		else {
			this.strength = strength;
		}
		damaged = false;
		location = 0.0;
	}
	
	/**
	 * Because damaged is private, we must have a method for when a car crashes
	 */
	public void Collision() {
		damaged = true;
	}
	
	/**
	 * Useful in the pitstop class, just undoes damage to a car
	 */
	public void Repair() {
		damaged = false;
	}
	
	/**
	 * Optimal to define this here as opposed to in the racetrack class in the tick method. 
	 * tests if the car is damaged, then moves the car accordingly
	 */
	public void move() {
		if (damaged == false) {
			location += speed;
		}
		else if ((getPosition() +(speed - (strength*5))%100) >= 75) {
			int laps = ((int) location)/100;
			location = 75.0 + (laps * 100);
		}
		else {
			location += (speed - (strength*5));
		}
	}
	
	/**
	 * Notably different from get position, because this can actually be used to judge if they have finished the race
	 * @return the location in the race
	 */
	public double getLocation() {
		return location;
	}
	
	/**
	 * used in .equals
	 * @returns the car's strength
	 */
	public int getStrength() {
		return strength;
	}
	
	/**
	 * used in .equals()
	 * @returns the car's speed
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * A car can only become damaged through a collision if it is undamaged, so we need this method
	 * @returns true if vehicle is damaged, false if it isn't 
	 */
	public boolean IsDamaged() {
		return damaged;
	}
	
	/**
	 * Gives the car's current position on the track (where on the lap are they)
	 * if past the first lap, the location on the track can be determined by subtracting increments of 100
	 * @returns location, useful for testing collisions, pit stop, etc.
	 */
	public double getPosition() {
		if (location < 100.0 || location >= 1000.0) {
			return location;
		}
		else {
			double position = location;
			while (position >= 100.0) {
				position -= 100.0;
			}
			return position;
		}
	}
	
	/**
	 * returns car in form "TypeofCarSpeed/Strength"
	 */
	public String toString() {
		return ("RaceCar" +speed + "/" + strength);
	}
	
	/**
	 * tests if strength and speed are equal among 2 cars, and returns true if they are
	 * doesn't test for damage or location
	 * Not used in PA because it actually screwed up the scenario where 2 identical cars are racing
	 */
//	public boolean equals(Object vehicle) {
//		if (vehicle instanceof RaceCar) {
//			if (((RaceCar) vehicle).getSpeed() == speed && ((RaceCar) vehicle).getStrength() == strength) {
//				return true;
//			}
//		}
//		return false;
//	}
}
