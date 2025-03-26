package org.example.ComponentsPack;

import junit.framework.TestCase;
import org.example.Model.ComponentsPack.*;

public class CabinTest extends TestCase {

    public void testGetNumAstronauts() {
        Cabin c = new Cabin(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        c.changeNumAstronauts(2);
        assertEquals(2,c.getNumAstronauts());
    }

    public void testGetAlien() {
        Cabin c = new Cabin(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        Alien a= new Alien(AlienColour.BROWN);
        LifeSupportSystem l=new LifeSupportSystem(AlienColour.BROWN,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        l.addLifeSupport(c);
        c.addAlien(a);
        assertEquals(a, c.getAlien());
    }

    public void testGetWithLifeSupport() {
        Cabin c = new Cabin(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        Alien a= new Alien(AlienColour.BROWN);
        LifeSupportSystem l=new LifeSupportSystem(AlienColour.BROWN,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        c.addLifeSupport(l);
        assertTrue(c.getWithLifeSupport());
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