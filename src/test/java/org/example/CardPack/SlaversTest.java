package org.example.CardPack;

import junit.framework.TestCase;
public class SlaversTest extends TestCase {

    public void testGetCannonPower() {
        Slavers s=new Slavers(1,2,10, 2, 10);
        assertEquals(10,s.getCannonPower());
    }

    public void testGetCardLevel() {
        Slavers s=new Slavers(1,2,10, 2, 10);
        assertEquals(1,s.getCardLevel());
    }

    public void testGetLostDays() {
        Slavers s=new Slavers(1,2,10, 2, 10);
        assertEquals(2,s.getLostDays());
    }

    public void testGetNumAstronauts() {
        Slavers s=new Slavers(1,2,10, 2, 10);
        assertEquals(2,s.getNumAstronauts());
    }

    public void testGetCredits() {
        Slavers s=new Slavers(1,2,10, 2, 10);
        assertEquals(10,s.getCredits());
    }
}