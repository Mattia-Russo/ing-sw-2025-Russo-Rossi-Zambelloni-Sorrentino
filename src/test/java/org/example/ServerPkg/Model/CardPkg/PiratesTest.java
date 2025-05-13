package org.example.ServerPkg.Model.CardPkg;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.PlayerStates.*;
import org.example.ServerPkg.Model.ComponentsPkg.*;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;
import org.example.ServerPkg.Model.ShipBoard;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

public class PiratesTest extends TestCase {

    private Game game;
    private Pirates card;
    private  ArrayList<Player> players;
    private ShipBoard ship1, ship2;
    private Player p1, p2;

    @BeforeEach
    public void setUp() {
        p1 = new Player(0, "a");
        p2 = new Player(1, "b");

        p1.changePosition(4);
        p2.changePosition(3);
        players = new ArrayList<>();
        players.add(p1);
        //players.add(p2);

        game = new Game(4, 2, 1, null);
        ArrayList<CannonFire> cannonFire = new ArrayList<>();
        cannonFire.add(new CannonFire(0, Direction.SOUTH));
        //cannonFire.add(new CannonFire(1, Direction.NORTH));
        //cannonFire.add(new CannonFire(1, Direction.NORTH));
        //cannonFire.add(new CannonFire(0, Direction.EAST));
        //cannonFire.add(new CannonFire(1, Direction.WEST));
        //cannonFire.add(new CannonFire(0, Direction.WEST));
        card = new Pirates(0,12, cannonFire, 2, 2, 3);
        game.setCard(card);

        ship1 = p1.getPlayerShipBoard();
        ship2 = p2.getPlayerShipBoard();

        Cabin c1 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Storage s1 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}, 3);
        Cannon ca1 = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cannon ca2 = new Cannon(0,2, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY});
        Cabin c2 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c3 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY});
        Shield sh1 = new Shield(0,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}, Direction.EAST);
        Shield sh2 = new Shield(0,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL}, Direction.WEST);
        Cannon ca3 = new Cannon(0,1, Direction.EAST, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Tubes t1 = new Tubes(0,Direction.EAST, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e1 = new Engine(0,1, Direction.SOUTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL});
        BatteryStorage bs1 = new BatteryStorage(0,3, Direction.NORTH,  new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL});
        BatteryStorage bs2 = new BatteryStorage(0,2, Direction.NORTH,  new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e2 = new Engine(0,2, Direction.SOUTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL});
        Cannon ca4 = new Cannon(0,1, Direction.WEST, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cannon ca5 = new Cannon(0,2, Direction.SOUTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL});

        ship1.placeComponent(3, 2, c1);
        //ship1.placeComponent(2, 2, sh1);
        //ship1.placeComponent(4, 2, s1);
        ship1.placeComponent(4, 1, ca1);
        ship1.placeComponent(1, 3, ca4);
        ship1.placeComponent(2, 3, bs2);
        ship1.placeComponent(1, 4, ca5);
        ship1.placeComponent(2, 4, e2);
        ship1.placeComponent(4, 3, e1);
        ship1.placeComponent(5, 3, t1);
        ship1.placeComponent(5, 4, sh2);
        ship1.placeComponent(3, 3, bs1);
        ship1.placeComponent(1, 2, c3);
        ship1.placeComponent(3, 1, ca2);
        //ship1.placeComponent(5, 2, c2);
        ship1.placeComponent(6, 3, ca3);

//        ship2.placeComponent(3, 2, c1);
//        ship2.placeComponent(2, 2, sh1);
//        ship2.placeComponent(4, 2, s1);
//        ship2.placeComponent(4, 1, ca1);
//        ship2.placeComponent(1, 3, ca4);
//        ship2.placeComponent(2, 3, bs2);
//        ship2.placeComponent(1, 4, ca5);
//        ship2.placeComponent(2, 4, e2);
//        ship2.placeComponent(4, 3, e1);
//        ship2.placeComponent(5, 3, t1);
//        ship2.placeComponent(5, 4, sh2);
//        ship2.placeComponent(3, 3, bs1);
//        ship2.placeComponent(1, 2, c3);
//        ship2.placeComponent(3, 1, ca2);
//        ship2.placeComponent(5, 2, c2);
//        ship2.placeComponent(6, 3, ca3);
    }

    public void testGetCannonPower() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        Pirates p=new Pirates(0,10, cannonFireList, 1, 2,10);
        assertEquals(10, p.getCannonPower());
    }

    public void testGetCardLevel() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        Pirates p=new Pirates(0,10, cannonFireList, 1, 2,10);
        assertEquals(1, p.getCardLevel());
    }

    public void testGetCredit() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        Pirates p=new Pirates(0,10, cannonFireList, 1, 2,10);
        assertEquals(10, p.getCredit());
    }

    public void testGetLostDays() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        Pirates p=new Pirates(0,10, cannonFireList, 1, 2,10);
        assertEquals(2, p.getLostDays());
    }

    public void testGetCannonFireList() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        Pirates p=new Pirates(0,10, cannonFireList, 1, 2,10);
        assertEquals(cannonFireList, p.getCannonFireList());
        assertEquals(2, p.getCannonFireList().size());
    }

    public void testSetCardState() {
        card.setCardState(game);
        assertTrue(p1.getState() instanceof ActivateCannonsState);

        card.setCurrentPlayerIndex(-1);
        ship1.setNumDoubleCannon(-2);
        card.setCardState(game);
        assertTrue(p1.getState() instanceof WaitingState);
    }

    public void testPlayCard() {
        // setto la potenza di fuoco del player
        // modifico di conseguenza il valore di lost (chiamando playCard)
        // chiamo playCard con gli scudi che vuole attivare
        // vedo come si comporta

        // singleCannonPower = 2
        // doubleCannonCount = 2
        // potentialDoubleCannonPower = 3

        card.setCardState(game);

        assertTrue((p1.getState() instanceof ActivateCannonsState));

        // p1 non attiva cannoni, totalPower = 2

        card.playCard(game, null,  null);

        //assertTrue((p1.getState() instanceof ActivateShieldsState));

        ArrayList<Points> shields = new ArrayList<>();
        //shields.add(new Points(2, 2));
        //shields.add(new Points(5, 4));

        ArrayList<Points> batteries = new ArrayList<>();
        //batteries.add(new Points(3, 3));

        card.playCard(game, shields, batteries);


        assertTrue((p1.getState() instanceof ShipWreckedState));

        p1.getState().chooseWrecked(new Points(4, 3), null);
        p1.getState().endWreckedState(null);

        assertNull(p1.getPlayerShipBoard().getComponentMatrix()[2][1]);
        assertNull(p1.getPlayerShipBoard().getComponentMatrix()[2][2]);
        assertNull(p1.getPlayerShipBoard().getComponentMatrix()[3][1]);
        assertNull(p1.getPlayerShipBoard().getComponentMatrix()[3][2]);
        assertNull(p1.getPlayerShipBoard().getComponentMatrix()[4][2]);
        assertNull(p1.getPlayerShipBoard().getComponentMatrix()[4][1]);
        assertNull(p1.getPlayerShipBoard().getComponentMatrix()[1][3]);
        assertNull(p1.getPlayerShipBoard().getComponentMatrix()[2][3]);
        assertNull(p1.getPlayerShipBoard().getComponentMatrix()[3][3]);


        // p2 attiva 1 cannone davanti, totalPower = 4
//        ArrayList<Points> cannons = new ArrayList<>();
//        cannons.add(new Points(3, 1));
//
//        batteries.add(new Points(2, 3));
//
//        assertTrue((p2.getState() instanceof ActivateCannonsState));
//
//        card.playCard(game, cannons, batteries);
//
//        assertTrue(p2.getState() instanceof WinEnemyState);
//
//        card.setAccept(true);
//
//        assertEquals(0, p2.getNumCredits());
//        assertEquals(3, p2.getPosition());
//
//        card.playCard(game);
//
//        assertEquals(12, p2.getNumCredits());
//        assertEquals(1, p2.getPosition());
//
//        assertTrue(p2.getState() instanceof WaitingState);
    }

    public void testSetAccept() {
        card.setAccept(true);
        assertTrue(card.getAccept());
        card.setAccept(false);
        assertFalse(card.getAccept());
    }
}