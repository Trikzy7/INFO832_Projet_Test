
package discreteBehaviorSimulator;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Vector;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import action.DiscreteAction;
import action.DiscreteActionInterface;


/**
 * @author Tiphaine Bulou (2016)
 * @author Flavien Vernier
 *
 */
public class DiscreteActionSimulator implements Runnable {


	private Thread t;
	private boolean running = false;
	
	private Clock globalTime;
	
	private Vector<DiscreteActionInterface> actionsList = new Vector<>();
	
	private int nbLoop;
	private int step;
	
	private Logger logger;
	private FileHandler logFile; 
	private ConsoleHandler logConsole;
	/**
	 * Default constructor for the DiscreteActionSimulator class.
	 * Initializes the logger, sets up the global clock instance, and prepares the thread for execution.
	 * The logger is set to log all levels of messages and is configured to output to both a file and the console.
	 * The global clock instance is retrieved using the Clock class's getInstance method.
	 * The thread is created but not started; it must be started manually using the start method.
	 */
	public DiscreteActionSimulator(){
		
		// Start logger
		this.logger = Logger.getLogger("DAS");
		//this.logger = Logger.getLogger("APP");
		this.logger.setLevel(Level.ALL);
		this.logger.setUseParentHandlers(true);
		try{
			this.logFile = new FileHandler(this.getClass().getName() + ".log");
			//this.logFile.setFormatter(new SimpleFormatter());
			this.logFile.setFormatter(new LogFormatter());
			this.logConsole = new ConsoleHandler();
		}catch(Exception e){
			e.printStackTrace();
		}
		this.logger.addHandler(logFile);
		this.logger.addHandler(logConsole);
		

		this.globalTime = Clock.getInstance();
		
		this.t = new Thread(this);
		this.t.setName("Discrete Action Simulator");
	}
	
	/**
	 * @param nbLoop defines the number of loop for the simulation, the simulation is infinite if nbLoop is negative or 0.
	 */
	public void setNbLoop(int nbLoop){
		if(nbLoop>0){
			this.nbLoop = nbLoop;
			this.step = 1;
		}else{ // running infinitely
			this.nbLoop = 0;
			this.step = -1;
		}
	}
	/**
	 * Adds a new action to the list of actions to be executed by the simulator.
	 * The action is added only if it has a next execution time.
	 * After adding the action, the list of actions is sorted to ensure the correct execution order.
	 *
	 * @param c The action to be added. Must implement the DiscreteActionInterface and have a next execution time.
	 */
	public void addAction(DiscreteActionInterface c){

		if(c.hasNext()) {
			// add to list of actions, next is call to the action exist at the first time
			this.actionsList.add(c.next());

			// sort the list for ordered execution 
			Collections.sort(this.actionsList);
		}
	}
	
	/*public void addTemporalRule(TemporalRule r){
		
	}*/

	/**
	 * @return the laps time before the next action
	 */
	private int nextLapsTime() {
		DiscreteActionInterface currentAction = this.actionsList.get(0);
		return currentAction.getCurrentLapsTime();
	}
	/**
	 * @return laps time of the running action
	 */
	private int runAction(){
		// Run the first action
		int sleepTime = 0;


		// TODO Manage parallel execution !  
		DiscreteActionInterface currentAction = this.actionsList.get(0);
		Object o = currentAction.getObject();
		Method m = currentAction.getMethod();
		sleepTime = currentAction.getCurrentLapsTime();
		
		try {
			//Thread.sleep(sleepTime); // Real time can be very slow
			Thread.yield();
			//Thread.sleep(1000); // Wait message bus sends
			if(this.globalTime!=null) {
				this.globalTime.increase(sleepTime);
			}
			m.invoke(o);
			if(this.globalTime!=null) {
				this.logger.log(Level.FINE, "[DAS] run action " + m.getName() + " on " + o.getClass().getName() + ":" + o.hashCode() + " at " + this.globalTime.getTime() + " after " + sleepTime + " time units\n");
				System.out.println("[DAS] run action " + m.getName() + " on " + o.getClass().getName() + ":" + o.hashCode() + " at " + this.globalTime.getTime() + " after " + sleepTime + " time units\n");
			}else {
				this.logger.log(Level.FINE, "[DAS] run action " + m.getName() + " on " + o.getClass().getName() + ":" + o.hashCode() + " after " + sleepTime + " time units\n");
				System.out.println("[DAS] run action " + m.getName() + " on " + o.getClass().getName() + ":" + o.hashCode() + " after " + sleepTime + " time units\n");
			
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}

		return sleepTime;
	}
	/**
	 * Updates the time lapses of all actions in the actions list after an action has been executed.
	 * The first action in the list is removed and if it has a next execution time, it is added back to the list.
	 * The list of actions is then sorted to ensure the correct execution order.
	 *
	 * @param runningTimeOf1stCapsul The time taken by the first action in the list to execute.
	 */
	private void updateTimes(int runningTimeOf1stCapsul){
		
		// update time laps off all actions
		for(int i=1 ; i < this.actionsList.size(); i++){
			//int d = this.actionsList.get(i).getLapsTime();
			//this.actionsList.get(i).setLapsTemps(d- runningTimeOf1stCapsul);
			this.actionsList.get(i).spendTime(runningTimeOf1stCapsul);
		}

		// get new time lapse of first action
		/*if(this.globalTime == null) {
			this.actionsList.get(0).updateTimeLaps();
		}else {	
			this.actionsList.get(0).updateTimeLaps(this.globalTime.getTime());
		}
		
		// remove the action if no more lapse time is defined
		if(this.actionsList.get(0).getLastLapsTime() == null) {
			this.actionsList.remove(0);
		}else {
			// resort the list
			Collections.sort(this.actionsList);
		}*/

		DiscreteActionInterface a = this.actionsList.remove(0);
		if(a.hasNext()) {
			a = a.next();
			this.actionsList.addElement(a);
			if(this.globalTime!=null) {
				this.logger.log(Level.FINE, "[DAS] reset action " + a.getMethod().getName() + " on " + a.getObject().getClass().getName() + ":" + a.getObject().hashCode() + " at " + this.globalTime.getTime() + " to " + a.getCurrentLapsTime() + " time units\n");
				System.out.println("[DAS] reset action " + a.getMethod().getName() + " on " + a.getObject().getClass().getName() + ":" + a.getObject().hashCode() + " at " + this.globalTime.getTime() + " to " + a.getCurrentLapsTime() + " time units\n");
			}else {
				this.logger.log(Level.FINE, "[DAS] reset action " + a.getMethod().getName() + " on " + a.getObject().getClass().getName() + ":" + a.getObject().hashCode() + " to " + a.getCurrentLapsTime() + " time units\n");
				System.out.println("[DAS] reset action " + a.getMethod().getName() + " on " + a.getObject().getClass().getName() + ":" + a.getObject().hashCode() + " to " + a.getCurrentLapsTime() + " time units\n");
			}
			Collections.sort(this.actionsList);
		}
	}

	/**
	 * Executes the simulation. The simulation runs in a loop until it is stopped or until it has completed the specified number of loops.
	 * In each iteration of the loop, the method checks if there are any actions to execute. If there are, it retrieves the next action, executes it, and updates the times of all actions.
	 * If there are no actions to execute, the method stops the simulation.
	 * The method also handles synchronization with the global clock and logs the execution of actions.
	 */
	public void run() {
		int count = this.nbLoop;
		boolean finished = false;

		System.out.println("LANCEMENT DU THREAD " + t.getName() + " \n");

		while(running && !finished){

			if(!this.actionsList.isEmpty()){
				System.out.println(this);
				this.globalTime.setNextJump(this.nextLapsTime());
				
				this.globalTime.lockWriteAccess();
				int runningTime = this.runAction();
				this.updateTimes(runningTime);
				this.globalTime.unlockWriteAccess();
				try {
					Thread.sleep(100);
				}catch(Exception e) {
					e.printStackTrace();
				}
				//TODO add global time synchronizer for action with list of date and avoid drift 
			}else{
				System.out.println("NOTHING TO DO\n");
				this.stop();
			}

			count -= this.step;
			if(count<=0) {
				finished = true;
			}
		}
		this.running = false;
		if(this.step>0) {
			System.out.println("DAS: " + (this.nbLoop - count) + " actions done for " + this.nbLoop + " turns asked.");
		}else {
			System.out.println("DAS: " + (count) + " actions done!");			
		}
	}
	/**
	 * Starts the simulation. This method sets the running flag to true and starts the thread.
	 * The actual execution of the simulation is handled by the run method.
	 */
	public void start(){
		this.running = true;
		this.t.start();
	}
	/**
	 * Stops the simulation. This method sets the running flag to false, which causes the run method to exit its loop and stop executing actions.
	 * The thread itself is not actually stopped; it will exit naturally when the run method returns.
	 */

	public void stop(){
		System.out.println("STOP THREAD " + t.getName() + "obj " + this);
		this.running = false;
	}

	/**
	 * Returns a string representation of the DiscreteActionSimulator.
	 * The string includes the number of actions in the actions list and a detailed description of each action.
	 * If the actions list is empty, the method returns a string indicating that there are no actions.
	 *
	 * @return A string representation of the DiscreteActionSimulator.
	 */
	public String toString(){
		if (this.actionsList.isEmpty()){
			return "No actions";
		}
		StringBuffer toS = new StringBuffer("------------------\nTestAuto :" + this.actionsList.size());
		for(DiscreteActionInterface c : this.actionsList){
			toS.append(c.toString() + "\n");
		}
		toS.append("---------------------\n");
		return toS.toString();
	}

	public boolean getRunning() {
		return this.running;
	}

}
