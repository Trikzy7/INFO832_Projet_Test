package discreteBehaviorSimulator;

import action.DiscreteActionInterface;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.mockito.Mockito.verify;import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

class DiscreteActionSimulatorTest {

//    @Test
//    void testConstructorLoggerInitialization() {
//        DiscreteActionSimulator simulator = new DiscreteActionSimulator();
//        Logger logger = simulator.getLogger(); // suppose we have a getter for the logger
//
//        assertNotNull(logger, "Logger should be initialized");
//    }

//    @Test
//    void testConstructorfileHandlerIsCreatedWithCorrectFileName() {
//        DiscreteActionSimulator simulator = new DiscreteActionSimulator();
//        FileHandler fileHandler = simulator.getFileHandler(); // suppose you have a getter for the FileHandler
//
//        String expectedFileName = "DiscreteActionSimulator.log"; // replace with your expected file name
//        String actualFileName = fileHandler.getPattern();
//
//        assertEquals(expectedFileName, actualFileName, "FileHandler should be created with the correct file name");
//    }

//    @Test
//    void testConstructorglobalClockIsSingleton() {
//        DiscreteActionSimulator simulator1 = new DiscreteActionSimulator();
//        DiscreteActionSimulator simulator2 = new DiscreteActionSimulator();
//
//        Clock clock1 = simulator1.getGlobalTime(); // suppose you have a getter for the global clock
//        Clock clock2 = simulator2.getGlobalTime(); // suppose you have a getter for the global clock
//
//        assertSame(clock1, clock2, "Global clock should be a singleton instance");
//    }

//    @Test
//    void testConstructorHandlesFileHandlerCreationFailureAndPrintsStackTrace() throws NoSuchFieldException, IllegalAccessException {
//        // Mock the Logger and FileHandler
//        Logger mockLogger = mock(Logger.class);
//        FileHandler mockFileHandler = mock(FileHandler.class);
//
//        // Create an IOException and mock its printStackTrace method
//        IOException mockException = mock(IOException.class);
//        doNothing().when(mockException).printStackTrace();
//
//        // Force the mock IOException when creating the FileHandler
//        doThrow(mockException).when(mockFileHandler).setLevel(any(Level.class));
//
//        // Create the simulator
//        DiscreteActionSimulator simulator = new DiscreteActionSimulator();
//
//        // Use reflection to set the logger and logFile fields
//        Field loggerField = DiscreteActionSimulator.class.getDeclaredField("logger");
//        loggerField.setAccessible(true);
//        loggerField.set(simulator, mockLogger);
//
//        Field logFileField = DiscreteActionSimulator.class.getDeclaredField("logFile");
//        logFileField.setAccessible(true);
//        logFileField.set(simulator, mockFileHandler);
//
//        // Call a method that uses the logger and logFile to trigger the exception
//        simulator.run();
//
//        // Verify that printStackTrace was called on the mock exception
//        verify(mockException).printStackTrace();
//    }

    @Test
    void testSetNbLoopSetsPositiveNumberOfLoopsAndStepToOne() throws NoSuchFieldException, IllegalAccessException {
        DiscreteActionSimulator simulator = new DiscreteActionSimulator();

        simulator.setNbLoop(5);

        Field nbLoopField = DiscreteActionSimulator.class.getDeclaredField("nbLoop");
        nbLoopField.setAccessible(true);
        int nbLoop = (int) nbLoopField.get(simulator);

        Field stepField = DiscreteActionSimulator.class.getDeclaredField("step");
        stepField.setAccessible(true);
        int step = (int) stepField.get(simulator);

        assertEquals(5, nbLoop, "nbLoop should be set to 5");
        assertEquals(1, step, "step should be set to 1");
    }

    @Test
    void testSetNbLoopSetsZeroNumberOfLoopsAndStepToMinusOne() throws NoSuchFieldException, IllegalAccessException {
        DiscreteActionSimulator simulator = new DiscreteActionSimulator();

        simulator.setNbLoop(0);

        Field nbLoopField = DiscreteActionSimulator.class.getDeclaredField("nbLoop");
        nbLoopField.setAccessible(true);
        int nbLoop = (int) nbLoopField.get(simulator);

        Field stepField = DiscreteActionSimulator.class.getDeclaredField("step");
        stepField.setAccessible(true);
        int step = (int) stepField.get(simulator);

        assertEquals(0, nbLoop, "nbLoop should be set to 0");
        assertEquals(-1, step, "step should be set to -1");
    }

    @Test
    void setNbLoopSetsNegativeNumberOfLoopsAndStepToMinusOne() throws NoSuchFieldException, IllegalAccessException {
        DiscreteActionSimulator simulator = new DiscreteActionSimulator();

        simulator.setNbLoop(-5);

        Field nbLoopField = DiscreteActionSimulator.class.getDeclaredField("nbLoop");
        nbLoopField.setAccessible(true);
        int nbLoop = (int) nbLoopField.get(simulator);

        Field stepField = DiscreteActionSimulator.class.getDeclaredField("step");
        stepField.setAccessible(true);
        int step = (int) stepField.get(simulator);

        assertEquals(0, nbLoop, "nbLoop should be set to 0");
        assertEquals(-1, step, "step should be set to -1");
    }

//    @Test
//    void addActionWithNextAddsActionToListAndSortsList() {
//        DiscreteActionSimulator simulator = new DiscreteActionSimulator();
//
//        // Create a mock action with hasNext() = true
//        DiscreteActionInterface action = mock(DiscreteActionInterface.class);
//        when(action.hasNext()).thenReturn(true);
//        when(action.next()).thenReturn(action);
//
//        simulator.addAction(action);
//
//        // Verify that the action was added to actionsList
//        assertEquals(1, simulator.getActionsList().size(), "The action should be added to actionsList");
//
//        // Verify that actionsList is sorted
//        Vector<DiscreteActionInterface> sortedList = new Vector<>(simulator.getActionsList());
//        sortedList.sort(Comparator.naturalOrder());
//        assertEquals(sortedList, simulator.getActionsList(), "actionsList should be sorted");
//    }

//    @Test
//    void testAddActionWithoutNextDoesNotAddActionToList() {
//        DiscreteActionSimulator simulator = new DiscreteActionSimulator();
//
//        // Create a mock action with hasNext() = false
//        DiscreteActionInterface action = mock(DiscreteActionInterface.class);
//        when(action.hasNext()).thenReturn(false);
//
//        simulator.addAction(action);
//
//        // Verify that no action was added to actionsList
//        assertEquals(0, simulator.getActionsList().size(), "No action should be added to actionsList");
//    }

//    @Test
//    void testAddActionMultipleActionsWithNextAddsAllActionsToListAndSortsList() {
//        DiscreteActionSimulator simulator = new DiscreteActionSimulator();
//
//        // Create several mock actions with hasNext() = true
//        DiscreteActionInterface action1 = mock(DiscreteActionInterface.class);
//        when(action1.hasNext()).thenReturn(true);
//        when(action1.next()).thenReturn(action1);
//
//        DiscreteActionInterface action2 = mock(DiscreteActionInterface.class);
//        when(action2.hasNext()).thenReturn(true);
//        when(action2.next()).thenReturn(action2);
//
//        DiscreteActionInterface action3 = mock(DiscreteActionInterface.class);
//        when(action3.hasNext()).thenReturn(true);
//        when(action3.next()).thenReturn(action3);
//
//        // Add the actions to the simulator
//        simulator.addAction(action1);
//        simulator.addAction(action2);
//        simulator.addAction(action3);
//
//        // Verify that all actions were added to actionsList
//        assertEquals(3, simulator.getActionsList().size(), "All actions should be added to actionsList");
//
//        // Verify that actionsList is sorted
//        Vector<DiscreteActionInterface> sortedList = new Vector<>(simulator.getActionsList());
//        sortedList.sort(Comparator.naturalOrder());
//        assertEquals(sortedList, simulator.getActionsList(), "actionsList should be sorted");
//    }

//    @Test
//    void testAddActionDuplicateActionsIncreasesActionsListSizeByOne() {
//        DiscreteActionSimulator simulator = new DiscreteActionSimulator();
//
//        // Create a mock action with hasNext() = true
//        DiscreteActionInterface action = mock(DiscreteActionInterface.class);
//        when(action.hasNext()).thenReturn(true);
//        when(action.next()).thenReturn(action);
//
//        int initialSize = simulator.getActionsList().size();
//
//        // Add the same action twice
//        simulator.addAction(action);
//        simulator.addAction(action);
//
//        // Verify that actionsList size increased by 1
//        assertEquals(initialSize + 1, simulator.getActionsList().size(), "Adding the same action twice should increase actionsList size by 1");
//    }

//    @Test
//    void testAddActionAndTriggerSortAddsActionToCorrectPositionInList() {
//        DiscreteActionSimulator simulator = new DiscreteActionSimulator();
//
//        // Create several mock actions with hasNext() = true and different laps times
//        DiscreteActionInterface action1 = mock(DiscreteActionInterface.class);
//        when(action1.hasNext()).thenReturn(true);
//        when(action1.next()).thenReturn(action1);
//        when(action1.getCurrentLapsTime()).thenReturn(10);
//
//        DiscreteActionInterface action2 = mock(DiscreteActionInterface.class);
//        when(action2.hasNext()).thenReturn(true);
//        when(action2.next()).thenReturn(action2);
//        when(action2.getCurrentLapsTime()).thenReturn(20);
//
//        // Add the actions to the simulator
//        simulator.addAction(action1);
//        simulator.addAction(action2);
//
//        // Create a new action with a shorter laps time
//        DiscreteActionInterface action3 = mock(DiscreteActionInterface.class);
//        when(action3.hasNext()).thenReturn(true);
//        when(action3.next()).thenReturn(action3);
//        when(action3.getCurrentLapsTime()).thenReturn(5);
//
//        // Add the new action to the simulator
//        simulator.addAction(action3);
//
//        // Verify that all actions were added to actionsList
//        assertEquals(3, simulator.getActionsList().size(), "All actions should be added to actionsList");
//
//        // Verify that actionsList is sorted and action3 is at the correct position
//        assertEquals(action3, simulator.getActionsList().get(0), "actionsList should be sorted and action3 should be at the correct position");
//    }


//    @Test
//    void testNextLapsTimeWithNonEmptyListReturnsTimeOfNextAction() {
//        DiscreteActionSimulator simulator = new DiscreteActionSimulator();
//
//        // Create a mock action with hasNext() = true and a specific laps time
//        DiscreteActionInterface action = mock(DiscreteActionInterface.class);
//        when(action.hasNext()).thenReturn(true);
//        when(action.next()).thenReturn(action);
//        when(action.getCurrentLapsTime()).thenReturn(10);
//
//        simulator.addAction(action);
//
//        // Verify that nextLapsTime returns the laps time of the first action
//        assertEquals(10, simulator.nextLapsTime(), "nextLapsTime should return the laps time of the first action");
//    }

//    @Test
//    void testRunActionFirstActionSuccessfullyExecutesActionMethodIncreasesGlobalTimeAndLogsMessage() throws NoSuchFieldException, IllegalAccessException {
//        DiscreteActionSimulator simulator = new DiscreteActionSimulator();
//
//        // Create a mock action with hasNext() = true and a specific laps time
//        DiscreteActionInterface action = mock(DiscreteActionInterface.class);
//        when(action.hasNext()).thenReturn(true);
//        when(action.next()).thenReturn(action);
//        when(action.getCurrentLapsTime()).thenReturn(10);
//
//        simulator.addAction(action);
//
//        // Get the initial global time
//        Clock globalTime = simulator.getGlobalTime();
//        int initialTime = globalTime.getTime();
//
//        // Run the first action
//        simulator.runAction();
//
//        // Verify that the action method was executed without exception
//        verify(action).run();
//
//        // Verify that globalTime was increased by the sleepTime
//        assertEquals(initialTime + 10, globalTime.getTime(), "globalTime should be increased by the sleepTime");
//
//        // Verify that a message was logged
//        Logger logger = simulator.getLogger(); // suppose you have a getter for the logger
//        verify(logger).log(Level.FINE, anyString());
//    }

//    @Test
//    void updateTimesOfActionsDecreasesTimeOfRemainingActionsAndSortsActions() throws NoSuchFieldException, IllegalAccessException {
//        DiscreteActionSimulator simulator = new DiscreteActionSimulator();
//
//        // Create several mock actions with hasNext() = true and different laps times
//        DiscreteActionInterface action1 = mock(DiscreteActionInterface.class);
//        when(action1.hasNext()).thenReturn(true);
//        when(action1.next()).thenReturn(action1);
//        when(action1.getCurrentLapsTime()).thenReturn(20);
//
//        DiscreteActionInterface action2 = mock(DiscreteActionInterface.class);
//        when(action2.hasNext()).thenReturn(true);
//        when(action2.next()).thenReturn(action2);
//        when(action2.getCurrentLapsTime()).thenReturn(30);
//
//        // Add the actions to the simulator
//        simulator.addAction(action1);
//        simulator.addAction(action2);
//
//        // Call updateTimes with runningTimeOf1stCapsul = 10
//        simulator.updateTimes(10);
//
//        // Verify that the time of all remaining actions was decreased by runningTimeOf1stCapsul
//        assertEquals(10, action1.getCurrentLapsTime(), "Time of action1 should be decreased by runningTimeOf1stCapsul");
//        assertEquals(20, action2.getCurrentLapsTime(), "Time of action2 should be decreased by runningTimeOf1stCapsul");
//
//        // Verify that actionsList is sorted
//        assertEquals(action1, simulator.getActionsList().get(0), "actionsList should be sorted");
//    }

//    @Test
//    void testUpdateTimesWithEmptyListDoesNotChangeListOrThrowException() {
//        DiscreteActionSimulator simulator = new DiscreteActionSimulator();
//
//        // Call updateTimes with runningTimeOf1stCapsul = 10
//        assertDoesNotThrow(() -> simulator.updateTimes(10));
//
//        // Verify that actionsList is still empty
//        assertTrue(simulator.getActionsList().isEmpty(), "actionsList should still be empty");
//    }

    @Test
    void testRunSimulationToCompletionExecutesForDefinedNumberOfLoopsThenStops() {
        DiscreteActionSimulator simulator = new DiscreteActionSimulator();

        // Create a mock action with hasNext() = true for 5 times, then false
        DiscreteActionInterface action = mock(DiscreteActionInterface.class);
        when(action.hasNext()).thenReturn(true, true, true, true, true, false);
        when(action.next()).thenReturn(action);
        when(action.getCurrentLapsTime()).thenReturn(10);

        // Add the action to the simulator
        simulator.addAction(action);

        // Start the simulation
        simulator.start();

        // Wait for the simulation to complete
        int counter = 0;
        while (simulator.getRunning() && counter < 100) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter++;
        }

        // Verify that the simulation has stopped
        assertFalse(simulator.getRunning(), "The simulation should have stopped");
    }

    @Test
    void testRunSimulationInfinitelyRunsUntilManuallyStopped() throws InterruptedException {
        DiscreteActionSimulator simulator = new DiscreteActionSimulator();

        // Set nbLoop to 0 for infinite simulation
        simulator.setNbLoop(0);

        // Create a mock action with hasNext() = true
        DiscreteActionInterface action = mock(DiscreteActionInterface.class);
        when(action.hasNext()).thenReturn(true);
        when(action.next()).thenReturn(action);

        // Add the action to the simulator
        simulator.addAction(action);

        // Start the simulation
        simulator.start();

        // Wait for some time
        Thread.sleep(1000);

        // Manually stop the simulation
        simulator.stop();

        // Verify that the simulation has stopped
        assertFalse(simulator.getRunning(), "The simulation should have stopped");
    }

    @Test
    void testRunSimulationWithEmptyActionsListExecutesNoActionsAndStops() {
        DiscreteActionSimulator simulator = new DiscreteActionSimulator();

        // Start the simulation
        simulator.start();

        // Wait for the simulation to complete
        while (simulator.getRunning()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Verify that the simulation has stopped
        assertFalse(simulator.getRunning(), "The simulation should have stopped");
    }

    @Test
    void testStartSimulationSetsRunningToTrue() {
        DiscreteActionSimulator simulator = new DiscreteActionSimulator();

        // Start the simulation
        simulator.start();

        // Verify that running is true
        assertTrue(simulator.getRunning(), "The simulation should be running");
    }

    @Test
    void testStopSimulationSetsRunningToFalse() throws InterruptedException {
        DiscreteActionSimulator simulator = new DiscreteActionSimulator();

        // Start the simulation
        simulator.start();

        // Wait for the simulation to start
        Thread.sleep(100);

        // Stop the simulation
        simulator.stop();

        // Verify that running is false
        assertFalse(simulator.getRunning(), "The simulation should have stopped");
    }

    @Test
    void testToStringWithActionsReturnsStringContainingActionsState() {
        DiscreteActionSimulator simulator = new DiscreteActionSimulator();

        // Create several mock actions with hasNext() = true
        DiscreteActionInterface action1 = mock(DiscreteActionInterface.class);
        when(action1.hasNext()).thenReturn(true);
        when(action1.next()).thenReturn(action1);
        when(action1.toString()).thenReturn("Action1");

        DiscreteActionInterface action2 = mock(DiscreteActionInterface.class);
        when(action2.hasNext()).thenReturn(true);
        when(action2.next()).thenReturn(action2);
        when(action2.toString()).thenReturn("Action2");

        // Add the actions to the simulator
        simulator.addAction(action1);
        simulator.addAction(action2);

        // Call toString
        String result = simulator.toString();

        // Verify that the string contains the state of the actions
        assertTrue(result.contains("Action1"), "The string should contain the state of action1");
        assertTrue(result.contains("Action2"), "The string should contain the state of action2");
    }

    @Test
    void testToStringWithEmptyActionsListReturnsStringIndicatingNoActions() {
        DiscreteActionSimulator simulator = new DiscreteActionSimulator();

        // Call toString
        String result = simulator.toString();

        // Verify that the string indicates there are no actions
        assertTrue(result.contains("No actions"), "The string should indicate there are no actions");
    }

//    @Test
//    void testNextLapsTimeWithEmptyListThrowsIndexOutOfBoundsException() {
//        DiscreteActionSimulator simulator = new DiscreteActionSimulator();
//
//        // Call nextLapsTime and expect an IndexOutOfBoundsException
//        assertThrows(IndexOutOfBoundsException.class, simulator::nextLapsTime);
//    }

//    @Test
//    void testRunActionWithExceptionCatchesExceptionAndPrintsStackTrace() throws NoSuchMethodException {
//        DiscreteActionSimulator simulator = new DiscreteActionSimulator();
//
//        // Create a mock action with hasNext() = true and that throws an exception when invoke() is called
//        DiscreteActionInterface action = mock(DiscreteActionInterface.class);
//        when(action.hasNext()).thenReturn(true);
//        when(action.next()).thenReturn(action);
//        when(action.getCurrentLapsTime()).thenReturn(10);
//        doThrow(new RuntimeException()).when(action).run();
//
//        simulator.addAction(action);
//
//        // Call runAction and expect no exception
//        assertDoesNotThrow(() -> simulator.runAction());
//
//        // Verify that printStackTrace was called on the RuntimeException
//        verify(action).run();
//    }

//    @Test
//    void setLargeNumberOfLoopsSetsNbLoopToMaxInteger() {
//        DiscreteActionSimulator simulator = new DiscreteActionSimulator();
//
//        // Set nbLoop to Integer.MAX_VALUE
//        simulator.setNbLoop(Integer.MAX_VALUE);
//
//        // Verify that nbLoop is Integer.MAX_VALUE
//        assertEquals(Integer.MAX_VALUE, simulator.getNbLoop(), "nbLoop should be set to Integer.MAX_VALUE");
//    }

    @Test
    void testSetMinimumIntegerValueForLoopsSetsNbLoopToZeroAndStepToMinusOne() throws NoSuchFieldException, IllegalAccessException {
        DiscreteActionSimulator simulator = new DiscreteActionSimulator();

        // Set nbLoop to Integer.MIN_VALUE
        simulator.setNbLoop(Integer.MIN_VALUE);

        // Access the private fields nbLoop and step
        Field nbLoopField = DiscreteActionSimulator.class.getDeclaredField("nbLoop");
        nbLoopField.setAccessible(true);
        int nbLoop = (int) nbLoopField.get(simulator);

        Field stepField = DiscreteActionSimulator.class.getDeclaredField("step");
        stepField.setAccessible(true);
        int step = (int) stepField.get(simulator);

        // Verify that nbLoop is 0 and step is -1
        assertEquals(0, nbLoop, "nbLoop should be set to 0");
        assertEquals(-1, step, "step should be set to -1");
    }


}