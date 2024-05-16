package timer;

public class MergedTimer implements Timer {
	private Timer timer1;
	private Timer timer2;

	/**
	 * Constructs a MergedTimer by merging two timers.
	 *
	 * @param timer1 The first timer to be merged.
	 * @param timer2 The second timer to be merged.
	 */
	public MergedTimer(Timer timer1, Timer timer2) {
		this.timer1 = timer1;
		this.timer2 = timer2;
	}

	/**
	 * Checks if both timers have next values.
	 *
	 * @return True if both timers have next values, otherwise false.
	 */
	@Override
	public boolean hasNext() {
		return (this.timer1.hasNext() && this.timer2.hasNext());
	}

	/**
	 * Retrieves the sum of next values from both timers.
	 *
	 * @return The sum of next values from both timers, or null if any of the timers has no next value.
	 */
	@Override
	public Integer next() {
		if (this.hasNext()) {
			return this.timer1.next() + this.timer2.next();
		}
		return null;
	}
}
