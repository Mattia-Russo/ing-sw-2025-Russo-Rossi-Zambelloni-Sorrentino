package org.example.CardPack;

import junit.framework.TestCase;
import org.example.Model.CardPack.AbandonedShip;

public class AbandonedShipTest extends TestCase {

    public void testGetCardLevel() {
        AbandonedShip a=new AbandonedShip(1,2,10, 3);
        assertEquals(1,a.getCardLevel());
    }

    public void testGetLostDays() {
        AbandonedShip a=new AbandonedShip(1,2,10, 3);
        assertEquals(2,a.getLostDays());
    }

    public void testGetCredits() {
        AbandonedShip a=new AbandonedShip(1,2,10, 3);
        assertEquals(10,a.getCredits());
    }

    public void testGetNumAstronauts() {
        AbandonedShip a=new AbandonedShip(1,2,10, 3);
        assertEquals(3,a.getNumAstronauts());
    }
}