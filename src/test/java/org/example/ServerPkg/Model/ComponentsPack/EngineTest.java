package org.example.ServerPkg.Model.ComponentsPack;

import junit.framework.TestCase;
import org.example.ServerPkg.Model.ShipBoard;

public class EngineTest extends TestCase {

    public void testGetPower() {
        Engine e = new Engine(0,2, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertEquals(2, e.getPower());
    }

    public void testRemove() {
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for(int i=0; i<7; i++){
            for(int j=0; j<5; j++) {
                availablePositionMatrix[i][j] = (i != 0 || j != 0) && (i != 0 || j != 1) && (i != 1 || j != 0) && (i != 3 || j != 0) && (i != 5 || j != 0) && (i != 6 || j != 0) && (i != 6 || j != 1) && (i != 3 || j != 4);
            }
        }
        Cabin c1 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Storage s1 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s2 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c2 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c3 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c4 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c5 = new Cabin(0,false, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cannon cannon1 = new Cannon(0,1, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c6 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c7 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c8 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c9 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e1 = new Engine(0,1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e2 = new Engine(0,1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});

        ShipBoard s = new ShipBoard(availablePositionMatrix, 7, 5);

        s.placeComponent(3,2, c1);
        s.placeComponent(2,2, s2);
        s.placeComponent(4,2, s1);
        s.placeComponent(4,1, cannon);
        s.placeComponent(1,3, cannon1);
        s.placeComponent(2,3, c2);
        s.placeComponent(1,4, c4);
        s.placeComponent(2,4, e2);
        s.placeComponent(4,3, c6);
        s.placeComponent(5,3, c7);
        s.placeComponent(4,4, e1);
        s.placeComponent(5,4, c9);
        s.placeComponent(3,3, c8);

        assertEquals(2, s.getSingleEnginePower());
        e1.remove(s);
        assertEquals(1, s.getSingleEnginePower());
        e2.remove(s);
        assertEquals(0, s.getSingleEnginePower());
    }

    public void testPlace() {
    }

    public void testIsDoubleEngine() {
        Engine cannon1 = new Engine(0,2, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine cannon2 = new Engine(0,1, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        assertEquals(cannon1, cannon1.isDoubleEngine());
        assertNull(cannon2.isDoubleEngine());
    }

    public void testCheckRightEngine() {
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for(int i=0; i<7; i++){
            for(int j=0; j<5; j++) {
                availablePositionMatrix[i][j] = (i != 0 || j != 0) && (i != 0 || j != 1) && (i != 1 || j != 0) && (i != 3 || j != 0) && (i != 5 || j != 0) && (i != 6 || j != 0) && (i != 6 || j != 1) && (i != 3 || j != 4);
            }
        }
        Cabin c1 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Storage s1 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s2 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c2 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c3 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c4 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c5 = new Cabin(0,false, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cannon cannon1 = new Cannon(0,1, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c6 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c7 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c8 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c9 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e1 = new Engine(0,1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e2 = new Engine(0,1, Direction.EAST, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Engine e3 = new Engine(0,1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.UNIVERSAL, Connector.DOUBLE});

        ShipBoard s = new ShipBoard(availablePositionMatrix, 7, 5);

        s.placeComponent(3,2, c1);
        s.placeComponent(2,2, s2);
        s.placeComponent(4,2, s1);
        s.placeComponent(4,1, cannon);
        s.placeComponent(1,3, cannon1);
        s.placeComponent(2,3, c2);
        s.placeComponent(1,4, c4);
        s.placeComponent(2,4, e2);
        s.placeComponent(4,3, c6);
        s.placeComponent(5,3, c7);
        s.placeComponent(4,4, e1);
        s.placeComponent(5,4, c9);
        s.placeComponent(3,3, c8);
        s.placeComponent(2, 1, e3);

        assertFalse(e1.checkRightEngine(s));
        assertTrue(e2.checkRightEngine(s));
        assertTrue(e3.checkRightEngine(s));
    }
}