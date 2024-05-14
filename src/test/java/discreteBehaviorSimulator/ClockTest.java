package discreteBehaviorSimulator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClockTest {

    class TestClockObserver implements ClockObserver {
        private int time;
        private int nextTime;

        @Override
        public void clockChange(int time) {
            this.time = time;
        }

        @Override
        public void nextClockChange(int nextTime) {
            this.nextTime = nextTime;
        }

        public int getTime() {
            return time;
        }

        public int getNextTime() {
            return nextTime;
        }
    }

    private Clock clock;
    private TestClockObserver observer;

    @BeforeEach
    void setUp() {
        clock = Clock.getInstance();
        observer = new TestClockObserver();
        // Add the observer to the clock
        clock.addObserver(observer);
    }

    @AfterEach
    void tearDown() {
        // Remove the observer from the clock after each test
        clock.removeObserver(observer);
    }

    //    --------------------------------------------- TEST PAR AFFIRMATIONS ---------------------------------------------
    @Test
    void testSingleton() {
        Clock anotherClock = Clock.getInstance();
        assertSame(clock, anotherClock);
    }

    @Test
    void testAddObserver() {
        clock.setNextJump(10);
        assertEquals(10, observer.getNextTime(), "Observer did not get updated after being added");
    }


    @Test
    void testRemoveObserver() {
        clock.removeObserver(observer);
        clock.setNextJump(20);
        assertNotEquals(20, observer.getNextTime(), "Observer got updated after being removed");
    }

    @Test
    void testSetAndGetVirtual() {
        clock.setVirtual(true);
        assertTrue(clock.isVirtual());
        clock.setVirtual(false);
        assertFalse(clock.isVirtual());
    }

    @Test
    void testSetNextJump() {
        clock.setNextJump(20);
        assertEquals(20, observer.getNextTime());
    }


//    @Test
//    void testIncrease() throws Exception {
//        clock.setNextJump(5);
//        clock.increase(15);
//        assertEquals(15, observer.getTime());
//    }


//    //    --------------------------------------------- TEST PAR NEGATION ---------------------------------------------
    @Test
    void testAddNullObserver() {
        // Try to add a null observer
        assertThrows(NullPointerException.class, () -> {
            clock.addObserver(null);
            // If the observer was added without throwing an exception -> remove it
            clock.removeObserver(null);
        }, "Expected addObserver(null) to throw NullPointerException, but it didn't");
    }

    @Test
    void testRemoveNullObserver() {
        // Try to remove a null observer
        NullPointerException thrown = assertThrows(
                NullPointerException.class,
                () -> clock.removeObserver(null),
                "Expected removeObserver(null) to throw NullPointerException, but it didn't"
        );

        assertEquals("Expected removeObserver(null) to throw NullPointerException, but it didn't", thrown.getMessage());
    }

    //    --------------------------------------------- TEST AUX LIMITES ---------------------------------------------
    @Test
    void testIncreaseWithNegativeValue() {
        clock.setNextJump(5);
        Exception thrown = assertThrows(
                Exception.class,
                () -> clock.increase(-50),
                "Expected increase(-50) to throw an exception, but it didn't"
        );

        assertEquals("Unexpected time change", thrown.getMessage());
    }

    @Test
    void testSetNextJumpWithNegativeValue() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> clock.setNextJump(-10),
                "Expected setNextJump(-10) to throw an exception, but it didn't"
        );

        assertEquals("Unexpected time change", thrown.getMessage());
    }
}
