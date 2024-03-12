package discreteBehaviorSimulator;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Clock {
	private static Clock instance = null;
	
	private int time;
	private int nextJump;
	private ReentrantReadWriteLock lock;
	private boolean virtual;
	
	
	private Set<ClockObserver> observers;
	
	private Clock() {
		this.time = 0;
		this.nextJump=0;
		this.lock = new ReentrantReadWriteLock();
		this.virtual = true;
		this.observers = new HashSet<ClockObserver>();
	}
	
	public static Clock getInstance() {
		if (Clock.instance == null) {
			Clock.instance = new Clock();
		}
		return Clock.instance;
	}
	
	public void addObserver(ClockObserver o) {
		this.observers.add(o);
	}
	public void removeObserver(ClockObserver o) {
		this.observers.remove(o);
	}
	
	public void setVirtual(boolean virtual) {
		this.virtual = virtual;
	}
	public boolean isVirtual() {
		return this.virtual;
	}
	
	public void setNextJump(int nextJump) {
		this.nextJump = nextJump;
		for(ClockObserver o:this.observers) {
			o.nextClockChange(this.nextJump);
		}
	}
	/*public void setTime(int time) throws IllegalAccessException {
		this.lock.lock();
		if (this.time < time) {
			this.time = time;
			for(ClockObserver o:this.observers) {
				o.ClockChange();
			}
		}else{
			this.lock.unlock();
			throw new IllegalAccessException("Set time error, cannot go back to the past !!!");
		}
		this.lock.unlock();
	}*/
	public void increase(int time) throws Exception {

		this.lockWriteAccess();

		if(time != this.nextJump) {
			throw new Exception("Unexpected time change");
		}
		this.time += time;
		for(ClockObserver o:this.observers) {
			o.clockChange(this.time);
		}
		this.unlockWriteAccess();
	}
	public long getTime() {
		if(this.virtual) {
			return this.time;
		}else {
			return new Date().getTime();
		}
	}
	
	public void lockReadAccess() {
		this.lock.readLock().lock();
	}
	
	public void unlockReadAccess() {
		this.lock.readLock().unlock();
	}
	
	public void lockWriteAccess() {
		this.lock.writeLock().lock();
	}
	public void unlockWriteAccess() {
		this.lock.writeLock().unlock();		
	}
	
	public String toString() {
		return ""+this.time;
	}
}
