package org.example.ServerPkg.Model.CardPkg;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.ComponentsPkg.*;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.ShipBoard;

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

    public void testSetCardState() {
        Player p1 = new Player("a", null);
        Player p2 = new Player( "a", null);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        Game g=new Game(4, 2, 1, new GameController());
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
        //assertTrue(p1.getState() instanceof ActivateShieldsState);
    }

    public void testPlayCard() {
        Player p1 = new Player("a", null);
        Player p2 = new Player( "a", null);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        Game g=new Game(4, 2, 1, new GameController());
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
        Cannon ca25 = new Cannon(0,2, Direction.SOUTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL});
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
        sp2.placeComponent(7,6, ca25);

        c.setCardState(g);
    }
}