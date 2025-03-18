package org.example.ComponentsPack;

import junit.framework.TestCase;

public class CabinTest extends TestCase {

    public void testGetNumAstronauts() {
        Cabin c = new Cabin(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertEquals(0, c.getNumAstronauts());
        c.changeNumAstronauts(3);
        assertEquals(3, c.getNumAstronauts());
    }

    public void testGetAlien() {
    }

    public void testGetWithLifeSupport() {
    }

    public void testChangeWithLifeSupport() {
    }

    public void testGetIsCentral() {
    }

    public void testChangeNumAstronauts() {

    }

    public void testAddAlien() {
    }
}