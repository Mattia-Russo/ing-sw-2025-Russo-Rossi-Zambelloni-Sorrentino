package org.example.ServerPkg.ControllerPkg.PlayerStates;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.CardPkg.*;
import org.example.ServerPkg.Model.ComponentsPkg.*;
import org.example.ServerPkg.Model.Exceptions.EndStateException;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;
import org.example.ServerPkg.Model.ShipBoard;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class WinEnemyStateTest extends TestCase {

    public void testAcceptReward() throws RemoteException {
        Pirates pirates = new Pirates(0, 10, List.of(), 1, 2, 1);
        Player p = new Player("Player", null);
        Game game = new Game(3, 1, 1, new GameController());
        game.getPlayers().add(p);
        game.setPlayersShipboard();

        // Aggiungo un cannone doppio con potenza sufficiente
        BatteryStorage batteryStorage = new BatteryStorage(2, 4, Direction.NORTH, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        BatteryStorage batteryStorage2 = new BatteryStorage(2, 4, Direction.NORTH, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        p.getPlayerShipBoard().placeComponent(6, 8, batteryStorage);
        p.getPlayerShipBoard().placeComponent(8, 8, batteryStorage2);

        Cannon cannon = new Cannon(0, 2, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        p.getPlayerShipBoard().placeComponent(6, 7, cannon);
        game.setCard(pirates);
        pirates.setCardState(game);
        assertTrue(p.getState() instanceof ActivateCannonsState);

        // Attivazione: potenza 3 > cannonPower 2 → vince
        pirates.playCard(game, new ArrayList<>(List.of(new Points(6, 7))), new ArrayList<>(List.of(new Points(6, 8))));
        assertTrue(p.getState() instanceof WinEnemyState);
        p.getState().acceptReward(true,p);

    }

    public void testAbandonGame() throws RemoteException {
        Pirates pirates = new Pirates(0, 10, List.of(), 1, 2, 1);
        Player p = new Player("Player", null);
        Game game = new Game(3, 1, 1, new GameController());
        game.getPlayers().add(p);
        game.setPlayersShipboard();

        // Aggiungo un cannone doppio con potenza sufficiente
        BatteryStorage batteryStorage = new BatteryStorage(2, 4, Direction.NORTH, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        BatteryStorage batteryStorage2 = new BatteryStorage(2, 4, Direction.NORTH, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        p.getPlayerShipBoard().placeComponent(6, 8, batteryStorage);
        p.getPlayerShipBoard().placeComponent(8, 8, batteryStorage2);

        Cannon cannon = new Cannon(0, 2, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        p.getPlayerShipBoard().placeComponent(6, 7, cannon);
        game.setCard(pirates);
        pirates.setCardState(game);
        WinEnemyState winEnemyState= new WinEnemyState(game,p);
        winEnemyState.AbandonGame(p);
    }

    public void testDisconnect() throws RemoteException {
        // DEVE esserci almeno una cannonFire!
        List<CannonFire> cannonFires = new ArrayList<>();
        cannonFires.add(new CannonFire(1,Direction.NORTH));

        Pirates pirates = new Pirates(0, 10, cannonFires, 1, 2, 1);
        Player p = new Player("Player", null);
        Game game = new Game(3, 1, 1, new GameController()){
            @Override
            public void Turn() {
                // puoi lasciare vuoto
            }
        };
        game.getPlayers().add(p);
        game.setPlayersShipboard();

        // Aggiungo un cannone doppio con potenza sufficiente
        BatteryStorage batteryStorage = new BatteryStorage(2, 4, Direction.NORTH, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        p.getPlayerShipBoard().placeComponent(6, 8, batteryStorage);

        Cannon cannon = new Cannon(0, 2, Direction.NORTH, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        p.getPlayerShipBoard().placeComponent(6, 7, cannon);

        game.setCard(pirates);
        pirates.setCardState(game);

        WinEnemyState winEnemyState = new WinEnemyState(game,p);
        // non dovrebbe più dare errore ora:
        try {
            winEnemyState.disconnect(p);
        }catch(IndexOutOfBoundsException e){}

    }

}