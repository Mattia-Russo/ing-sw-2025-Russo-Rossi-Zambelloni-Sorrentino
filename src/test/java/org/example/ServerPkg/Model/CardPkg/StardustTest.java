package org.example.ServerPkg.Model.CardPkg;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.ComponentsPkg.Components;
import org.example.ServerPkg.Model.ComponentsPkg.Connector;
import org.example.ServerPkg.Model.ComponentsPkg.Direction;
import org.example.ServerPkg.Model.ComponentsPkg.Tubes;
import org.example.ServerPkg.Model.ForView.AdventureCardView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.List;

public class StardustTest extends TestCase {

    public void testSetCardState() throws RemoteException {
        // Crea il gioco
        Game game = new Game(3, 2, 1, new GameController());
        game.setCard(new Stardust(0, 1, 0));
        // Aggiungi 3 giocatori
        Player p1 = new Player("a", null);
        Player p2 = new Player("b", null);
        Player p3 = new Player("c", null);

        game.getPlayers().add(p1);
        game.getPlayers().add(p2);
        game.getPlayers().add(p3);
        game.setPlayersShipboard();

        Tubes t1 = new Tubes(0, Direction.NORTH,
                new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
//                Tubes t2 = new Tubes(0, Direction.NORTH,
//                new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY});
        p1.getPlayerShipBoard().placeComponent(8, 7, t1);
//        p2.getPlayerShipBoard().placeComponent(8, 7, t2);
        Components[][] matrix = p1.getPlayerShipBoard().getComponentMatrix();
        System.out.println("Componenti presenti nella ShipBoard di " + p1.getName() + ":");

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != null) {
                    System.out.println("→ Componente a (" + i + "," + j + "): " + matrix[i][j].getClass().getSimpleName());
                }
            }
        }
        p1.changePosition(10);
        p2.changePosition(11);
        p3.changePosition(12);


        AdventureCard drawnCard = game.getCurrentCard();
        assertTrue(drawnCard instanceof Stardust);
        drawnCard.setCardState(game);
        assertEquals(2, p3.getPosition());


    }

    public void testPlayCard() {

    }

    public void testCreateView() {
        Stardust card = new Stardust(42, 1, 3);

        AdventureCardView view = card.createView();

        String expectedCommand = """
                You are playing the stardust card, this card is automatic
                """;

        assertEquals("Stardust", view.getType());
        assertEquals(42, view.getId());
        assertEquals(3, view.getLostDays());
        assertEquals(expectedCommand, view.getCommands());
    }
}