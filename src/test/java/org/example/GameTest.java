package org.example;

import junit.framework.TestCase;
import org.example.CardPack.*;
import org.example.ComponentsPack.Components;

import java.util.ArrayList;
import java.util.List;

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

        Player p1 = new Player(s, 12);
        Player p2 = new Player(s, 7);
        Player p3 = new Player(s, 14);
        Player p4 = new Player(s, 9);

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

        Game g=new Game(4, players, deck,1);

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

        Player p1 = new Player(s, 12);
        Player p2 = new Player(s, 7);
        Player p3 = new Player(s, 14);
        Player p4 = new Player(s, 9);

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

        Game g=new Game(4, players, deck,1);

        p1.changePosition(4);
        p2.changePosition(1);
        p3.changePosition(9);
        p4.changePosition(5);

        g.adjustPlayerPositions();
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

        Player p1 = new Player(s, 12);
        Player p2 = new Player(s, 7);
        Player p3 = new Player(s, 14);
        Player p4 = new Player(s, 9);

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

        Game g=new Game(4, players, deck,1);

        p1.changePosition(4);
        p2.changePosition(1);
        p3.changePosition(9);
        p4.changePosition(5);
        g.adjustPlayerPositions();

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

        Player p1 = new Player(s, 12);
        Player p2 = new Player(s, 7);
        Player p3 = new Player(s, 14);
        Player p4 = new Player(s, 9);

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

        Game g=new Game(4, players, deck,1);

        AdventureCard pickedCard = g.pickCard();
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

        Player p1 = new Player(s, 12);
        Player p2 = new Player(s, 7);
        Player p3 = new Player(s, 14);
        Player p4 = new Player(s, 9);

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

        Game g=new Game(4, players, deck,1);

        p1.abandon();
        p3.abandon();
        assertTrue(g.checkGiveUp(p1));
        assertTrue(g.checkGiveUp(p3));
        assertFalse(g.checkGiveUp(p2));
        assertFalse(g.checkGiveUp(p4));
    }

    public void testCalculateWinner() {
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for(int i=0; i<7; i++){
            for(int j=0; j<5; j++) {
                availablePositionMatrix[i][j] = (i != 0 || j != 0) && (i != 0 || j != 1) && (i != 1 || j != 0) && (i != 3 || j != 0) && (i != 5 || j != 0) && (i != 6 || j != 0) && (i != 6 || j != 1) && (i != 3 || j != 4);
            }
        }
        ShipBoard s = new ShipBoard(availablePositionMatrix, 7, 5);

        Player p1 = new Player(s, 12);
        Player p2 = new Player(s, 7);
        Player p3 = new Player(s, 14);
        Player p4 = new Player(s, 9);

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

        Game g=new Game(4, players, deck,1);

        p1.changeCredits(4);
        p3.changeCredits(7);
        p4.changeCredits(14);
        assertEquals(p4, g.calculateWinner());
    }

}