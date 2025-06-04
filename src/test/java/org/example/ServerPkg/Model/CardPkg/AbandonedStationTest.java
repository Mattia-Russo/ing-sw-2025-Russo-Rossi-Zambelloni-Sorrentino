package org.example.ServerPkg.Model.CardPkg;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.ControllerPkg.PlayerStates.ChangeGoodsState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.LandOnAbandonState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.WaitingState;
import org.example.ServerPkg.Model.ComponentsPkg.Goods;
import org.example.ServerPkg.Model.ComponentsPkg.GoodsColour;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

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

    public void testSetCardState() {
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

    public void testPlayCard() {
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
        players.getFirst().getPlayerShipBoard().setNumAstronauts(3);
        Goods[] goods = new Goods[3];
        goods[0] = new Goods(GoodsColour.GREEN);
        goods[1] = new Goods(GoodsColour.RED);
        goods[2] = new Goods(GoodsColour.YELLOW);

        AbandonedStation card = new AbandonedStation(0,1, 2, 3, goods);
        game.setCard(card);
        card.setCardState(game);
        assertTrue(players.getFirst().getState() instanceof LandOnAbandonState);
        card.setChangeGoodsFlag(false);
        card.playCard(game, 0);
        assertEquals(-2, players.getFirst().getPosition());
        assertTrue(players.getFirst().getState() instanceof WaitingState);
        assertEquals(-1, card.getCurrentPlayerIndex());
        assertTrue(card.getChangeGoodsFlag());
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

    public void testGetCurrentPlayerIndex() {
        Goods[] goods = new Goods[3];
        goods[0] = new Goods(GoodsColour.GREEN);
        goods[1] = new Goods(GoodsColour.RED);
        goods[2] = new Goods(GoodsColour.YELLOW);

        AbandonedStation card = new AbandonedStation(0,1, 2, 3, goods);

        assertEquals(-1,card.getCurrentPlayerIndex());
    }
}