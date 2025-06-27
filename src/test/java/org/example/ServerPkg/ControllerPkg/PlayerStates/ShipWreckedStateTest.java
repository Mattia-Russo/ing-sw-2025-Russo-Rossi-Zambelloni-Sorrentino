package org.example.ServerPkg.ControllerPkg.PlayerStates;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.CardPkg.OpenSpace;
import org.example.ServerPkg.Model.ComponentsPkg.Cabin;
import org.example.ServerPkg.Model.ComponentsPkg.Cannon;
import org.example.ServerPkg.Model.ComponentsPkg.Connector;
import org.example.ServerPkg.Model.ComponentsPkg.Direction;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.rmi.RemoteException;
import java.util.List;

public class ShipWreckedStateTest extends TestCase {

    public void testChooseWrecked() throws RemoteException {
        GameController controller = new GameController();
        Game game = new Game(2, 1, 0, controller);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();

        // Inserisco un componente a WEST nella riga 5
        Cabin cabin = new Cabin(0, true, Direction.NORTH.WEST, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL
        });
        player.getPlayerShipBoard().placeComponent(8, 8, cabin);
        Player player1 = game.getPlayers().getFirst();
        ShipWreckedState state = new ShipWreckedState(game, player1);

        // Scegliamo il pezzo da riparare
        state.chooseWrecked(new Points(8, 8), player1);
        assertTrue(player1.getShipOK());

        // Ora testiamo endWreckedState con ship ok, senza carta corrente
        state.endWreckedState(player1);
        // Dopo il metodo, il player dovrebbe essere pronto per le carte
        assertTrue(player1.getReadyForCards());
    }

    public void testEndWreckedState() throws RemoteException {
        GameController ctrl = new GameController();
        Game game = new Game(2, 1, 1, ctrl){
            @Override
            public void Turn(){

            }
        };
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();

        // Prepara una carta
        OpenSpace card = new OpenSpace(0, 0, 0);
        card.setShipWrecked(true);
        game.setCard(card);

        player.setShipOK(true);
        ShipWreckedState state = new ShipWreckedState(game, player);

        // Deve settare la carta come non più "wrecked"
        state.endWreckedState(player);

    }
    public void testEndWreckedState1() throws RemoteException {
        GameController ctrl = new GameController();
        Game game = new Game(2, 1, 1, ctrl){
            @Override
            public void Turn(){

            }
        };
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();

        player.setShipOK(true);
        ShipWreckedState state = new ShipWreckedState(game, player);

        // Caso: nessuna carta corrente, gameMode==1
        game.setCard(null);
        state.endWreckedState(player);

        assertTrue(player.getState() instanceof AddAlienState);

    }
    public void testEndWreckedState2() throws RemoteException {
        GameController ctrl = new GameController();
        Game game = new Game(2, 1, 0, ctrl){
            @Override
            public void Turn(){

            }
        };
        Player p1 = new Player("Test1", game);
        Player p2 = new Player("Test2", game);
        game.getPlayers().addAll(List.of(p1, p2));
        game.setPlayersShipboard();

        p1.setShipOK(true);
        p1.setReadyForCards(false);
        ShipWreckedState state = new ShipWreckedState(game, p1);

        // Nessuna carta corrente, gameMode==0
        game.setCard(null);

        // Dovrebbe mettere tutti gli altri giocatori in WaitingState e chiamare Turn()
        state.endWreckedState(p1);

        // Se non è abbandonato, lo stato deve essere WaitingState
        assertTrue(p1.getState() instanceof WaitingState);
    }
    public void testEndWreckedState3() throws RemoteException {
        GameController ctrl = new GameController();
        Game game = new Game(2, 1, 0, ctrl);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();

        player.setShipOK(false);
        ShipWreckedState state = new ShipWreckedState(game, player);

        state.endWreckedState(player);

    }
    public void testAbandonGame() throws RemoteException {
        GameController ctrl = new GameController();
        Game game = new Game(2, 1, 0, ctrl){
            @Override
            public void Turn(){

            }
        };
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();

        // Aggiungi un componente nella posizione da "distruggere"
        Cannon cannon = new Cannon(0, 1, Direction.WEST, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        player.getPlayerShipBoard().placeComponent(8, 8, cannon);

        ShipWreckedState state = new ShipWreckedState(game, player);

        state.AbandonGame(player);

        assertTrue(player.isAbandoned());
        assertTrue(player.getShipOK());
    }
    public void testAbandonGame1() throws RemoteException {
        GameController ctrl = new GameController();
        Game game = new Game(2, 1, 1, ctrl){
            @Override
            public void Turn(){

            }
        };
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();

        // Aggiungi un componente nella posizione da "distruggere"
        Cannon cannon = new Cannon(0, 1, Direction.WEST, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        player.getPlayerShipBoard().placeComponent(8, 8, cannon);

        ShipWreckedState state = new ShipWreckedState(game, player);

        state.AbandonGame(player);

        assertTrue(player.isAbandoned());
        assertTrue(player.getShipOK());
    }
    public void testAbandonGame2() throws RemoteException {
        GameController ctrl = new GameController();
        Game game = new Game(2, 1, 1, ctrl){
            @Override
            public void Turn(){

            }
        };
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();

        // Aggiungi un componente nella posizione da "distruggere"
        Cannon cannon = new Cannon(0, 1, Direction.WEST, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        player.getPlayerShipBoard().placeComponent(8, 8, cannon);
        player.setReadyForCards(true);
        ShipWreckedState state = new ShipWreckedState(game, player);

        state.AbandonGame(player);

        assertTrue(player.isAbandoned());
        assertTrue(player.getShipOK());
    }

    public void testDisconnect() throws RemoteException {
        GameController ctrl = new GameController();
        Game game = new Game(2, 1, 0, ctrl){
            @Override
            public void Turn(){

            }
        };
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();

        // Aggiungi un componente nella posizione da "distruggere"
        Cannon cannon = new Cannon(0, 1, Direction.WEST, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        player.getPlayerShipBoard().placeComponent(8, 8, cannon);

        ShipWreckedState state = new ShipWreckedState(game, player);

        state.disconnect(player);

        // Qui puoi controllare che il player sia stato disconnesso, ship sistemata ecc.
        assertTrue(player.getShipOK());
    }
    public void testDisconnect2() throws RemoteException {
        GameController ctrl = new GameController();
        Game game = new Game(2, 1, 1, ctrl); // gameMode==1
        Player p1 = new Player("Test1", game);
        Player p2 = new Player("Test2", game);
        game.getPlayers().add(p1);
        game.getPlayers().add(p2);
        game.setPlayersShipboard();

        // Nessuna carta corrente
        game.setCard(null);

        // p2 già pronto
        p2.setReadyForCards(true);

        ShipWreckedState state = new ShipWreckedState(game, p1);
        state.AbandonGame(p1);

        // Se non crasha, ha fatto return e NON chiamato Turn.
        assertTrue(p2.getReadyForCards());
    }
    public void testAbandonGame3() throws RemoteException {
        GameController ctrl = new GameController();
        Game game = new Game(2, 1, 1, ctrl){
            @Override
            public void Turn(){

            }
        };
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();

        // Setta una carta corrente
        OpenSpace card = new OpenSpace(0, 0, 0);
        card.setShipWrecked(true);
        game.setCard(card);

        ShipWreckedState state = new ShipWreckedState(game, player);
        // triggera autoFix tramite AbandonGame
        state.AbandonGame(player);

    }
}