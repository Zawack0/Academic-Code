package cs131.pa4.CarsTunnels;

import java.util.Collection;
import java.util.concurrent.locks.*;
import cs131.pa4.Abstract.Scheduler;
import cs131.pa4.Abstract.Tunnel;
import cs131.pa4.Abstract.Vehicle;


/**
 * The priority scheduler assigns vehicles to tunnels based on their priority
 * It extends the Scheduler class.
 * @author cs131a
 *
 */
public class PriorityScheduler extends Scheduler{
	
	//Synchronization Variables
	private final Lock lock = new ReentrantLock();
	// Creating a lock condition for each priority is somewhat memory inefficient,
	// but more runtime efficient and probably better for a situation where there is
	// a small number of different priorities
	private final Condition Prio4Go = lock.newCondition();
	private final Condition Prio3Go = lock.newCondition();
	private final Condition Prio2Go = lock.newCondition();
	private final Condition Prio1Go = lock.newCondition();
	private final Condition Prio0Go = lock.newCondition();
	
	// State variables 
	private Tunnel tunnel;
	private int HighPrio;
	private int P0wait = 0;
	private int P1wait = 0;
	private int P2wait = 0;
	private int P3wait = 0;
	private int P4wait = 0;
	

	/**
	 * Creates an instance of a priority scheduler with the given name and tunnels
	 * @param name the name of the priority scheduler
	 * @param tunnels the tunnels where the vehicles will be scheduled to
	 */
	public PriorityScheduler(String name, Collection<Tunnel> tunnels) {
		super(name, tunnels);
		tunnel = super.getTunnels().iterator().next();
		HighPrio=0;
	}

	@Override
	public Tunnel admit(Vehicle vehicle) {
		lock.lock();
		if (vehicle.getPriority()>HighPrio) {
			HighPrio = vehicle.getPriority();
		}
		if (vehicle.getPriority()<HighPrio) {
			Condition toWait = determineWaitlock(vehicle.getPriority());
			if (vehicle.getPriority()==4) {
				P4wait++;
			}
			if (vehicle.getPriority()==3) {
				P3wait++;
			}
			if (vehicle.getPriority()==2) {
				P2wait++;
			}
			if (vehicle.getPriority()==1) {
				P1wait++;
			}
			if (vehicle.getPriority()==0) {
				P0wait++;
			}
			try {
				toWait.await();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		while (tunnel.tryToEnter(vehicle) == false) {
			//busy waiting is still useful here, because we've determined 
			//the current vehicle is the highest priority
		}
		lock.unlock();
		return tunnel;
	}
	
	@Override
	public void exit(Vehicle vehicle) {
		lock.lock();
		tunnel.exitTunnel(vehicle);
		if (vehicle.getPriority()==4) {
			P4wait--;
		}
		if (vehicle.getPriority()==3) {
			P3wait--;
		}
		if (vehicle.getPriority()==2) {
			P2wait--;
		}
		if (vehicle.getPriority()==1) {
			P1wait--;
		}
		if (vehicle.getPriority()==0) {
			P0wait--;
		}
		if (P4wait>0) {
			Prio4Go.signalAll();
		}
		else if(P3wait>0){
			Prio3Go.signalAll();
		}
		else if(P2wait>0){
			Prio2Go.signalAll();
		}
		else if(P1wait>0){
			Prio1Go.signalAll();
		}
		else if(P0wait>0){
			Prio0Go.signalAll();
		}
		lock.unlock();
	}
	
	protected Condition determineWaitlock(int prio) {
		if (prio==0) {
			return Prio0Go;
		}
		if (prio==1) {
			return Prio1Go;
		}
		if (prio==2) {
			return Prio2Go;
		}
		if (prio==3) {
			return Prio3Go;
		}
		if (prio==4) {
			return Prio4Go;
		}
		System.out.println("If you got here, something is wrong");
		return null;
	}
	
}
