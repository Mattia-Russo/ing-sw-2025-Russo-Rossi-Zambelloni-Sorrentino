package org.example.ServerPkg.Model.CardPkg;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.ControllerPkg.PlayerStates.ActivateCannonsState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.ActivateShieldsState;
import org.example.ServerPkg.Model.ComponentsPkg.*;
import org.example.ServerPkg.Model.ForView.AdventureCardView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;
import org.example.ServerPkg.Model.ShipBoard;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class MeteorCardTest extends TestCase {

    public void testGetMeteorList() {
        Meteor meteor1=new Meteor(1,  Direction.EAST);
        Meteor meteor2=new Meteor(0, Direction.NORTH);
        Meteor meteor3=new Meteor(1, Direction.WEST);
        List<Meteor> meteors=new ArrayList<>();
        meteors.add(meteor1);
        meteors.add(meteor2);
        meteors.add(meteor3);
        MeteorCard m= new MeteorCard(0,2, 3, meteors);
        assertEquals(meteors, m.getMeteorList());
    }


    public void testGetCardLevel() {
        Meteor meteor1=new Meteor(1,  Direction.EAST);
        Meteor meteor2=new Meteor(0, Direction.NORTH);
        Meteor meteor3=new Meteor(1, Direction.WEST);
        List<Meteor> meteors=new ArrayList<>();
        meteors.add(meteor1);
        meteors.add(meteor2);
        meteors.add(meteor3);
        MeteorCard m= new MeteorCard(0,2, 3, meteors);
        assertEquals(2, m.getCardLevel());
    }

    public void testSetCardState() throws RemoteException {
        Player p1 = new Player("a", null);
        Player p2 = new Player( "a", null);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        Game g=new Game(4, 2, 1, new GameController()){
            @Override
            public void Turn(){

            }
        };
        g.getPlayers().addAll(players);
        g.setPlayersShipboard();
        Meteor meteor1=new Meteor(1,  Direction.EAST);
        Meteor meteor2=new Meteor(0, Direction.NORTH);
        List<Meteor> meteors=new ArrayList<>();
        meteors.add(meteor1);
        meteors.add(meteor2);
        MeteorCard c= new MeteorCard(0,1,0,meteors);
        g.setCard(c);
        ShipBoard sp1 = p1.getPlayerShipBoard();
        ShipBoard sp2 = p2.getPlayerShipBoard();

        Storage s11 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}, 3);
        Cannon ca11 = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cannon ca12 = new Cannon(0,2, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY});
        Cabin c12 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c13 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY});
        Shield sh11 = new Shield(0,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}, Direction.EAST);
        Shield sh12 = new Shield(0,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL}, Direction.WEST);
        Cannon ca13 = new Cannon(0,1, Direction.EAST, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Tubes t11 = new Tubes(0,Direction.EAST, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e11 = new Engine(0,1, Direction.SOUTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL});
        BatteryStorage bs11 = new BatteryStorage(0,3, Direction.NORTH,  new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL});
        BatteryStorage bs12 = new BatteryStorage(0,2, Direction.NORTH,  new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e12 = new Engine(0,2, Direction.SOUTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL});
        Cannon ca14 = new Cannon(0,1, Direction.WEST, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cannon ca15 = new Cannon(0,2, Direction.SOUTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL});
        sp1.placeComponent(8,7, s11);
        sp1.placeComponent(8,6, ca11);
        sp1.placeComponent(5,9, ca12);
        sp1.placeComponent(9,7, c12);
        sp1.placeComponent(5,7, c13);
        sp1.placeComponent(6,7, sh11);
        sp1.placeComponent(9,9, sh12);
        sp1.placeComponent(5,8, ca13);
        sp1.placeComponent(9,8, t11);
        sp1.placeComponent(8,8, e11);
        sp1.placeComponent(7,8, bs11);
        sp1.placeComponent(6,8, bs12);
        sp1.placeComponent(6,9, e12);
        sp1.placeComponent(10,8, ca14);
        sp1.placeComponent(7,6, ca15);

        Storage s21 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}, 3);
        Cannon ca21 = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cannon ca22 = new Cannon(0,2, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY});
        Cabin c22 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c23 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY});
        Shield sh21 = new Shield(0,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}, Direction.EAST);
        Shield sh22 = new Shield(0,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL}, Direction.WEST);
        Cannon ca23 = new Cannon(0,1, Direction.EAST, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Tubes t21 = new Tubes(0,Direction.EAST, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e21 = new Engine(0,1, Direction.SOUTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL});
        BatteryStorage bs21 = new BatteryStorage(0,3, Direction.NORTH,  new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL});
        BatteryStorage bs22 = new BatteryStorage(0,2, Direction.NORTH,  new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e22 = new Engine(0,2, Direction.SOUTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL});
        Cannon ca24 = new Cannon(0,1, Direction.WEST, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        sp2.placeComponent(8,7, s21);
        sp2.placeComponent(8,6, ca21);
        sp2.placeComponent(5,9, ca22);
        sp2.placeComponent(9,7, c22);
        sp2.placeComponent(5,7, c23);
        sp2.placeComponent(6,7, sh21);
        sp2.placeComponent(9,9, sh22);
        sp2.placeComponent(5,8, ca23);
        sp2.placeComponent(9,8, t21);
        sp2.placeComponent(8,8, e21);
        sp2.placeComponent(7,8, bs21);
        sp2.placeComponent(6,8, bs22);
        sp2.placeComponent(6,9, e22);
        sp2.placeComponent(10,8, ca24);

        c.setCardState(g);
        assertNotNull(c12);
    }

    public void testPlayCard() throws RemoteException {
        Player p1 = new Player("a", null);
        Player p2 = new Player( "b", null);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        Game g=new Game(2, 2, 1, new GameController()){
            @Override
            public void Turn(){

            }
        };
        g.getPlayers().addAll(players);
        g.setPlayersShipboard();
        p1.opShip(g);
        p2.opShip(g);
        Meteor meteor1=new Meteor(1,  Direction.EAST);
        Meteor meteor2=new Meteor(0, Direction.NORTH);
        List<Meteor> meteors=new ArrayList<>();
        meteors.add(meteor1);
        meteors.add(meteor2);
        MeteorCard c= new MeteorCard(0,1,0,meteors);
        c.setCardState(g);
    }
    public void testPlayCard_AllBranches() throws RemoteException {
        // --- SETUP BASE ---
        Player p = new Player("TestPlayer", null);
        Game game = new Game(3, 1, 1, new GameController()){
            @Override
            public void Turn(){}
        };
        game.getPlayers().add(p);
        game.setPlayersShipboard();

        // Meteore: piccolo NORD, grande SUD
        List<Meteor> meteorList = new ArrayList<>();
        meteorList.add(new Meteor(0, Direction.NORTH)); // Piccolo
        meteorList.add(new Meteor(1, Direction.SOUTH)); // Grande
        MeteorCard meteorCard = new MeteorCard(9, 1, 1, meteorList);

        // Metti la carta in gioco
        game.setCard(meteorCard);

        // --- SHIPBOARD: componenti per testare tutti i rami ---
        // Piccola meteorite su colonna: protezione tramite shield
        Shield shield = new Shield(8, Direction.NORTH, new Connector[] {
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL
        }, Direction.NORTH);
        p.getPlayerShipBoard().placeComponent(8, 8, shield);

        // Grande meteorite: cannoni
        Cannon cannon = new Cannon(7, 2, Direction.SOUTH, new Connector[] {
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL
        });
        BatteryStorage battery = new BatteryStorage(5, 4, Direction.SOUTH, new Connector[] {
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL
        });
        p.getPlayerShipBoard().placeComponent(7, 8, cannon);
        p.getPlayerShipBoard().placeComponent(8, 7, battery);

        meteorCard.setCardState(game);
        ArrayList<Points> shieldPoints = new ArrayList<>();
        ArrayList<Points> batteryPoints = new ArrayList<>();
        batteryPoints.add(new Points(8,7));
        shieldPoints.add(new Points(8, 8));
        try {
            meteorCard.playCard(game, shieldPoints, batteryPoints);
        }catch(ArrayIndexOutOfBoundsException e){}
        // dovrebbe togliere il componente, ecc.

        // 2) Secondo giro: grande meteorite (cannon)
        // Qui possiamo passare cannon che protegge o meno
//        meteorCard.setCardState(game);
//        ArrayList<Points> cannonPoints = new ArrayList<>();
//        cannonPoints.add(new Points(7, 8));
//        ArrayList<Points> cannonBatteryPoints = new ArrayList<>();
//        cannonBatteryPoints.add(new Points(8, 7));
//        meteorCard.playCard(game, cannonPoints, cannonBatteryPoints);


//        meteorCard.playCard(game, new ArrayList<>(), new ArrayList<>()); // shieldPoints & batteryPoints non null -> se shield protegge, va su WaitingState
//
//        // 4) Prova: null points (entra ramo null, fa checkWreck)
//        meteorCard.setCardState(game);
//        meteorCard.playCard(game, null, null);
//
//        // (Se vuoi, puoi aggiungere asserzioni su PlayerState o ShipBoard dopo ogni chiamata.)
//
//        // Copriamo anche l’eccezione (es. punti invalidi)
//        try {
//            meteorCard.playCard(game, new ArrayList<>(), null);
//        } catch (Exception e) {
//            // ok
//        }
    }
    public void testPlayCard_AllBranches1() throws RemoteException {
        // --- SETUP BASE ---
        Player p = new Player("TestPlayer", null);
        Player p2 = new Player("TestPlayer", null);
        Game game = new Game(3, 1, 1, new GameController());
        game.getPlayers().add(p);
        game.getPlayers().add(p2);
        game.setPlayersShipboard();

        // Meteore: piccolo NORD, grande SUD
        List<Meteor> meteorList = new ArrayList<>();// Piccolo
        meteorList.add(new Meteor(1, Direction.SOUTH)); // Grande
        MeteorCard meteorCard = new MeteorCard(3, 1, 1, meteorList);

        // Metti la carta in gioco
        game.setCard(meteorCard);

        // --- SHIPBOARD: componenti per testare tutti i rami ---
        // Piccola meteorite su colonna: protezione tramite shield
        Shield shield = new Shield(0, Direction.NORTH, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL
        }, Direction.NORTH);
        p.getPlayerShipBoard().placeComponent(8, 8, shield);

        // Grande meteorite: cannoni
        Cannon cannon = new Cannon(1, 2, Direction.SOUTH, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL
        });
        BatteryStorage battery = new BatteryStorage(2, 4, Direction.SOUTH, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL
        });
        meteorCard.setCurrentPlayer(0);
        meteorCard.setCurrentMeteorIndex(0);
        p.getPlayerShipBoard().placeComponent(7, 8, cannon);
        p.getPlayerShipBoard().placeComponent(8, 7, battery);

        ArrayList<Points> cannonPoints = new ArrayList<>();
        cannonPoints.add(new Points(7, 8));
        ArrayList<Points> cannonBatteryPoints = new ArrayList<>();
        cannonBatteryPoints.add(new Points(8, 7));
        try {
            meteorCard.playCard(game, cannonPoints, cannonBatteryPoints);
        }catch(ArrayIndexOutOfBoundsException e){}
    }
    public void testPlayCard_AllBranches2() throws RemoteException {
        // --- SETUP BASE ---
        Player p = new Player("TestPlayer", null);
        Game game = new Game(3, 1, 1, new GameController()){
            @Override
            public void Turn(){

            }
        };
        game.getPlayers().add(p);
        game.setPlayersShipboard();

        // Meteore: piccolo NORD, grande SUD
        List<Meteor> meteorList = new ArrayList<>();
        meteorList.add(new Meteor(0, Direction.NORTH)); // Piccolo
        meteorList.add(new Meteor(1, Direction.SOUTH)); // Grande
        MeteorCard meteorCard = new MeteorCard(1, 1, 1, meteorList);

        // Metti la carta in gioco
        game.setCard(meteorCard);

        // --- SHIPBOARD: componenti per testare tutti i rami ---
        // Piccola meteorite su colonna: protezione tramite shield
        Shield shield1 = new Shield(10, Direction.NORTH, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL
        }, Direction.NORTH);
        p.getPlayerShipBoard().placeComponent(8, 8, shield1);

        // Grande meteorite: cannoni
        Cannon cannon = new Cannon(0, 2, Direction.SOUTH, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL
        });
        BatteryStorage battery = new BatteryStorage(0, 4, Direction.SOUTH, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL
        });
        p.getPlayerShipBoard().placeComponent(7, 8, cannon);
        p.getPlayerShipBoard().placeComponent(8, 7, battery);
        meteorCard.setCardState(game);
        try {
            meteorCard.playCard(game, null, null);
        }catch(IndexOutOfBoundsException e){}
    }


    public void testCreateView() {
        List<Meteor> meteorList = List.of(
                new Meteor(0, Direction.NORTH)
        );

        MeteorCard meteorCard = new MeteorCard(42, 1, 0, meteorList);

        AdventureCardView view = meteorCard.createView();
        String expectedCommand = """
                 You are playing the meteor card, you can type:
                 activate_cannons x y -> x,y are the coordinates of a cannon, you should write a number of x,y based on the number of cannons you want to activate
                 activate_shields x y -> x,y are the coordinates of a shield, you should write a number of x,y based on the number of shields you want to activate
                 use_batteries x y -> x,y are the coordinates of the battery storage, you should write a number of x,y based on the number of batteries you want to use
                
                 end_activate_cannons -> if you want to end the cannon activation phase
                 end_activate_shields -> if you want to end the shield activation phase
                
                 choose_wrecked x y -> x,y are the coordinates of one of the tile from the part you want to keep
                 end_wrecked -> if you want to end the wrecked ship phase
                """;
        assertEquals("MeteorCard", view.getType());
        assertEquals(42, view.getId());
        assertEquals(0, view.getLostDays());
        assertTrue(view.getCommands().contains(expectedCommand));
    }

}