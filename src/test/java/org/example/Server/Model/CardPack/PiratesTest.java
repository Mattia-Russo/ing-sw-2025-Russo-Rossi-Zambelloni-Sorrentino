package org.example.Server.Model.CardPack;

import junit.framework.TestCase;
import org.example.Server.Controller.States.ActivateCannonsState;
import org.example.Server.Controller.States.WaitingState;
import org.example.Server.Model.ComponentsPack.*;
import org.example.Server.Model.Game;
import org.example.Server.Model.Player;
import org.example.Server.Model.ShipBoard;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

public class PiratesTest extends TestCase {

    private Game game;
    private Pirates card;
    private  ArrayList<Player> players;
    private ShipBoard ship1;
    private Player p1;

    @BeforeEach
    public void setUp() {
        p1 = new Player(0, "a");

        p1.changePosition(4);
        players = new ArrayList<>();
        players.add(p1);

        game = new Game(4, 2, players, 1, 30);
        ArrayList<CannonFire> cannonFire = new ArrayList<>();
        cannonFire.add(new CannonFire(0, Direction.NORTH));
        //cannonFire.add(new CannonFire(1, Direction.SOUTH));
        //cannonFire.add(new CannonFire(1, Direction.NORTH));
        //cannonFire.add(new CannonFire(0, Direction.EAST));
        //cannonFire.add(new CannonFire(1, Direction.WEST));
        //cannonFire.add(new CannonFire(0, Direction.WEST));
        card = new Pirates(12, cannonFire, 2, 2, 7);


        ship1 = p1.getPlayerShipBoard();

        Cabin c1 = new Cabin(true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Storage s1 = new Storage(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}, 3);
        Cannon ca1 = new Cannon(1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cannon ca2 = new Cannon(2, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c2 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c3 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY});
        Shield sh1 = new Shield(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL}, Direction.EAST);
        Shield sh2 = new Shield(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL}, Direction.WEST);
        Cannon ca3 = new Cannon(1, Direction.EAST, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Tubes t1 = new Tubes(Direction.EAST, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e1 = new Engine(1, Direction.SOUTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL});
        BatteryStorage bs1 = new BatteryStorage(3, Direction.NORTH,  new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL});
        BatteryStorage bs2 = new BatteryStorage(2, Direction.NORTH,  new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e2 = new Engine(2, Direction.SOUTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL});
        Cannon ca4 = new Cannon(1, Direction.WEST, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cannon ca5 = new Cannon(2, Direction.SOUTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL});

        ship1.placeComponent(3, 2, c1);
        ship1.placeComponent(2, 2, sh1);
        ship1.placeComponent(4, 2, s1);
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
        ship1.placeComponent(5, 2, c2);
        ship1.placeComponent(6, 3, ca3);
    }

    public void testGetCannonPower() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        Pirates p=new Pirates(10, cannonFireList, 1, 2,10);
        assertEquals(10, p.getCannonPower());
    }

    public void testGetCardLevel() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        Pirates p=new Pirates(10, cannonFireList, 1, 2,10);
        assertEquals(1, p.getCardLevel());
    }

    public void testGetCredit() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        Pirates p=new Pirates(10, cannonFireList, 1, 2,10);
        assertEquals(10, p.getCredit());
    }

    public void testGetLostDays() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        Pirates p=new Pirates(10, cannonFireList, 1, 2,10);
        assertEquals(2, p.getLostDays());
    }

    public void testGetCannonFireList() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        Pirates p=new Pirates(10, cannonFireList, 1, 2,10);
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
    }

    public void testTestPlayCard() {
    }

    public void testSetAccept() {
    }
}