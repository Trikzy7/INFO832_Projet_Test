package timer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class MergedTimerTest {

    @Test
    void testHasNextFirstNoNext() {
        TreeSet<Integer> dates1 = new TreeSet<Integer>();
        TreeSet<Integer> dates2 = new TreeSet<Integer>();
        dates2.add(1);
        dates2.add(2);

        Timer timer1 = new DateTimer(dates1);
        Timer timer2 = new DateTimer(dates2);
        //timer1.hasNext() = false
        //timer2.hasNext() = true

        MergedTimer mergedTimer = new MergedTimer(timer1, timer2);
        assertFalse(mergedTimer.hasNext());
    }

    @Test
    void testHasNextSecondNoNext() {
        TreeSet<Integer> dates1 = new TreeSet<Integer>();
        TreeSet<Integer> dates2 = new TreeSet<Integer>();
        dates2.add(1);
        dates2.add(2);

        Timer timer1 = new DateTimer(dates2);
        Timer timer2 = new DateTimer(dates1);
        MergedTimer mergedTimer = new MergedTimer(timer1, timer2);
        assertFalse(mergedTimer.hasNext());
    }


    @Test
    void testHasNextBothNoNext() {
        TreeSet<Integer> dates1 = new TreeSet<Integer>();
        TreeSet<Integer> dates2 = new TreeSet<Integer>();

        Timer timer1 = new DateTimer(dates1);
        Timer timer2 = new DateTimer(dates2);
        //timer1.hasNext() = false
        //timer2.hasNext() = false

        MergedTimer mergedTimer = new MergedTimer(timer1, timer2);
        assertFalse(mergedTimer.hasNext());
    }

    @Test
    void testHasNextBothNext() {
        TreeSet<Integer> dates1 = new TreeSet<Integer>();
        dates1.add(1);
        TreeSet<Integer> dates2 = new TreeSet<Integer>();
        dates2.add(1);

        Timer timer1 = new DateTimer(dates1);
        Timer timer2 = new DateTimer(dates2);
        //timer1.hasNext() = true
        //timer2.hasNext() = true

        MergedTimer mergedTimer = new MergedTimer(timer1, timer2);
        assertTrue(mergedTimer.hasNext());
    }


    @Test
    void testNextBothEmpty(){
        TreeSet<Integer> dates1 = new TreeSet<Integer>();
        TreeSet<Integer> dates2 = new TreeSet<Integer>();

        Timer timer1 = new DateTimer(dates1);
        Timer timer2 = new DateTimer(dates2);

        MergedTimer mergedTimer = new MergedTimer(timer1, timer2);
        assertNull(mergedTimer.next());
    }

    @Test
    void testNextFirstEmpty(){
        TreeSet<Integer> dates1 = new TreeSet<Integer>();
        TreeSet<Integer> dates2 = new TreeSet<Integer>();
        dates2.add(1);

        Timer timer1 = new DateTimer(dates1);
        Timer timer2 = new DateTimer(dates2);

        MergedTimer mergedTimer = new MergedTimer(timer1, timer2);
        assertNull(mergedTimer.next());
    }

    @Test
    void testNextSecondEmpty(){
        TreeSet<Integer> dates1 = new TreeSet<Integer>();
        TreeSet<Integer> dates2 = new TreeSet<Integer>();
        dates1.add(1);

        Timer timer1 = new DateTimer(dates1);
        Timer timer2 = new DateTimer(dates2);

        MergedTimer mergedTimer = new MergedTimer(timer1, timer2);
        assertNull(mergedTimer.next());
    }
    @Test
    void testNextNotFinished(){
        TreeSet<Integer> dates1 = new TreeSet<Integer>();
        TreeSet<Integer> dates2 = new TreeSet<Integer>();
        dates1.add(3);
        dates1.add(5);
        dates1.add(8);
        dates2.add(5);
        dates2.add(7);

        Timer timer1 = new DateTimer(dates1);
        Timer timer2 = new DateTimer(dates2);

        MergedTimer mergedTimer = new MergedTimer(timer1, timer2);

        int result = mergedTimer.next();
        assertEquals(8, result);
    }


}