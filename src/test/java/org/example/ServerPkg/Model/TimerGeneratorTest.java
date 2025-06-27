package org.example.ServerPkg.Model;

import junit.framework.TestCase;
import org.example.ServerPkg.Model.Exceptions.InvalidMethodCallException;

public class TimerGeneratorTest extends TestCase {

    public void testStart() {
        TimerGenerator tg = new TimerGenerator();
        try {
            tg.start(); // Deve avviarsi senza eccezioni
        } catch (Exception e) {
            fail("start() non dovrebbe lanciare eccezioni al primo avvio, ma ha lanciato: " + e.getClass());
        }

        TimerGenerator tg1 = new TimerGenerator();
        tg1.start();
        boolean exceptionThrown = false;
        try {
            tg1.start();
        } catch (InvalidMethodCallException e) {
            exceptionThrown = true;
        }
        assertTrue("Se start() viene chiamato due volte, deve lanciare InvalidMethodCallException", exceptionThrown);
    }
    public void testStart2() throws InterruptedException {
        TimerGenerator tg = new TimerGenerator(2); // solo 2 secondi per il test
        tg.start();

        // Dopo 3 secondi il timer DEVE essere tornato disponibile
        Thread.sleep(2500);

        assertTrue("Dopo il termine del timer, isAvailable deve essere true", tg.isAvailable());

    }
    public void testStart3() throws InterruptedException {

    }
}