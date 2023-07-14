package cs131.pa3.CarsTunnels;

import cs131.pa3.Abstract.Direction;
import cs131.pa3.Abstract.Factory;
import cs131.pa3.Abstract.Tunnel;
import cs131.pa3.Abstract.Vehicle;
import cs131.pa3.CarsTunnels.*;

/**
 * The class implementing the Factory interface for creating instances of classes
 * @author cs131a
 *
 */
public class ConcreteFactory implements Factory {

    @Override
    public Tunnel createNewBasicTunnel(String name){
    	BasicTunnel basic = new BasicTunnel(name);
    	return basic;
    }

    @Override
    public Vehicle createNewCar(String name, Direction direction){
    	Car newCar = new Car(name, direction);
    	return newCar;
    }

    @Override
    public Vehicle createNewSled(String name, Direction direction){
    	Sled newSled = new Sled(name, direction);
    	return newSled;
    }
}
