package org.example.ComponentsPack;

import junit.framework.TestCase;
import org.example.Model.ComponentsPack.Alien;
import org.example.Model.ComponentsPack.AlienColour;

public class AlienTest extends TestCase {

    public void testGetColour() {
        Alien a = new Alien(AlienColour.BROWN);
        assertEquals(AlienColour.BROWN, a.getColour());
    }
}