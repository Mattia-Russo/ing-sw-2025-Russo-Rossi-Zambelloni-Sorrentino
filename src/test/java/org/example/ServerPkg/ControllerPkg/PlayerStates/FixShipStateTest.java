package org.example.ServerPkg.ControllerPkg.PlayerStates;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.ComponentsPkg.Cabin;
import org.example.ServerPkg.Model.ComponentsPkg.Components;
import org.example.ServerPkg.Model.ComponentsPkg.Connector;
import org.example.ServerPkg.Model.ComponentsPkg.Direction;
import org.example.ServerPkg.Model.Exceptions.AlreadyEmptyPositionException;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;
import org.example.ServerPkg.Model.ShipBoard;

import java.rmi.RemoteException;

public class FixShipStateTest extends TestCase {

    public void testRemoveTile() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 0, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        Cabin cabin = new Cabin(1, false, Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        });
        player.getPlayerShipBoard().placeComponent(7,7,cabin);
        player.setCurrentTile(cabin);
        FixShipState state = new FixShipState(game,player);

        // Prova a piazzare la tile in una posizione
        state.removeTile(new Points(7,7),player);
        try{
            state.removeTile(new Points(8,8),player);
        }catch(AlreadyEmptyPositionException e){}
    }

    public void testEndFixShip() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 0, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        Cabin cabin = new Cabin(1, false, Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        });
        player.getPlayerShipBoard().placeComponent(7,7,cabin);
        player.setCurrentTile(cabin);
        FixShipState state = new FixShipState(game,player);

        // Prova a piazzare la tile in una posizione
        state.endFixShip(player);
    }

    public void testAbandonGame() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 0, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        Cabin cabin = new Cabin(1, false, Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        });
        player.getPlayerShipBoard().placeComponent(7,7,cabin);
        player.setCurrentTile(cabin);
        FixShipState state = new FixShipState(game,player);

        // Prova a piazzare la tile in una posizione
        state.AbandonGame(player);
    }

    public void testDisconnect() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 1, 0, Game){
            @Override
            public void Turn(){

            }
        };
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        player.opShip(game);
        FixShipState state = new FixShipState(game,player);

        // Prova a piazzare la tile in una posizione
        try {
            state.disconnect(player);
        }catch(AlreadyEmptyPositionException e){}
    }
    public void testDisconnect_fullCoverage() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 0, Game){
            @Override
            public void Turn()  {
                // NOP per non uscire dal test
            }
        };
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(2);

        // Aggiungi più componenti con differenti connettori
        Cabin cabin = new Cabin(1, false, Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE
        });
        player.getPlayerShipBoard().placeComponent(7, 7, cabin);

        // Aggiungi un componente con cannon/engine (devi usare la tua classe Cannon/Engine se hai checkRightCannon/Engine veri)
        Components cannon = new Components( Direction.EAST, new Connector[]{
                Connector.EMPTY, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        }) {
            @Override
            public boolean checkRightCannon(ShipBoard board) {
                return true;
            }
            @Override
            public boolean checkRightEngine(ShipBoard board)  {
                return false;
            }
        };
        player.getPlayerShipBoard().placeComponent(7, 8, cannon);

        // Aggiungi un altro componente a sinistra per testare i connettori
        Components left = new Components( Direction.WEST, new Connector[]{
                Connector.UNIVERSAL, Connector.DOUBLE, Connector.EMPTY, Connector.SINGLE
        });
        player.getPlayerShipBoard().placeComponent(7, 6, left);

        // Aggiungi componente sopra per coprire tutti i case
        Components up = new Components( Direction.SOUTH, new Connector[]{
                Connector.DOUBLE, Connector.DOUBLE, Connector.DOUBLE, Connector.DOUBLE
        });
        player.getPlayerShipBoard().placeComponent(6, 7, up);

        // Aggiungi componente sotto
        Components down = new Components( Direction.NORTH, new Connector[]{
                Connector.DOUBLE, Connector.DOUBLE, Connector.DOUBLE, Connector.DOUBLE
        });
        player.getPlayerShipBoard().placeComponent(8, 7, down);

        // Per forzare la divisione nave, mocka getFirstComponent e checkIfSplit
        ShipBoard board = player.getPlayerShipBoard();


        FixShipState state = new FixShipState(game,player);
        // Test completo: copre tutte le condizioni dei cicli, connettori, cannon/engine, wreck
        state.disconnect(player);
    }
}