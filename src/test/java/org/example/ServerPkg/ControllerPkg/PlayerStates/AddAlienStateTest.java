package org.example.ServerPkg.ControllerPkg.PlayerStates;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.ComponentsPkg.*;
import org.example.ServerPkg.Model.Exceptions.AlreadyShieldException;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;
import org.example.ServerPkg.Model.ShipBoard;

import java.rmi.RemoteException;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class AddAlienStateTest extends TestCase {

    public void testAddBrownAlien() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();

        // Mettiamo un cannone in una posizione nota
        Cabin cannon = new Cabin(1,false, Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        });
        LifeSupportSystem lf= new LifeSupportSystem(1,AlienColour.BROWN,Direction.EAST,new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        });
        Alien a= new Alien(AlienColour.BROWN);
        sb.placeComponent(5, 6, cannon);
        sb.placeComponent(5, 7, lf);

        // Prepara i punti da attivare
        ArrayList<Points> cannonsToActivate = new ArrayList<>();
        cannonsToActivate.add(new Points(6, 5));

        // Stato
        AddAlienState state = new AddAlienState(game,player);
        state.addBrownAlien(new Points(5,6),player);
        try{
            state.addBrownAlien(new Points(5,7),player);
        }catch(InvalidParameterException e){

        }
    }

    public void testSelectPosition() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        Player player1= new Player("Test1",game );
        Player player2= new Player("Test2",game );
        game.getPlayers().add(player);
        game.getPlayers().add(player1);
        game.getPlayers().add(player2);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        AddAlienState state = new AddAlienState(game,player);
        state.selectPosition(-1,player);
        try{
            state.selectPosition(-1,player1);
        }catch(InvalidParameterException e){

        }
        try{
            state.selectPosition(69,player2);
        }catch(InvalidParameterException e){

        }
    }

    public void testAddPurpleAlien() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();

        // Mettiamo un cannone in una posizione nota
        Cabin cannon = new Cabin(1,false, Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        });
        LifeSupportSystem lf= new LifeSupportSystem(1,AlienColour.PURPLE,Direction.EAST,new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        });
        Alien a= new Alien(AlienColour.PURPLE);
        sb.placeComponent(5, 6, cannon);
        sb.placeComponent(5, 7, lf);

        // Prepara i punti da attivare
        ArrayList<Points> cannonsToActivate = new ArrayList<>();
        cannonsToActivate.add(new Points(6, 5));

        // Stato
        AddAlienState state = new AddAlienState(game,player);
        state.addPurpleAlien(new Points(5,6),player);
        try{
            state.addPurpleAlien(new Points(5,7),player);
        }catch(InvalidParameterException e){

        }
    }

    public void testEndAlienState() throws RemoteException {
        GameController gc = new GameController();
        Game game = new Game(1, 1, 1, gc);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);

        AddAlienState state = new AddAlienState(game,player);
        player.setPlayerState(state);

        // Chiama: posizione non settata, va nel ramo else
        state.endAlienState(player);
    }
    public void testEndAlienStateWithPositionSet() throws RemoteException {
        GameController gc = new GameController();
        Game game = new Game(1, 1, 1, gc){
            @Override
            public void Turn(){}
        };
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        AddAlienState state = new AddAlienState(game,player);
        player.setPlayerState(state);

        // Fai in modo che positionSet sia true chiamando selectPosition
        state.selectPosition(-1, player);

        state.endAlienState(player);
        // Qui puoi controllare che player.getReadyForCards() == true
        assertTrue(player.getReadyForCards());
        // Puoi anche verificare che il player vada in WaitingState se serve
    }

    public void testAbandonGame() throws RemoteException {
        GameController gc = new GameController();
        Game game = new Game(1, 1, 1, gc);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);

        AddAlienState state = new AddAlienState(game,player);
        player.setPlayerState(state);

        state.AbandonGame(player);

        assertTrue(player.isAbandoned());
    }

    public void testDisconnect() throws RemoteException {
        GameController gc = new GameController();
        Game game = new Game(1, 1, 1, gc);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);

        AddAlienState state = new AddAlienState(game,player);
        player.setPlayerState(state);

        state.disconnect(player);
    }
}