package action;

import java.lang.reflect.Method;
import java.util.TreeSet;
import java.util.Vector;

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
	private Integer lastOffDelay=0;
	
	/**
	 * Construct an On Off dependence, first action (method) called is On, then method nextMethod() is called to select the next action.
	 * The default behavior of nextMethod() is to switch between On and Off actions.  It can be change by overloading. 
	 * 
	 * @param o
	 * @param on
	 * @param timerOn
	 * @param off
	 * @param timerOff
	 */
	/*public DiscreteActionOnOffDependent(Wo o, Method on, Timer timerOn, Method off, Timer timerOff){
		this.onAction = new DiscreteAction(o, on, timerOn);
		this.offAction = new DiscreteAction(o, off, timerOff);
		
		this.currentAction = this.onAction;
	}*/
	
	public DiscreteActionOnOffDependent(Object o, String on, Timer timerOn, String off, Timer timerOff){
		this.onAction = new DiscreteAction(o, on, timerOn);
		this.offAction = new DiscreteAction(o, off, timerOff);
		
		this.currentAction = this.offAction;
		this.currentLapsTime = 0;
	}
	
	private void dates2Timalapse(TreeSet<Integer> datesOn, TreeSet<Integer> datesOff, Vector<Integer> timeLapseOn, Vector<Integer> timeLapseOff) {
		Vector<Integer> currentTimeLapse;
		TreeSet<Integer> currentDates;
		Integer date=0;
		if(datesOn.first()<datesOff.first()) {
			currentTimeLapse = timeLapseOn;
			currentDates = datesOn;
		}else {
			currentTimeLapse = timeLapseOff;	
			currentDates = datesOff;		
		}
		Integer nextDate;
		
		while(datesOn.size()>0 || datesOff.size()>0) {
			nextDate = currentDates.first();
		
			currentTimeLapse.add(nextDate - date);
			currentDates.remove(nextDate);
		
			date = nextDate;
			
			if(currentDates == datesOn) {
				currentDates = datesOff;
				currentTimeLapse = timeLapseOff;
			}else {
				currentDates = datesOn;
				currentTimeLapse = timeLapseOn;			
			}
		}
		
	}
	
	public DiscreteActionOnOffDependent(Object o, String on, TreeSet<Integer> datesOn, String off, TreeSet<Integer> datesOff){
		/*Vector<Integer> timeLapseOn = new Vector<Integer>();
		Vector<Integer> timeLapseOff = new Vector<Integer>();
		
		dates2Timalapse((TreeSet<Integer>)datesOn.clone(), (TreeSet<Integer>)datesOff.clone(), timeLapseOn, timeLapseOff);
		*/
		this.onAction = new DiscreteAction(o, on, new DateTimer(datesOn));
		this.offAction = new DiscreteAction(o, off, new DateTimer(datesOff));
		
		
		
		if(datesOn.first() < datesOff.first()){
			this.currentAction = this.onAction;
		}else{
			this.currentAction = this.offAction;
		}
	}


	public void nextAction(){
		if (this.currentAction == this.onAction){
			this.currentAction = this.offAction;
		}else{
			this.currentAction = this.onAction;
		}
		this.currentLapsTime = this.currentAction.getCurrentLapsTime();
		this.lastOffDelay = this.currentLapsTime;
	}

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
//		this.currentLapsTime += t;
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

	public int compareTo(DiscreteActionInterface c) {
		return this.currentAction.compareTo(c);
	}

	public DiscreteActionInterface next() {
		this.nextAction();
		return this;
	}
	
	public boolean hasNext() {
		return this.onAction.hasNext() || this.offAction.hasNext();		
	}

	

}
