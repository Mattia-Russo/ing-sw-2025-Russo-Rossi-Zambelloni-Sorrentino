package org.example.Model.CardPack;

import junit.framework.TestCase;
import org.example.Model.ComponentsPack.Direction;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class WarZoneTest extends TestCase {

    public void testGetNumAstronauts() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        WarZone w=new WarZone(1,0, 3, 2, cannonFireList, null,null);

        assertEquals(3, w.getNumAstronauts());
    }

    public void testGetNumGoods() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        WarZone w=new WarZone(1,0, 3, 2, cannonFireList, null,null);

        assertEquals(2, w.getNumGoods());
    }

    public void testGetCannonFireList() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        WarZone w=new WarZone(1,0, 3, 2, cannonFireList, null,null);

        assertEquals(cannonFireList, w.getCannonFireList());
        assertEquals(2, w.getCannonFireList().size());
    }

    public void testGetLostDays() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        WarZone w=new WarZone(1,0, 3, 2, cannonFireList, null,null);

        assertEquals(0, w.getLostDays());
    }

    public void testGetPenalities() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        WarZone warZone1 = new WarZone(1, 0, 3, 2, cannonFireList, null, null);

        assertNull("Penalities should be null", warZone1.getPenalities());

        List<CannonFire> cannonFireList2 = new ArrayList<>();
        cannonFireList2.add(new CannonFire(0, Direction.NORTH));
        cannonFireList2.add(new CannonFire(1, Direction.WEST));

        String[] expectedPenalities = {"Lose 1 astronaut", "Lose 2 goods"};

        WarZone warZone2 = new WarZone(1, 0, 3, 2, cannonFireList2, expectedPenalities, null);

        assertArrayEquals(expectedPenalities, warZone2.getPenalities());
    }

    public void testGetCriteria() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        WarZone warZone1 = new WarZone(1, 0, 3, 2, cannonFireList, null, null);

        assertNull("Criteria should be null", warZone1.getCriteria());

        List<CannonFire> cannonFireList2 = new ArrayList<>();
        cannonFireList2.add(new CannonFire(0, Direction.NORTH));
        cannonFireList2.add(new CannonFire(1, Direction.WEST));

        String[] expectedCriteria = {"cannonPower"};

        WarZone warZone2 = new WarZone(1, 0, 3, 2, cannonFireList2, null, expectedCriteria);

        assertArrayEquals(expectedCriteria, warZone2.getCriteria());
    }
}