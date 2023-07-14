package main;

/**
 * RaceCar class keeps track of the location, damage status, and stats of an individual race car
 * @author Connor Zawacki
 *
 */
public class RaceCar extends Car{

	/**
	 * Default race car stats
	 */
	public RaceCar() {
		super();
		speed = 40;
		strength = 3;
		
	}
	
	/**
	 * constructor for custom race car
	 * to note, cars start out undamaged
	 * @param speed between 30 and 55
	 * @param strength between 2 and 4
	 */
	public RaceCar(int speed, int strength) {
		super(speed, strength);
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
	 * returns car in form "TypeofCarSpeed/Strength"
	 */
	@Override
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
