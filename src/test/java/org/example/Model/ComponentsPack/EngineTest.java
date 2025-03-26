package org.example.Model.ComponentsPack;

import junit.framework.TestCase;

public class EngineTest extends TestCase {

    public void testGetPower() {
        Engine e = new Engine(2, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertEquals(2, e.getPower());
    }
}