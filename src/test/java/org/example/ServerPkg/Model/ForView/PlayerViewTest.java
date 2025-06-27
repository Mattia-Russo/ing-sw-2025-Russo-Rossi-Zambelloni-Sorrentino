package org.example.ServerPkg.Model.ForView;

import junit.framework.TestCase;
import org.example.ServerPkg.Model.CardPkg.AdventureCard;
import org.example.ServerPkg.Model.ComponentsPkg.Cabin;
import org.example.ServerPkg.Model.ComponentsPkg.Connector;
import org.example.ServerPkg.Model.ComponentsPkg.Direction;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class PlayerViewTest extends TestCase {

    public void testIsPosValid() {
    }

    public void testGetShipboardView() throws RemoteException {
        Game game = new Game(2, 1, 0, null);
        Player player = new Player("Peach", game);
        player.setPlayerShipboard(1);
        // Inseriamo un componente nella Shipboard
        Connector[] connectors = new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL};
        Cabin cabin = new Cabin(1, false, Direction.NORTH, connectors);
        player.getPlayerShipBoard().placeComponent(7, 8, cabin);

        // Set currentTile
        player.setCurrentTile(cabin);

        PlayerView playerView = new PlayerView(player);

        assertNotNull(playerView.getShipboardView());
        assertNotNull(playerView.getCurrentTile());
    }

    public void testGetRocketColour() throws RemoteException {
        Game game = new Game(2, 1, 0, null);
        Player player = new Player("Mario", game);
        player.setPlayerShipboard(1);
        player.setRocketColour("Red");
        PlayerView playerView = new PlayerView(player);
        assertEquals("Red", playerView.getRocketColour());
    }

    public void testGetCurrentTile() throws RemoteException {
        Game game = new Game(2, 1, 0, null);
        Player player = new Player("Peach", game);
        player.setPlayerShipboard(1);
        // Inseriamo un componente nella Shipboard
        Connector[] connectors = new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL};
        Cabin cabin = new Cabin(1, false, Direction.NORTH, connectors);
        player.getPlayerShipBoard().placeComponent(7, 8, cabin);

        // Set currentTile
        player.setCurrentTile(cabin);

        PlayerView playerView = new PlayerView(player);

        assertNotNull(playerView.getShipboardView());
        assertNotNull(playerView.getCurrentTile());
    }

    public void testGetDeckShowed() throws RemoteException {
        Game game = new Game(2, 1, 0, null);
        Player player = new Player("Daisy", game);
        player.setPlayerShipboard(1);
        // Simuliamo una carta avventura mostrata (deve implementare createView)
        AdventureCard adventureCard = new AdventureCard(1,2) {
            @Override
            public AdventureCardView createView() {
                return new AdventureCardView("cmd", 1, "type", 0, 2, 1, 1,
                        null, null, null, null, 1, null, null);
            }
        };
        ArrayList<AdventureCard> deck = new ArrayList<>();
        deck.add(adventureCard);
        player.setDeckShowed(deck);

        PlayerView playerView = new PlayerView(player);

        assertEquals(1, playerView.getDeckShowed().size());
        assertEquals(1, playerView.getDeckShowed().get(0).getId());
    }

    public void testGetPosition() throws RemoteException {
        Game game = new Game(2, 1, 0, null);
        Player player = new Player("Luigi", game);
        player.setPlayerShipboard(1);
        player.setPosition(3);
        PlayerView playerView = new PlayerView(player);

        assertEquals(3, playerView.getPosition());
    }

    public void testGetNumCredits() throws RemoteException {
        Game game = new Game(2, 1, 0, null);
        Player player = new Player("Luigi", game);
        player.setPlayerShipboard(1);
        player.changeCredits(77);
        player.setPosition(3);
        PlayerView playerView = new PlayerView(player);

        assertEquals(77, playerView.getNumCredits());
    }

    public void testTestGetName() throws RemoteException {
        Game game = new Game(2, 1, 0, null);
        Player player = new Player("Mario", game);
        player.setPlayerShipboard(1);
        PlayerView playerView = new PlayerView(player);

        assertEquals("Mario", playerView.getName());
    }

    public void testIsAbandoned() throws RemoteException {
        Game game = new Game(2, 1, 0, null);
        Player player = new Player("Wario", game);
        player.setPlayerShipboard(1);

        // Abbandonato
        player.abandon(game);
        // Nave OK
        player.setShipOK(true);
        // Posizione valida
        player.setPosition(1);

        PlayerView playerView = new PlayerView(player);

        assertTrue(playerView.isAbandoned());
        assertTrue(playerView.isShipOK());
        assertTrue(playerView.isPosValid());
    }

    public void testIsShipOK() throws RemoteException {
        Game game = new Game(2, 1, 0, null);
        Player player = new Player("Wario", game);
        player.setPlayerShipboard(1);

        // Abbandonato
        player.abandon(game);
        // Nave OK
        player.setShipOK(true);
        // Posizione valida
        player.setPosition(0);

        PlayerView playerView = new PlayerView(player);

        assertTrue(playerView.isAbandoned());
        assertTrue(playerView.isShipOK());
        assertTrue(playerView.isPosValid());
    }
}