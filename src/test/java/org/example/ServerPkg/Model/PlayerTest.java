package org.example.ServerPkg.Model;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.ControllerPkg.PlayerStates.ActivateCannonsState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.AddAlienState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.WaitingState;
import org.example.ServerPkg.Model.CardPkg.AdventureCard;
import org.example.ServerPkg.Model.ComponentsPkg.*;
import org.example.ServerPkg.Model.Exceptions.PlayerAbandonedException;
import org.example.ServerPkg.Model.Exceptions.TilesEndedExceptions;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class PlayerTest extends TestCase {

    public void testGetPosition() throws RemoteException {
        Player p = new Player("a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();
        Cabin c1 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        Storage s1 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        Storage s2 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c2 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c3 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c4 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c5 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c6 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c7 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c8 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c9 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        s.placeComponent(6,7, s2);
        //s.placeComponent(2,4, s1);
        //s.placeComponent(1,4, cannon);
        s.placeComponent(7,7, c1);
        s.placeComponent(5,8, c5);
        s.placeComponent(6,8, c2);
        s.placeComponent(5,9, c4);
        s.placeComponent(6,9, c3);
        s.placeComponent(8,7, c6);
        s.placeComponent(9,8, c7);
        s.placeComponent(8,9, c8);
        s.placeComponent(9,9, c9);

        assertEquals(0, p.getPosition());
    }

    public void testIsAbandoned() throws RemoteException {
        Player p = new Player("a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();

        assertFalse(p.isAbandoned());
    }

    public void testIsOnPlanet() throws RemoteException {
        Player p = new Player("a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();

        assertFalse(p.isOnPlanet());
    }

    public void testGetPlayerShipBoard() throws RemoteException {
        Player p = new Player("a", null);
        p.setPlayerShipboard(1);
        ShipBoard sh1=p.getPlayerShipBoard();
        Cabin c1 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        Storage s1 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        Storage s2 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c2 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c3 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c4 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c5 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c6 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c7 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c8 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c9 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        sh1.placeComponent(7,7, c1);
        sh1.placeComponent(6,7, s2);
        sh1.placeComponent(8,7, s1);
        sh1.placeComponent(8,6, cannon);
        sh1.placeComponent(5,8, c5);
        sh1.placeComponent(6,8, c2);
        sh1.placeComponent(5,9, c4);
        sh1.placeComponent(6,9, c3);
        sh1.placeComponent(8,8, c6);
        sh1.placeComponent(9,8, c7);
        sh1.placeComponent(8,9, c8);
        sh1.placeComponent(9,9, c9);

        assertEquals(sh1, p.getPlayerShipBoard());

    }

    public void testChangeOnPlanet() throws RemoteException {
        Player p = new Player("a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();

        assertFalse(p.isOnPlanet());
        p.changeOnPlanet();
        assertTrue(p.isOnPlanet());
    }

    public void testAbandon() throws RemoteException {
        Player p = new Player( "a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();

        assertFalse(p.isAbandoned());
        p.abandon(null);
        assertTrue(p.isAbandoned());
    }

    public void testChangePosition() throws RemoteException {
        Player p = new Player( "a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();
        Cabin c1 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        Storage s1 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        Storage s2 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c2 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c3 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c4 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c5 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c6 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c7 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c8 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c9 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        s.placeComponent(7,7, c1);
        s.placeComponent(6,7, s2);
        s.placeComponent(8,7, s1);
        s.placeComponent(8,6, cannon);
        s.placeComponent(5,8, c5);
        s.placeComponent(6,8, c2);
        s.placeComponent(5,9, c4);
        s.placeComponent(6,9, c3);
        s.placeComponent(8,8, c6);
        s.placeComponent(9,8, c7);
        s.placeComponent(8,9, c8);
        s.placeComponent(9,9, c9);

        assertEquals(0, p.getPosition());

        p.changePosition(10);
        assertEquals(10, p.getPosition());

        p.changePosition(-3);
        assertEquals(7, p.getPosition());
        p.abandon(null);
        try{
            p.changePosition(4);
        }catch (PlayerAbandonedException e){

        }
    }

    public void testGetNumCredits() throws RemoteException {
        Player p = new Player( "a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();

        assertEquals(0, p.getNumCredits());
    }


    public void testChangeCredits() throws RemoteException {
        Player p = new Player( "a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();

        assertEquals(0, p.getNumCredits());
        p.changeCredits(10);
        assertEquals(10, p.getNumCredits());
        p.changeCredits(-3);
        assertEquals(7, p.getNumCredits());
    }

    public void testPickComponent() throws RemoteException {
        Cabin c1 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        Storage s1 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        Storage s2 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c2 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c3 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c4 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c5 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c6 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c7 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c8 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c9 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        ArrayList<Components> deck = new ArrayList<>();
        deck.add(c1);
        deck.add(c2);
        deck.add(c3);
        deck.add(c4);
        deck.add(c5);
        deck.add(c6);
        deck.add(c7);
        deck.add(c8);
        deck.add(c9);
        deck.add(cannon);
        deck.add(s1);
        deck.add(s2);

        Player p = new Player( "a", null);
        p.setPlayerShipboard(1);
        ShipBoard s=p.getPlayerShipBoard();

        for (int i=0; i< 20; i++){
            Components c = p.pickComponent(deck);
            assertTrue(deck.contains(c));
        }
        try {
            p.pickComponent(null);
            fail();
        }catch (TilesEndedExceptions e){

        }
    }

    public void testCheckShip() throws RemoteException {
        Player p = new Player( "a", null);
        Game g=new Game(2, 2,1, new GameController());
        p.setPlayerShipboard(2);
        p.opShip(g);
        /*ShipBoard sp1= p.getPlayerShipBoard();
        Cabin c11 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Storage s11 = new Storage(0,true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}, 3);
        Cannon cannon1 = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Storage s21 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}, 2);
        Cabin c21 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c31 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c41 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c51 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c61 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c71 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c81 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c91 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e11 = new Engine(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        sp1.placeComponent(3,2, c11);
        sp1.placeComponent(2,2, s21);
        sp1.placeComponent(4,1, cannon1);
        sp1.placeComponent(1,3, c51);
        sp1.placeComponent(2,3, c21);
        sp1.placeComponent(1,4, c41);
        sp1.placeComponent(2,4, c31);
        sp1.placeComponent(4,3, c61);
        sp1.placeComponent(5,3, c71);
        sp1.placeComponent(4,4, e11);
        sp1.placeComponent(5,4, c91);
        sp1.placeComponent(3,3, c81);

        /*PlayerView pv = new PlayerView(p);
        List<PlayerView> players = new ArrayList<>();
        players.add(pv);
        TUI tui = new TUI(null);
        tui.DrawShipboard(players);*/
        assertTrue(p.checkShip());
    }
    public void testCheckShip2() throws RemoteException {
        Player p = new Player( "a", null);
        new Game(2, 2,1, new GameController());
        p.setPlayerShipboard(1);
        Cabin c= new Cabin(0, true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cannon cannon= new Cannon(1,1,Direction.NORTH,new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        p.getPlayerShipBoard().placeComponent(7,8,cannon);
        p.getPlayerShipBoard().placeComponent(7,7,c);
        assertFalse(p.checkShip());
    }
    public void testCheckShip3() throws RemoteException {
        Player p = new Player( "a", null);
        new Game(2, 2,1, new GameController());
        p.setPlayerShipboard(1);
        Cabin c= new Cabin(0, true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine Engine= new Engine(1,1,Direction.NORTH,new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        p.getPlayerShipBoard().placeComponent(7,7,Engine);
        p.getPlayerShipBoard().placeComponent(7,8,c);
        assertFalse(p.checkShip());
    }


    public void testSetRocketColour() throws RemoteException {
        Player p= new Player("Gianmarco",null);
        p.setRocketColour("Yellow");
        assertEquals("Yellow",p.getRocketColour());
    }

    public void testGetRocketColour() throws RemoteException {
        Player p = new Player("Test", null);
        p.setRocketColour("BLUE");
        assertEquals("BLUE", p.getRocketColour());
    }

    public void testSetPlayerShipboard() throws RemoteException {
        Player p = new Player("Test", null);
        p.setPlayerShipboard(1);
        assertNotNull(p.getPlayerShipBoard());

    }

    public void testIsPosValid() throws RemoteException {
        Player p = new Player("Test", null);
        assertFalse(p.isPosValid());
        p.setPosition(5);
        assertTrue(p.isPosValid());

    }

    public void testSetPosition() throws RemoteException {
        Player p = new Player("Test", null);
        p.setPosition(10);
        assertEquals(10, p.getPosition());
    }

    public void testOpposite() throws RemoteException {
        Player p = new Player("Test", null);
        assertEquals(Direction.SOUTH, p.opposite(Direction.NORTH));
        assertEquals(Direction.NORTH, p.opposite(Direction.SOUTH));
        assertEquals(Direction.WEST, p.opposite(Direction.EAST));
        assertEquals(Direction.EAST, p.opposite(Direction.WEST));
    }

    public void testGetState() throws RemoteException {
        Player p = new Player("Test", null);
        WaitingState ws= new WaitingState(null,p);
        p.setPlayerState(ws);
        assertEquals(ws, p.getState());
    }

    public void testSetPlayerState() throws RemoteException {
        Player p = new Player("Test", null);
        ActivateCannonsState ac= new ActivateCannonsState(null,p);
        p.setPlayerState(ac);
        assertEquals(ac, p.getState());
    }

    public void testGetShipOK() throws RemoteException {
        Player p = new Player("Test", null);
        assertTrue(p.getShipOK());
        p.setShipOK(false);
        assertFalse(p.getShipOK());
    }

    public void testSetShipOK() throws RemoteException {
        Player p = new Player("Test", null);
        p.setShipOK(false);
        assertFalse(p.getShipOK());
        p.setShipOK(true);
        assertTrue(p.getShipOK());
    }

    public void testSetCurrentTile() throws RemoteException {
        Player p = new Player("Test", null);
        Cannon cannon = new Cannon(0,1,Direction.NORTH,new Connector[4]);
        p.setCurrentTile(cannon);
        assertEquals(cannon, p.getCurrentTile());
    }

    public void testGetCurrentTile() throws RemoteException {
        Player p = new Player("Test", null);
        assertNull(p.getCurrentTile());
        Engine engine = new Engine(0,1,Direction.NORTH,new Connector[4]);
        p.setCurrentTile(engine);
        assertEquals(engine, p.getCurrentTile());
    }

    public void testGetShipBuilt() throws RemoteException {
        Player p = new Player("Test", null);
        assertFalse(p.getShipBuilt());
        p.setShipBuilt();
        assertTrue(p.getShipBuilt());
    }

    public void testSetShipBuilt() throws RemoteException {
        Player p = new Player("Test", null);
        p.setShipBuilt();
        assertTrue(p.getShipBuilt());
    }

    public void testGetDeckShowed() throws RemoteException {
        Player p = new Player("Test", null);
        assertNull(p.getDeckShowed());
        ArrayList<AdventureCard> deck = new ArrayList<>();
        p.setDeckShowed(deck);
        assertEquals(deck, p.getDeckShowed());
    }

    public void testSetDeckShowed() throws RemoteException {
        Player p = new Player("Test", null);
        ArrayList<AdventureCard> deck = new ArrayList<>();
        p.setDeckShowed(deck);
        assertEquals(deck, p.getDeckShowed());
    }

    public void testSetReadyForCards() throws RemoteException {
        Player p = new Player("Test", null);
        p.setReadyForCards(true);
        assertTrue(p.getReadyForCards());
        p.setReadyForCards(false);
        assertFalse(p.getReadyForCards());
    }

    public void testGetReadyForCards() throws RemoteException {
        Player p = new Player("Test", null);
        assertFalse(p.getReadyForCards());
        p.setReadyForCards(true);
        assertTrue(p.getReadyForCards());
    }

    public void testOpShip() throws RemoteException {
        Player p = new Player("Test", null);
        p.setPlayerShipboard(1);
        Cabin c = new Cabin(0, true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        p.getPlayerShipBoard().placeComponent(7,7, c);
        assertNotNull(p.getPlayerShipBoard().getComponent(7,7));
    }
    public void testOpship2() throws RemoteException {
        // 1. Crea un Player e una ShipBoard 11x11 (o la dimensione giusta del tuo gioco)
        Player player = new Player("TestPlayer", null);
        boolean[][] availablePositionMatrix = new boolean[11][11];
        for (int i = 0; i < 11; i++)
            for (int j = 0; j < 11; j++)
                availablePositionMatrix[i][j] = true;
        ShipBoard board = new ShipBoard(availablePositionMatrix, 11, 11);
        player.setPlayerShipboard(2);

        // 2. Inserisci una cabina centrale e almeno un altro componente altrove
        Cabin central = new Cabin(0, true, Direction.NORTH,
                new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        Cannon sideCannon = new Cannon(0, 1, Direction.NORTH,
                new Connector[]{Connector.EMPTY, Connector.UNIVERSAL, Connector.EMPTY, Connector.EMPTY});

        player.getPlayerShipBoard().placeComponent(8, 8, sideCannon); // fuori dal centro

        // 3. Chiama il metodo
        player.opShip(new Game(2,2,1,new GameController()));

        // 4. Verifica: centrale non è stato rimosso, gli altri sì

    }
    public void testOpship1() throws RemoteException {
        // 1. Crea un Player e una ShipBoard 11x11 (o la dimensione giusta del tuo gioco)
        Player player = new Player("TestPlayer", null);
        player.setPlayerShipboard(1);
        // 3. Chiama il metodo
        player.opShip(new Game(2,1,1,new GameController()));

        // 4. Verifica: centrale non è stato rimosso, gli altri sì

    }
}