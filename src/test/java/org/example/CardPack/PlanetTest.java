package org.example.CardPack;

import junit.framework.TestCase;
import org.example.ComponentsPack.Goods;

import java.util.ArrayList;
import java.util.List;

public class PlanetTest extends TestCase {

    public void testGetPlanetNumber() {
            List<Goods> goods= new ArrayList<Goods>();
            goods.add(new Goods(0));
            goods.add(new Goods(1));
            goods.add(new Goods(2));
            Planet s= new Planet(1,goods);
            assertEquals(1,s.getPlanetNumber());

    }

    public void testGetIsOccupied() {
        List<Goods> goods= new ArrayList<Goods>();
        goods.add(new Goods(0));
        goods.add(new Goods(1));
        goods.add(new Goods(2));
        Planet s= new Planet(1,goods);
        Planet o= new Planet(2,goods);
        o.changeIsOccupied(true);
        assertEquals(false,s.getIsOccupied());
        assertEquals(true, o.getIsOccupied());

    }

    public void testChangeIsOccupied() {
        List<Goods> goods= new ArrayList<Goods>();
        goods.add(new Goods(0));
        goods.add(new Goods(1));
        goods.add(new Goods(2));
        Planet s= new Planet(1,goods);
        Planet o= new Planet(2,goods);
        o.changeIsOccupied(true);
        assertEquals(false,s.getIsOccupied());
        assertEquals(true, o.getIsOccupied());
        o.changeIsOccupied(false);
        assertEquals(false,o.getIsOccupied());
    }

    public void testGetGoodsList() {
        List<Goods> goods= new ArrayList<Goods>();
        goods.add(new Goods(0));
        goods.add(new Goods(1));
        goods.add(new Goods(2));
        Planet s= new Planet(1,goods);
        assertEquals(goods,s.getGoodsList());
        assertEquals(3,s.getGoodsList().size());
    }
}