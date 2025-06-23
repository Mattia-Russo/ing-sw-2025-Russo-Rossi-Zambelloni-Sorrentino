package org.example.ServerPkg.Model.CardPkg;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.ControllerPkg.PlayerStates.ChangeGoodsState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.LandOnAbandonState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.WaitingState;
import org.example.ServerPkg.Model.ComponentsPkg.Alien;
import org.example.ServerPkg.Model.ComponentsPkg.AlienColour;
import org.example.ServerPkg.Model.ComponentsPkg.Goods;
import org.example.ServerPkg.Model.ComponentsPkg.GoodsColour;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class AbandonedStationTest extends TestCase {

    public void testGetCardLevel() {
        Goods[] goods = new Goods[3];
        goods[0] = new Goods(GoodsColour.RED);
        goods[1] = new Goods(GoodsColour.YELLOW);
        goods[2] = new Goods(GoodsColour.GREEN);
        AbandonedStation a=new AbandonedStation(0,1,1, 5, goods);
        assertEquals(1,a.getCardLevel());
    }

    public void testGetNumAstronauts() {
        Goods[] goods = new Goods[3];
        goods[0] = new Goods(GoodsColour.RED);
        goods[1] = new Goods(GoodsColour.YELLOW);
        goods[2] = new Goods(GoodsColour.GREEN);
        AbandonedStation a=new AbandonedStation(0,1,1, 5, goods);
        assertEquals(5,a.getNumAstronauts());
    }

    public void testGetGoodsList() {
        Goods[] goods = new Goods[3];
        goods[0] = new Goods(GoodsColour.RED);
        goods[1] = new Goods(GoodsColour.YELLOW);
        goods[2] = new Goods(GoodsColour.GREEN);
        AbandonedStation a=new AbandonedStation(0,1,1, 5, goods);
        assertEquals(goods,a.getGoodsList());
        assertEquals(3,a.getGoodsList().length);
    }

    public void testGetLostDays() {
        Goods[] goods = new Goods[3];
        goods[0] = new Goods(GoodsColour.RED);
        goods[1] = new Goods(GoodsColour.YELLOW);
        goods[2] = new Goods(GoodsColour.GREEN);
        AbandonedStation a=new AbandonedStation(0,1,1, 5, goods);
        assertEquals(1,a.getLostDays());
    }

    public void testSetCardState() throws RemoteException {
        GameController g = new GameController();
        g.createLobby("a",3,2,1);
        g.joinLobby("b");
        g.joinLobby("c");
        Game game = g.getGame();
        game.setPlayersShipboard();
        ArrayList<Player> players = new ArrayList<>();
        players.add(game.getPlayers().get(0));
        players.add(game.getPlayers().get(1));
        players.add(game.getPlayers().get(2));
        Goods[] goods = new Goods[3];
        goods[0] = new Goods(GoodsColour.GREEN);
        goods[1] = new Goods(GoodsColour.RED);
        goods[2] = new Goods(GoodsColour.YELLOW);

        AbandonedStation card = new AbandonedStation(0,1, 2, 3, goods);
        game.setCard(card);

        card.setCardState(game);
        assertTrue(players.get(0).getState() instanceof LandOnAbandonState);
        assertTrue(players.get(1).getState() instanceof WaitingState);
        assertTrue(players.get(2).getState() instanceof WaitingState);
        players.get(0).setPlayerState(new WaitingState(game));

        card.setCardState(game);
        assertTrue(players.get(0).getState() instanceof WaitingState);
        assertTrue(players.get(1).getState() instanceof LandOnAbandonState);
        assertTrue(players.get(2).getState() instanceof WaitingState);
        players.get(1).setPlayerState(new WaitingState(game));

        card.setCardState(game);
        assertTrue(players.get(0).getState() instanceof WaitingState);
        assertTrue(players.get(1).getState() instanceof WaitingState);
        assertTrue(players.get(2).getState() instanceof LandOnAbandonState);
        players.get(2).setPlayerState(new WaitingState(game));

        assertTrue(players.get(0).getState() instanceof WaitingState);
        assertTrue(players.get(1).getState() instanceof WaitingState);
        assertTrue(players.get(2).getState() instanceof WaitingState);
    }

    public void testPlayCard() throws RemoteException {
        Player p1 = new Player("a", null);
        Player p2 = new Player( "b", null);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        Game g=new Game(2, 2, 1, new GameController());
        g.getPlayers().addAll(players);
        g.setPlayersShipboard();
        p1.opShip(g);
        p2.opShip(g);
        p1.getPlayerShipBoard().getComponent(6,8).isCabin().addAlien(new Alien(AlienColour.BROWN), p1.getPlayerShipBoard());
        Goods[] goods = new Goods[3];
        goods[0] = new Goods(GoodsColour.GREEN);
        goods[1] = new Goods(GoodsColour.RED);
        goods[2] = new Goods(GoodsColour.YELLOW);

        AbandonedStation card = new AbandonedStation(0,1, 2, 7, goods);

        card.setCardState(g);
        assertTrue(players.getFirst().getState() instanceof LandOnAbandonState);
        card.setChangeGoodsFlag(true);
        card.playCard(g, 0);
        assertTrue(players.getFirst().getState() instanceof ChangeGoodsState);
        card.setChangeGoodsFlag(false);
        card.playCard(g, 0);
        assertEquals(-2, players.getFirst().getPosition());
        assertTrue(players.getFirst().getState() instanceof WaitingState);
    }

    public void testSetChangeGoodsFlag() {
        Goods[] goods = new Goods[3];
        goods[0] = new Goods(GoodsColour.GREEN);
        goods[1] = new Goods(GoodsColour.RED);
        goods[2] = new Goods(GoodsColour.YELLOW);

        AbandonedStation card = new AbandonedStation(0,1, 2, 3, goods);

        assertTrue(card.getChangeGoodsFlag());
        card.setChangeGoodsFlag(false);
        assertFalse(card.getChangeGoodsFlag());
        card.setChangeGoodsFlag(true);
        assertTrue(card.getChangeGoodsFlag());
    }
}