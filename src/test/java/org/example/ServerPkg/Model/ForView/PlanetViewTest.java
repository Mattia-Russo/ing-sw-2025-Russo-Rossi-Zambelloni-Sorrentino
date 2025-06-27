package org.example.ServerPkg.Model.ForView;

import junit.framework.TestCase;
import org.example.ServerPkg.Model.CardPkg.Planet;
import org.example.ServerPkg.Model.ComponentsPkg.Goods;
import org.example.ServerPkg.Model.ComponentsPkg.GoodsColour;

import java.util.List;

public class PlanetViewTest extends TestCase {

    public void testGetPlanetNumber() {
        Goods[] goods = new Goods[] {
                new Goods(GoodsColour.YELLOW),
                new Goods(GoodsColour.GREEN)
        };
        Planet planet = new Planet(7, goods);

        PlanetView planetView = new PlanetView(planet);

        assertEquals(7, planetView.getPlanetNumber());
    }

    public void testGetGoods() {
        Goods[] goods = new Goods[] {
                new Goods(GoodsColour.YELLOW),
                new Goods(GoodsColour.GREEN),
                null // test anche che venga ignorato il null
        };
        Planet planet = new Planet(3, goods);

        PlanetView planetView = new PlanetView(planet);
        List<GoodsView> goodsViewList = planetView.getGoods();

        assertEquals(2, goodsViewList.size());
        assertEquals(GoodsColour.YELLOW, goodsViewList.get(0).getColour());
        assertEquals(GoodsColour.GREEN, goodsViewList.get(1).getColour());
    }
}