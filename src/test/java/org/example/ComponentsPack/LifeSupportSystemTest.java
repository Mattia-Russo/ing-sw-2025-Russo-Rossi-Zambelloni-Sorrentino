package org.example.ComponentsPack;

import junit.framework.TestCase;
import org.example.Model.ComponentsPack.AlienColour;
import org.example.Model.ComponentsPack.Connector;
import org.example.Model.ComponentsPack.Direction;
import org.example.Model.ComponentsPack.LifeSupportSystem;

public class LifeSupportSystemTest extends TestCase {

    public void testGetColour() {
        LifeSupportSystem l = new LifeSupportSystem(AlienColour.BROWN, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertEquals(AlienColour.BROWN, l.getColour());
    }
}