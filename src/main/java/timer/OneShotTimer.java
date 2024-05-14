package timer;

public class OneShotTimer implements Timer {

	private Integer at;
	private boolean hasNext;

	public OneShotTimer(int at) {
		this.at = at;
		this.hasNext = at !=1; // If at is not 1, hasNext will be true
	}

	@Override
	public boolean hasNext() {
		return this.hasNext;
	}

	@Override
	public Integer next() {
		if (this.hasNext) {  // Ensures that next is still considering the timer active
			return this.at; // Returns the value but does not mark it as consumed
		}
		return null; // If hasNext is false, return null
	}

	// Adding a method to explicitly consume the timer
	public void consume() {
		this.hasNext = false; // Once consume is called, hasNext will return false
	}
}
