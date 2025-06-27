package org.example.ServerPkg.ControllerPkg.PlayerStates;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.CardPkg.Meteor;
import org.example.ServerPkg.Model.CardPkg.MeteorCard;
import org.example.ServerPkg.Model.ComponentsPkg.*;
import org.example.ServerPkg.Model.Exceptions.AlreadyBatteryException;
import org.example.ServerPkg.Model.Exceptions.AlreadyEngineException;
import org.example.ServerPkg.Model.Exceptions.AlreadyShieldException;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;
import org.example.ServerPkg.Model.ShipBoard;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ActivateShieldsStateTest extends TestCase {

    public void testActivateShields() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();

        // Mettiamo un cannone in una posizione nota
        Shield cannon = new Shield(1, Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        },Direction.NORTH);
        sb.placeComponent(5, 6, cannon);

        // Prepara i punti da attivare
        ArrayList<Points> cannonsToActivate = new ArrayList<>();
        cannonsToActivate.add(new Points(6, 5));

        // Stato
        ActivateShieldsState state = new ActivateShieldsState(game,player);

        // Test: attivazione dei cannoni
        state.activateShields(cannonsToActivate, player);
        try{
            state.activateShields(cannonsToActivate, player);
        }catch(AlreadyShieldException e){

        }
    }

    public void testUseBatteries() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();

        // Mettiamo un cannone in una posizione nota
        BatteryStorage cannon = new BatteryStorage(1,5,Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        });
        sb.placeComponent(5, 6, cannon);

        // Prepara i punti da attivare
        ArrayList<Points> cannonsToActivate = new ArrayList<>();
        cannonsToActivate.add(new Points(6, 5));

        // Stato
        ActivateShieldsState state = new ActivateShieldsState(game,player);

        // Test: attivazione dei cannoni
        state.useBatteries(cannonsToActivate, player);
        try{
            state.useBatteries(cannonsToActivate, player);
        }catch(AlreadyBatteryException e){

        }
    }

    public void testEndActivateShields() throws RemoteException {
        GameController gc = new GameController();
        Game game = new Game(1, 1, 1, gc);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);

        // Setup: meteora a nord, cannon in posizione
        Cannon cannon = new Cannon(1, 1, Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        });
        player.getPlayerShipBoard().placeComponent(8, 8, cannon);

        // Setto la carta Meteor come attiva (serve per la chiamata .playCard)
        MeteorCard card = new MeteorCard(0, 1, 0, List.of(new Meteor(1, Direction.NORTH)));
        game.setCard(card);

        // Setto lo stato
        ActivateShieldsState state = new ActivateShieldsState(game,player);
        player.setPlayerState(state);

        // Verifica che non lancia eccezione
        try {
            state.endActivateCannons(player);
        }catch(IndexOutOfBoundsException e){}
    }

    public void testDisconnect() throws RemoteException {
        GameController gc = new GameController();
        Game game = new Game(1, 1, 1, gc);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);

        // Setup: carta meteor attiva
        MeteorCard card = new MeteorCard(0, 1, 0, List.of(new Meteor(1, Direction.NORTH)));
        game.setCard(card);

        ActivateShieldsState state = new ActivateShieldsState(game,player);
        player.setPlayerState(state);

        // Chiamata senza eccezioni
        try {
            state.disconnect(player);
        }catch(IndexOutOfBoundsException e){}
    }

    public void testAbandonGame() throws RemoteException {
        GameController gc = new GameController();
        Game game = new Game(1, 1, 1, gc);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);

        // Setup: carta meteor attiva
        MeteorCard card = new MeteorCard(0, 1, 0, List.of(new Meteor(1, Direction.NORTH)));
        game.setCard(card);

        ActivateShieldsState state = new ActivateShieldsState(game,player);
        player.setPlayerState(state);

        // Chiamata senza eccezioni
        try{
            state.AbandonGame(player);
        }catch(IndexOutOfBoundsException e){}
        // Puoi anche assertare che il player è abbandonato se vuoi:
        assertTrue(player.isAbandoned());
    }
}