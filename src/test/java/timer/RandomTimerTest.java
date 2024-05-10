package timer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomTimerTest {
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testDistribution2String() {
        assertEquals("POISSON", RandomTimer.distribution2String(RandomTimer.randomDistribution.POISSON));
    }

    @Test
    void testNext(){
        RandomTimer testTimer = null;
        try {
            testTimer = new RandomTimer(RandomTimer.randomDistribution.POISSON, 1);
        } catch (Exception e) {
            e.printStackTrace();
    }
        assertNotNull(testTimer);
        assertTrue(testTimer.next() >= 0);
    }
}