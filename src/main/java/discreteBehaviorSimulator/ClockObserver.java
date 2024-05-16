package discreteBehaviorSimulator;

public interface ClockObserver {
	/**
	 * This method is called whenever the observed clock's time changes.
	 *
	 * @param time The new time of the clock.
	 */
	public void clockChange(int time);
	/**
	 * This method is called to notify the observer of the next time the observed clock will change.
	 *
	 * @param nextJump The time of the next clock change.
	 */
	public void nextClockChange(int nextJump);
}
