package action;

import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import timer.Timer;

/**
 * @author Tiphaine Bulou (2016)
 * @author Flavien Vernier
 *
 */


public class DiscreteAction implements DiscreteActionInterface {
	private Object object;
	private Method method;

	private Timer timmer;				// timer provides new lapsTime
	//private TreeSet<Integer> dates;	// obsolete, managed in timer 
	//private Vector<Integer> lapsTimes;// obsolete, managed in timer
	private Integer lapsTime; 			// waiting time (null if never used)
	
	private Logger logger;

	// Constructor

	/**
	 * Private constructor for creating a new DiscreteAction instance.
	 * Initializes the logger for the DiscreteAction class.
	 */
	private DiscreteAction() {
		// Start logger
			this.logger = Logger.getLogger("DAS");
			this.logger.setLevel(Level.ALL);
			this.logger.setUseParentHandlers(true);
	}

	/**
	 * Constructs a new DiscreteAction instance with the specified object, method name, and timer.
	 *
	 * @param o The object on which the method will be invoked.
	 * @param m The name of the method to be invoked.
	 * @param timmer The timer associated with the action.
	 */
	public DiscreteAction(Object o, String m, Timer timmer){
		this();
		this.object = o;
		try{	
			this.method = o.getClass().getDeclaredMethod(m);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		this.timmer = timmer;
	}
	
	// ATTRIBUTION
	/**
	 * Updates the laps time by subtracting the specified amount of time.
	 *
	 * @param t The amount of time to be spent. It should be non-negative.
	 */
	public void spendTime(int t) {
		Integer old = this.lapsTime;
		if(this.lapsTime != null) {
			this.lapsTime -= t;
		}
		this.logger.log(Level.FINE, () -> String.format("[DA] operate spendTime on %s:%d: old time %d new time %d",
				this.getObject().getClass().getName(),
				this.getObject().hashCode(),
				old,
				this.getCurrentLapsTime()));
	}
	// RECUPERATION

	public Method getMethod(){
		return method;
	}
	public Integer getCurrentLapsTime(){
		return lapsTime;
	}
	public Object getObject(){
		return object;
	}



	// COMPARAISON
	/**
	 * Compares this DiscreteActionInterface with the specified DiscreteActionInterface for order.
	 * Logs the comparison operation.
	 *
	 * @param c The DiscreteActionInterface to be compared.
	 * @return A negative integer, zero, or a positive integer as this DiscreteActionInterface is less than, equal to, or greater than the specified DiscreteActionInterface.
	 */
	public int compareTo(DiscreteActionInterface c) {
		logger.info("Comparing {} with {}\n");
		if ((this.lapsTime == null) &&  (c.getCurrentLapsTime() != null)) { // no lapstime is equivalent to infinity
			return 1;
		}
		if ((c.getCurrentLapsTime() == null) && (this.lapsTime != null)) {// no lapstime is equivalent to infinity
			return -1;
		}
		if(this.lapsTime != null && c.getCurrentLapsTime() != null){
			if(this.lapsTime > c.getCurrentLapsTime()){
				return 1;
			}
			if(this.lapsTime < c.getCurrentLapsTime()){
				return -1;
			}
			if(Objects.equals(this.lapsTime, c.getCurrentLapsTime())){
				return 0;
			}

		}
		return 0;
	}

	public String toString(){
		return "Object : " + this.object.getClass().getName() + "\n Method : " + this.method.getName()+"\n Stat. : "+ this.timmer + "\n delay: " + this.lapsTime;

	}

	/**
	 * Moves to the next time interval according to the timer associated with this action.
	 * Logs the operation.
	 *
	 * @return The current instance after moving to the next time interval.
	 */
	public DiscreteActionInterface next() {
		Integer old = this.lapsTime;
		this.lapsTime = this.timmer.next();
		this.logger.log(Level.FINE, () -> String.format("[DA] operate next on %s:%d: old time %d new time %d",
				this.getObject().getClass().getName(),
				this.getObject().hashCode(),
				old,
				this.getCurrentLapsTime()));
		return this;
	}

	/**
	 * Checks if there are more time intervals available according to the associated timer.
	 *
	 * @return true if there are more time intervals available, false otherwise.
	 */
	public boolean hasNext() {
		boolean more=false;
		if (this.timmer != null && this.timmer.hasNext()) {
			more = true;
		}
		return more;		
	}

	public void setCurrentLapsTime(Integer lapsTime) {
		this.lapsTime = lapsTime;
	}

}
