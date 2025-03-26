package org.example.Model.ComponentsPack;

import junit.framework.TestCase;
import org.example.Model.ShipBoard;
import org.junit.jupiter.api.BeforeEach;

public class CannonTest extends TestCase {

    public void testGetPower() {
        Cannon c = new Cannon(1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertEquals(1, c.getPower());
    }

    public void testRemove() {
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for(int i=0; i<7; i++){
            for(int j=0; j<5; j++) {
                availablePositionMatrix[i][j] = (i != 0 || j != 0) && (i != 0 || j != 1) && (i != 1 || j != 0) && (i != 3 || j != 0) && (i != 5 || j != 0) && (i != 6 || j != 0) && (i != 6 || j != 1) && (i != 3 || j != 4);
            }
        }
        Cabin c1 = new Cabin(true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Storage s1 = new Storage(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon = new Cannon(1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s2 = new Storage(false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c2 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c3 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c4 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c5 = new Cabin(false, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cannon cannon1 = new Cannon(1, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c6 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c7 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c8 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c9 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e1 = new Engine(1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        ShipBoard s = new ShipBoard(availablePositionMatrix, 7, 5);

        s.placeComponent(3,2, c1);
        s.placeComponent(2,2, s2);
        s.placeComponent(4,2, s1);
        s.placeComponent(4,1, cannon);
        s.placeComponent(1,3, cannon1);
        s.placeComponent(2,3, c2);
        s.placeComponent(1,4, c4);
        s.placeComponent(2,4, c3);
        s.placeComponent(4,3, c6);
        s.placeComponent(5,3, c7);
        s.placeComponent(4,4, e1);
        s.placeComponent(5,4, c9);
        s.placeComponent(3,3, c8);

        int pow = s.getSingleEnginePower();
        assertEquals(2, pow);
        cannon.remove(s);
        assertEquals(1, pow);
    }

    public void testPlace() {
    }

    public void testIsDoubleCannon() {
    }

    public void testCheckRightCannon() {
    }

    public void testIsCannon() {
    }
}