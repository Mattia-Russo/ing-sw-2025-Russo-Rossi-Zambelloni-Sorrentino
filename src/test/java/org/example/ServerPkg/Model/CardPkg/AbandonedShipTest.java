package org.example.ServerPkg.Model.CardPkg;

import junit.framework.TestCase;
import org.example.ServerPkg.ConnectionsPkg.Handler;
import org.example.ServerPkg.ConnectionsPkg.Server;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.ControllerPkg.PlayerStates.LandOnAbandonState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.RemoveAstronautsState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.WaitingState;
import org.example.ServerPkg.Model.ComponentsPkg.*;
import org.example.ServerPkg.Model.ForView.AdventureCardView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AbandonedShipTest extends TestCase {

    public void testGetCardLevel() {
        AbandonedShip a=new AbandonedShip(0,1,2,10, 3);
        assertEquals(1,a.getCardLevel());
    }

    public void testGetLostDays() {
        AbandonedShip a=new AbandonedShip(0,1,2,10, 3);
        assertEquals(2,a.getLostDays());
    }

    public void testGetCredits() {
        AbandonedShip a=new AbandonedShip(0,1,2,10, 3);
        assertEquals(10,a.getCredits());
    }

    public void testGetNumAstronauts() {
        AbandonedShip a=new AbandonedShip(0,1,2,10, 3);
        assertEquals(3,a.getNumAstronauts());
    }

    public void testSetCardState() throws RemoteException {
        // Setup giocatori

        Player p1 = new Player("p1", null); // ha 1 astronauta → non idoneo
        Player p2 = new Player("p2", null); // ha 2 astronauti → idoneo
        Player p3 = new Player("p3", null); // ha 3 astronauti → idoneo ma non verrà scelto

        Game game = new Game(3, 2, 1, new GameController());
        game.getPlayers().add(p1);
        game.getPlayers().add(p2);
        game.getPlayers().add(p3);
        game.setPlayersShipboard();

        // p1 → 1 astronauta
        Cabin c1 = new Cabin(0,false,Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY});
        p1.getPlayerShipBoard().placeComponent(6, 6, c1);

        // p2 → 2 astronauti
        Cabin c2 = new Cabin(0,false,Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY});
        p2.getPlayerShipBoard().placeComponent(6, 6, c2);

        // p3 → 3 astronauti
        Cabin c3 = new Cabin(0,false,Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY});
        p3.getPlayerShipBoard().placeComponent(6, 6, c3);

        // p1 è anche abbandonato → va saltato
        p1.abandon(game);

        // Crea carta che richiede 2 astronauti
        AbandonedShip card = new AbandonedShip(0, 1, 1, 2, 2);
        game.setCard(card);

        // Applica setCardState
        card.setCardState(game);

        // Verifica che venga scelto p2
        assertSame(p2.getState().getClass(), LandOnAbandonState.class);

        // Verifica che gli altri NON vengano selezionati
        assertFalse(p1.getState() instanceof LandOnAbandonState);
        assertFalse(p3.getState() instanceof LandOnAbandonState);
    }

    public void testPlayCard() throws RemoteException {
        // Setup iniziale: 1 solo giocatore
        Game game = new Game(3, 1, 1, new GameController());
        Player player = new Player("Player1", null);
        game.getPlayers().add(player);
        game.setPlayersShipboard();

        // Il giocatore parte con 5 astronauti
        player.getPlayerShipBoard().setNumAstronauts(5);
        assertEquals(7, player.getPlayerShipBoard().getTotalAstronauts());

        // Creo una carta AbandonedShip con:
        // id=0, lostDays=1, credits=2, astronautsRequired=3, order=2
        AbandonedShip card = new AbandonedShip(0, 1, 2, 3, 2);
        game.setCard(card);

        // Simulo che la carta venga pescata (setCardState)
        card.setCardState(game);

        // Verifica che il giocatore entri nello stato corretto
        assertTrue(player.getState() instanceof LandOnAbandonState);

        // Simula che il giocatore voglia atterrare → chiama internamente playCard
        player.getState().landOnAbandon(true, player);

        // Ora dovrebbe essere entrato nello stato RemoveAstronautsState
        assertTrue(player.getState() instanceof RemoveAstronautsState);

        // Verifica che i crediti siano stati aggiornati
        assertEquals(3, player.getNumCredits());

        // Verifica che la posizione sia diminuita di 1 (lostDays = 1)
        assertEquals(-2, player.getPosition());

        // Simulazione rimozione astronauti (abbinamento semplificato)
        player.getPlayerShipBoard().setNumAstronauts(-3);
        assertEquals(4, player.getPlayerShipBoard().getTotalAstronauts());

        // Fine turno manualmente (in gioco reale avverrebbe automaticamente)
        player.setPlayerState(new WaitingState(game,player));
        assertTrue(player.getState() instanceof WaitingState);
    }




    public void testCreateView() {
        // Arrange
        int id = 42;
        int cardLevel = 1;
        int lostDays = 2;
        int credits = 100;
        int astronauts = 3;

        AbandonedShip card = new AbandonedShip(id, cardLevel, lostDays, credits, astronauts);

        // Act
        AdventureCardView view = card.createView();


        assertEquals(id, view.getId());
        assertEquals("AbandonedShip", view.getType());
        assertEquals(credits, view.getNumCredits());
        assertEquals(astronauts, view.getNumAstronauts());
        assertEquals(lostDays, view.getLostDays());
        assertEquals(0, view.getNumGoods());
        assertEquals(0, view.getCannonPower());
        assertNotNull(view.getCommands());
        assertTrue(view.getCommands().contains("land_on_abandon"));
        assertTrue(view.getCommands().contains("remove_astronauts"));

        assertTrue(view.getMeteorList().isEmpty());
        assertTrue(view.getPlanetList().isEmpty());
        assertTrue(view.getGoodsList().isEmpty());
        assertTrue(view.getCannonFireList().isEmpty());
        assertEquals(0, view.getCriteria().length);
        assertEquals(0, view.getPenalties().length);
    }
}