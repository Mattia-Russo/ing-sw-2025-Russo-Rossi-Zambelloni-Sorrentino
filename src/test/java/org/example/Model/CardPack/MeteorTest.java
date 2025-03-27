package org.example.Model.CardPack;

import junit.framework.TestCase;
import org.example.Model.ComponentsPack.Direction;

public class MeteorTest extends TestCase {

    public void testGetDirection() {
        Meteor m=new Meteor(1,  Direction.EAST);
        assertEquals(Direction.EAST,m.getDirection());
    }

    public void testGetType() {
        Meteor m=new Meteor(0,  Direction.NORTH);
        assertEquals( Direction.NORTH,m.getDirection());
    }
}