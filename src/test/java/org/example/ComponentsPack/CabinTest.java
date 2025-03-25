package org.example.ComponentsPack;

import junit.framework.TestCase;
import org.example.Model.ComponentsPack.Cabin;
import org.example.Model.ComponentsPack.Connector;
import org.example.Model.ComponentsPack.Direction;

public class CabinTest extends TestCase {

    public void testGetNumAstronauts() {

    }

    public void testGetAlien() {

    }

    public void testGetWithLifeSupport() {

    }

    public void testChangeWithLifeSupport() {
        Cabin c = new Cabin(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertFalse(c.getWithLifeSupport());
        c.changeWithLifeSupport(true);
        assertTrue(c.getWithLifeSupport());
    }

    public void testGetIsCentral() {
    }

    public void testChangeNumAstronauts() {

    }

    public void testAddAlien() {

    }
}