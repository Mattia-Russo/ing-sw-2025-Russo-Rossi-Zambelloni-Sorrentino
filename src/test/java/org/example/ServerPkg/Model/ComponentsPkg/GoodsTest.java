package org.example.ServerPkg.Model.ComponentsPkg;

import junit.framework.TestCase;

public class GoodsTest extends TestCase {

    public void testGetColour() {
        Goods g1 = new Goods(GoodsColour.BLUE);
        assertEquals(GoodsColour.BLUE, g1.getColour());

    }

    public void testGetStorage() {
        Goods goods = new Goods(GoodsColour.YELLOW);
        assertEquals(GoodsColour.YELLOW, goods.getColour());
        assertNull(goods.getStorage());
        Storage storage = new Storage(1, false, null, null,2);
        goods.setStorage(storage);
        assertEquals(storage, goods.getStorage());
    }

    public void testSetStorage() {
        Goods goods = new Goods(GoodsColour.YELLOW);
        assertEquals(GoodsColour.YELLOW, goods.getColour());
        assertNull(goods.getStorage());
        Storage storage = new Storage(1, false, null, null,2);
        goods.setStorage(storage);
        assertEquals(storage, goods.getStorage());
    }

    public void testSetTaken() {
        Goods goods = new Goods(GoodsColour.YELLOW);
        goods.setTaken();
    }
}