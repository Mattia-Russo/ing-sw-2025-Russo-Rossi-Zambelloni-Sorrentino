package org.example.ComponentsPack;

import junit.framework.TestCase;
import org.example.Model.ComponentsPack.Cannon;
import org.example.Model.ComponentsPack.Connector;
import org.example.Model.ComponentsPack.Direction;

public class CannonTest extends TestCase {

    public void testGetPower() {
        Cannon c = new Cannon(1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertEquals(1, c.getPower());
    }
}