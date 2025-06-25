package org.example.ServerPkg.Model.CardPkg;

import junit.framework.TestCase;
import org.example.MessagePkg.CreateLobbyMessage;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.ControllerPkg.PlayerStates.RemoveAstronautsState;
import org.example.ServerPkg.Model.ComponentsPkg.*;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;
import org.example.ServerPkg.Model.ShipBoard;

import java.util.ArrayList;
import java.util.List;


public class WarZoneTest extends TestCase {

    public void testGetNumAstronauts() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        WarZone w=new WarZone(0,1,0, 3, 2, cannonFireList, null,null);

        assertEquals(3, w.getNumAstronauts());
    }

    public void testGetNumGoods() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        WarZone w=new WarZone(0,1,0, 3, 2, cannonFireList, null,null);

        assertEquals(2, w.getNumGoods());
    }

    public void testGetCannonFireList() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        WarZone w=new WarZone(0,1,0, 3, 2, cannonFireList, null,null);

        assertEquals(cannonFireList, w.getCannonFireList());
        assertEquals(2, w.getCannonFireList().size());
    }

    public void testGetLostDays() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        WarZone w=new WarZone(0,1,0, 3, 2, cannonFireList, null,null);

        assertEquals(0, w.getLostDays());
    }

    public void testGetPenalities() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        WarZone warZone1 = new WarZone(0,1, 0, 3, 2, cannonFireList, null, null);

        assertNull("Penalities should be null", warZone1.getPenalties());

        List<CannonFire> cannonFireList2 = new ArrayList<>();
        cannonFireList2.add(new CannonFire(0, Direction.NORTH));
        cannonFireList2.add(new CannonFire(1, Direction.WEST));

        String[] expectedPenalities = {"Lose 1 astronaut", "Lose 2 goods"};

        WarZone warZone2 = new WarZone(0,1, 0, 3, 2, cannonFireList2, expectedPenalities, null);

        assertEquals(expectedPenalities, warZone2.getPenalties());
    }

    public void testGetCriteria() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        WarZone warZone1 = new WarZone(0,1, 0, 3, 2, cannonFireList, null, null);

        assertNull("Criteria should be null", warZone1.getCriteria());

        List<CannonFire> cannonFireList2 = new ArrayList<>();
        cannonFireList2.add(new CannonFire(0, Direction.NORTH));
        cannonFireList2.add(new CannonFire(1, Direction.WEST));

        String[] expectedCriteria = {"cannonPower"};

        WarZone warZone2 = new WarZone(0,1, 0, 3, 2, cannonFireList2, null, expectedCriteria);

        assertEquals(expectedCriteria, warZone2.getCriteria());
    }

    public void testSetCardState() {
        Player p1 = new Player("a", null);
        Player p2 = new Player("a", null);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        String[] penalties = {"LoseDays", "LoseAstronauts","cannonFire"};
        String[] criteria = {"FewestAstronauts", "LessEnginePower","LessCannonPower"};
        Game g=new Game(2, 1,1, new GameController());
        g.getPlayers().addAll(players);
        g.setPlayersShipboard();
        WarZone c= new WarZone(0,1,2,2,3,cannonFireList,penalties,criteria);
        g.setCard(c);
        ShipBoard sp1 = p1.getPlayerShipBoard();
        ShipBoard sp2 = p2.getPlayerShipBoard();
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

        sp1.placeComponent(6,7, s21);
        sp1.placeComponent(8,7, s11);
        sp1.placeComponent(8,6, cannon1);
        sp1.placeComponent(5,8, c51);
        sp1.placeComponent(6,8, c21);
        sp1.placeComponent(5,9, c41);
        sp1.placeComponent(6,9, c31);
        sp1.placeComponent(8,8, c61);
        sp1.placeComponent(9,8, c71);
        sp1.placeComponent(8,9, e11);
        sp1.placeComponent(9,9, c91);
        sp1.placeComponent(7,8, c81);

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

        sp2.placeComponent(6,7, s22);
        sp2.placeComponent(8,7, s12);
        sp2.placeComponent(8,6, cannon2);
        sp2.placeComponent(5,8, c52);
        sp2.placeComponent(6,8, cannonDouble);
        sp2.placeComponent(5,9, c42);
        sp2.placeComponent(6,9, c32);
        sp2.placeComponent(8,8, c62);
        sp2.placeComponent(9,8, c72);
        sp2.placeComponent(8,9, e12);
        sp2.placeComponent(9,9, c92);
        sp2.placeComponent(7,8, c82);


        c.setCardState(g);
        assertTrue(p1.getState() instanceof RemoveAstronautsState);
    }

    public void testWarZone(){
        Player p1 = new Player("Giacomo", null);
        Player p2 = new Player("Mattia", null);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        GameController c =  new GameController();
        Game g =new Game(2, 1, 0, c);
        g.getPlayers().addAll(players);
        g.setPlayersShipboard();
        p1.opShip(g);
        p2.opShip(g);
        g.Turn();
        p2.getState().endActivateEngines(p2);
        p1.getState().endActivateEngines(p1);
        Points p = new Points(8,7);
        p2.getState().removeAstronauts(p, p2);
        p2.getState().removeAstronauts(p, p2);
        p2.getState().endRemoveAstronauts(p2);
        p2.getState().endActivateCannons(p2);
        p1.getState().endActivateCannons(p1);
        p2.getState().endActivateShields(p2);
    }
}