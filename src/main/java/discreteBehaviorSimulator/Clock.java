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
	/**
	 * Private constructor for the Clock class.
	 * Initializes the time and nextJump to 0, creates a new ReentrantReadWriteLock,
	 * sets the clock to virtual, and initializes an empty set of ClockObservers.
	 */
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
	/**
	 * Adds an observer to the set of observers for this clock.
	 *
	 * @param o The observer to be added. Must not be null.
	 * @throws NullPointerException if the passed observer is null.
	 */
	public void addObserver(ClockObserver o) {

		if (o == null){
			//return nullPointerException
			throw new NullPointerException("Observers set is null");
		}
		this.observers.add(o);
	}

	/**
	 * Removes an observer from the set of observers of this clock.
	 *
	 * @param o The observer to be removed. Must not be null.
	 * @throws NullPointerException if the passed observer is null.
	 */
	public void removeObserver(ClockObserver o) {
		if (o == null){
			//return nullPointerException
			throw new NullPointerException("Expected removeObserver(null) to throw NullPointerException, but it didn't");
		}
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
	/**
	 * Increases the current time of the clock by a specified amount.
	 *
	 * @param time The amount of time to add to the current time. Must be equal to the next jump time.
	 * @throws Exception if the provided time is not equal to the next jump time.
	 */
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
	/**
	 * Returns the current time of the clock.
	 *
	 * @return The current time of the clock. If the clock is virtual, it returns the virtual time. Otherwise, it returns the current system time.
	 */
	public long getTime() {
		if(this.virtual) {
			return this.time;
		}else {
			return new Date().getTime();
		}
	}
	/**
	 * Locks the clock for read access.
	 */
	public void lockReadAccess() {
		this.lock.readLock().lock();
	}
	/**
	 * Unlocks the clock from read access.
	 */
	public void unlockReadAccess() {
		this.lock.readLock().unlock();
	}
	/**
	 * Locks the clock for write access.
	 */
	public void lockWriteAccess() {
		this.lock.writeLock().lock();
	}
	/**
	 * Unlocks the clock from write access.
	 */
	public void unlockWriteAccess() {
		this.lock.writeLock().unlock();		
	}
	/**
	 * Returns a string representation of the current time of the clock.
	 *
	 * @return A string representation of the current time of the clock.
	 */
	public String toString() {
		return ""+this.time;
	}
}
