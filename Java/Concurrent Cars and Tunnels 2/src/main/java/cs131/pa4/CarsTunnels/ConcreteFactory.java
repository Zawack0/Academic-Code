package cs131.pa4.CarsTunnels;

import java.util.Collection;

import cs131.pa4.CarsTunnels.BasicTunnel;
import cs131.pa4.CarsTunnels.Car;
import cs131.pa4.CarsTunnels.Sled;
import cs131.pa4.Abstract.Direction;
import cs131.pa4.Abstract.Factory;
import cs131.pa4.Abstract.Scheduler;
import cs131.pa4.Abstract.Tunnel;
import cs131.pa4.Abstract.Vehicle;

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

    @Override
    public Scheduler createNewPriorityScheduler(String name, Collection<Tunnel> tunnels){
    		PriorityScheduler newScheduler = new PriorityScheduler(name, tunnels);
    		return newScheduler;
    }
}
