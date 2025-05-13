package org.example.ServerPkg.Model.ComponentsPkg;

import junit.framework.TestCase;
import org.example.ServerPkg.Model.ShipBoard;

public class ComponentsTest extends TestCase {

    public void testUncover() {
        Components c = new Components(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        //assertTrue(c.getIfCovered());
        //c.uncover();
        //assertFalse(c.getIfCovered());
    }

    public void testGetIfCovered() {
        Components c = new Components(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        //assertTrue(c.getIfCovered());
    }

    public void testGetIfPositioned() {
        Components c = new Components(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertFalse(c.getIfPositioned());
    }

    public void testSetPosition() {
        Components c = new Components(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        //c.uncover();
        assertFalse(c.getIfPositioned());
        c.setPosition(5,6);
        //assertFalse(c.getIfCovered());
        assertTrue(c.getIfPositioned());
        assertEquals(5, c.getPosX());
        assertEquals(6, c.getPosY());
    }

    public void testGetDirection() {
        Components c = new Components(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertEquals(Direction.NORTH, c.getDirection());
    }

    public void testLeftRotate() {
        Components c = new Components(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        c.leftRotate();
        assertEquals(Direction.WEST, c.getDirection());
    }

    public void testRightRotate() {
        Components c = new Components(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        c.rightRotate();
        assertEquals(Direction.EAST, c.getDirection());
    }

    public void testGetConnectors() {
        Components c = new Components(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        Connector[] conn = {Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE};
        assertEquals(conn[0], c.getConnectors()[0]);
        assertEquals(conn[1], c.getConnectors()[1]);
        assertEquals(conn[2], c.getConnectors()[2]);
        assertEquals(conn[3], c.getConnectors()[3]);
    }

    public void testGetPosX() {
        Components c = new Components(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertEquals(0, c.getPosX());
    }

    public void testGetPosY() {
        Components c = new Components(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertEquals(0, c.getPosY());
    }

    public void testGetDirConnector() {
        Components c1 = new Components(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        Components c2 = new Components(Direction.EAST, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        Components c3 = new Components(Direction.SOUTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        Components c4 = new Components(Direction.WEST, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertEquals(Connector.UNIVERSAL, c1.getDirConnector(Direction.NORTH));
        assertEquals(Connector.EMPTY, c2.getDirConnector(Direction.SOUTH));
        assertEquals(Connector.SINGLE, c3.getDirConnector(Direction.NORTH));
        assertEquals(Connector.DOUBLE, c4.getDirConnector(Direction.SOUTH));
    }

    public void testRemove() {

    }

    public void testPlace() {
    }

    public void testAddLifeSupport() {
    }

    public void testAddCabin() {
    }

    public void testRemoveCabin() {
    }

    public void testIsDoubleCannon() {
        Components c = new Components(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertNull(c.isDoubleCannon());
    }

    public void testIsSingleCannon() {
        Components c = new Components(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertNull(c.isSingleCannon());
    }

    public void testIsDoubleEngine() {
        Components c = new Components(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertNull(c.isDoubleEngine());
    }

    public void testHasAlien() {
        Components c = new Components(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertNull(c.hasAlien());
    }

    public void testManageEpidemic() {

    }

    public void testAddEpidemicCabin() {
    }

    public void testAddStorage() {

    }

    public void testCheckRightCannon() {
        Components c = new Components(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                availablePositionMatrix[i][j] = (i != 0 || j != 0) && (i != 0 || j != 1) && (i != 1 || j != 0) && (i != 3 || j != 0) && (i != 5 || j != 0) && (i != 6 || j != 0) && (i != 6 || j != 1) && (i != 3 || j != 4);
            }
        }
        ShipBoard s = new ShipBoard(availablePositionMatrix, 7, 5);
        assertFalse(c.checkRightCannon(s));
    }

    public void testCheckRightEngine() {
        Components c = new Components(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                availablePositionMatrix[i][j] = (i != 0 || j != 0) && (i != 0 || j != 1) && (i != 1 || j != 0) && (i != 3 || j != 0) && (i != 5 || j != 0) && (i != 6 || j != 0) && (i != 6 || j != 1) && (i != 3 || j != 4);
            }
        }
        ShipBoard s = new ShipBoard(availablePositionMatrix, 7, 5);
        assertFalse(c.checkRightEngine(s));
    }

    public void testIsBatteryStorage() {
        Components c = new Components(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertNull(c.isBatteryStorage());
    }
}