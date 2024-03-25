package action;

import org.junit.Before;
import org.junit.Test;
import timer.DateTimer;
import timer.Timer;

import java.util.TreeSet;
import java.util.Vector;

import static org.junit.Assert.*;

public class DiscreteActionOnOffDependentTest {

    private DiscreteActionOnOffDependent actionDependent;
    private Object testObject;
    private Timer timerOn;
    private Timer timerOff;

    @Before
    public void setUp() {
        testObject = new Object();
        TreeSet<Integer> datesOn = new TreeSet<>();
        TreeSet<Integer> datesOff = new TreeSet<>();
        datesOff.add(10);

        timerOn = new DateTimer(datesOn);
        timerOff = new DateTimer(datesOff);
    }

    @Test
    public void testInitializationWithEmptyDatesOn() {
        actionDependent = new DiscreteActionOnOffDependent(testObject, "methodNameOn", timerOn, "methodNameOff", timerOff);

        assertNotNull("L'action suivante ne devrait pas être nulle", actionDependent.next());

    }


}
