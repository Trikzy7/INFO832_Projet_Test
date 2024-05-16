package timer;

/**
 * Represents a timer bounded by a specified time interval.
 * This timer wraps around another timer and only provides values within the specified time interval.
 */
public class TimeBoundedTimer implements Timer {

	private Timer timer2bound; // The timer to be bounded
	private Integer startTime; // The start time of the interval
	private Integer stopTime; // The stop time of the interval

	private Integer next = 0; // The next value of the timer
	private int time = 0; // The accumulated time
	private boolean hasNext; // Flag indicating whether the timer has a next value

	/**
	 * Constructs a TimeBoundedTimer with the specified parameters.
	 *
	 * @param timer2bound The timer to be bounded.
	 * @param startTime   The start time of the interval.
	 * @param stopTime    The stop time of the interval.
	 */
	public TimeBoundedTimer(Timer timer2bound, int startTime, int stopTime) {
		this.timer2bound = timer2bound;
		this.startTime = startTime;
		this.stopTime = stopTime;
		this.init();
	}

	/**
	 * Constructs a TimeBoundedTimer with the specified parameters, setting stopTime to Integer.MAX_VALUE.
	 *
	 * @param timer2bound The timer to be bounded.
	 * @param startTime   The start time of the interval.
	 */
	public TimeBoundedTimer(Timer timer2bound, int startTime) {
		this.timer2bound = timer2bound;
		this.startTime = startTime;
		this.stopTime = Integer.MAX_VALUE;
		this.init();
	}

	/**
	 * Initializes the timer by advancing it to the next value within the time interval.
	 */
	private void init() {
		this.next = this.timer2bound.next();
		while (this.next < this.startTime) {
			this.next += this.timer2bound.next();
		}
		if (this.next < this.stopTime) {
			this.hasNext = true;
		} else {
			this.hasNext = false;
		}
	}

	/**
	 * Checks whether the timer has a next value within the time interval.
	 *
	 * @return true if the timer has a next value within the time interval, false otherwise.
	 */
	@Override
	public boolean hasNext() {
		return this.hasNext;
	}

	/**
	 * Retrieves the next value of the timer within the time interval.
	 * If the accumulated time exceeds the stop time, returns null.
	 *
	 * @return The next value of the timer within the time interval, or null if the stop time is exceeded.
	 */
	@Override
	public Integer next() {
		Integer nextValue = this.next;
		this.time += this.next;
		if (this.time < this.stopTime) {
			this.next = this.timer2bound.next();
			this.hasNext = false;
		} else {
			nextValue = null;
			this.hasNext = false;
		}
		return nextValue;
	}
}
