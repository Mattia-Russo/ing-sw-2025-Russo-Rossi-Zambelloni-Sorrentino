package org.example.ServerPkg.Model.CardPkg;

import junit.framework.TestCase;
import org.example.ServerPkg.Model.ComponentsPkg.Goods;
import org.example.ServerPkg.Model.ComponentsPkg.GoodsColour;

public class PlanetTest extends TestCase {

    public void testGetPlanetNumber() {
        Goods[] goods = new Goods[3];
        goods[0] = new Goods(GoodsColour.RED);
        goods[1] = new Goods(GoodsColour.YELLOW);
        goods[2] = new Goods(GoodsColour.GREEN);
        Planet s= new Planet(1,goods);
        assertEquals(1,s.getPlanetNumber());

    }

    public void testGetIsOccupied() {
        Goods[] goods = new Goods[3];
        goods[0] = new Goods(GoodsColour.RED);
        goods[1] = new Goods(GoodsColour.YELLOW);
        goods[2] = new Goods(GoodsColour.GREEN);
        Planet s= new Planet(1,goods);
        Planet o= new Planet(2,goods);
        o.changeIsOccupied(true);
        assertFalse(s.getIsOccupied());
        assertTrue(o.getIsOccupied());

    }

    public void testChangeIsOccupied() {
        Goods[] goods = new Goods[3];
        goods[0] = new Goods(GoodsColour.RED);
        goods[1] = new Goods(GoodsColour.YELLOW);
        goods[2] = new Goods(GoodsColour.GREEN);
        Planet s= new Planet(1,goods);
        Planet o= new Planet(2,goods);
        o.changeIsOccupied(true);
        assertFalse(s.getIsOccupied());
        assertTrue(o.getIsOccupied());
        o.changeIsOccupied(false);
        assertFalse(o.getIsOccupied());
    }

    public void testGetGoodsList() {
        Goods[] goods = new Goods[3];
        goods[0] = new Goods(GoodsColour.RED);
        goods[1] = new Goods(GoodsColour.YELLOW);
        goods[2] = new Goods(GoodsColour.GREEN);
        Planet s= new Planet(1,goods);
        assertEquals(goods,s.getGoodsList());
        assertEquals(3,s.getGoodsList().length);
    }
}