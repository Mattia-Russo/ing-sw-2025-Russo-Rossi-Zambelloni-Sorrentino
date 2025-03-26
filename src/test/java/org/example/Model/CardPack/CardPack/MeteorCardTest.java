package org.example.Model.CardPack.CardPack;

import junit.framework.TestCase;
import org.example.Model.ComponentsPack.Direction;
import org.example.Model.CardPack.Meteor;
import org.example.Model.CardPack.MeteorCard;

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

}