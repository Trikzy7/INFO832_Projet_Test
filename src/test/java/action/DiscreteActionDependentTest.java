package action;

import org.junit.Before;
import org.junit.Test;
import timer.OneShotTimer;
import java.lang.reflect.Field;
import java.util.TreeSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.lang.reflect.Method;
import static org.junit.Assert.assertNotSame;



public class DiscreteActionDependentTest {

    private DiscreteActionDependent actionDependent;
    private Runnable o;
    private OneShotTimer timerDependence;

    @Before
    public void setUp() throws Exception {
        o = () -> {};
        timerDependence = new OneShotTimer(1);
        actionDependent = new DiscreteActionDependent(o, "run", timerDependence);
    }

    @Test
    public void testAddDependence() throws Exception {
        Field depedentActionsField = DiscreteActionDependent.class.getDeclaredField("depedentActions");
        depedentActionsField.setAccessible(true);
        @SuppressWarnings("unchecked")
        TreeSet<DiscreteAction> depedentActions = (TreeSet<DiscreteAction>) depedentActionsField.get(actionDependent);

        int initialSize = depedentActions.size();
        actionDependent.addDependence(o, "run", new OneShotTimer(2));
        int newSize = depedentActions.size();

        assertEquals("La taille de depedentActions doit augmenter de 1", initialSize + 1, newSize);
        System.out.println("Le testAddDependence a été exécuté avec succès.");
    }
    @Test
    public void testNextChangesState() {
        actionDependent.addDependence(new Object(), "toString", new OneShotTimer(2)); // Exemple d'ajout

        Object oldObject = actionDependent.getObject();
        Method oldMethod = actionDependent.getMethod();

        actionDependent.next(); // Suppose que cela change l'action courante

        Object newObject = actionDependent.getObject();
        Method newMethod = actionDependent.getMethod();

        assertNotSame("L'objet devrait être différent après next", oldObject, newObject);
        assertNotSame("La méthode devrait être différente après next", oldMethod, newMethod);

    }




}
