package org.example.CardPack;

import junit.framework.TestCase;
import org.example.ComponentsPack.Direction;

import java.util.ArrayList;
import java.util.List;

public class WarZoneTest extends TestCase {

    public void testGetNumAstronauts() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        WarZone w=new WarZone(1,0, 3, 2, cannonFireList);

        assertEquals(3, w.getNumAstronauts());
    }

    public void testGetNumGoods() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        WarZone w=new WarZone(1,0, 3, 2, cannonFireList);

        assertEquals(2, w.getNumGoods());
    }

    public void testGetCannonFireList() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        WarZone w=new WarZone(1,0, 3, 2, cannonFireList);

        assertEquals(cannonFireList, w.getCannonFireList());
        assertEquals(2, w.getCannonFireList().size());
    }
}