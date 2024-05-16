package timer;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.Vector;

/**
 * A timer that iterates over a series of time intervals between dates.
 */
public class DateTimer implements Timer {

	private final Vector<Integer> lapsTimes;  // Vector containing time intervals
	private final Iterator<Integer> it;       // Iterator over time intervals

	/**
	 * Constructs a DateTimer based on a set of dates.
	 *
	 * @param dates A TreeSet containing dates in ascending order.
	 */
	public DateTimer(SortedSet<Integer> dates) {
		this.lapsTimes = new Vector<>();
		Integer last;
		Integer current = 0;

		Iterator<Integer> itr = dates.iterator();
		while (itr.hasNext()) {
			last = current;
			current = itr.next();
			this.lapsTimes.add(current - last);
		}
		this.it = this.lapsTimes.iterator();
	}

	/**
	 * Constructs a DateTimer based on a vector of time intervals.
	 *
	 * @param lapsTimes A Vector containing time intervals.
	 */
	public DateTimer(Vector<Integer> lapsTimes) {
		this.lapsTimes = new Vector<>(lapsTimes);
		this.it = this.lapsTimes.iterator();
	}

	/**
	 * Checks if there are more time intervals to iterate over.
	 *
	 * @return true if there are more time intervals, otherwise false.
	 */
	@Override
	public boolean hasNext() {
		return it.hasNext();
	}

	/**
	 * Gets the next time interval.
	 *
	 * @return The next time interval.
	 */
	@Override
	public Integer next() {
		return it.next();
	}
}
