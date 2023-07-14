package main;

/**
 * Pretty much the same as RaceCar class, but with different constructors
 * Would have just used inheritance, but the PA said no :(
 * @author Connor Zawacki
 *
 */
public class FormulaOne extends Car {

	
	/**
	 * Constructor for a default FormulaOne car
	 */
	public FormulaOne() {
		super();
		speed = 50;
		strength = 4;
	}
	
	/**
	 * Constructor for a custom FormulaOne car
	 * @param speed desired speed stat between 30 and 70
	 * @param strength desired strength stat between 3 and 5
	 */
	public FormulaOne(int speed, int strength) {
		super(speed, strength);
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
	 * returns string in format "CarTypeSpeed/Strength"
	 */
	@Override
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
