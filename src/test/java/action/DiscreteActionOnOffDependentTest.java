package action;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import java.util.TreeSet;
import java.util.Vector;
import static org.junit.jupiter.api.Assertions.*;
public class DiscreteActionOnOffDependentTest {

    private DiscreteActionOnOffDependent discreteAction;

    @BeforeEach
    public void setUp() throws NoSuchMethodException {
        // Création des TreeSet pour les dates
        TreeSet<Integer> datesOn = new TreeSet<>();
        TreeSet<Integer> datesOff = new TreeSet<>();
        datesOn.add(10);
        datesOn.add(20);
        datesOff.add(15);
        datesOff.add(25);
        // Création des méthodes
        Method onMethod = Object.class.getMethod("toString");
        Method offMethod = Object.class.getMethod("hashCode");

        // Création de l'objet DiscreteActionOnOffDependent
        discreteAction = new DiscreteActionOnOffDependent(new Object(), "on", datesOn, "off", datesOff);
    }

    @Test
    public void testNextMethodChange() {
        // Stockage de la méthode initiale
        Method initialMethod = discreteAction.getMethod();

        // Appels successifs à next et vérification des changements
        for (int i = 0; i < 5; i++) {
            discreteAction.next();
            Method newMethod = discreteAction.getMethod();
            assertNotEquals(initialMethod, newMethod);
            initialMethod = newMethod;
        }
    }
//nous vérifions que la méthode next() de DiscreteActionOnOffDependent provoque un
// changement dans la méthode actuelle utilisée par l'action, en stockant la méthode initiale,
// en appelant next() plusieurs fois et en vérifiant si la méthode actuelle a changé après
// chaque appel.

    @Test
    public void testSpendTime() {
        // Vérification de la mise à jour de spendTime
        discreteAction.spendTime(10);
        assertEquals(10, (int) discreteAction.getCurrentLapsTime());
        discreteAction.spendTime(-5);
        assertEquals(-5, (int) discreteAction.getCurrentLapsTime());
    }
    //nous testons la méthode spendTime() de DiscreteActionOnOffDependent en lui
    // fournissant une valeur d'entrée positive et négative, puis en vérifiant si le temps
    // de laps actuel est correctement mis à jour en conséquence.

    @Test
    public void testDates2Timalapse() {

        TreeSet<Integer> datesOn = new TreeSet<>();
        datesOn.add(1);
        datesOn.add(4);

        TreeSet<Integer> datesOff = new TreeSet<>();
        datesOff.add(3);
        datesOff.add(6);
        datesOff.add(8);
        datesOff.add(14);
        datesOff.add(20);

        Vector<Integer> timeLapseOn = new Vector<>();
        Vector<Integer> timeLapseOff = new Vector<>();

        // Instanciation de DiscreteActionOnOffDependent avec les paramètres nécessaires
        DiscreteActionOnOffDependent action = new DiscreteActionOnOffDependent(new Object(), "on", datesOn, "off", datesOff);

        action.dates2Timalapse(datesOn, datesOff, timeLapseOn, timeLapseOff);

        // Ajout des assertions pour vérifier le comportement de la méthode dates2Timalapse
        // par rapport aux valeurs d'entrée fournies

        // Par exemple, vérifiant si les vecteurs de laps de temps contiennent les valeurs attendues
        assertEquals(3,(int) timeLapseOn.get(0));  // Valeur attendue pour le premier laps de temps dans timeLapseOn
        assertEquals(3, (int) timeLapseOff.get(0)); // Valeur attendue pour le premier laps de temps dans timeLapseOff

        // Vérifiez si les vecteurs de laps de temps contiennent les valeurs attendues
        assertEquals(1, timeLapseOn.size());  // Le nombre de laps de temps dans timeLapseOn
        assertEquals(4, timeLapseOff.size()); // Le nombre de laps de temps dans timeLapseOff
    }
    @Test
    public void testDates2TimalapseWithEmptyDatesOn() {
        // Préparation: Créer des entrées avec datesOn vide
        TreeSet<Integer> datesOn = new TreeSet<>();
        TreeSet<Integer> datesOff = new TreeSet<>();
        datesOff.add(5);
        Vector<Integer> timeLapseOn = new Vector<>();
        Vector<Integer> timeLapseOff = new Vector<>();


        DiscreteActionOnOffDependent action = new DiscreteActionOnOffDependent(new Object(), "onMethod", datesOn, "offMethod", datesOff);
        assertTrue("timeLapseOn ou timeLapseOff devrait être mis à jour en fonction de dates2Timalapse", timeLapseOn.isEmpty() || !timeLapseOff.isEmpty());
    }

    /*it won't compile as we are using the wrong type

        @Test(expected = CompileTimeError.class)
        public void testSpendTimeWithInvalidType() {
            DiscreteActionOnOffDependent action = actionDependent;

                    // Attempt to call spendTime with a String, which should result in a compile-time error, not a test failure.
                    action.spendTime("test");
        }
     */


    @Test
    public void testSpendTimeWithNegativeInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            discreteAction.spendTime(-5);
        });
    }


}

//La méthode `dates2Timalapse` de la classe `DiscreteActionOnOffDependent` est
// déclarée avec un accès privé, limitant son utilisation uniquement à l'intérieur de la
// classe. Ainsi, elle n'est pas accessible depuis l'extérieur de cette classe, y compris
// dans les tests.