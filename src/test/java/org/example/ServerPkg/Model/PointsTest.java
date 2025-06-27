package org.example.ServerPkg.Model;

import junit.framework.TestCase;

public class PointsTest extends TestCase {

    public void testGetX() {
        Points p = new Points(3, 7);
        assertEquals(3, p.getX());

    }

    public void testGetY() {
        Points p = new Points(3, 7);
        assertEquals(7, p.getY());
    }

    public void testEquals() {
        Points p = new Points(1, 2);
        assertTrue(p.equals(p));

        Points p1 = new Points(5, 9);
        Points p2 = new Points(5, 9);
        assertTrue(p1.equals(p2));
        assertTrue(p2.equals(p1));

        Points p3 = new Points(1, 2);
        Points p4 = new Points(9, 2);
        assertFalse(p3.equals(p4));
    }

    public void testHashCode() {
        Points p1 = new Points(6, 8);
        Points p2 = new Points(6, 8);
        assertEquals(p1.hashCode(), p2.hashCode());

        Points p3 = new Points(1, 2);
        Points p4 = new Points(3, 4);
        assertNotSame(p3.hashCode(), p4.hashCode());
    }
}