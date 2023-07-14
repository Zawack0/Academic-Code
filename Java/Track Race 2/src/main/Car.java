package main;

public abstract class Car {
	protected int speed;
	protected int strength;
	protected double location;
	protected boolean damaged;
	
	protected Car(int speed_, int strength_) {
		speed = speed_;
		strength = strength_;
		damaged = false;
		location = 0.0;
	}
	
	protected Car() {
		damaged = false;
		location = 0.0;
	}
	
	/**
	 * Because damaged is encapsulated, we must have a method for when a car crashes
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
	 * changes for every subclass, so we just make the toString representation abstract for convinece
	 */
	public abstract String toString();
}
