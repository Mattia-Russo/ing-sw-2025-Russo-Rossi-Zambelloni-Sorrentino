package org.example.ServerPkg.Model.ComponentsPkg;

import junit.framework.TestCase;

public class AlienTest extends TestCase {

    public void testColour() {
        Alien a = new Alien(AlienColour.BROWN);
        assertEquals(AlienColour.BROWN, a.colour());
    }
}