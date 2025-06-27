package org.example.ServerPkg.Model.ForView;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.ControllerPkg.LobbyState;
import org.example.ServerPkg.Model.ComponentsPkg.AlienColour;
import org.example.ServerPkg.Model.ComponentsPkg.Cabin;
import org.example.ServerPkg.Model.ComponentsPkg.Connector;
import org.example.ServerPkg.Model.ComponentsPkg.Direction;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.ShipBoard;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;

public class GameViewCacheTest extends TestCase {

    public void testCompareAndUpdate() throws RemoteException {
        GameController controller = new GameController();
        controller.setLobbyState(LobbyState.GAME_READY);
        Game game = new Game(2, 1, 0, controller);
        Player player = new Player("Mario", game);
        game.getPlayers().add(player);
        player.setPlayerShipboard(1);
        Connector[] connectors = new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL};
        Cabin cabin = new Cabin(1, false, Direction.NORTH, connectors);
        player.getPlayerShipBoard().placeComponent(7, 8, cabin); // Puoi usare altre coordinate, ma così è centrale

        // 3. Assegna la ShipBoard al player


        // 4. Costruisci la ShipboardView dal player
        ShipboardView shipboardView = new ShipboardView(player.getPlayerShipBoard());
        PlayerView playerView = new PlayerView(player);

        // 5. Crea la GameView
        GameView gameView = new GameView(game,null);

        // 6. Testa la cache
        GameViewCache cache = new GameViewCache("Mario");
        GameViewCache.GameViewDifferences diff = cache.compareAndUpdate(gameView);

        assertTrue(diff.hasChanges());
        assertEquals(1, diff.getNewShipboardComponents().size());
    }

    public void testGetCachedGameView() throws RemoteException {
        GameController controller = new GameController();
        controller.setLobbyState(LobbyState.GAME_READY);
        Game game = new Game(2, 1, 0, controller);
        Player player = new Player("Mario", game);
        game.getPlayers().add(player);
        player.setPlayerShipboard(1);

        GameViewCache cache = new GameViewCache("Mario");
        assertNull(cache.getCachedGameView()); // deve essere null inizialmente

        GameView gameView = new GameView(game, null);
        cache.compareAndUpdate(gameView);

        // Dopo l'update la cache deve essere esattamente il gameView passato
        assertEquals(gameView, cache.getCachedGameView());
    }

    public void testHasCachedGameView() throws RemoteException {
        GameController controller = new GameController();
        controller.setLobbyState(LobbyState.GAME_READY);
        Game game = new Game(2, 1, 0, controller);
        Player player = new Player("Mario", game);
        game.getPlayers().add(player);
        player.setPlayerShipboard(1);

        GameViewCache cache = new GameViewCache("Mario");
        assertFalse(cache.hasCachedGameView()); // all'inizio è false

        GameView gameView = new GameView(game, null);
        cache.compareAndUpdate(gameView);

        assertTrue(cache.hasCachedGameView());
    }

    public void testResetCache() throws RemoteException {
        GameController controller = new GameController();
        controller.setLobbyState(LobbyState.GAME_READY);
        Game game = new Game(2, 1, 0, controller);
        Player player = new Player("Mario", game);
        game.getPlayers().add(player);
        player.setPlayerShipboard(1);
        Connector[] connectors = new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL};
        Cabin cabin = new Cabin(1, false, Direction.NORTH, connectors);
        player.getPlayerShipBoard().placeComponent(7, 8, cabin);

        GameView gameView = new GameView(game, null);

        GameViewCache cache = new GameViewCache("Mario");
        cache.compareAndUpdate(gameView); // aggiorna la cache

        assertTrue(cache.hasCachedGameView()); // la cache ora esiste

        cache.resetCache();
        assertFalse(cache.hasCachedGameView());
        assertNull(cache.getCachedGameView());
    }

    public void testSetCurrentPlayerName() throws RemoteException {
        GameController controller = new GameController();
        controller.setLobbyState(LobbyState.GAME_READY);
        Game game = new Game(2, 1, 0, controller);
        Player mario = new Player("Mario", game);
        Player luigi = new Player("Luigi", game);
        game.getPlayers().add(mario);
        game.getPlayers().add(luigi);

        mario.setPlayerShipboard(1);
        luigi.setPlayerShipboard(1);

        // Aggiungi un componente a entrambi i giocatori
        Connector[] connectors = new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL};
        Cabin marioCabin = new Cabin(1, false, Direction.NORTH, connectors);
        Cabin luigiCabin = new Cabin(2, false, Direction.NORTH, connectors);
        mario.getPlayerShipBoard().placeComponent(7, 8, marioCabin);
        luigi.getPlayerShipBoard().placeComponent(7, 8, luigiCabin);

        GameView gameView = new GameView(game, null);

        GameViewCache cache = new GameViewCache("Mario");
        cache.compareAndUpdate(gameView); // Primo confronto su Mario (cache di Mario con 1 componente)

        // Ora cambio player osservato su Luigi, che ha la nave con 1 componente
        cache.setCurrentPlayerName("Luigi");
        GameViewCache.GameViewDifferences diff = cache.compareAndUpdate(gameView);

        assertEquals(0, diff.getNewShipboardComponents().size()); // Ora dovrebbe essere true!

    }
    public void testGettersAndHasChanges() throws RemoteException {
        // Costruisci una ComponentsView mock minimale
        Game game=new Game(2,1,1,new GameController());
        Player p= new Player("g",game);
        game.getPlayers().add(p);
        game.setPlayersShipboard();
        ComponentsView comp1 = new ComponentsView(7,7,Direction.NORTH,new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL},1,"ciao",4,4,new GoodsView[]{},Direction.SOUTH, AlienColour.BROWN);
        ComponentsView comp2 = new ComponentsView(7,7,Direction.NORTH,new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL},2,"ciao",4,4,new GoodsView[]{},Direction.SOUTH, AlienColour.BROWN);


        PlayerView player1 = new PlayerView(p);

        // List con valori diversi per testare i getter
        List<ComponentsView> newShipboardComponents = List.of(comp1);
        List<ComponentsView> newDiscoveredComponents = List.of(comp2);
        List<ComponentsView> changedShipboardComponents = List.of(comp1, comp2);
        ComponentsView newCurrentTile = comp1;
        boolean currentTileChanged = true;
        List<PlayerView> playersWithChangedPositions = List.of(player1);

        // Istanzia la classe GameViewDifferences
        GameViewCache.GameViewDifferences diffs = new GameViewCache.GameViewDifferences(
                newShipboardComponents,
                newDiscoveredComponents,
                changedShipboardComponents,
                newCurrentTile,
                currentTileChanged,
                playersWithChangedPositions
        );

        // Getter test
        assertEquals(newShipboardComponents, diffs.getNewShipboardComponents());
        assertEquals(newDiscoveredComponents, diffs.getNewDiscoveredComponents());
        assertEquals(changedShipboardComponents, diffs.getChangedShipboardComponents());
        assertEquals(newCurrentTile, diffs.getNewCurrentTile());
        assertTrue(diffs.isCurrentTileChanged());
        assertEquals(playersWithChangedPositions, diffs.getPlayersWithChangedPositions());

        // hasChanges true (perché tutte le liste sono non vuote)
        assertTrue(diffs.hasChanges());

        // Caso con tutto vuoto (tranne currentTileChanged=false)
        diffs = new GameViewCache.GameViewDifferences(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                null,
                false,
                Collections.emptyList()
        );
        assertFalse(diffs.hasChanges());

        // Caso con solo currentTileChanged true
        diffs = new GameViewCache.GameViewDifferences(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                comp1,
                true,
                Collections.emptyList()
        );
        assertTrue(diffs.hasChanges());
}
    public void testGetCurrentPlayerName() {
        String playerName = "gianmarco";
        GameViewCache cache = new GameViewCache(playerName);
        assertEquals(playerName, cache.getCurrentPlayerName());

        // Cambia il nome e verifica che aggiorna
        cache.setCurrentPlayerName("pippo");
        assertEquals("pippo", cache.getCurrentPlayerName());
    }
}
