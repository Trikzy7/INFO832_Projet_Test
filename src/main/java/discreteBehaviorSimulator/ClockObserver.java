package discreteBehaviorSimulator;

public interface ClockObserver {
	public void clockChange(int time);
	public void nextClockChange(int nextJump);
}
