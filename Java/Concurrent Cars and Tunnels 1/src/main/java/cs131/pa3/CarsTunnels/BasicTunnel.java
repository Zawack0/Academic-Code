package cs131.pa3.CarsTunnels;

import cs131.pa3.Abstract.Direction;
import cs131.pa3.Abstract.Tunnel;
import cs131.pa3.Abstract.Vehicle;

/**
 * 
 * The class for the Basic Tunnel, extending Tunnel.
 * @author cs131a
 *
 */
public class BasicTunnel extends Tunnel{
	
	/**
	 * these three fields are sufficient to determine if a vehicle should enter a tunnel, as the only case in which they 
	 * shouldn't occurs if vehicles are traveling in the opposite direction or too many of one kind of vehicle is already
	 * in the tunnel
	 */
	private int carsInTunnel;
	private int sledsInTunnel;
	Direction currentDirection;

	/**
	 * Creates a new instance of a basic tunnel with the given name
	 * @param name the name of the basic tunnel
	 */
	public BasicTunnel(String name) {
		super(name);
		carsInTunnel = 0;
		sledsInTunnel = 0;
		// currentDirection need not be initialized here
		// we only care about it in the canEnter method, and it is explicityly handeld there
	}

	@Override
	protected synchronized boolean tryToEnterInner(Vehicle vehicle){
		while (canEnter(vehicle)==false) {
			return false;
		}
		if (vehicle instanceof Car) {
			carsInTunnel ++;
		}
		if (vehicle instanceof Sled) {
			sledsInTunnel ++;
		}
		return true;
	}

	@Override
	protected synchronized void exitTunnelInner(Vehicle vehicle) {
		if (vehicle instanceof Car) {
			carsInTunnel --;
		}
		if (vehicle instanceof Sled) {
			sledsInTunnel --;
		}

	}
	
	/**
	 * ensures that the current vehicle is able to enter the tunnel, changing current direction if appropriate
	 * should only be called in the synchronized tryToEnter method
	 * @param vehicle
	 * @return true if vehicle can enter the current tunnel
	 */
	private boolean canEnter(Vehicle vehicle) {
		if (carsInTunnel == sledsInTunnel && carsInTunnel == 0) {
			currentDirection = vehicle.getDirection();
			return true;
		}
		if (vehicle.getDirection()!=currentDirection) {
			return false;
		}
		if (vehicle instanceof Sled) {
			if (carsInTunnel>0 || sledsInTunnel>=1) {
				return false;
			}
			return true; //at this point, we've tested for direction, number of sleds, and number of cars, any sled that got to this line is good to go
		}
		
		if (vehicle instanceof Car) {
			if (carsInTunnel>=3 || sledsInTunnel>0) {
				return false;
			}
			return true;
		}
		System.out.println("Something went wrong if you see me");//should never get here
		return false;//should never get here
	}
	
}
