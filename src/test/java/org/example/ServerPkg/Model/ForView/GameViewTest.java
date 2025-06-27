package org.example.ServerPkg.Model.ForView;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.ControllerPkg.LobbyState;
import org.example.ServerPkg.Model.CardPkg.AdventureCard;
import org.example.ServerPkg.Model.CardPkg.Planet;
import org.example.ServerPkg.Model.CardPkg.Stardust;
import org.example.ServerPkg.Model.ComponentsPkg.*;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

import java.rmi.RemoteException;
import java.util.Collections;

public class GameViewTest extends TestCase {

    public void testGetCurrentCard() throws RemoteException {
        GameController controller = new GameController();
        controller.setLobbyState(LobbyState.GAME_READY);
        Game game = new Game(2, 1, 0, controller);
        Player player = new Player("Marco", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();

        Goods[] goods = new Goods[]{new Goods(GoodsColour.YELLOW)};
        Planet planet = new Planet(1, goods);

        // Usa la carta vera Stardust
        Stardust stardust = new Stardust(1, 1, 2); // id, crediti, numAstronauti, etc.

        // Imposta la carta nel game
        game.setCard(stardust);

        // Ottieni la view attesa (usando il vero metodo createView di Stardust)
        AdventureCardView expected = stardust.createView();

        // Crea la GameView e confronta il getCurrentCard
        GameView view = new GameView(game, null);
        AdventureCardView actual = view.getCurrentCard();

        // Test: confronta due AdventureCardView
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getType(), actual.getType());
        assertEquals(expected.getNumCredits(), actual.getNumCredits());
        assertEquals(expected.getNumAstronauts(), actual.getNumAstronauts());
    }

    public void testGetPlayers() throws RemoteException {
        GameController controller = new GameController();
        controller.setLobbyState(LobbyState.GAME_READY);
        Game game = new Game(2, 1, 0, controller);
        Player player1 = new Player("Gianmarco", game);
        Player player2 = new Player("Alice", game);
        game.getPlayers().add(player1);
        game.getPlayers().add(player2);
        game.setPlayersShipboard();

        GameView view = new GameView(game, null);
        assertEquals(2, view.getPlayers().size());
        assertEquals("Gianmarco", view.getPlayers().get(0).getName());
        assertEquals("Alice", view.getPlayers().get(1).getName());
    }

    public void testGetException() throws RemoteException {
        GameController controller = new GameController();
        controller.setLobbyState(LobbyState.GAME_READY);
        Game game = new Game(2, 1, 0, controller);
        Player player = new Player("Edoardo", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        Exception ex = new Exception("Test exception");
        GameView view = new GameView(game, ex);
        assertEquals(ex, view.getException());
    }

    public void testGetComponentsDiscovered() throws RemoteException {
        GameController controller = new GameController();
        controller.setLobbyState(LobbyState.GAME_READY);
        Game game = new Game(2, 1, 0, controller);
        Player player = new Player("Edoardo", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();

        Components tubes = new Tubes(10, Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        });
        game.getDiscoveredComponent().add(tubes);

        GameView view = new GameView(game, null);
        assertEquals(1, view.getComponentsDiscovered().size());
        assertEquals("Tubes", view.getComponentsDiscovered().get(0).getType());
    }

    public void testGetLobbyState() throws RemoteException {
        GameController controller = new GameController();
        controller.setLobbyState(LobbyState.GAME_READY);
        Game game = new Game(2, 1, 0, controller);
        Player player = new Player("PlayerX", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        GameView view = new GameView(game, null);
        assertEquals(LobbyState.GAME_READY, view.getLobbyState());
    }

    public void testGetShipBoardLevel() throws RemoteException {
        GameController controller = new GameController();
        controller.setLobbyState(LobbyState.GAME_READY);
        Game game = new Game(2, 1, 0, controller);
        Player player = new Player("PlayerX", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        GameView view = new GameView(game, null);
        assertEquals(1, view.getShipBoardLevel());
    }

    public void testGetGameMode() throws RemoteException {
        GameController controller = new GameController();
        controller.setLobbyState(LobbyState.GAME_READY);
        Game game = new Game(2, 1, 0, controller);
        Player player = new Player("PlayerX", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        GameView view = new GameView(game, null);
        assertEquals(0, view.getGameMode());
    }
}