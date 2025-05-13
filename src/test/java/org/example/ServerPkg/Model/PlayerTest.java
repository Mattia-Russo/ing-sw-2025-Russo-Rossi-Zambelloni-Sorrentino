package org.example.ServerPkg.Model;

import junit.framework.TestCase;
import org.example.ServerPkg.Model.ComponentsPkg.*;

import java.util.ArrayList;

public class PlayerTest extends TestCase {

    public void testGetPosition() {
        Player p = new Player(12, "a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();
        Cabin c1 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        Storage s1 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        Storage s2 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c2 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c3 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c4 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c5 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c6 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c7 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c8 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c9 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        s.placeComponent(2,2, s2);
        //s.placeComponent(2,4, s1);
        //s.placeComponent(1,4, cannon);
        s.placeComponent(3,2, c1);
        s.placeComponent(1,3, c5);
        s.placeComponent(2,3, c2);
        s.placeComponent(1,4, c4);
        s.placeComponent(2,4, c3);
        s.placeComponent(4,2, c6);
        s.placeComponent(5,3, c7);
        s.placeComponent(4,4, c8);
        s.placeComponent(5,4, c9);


        assertEquals(0, p.getPosition());
    }

    public void testIsAbandoned() {
        Player p = new Player(12, "a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();

        assertFalse(p.isAbandoned());
    }

    public void testIsOnPlanet() {
        Player p = new Player(12, "a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();

        assertFalse(p.isOnPlanet());
    }

    public void testGetPlayerShipBoard() {
        Player p = new Player(12, "a", null);
        p.setPlayerShipboard(1);
        ShipBoard sh1=p.getPlayerShipBoard();
        Cabin c1 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        Storage s1 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        Storage s2 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c2 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c3 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c4 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c5 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c6 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c7 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c8 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c9 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        sh1.placeComponent(3,2, c1);
        sh1.placeComponent(2,2, s2);
        sh1.placeComponent(4,2, s1);
        sh1.placeComponent(4,1, cannon);
        sh1.placeComponent(1,3, c5);
        sh1.placeComponent(2,3, c2);
        sh1.placeComponent(1,4, c4);
        sh1.placeComponent(2,4, c3);
        sh1.placeComponent(4,3, c6);
        sh1.placeComponent(5,3, c7);
        sh1.placeComponent(4,4, c8);
        sh1.placeComponent(5,4, c9);

        for(int i = 0; i<7; i++){
            for(int j = 0; j<7; j++){
                if (sh1.validPosition(i, j)){
                    assertEquals(sh1.getComponentMatrix()[i][j], p.getPlayerShipBoard().getComponentMatrix()[i][j]);
                }
            }
        }


    }

    public void testChangeOnPlanet() {
        Player p = new Player(12, "a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();

        assertFalse(p.isOnPlanet());
        p.changeOnPlanet();
        assertTrue(p.isOnPlanet());
    }

    public void testAbandon() {
        Player p = new Player(12, "a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();

        assertFalse(p.isAbandoned());
        p.abandon(null);
        assertTrue(p.isAbandoned());
    }

    public void testChangePosition() {
        Player p = new Player(12, "a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();
        Cabin c1 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        Storage s1 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        Storage s2 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c2 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c3 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c4 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c5 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c6 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c7 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c8 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c9 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        s.placeComponent(3,2, c1);
        s.placeComponent(2,2, s2);
        s.placeComponent(4,2, s1);
        s.placeComponent(4,1, cannon);
        s.placeComponent(1,3, c5);
        s.placeComponent(2,3, c2);
        s.placeComponent(1,4, c4);
        s.placeComponent(2,4, c3);
        s.placeComponent(4,3, c6);
        s.placeComponent(5,3, c7);
        s.placeComponent(4,4, c8);
        s.placeComponent(5,4, c9);

        assertEquals(0, p.getPosition());

        p.changePosition(10);
        assertEquals(10, p.getPosition());

        p.changePosition(-3);
        assertEquals(7, p.getPosition());
    }

    public void testGetNumCredits() {
        Player p = new Player(12, "a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();

        assertEquals(0, p.getNumCredits());
    }

    public void testRollDice() {
        Player p = new Player(12, "a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();

        for (int i=0; i<20; i++){
            int res = p.rollDice();
            assertTrue(res <13 && res > 1);
        }



    }

    public void testChangeCredits() {
        Player p = new Player(12, "a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();

        assertEquals(0, p.getNumCredits());
        p.changeCredits(10);
        assertEquals(10, p.getNumCredits());
        p.changeCredits(-3);
        assertEquals(7, p.getNumCredits());
    }

    public void testPickComponent() {
        Cabin c1 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        Storage s1 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        Storage s2 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c2 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c3 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c4 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c5 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c6 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c7 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c8 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c9 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        ArrayList<Components> deck = new ArrayList<>();
        deck.add(c1);
        deck.add(c2);
        deck.add(c3);
        deck.add(c4);
        deck.add(c5);
        deck.add(c6);
        deck.add(c7);
        deck.add(c8);
        deck.add(c9);
        deck.add(cannon);
        deck.add(s1);
        deck.add(s2);

        Player p = new Player(12, "a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();

        for (int i=0; i< 20; i++){
            Components c = p.pickComponent(deck);
            assertTrue(deck.contains(c));
        }
    }

    public void testCheckShip() {
        Player p = new Player(12, "a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();
        Cabin c1 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Storage s1 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s2 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c2 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c3 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c4 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c5 = new Cabin(0,false, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c6 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c7 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c8 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c9 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e1 = new Engine(0,1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        s.placeComponent(3,2, c1);
        s.placeComponent(2,2, s2);
        s.placeComponent(4,2, s1);
        s.placeComponent(4,1, cannon);
        s.placeComponent(1,3, c5);
        s.placeComponent(2,3, c2);
        s.placeComponent(1,4, c4);
        s.placeComponent(2,4, c3);
        s.placeComponent(4,3, c6);
        s.placeComponent(5,3, c7);
        s.placeComponent(4,4, e1);
        s.placeComponent(5,4, c9);
        s.placeComponent(3,3, c8);

        assertFalse(p.checkShip());
    }
}