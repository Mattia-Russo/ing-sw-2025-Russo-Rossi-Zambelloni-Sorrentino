package org.example.ComponentsPack;

import junit.framework.TestCase;

public class AlienTest extends TestCase {

    public void testGetColour() {

    }

    public void testGetCabin() {

    }

    public void testSetCabin(){
        Alien a = new Alien(AlienColour.ORANGE);
        Cabin c = new Cabin(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertNull(a.getCabin());
        a.setCabin(c);
        assertEquals(c, a.getCabin());

    }
}