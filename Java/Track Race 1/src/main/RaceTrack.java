package main;

/**
 * RaceTrack class is responsible for the actual running of the race. Keeps track of an array of racers and formula ones, as well as a PitStop and FinishLine
 * most of the class is setup for the run() method, which uses all of the setup and loops through ticks and collisions until race is over
 * Purposefully omitted a .equals() method because we should never construct multiple RaceTrack objects
 */
public class RaceTrack {
	
	private FinishLine finish;
	private PitStop pit;
	private RaceCar[] car_racers;
	private FormulaOne[] formula_racers;
	private int ticks; // used to track score at the end for each finish
	private int place; // used to track what place cars come in. If 2 cars finish in the same tick, it is a tie, and they will have the same place

	/**
	 * DO NOT REMOVE THIS - you should be using this to log this track's events. For more see the assignment PDF / documentation for TrackLoggerB.java
	 */
	private TrackLoggerB logger;
	
	/**
	 * Constructs the logger
	 * IMPORTANT: does NOT construct pit stop or finish line. That is because the way I have them constructed we need to know the number of racers first
	 * also sets the ticks to 0 and place to 1
	 */
	public RaceTrack() {
		logger = new TrackLoggerB(); // DO NOT REMOVE THIS LINE
		ticks = 0;
		place = 1;
	}
	
	/**
	 * Creates the racers arrays that we use for most of the class
	 * @param racecars the  racecar objects in the car_racers
	 * @param formulaOnes the number of formula objects in the formula_racers array
	 * the way I wrote pitstop and finishLine, we need to know how many drivers, so the constructor for those are here too
	 */
	public void setCars(RaceCar[] racecars, FormulaOne[] formulaOnes) {
		car_racers = racecars;
		formula_racers = formulaOnes;
		pit = new PitStop(car_racers.length, formula_racers.length, logger);
		finish= new FinishLine(car_racers.length ,formula_racers.length, logger);
		
	}
	
	/**
	 * Same as method above, only without any FormulaOne objects. Should only be used in a race with no formulaOnes
	 * @param racecars
	 */
	public void setCars(RaceCar[] racecars) {
		car_racers = racecars;
		formula_racers = new FormulaOne[0];
		pit = new PitStop(car_racers.length, 0, logger);
		finish= new FinishLine(car_racers.length, 0, logger);
	}
	
	/**
	 * One "round" or turn or whatever. First moves all non pit non finished cars, then increments pit, then checked if finished
	 * could have checked if finished in the run method, but I find this mildly more convenient
	 * order of operations is important here, pit.tick has the ability to remove a car from the pit, so it needs to be done after normal movement
	 * and checking if cars are finished must be done after the pit.tick, because a car can go from the pit to the finish
	 */
	public void tick() {
		for (int i = 0; i<car_racers.length; i++) {
			if (!pit.InPit(car_racers[i]) && !finish.IsFinished(car_racers[i])) {
				car_racers[i].move();
			}
		}
		for (int i = 0; i<formula_racers.length; i++) {
			if (!pit.InPit(formula_racers[i]) && !finish.IsFinished(formula_racers[i])) {
				formula_racers[i].move();
			}
		}
		
		pit.tick(); // has to be done after other cars move otherwise cars that exited the pit this tick would move twice
		
		for (int i = 0; i<car_racers.length; i++) { // these two for loops have to be done after pit.tick because a car could go from the pit to finish in a tick
			if (car_racers[i].getLocation()>=1000 && !finish.IsFinished(car_racers[i])) {
				finish.enterFinishLine(car_racers[i]);
				logger.logFinish(car_racers[i], place);
				place++;
			}
		}
		for (int i = 0; i<formula_racers.length; i++) {
			if (formula_racers[i].getLocation()>=1000 && !finish.IsFinished(formula_racers[i])) {
				finish.enterFinishLine(formula_racers[i]);
				logger.logFinish(formula_racers[i], place);
				place++;
			}
		}
	}
	
	/**
	 * This feels sloppy, and looks unsightly, but truth be told, it isn't as bad as it looks.
	 * In order for 2 cars to collide, they have to be different cars (1) in the same spot (2) not in the pit (3+4) not finished (5+6) meaning we have to test for 6 conditions
	 * we have to compare RaceCars to RaceCars, RaceCars to FormulaOnes, and FormulaOnes to FormulaOnes
	 */
	public void checkCollision() {
		for (int i = 0; i<car_racers.length; i++) {
			for (int j = 0; j<car_racers.length; j++) { // we loop through car racers twice to test if any are colliding. See collision criteria above
				if (car_racers[i].getPosition() == car_racers[j].getPosition() && !pit.InPit(car_racers[i]) && !finish.IsFinished(car_racers[i]) && !pit.InPit(car_racers[j]) && !finish.IsFinished(car_racers[j]) && i!=j) { // all six collision criteria tested for
					if (car_racers[i].IsDamaged() == false) {
						car_racers[i].Collision();
						logger.logDamaged(car_racers[i]);
					}
					if (car_racers[j].IsDamaged() == false) {
						car_racers[j].Collision();
						logger.logDamaged(car_racers[j]);
					}
				}
			}
			for (int j = 0; j<formula_racers.length; j++) { // repeat the above process except comparing cars vs formulas
				if (car_racers[i].getPosition() == formula_racers[j].getPosition() && !pit.InPit(car_racers[i]) && !finish.IsFinished(car_racers[i]) && !pit.InPit(formula_racers[j]) && !finish.IsFinished(formula_racers[j])) { // 5 conditions tested because i==j no longer matters
					if (car_racers[i].IsDamaged() == false) {
						car_racers[i].Collision();
						logger.logDamaged(car_racers[i]);
					}
					if (formula_racers[j].IsDamaged() == false) {
						formula_racers[j].Collision();
						logger.logDamaged(formula_racers[j]);
					}
				}
			}
		}
		for (int i = 0; i<formula_racers.length; i++) {
			for (int j = 0; j<formula_racers.length; j++) { // we loop through Formula racers twice to test if any are colliding. See collision criteria above
				if (formula_racers[i].getPosition() == formula_racers[j].getPosition() && !pit.InPit(formula_racers[i]) && !finish.IsFinished(formula_racers[i]) && !pit.InPit(formula_racers[j]) && !finish.IsFinished(formula_racers[j]) && i!=j) { // all six collision criteria tested for
					if (formula_racers[i].IsDamaged() == false) {
						formula_racers[i].Collision();
						logger.logDamaged(formula_racers[i]);
					}
					if (formula_racers[j].IsDamaged() == false) {
						formula_racers[j].Collision();
						logger.logDamaged(formula_racers[j]);
					}
				}
			}
		}
	}
	
	public void pits() {
		for (int i = 0; i<car_racers.length; i++) {
			if (car_racers[i].getPosition() == 75.0 && car_racers[i].IsDamaged() && !pit.InPit(car_racers[i]) && !finish.IsFinished(car_racers[i])) {
				pit.enterPitStop(car_racers[i]);
			}
		}
		for (int i = 0; i<formula_racers.length; i++) {
			if (formula_racers[i].getPosition() == 75.0 && formula_racers[i].IsDamaged() && !pit.InPit(formula_racers[i])  && !finish.IsFinished(formula_racers[i])) {
				pit.enterPitStop(formula_racers[i]);
			}
		}
	}
	
	/**
	 * Perhaps not the best stylistically, but nearly all of the code is already written
	 * to run a race its just a while loop of ticking and checking collisions until finished returns true
	 */
	public void run() {
		boolean flag = false;
		while (flag == false){
			logger.logNewTick();
			ticks++;
			tick();
			checkCollision();
			pits();
			flag = finish.finished();
			if (ticks>1000) { // this was just here for testing purposes. This will stop any race that has gone on for longer than 1000 ticks
				System.out.println("error, race too long");
				flag = true;
			}
		}
		logger.logScore(calculatorScore(ticks));

	}
	
	/**
	 * Calculates score based off of equation provided in PA
	 * @param ticks number of ticks it took for game to end
	 * @returns the score for the track. TA mentioned that score is for the general racetrack not each car. This doesn't make much sense to me but hey
	 */
	public int calculatorScore(int ticks) {
		int score = 1000 - (20*ticks) + (150*car_racers.length) + (100*formula_racers.length);
		return score;
	}
	
	/**
	 * This method returns the logger instance used by this RaceTrack. You <b>SHOULD NOT</b> be using this method. 
	 * @return logger with this track's events 
	 */
	public TrackLoggerB getLogger() {
		return logger;
	}

}
