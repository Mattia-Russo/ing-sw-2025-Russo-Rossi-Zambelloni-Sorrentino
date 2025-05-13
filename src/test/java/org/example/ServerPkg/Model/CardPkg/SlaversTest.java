package org.example.ServerPkg.Model.CardPkg;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.PlayerStates.ActivateCannonsState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.RemoveAstronautsState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.WaitingState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.WinEnemyState;
import org.example.ServerPkg.Model.ComponentsPkg.*;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;
import org.example.ServerPkg.Model.ShipBoard;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

public class SlaversTest extends TestCase {
    private Game game;
    private Slavers slavers;
    private  ArrayList<Player> players;
    ShipBoard sp3;

    @BeforeEach
    public void setUp() {
        Player p1 = new Player(12, "a", null);
        Player p2 = new Player( 7, "a", null);
        Player p3 = new Player(14, "a", null);
        Player p4 = new Player( 9, "a", null);

        p1.changePosition(4);
        players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        game=new Game(4,1,  1, null);
        slavers=new Slavers(0,1,1,2,3,4);
        game.setCard(slavers);

        ShipBoard sp1 = p1.getPlayerShipBoard();
        ShipBoard sp2 = p2.getPlayerShipBoard();
        sp3 = p3.getPlayerShipBoard();
        ShipBoard sp4 = p4.getPlayerShipBoard();


        Cabin c11 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Storage s11 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon1 = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s21 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c21 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c31 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c41 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c51 = new Cabin(0,false, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c61 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c71 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c81 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c91 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e11 = new Engine(0,1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        sp1.placeComponent(3,2, c11);
        sp1.placeComponent(2,2, s21);
        sp1.placeComponent(4,2, s11);
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

        Cabin c12 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL, Connector.DOUBLE});
        BatteryStorage s12 = new BatteryStorage(0,2, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE});
        Cannon cannon2 = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s22 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cannon cannonDouble = new Cannon(0,2, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c32 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c42 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c52 = new Cabin(0,false, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c62 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c72 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c82 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c92 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e12 = new Engine(0,1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        sp2.placeComponent(3,2, c12);
        sp2.placeComponent(2,2, s22);
        sp2.placeComponent(4,2, s12);
        sp2.placeComponent(4,1, cannon2);
        sp2.placeComponent(1,3, c52);
        sp2.placeComponent(2,3, cannonDouble);
        sp2.placeComponent(1,4, c42);
        sp2.placeComponent(2,4, c32);
        sp2.placeComponent(4,3, c62);
        sp2.placeComponent(5,3, c72);
        sp2.placeComponent(4,4, e12);
        sp2.placeComponent(5,4, c92);
        sp2.placeComponent(3,3, c82);

        Cabin c13 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL, Connector.DOUBLE});
        BatteryStorage s13 = new BatteryStorage(0,2, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE});
        Cannon cannon3 = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s23 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cannon cannonDouble3 = new Cannon(0,2, Direction.EAST, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c33 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c43 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c53 = new Cabin(0,false, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c63 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c73 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c83 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c93 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e13 = new Engine(0,1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        sp3.placeComponent(3,2, c13);
        sp3.placeComponent(2,2, s23);
        sp3.placeComponent(4,2, s13);
        sp3.placeComponent(4,1, cannon3);
        sp3.placeComponent(1,3, c53);
        sp3.placeComponent(2,3, cannonDouble3);
        sp3.placeComponent(1,4, c43);
        sp3.placeComponent(2,4, c33);
        sp3.placeComponent(4,3, c63);
        sp3.placeComponent(5,3, c73);
        sp3.placeComponent(4,4, e13);
        sp3.placeComponent(5,4, c93);
        sp3.placeComponent(3,3, c83);

        Cabin c14 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Storage s14 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon4 = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s24 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c24 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c34 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c44 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c54 = new Cabin(0,false, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c64 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c74 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c84 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c94 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e14 = new Engine(0,1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        sp4.placeComponent(3,2, c14);
        sp4.placeComponent(2,2, s24);
        sp4.placeComponent(4,2, s14);
        sp4.placeComponent(4,1, cannon4);
        sp4.placeComponent(1,3, c54);
        sp4.placeComponent(2,3, c24);
        sp4.placeComponent(1,4, c44);
        sp4.placeComponent(2,4, c34);
        sp4.placeComponent(4,3, c64);
        sp4.placeComponent(5,3, c74);
        sp4.placeComponent(4,4, e14);
        sp4.placeComponent(5,4, c94);
        sp4.placeComponent(3,3, c84);
    }

    public void testGetCannonPower() {
        Slavers s=new Slavers(0,1,2,10, 2, 10);
        assertEquals(10,s.getCannonPower());
    }

    public void testGetCardLevel() {
        Slavers s=new Slavers(0,1,2,10, 2, 10);
        assertEquals(1,s.getCardLevel());
    }

    public void testGetLostDays() {
        Slavers s=new Slavers(0,1,2,10, 2, 10);
        assertEquals(2,s.getLostDays());
    }

    public void testGetNumAstronauts() {
        Slavers s=new Slavers(0,1,2,10, 2, 10);
        assertEquals(2,s.getNumAstronauts());
    }

    public void testGetCredits() {
        Slavers s=new Slavers(0,1,2,10, 2, 10);
        assertEquals(10,s.getCredits());
    }

    public void testSetCardState() {
        slavers.setCardState(game);
        assertTrue(players.get(0).getState() instanceof ActivateCannonsState);
        assertTrue(players.get(1).getState() instanceof WaitingState);
        assertTrue(players.get(2).getState() instanceof WaitingState);
        assertTrue(players.get(3).getState() instanceof WaitingState);
    }

    public void testPlayCard3Par() {
        slavers.setCardState(game);
        slavers.playCard(game, null, null);
        assertTrue(players.get(0).getState() instanceof RemoveAstronautsState);

        slavers.setCardState(game);
        ArrayList<Points> cannons = new ArrayList<Points>();
        cannons.add(new Points(2,3));
        ArrayList<Points> batteries = new ArrayList<Points>();
        batteries.add(new Points(4,2));
        slavers.playCard(game, cannons, batteries);
        assertTrue(players.get(1).getState() instanceof WinEnemyState);

        slavers.setCardState(game);
        slavers.playCard(game, cannons, batteries);
        assertTrue(players.get(2).getState() instanceof ActivateCannonsState);

        assertTrue(players.get(3).getState() instanceof ActivateCannonsState);
    }

    public void testPlayCard1Par() {
        slavers.setCardState(game);
        slavers.setAccept(true);
        slavers.playCard(game);
        assertEquals(4, game.getPlayers().get(0).getNumCredits());
        assertEquals(3, game.getPlayers().get(0).getPosition());
    }

    public void testPlayCard2Par() {
        slavers.setCardState(game);
        slavers.playCard(game, 0);
        assertTrue(players.get(0).getState() instanceof WaitingState);
    }

    public void testSetAccept() {
        slavers.setAccept(true);
        assertTrue(slavers.getAccept());

        slavers.setAccept(false);
        assertFalse(slavers.getAccept());
    }

    public void testGetAccept() {
        assertFalse(slavers.getAccept());
    }
}