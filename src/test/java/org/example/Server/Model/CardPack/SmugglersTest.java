package org.example.Server.Model.CardPack;

import junit.framework.TestCase;
import org.example.Server.Model.ComponentsPack.Goods;
import org.example.Server.Model.ComponentsPack.GoodsColour;

import java.util.ArrayList;
import java.util.List;

public class SmugglersTest extends TestCase {

    public void testGetCannonPower() {
        List<Goods> goods= new ArrayList<Goods>();
        goods.add(new Goods(GoodsColour.RED));
        goods.add(new Goods(GoodsColour.YELLOW));
        goods.add(new Goods(GoodsColour.GREEN));
        Smugglers s= new Smugglers(1,2,10, 5, goods);
        assertEquals(10,s.getCannonPower());
    }

    public void testGetCardLevel() {
        List<Goods> goods= new ArrayList<Goods>();
        goods.add(new Goods(GoodsColour.RED));
        goods.add(new Goods(GoodsColour.YELLOW));
        goods.add(new Goods(GoodsColour.GREEN));
        Smugglers s= new Smugglers(1,2,10, 5, goods);
        assertEquals(1,s.getCardLevel());
    }

    public void testGetLostDays() {
        List<Goods> goods= new ArrayList<Goods>();
        goods.add(new Goods(GoodsColour.RED));
        goods.add(new Goods(GoodsColour.YELLOW));
        goods.add(new Goods(GoodsColour.GREEN));
        Smugglers s= new Smugglers(1,2,10, 5, goods);
        assertEquals(2,s.getLostDays());
    }

    public void testGetGoodsWin() {
        List<Goods> goods= new ArrayList<Goods>();
        goods.add(new Goods(GoodsColour.RED));
        goods.add(new Goods(GoodsColour.YELLOW));
        goods.add(new Goods(GoodsColour.GREEN));
        Smugglers s= new Smugglers(1,2,10, 5, goods);
        assertEquals(goods,s.getGoodsWin());
        assertEquals(3,s.getGoodsWin().size());
    }

    public void testGetGoodsLost() {
        List<Goods> goods= new ArrayList<Goods>();
        goods.add(new Goods(GoodsColour.RED));
        goods.add(new Goods(GoodsColour.YELLOW));
        goods.add(new Goods(GoodsColour.GREEN));
        Smugglers s= new Smugglers(1,2,10, 5, goods);
        assertEquals(5,s.getGoodsLost());
    }
}