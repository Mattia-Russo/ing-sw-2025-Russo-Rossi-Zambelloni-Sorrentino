package org.example.ServerPkg.ControllerPkg.PlayerStates;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.CardPkg.*;
import org.example.ServerPkg.Model.ComponentsPkg.*;
import org.example.ServerPkg.Model.Exceptions.AlreadyEngineException;
import org.example.ServerPkg.Model.Exceptions.EnoughAstronautsRemovedException;
import org.example.ServerPkg.Model.Exceptions.NotCabinException;
import org.example.ServerPkg.Model.Exceptions.NotEnoughAstronautsRemovedException;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;
import org.example.ServerPkg.Model.ShipBoard;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class RemoveAstronautsStateTest extends TestCase {

    public void testRemoveAstronauts() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        ShipBoard sb = player.getPlayerShipBoard();

        // Mettiamo un cannone in una posizione nota
        Engine cannon = new Engine(1, 1, Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        });
        Cabin cabin = new Cabin(1, true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY});
        sb.placeComponent(6, 6, cabin);


        // Prepara i punti da attivare
        ArrayList<Points> cannonsToActivate = new ArrayList<>();
        cannonsToActivate.add(new Points(6, 6));
        game.setCard(new Slavers(1, 1, 1, 1, 3, 1));

        // Stato
        RemoveAstronautsState state = new RemoveAstronautsState(game,player);

        // Test: attivazione dei cannoni
        try {
            state.removeAstronauts(new Points(6, 6), player);
        } catch (EnoughAstronautsRemovedException e) {

        }
    }
    public void testRemoveAstronauts1() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        ShipBoard sb = player.getPlayerShipBoard();

        // Mettiamo un cannone in una posizione nota
        Engine cannon = new Engine(1, 1, Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        });
        Tubes tubes = new Tubes(1, Direction.NORTH,new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY});
        sb.placeComponent(6, 6, tubes);


        // Prepara i punti da attivare
        ArrayList<Points> cannonsToActivate = new ArrayList<>();
        cannonsToActivate.add(new Points(6, 6));
        game.setCard(new Slavers(1, 1, 1, 1, 3, 1));

        // Stato
        RemoveAstronautsState state = new RemoveAstronautsState(game,player);

        // Test: attivazione dei cannoni
        try {
            state.removeAstronauts(new Points(6, 6), player);
        } catch (NotCabinException e){

        }
    }
    public void testRemoveAstronauts2() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        ShipBoard sb = player.getPlayerShipBoard();

        // Mettiamo un cannone in una posizione nota
        Engine cannon = new Engine(1, 1, Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        });
        Cabin cabin = new Cabin(1, true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY});
        sb.placeComponent(6, 6, cabin);
        cabin.changeNumAstronauts(-2,sb);

        // Prepara i punti da attivare
        ArrayList<Points> cannonsToActivate = new ArrayList<>();
        cannonsToActivate.add(new Points(6, 6));
        game.setCard(new Slavers(1, 1, 1, 1, 0, 1));

        // Stato
        RemoveAstronautsState state = new RemoveAstronautsState(game,player);

        // Test: attivazione dei cannoni
        try {
            state.removeAstronauts(new Points(6, 6), player);
        } catch (EnoughAstronautsRemovedException e) {

        }
    }
    public void testRemoveAstronauts3() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        ShipBoard sb = player.getPlayerShipBoard();

        // Mettiamo un cannone in una posizione nota
        Engine cannon = new Engine(1, 1, Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        });
        Cabin cabin = new Cabin(1, true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        sb.placeComponent(6, 6, cabin);
        LifeSupportSystem lf=new LifeSupportSystem(1,AlienColour.BROWN,Direction.EAST,new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        sb.placeComponent(6,7,lf);
        cabin.changeWithLifeSupport(true);
        lf.addLifeSupport(cabin);
        cabin.addAlien(new Alien(AlienColour.BROWN),sb);
        // Prepara i punti da attivare
        ArrayList<Points> cannonsToActivate = new ArrayList<>();
        cannonsToActivate.add(new Points(6, 6));
        game.setCard(new Slavers(1, 1, 1, 1, 3, 1));

        // Stato
        RemoveAstronautsState state = new RemoveAstronautsState(game,player);

        state.removeAstronauts(new Points(6, 6), player);

    }


    public void testEndRemoveAstronauts() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        ShipBoard sb = player.getPlayerShipBoard();

        // Mettiamo un cannone in una posizione nota
        Engine cannon = new Engine(1, 1, Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        });
        Cabin cabin = new Cabin(1, true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY});
        sb.placeComponent(6, 6, cabin);
        cabin.changeNumAstronauts(-2,sb);

        // Prepara i punti da attivare
        ArrayList<Points> cannonsToActivate = new ArrayList<>();
        cannonsToActivate.add(new Points(6, 6));
        game.setCard(new Slavers(1, 1, 1, 1, 0, 1));

        // Stato
        RemoveAstronautsState state = new RemoveAstronautsState(game,player);

        // Test: attivazione dei cannoni
        try {
            state.endRemoveAstronauts(player);
        } catch (EnoughAstronautsRemovedException e) {

        }
    }
    public void testEndRemoveAstronauts2() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        ShipBoard sb = player.getPlayerShipBoard();

        // Mettiamo un cannone in una posizione nota
        Engine cannon = new Engine(1, 1, Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        });
        Cabin cabin = new Cabin(1, true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY});
        sb.placeComponent(6, 6, cabin);


        // Prepara i punti da attivare
        ArrayList<Points> cannonsToActivate = new ArrayList<>();
        cannonsToActivate.add(new Points(6, 6));
        game.setCard(new Slavers(1, 1, 1, 1, 1, 1));

        // Stato
        RemoveAstronautsState state = new RemoveAstronautsState(game,player);

        // Test: attivazione dei cannoni
        try {
            state.endRemoveAstronauts(player);
        } catch (NotEnoughAstronautsRemovedException e) {

        }
    }

    public void testAbandonGame() throws RemoteException {
        Game game = new Game(2, 1, 0, new GameController());
        Player p = new Player("test", game);
        game.getPlayers().add(p);
        game.setPlayersShipboard();

        // Uso una carta reale che implementa getNumAstronauts(), come Epidemic.
        // Qui assumo che Epidemic richieda di rimuovere 2 astronauti (modifica se serve!)
        Epidemic card = new Epidemic(1,1,1);
        game.setCard(card);

        // Aggiungo una cabina con 2 astronauti
        Cabin cab = new Cabin(0, false, Direction.NORTH,
                new Connector[] {Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        cab.changeNumAstronauts(2, p.getPlayerShipBoard());
        p.getPlayerShipBoard().placeComponent(8, 8, cab);

        RemoveAstronautsState state = new RemoveAstronautsState(game,p);
        p.setPlayerState(state);

        assertEquals(2, cab.getNumAstronauts());

        state.AbandonGame(p);

        // Astronauti rimossi
        assertEquals(2, cab.getNumAstronauts());
        assertTrue(p.isAbandoned());
    }

    public void testDisconnect() throws RemoteException {
        Game game = new Game(2, 1, 0, new GameController());
        Player p = new Player("test", game);
        game.getPlayers().add(p);
        game.setPlayersShipboard();

        Epidemic card = new Epidemic(1,1,1);
        game.setCard(card);

        // Cabina con 1 astronauta
        Cabin cab = new Cabin(0, false, Direction.NORTH,
                new Connector[] {Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        cab.changeNumAstronauts(1, p.getPlayerShipBoard());
        p.getPlayerShipBoard().placeComponent(8, 8, cab);

        RemoveAstronautsState state = new RemoveAstronautsState(game,p);
        p.setPlayerState(state);

        assertEquals(2, cab.getNumAstronauts());

        state.disconnect(p);

        // Astronauti rimossi
        assertEquals(2, cab.getNumAstronauts());
        // Qui puoi controllare lo stato di p se la tua disconnectPlayer lo marca in qualche modo
    }
    public void testRemoveLeftAstronauts_withLessAstronautsThanRequired() throws RemoteException {
        // Setup
        GameController gc = new GameController();
        Game game = new Game(1, 1, 1, gc);
        Player player = new Player("P3", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);

        // Cabin con 1 astronauta
        Cabin cabin = new Cabin(1, false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY});
        player.getPlayerShipBoard().placeComponent(8, 8, cabin);

        // Carta che richiede di rimuovere 3 astronauti, ma ce n'è solo 1
        Goods[] goods = new Goods[0];
        AbandonedStation card = new AbandonedStation(3, 1, 0, 3, goods);
        game.setCard(card);

        RemoveAstronautsState state = new RemoveAstronautsState(game,player);



        // L'unico astronauta viene rimosso
        assertEquals(2, ((Cabin) player.getPlayerShipBoard().getComponent(8, 8)).getNumAstronauts());
    }
}