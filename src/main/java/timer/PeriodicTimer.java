package timer;

public class PeriodicTimer implements Timer {

	private int period;
	private int next;
	private RandomTimer moreOrLess = null;
	
	public PeriodicTimer(int at) {
		this.period = at;
		this.next = at;
	}
	
	/**
	 * @param at
	 * @param moreOrLess
	 * 
	 * use MergedTimer instead
	 */
	@Deprecated
	public PeriodicTimer(int at, RandomTimer moreOrLess) {
		this.period = at;
		this.moreOrLess = moreOrLess;
		this.next = at + (int)(this.moreOrLess.next() - this.moreOrLess.getMean());
	}
	
	public PeriodicTimer(int period, int at) {
		this.period = period;
		this.next = at;
	}
	
	/**
	 * @param period
	 * @param at
	 * @param moreOrLess
	 * 
	 * use MergedTimer instead
	 */
	@Deprecated
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
		
		int next =  this.next;
		
		if(this.moreOrLess != null) {
			this.next = this.period + (int)(this.moreOrLess.next() - this.moreOrLess.getMean());
		}else {
			this.next = this.period;
		}
		
		return next;
	}
	
	/*@Override
	public Integer next(int since) {
		
		int next = (this.at - (since % this.period) + this.period) % this.period;
		
		if(this.moreOrLess != null) {
			next += this.moreOrLess.next() - this.moreOrLess.getMean();
			this.next = this.period * 2 - next;
		}else {
			this.next = this.period;
		}
		
		return next;
	}*/

	@Override
	public boolean hasNext() {
		return true;
	}

}
