package org.example.Model.ComponentsPack;

import junit.framework.TestCase;
import org.example.Server.Model.ComponentsPack.Alien;
import org.example.Server.Model.ComponentsPack.AlienColour;

public class AlienTest extends TestCase {

    public void testGetColour() {
        Alien a = new Alien(AlienColour.BROWN);
        assertEquals(AlienColour.BROWN, a.getColour());
    }

    public void testTestGetColour() {
    }
}