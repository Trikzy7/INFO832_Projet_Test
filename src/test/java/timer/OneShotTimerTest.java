package timer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OneShotTimerTest {

    @Test
    void testHasNextNotFinished() {
        OneShotTimer ost = new OneShotTimer(3);
        ost.next();
        assertTrue(ost.hasNext());
    }
    @Test
    void testHasNextFinished() {
        OneShotTimer ost = new OneShotTimer(1);
        ost.next();
        assertFalse(ost.hasNext());
    }
    @Test
    void testNextExisted(){
        OneShotTimer ost = new OneShotTimer(3);
        int ostNext = ost.next();
        assertEquals(3, ostNext);
    }

}