package action;

import java.lang.reflect.Method;
import java.util.SortedSet;
import java.util.TreeSet;

import timer.DateTimer;
import timer.Timer;

/**
 * @author flver
 *
 */
public class DiscreteActionOnOffDependent implements DiscreteActionInterface {
	
	protected DiscreteActionInterface onAction;
	protected DiscreteActionInterface offAction;
	protected DiscreteActionInterface currentAction;
	
	private Integer currentLapsTime;
	
	/**
	 * Construct an On Off dependence, first action (method) called is On, then method nextMethod() is called to select the next action.
	 * The default behavior of nextMethod() is to switch between On and Off actions.  It can be changed by overloading.
	 * 
	 * @param o
	 * @param on
	 * @param timerOn
	 * @param off
	 * @param timerOff
	 */
	public DiscreteActionOnOffDependent(Object o, String on, Timer timerOn, String off, Timer timerOff){
		if (timerOn.hasNext() && timerOff.hasNext()) {
			this.onAction = new DiscreteAction(o, on, timerOn);
			this.offAction = new DiscreteAction(o, off, timerOff);
		} else {
			throw new IllegalArgumentException("Both timerOn and timerOff should have at least one laps time");
		}

		this.currentAction = this.offAction;
		this.currentLapsTime = 0;
	}

	/**
	 * Constructor for the DiscreteActionOnOffDependent class.
	 * This class allows creating dependent discrete actions, activated and deactivated based on specified dates.
	 *
	 * @param o         The object on which the action will be executed.
	 * @param on        The name of the action to be executed upon activation.
	 * @param datesOn   A sorted set of dates for activating the action.
	 * @param off       The name of the action to be executed upon deactivation.
	 * @param datesOff  A sorted set of dates for deactivating the action.
	 * @throws IllegalArgumentException If either set of dates is empty.
	 */
	public DiscreteActionOnOffDependent(Object o, String on, SortedSet<Integer> datesOn, String off, SortedSet<Integer> datesOff){
		this.onAction = new DiscreteAction(o, on, new DateTimer((TreeSet<Integer>) datesOn));
		this.offAction = new DiscreteAction(o, off, new DateTimer((TreeSet<Integer>) datesOff));
		if(datesOn.first() < datesOff.first()){
			this.currentAction = this.onAction;
		}else{
			this.currentAction = this.offAction;
		}
	}

	/**
	 * Determines the next action to be executed based on the current state.
	 * If the current action is 'onAction' and there is a method specified for 'offAction',
	 * it switches to 'offAction'. If the current action is 'offAction' and there is a method specified for 'onAction',
	 * it switches to 'onAction'.
	 * Updates the current laps time to match the new action.
	 */
	public void nextAction(){
		if (this.currentAction == this.onAction){
			if (this.offAction.getMethod() != null) {
				this.currentAction = this.offAction;
			}
		}else{
			if (this.onAction.getMethod() != null) {
				this.currentAction = this.onAction;
			}
		}
		this.currentLapsTime = this.currentAction.getCurrentLapsTime();
	}

	/**
	 * Updates the current laps time by adding the specified amount of time.
	 *
	 * @param t The amount of time to be spent. It should be non-negative.
	 * @throws IllegalArgumentException If the specified time is negative.
	 */
	public void spendTime(int t) {
		if (t < 0) {
			throw new IllegalArgumentException("Time cannot be negative");
		}
		if (this.getCurrentLapsTime() == null) {
			this.currentLapsTime = t;
		}
		else {
			this.currentLapsTime += t;
		}
	}

	public Method getMethod() {
		return this.currentAction.getMethod();
	}

	@Override
	public Integer getCurrentLapsTime() {
		return this.currentLapsTime;
	}

	public Object getObject() {
		return this.currentAction.getObject();
	}

	/**
	 * Compares this DiscreteActionInterface with the specified DiscreteActionInterface for order.
	 *
	 * @param c The DiscreteActionInterface to be compared.
	 * @return A negative integer, zero, or a positive integer as this DiscreteActionInterface is less than, equal to, or greater than the specified DiscreteActionInterface.
	 */
	public int compareTo(DiscreteActionInterface c) {
		return this.currentAction.compareTo(c);
	}

	/**
	 * Executes the next action in the sequence and returns the current instance.
	 *
	 * @return The current instance after executing the next action.
	 */
	public DiscreteActionInterface next() {
		this.nextAction();
		return this;
	}

	/**
	 * Checks if there are remaining actions in either the 'onAction' or 'offAction' sequences.
	 *
	 * @return true if there are remaining actions, false otherwise.
	 */
	public boolean hasNext() {
		return this.onAction.hasNext() || this.offAction.hasNext();		
	}

	

}
