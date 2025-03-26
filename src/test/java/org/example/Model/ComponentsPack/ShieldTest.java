package org.example.Model.ComponentsPack;

import junit.framework.TestCase;

public class ShieldTest extends TestCase {

    public void testGetDirection1() {

    }

    public void testGetDirection2() {
        Shield s = new Shield(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE}, Direction.EAST);
        assertEquals(Direction.EAST, s.getDirection2());
    }
}