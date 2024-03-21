package timer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class TimeBoundedTimerTest {


    @Test
    void testHasNextNotFinished() {
        TreeSet<Integer> dates = new TreeSet<Integer>();
        dates.add(1);
        dates.add(2);
        dates.add(3);
        Timer timer = new DateTimer(dates);
        TimeBoundedTimer timeBoundedTimer = new TimeBoundedTimer(timer, 1, 3);
        assertTrue(timeBoundedTimer.hasNext());
    }
    @Test
    void testHasNextFinished() {
        TreeSet<Integer> dates = new TreeSet<Integer>();
        dates.add(1);
        dates.add(2);
        dates.add(3);
        Timer timer = new DateTimer(dates);
        TimeBoundedTimer timeBoundedTimer = new TimeBoundedTimer(timer, 1, 3);
        //two step so finished
        timeBoundedTimer.next();
        timeBoundedTimer.next();
        assertFalse(timeBoundedTimer.hasNext());
    }

    @Test
    void testNextNotFinished(){
        TreeSet<Integer> dates = new TreeSet<Integer>();
        dates.add(0);
        dates.add(1);
        dates.add(2);
        Timer timer = new DateTimer(dates);
        TimeBoundedTimer timeBoundedTimer = new TimeBoundedTimer(timer, 0, 2);
        int next = timeBoundedTimer.next();
        assertEquals(0, next);
    }
    @Test
    void testNextFinished(){
        TreeSet<Integer> dates = new TreeSet<Integer>();
        dates.add(0);
        dates.add(1);
        dates.add(2);
        Timer timer = new DateTimer(dates);
        TimeBoundedTimer timeBoundedTimer = new TimeBoundedTimer(timer, 0, 2);
        //to step so null
        timeBoundedTimer.next();
        timeBoundedTimer.next();
        assertNull(timeBoundedTimer.next());
    }
}