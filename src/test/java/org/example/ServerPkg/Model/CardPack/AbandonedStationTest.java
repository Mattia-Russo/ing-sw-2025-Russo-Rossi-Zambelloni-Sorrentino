package org.example.ServerPkg.Model.CardPack;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.PlayerStates.ChangeGoodsState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.LandOnAbandonState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.WaitingState;
import org.example.ServerPkg.Model.ComponentsPack.Goods;
import org.example.ServerPkg.Model.ComponentsPack.GoodsColour;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

import java.util.ArrayList;

public class AbandonedStationTest extends TestCase {

    public void testGetCardLevel() {
        Goods[] goods = new Goods[3];
        goods[0] = new Goods(GoodsColour.RED);
        goods[1] = new Goods(GoodsColour.YELLOW);
        goods[2] = new Goods(GoodsColour.GREEN);
        AbandonedStation a=new AbandonedStation(1,1, 5, goods);
        assertEquals(1,a.getCardLevel());
    }

    public void testGetNumAstronauts() {
        Goods[] goods = new Goods[3];
        goods[0] = new Goods(GoodsColour.RED);
        goods[1] = new Goods(GoodsColour.YELLOW);
        goods[2] = new Goods(GoodsColour.GREEN);
        AbandonedStation a=new AbandonedStation(1,1, 5, goods);
        assertEquals(5,a.getNumAstronauts());
    }

    public void testGetGoodsList() {
        Goods[] goods = new Goods[3];
        goods[0] = new Goods(GoodsColour.RED);
        goods[1] = new Goods(GoodsColour.YELLOW);
        goods[2] = new Goods(GoodsColour.GREEN);
        AbandonedStation a=new AbandonedStation(1,1, 5, goods);
        assertEquals(goods,a.getGoodsList());
        assertEquals(3,a.getGoodsList().length);
    }

    public void testGetLostDays() {
        Goods[] goods = new Goods[3];
        goods[0] = new Goods(GoodsColour.RED);
        goods[1] = new Goods(GoodsColour.YELLOW);
        goods[2] = new Goods(GoodsColour.GREEN);
        AbandonedStation a=new AbandonedStation(1,1, 5, goods);
        assertEquals(1,a.getLostDays());
    }

    public void testSetCardState() {
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player(1, "a");
        Player p2 = new Player(2, "b");
        Player p3 = new Player( 3, "c");
        players.add(p1);
        players.add(p2);
        players.add(p3);

        Game game = new Game(3, 2, 1, null);
        Goods[] goods = new Goods[3];
        goods[0] = new Goods(GoodsColour.GREEN);
        goods[1] = new Goods(GoodsColour.RED);
        goods[2] = new Goods(GoodsColour.YELLOW);

        AbandonedStation card = new AbandonedStation(1, 2, 3, goods);
        game.setCard(card);

        card.setCardState(game);
        assertTrue(p1.getState() instanceof LandOnAbandonState);
        assertTrue(p2.getState() instanceof WaitingState);
        assertTrue(p3.getState() instanceof WaitingState);
        p1.setPlayerState(new WaitingState());

        card.setCardState(game);
        assertTrue(p1.getState() instanceof WaitingState);
        assertTrue(p2.getState() instanceof LandOnAbandonState);
        assertTrue(p3.getState() instanceof WaitingState);
        p2.setPlayerState(new WaitingState());

        card.setCardState(game);
        assertTrue(p1.getState() instanceof WaitingState);
        assertTrue(p2.getState() instanceof WaitingState);
        assertTrue(p3.getState() instanceof LandOnAbandonState);
        p3.setPlayerState(new WaitingState());

        assertTrue(p1.getState() instanceof WaitingState);
        assertTrue(p2.getState() instanceof WaitingState);
        assertTrue(p3.getState() instanceof WaitingState);
    }

    public void testPlayCard() {
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player(1, "a");
        Player p2 = new Player(2, "b");
        Player p3 = new Player( 3, "c");
        players.add(p1);
        players.add(p2);
        players.add(p3);

        Game game = new Game(3, 2, 1, null);
        Goods[] goods = new Goods[3];
        goods[0] = new Goods(GoodsColour.GREEN);
        goods[1] = new Goods(GoodsColour.RED);
        goods[2] = new Goods(GoodsColour.YELLOW);

        AbandonedStation card = new AbandonedStation(1, 2, 3, goods);
        game.setCard(card);
        card.setCardState(game);
        card.playCard(game);
        assertTrue(p1.getState() instanceof ChangeGoodsState);
        card.setChangeGoodsFlag(false);
        card.playCard(game);
        assertEquals(-2, p1.getPosition());
        assertTrue(p1.getState() instanceof WaitingState);
        assertEquals(-1, card.getCurrentPlayerIndex());
        assertTrue(card.getChangeGoodsFlag());
    }

    public void testSetChangeGoodsFlag() {
        Goods[] goods = new Goods[3];
        goods[0] = new Goods(GoodsColour.GREEN);
        goods[1] = new Goods(GoodsColour.RED);
        goods[2] = new Goods(GoodsColour.YELLOW);

        AbandonedStation card = new AbandonedStation(1, 2, 3, goods);

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

        AbandonedStation card = new AbandonedStation(1, 2, 3, goods);

        assertEquals(-1,card.getCurrentPlayerIndex());
    }
}