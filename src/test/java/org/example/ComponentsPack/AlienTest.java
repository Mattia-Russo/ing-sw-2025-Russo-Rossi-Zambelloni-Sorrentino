package org.example.ComponentsPack;

import junit.framework.TestCase;

public class AlienTest extends TestCase {

    public void testGetColour() {
        Alien a = new Alien(AlienColour.BROWN);
        assertEquals(AlienColour.BROWN, a.getColour());
    }
}