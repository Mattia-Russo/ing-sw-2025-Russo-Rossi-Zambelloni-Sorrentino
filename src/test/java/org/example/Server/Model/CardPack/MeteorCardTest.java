package org.example.Server.Model.CardPack;

import junit.framework.TestCase;
import org.example.Server.Model.ComponentsPack.*;
import org.example.Server.Model.Game;
import org.example.Server.Model.Player;
import org.example.Server.Model.ShipBoard;

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
        MeteorCard m= new MeteorCard(2, 3, meteors);
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
        MeteorCard m= new MeteorCard(2, 3, meteors);
        assertEquals(2, m.getCardLevel());
    }

    public void testSetCardState() {
        Player p1 = new Player(12, "a");
        Player p2 = new Player( 7, "a");
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        Game g=new Game(4, 2, 1, null);
        Meteor meteor1=new Meteor(1,  Direction.EAST);
        Meteor meteor2=new Meteor(0, Direction.NORTH);
        List<Meteor> meteors=new ArrayList<>();
        meteors.add(meteor1);
        meteors.add(meteor2);
        MeteorCard c= new MeteorCard(1,0,meteors);
        g.setCard(c);
        ShipBoard sp1 = p1.getPlayerShipBoard();
        ShipBoard sp2 = p2.getPlayerShipBoard();

        Cabin c11 = new Cabin(true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Storage s11 = new Storage(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}, 3);
        Cannon ca11 = new Cannon(1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cannon ca12 = new Cannon(2, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY});
        Cabin c12 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c13 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY});
        Shield sh11 = new Shield(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}, Direction.EAST);
        Shield sh12 = new Shield(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL}, Direction.WEST);
        Cannon ca13 = new Cannon(1, Direction.EAST, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Tubes t11 = new Tubes(Direction.EAST, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e11 = new Engine(1, Direction.SOUTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL});
        BatteryStorage bs11 = new BatteryStorage(3, Direction.NORTH,  new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL});
        BatteryStorage bs12 = new BatteryStorage(2, Direction.NORTH,  new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e12 = new Engine(2, Direction.SOUTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL});
        Cannon ca14 = new Cannon(1, Direction.WEST, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cannon ca15 = new Cannon(2, Direction.SOUTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL});
        sp1.placeComponent(3,2, c11);
        sp1.placeComponent(4,2, s11);
        sp1.placeComponent(4,1, ca11);
        sp1.placeComponent(1,4, ca12);
        sp1.placeComponent(5,2, c12);
        sp1.placeComponent(1,2, c13);
        sp1.placeComponent(2,2, sh11);
        sp1.placeComponent(5,4, sh12);
        sp1.placeComponent(1,3, ca13);
        sp1.placeComponent(5,3, t11);
        sp1.placeComponent(4,3, e11);
        sp1.placeComponent(3,3, bs11);
        sp1.placeComponent(2,3, bs12);
        sp1.placeComponent(2,4, e12);
        sp1.placeComponent(6,3, ca14);
        sp1.placeComponent(3,1, ca15);

        Cabin c21 = new Cabin(true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Storage s21 = new Storage(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}, 3);
        Cannon ca21 = new Cannon(1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cannon ca22 = new Cannon(2, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY});
        Cabin c22 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c23 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY});
        Shield sh21 = new Shield(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}, Direction.EAST);
        Shield sh22 = new Shield(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL}, Direction.WEST);
        Cannon ca23 = new Cannon(1, Direction.EAST, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Tubes t21 = new Tubes(Direction.EAST, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e21 = new Engine(1, Direction.SOUTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL});
        BatteryStorage bs21 = new BatteryStorage(3, Direction.NORTH,  new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL});
        BatteryStorage bs22 = new BatteryStorage(2, Direction.NORTH,  new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e22 = new Engine(2, Direction.SOUTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL});
        Cannon ca24 = new Cannon(1, Direction.WEST, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cannon ca25 = new Cannon(2, Direction.SOUTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL});
        sp2.placeComponent(3,2, c21);
        sp2.placeComponent(4,2, s21);
        sp2.placeComponent(4,1, ca21);
        sp2.placeComponent(1,4, ca22);
        sp2.placeComponent(5,2, c22);
        sp2.placeComponent(1,2, c23);
        sp2.placeComponent(2,2, sh21);
        sp2.placeComponent(5,4, sh22);
        sp2.placeComponent(1,3, ca23);
        sp2.placeComponent(5,3, t21);
        sp2.placeComponent(4,3, e21);
        sp2.placeComponent(3,3, bs21);
        sp2.placeComponent(2,3, bs22);
        sp2.placeComponent(2,4, e22);
        sp2.placeComponent(6,3, ca24);
        sp2.placeComponent(3,1, ca25);

        c.setCardState(g);
        assertNotNull(c12);
        //assertTrue(p1.getState() instanceof ActivateShieldsState);
    }

    public void testPlayCard() {
        Player p1 = new Player(12, "a");
        Player p2 = new Player( 7, "a");
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        Game g=new Game(4, 2, 1, null);
        Meteor meteor1=new Meteor(1,  Direction.EAST);
        Meteor meteor2=new Meteor(0, Direction.NORTH);
        List<Meteor> meteors=new ArrayList<>();
        meteors.add(meteor1);
        meteors.add(meteor2);
        MeteorCard c= new MeteorCard(1,0,meteors);
        g.setCard(c);
        ShipBoard sp1 = p1.getPlayerShipBoard();
        ShipBoard sp2 = p2.getPlayerShipBoard();

        Cabin c11 = new Cabin(true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Storage s11 = new Storage(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}, 3);
        Cannon ca11 = new Cannon(1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cannon ca12 = new Cannon(2, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY});
        Cabin c12 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c13 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY});
        Shield sh11 = new Shield(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}, Direction.EAST);
        Shield sh12 = new Shield(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL}, Direction.WEST);
        Cannon ca13 = new Cannon(1, Direction.EAST, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Tubes t11 = new Tubes(Direction.EAST, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e11 = new Engine(1, Direction.SOUTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL});
        BatteryStorage bs11 = new BatteryStorage(3, Direction.NORTH,  new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL});
        BatteryStorage bs12 = new BatteryStorage(2, Direction.NORTH,  new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e12 = new Engine(2, Direction.SOUTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL});
        Cannon ca14 = new Cannon(1, Direction.WEST, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cannon ca15 = new Cannon(2, Direction.SOUTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL});
        sp1.placeComponent(3,2, c11);
        sp1.placeComponent(4,2, s11);
        sp1.placeComponent(4,1, ca11);
        sp1.placeComponent(1,4, ca12);
        sp1.placeComponent(5,2, c12);
        sp1.placeComponent(1,2, c13);
        sp1.placeComponent(2,2, sh11);
        sp1.placeComponent(5,4, sh12);
        sp1.placeComponent(1,3, ca13);
        sp1.placeComponent(5,3, t11);
        sp1.placeComponent(4,3, e11);
        sp1.placeComponent(3,3, bs11);
        sp1.placeComponent(2,3, bs12);
        sp1.placeComponent(2,4, e12);
        sp1.placeComponent(6,3, ca14);
        sp1.placeComponent(3,1, ca15);

        Cabin c21 = new Cabin(true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Storage s21 = new Storage(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}, 3);
        Cannon ca21 = new Cannon(1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cannon ca22 = new Cannon(2, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY});
        Cabin c22 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c23 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY});
        Shield sh21 = new Shield(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}, Direction.EAST);
        Shield sh22 = new Shield(Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL}, Direction.WEST);
        Cannon ca23 = new Cannon(1, Direction.EAST, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Tubes t21 = new Tubes(Direction.EAST, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e21 = new Engine(1, Direction.SOUTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL});
        BatteryStorage bs21 = new BatteryStorage(3, Direction.NORTH,  new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL});
        BatteryStorage bs22 = new BatteryStorage(2, Direction.NORTH,  new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e22 = new Engine(2, Direction.SOUTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL});
        Cannon ca24 = new Cannon(1, Direction.WEST, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cannon ca25 = new Cannon(2, Direction.SOUTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL});
        sp2.placeComponent(3,2, c21);
        sp2.placeComponent(4,2, s21);
        sp2.placeComponent(4,1, ca21);
        sp2.placeComponent(1,4, ca22);
        sp2.placeComponent(5,2, c22);
        sp2.placeComponent(1,2, c23);
        sp2.placeComponent(2,2, sh21);
        sp2.placeComponent(5,4, sh22);
        sp2.placeComponent(1,3, ca23);
        sp2.placeComponent(5,3, t21);
        sp2.placeComponent(4,3, e21);
        sp2.placeComponent(3,3, bs21);
        sp2.placeComponent(2,3, bs22);
        sp2.placeComponent(2,4, e22);
        sp2.placeComponent(6,3, ca24);
        sp2.placeComponent(3,1, ca25);

        c.setCardState(g);
    }
}