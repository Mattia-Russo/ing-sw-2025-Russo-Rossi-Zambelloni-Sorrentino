package org.example.ComponentsPack;

import junit.framework.TestCase;
public class StorageTest extends TestCase {

    public void testGetGoods() {
        Storage s = new Storage(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE}, 3);
        Goods g1 = new Goods(0);
        Goods g2 = new Goods(0);
        Goods g3 = new Goods(0);
        s.addGood(g1);
        s.addGood(g2);
        s.addGood(g3);
        Goods[] goods = s.getGoods();
        assertEquals(g1, goods[0]);
        assertEquals(g2, goods[1]);
        assertEquals(g3, goods[2]);

    }

    public void testGetIsSpecial() {

    }

    public void testRemoveGood() {
//        Storage s = new Storage(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE}, 3);
//        Goods g1 = new Goods(0);
//        Goods g2 = new Goods(0);
//        Goods g3 = new Goods(0);
//        assertNull(s.getGoods()[0]);
//        s.addGood(g1);
//        assertEquals(g1, s.getGoods()[0]);
//        s.addGood(g2);
//        assertEquals(g2, s.getGoods()[1]);
//        s.addGood(g3);
//        assertEquals(g3, s.getGoods()[2]);
//        s.removeGood(g2);
//        assertNull(s.getGoods()[1]);
//        Goods g4 = new Goods(0);
//        s.addGood(g4);
//        assertEquals(g4, s.getGoods()[1]);
//        s.removeGood(g4);
//        s.removeGood(g1);
//        s.removeGood(g3);
//        assertNull(s.getGoods()[0]);
//        assertNull(s.getGoods()[1]);
//        assertNull(s.getGoods()[2]);
    }

    public void testAddGood() {

    }

    public void testgetCapacity(){
        Storage s = new Storage(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE}, 3);
        assertEquals(3, s.getCapacity());
    }
}