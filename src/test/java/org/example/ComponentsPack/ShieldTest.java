package org.example.ComponentsPack;

import junit.framework.TestCase;
import org.example.Model.ComponentsPack.Connector;
import org.example.Model.ComponentsPack.Direction;
import org.example.Model.ComponentsPack.Shield;

public class ShieldTest extends TestCase {

    public void testGetDirection1() {

    }

    public void testGetDirection2() {
        Shield s = new Shield(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE}, Direction.EAST);
        assertEquals(Direction.EAST, s.getDirection2());
    }
}