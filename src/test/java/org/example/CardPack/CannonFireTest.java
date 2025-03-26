package org.example.CardPack;

import junit.framework.TestCase;
import org.example.Model.ComponentsPack.Direction;
import org.example.Model.CardPack.CannonFire;

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