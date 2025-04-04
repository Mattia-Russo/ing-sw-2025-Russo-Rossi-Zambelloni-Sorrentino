package org.example.Server.Model;

import junit.framework.TestCase;
import org.example.Server.Model.CardPack.AbandonedShip;
import org.example.Server.Model.CardPack.AdventureCard;
import org.example.Server.Model.CardPack.Slavers;
import org.example.Server.Model.ComponentsPack.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class GameTest extends TestCase {

    public void testGetPlayers() {
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for(int i=0; i<7; i++){
            for(int j=0; j<5; j++) {
                availablePositionMatrix[i][j] = (i != 0 || j != 0) && (i != 0 || j != 1) && (i != 1 || j != 0) && (i != 3 || j != 0) && (i != 5 || j != 0) && (i != 6 || j != 0) && (i != 6 || j != 1) && (i != 3 || j != 4);
            }
        }
        ShipBoard s = new ShipBoard(availablePositionMatrix, 7, 5);

        Player p1 = new Player(s, 12, "a");
        Player p2 = new Player(s, 7, "a");
        Player p3 = new Player(s, 14, "a");
        Player p4 = new Player(s, 9, "a");

        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        Slavers s1=new Slavers(1,3, 2, 4, 2);
        ArrayList<AdventureCard> deck = new ArrayList<>();

        Game g = new Game(4, players, deck, 0, 10);

        AbandonedShip as=new AbandonedShip(1, 2, 3, 2);

        deck.add(as);
        deck.add(s1);

        assertArrayEquals(players.toArray(),g.getPlayers().toArray());
    }

    public void testAdjustPlayerPositions() {
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for(int i=0; i<7; i++){
            for(int j=0; j<5; j++) {
                availablePositionMatrix[i][j] = (i != 0 || j != 0) && (i != 0 || j != 1) && (i != 1 || j != 0) && (i != 3 || j != 0) && (i != 5 || j != 0) && (i != 6 || j != 0) && (i != 6 || j != 1) && (i != 3 || j != 4);
            }
        }
        ShipBoard s = new ShipBoard(availablePositionMatrix, 7, 5);

        Player p1 = new Player(s, 12, "a");
        Player p2 = new Player(s, 7, "a");
        Player p3 = new Player(s, 14, "a");
        Player p4 = new Player(s, 9, "a");

        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        AbandonedShip as=new AbandonedShip(1, 2, 3, 2);
        Slavers s1=new Slavers(1,3, 2, 4, 2);
        ArrayList<AdventureCard> deck = new ArrayList<>();
        deck.add(as);
        deck.add(s1);

        Game g=new Game(4, players, deck,1, 30);

        p1.changePosition(4);
        p2.changePosition(1);
        p3.changePosition(9);
        p4.changePosition(5);

        
        int[] expectedPositions = {9, 5, 4, 1};
        int[] actualPositions = g.getPlayers().stream().mapToInt(Player::getPosition).toArray();

        assertArrayEquals(expectedPositions, actualPositions);
    }

    public void testGetOccupiedPositions() {
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for(int i=0; i<7; i++){
            for(int j=0; j<5; j++) {
                availablePositionMatrix[i][j] = (i != 0 || j != 0) && (i != 0 || j != 1) && (i != 1 || j != 0) && (i != 3 || j != 0) && (i != 5 || j != 0) && (i != 6 || j != 0) && (i != 6 || j != 1) && (i != 3 || j != 4);
            }
        }
        ShipBoard s = new ShipBoard(availablePositionMatrix, 7, 5);

        Player p1 = new Player(s, 12, "a");
        Player p2 = new Player(s, 7, "a");
        Player p3 = new Player(s, 14, "a");
        Player p4 = new Player(s, 9, "a");

        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        AbandonedShip as=new AbandonedShip(1, 2, 3, 2);
        Slavers s1=new Slavers(1,3, 2, 4, 2);
        ArrayList<AdventureCard> deck = new ArrayList<>();
        deck.add(as);
        deck.add(s1);

        Game g=new Game(4, players, deck,1, 30);

        p1.changePosition(4);
        p2.changePosition(1);
        p3.changePosition(9);
        p4.changePosition(5);


        assertEquals(1, g.getOccupiedPositions(p1, 2));
    }

    public void testPickCard() {
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for(int i=0; i<7; i++){
            for(int j=0; j<5; j++) {
                availablePositionMatrix[i][j] = (i != 0 || j != 0) && (i != 0 || j != 1) && (i != 1 || j != 0) && (i != 3 || j != 0) && (i != 5 || j != 0) && (i != 6 || j != 0) && (i != 6 || j != 1) && (i != 3 || j != 4);
            }
        }
        ShipBoard s = new ShipBoard(availablePositionMatrix, 7, 5);

        Player p1 = new Player(s, 12, "a");
        Player p2 = new Player(s, 7, "a");
        Player p3 = new Player(s, 14, "a");
        Player p4 = new Player(s, 9, "a");

        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        AbandonedShip as=new AbandonedShip(1, 2, 3, 2);
        Slavers s1=new Slavers(1,3, 2, 4, 2);
        ArrayList<AdventureCard> deck = new ArrayList<>();
        deck.add(as);
        deck.add(s1);

        Game g=new Game(4, players, deck,1, 30);

        g.Turn();

        AdventureCard pickedCard = g.getCurrentCard();
        assertFalse(deck.contains(pickedCard));
        assertEquals(1, deck.size());
    }

    public void testCheckGiveUp() {
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for(int i=0; i<7; i++){
            for(int j=0; j<5; j++) {
                availablePositionMatrix[i][j] = (i != 0 || j != 0) && (i != 0 || j != 1) && (i != 1 || j != 0) && (i != 3 || j != 0) && (i != 5 || j != 0) && (i != 6 || j != 0) && (i != 6 || j != 1) && (i != 3 || j != 4);
            }
        }
        ShipBoard s = new ShipBoard(availablePositionMatrix, 7, 5);

        Player p1 = new Player(s, 12, "a");
        Player p2 = new Player(s, 7, "a");
        Player p3 = new Player(s, 14, "a");
        Player p4 = new Player(s, 9, "a");

        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        AbandonedShip as=new AbandonedShip(1, 2, 3, 2);
        Slavers s1=new Slavers(1,3, 2, 4, 2);
        ArrayList<AdventureCard> deck = new ArrayList<>();
        deck.add(as);
        deck.add(s1);

        Game g=new Game(4, players, deck,1, 30);

        p1.abandon();
        p3.abandon();
        assertTrue(g.checkGiveUp(p1));
        assertTrue(g.checkGiveUp(p3));
        assertFalse(g.checkGiveUp(p2));
        assertFalse(g.checkGiveUp(p4));
    }

    public void testCalculateWinners() {
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for(int i=0; i<7; i++){
            for(int j=0; j<5; j++) {
                availablePositionMatrix[i][j] = (i != 0 || j != 0) && (i != 0 || j != 1) && (i != 1 || j != 0) && (i != 3 || j != 0) && (i != 5 || j != 0) && (i != 6 || j != 0) && (i != 6 || j != 1) && (i != 3 || j != 4);
            }
        }
        ShipBoard sp1 = new ShipBoard(availablePositionMatrix, 7, 5);
        ShipBoard sp2 = new ShipBoard(availablePositionMatrix, 7, 5);
        ShipBoard sp3 = new ShipBoard(availablePositionMatrix, 7, 5);
        ShipBoard sp4 = new ShipBoard(availablePositionMatrix, 7, 5);

        Cabin c11 = new Cabin(true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Storage s11 = new Storage(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon1 = new Cannon(1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s21 = new Storage(false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c21 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c31 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c41 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c51 = new Cabin(false, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c61 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c71 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c81 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c91 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e11 = new Engine(1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

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

        Cabin c12 = new Cabin(true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Storage s12 = new Storage(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon2 = new Cannon(1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s22 = new Storage(false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c22 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c32 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c42 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c52 = new Cabin(false, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c62 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c72 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c82 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c92 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e12 = new Engine(1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        sp2.placeComponent(3,2, c12);
        sp2.placeComponent(2,2, s22);
        sp2.placeComponent(4,2, s12);
        sp2.placeComponent(4,1, cannon2);
        sp2.placeComponent(1,3, c52);
        sp2.placeComponent(2,3, c22);
        sp2.placeComponent(1,4, c42);
        sp2.placeComponent(2,4, c32);
        sp2.placeComponent(4,3, c62);
        sp2.placeComponent(5,3, c72);
        sp2.placeComponent(4,4, e12);
        sp2.placeComponent(5,4, c92);
        sp2.placeComponent(3,3, c82);

        Cabin c13 = new Cabin(true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Storage s13 = new Storage(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon3 = new Cannon(1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s23 = new Storage(false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c23 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c33 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c43 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c53 = new Cabin(false, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c63 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c73 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c83 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c93 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e13 = new Engine(1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        sp3.placeComponent(3,2, c13);
        sp3.placeComponent(2,2, s23);
        sp3.placeComponent(4,2, s13);
        sp3.placeComponent(4,1, cannon3);
        sp3.placeComponent(1,3, c53);
        sp3.placeComponent(2,3, c23);
        sp3.placeComponent(1,4, c43);
        sp3.placeComponent(2,4, c33);
        sp3.placeComponent(4,3, c63);
        sp3.placeComponent(5,3, c73);
        sp3.placeComponent(4,4, e13);
        sp3.placeComponent(5,4, c93);
        sp3.placeComponent(3,3, c83);

        Cabin c14 = new Cabin(true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Storage s14 = new Storage(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon4 = new Cannon(1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s24 = new Storage(false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c24 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c34 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c44 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c54 = new Cabin(false, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c64 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c74 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c84 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c94 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e14 = new Engine(1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

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

        Player p1 = new Player(sp1, 12, "a");
        Player p2 = new Player(sp2, 7, "a");
        Player p3 = new Player(sp3, 14, "a");
        Player p4 = new Player(sp4, 9, "a");

        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        AbandonedShip as = new AbandonedShip(1, 2, 3, 2);
        Slavers sl = new Slavers(1,3, 2, 4, 2);
        ArrayList<AdventureCard> deck = new ArrayList<>();
        deck.add(as);
        deck.add(sl);

        Game g=new Game(4, players, deck,1, 30);

        ArrayList<Player> win  = new ArrayList<>();

        for (Player p : players) {
            p.changeCredits(2);
            win.add(p);
        }
        p1.changeCredits(-2);
        win.remove(p1);



    }

    public void testCalculateFinalCredits() {
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for(int i=0; i<7; i++){
            for(int j=0; j<5; j++) {
                availablePositionMatrix[i][j] = (i != 0 || j != 0) && (i != 0 || j != 1) && (i != 1 || j != 0) && (i != 3 || j != 0) && (i != 5 || j != 0) && (i != 6 || j != 0) && (i != 6 || j != 1) && (i != 3 || j != 4);
            }
        }
        ShipBoard sp1 = new ShipBoard(availablePositionMatrix, 7, 5);
        ShipBoard sp2 = new ShipBoard(availablePositionMatrix, 7, 5);
        ShipBoard sp3 = new ShipBoard(availablePositionMatrix, 7, 5);
        ShipBoard sp4 = new ShipBoard(availablePositionMatrix, 7, 5);

        Cabin c11 = new Cabin(true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Storage s11 = new Storage(true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon1 = new Cannon(1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s21 = new Storage(false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c21 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c31 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.DOUBLE});
        Cabin c41 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.EMPTY});
        Cabin c51 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY});
        Cabin c61 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c71 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c81 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c91 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e11 = new Engine(1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

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

        Cabin c12 = new Cabin(true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Storage s12 = new Storage(true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon2 = new Cannon(1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s22 = new Storage(false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c22 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c32 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c42 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c52 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c62 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c72 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c82 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c92 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e12 = new Engine(1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        sp2.placeComponent(3,2, c12);
        sp2.placeComponent(2,2, s22);
        sp2.placeComponent(4,2, s12);
        sp2.placeComponent(4,1, cannon2);
        sp2.placeComponent(1,3, c52);
        sp2.placeComponent(2,3, c22);
        sp2.placeComponent(1,4, c42);
        sp2.placeComponent(2,4, c32);
        sp2.placeComponent(4,3, c62);
        sp2.placeComponent(5,3, c72);
        sp2.placeComponent(4,4, e12);
        sp2.placeComponent(5,4, c92);
        sp2.placeComponent(3,3, c82);

        Cabin c13 = new Cabin(true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Storage s13 = new Storage(true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon3 = new Cannon(1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s23 = new Storage(false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c23 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c33 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.DOUBLE});
        Cabin c43 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.EMPTY});
        Cabin c53 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY});
        Cabin c63 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c73 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c83 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c93 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e13 = new Engine(1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        sp3.placeComponent(3,2, c13);
        sp3.placeComponent(2,2, s23);
        sp3.placeComponent(4,2, s13);
        sp3.placeComponent(4,1, cannon3);
        sp3.placeComponent(1,3, c53);
        sp3.placeComponent(2,3, c23);
        sp3.placeComponent(1,4, c43);
        sp3.placeComponent(2,4, c33);
        sp3.placeComponent(4,3, c63);
        sp3.placeComponent(5,3, c73);
        sp3.placeComponent(4,4, e13);
        sp3.placeComponent(5,4, c93);
        sp3.placeComponent(3,3, c83);

        Cabin c14 = new Cabin(true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Storage s14 = new Storage(true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon4 = new Cannon(1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s24 = new Storage(false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c24 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c34 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.DOUBLE});
        Cabin c44 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.EMPTY});
        Cabin c54 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY});
        Cabin c64 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c74 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c84 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c94 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e14 = new Engine(1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

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

        Player p1 = new Player(sp1, 12, "a");
        Player p2 = new Player(sp2, 7, "a");
        Player p3 = new Player(sp3, 14, "a");
        Player p4 = new Player(sp4, 9, "a");

        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        AbandonedShip as = new AbandonedShip(1, 2, 3, 2);
        Slavers sl = new Slavers(1,3, 2, 4, 2);
        ArrayList<AdventureCard> deck = new ArrayList<>();
        deck.add(as);
        deck.add(sl);

        Game g=new Game(4, players, deck,1, 30);

        ArrayList<Player> win  = new ArrayList<>();

        p3.changePosition(5);// p3 p1 p2 p4


        p1.abandon();   // p1 abbandona

        Goods g1 = new Goods(GoodsColour.RED); //4
        Goods g2 = new Goods(GoodsColour.RED);
        Goods g3 = new Goods(GoodsColour.GREEN); //2
        Goods g4 = new Goods(GoodsColour.GREEN);
        Goods g5 = new Goods(GoodsColour.YELLOW); //3
        Goods g6 = new Goods(GoodsColour.YELLOW);
        Goods g7 = new Goods(GoodsColour.BLUE); //1
        Goods g8 = new Goods(GoodsColour.BLUE);

        s11.addGood(g1);
        s11.addGood(g7);
        s12.addGood(g5);
        s12.addGood(g3);
        s13.addGood(g8);
        s13.addGood(g2);
        s14.addGood(g4);
        s14.addGood(g6);



        assertEquals(3, p1.getNumCredits());
        assertEquals(8, p2.getNumCredits());
        assertEquals(11, p3.getNumCredits());
        assertEquals(9, p4.getNumCredits());

    }
}