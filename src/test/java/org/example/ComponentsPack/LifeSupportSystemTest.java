package org.example.ComponentsPack;

import junit.framework.TestCase;

public class LifeSupportSystemTest extends TestCase {

    public void testGetColour() {
        LifeSupportSystem l = new LifeSupportSystem(AlienColour.ORANGE, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertEquals(AlienColour.ORANGE, l.getColour());
    }
}