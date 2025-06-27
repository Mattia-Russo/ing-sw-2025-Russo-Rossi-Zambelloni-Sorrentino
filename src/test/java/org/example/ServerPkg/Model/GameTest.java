package org.example.ServerPkg.Model;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.ControllerPkg.PlayerStates.*;
import org.example.ServerPkg.Model.CardPkg.*;
import org.example.ServerPkg.Model.ComponentsPkg.*;
import org.example.ServerPkg.Model.Exceptions.InvalidDeckNumberException;
import org.example.ServerPkg.Model.Exceptions.TilesHeapNotInitializedException;
import org.example.UIPkg.GameUpdater;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameTest extends TestCase {

    public void testGetNumPlayers() throws RemoteException {
        Player p1 = new Player("a", null);
        Player p2 = new Player("a", null);
        Player p3 = new Player("a", null);
        Player p4 = new Player("a", null);

        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        Game g = new Game(4, 0, 0, new GameController());
        g.getPlayers().addAll(players);

        assertEquals(4,g.getNumPlayer());
    }

    public void testGetPlayers() throws RemoteException {
        Player p1 = new Player("a", null);
        Player p2 = new Player("a", null);
        Player p3 = new Player("a", null);
        Player p4 = new Player("a", null);

        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        Game g = new Game(4, 0, 0, new GameController());
        g.getPlayers().addAll(players);
        assertEquals(players,g.getPlayers());
    }

    public void testAdjustPlayerPositions() throws RemoteException {
        Player p1 = new Player("a", null);
        Player p2 = new Player("a", null);
        Player p3 = new Player("a", null);
        Player p4 = new Player("a", null);

        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        Game g=new Game(4,1, 1, new GameController());
        g.getPlayers().addAll(players);

        p1.changePosition(4);
        p2.changePosition(1);
        p3.changePosition(9);
        p4.changePosition(5);

        int[] expectedPositions = {4, 1, 9, 5};
        for (int i = 0; i < expectedPositions.length; i++) {
            assertEquals(expectedPositions[i], g.getPlayers().get(i).getPosition());
        }
    }

    public void testGetOccupiedPositions() throws RemoteException {
        Player p1 = new Player("a", null);
        Player p2 = new Player("a", null);
        Player p3 = new Player( "a", null);
        Player p4 = new Player("a", null);

        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        Game g=new Game(4, 1, 1, new GameController());
        g.getPlayers().addAll(players);
        p1.changePosition(4);
        p2.changePosition(1);
        p3.changePosition(9);
        p4.changePosition(5);

        g.adjustPlayerPositions();
        assertEquals(1, g.getOccupiedPositions(p1, 2));
    }

    public void testPickCard() throws RemoteException {
        Player p1 = new Player("a", null);
        Player p2 = new Player( "a", null);
        Player p3 = new Player("a", null);
        Player p4 = new Player( "a", null);

        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        Game g=new Game(4,1,  1, new GameController());
        g.getPlayers().addAll(players);

        //g.Turn();

        AdventureCard pickedCard = g.getCurrentCard();
        assertFalse(g.getDeck(0).contains(pickedCard));
    }

    public void testCheckGiveUp() throws RemoteException {
        Player p1 = new Player( "a", null);
        Player p2 = new Player( "a", null);
        Player p3 = new Player( "a", null);
        Player p4 = new Player( "a", null);

        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        Game g=new Game(4,1,  1, new GameController());
        g.getPlayers().addAll(players);
        p1.abandon(g);
        p3.abandon(g);
        assertTrue(g.checkGiveUp(p1));
        assertTrue(g.checkGiveUp(p3));
        assertFalse(g.checkGiveUp(p2));
        assertFalse(g.checkGiveUp(p4));
    }

    public void testCalculateWinners() throws RemoteException {
        Player p1 = new Player("a", null);
        Player p2 = new Player( "a", null);
        Player p3 = new Player( "a", null);
        Player p4 = new Player( "a", null);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        Game g=new Game(4,1,  1, new GameController());

        g.getPlayers().addAll(players);
        g.setPlayersShipboard();

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

        sp1.placeComponent(7,5, c11);
        sp1.placeComponent(6,7, s21);
        sp1.placeComponent(8,7, s11);
        sp1.placeComponent(8,6, cannon1);
        sp1.placeComponent(5,8, c51);
        sp1.placeComponent(6,9, c21);
        sp1.placeComponent(5,9, c41);

        sp1.placeComponent(8,8, c61);
        sp1.placeComponent(9,8, c71);
        sp1.placeComponent(8,9, e11);
        sp1.placeComponent(9,9, c91);
        sp1.placeComponent(7,8, c81);

        Cabin c12 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Storage s12 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon2 = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s22 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c22 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
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
        sp2.placeComponent(6,8, c22);
        sp2.placeComponent(5,9, c42);
        sp2.placeComponent(6,9, c32);
        sp2.placeComponent(8,8, c62);
        sp2.placeComponent(9,8, c72);
        sp2.placeComponent(8,9, e12);
        sp2.placeComponent(9,9, c92);
        sp2.placeComponent(7,8, c82);


        ArrayList<Player> win  = new ArrayList<>();

        for (Player p : players) {
            p.changeCredits(2);
            win.add(p);
        }
        p1.changeCredits(-2);
        win.remove(p1);

    }

    public void testCalculateFinalCredits() throws RemoteException {
        Player p1 = new Player("a", null);
        Player p2 = new Player( "a", null);
        Player p3 = new Player( "a", null);
        Player p4 = new Player( "a", null);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);

        Game g=new Game(4, 1, 1, new GameController());
        g.getPlayers().addAll(players);
        g.setPlayersShipboard();

        ShipBoard sp1 = p1.getPlayerShipBoard();
        ShipBoard sp2 = p2.getPlayerShipBoard();

        Cabin c11 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Storage s11 = new Storage(0,true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
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

        sp1.placeComponent(7,5, c11);
        sp1.placeComponent(6,7, s21);
        sp1.placeComponent(8,7, s11);
        sp1.placeComponent(8,6, cannon1);
        sp1.placeComponent(5,9, c51);
        sp1.placeComponent(6,9, c21);
        sp1.placeComponent(8,8, c61);
        sp1.placeComponent(9,8, c71);
        sp1.placeComponent(8,9, e11);
        sp1.placeComponent(9,9, c91);
        sp1.placeComponent(7,8, c81);

        Cabin c12 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Storage s12 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon2 = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s22 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c22 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
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
        sp2.placeComponent(6,8, c22);
        sp2.placeComponent(5,9, c42);
        sp2.placeComponent(6,9, c32);
        sp2.placeComponent(8,8, c62);
        sp2.placeComponent(9,8, c72);
        sp2.placeComponent(8,9, e12);
        sp2.placeComponent(9,9, c92);
        sp2.placeComponent(7,8, c82);

        p2.changePosition(5);
        p1.abandon(g);

        Goods g1 = new Goods(GoodsColour.RED);
        Goods g3 = new Goods(GoodsColour.GREEN);
        Goods g5 = new Goods(GoodsColour.YELLOW);
        Goods g7 = new Goods(GoodsColour.BLUE);

        s11.addGood(g1);
        s11.addGood(g7);
        s12.addGood(g5);
        s12.addGood(g3);

        g.calculateFinalCredits();

        assertEquals(3, p1.getNumCredits());
        assertEquals(11, p2.getNumCredits());

    }

    public void testGetCurrentCard() {
        Game g=new Game(4, 1, 1, new GameController());
        assertNull(g.getCurrentCard());
    }

    public void testTurn() throws RemoteException {
        Player p1 = new Player("a", null);
        Player p2 = new Player( "a", null);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);

        Game g=new Game(4, 1, 1, new GameController());
        g.getPlayers().addAll(players);
        g.setPlayersShipboard();

        ShipBoard sp1 = p1.getPlayerShipBoard();
        ShipBoard sp2 = p2.getPlayerShipBoard();

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

        Storage s12 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon2 = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s22 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c22 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
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
        sp2.placeComponent(6,8, c22);
        sp2.placeComponent(5,9, c42);
        sp2.placeComponent(6,9, c32);
        sp2.placeComponent(8,8, c62);
        sp2.placeComponent(9,8, c72);
        sp2.placeComponent(8,9, e12);
        sp2.placeComponent(9,9, c92);
        sp2.placeComponent(7,8, c82);

        g.Turn();

    }

    public void testCheckAllPlayersShip() throws RemoteException {
        Player p1 = new Player("a", null);
        Player p2 = new Player( "a", null);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p2);
        players.add(p1);
        Game g=new Game(2, 1, 1, new GameController());
        g.getPlayers().addAll(players);
        g.setPlayersShipboard();

        ShipBoard sp2 = p2.getPlayerShipBoard();
        Storage s12 = new Storage(0,true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon2 = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        sp2.placeComponent(8,7, s12);
        sp2.placeComponent(6,7, cannon2);
        p1.setShipOK(true);
        g.checkAllPlayersShip();

        assertTrue(g.getPlayers().get(0).getState() instanceof FixShipState);
        assertTrue(g.getPlayers().get(1).getState() instanceof WaitingState);
    }
    public void testCheckAllPlayersShip2() throws RemoteException {

        Player p1 = new Player("a", null);
        Player p2 = new Player("a", null);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p2);
        players.add(p1);
        Game g = new Game(2, 1, 1, new GameController()){
            @Override
            public void Turn(){

            }
        };
        g.getPlayers().addAll(players);
        g.setPlayersShipboard();
        p1.setPlayerShipboard(1);
        p2.setPlayerShipboard(1);

        ShipBoard sp2 = p2.getPlayerShipBoard();
        Storage s12 = new Storage(0, true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon2 = new Cannon(0, 1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        sp2.placeComponent(8, 7, s12);
        sp2.placeComponent(6, 7, cannon2);
        p1.setReadyForCards(true);
        g.checkAllPlayersShip();


    }
    public void testCheckAllPlayersShip3() throws RemoteException {

        Player p1 = new Player("a", null);
        Player p2 = new Player("a", null);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p2);
        players.add(p1);
        Game g = new Game(2, 1, 1, new GameController()){
            @Override
            public void Turn(){

            }
        };
        g.getPlayers().addAll(players);
        g.setPlayersShipboard();
        p1.setPlayerShipboard(1);
        p2.setPlayerShipboard(1);

        ShipBoard sp2 = p2.getPlayerShipBoard();
        Storage s12 = new Storage(0, true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon2 = new Cannon(0, 1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        sp2.placeComponent(8, 7, s12);
        sp2.placeComponent(6, 7, cannon2);
        p1.setReadyForCards(true);
        p2.setReadyForCards(true);
        p1.setShipOK(true);
        p2.setShipOK(true);
        g.checkAllPlayersShip();


    }

    public void testCheckAllWrackedShip() throws RemoteException {
        Player p1 = new Player("a", null);
        Player p2 = new Player( "a", null);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p2);
        players.add(p1);
        Game g=new Game(2, 1, 1, new GameController());
        g.getPlayers().addAll(players);
        g.setPlayersShipboard();

        ShipBoard sp2 = p2.getPlayerShipBoard();
        Storage s12 = new Storage(0,true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon2 = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});

        sp2.placeComponent(8,6, cannon2);

        ShipBoard sp1= p1.getPlayerShipBoard();
        Storage s11 = new Storage(0,true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon1 = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s21 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c21 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c31 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.DOUBLE});
        Cabin c41 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.EMPTY});
        Cabin c51 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY});
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

        PlayerState state = p1.getState();
        g.checkAllWrackedShip();

        assertTrue(g.getPlayers().getFirst().getState() instanceof ShipWreckedState);

    }

    public void testStartBuildingShips() throws RemoteException {
        Player p1 = new Player("a", null);
        Player p2 = new Player("a", null);
        Player p3 = new Player("a", null);
        Player p4 = new Player("a", null);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        Game g=new Game(4, 1, 1, new GameController());
        g.getPlayers().addAll(players);
        g.startBuildingShips();

        assertTrue(g.getPlayers().get(0).getState() instanceof BuildShipState);
        assertTrue(g.getPlayers().get(1).getState() instanceof BuildShipState);
        assertTrue(g.getPlayers().get(2).getState() instanceof BuildShipState);
        assertTrue(g.getPlayers().get(3).getState() instanceof BuildShipState);
    }

    public void testPickComponentTile() {
        Game g=new Game(4, 1, 1, new GameController());
        int size = g.getComponentsList().size();
        Components c;
        c=g.pickComponentTile();

        assertEquals(size-1, g.getComponentsList().size());
    }
    public void testPickComponentTile1() {
        Game g=new Game(4, 1, 1, new GameController());
        g.setComponentsList(g.getComponentsList());
        try{
            g.pickComponentTile();
        }catch(TilesHeapNotInitializedException _){

        }
    }


    public void testSetPlayersShipboard() throws RemoteException {
        Player p1 = new Player("a", null);
        Player p2 = new Player( "a", null);
        Player p3 = new Player( "a", null);
        Player p4 = new Player( "a", null);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        Game g=new Game(4, 2, 1, new GameController());
        g.getPlayers().addAll(players);

        g.setPlayersShipboard();
        p1.setRocketColour("RED");
        for(Components c: g.getComponentsList()){
            System.out.println(c);
        }

        for(AdventureCard c: g.getDeck()){
            System.out.println(c);
        }
    }

    public void testGetNumPlayer() {
        Game g= new Game(3,2,1, new GameController());
        assertEquals(3,g.getNumPlayer());
    }

    public void testGetShipBoardLevel() {
        Game g= new Game(3,2,1, new GameController());
        assertEquals(2,g.getShipBoardLevel());
    }

    public void testGetGameMode() {
        Game g= new Game(3,2,1, new GameController());
        assertEquals(1,g.getGameMode());
    }

    public void testSetCard() {
        Game g = new Game(2, 1, 0, new GameController());
        Stardust card = new Stardust(1,1,2);
        g.setCard(card);
        assertEquals(card, g.getCurrentCard());
        assertEquals(card, g.getDeck().get(0));
    }

    public void testPickDiscoveredComponent() {
        Game g = new Game(2, 1, 1, new GameController());
        Cabin cabin = new Cabin(42, false, Direction.NORTH, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL
        });
        g.addDiscoveredComponent(cabin);
        assertEquals(cabin, g.pickDiscoveredComponent(0));
        assertTrue(g.getDiscoveredComponent().isEmpty());
    }

    public void testGetComponentsList() {
        Game g = new Game(2, 1, 1, new GameController());
        assertNotNull(g.getComponentsList());
        assertTrue(g.getComponentsList().size() > 0);
    }

    public void testGetPlayerByName() throws RemoteException {
        Game g = new Game(2, 1, 1, new GameController());
        Player p = new Player("Mario",g);
        g.getPlayers().add(p);
        assertEquals(p,g.getPlayerByName("Mario"));
        assertNull(g.getPlayerByName("Luigi"));
    }

    public void testSetGameUpdaters() {
        Game g = new Game(2, 1, 1, new GameController());
        Map<String, GameUpdater> updaters = new HashMap<>();
        g.setGameUpdaters(updaters);
    }

    public void testUpdateGame() {
        Game g = new Game(2, 1, 1, new GameController());
        Map<String, org.example.UIPkg.GameUpdater> updaters = new HashMap<>();
        final boolean[] called = {false};
        updaters.put("Mario", gameView -> called[0] = true);
        g.setGameUpdaters(updaters);
        g.updateGame(null);
        assertTrue(called[0]);
    }

    public void testDisconnectPlayer() throws RemoteException {
        Game g = new Game(2, 1, 1, new GameController());
        Player p = new Player("Mario", g);
        g.getPlayers().add(p);
        // Aggiungi anche all'updaters per coprire il ramo completo
        Map<String, org.example.UIPkg.GameUpdater> updaters = new HashMap<>();
        updaters.put("Mario", null);
        g.setGameUpdaters(updaters);

        g.disconnectPlayer(p);

        assertFalse(g.getPlayers().contains(p));
    }

    public void testAddDiscoveredComponent() {
        Game g = new Game(2, 1, 1, new GameController());
        Storage s = new Storage(1, false, Direction.NORTH,
                new Connector[] {Connector.SINGLE, Connector.SINGLE, Connector.SINGLE, Connector.SINGLE}, 2);

        g.addDiscoveredComponent(s);

        assertTrue(g.getDiscoveredComponent().contains(s));
    }

    public void testGetDiscoveredComponent() {
        Game g = new Game(2, 1, 1, new GameController());
        assertNotNull(g.getDiscoveredComponent());
        assertEquals(0, g.getDiscoveredComponent().size());

        Storage s = new Storage(1, false, Direction.NORTH,
                new Connector[] {Connector.SINGLE, Connector.SINGLE, Connector.SINGLE, Connector.SINGLE}, 2);
        g.addDiscoveredComponent(s);

        assertEquals(1, g.getDiscoveredComponent().size());
    }

    public void testGetController() {
        GameController controller = new GameController();
        Game g = new Game(2, 1, 1, controller);
        assertEquals(controller, g.getController());
    }

    public void testSetController() {
        GameController controller1 = new GameController();
        GameController controller2 = new GameController();
        Game g = new Game(2, 1, 1, controller1);
        g.setController(controller2);
        assertEquals(controller2, g.getController());
    }

    public void testSetTimerTurned() {
        Game g = new Game(2, 1, 1, new GameController());
        int prev= g.getTimerTurned();
        g.setTimerTurned();
        assertEquals(prev+1, g.getTimerTurned());
    }

    public void testGetTimerTurned() {
        Game g = new Game(2, 1, 1, new GameController());
        assertEquals(0, g.getTimerTurned());
        g.setTimerTurned();
        assertEquals(1, g.getTimerTurned());
    }

    public void testRollDice() {
        Game g = new Game(2, 1, 1, new GameController());
        int result = g.rollDice();
        assertTrue(result >= 2 && result <= 12);
    }

    public void testGetDeck() {
        Game g= new Game(2, 1, 1, new GameController());
        try{
            g.getDeck(5);
        }catch(InvalidDeckNumberException e){

        }
    }


}