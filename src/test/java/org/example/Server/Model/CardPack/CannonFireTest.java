package org.example.Server.Model.CardPack;

import junit.framework.TestCase;
import org.example.Server.Model.ComponentsPack.Direction;

public class CannonFireTest extends TestCase {

    public void testGetDirection() {
        CannonFire c= new CannonFire(0, Direction.NORTH);
        assertEquals(Direction.NORTH, c.getDirection());
    }

    public void testGetType() {
        CannonFire c= new CannonFire(0, Direction.NORTH);
        assertEquals(0,c.getType());
    }
}