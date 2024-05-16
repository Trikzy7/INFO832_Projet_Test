package timer;

/**
 * Represents a one-shot timer that fires only once.
 * The timer can be queried for the presence of a next value and retrieved if present.
 */
public class OneShotTimer implements Timer {

	private Integer at; // The value of the timer
	private boolean hasNext; // Flag indicating whether the timer has a next value

	/**
	 * Constructs a OneShotTimer with the specified value.
	 * If the value is 1, hasNext will be false, indicating that there is no next value.
	 *
	 * @param at The value of the timer.
	 */
	public OneShotTimer(int at) {
		this.at = at;
		this.hasNext = at != 1; // If at is not 1, hasNext will be true
	}

	/**
	 * Checks whether the timer has a next value.
	 *
	 * @return true if the timer has a next value, false otherwise.
	 */
	@Override
	public boolean hasNext() {
		return this.hasNext;
	}

	/**
	 * Retrieves the next value of the timer.
	 * If hasNext is true, returns the value without marking it as consumed.
	 * If hasNext is false, returns null.
	 *
	 * @return The next value of the timer, or null if there is no next value.
	 */
	@Override
	public Integer next() {
        Integer result = null;
        if (this.hasNext) { // Ensures that next is still considering the timer active
            result = this.at;// Returns the value but does not mark it as consumed
        } // If hasNext is false, return null

        return result;
    }
}
