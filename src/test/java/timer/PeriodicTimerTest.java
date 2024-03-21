package timer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PeriodicTimerTest {





    @Test
    void testHasNextFinished() {
        PeriodicTimer pt = new PeriodicTimer(3);
        pt.next();
        pt.next();
        pt.next();
        assertFalse(pt.hasNext());
    }

    @Test
    void testGetPeriod(){
        PeriodicTimer pt = new PeriodicTimer(3);
        assertEquals(3, pt.getPeriod());
    }

    @Test
    void testNext(){
        PeriodicTimer pt = new PeriodicTimer(10);
        pt.next();
        int next = pt.next();

        assertEquals(3, next);
    }
}