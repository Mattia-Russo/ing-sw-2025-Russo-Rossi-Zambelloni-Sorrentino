package org.example.ServerPkg.Model.CardPkg;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.ComponentsPkg.Cabin;
import org.example.ServerPkg.Model.ComponentsPkg.Connector;
import org.example.ServerPkg.Model.ComponentsPkg.Direction;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.ShipBoard;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class EpidemicTest extends TestCase {

    public void testCheckAdjacentCabins() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("a", null);
        players.add(p1);

        Game game = new Game(3, 2, 1, new GameController());
        game.getPlayers().addAll(players);
        game.setPlayersShipboard();
        Cabin cabin1 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin cabin2 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        p1.getPlayerShipBoard().placeComponent(5,6, cabin1);
        p1.getPlayerShipBoard().placeComponent(6,6, cabin2);
        Epidemic epidemic = new Epidemic(0,1,0);
        //epidemic.checkAdjacentCabins(s);
        //assertEquals(1, cabin1.getNumAstronauts());
        //assertEquals(1, cabin2.getNumAstronauts());
    }

    public void testGetCardLevel() {
        Epidemic epidemic = new Epidemic(0,1,0);
        assertEquals(1,epidemic.getCardLevel());
    }

    public void testGetLostDays() {
        Epidemic epidemic = new Epidemic(0,1,0);
        assertEquals(0,epidemic.getLostDays());
    }

    public void testSetCardState() {

    }

    public void testPlayCard() {
    }
}