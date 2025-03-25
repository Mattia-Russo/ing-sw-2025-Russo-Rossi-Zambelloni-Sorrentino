package org.example.ComponentsPack;

import junit.framework.TestCase;
import org.example.Model.ComponentsPack.Goods;
import org.example.Model.ComponentsPack.GoodsColour;

public class GoodsTest extends TestCase {

    public void testGetColour() {
        Goods g1 = new Goods(GoodsColour.BLUE);
        assertEquals(GoodsColour.BLUE, g1.getColour());

    }

    public void testGetStorage() {

    }

    public void testSetStorage() {

    }
}