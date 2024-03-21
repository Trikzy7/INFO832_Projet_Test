package timer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.TreeSet;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class DateTimerTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testHasNextIsFalse() {
        Timer testTimer = new DateTimer(new Vector<Integer>());
        assertFalse(testTimer.hasNext());
    }

    @Test
    void testHasNextIsTrue() {
        Vector<Integer> lapstimes = new Vector<Integer>();
        lapstimes.add(10);
        Timer testTimer = new DateTimer(lapstimes);
        assertTrue(testTimer.hasNext());
    }

    @Test
    void testNextIsFirstElement() {
        Vector<Integer> lapstimes = new Vector<Integer>();
        lapstimes.add(10);
        Timer testTimer = new DateTimer(lapstimes);
        assertEquals(10, (int) testTimer.next());
    }


    // Test aux limites : erreurs attendues
    @Test
    void testConstructTreesetNull() {
        try{
            new DateTimer((TreeSet<Integer>) null);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            // Success
        }
    }

    @Test
    void testConstructVectorNull() {
        try {
            new DateTimer((Vector<Integer>) null);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            // Success

        }
    }

    @Test
    void testNextVectorEmpty() {
        try {
            Timer testTimer = new DateTimer(new Vector<Integer>());
            testTimer.next();
            fail("Expected NullPointerException");
        } catch (NoSuchElementException e) {
            // Success
        }
    }
}