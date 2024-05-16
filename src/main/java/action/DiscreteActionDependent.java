package action;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.TreeSet;

import timer.Timer;

/**
 * @author flver
 *
 */

public class DiscreteActionDependent implements DiscreteActionInterface {
	
	protected DiscreteAction baseAction;
	protected TreeSet<DiscreteAction> depedentActions;
	private Iterator<DiscreteAction> it;
	protected DiscreteAction currentAction;
	
	
	/**
	 * Construct a series of dependent actions, first action (method) called is baseMethodName, then method nextMethod() is called to select the next action. 
	 * 
	 * @param o
	 * @param baseMethodName
	 * @param timerBase
	 */	
	public DiscreteActionDependent(Object o, String baseMethodName, Timer timerBase){
		this.baseAction = new DiscreteAction(o, baseMethodName, timerBase);
		this.depedentActions = new TreeSet<>();
		this.it = this.depedentActions.iterator();
		this.currentAction = this.baseAction;
	}

	/**
	 * Adds a dependence to the current action, represented by the specified object, method name, and timer.
	 *
	 * @param o The object representing the dependence.
	 * @param depentMethodName The name of the method representing the dependence.
	 * @param timerDependence The timer associated with the dependence.
	 */
	public void addDependence(Object o, String depentMethodName, Timer timerDependence) {
		this.depedentActions.add(new DiscreteAction(o, depentMethodName, timerDependence));
	}

	/**
	 * Moves to the next method in the sequence of dependent actions.
	 * If the current action is the base action, it sets the iterator to iterate over the dependent actions
	 * and moves to the first dependent action if available.
	 * If the current action is not the base action and there are no more dependent actions, it returns to the base action.
	 * Otherwise, it moves to the next dependent action.
	 */
	public void nextMethod(){
		if (this.currentAction == this.baseAction){
			this.it = this.depedentActions.iterator();
			if (this.it.hasNext()) {
				this.currentAction = this.it.next();
			}
		}else if(!this.it.hasNext()){
			this.currentAction = this.baseAction;
		}else {
			this.currentAction = this.it.next();
		}
	}

	/**
	 * Updates the laps time for all dependent actions by the specified amount of time.
	 *
	 * @param t The amount of time to be spent. It should be non-negative.
	 */
	public void spendTime(int t) {
		for (Iterator<DiscreteAction> iter = this.depedentActions.iterator(); iter.hasNext(); ) {
		    DiscreteAction element = iter.next();
		    element.spendTime(t);
		}
	}

	public void updateTimeLaps() {
		// time laps is updated at the re-initialization
		this.nextMethod();	
	}

	public Method getMethod() {
		return this.currentAction.getMethod();
	}

	public Integer getCurrentLapsTime() {
		return this.currentAction.getCurrentLapsTime();
	}

	public Object getObject() {
		return this.currentAction.getObject();
	}

	public int compareTo(DiscreteActionInterface c) {
		return this.currentAction.compareTo(c);
	}

	public Boolean isEmpty() {
		return !this.hasNext();
	}

	/**
	 * Updates the laps time and returns the current instance.
	 *
	 * @return The current instance after updating the laps time.
	 */
	public DiscreteActionInterface next() {
		updateTimeLaps();
		return this;
	}

	/**
	 * Checks if there are more actions available in either the base action or the list of dependent actions.
	 *
	 * @return true if there are more actions available, false otherwise.
	 */
	public boolean hasNext() {
		return this.baseAction.hasNext() || !this.depedentActions.isEmpty();		
	}

}
