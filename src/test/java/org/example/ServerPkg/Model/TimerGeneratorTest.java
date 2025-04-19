package org.example.ServerPkg.Model;

import junit.framework.TestCase;
import org.example.ServerPkg.Model.Exceptions.InvalidMethodCallException;

public class TimerGeneratorTest extends TestCase {
    //2 seconds were set in the constructor for this test
    public void testStart() throws InterruptedException {
        TimerGenerator tg = new TimerGenerator();
        assertEquals(1, tg.start());
        assertEquals(2, tg.start());
        assertEquals(3, tg.start());
        assertEquals(4, tg.start());
    }
}