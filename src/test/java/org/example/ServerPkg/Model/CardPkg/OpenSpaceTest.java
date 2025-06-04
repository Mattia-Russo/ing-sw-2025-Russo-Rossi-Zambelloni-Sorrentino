package org.example.ServerPkg.Model.CardPkg;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.ControllerPkg.PlayerStates.AbandonedState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.ActivateEnginesState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.WaitingState;
import org.example.ServerPkg.Model.ComponentsPkg.*;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;
import org.example.ServerPkg.Model.ShipBoard;

import java.util.ArrayList;

public class OpenSpaceTest extends TestCase {

    public void testCheckEnginePower() {
        Player p1 = new Player("a", null);
        Player p2 = new Player( "a", null);
        Player p3 = new Player( "a", null);
        Player p4 = new Player( "a", null);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        Game g=new Game(4,1, 1, new GameController());
        g.getPlayers().addAll(players);
        g.setPlayersShipboard();
        ShipBoard s1 = p1.getPlayerShipBoard();
        ShipBoard s2 = p2.getPlayerShipBoard();
        ShipBoard s3 = p3.getPlayerShipBoard();
        ShipBoard s4 = p4.getPlayerShipBoard();

        s1.setSingleEnginePower(1);
        s2.setSingleEnginePower(0);
        s2.setNumDoubleEngines(0);
        s2.setTotalBattery(0);
        s3.setSingleEnginePower(0);
        s3.setNumDoubleEngines(1);
        s3.setTotalBattery(1);
        s4.setSingleEnginePower(0);
        s4.setNumDoubleEngines(0);
        s4.setTotalBattery(0);

        OpenSpace openSpace = new OpenSpace(0,2, 1);
        /*openSpace.checkEnginePower(players, g);

        assertFalse(p1.isAbandoned());
        assertTrue(p2.isAbandoned());
        assertFalse(p3.isAbandoned());
        assertTrue(p4.isAbandoned());*/
    }

    public void testSetCardState() {
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("a", null);
        Player p2 = new Player("b", null);
        Player p3 = new Player( "c", null);
        players.add(p1);
        players.add(p2);
        players.add(p3);

        Game game = new Game(3, 2, 1, new GameController() );
        game.getPlayers().addAll(players);
        game.setPlayersShipboard();
        OpenSpace card = new OpenSpace(0,2, 0);

        p1.getPlayerShipBoard().setNumDoubleEngines(2);
        p2.getPlayerShipBoard().setNumDoubleEngines(0);
        p2.abandon(game);
        p3.getPlayerShipBoard().setNumDoubleEngines(1);

        /*card.setCardState(game);
        assertTrue(p1.getState() instanceof ActivateEnginesState);

        card.setCardState(game);
        assertTrue(p2.getState() instanceof AbandonedState);

        card.setCardState(game);
        assertTrue(p3.getState() instanceof ActivateEnginesState);*/
    }

    public void testPlayCard() {
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("a", null);
        Player p2 = new Player("b", null);
        Player p3 = new Player("c", null);
        players.add(p1);
        players.add(p2);
        players.add(p3);

        Game game = new Game(3, 2, 1, new GameController());
        game.getPlayers().addAll(players);
        game.setPlayersShipboard();
        OpenSpace card = new OpenSpace(0,2, 0);

        Engine e1 = new Engine(0,2, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e2 = new Engine(0,1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        BatteryStorage bs1 = new BatteryStorage(0,3, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        BatteryStorage bs2 = new BatteryStorage(0,2, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});

        p1.getPlayerShipBoard().placeComponent(5,7, e1);
        p1.getPlayerShipBoard().placeComponent(6,7, e2);
        p1.getPlayerShipBoard().placeComponent(4,7, bs1);
        p1.getPlayerShipBoard().placeComponent(8,7, bs2);

        ArrayList<Points> engines = new ArrayList<Points>();
        ArrayList<Points> batteries = new ArrayList<Points>();
        engines.add(new Points(5, 7));
        batteries.add(new Points(8, 7));

        card.setCardState(game);
        card.playCard(game, engines, batteries);

        assertTrue(p1.getState() instanceof WaitingState);
        assertEquals(3, p1.getPosition());


    }
}