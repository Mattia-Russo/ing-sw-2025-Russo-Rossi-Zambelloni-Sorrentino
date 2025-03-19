package org.example.ComponentsPack;

import junit.framework.TestCase;

public class ComponentsTest extends TestCase {

    public void testUncover() {
        Components c = new Components(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertTrue(c.getIfCovered());
        c.uncover();
        assertFalse(c.getIfCovered());
    }

    public void testGetIfCovered() {
        Components c = new Components(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertTrue(c.getIfCovered());
    }

    public void testGetIfPositioned() {
        Components c = new Components(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertFalse(c.getIfPositioned());
    }

    public void testSetPosition() {
        Components c = new Components(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        c.uncover();
        assertFalse(c.getIfPositioned());
        c.setPosition(5,6);
        assertFalse(c.getIfCovered());
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
}