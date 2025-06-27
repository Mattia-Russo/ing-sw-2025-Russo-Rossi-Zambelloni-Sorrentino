package org.example.ServerPkg.Model.CardPkg;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.ControllerPkg.PlayerStates.ChangeGoodsState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.LandOnAbandonState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.WaitingState;
import org.example.ServerPkg.Model.ComponentsPkg.*;
import org.example.ServerPkg.Model.ForView.AdventureCardView;
import org.example.ServerPkg.Model.ForView.GoodsView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

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
        // Setup game with 3 players
        Game game = new Game(3, 2, 1, new GameController());
        Player p1 = new Player("P1", game);
        Player p2 = new Player("P2", game);
        Player p3 = new Player("P3", game);

        game.getPlayers().add(p1);
        game.getPlayers().add(p2);
        game.getPlayers().add(p3);
        game.setPlayersShipboard();

        // Add 1 astronaut to P1 and P2, 3 to P3
        p1.getPlayerShipBoard().placeComponent(6, 6, new Cabin(1,false, Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY})); // 1 astronaut
        p2.getPlayerShipBoard().placeComponent(6, 6, new Cabin(1,false, Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}));// 1 astronaut
        p3.getPlayerShipBoard().placeComponent(6, 6, new Cabin(1,false, Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}));// 3 astronauts

        // Create AbandonedStation card with 3 astronaut requirement
        Goods[] goods = new Goods[] { new Goods(GoodsColour.RED) };
        AbandonedStation card = new AbandonedStation(1, 1, 2, 3, goods);

        // Execute setCardState
        card.setCardState(game);

        // Verify only P3 enters LandOnAbandonState
        assertTrue(p1.getState() instanceof LandOnAbandonState);
        assertFalse(p2.getState() instanceof LandOnAbandonState);
        assertFalse(p3.getState() instanceof LandOnAbandonState);
    }

    public void testPlayCard() throws RemoteException {
        Game game = new Game(3, 1, 1, new GameController()){
            @Override
            public void Turn() {
            }
        };
        Player p = new Player("Player", null);
        game.getPlayers().add(p);
        game.setPlayersShipboard();
        p.setPlayerShipboard(1);

        Goods[] goods = new Goods[]{new Goods(GoodsColour.GREEN), new Goods(GoodsColour.RED)};
        AbandonedStation card = new AbandonedStation(1, 1, 2, 0, goods);  // 0 astronauti richiesti, quindi può sempre atterrare

        // ---- Fase 1: setCardState() → deve entrare in LandOnAbandonState
        card.setCardState(game);
        assertTrue(p.getState() instanceof LandOnAbandonState);

        // ---- Fase 2: Prima chiamata playCard (flag attivo) → entra in ChangeGoodsState
        card.playCard(game);
        assertTrue(p.getState() instanceof ChangeGoodsState);
        assertTrue(card.getChangeGoodsFlag());

        // ---- Fase 3: Seconda chiamata playCard (flag disattivato) → cambia posizione e passa il turno
        int initialPos = p.getPosition();
        card.setChangeGoodsFlag(false);
        card.playCard(game);

        // Dopo aver finito il turno, la posizione del giocatore cambia
        assertEquals(initialPos - 2, p.getPosition());  // lostDays = 2
        assertTrue(card.getChangeGoodsFlag()); // torna true per il prossimo turno

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

//    public void testGetCurrentPlayerIndex() {
//        Goods[] goods = new Goods[3];
//        goods[0] = new Goods(GoodsColour.GREEN);
//        goods[1] = new Goods(GoodsColour.RED);
//        goods[2] = new Goods(GoodsColour.YELLOW);
//
//        AbandonedStation card = new AbandonedStation(0,1, 2, 3, goods);
//
//        assertEquals(-1,card.getCurrentPlayerIndex());
//    }

    public void testCreateView() {

        Goods[] goods = new Goods[3];
        goods[0] = new Goods(GoodsColour.RED);
        goods[1] = new Goods(GoodsColour.YELLOW);
        goods[2] = new Goods(GoodsColour.GREEN);

        AbandonedStation card = new AbandonedStation(5, 2, 1, 4, goods);
        AdventureCardView view = card.createView();

        assertEquals("AbandonedStation", view.getType());
        assertEquals(5, view.getId());
        assertEquals(1, view.getLostDays());
        assertEquals(4, view.getNumAstronauts());

        List<GoodsView> goodsView = view.getGoodsList();
        assertEquals(3, goodsView.size());
        assertEquals(GoodsColour.RED, goodsView.get(0).getColour());
        assertEquals(GoodsColour.YELLOW, goodsView.get(1).getColour());
        assertEquals(GoodsColour.GREEN, goodsView.get(2).getColour());

        String command = view.getCommands();
        assertTrue(command.contains("land_on_abandon"));
        assertTrue(command.contains("add_good"));
        assertTrue(command.contains("remove_good"));
        assertTrue(command.contains("end_change_goods"));
    }

    public void testGetChangeGoodsFlag() {
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