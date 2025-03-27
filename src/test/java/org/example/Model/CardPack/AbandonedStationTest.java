package org.example.Model.CardPack;

import junit.framework.TestCase;
import org.example.Model.ComponentsPack.Goods;
import org.example.Model.ComponentsPack.GoodsColour;

import java.util.ArrayList;
import java.util.List;

public class AbandonedStationTest extends TestCase {

    public void testGetCardLevel() {
        List<Goods> goods= new ArrayList<Goods>();
        goods.add(new Goods(GoodsColour.RED));
        goods.add(new Goods(GoodsColour.YELLOW));
        goods.add(new Goods(GoodsColour.GREEN));
        AbandonedStation a=new AbandonedStation(1,1, 5, goods);
        assertEquals(1,a.getCardLevel());
    }

    public void testGetNumAstronauts() {
        List<Goods> goods= new ArrayList<Goods>();
        goods.add(new Goods(GoodsColour.RED));
        goods.add(new Goods(GoodsColour.YELLOW));
        goods.add(new Goods(GoodsColour.GREEN));
        AbandonedStation a=new AbandonedStation(1,1, 5, goods);
        assertEquals(5,a.getNumAstronauts());
    }

    public void testGetGoodsList() {
        List<Goods> goods= new ArrayList<Goods>();
        goods.add(new Goods(GoodsColour.RED));
        goods.add(new Goods(GoodsColour.YELLOW));
        goods.add(new Goods(GoodsColour.GREEN));
        AbandonedStation a=new AbandonedStation(1,1, 5, goods);
        assertEquals(goods,a.getGoodsList());
        assertEquals(3,a.getGoodsList().size());
    }

    public void testGetLostDays() {
        List<Goods> goods= new ArrayList<Goods>();
        goods.add(new Goods(GoodsColour.RED));
        goods.add(new Goods(GoodsColour.YELLOW));
        goods.add(new Goods(GoodsColour.GREEN));
        AbandonedStation a=new AbandonedStation(1,1, 5, goods);
        assertEquals(1,a.getLostDays());
    }
}