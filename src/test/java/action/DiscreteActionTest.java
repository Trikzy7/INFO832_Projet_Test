package action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.Test;
import timer.Timer;
import java.lang.reflect.Method;

public class DiscreteActionTest {

    private DiscreteAction action;
    private Timer timer;
    private Object testObject;
    private String methodName = "toString";
    private Integer initialLapsTime = 10; // Initial lapsTime to be set for testing

    @Before
    public void setUp() throws NoSuchMethodException, SecurityException {
        // Initialize necessary objects
        testObject = new Object();
        //timer = new Timer(); // Timer is an interface

        // Assume setCurrentLapsTime is a method in DiscreteAction to set the lapsTime for testing
        action = new DiscreteAction(testObject, methodName, timer);
        //action.setCurrentLapsTime(initialLapsTime); // This method must be added to your DiscreteAction class
    }

    @Test
    public void testDiscreteActionConstructor() {
        // Vérification que l'objet est correctement initialisé
        assertEquals("L'objet passé au constructeur doit être le même que l'objet retourné par getObject()", testObject, action.getObject());

        // Vérification que la méthode est correctement initialisée
        Method expectedMethod = null;
        try {
            expectedMethod = testObject.getClass().getMethod(methodName);
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        assertEquals("La méthode passée au constructeur doit être la même que celle retournée par getMethod()", expectedMethod, action.getMethod());

        // Vérification que le Timer est correctement initialisé
        // assertEquals("Le timer passé au constructeur doit être le même que celui retourné par getTimmer()", timer, action.getTimmer()); // Uncomment when getTimmer method is available
    }

    @Test
    public void testSpendTime() {
        // Given
        int timeToSpend = 5;
        Integer expectedLapsTime = initialLapsTime - timeToSpend;

        // When
        action.spendTime(timeToSpend);

        // Then
        assertNotNull("LapsTime should not be null after spendTime", action.getCurrentLapsTime());
        assertEquals("LapsTime should be decreased by the time spent", expectedLapsTime, action.getCurrentLapsTime());
    }
    @Test
    public void testNextMethodUpdatesLapsTime() {

        //action = new DiscreteAction(testObject, methodName, mockTimer);

        // Set the initial lapsTime
        //action.setCurrentLapsTime(initialLapsTime); // there is no method to set LapsTime

        // Capture the old lapsTime before calling next()
        Integer oldLapsTime = action.getCurrentLapsTime();

        // When
        action.next(); // Calls Timer.next() internally, which is mocked

        // Then
        Integer newLapsTime = action.getCurrentLapsTime();
        assertNotNull("LapsTime should not be null after next()", newLapsTime);
        assertNotEquals("LapsTime should be updated (not equal to the old value) after next()",
                oldLapsTime, newLapsTime);
        // Optionally, check if the newLapsTime matches what the mock Timer returns
        /*assertEquals("LapsTime should be updated to the new value provided by the Timer",
                mockTimer.next(), newLapsTime);*/ //Waiting for mock in pom.xml
    }

}
