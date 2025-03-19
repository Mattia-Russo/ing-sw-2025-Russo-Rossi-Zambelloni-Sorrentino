package org.example;

import junit.framework.TestCase;
import org.example.ComponentsPack.*;

public class PlayerTest extends TestCase {

    public void testGetPosition() {
        boolean[][] availablePositionMatrix = new boolean[7][7];
        for(int i=0; i<7; i++){
            for(int j=0; j<5; j++) {
                availablePositionMatrix[i][j] = (i != 0 || j != 0) && (i != 0 || j != 1) && (i != 1 || j != 0) && (i != 3 || j != 0) && (i != 5 || j != 0) && (i != 6 || j != 0) && (i != 6 || j != 1) && (i != 3 || j != 4);
            }
        }
        Cabin c1 = new Cabin(true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        Storage s1 = new Storage(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon = new Cannon(1, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        Storage s2 = new Storage(false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c2 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c3 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c4 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c5 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c6 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c7 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c8 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c9 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        ShipBoard s = new ShipBoard(availablePositionMatrix, 7);

        Components[][] componentsMatrix = new Components[7][7];
        s.placeComponent(2,3, c1);
        s.placeComponent(2,2, s2);
        s.placeComponent(2,4, s1);
        s.placeComponent(1,4, cannon);
        s.placeComponent(3,1, c5);
        s.placeComponent(3,2, c2);
        s.placeComponent(4,1, c4);
        s.placeComponent(4,2, c3);
        s.placeComponent(3,4, c6);
        s.placeComponent(3,5, c7);
        s.placeComponent(4,4, c8);
        s.placeComponent(4,5, c9);

        Player p = new Player(s, 12);

        assertEquals(0, p.getPosition());
    }

    public void testIsAbandoned() {
    }

    public void testIsOnPlanet() {
    }

    public void testGetPlayerShipBoard() {
    }

    public void testChangeOnPlanet() {
    }

    public void testAbandon() {
    }

    public void testChangePosition() {
        boolean[][] availablePositionMatrix = new boolean[7][7];
        for(int i=0; i<7; i++){
            for(int j=0; j<5; j++) {
                availablePositionMatrix[i][j] = (i != 0 || j != 0) && (i != 0 || j != 1) && (i != 1 || j != 0) && (i != 3 || j != 0) && (i != 5 || j != 0) && (i != 6 || j != 0) && (i != 6 || j != 1) && (i != 3 || j != 4);
            }
        }
        Cabin c1 = new Cabin(true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        Storage s1 = new Storage(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon = new Cannon(1, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        Storage s2 = new Storage(false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c2 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c3 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c4 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c5 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c6 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c7 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c8 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c9 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        ShipBoard s = new ShipBoard(availablePositionMatrix, 7);

        Components[][] componentsMatrix = new Components[7][7];
        s.placeComponent(2,3, c1);
        s.placeComponent(2,2, s2);
        s.placeComponent(2,4, s1);
        s.placeComponent(1,4, cannon);
        s.placeComponent(3,1, c5);
        s.placeComponent(3,2, c2);
        s.placeComponent(4,1, c4);
        s.placeComponent(4,2, c3);
        s.placeComponent(3,4, c6);
        s.placeComponent(3,5, c7);
        s.placeComponent(4,4, c8);
        s.placeComponent(4,5, c9);

        Player p = new Player(s, 12);

        assertEquals(0, p.getPosition());

        p.changePosition(10);
        assertEquals(10, p.getPosition());

        p.changePosition(-3);
        assertEquals(7, p.getPosition());
    }

    public void testGetNumCredits() {
    }

    public void testRollDice() {
    }

    public void testChangeCredits() {
    }

    public void testPickComponent() {
    }

    public void testCheckShip() {
    }
}