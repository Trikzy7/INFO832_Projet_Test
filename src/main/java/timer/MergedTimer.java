package timer;

public class MergedTimer implements Timer{

	private Timer timer1; // The first timer
	private Timer timer2; // The second timer

	/**
	 * Constructs a MergedTimer with two timers to merge.
	 *
	 * @param timer1 The first timer to merge.
	 * @param timer2 The second timer to merge.
	 */
	public MergedTimer(Timer timer1, Timer timer2) {
		this.timer1 = timer1;
		this.timer2 = timer2;
	}

	/**
	 * Checks if the MergedTimer has a next value.
	 *
	 * @return True if both timers have a next value, otherwise false.
	 */
	@Override
	public boolean hasNext() {
		return (this.timer1.hasNext() && this.timer2.hasNext());
	}

	/**
	 * Retrieves the next value from the MergedTimer by summing the next values of both timers.
	 *
	 * @return The sum of the next values from both timers, or null if either timer has no next value.
	 */
	@Override
	public Integer next() {
		if(this.hasNext()) {
			return this.timer1.next() + this.timer2.next();
		}
		return null;
	}

}
