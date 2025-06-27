package org.example.ServerPkg.Model.ForView;

import junit.framework.TestCase;
import org.example.ServerPkg.Model.ComponentsPkg.Cabin;
import org.example.ServerPkg.Model.ComponentsPkg.Connector;
import org.example.ServerPkg.Model.ComponentsPkg.Direction;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.ShipBoard;

import java.rmi.RemoteException;

public class ShipboardViewTest extends TestCase {

    public void testGetComponentsView() throws RemoteException {
        Game game = new Game(2, 1, 0, null);
        Player player = new Player("Peach", game);
        player.setPlayerShipboard(1);

        // 2. Inserisci un componente in una posizione valida (es: centro 7,8)
        Connector[] connectors = new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL
        };
        Cabin cabin = new Cabin(1, false, Direction.NORTH, connectors);
        player.getPlayerShipBoard().placeComponent(7, 8, cabin);

        // 3. Inserisci un componente "booked"
        Cabin bookedCabin = new Cabin(2, false, Direction.SOUTH, connectors);
        player.getPlayerShipBoard().getBookedComponents()[0] = bookedCabin;

        // 4. Costruisci la ShipboardView
        ShipboardView view = new ShipboardView(player.getPlayerShipBoard());

        // 5. Testa che il componente sia presente nella matrice (componente centrale)
        boolean found = false;
        for (int i = 0; i < view.getComponentsView().length; i++) {
            for (int j = 0; j < view.getComponentsView()[i].length; j++) {
                if (view.getComponentsView()[i][j] != null && view.getComponentsView()[i][j].getId() == 1) {
                    found = true;
                }
            }
        }
        assertTrue(found);

        // 6. Testa che il componente booked sia presente
        assertNotNull(view.getBookedComponents()[0]);
        assertEquals(2, view.getBookedComponents()[0].getId());

        // 7. L'altro booked deve essere null
        assertNull(view.getBookedComponents()[1]);
    }

    public void testGetBookedComponents() throws RemoteException {
        Game game = new Game(2, 1, 0, null);
        Player player = new Player("Peach", game);
        player.setPlayerShipboard(1);

        // 2. Inserisci un componente in una posizione valida (es: centro 7,8)
        Connector[] connectors = new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL
        };
        Cabin cabin = new Cabin(1, false, Direction.NORTH, connectors);
        player.getPlayerShipBoard().placeComponent(7, 8, cabin);

        // 3. Inserisci un componente "booked"
        Cabin bookedCabin = new Cabin(2, false, Direction.SOUTH, connectors);
        player.getPlayerShipBoard().getBookedComponents()[0] = bookedCabin;

        // 4. Costruisci la ShipboardView
        ShipboardView view = new ShipboardView(player.getPlayerShipBoard());

        // 5. Testa che il componente sia presente nella matrice (componente centrale)
        boolean found = false;
        for (int i = 0; i < view.getComponentsView().length; i++) {
            for (int j = 0; j < view.getComponentsView()[i].length; j++) {
                if (view.getComponentsView()[i][j] != null && view.getComponentsView()[i][j].getId() == 1) {
                    found = true;
                }
            }
        }
        assertTrue(found);

        // 6. Testa che il componente booked sia presente
        assertNotNull(view.getBookedComponents()[0]);
        assertEquals(2, view.getBookedComponents()[0].getId());

        // 7. L'altro booked deve essere null
        assertNull(view.getBookedComponents()[1]);
    }
}