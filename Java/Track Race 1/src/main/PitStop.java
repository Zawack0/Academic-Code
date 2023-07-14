package main;

/**
 * The pitstop class repairs damaged car and formula one objects. In doing so it skips 2 of their "turns"
 * I purposefully omitted a .equals() method because there should never be a case where more than one pitstop is constructed
 * @author Owner
 *
 */


public class PitStop {
	private RaceCar[] stopped_car;
	private FormulaOne[] stopped_formula;
	private int[] car_time; // here just to keep track of how long each car has been in the pit
	private int[] formula_time;
	private TrackLoggerB logger;
	
	
	/**
	 * PitStop constructor. Would have loved to simplify things and just have one array of objects for the pit instead of one for formulas and one for cars, but PA said no
	 * @param car_drivers number of race cars in race
	 * @param formula_drivers number of formulas in race
	 */
	public PitStop(int car_drivers, int formula_drivers, TrackLoggerB log) {
		stopped_car = new RaceCar[car_drivers];
		car_time = new int[car_drivers];
		stopped_formula = new FormulaOne[formula_drivers];
		formula_time = new int[formula_drivers];
		logger = log;
	}
	
	/**
	 * This constructor is overloaded purely for the purpose of Junit testing. Notice it is identical only without the logger
	 */
	public PitStop(int car_drivers, int formula_drivers) {
		stopped_car = new RaceCar[car_drivers];
		car_time = new int[car_drivers];
		stopped_formula = new FormulaOne[formula_drivers];
		formula_time = new int[formula_drivers];
	}
	
	/**
	 * adds a car to the pit and begins its timer at 1.
	 * @param car is the damaged race car added
	 */
	public void enterPitStop(RaceCar car) { 
		for (int i = 0; i<stopped_car.length; i++) {
			if (stopped_car[i]==null) {
				stopped_car[i] = car;
				car_time[i] = 1;
				if (logger!=null) { // this line is a necessary evil for JUnit testing. Will never be relevant outside of testing
				logger.logEnterPit(car);
				}
				return;
			}
		}
	}
	
	/**
	 * adds a formulaOne to the pit and begins its timer at 1.
	 * @param formula is the damaged FormulaOne added
	 */
	public void enterPitStop(FormulaOne formula) { 
		for (int i = 0; i<stopped_formula.length; i++) {
			if (stopped_formula[i]==null) {
				stopped_formula[i] = formula;
				formula_time[i] = 1;
				if (logger!=null) { // this line is a necessary evil for JUnit testing. Will never be relevant outside of testing
				logger.logEnterPit(formula);
				}
				return;
			}
		}
	}
	
	/**
	 * As opposed to needing to keep track of entering, exiting, and timing in the RaceTrack method, it can all be done here
	 * Timers begin at 1, and increments by 1 each tick, meaning that skipping 2 turns (2 and 3) will leave the car exiting at 4
	 * because stopped and time use the same indexes, we can reference them both here for the removal of a car/formula
	 */
	public void tick() {
		for (int i = 0; i<car_time.length; i++) {
			if (car_time[i]>0) {
				car_time[i]++;
				if (car_time[i] == 3) {
					(stopped_car[i]).Repair();
					logger.logExitPit(stopped_car[i]);
					(stopped_car[i]).move();
					car_time[i] = 0; // resets the time for use by another car
					stopped_car[i] = null; // resets the slot for use by another car
				}
			}
		}
		for (int i = 0; i<formula_time.length; i++) {
			if (formula_time[i]>0) {
				formula_time[i]++;
				if (formula_time[i] == 3) {
					(stopped_formula[i]).Repair();
					logger.logExitPit(stopped_formula[i]);
					(stopped_formula[i]).move();
					formula_time[i] = 0; // resets the time for use by another formula
					stopped_formula[i] = null; // resets the slot for use by another formula
				}
			}
		}
	}
	
	/**
	 * Tests if the passed car or formula is in the pit currently
	 * useful for the tick method in racetrack
	 * @param vehicle is either a car or formula
	 * @return true if the car is in the pit
	 */
	public boolean InPit(RaceCar car) {
		for (int i = 0; i<stopped_car.length; i++) {
			if (car.equals(stopped_car[i])) {
				return true;
			}
		}
		return false;
	}
	
	public boolean InPit(FormulaOne formula) {
		for (int i = 0; i<stopped_formula.length; i++) {
			if (formula.equals(stopped_formula[i])) {
				return true;
			}
		}
		return false;
	}
	/**
	 * returns a string representation of the PitStop
	 * not used in PA, but its good practice to create a toString method
	 */
	public String toString() {
		String return_str = "The following are in the pit:";
		for (int i = 0; i<stopped_car.length; i++) {
			if (stopped_car[i]!=null) {
				return_str += (stopped_car[i].toString() + " ");
			}
		}
		for (int i = 0; i<stopped_formula.length; i++) {
			if (stopped_formula[i]!=null) {
				return_str += (stopped_formula[i].toString() + " ");
			}
		}
		return return_str;
	}
	
}
