package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import main.*;

public class SimpleUnitTests {
	
	RaceCar Slow_Car = new RaceCar(1, 1);
	RaceCar Fast_Car = new RaceCar(999, 999);
	
	FormulaOne Slow_Formula = new FormulaOne(1, 1);
	FormulaOne Fast_Fromula = new FormulaOne(999, 999);
	
	PitStop pit = new PitStop (2,2);
	
	/**
	 * tests constructors, the min/max speeds, and getStrength / getSpeed
	 */
	@Test
	public void ContructorTests() {
		assertTrue(Slow_Car.getSpeed()==30 && Slow_Car.getStrength()==2);
		assertTrue(Fast_Car.getSpeed()==55 && Fast_Car.getStrength()==4);
		
		assertTrue(Slow_Formula.getSpeed()==30 && Slow_Formula.getStrength()==3);
		assertTrue(Fast_Fromula.getSpeed()==70 && Fast_Fromula.getStrength()==5);
	}
	
	
	/**
	 * tests the pit constructor, enterPitStop, and InPit
	 */
	@Test
	public void PitTest() {
		pit.enterPitStop(Slow_Car);
		pit.enterPitStop(Fast_Fromula);
		
		assertTrue(pit.InPit(Slow_Car));
		assertTrue(pit.InPit(Fast_Fromula));
		
		assertTrue(!pit.InPit(Fast_Car)); // could have used assertFalse
		assertTrue(!pit.InPit(Slow_Formula));// could have used assertFalse
	}

}
