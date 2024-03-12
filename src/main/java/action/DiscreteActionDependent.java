package action;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.TreeSet;

import timer.Timer;

/**
 * @author flver
 *
 */
//TODO Must be refactored to be generic
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
		this.depedentActions = new TreeSet<DiscreteAction>();
		this.it = this.depedentActions.iterator();
		this.currentAction = this.baseAction;
	}
	
	public void addDependence(Object o, String depentMethodName, Timer timerDependence) {
		this.depedentActions.add(new DiscreteAction(o, depentMethodName, timerDependence));
	}
	
	/*private void dates2Timalapse(TreeSet<Integer> datesOn, TreeSet<Integer> datesOff, Vector<Integer> timeLapseOn, Vector<Integer> timeLapseOff) {
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
	@SuppressWarnings("unchecked")
	public DiscreteActionDependent(Wo o, String on, TreeSet<Integer> datesOn, String off, TreeSet<Integer> datesOff){
		Vector<Integer> timeLapseOn = new Vector<Integer>();
		Vector<Integer> timeLapseOff = new Vector<Integer>();
		
		dates2Timalapse((TreeSet<Integer>)datesOn.clone(), (TreeSet<Integer>)datesOff.clone(), timeLapseOn, timeLapseOff);
		
		this.baseAction = new DiscreteAction(o, on, timeLapseOn);
		this.offAction = new DiscreteAction(o, off, timeLapseOff);
		
		if(datesOn.first() < datesOff.first()){
			this.currentAction = this.baseAction;
		}else{
			this.currentAction = this.offAction;
		}
	}
*/
	private void reInit() {
		//this.baseAction.updateTimeLaps();
		for (Iterator<DiscreteAction> iter = this.depedentActions.iterator(); iter.hasNext(); ) {
		    DiscreteAction element = iter.next();
		    //element.updateTimeLaps();
		}		
	}
	
	public void nextMethod(){
		if (this.currentAction == this.baseAction){
			this.it = this.depedentActions.iterator();
			this.currentAction = this.it.next();
		}else if(this.currentAction == this.depedentActions.last()){
			this.currentAction = this.baseAction;
			this.reInit();
		}else {
			this.currentAction = this.it.next();
		}
	}
	
	public void spendTime(int t) {
		for (Iterator<DiscreteAction> iter = this.depedentActions.iterator(); iter.hasNext(); ) {
		    DiscreteAction element = iter.next();
		    element.spendTime(t);
		}
	}

	public void updateTimeLaps() {
		// time laps is updated at the re-initialization
		//this.currentAction.updateTimeLaps();	
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

	public DiscreteActionInterface next() {
		//Integer lapsTime = this.getNextLapsTime();
		Method method = this.getMethod();
		Object object = this.getObject();
		return this;
	}

	public boolean hasNext() {
		return this.baseAction.hasNext() || !this.depedentActions.isEmpty();		
	}

}
