package org.example.Server.Model.CardPack;

import junit.framework.TestCase;
import org.example.Server.Model.ComponentsPack.Direction;
import org.example.Server.Model.Game;
import org.example.Server.Model.Player;

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
        Player p2 = new Player(7, "a");
        Player p3 = new Player( 14, "a");
        Player p4 = new Player(9, "a");
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        Game g=new Game(4, 1, players,1, 30);
        g.setCard(new MeteorCard(1,0,null));
        g.Turn();
    }

    public void testPlayCard() {
    }
}