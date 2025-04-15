package org.example.ServerPkg.Model.ComponentsPack;

import junit.framework.TestCase;


import java.util.ArrayList;

public class StorageTest extends TestCase {

    public void testGetGoods() {
        Storage s1 = new Storage(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Goods g1 = new Goods(GoodsColour.GREEN);
        Goods g2 = new Goods(GoodsColour.GREEN);
        Goods g3 = new Goods(GoodsColour.GREEN);

        s1.addGood(g1);
        s1.addGood(g2);
        s1.addGood(g3);

        ArrayList<Goods> goods = new ArrayList<Goods>();
        goods.add(g1);
        goods.add(g2);
        goods.add(g3);

        for(int i = 0; i < goods.size(); i++) {
            assertEquals(goods.get(i), s1.getGoods()[i]);
        }

    }

    public void testGetIsSpecial() {
        Storage s1 = new Storage(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Storage s2 = new Storage(true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        assertTrue(s2.getIsSpecial());
        assertFalse(s1.getIsSpecial());
    }

    public void testRemoveGood() {
        Storage s1 = new Storage(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Goods g1 = new Goods(GoodsColour.GREEN);
        Goods g2 = new Goods(GoodsColour.GREEN);
        Goods g3 = new Goods(GoodsColour.GREEN);

        s1.addGood(g1);
        s1.addGood(g2);
        s1.addGood(g3);

        ArrayList<Goods> goods = new ArrayList<Goods>();
        goods.add(g1);
        goods.add(g2);
        goods.add(g3);

        for(int i = 0; i < goods.size(); i++) {
            assertEquals(goods.get(i), s1.getGoods()[i]);
        }

        s1.removeGood(1);
        goods.remove(g1);
        assertEquals(null, s1.getGoods()[1]);
    }

    public void testAddGood() {
        Storage s1 = new Storage(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Goods g1 = new Goods(GoodsColour.GREEN);
        Goods g2 = new Goods(GoodsColour.GREEN);
        Goods g3 = new Goods(GoodsColour.BLUE);
        Goods g4 = new Goods(GoodsColour.GREEN);

        s1.addGood(g1);
        s1.addGood(g2);
        s1.addGood(g3);
    }

    public void testGetCapacity() {
        Storage s1 = new Storage(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Storage s2 = new Storage(true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 2);
        assertEquals(3, s1.getCapacity());
        assertEquals(2, s2.getCapacity());
    }

    public void testAddStorage() {
        ArrayList<Goods> goods = new ArrayList<>();
        Storage s1 = new Storage(true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        s1.addGood(new Goods(GoodsColour.GREEN));
        s1.addGood(new Goods(GoodsColour.BLUE));
        s1.addGood(new Goods(GoodsColour.RED));

        s1.addStorage(goods);
        for(int i = 0; i < goods.size(); i++) {
            assertEquals(goods.get(i), s1.getGoods()[i]);
        }
    }
}