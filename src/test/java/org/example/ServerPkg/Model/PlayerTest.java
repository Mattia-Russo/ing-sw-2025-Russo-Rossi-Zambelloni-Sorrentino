package org.example.ServerPkg.Model;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.ControllerPkg.PlayerStates.AddAlienState;
import org.example.ServerPkg.Model.ComponentsPkg.*;

import java.util.ArrayList;

public class PlayerTest extends TestCase {

    public void testGetPosition() {
        Player p = new Player("a", null);
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

        s.placeComponent(6,7, s2);
        //s.placeComponent(2,4, s1);
        //s.placeComponent(1,4, cannon);
        s.placeComponent(7,7, c1);
        s.placeComponent(5,8, c5);
        s.placeComponent(6,8, c2);
        s.placeComponent(4,9, c4);
        s.placeComponent(6,9, c3);
        s.placeComponent(8,7, c6);
        s.placeComponent(9,8, c7);
        s.placeComponent(8,9, c8);
        s.placeComponent(9,9, c9);


        assertEquals(0, p.getPosition());
    }

    public void testIsAbandoned() {
        Player p = new Player("a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();

        assertFalse(p.isAbandoned());
    }

    public void testIsOnPlanet() {
        Player p = new Player("a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();

        assertFalse(p.isOnPlanet());
    }

    public void testGetPlayerShipBoard() {
        Player p = new Player("a", null);
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

        sh1.placeComponent(7,7, c1);
        sh1.placeComponent(6,7, s2);
        sh1.placeComponent(8,7, s1);
        sh1.placeComponent(8,6, cannon);
        sh1.placeComponent(5,8, c5);
        sh1.placeComponent(6,8, c2);
        sh1.placeComponent(5,9, c4);
        sh1.placeComponent(6,9, c3);
        sh1.placeComponent(8,8, c6);
        sh1.placeComponent(9,8, c7);
        sh1.placeComponent(8,9, c8);
        sh1.placeComponent(9,9, c9);

        for(int i = 0; i<7; i++){
            for(int j = 0; j<7; j++){
                if (sh1.validPosition(i, j)){
                    assertEquals(sh1.getComponentMatrix()[i][j], p.getPlayerShipBoard().getComponentMatrix()[i][j]);
                }
            }
        }


    }

    public void testChangeOnPlanet() {
        Player p = new Player("a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();

        assertFalse(p.isOnPlanet());
        p.changeOnPlanet();
        assertTrue(p.isOnPlanet());
    }

    public void testAbandon() {
        Player p = new Player( "a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();

        assertFalse(p.isAbandoned());
        p.abandon(null);
        assertTrue(p.isAbandoned());
    }

    public void testChangePosition() {
        Player p = new Player( "a", null);
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

        s.placeComponent(7,7, c1);
        s.placeComponent(6,7, s2);
        s.placeComponent(8,7, s1);
        s.placeComponent(8,6, cannon);
        s.placeComponent(5,8, c5);
        s.placeComponent(6,8, c2);
        s.placeComponent(5,9, c4);
        s.placeComponent(6,9, c3);
        s.placeComponent(8,8, c6);
        s.placeComponent(9,8, c7);
        s.placeComponent(8,9, c8);
        s.placeComponent(9,9, c9);

        assertEquals(0, p.getPosition());

        p.changePosition(10);
        assertEquals(10, p.getPosition());

        p.changePosition(-3);
        assertEquals(7, p.getPosition());
    }

    public void testGetNumCredits() {
        Player p = new Player( "a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();

        assertEquals(0, p.getNumCredits());
    }

    public void testRollDice() {
        Player p = new Player( "a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();

        for (int i=0; i<20; i++){
            int res = p.rollDice();
            assertTrue(res <13 && res > 1);
        }



    }

    public void testChangeCredits() {
        Player p = new Player( "a", null);
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

        Player p = new Player( "a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();

        for (int i=0; i< 20; i++){
            Components c = p.pickComponent(deck);
            assertTrue(deck.contains(c));
        }
    }

    public void testCheckShip() {
        Player p = new Player( "a", null);
        new Game(2, 2,1, new GameController());
        p.setPlayerShipboard(2);
        p.opShip();
        /*ShipBoard sp1= p.getPlayerShipBoard();
        Cabin c11 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Storage s11 = new Storage(0,true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}, 3);
        Cannon cannon1 = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Storage s21 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}, 2);
        Cabin c21 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c31 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c41 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c51 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c61 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c71 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c81 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c91 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e11 = new Engine(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        sp1.placeComponent(3,2, c11);
        sp1.placeComponent(2,2, s21);
        sp1.placeComponent(4,1, cannon1);
        sp1.placeComponent(1,3, c51);
        sp1.placeComponent(2,3, c21);
        sp1.placeComponent(1,4, c41);
        sp1.placeComponent(2,4, c31);
        sp1.placeComponent(4,3, c61);
        sp1.placeComponent(5,3, c71);
        sp1.placeComponent(4,4, e11);
        sp1.placeComponent(5,4, c91);
        sp1.placeComponent(3,3, c81);

        /*PlayerView pv = new PlayerView(p);
        List<PlayerView> players = new ArrayList<>();
        players.add(pv);
        TUI tui = new TUI(null);
        tui.DrawShipboard(players);*/
        assertTrue(p.checkShip());
    }
}