package org.example.ServerPkg.Model.CardPkg;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.PlayerStates.LandOnAbandonState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.WaitingState;
import org.example.ServerPkg.Model.ComponentsPkg.Goods;
import org.example.ServerPkg.Model.ComponentsPkg.GoodsColour;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

import java.util.ArrayList;

public class AbandonedShipTest extends TestCase {

    public void testGetCardLevel() {
        AbandonedShip a=new AbandonedShip(0,1,2,10, 3);
        assertEquals(1,a.getCardLevel());
    }

    public void testGetLostDays() {
        AbandonedShip a=new AbandonedShip(0,1,2,10, 3);
        assertEquals(2,a.getLostDays());
    }

    public void testGetCredits() {
        AbandonedShip a=new AbandonedShip(0,1,2,10, 3);
        assertEquals(10,a.getCredits());
    }

    public void testGetNumAstronauts() {
        AbandonedShip a=new AbandonedShip(0,1,2,10, 3);
        assertEquals(3,a.getNumAstronauts());
    }

    public void testSetCardState() {
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("a", null);
        Player p2 = new Player("b", null);
        Player p3 = new Player( "c", null);
        players.add(p1);
        players.add(p2);
        players.add(p3);

        Game game = new Game(3, 2,1, null);
        Goods[] goods = new Goods[3];
        goods[0] = new Goods(GoodsColour.GREEN);
        goods[1] = new Goods(GoodsColour.RED);
        goods[2] = new Goods(GoodsColour.YELLOW);

        AbandonedStation card = new AbandonedStation(0,1, 2, 3, goods);
        game.setCard(card);

        card.setCardState(game);
        assertTrue(p1.getState() instanceof LandOnAbandonState);
        assertTrue(p2.getState() instanceof WaitingState);
        assertTrue(p3.getState() instanceof WaitingState);
        p1.setPlayerState(new WaitingState(game));

        card.setCardState(game);
        assertTrue(p1.getState() instanceof WaitingState);
        assertTrue(p2.getState() instanceof LandOnAbandonState);
        assertTrue(p3.getState() instanceof WaitingState);
        p2.setPlayerState(new WaitingState(game));

        card.setCardState(game);
        assertTrue(p1.getState() instanceof WaitingState);
        assertTrue(p2.getState() instanceof WaitingState);
        assertTrue(p3.getState() instanceof LandOnAbandonState);
        p3.setPlayerState(new WaitingState(game));

        assertTrue(p1.getState() instanceof WaitingState);
        assertTrue(p2.getState() instanceof WaitingState);
        assertTrue(p3.getState() instanceof WaitingState);
    }

    public void testPlayCard() {
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("a", null);
        Player p2 = new Player("b", null);
        Player p3 = new Player( "c", null);
        players.add(p1);
        players.add(p2);
        players.add(p3);


        Game game = new Game(3, 2, 1, null);
        p1.setPlayerState(new LandOnAbandonState(game));
        AbandonedShip card = new AbandonedShip(0,1, 2, 5, 3);
        game.setCard(card);
        card.setCardState(game);
        p1.getPlayerShipBoard().setNumAstronauts(5);
        card.playCard(game);
        assertEquals(2, p1.getPlayerShipBoard().getTotalAstronauts());
        assertEquals(5, p1.getNumCredits());
        assertEquals(-2, p1.getPosition());
        assertTrue(p1.getState() instanceof WaitingState);
        assertEquals(-1, card.getCurrentPlayerIndex());


    }

    public void testGetCurrentPlayerIndex() {
        AbandonedShip card = new AbandonedShip(0,1, 2, 5, 3);
        assertEquals(-1,card.getCurrentPlayerIndex());
    }
}