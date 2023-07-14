package main;

/**
 * Pretty much the same as RaceCar class, but with different constructors
 * Would have just used inheritance, but the PA said no :(
 * @author Connor Zawacki
 *
 */
public class FormulaOne {
	private int speed;
	private int strength;
	private double location;
	private boolean damaged;
	
	/**
	 * Constructor for a default FormulaOne car
	 */
	public FormulaOne() {
		speed = 50;
		strength = 4;
		damaged = false;
		location = 0.0;
	}
	
	/**
	 * Constructor for a custom FormulaOne car
	 * @param speed desired speed stat between 30 and 70
	 * @param strength desired strength stat between 3 and 5
	 */
	public FormulaOne(int speed, int strength) {
		if (speed > 70) {
			this.speed = 70;
		}
		else if (speed < 30) {
			this.speed = 30;
		}
		else {
			this.speed = speed;
		}
		if (strength > 5) {
			this.strength = 5;
		}
		else if (strength < 3) {
			this.strength = 3;
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
	 * Gives the car's current location on the track
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
	 * returns string in format "CarTypeSpeed/Strength"
	 */
	public String toString() {
		return ("FormulaOne" +speed + "/" + strength);
	}
	
	
	/**
	 * tests if strength and speed are equal among 2 Formula Ones, and returns true if they are
	 * doesn't test for damage or location
	 * Not used in PA because it actually screwed up the scenario where 2 identical cars are racing
	 */
//	public boolean equals(Object vehicle) {
//		if (vehicle instanceof FormulaOne) {
//			if (((FormulaOne) vehicle).getSpeed() == speed && ((FormulaOne) vehicle).getStrength() == strength) {
//				return true;
//			}
//		}
//		return false;
//	}
}
