package org.example.Model.CardPack;

import junit.framework.TestCase;
import org.example.Server.Model.CardPack.CannonFire;
import org.example.Server.Model.CardPack.Pirates;
import org.example.Server.Model.ComponentsPack.Direction;

import java.util.ArrayList;
import java.util.List;

public class PiratesTest extends TestCase {

    public void testGetCannonPower() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        Pirates p=new Pirates(10, cannonFireList, 1, 2,10);
        assertEquals(10, p.getCannonPower());
    }

    public void testGetCardLevel() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        Pirates p=new Pirates(10, cannonFireList, 1, 2,10);
        assertEquals(1, p.getCardLevel());
    }

    public void testGetCredit() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        Pirates p=new Pirates(10, cannonFireList, 1, 2,10);
        assertEquals(10, p.getCredit());
    }

    public void testGetLostDays() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        Pirates p=new Pirates(10, cannonFireList, 1, 2,10);
        assertEquals(2, p.getLostDays());
    }

    public void testGetCannonFireList() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        Pirates p=new Pirates(10, cannonFireList, 1, 2,10);
        assertEquals(cannonFireList, p.getCannonFireList());
        assertEquals(2, p.getCannonFireList().size());
    }
}