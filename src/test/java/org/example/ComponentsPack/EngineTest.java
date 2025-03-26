package org.example.ComponentsPack;

import junit.framework.TestCase;
import org.example.Model.ComponentsPack.Connector;
import org.example.Model.ComponentsPack.Direction;
import org.example.Model.ComponentsPack.Engine;

public class EngineTest extends TestCase {

    public void testGetPower() {
        Engine e = new Engine(2, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertEquals(2, e.getPower());
    }
}