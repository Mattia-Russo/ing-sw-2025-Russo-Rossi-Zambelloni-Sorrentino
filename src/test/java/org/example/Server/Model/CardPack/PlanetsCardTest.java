package org.example.Server.Model.CardPack;

import junit.framework.TestCase;
import org.example.Server.Model.ComponentsPack.Goods;
import org.example.Server.Model.ComponentsPack.GoodsColour;

import java.util.ArrayList;
import java.util.List;

public class PlanetsCardTest extends TestCase {

    public void testGetPlanets() {
        Goods[] goods1 = new Goods[3];
        goods1[0] = new Goods(GoodsColour.RED);
        goods1[1] = new Goods(GoodsColour.YELLOW);
        Goods[] goods2 = new Goods[3];
        goods1[2] = new Goods(GoodsColour.GREEN);
        Planet planet1 = new Planet(1, goods1);
        Planet planet2 = new Planet(2, goods2);
        List<Planet> planetList = new ArrayList<Planet>();
        planetList.add(planet1);
        planetList.add(planet2);
        PlanetsCard planetsList= new PlanetsCard(3, 5, planetList);
        assertEquals(planetList, planetsList.getPlanets());
        assertEquals(2, planetsList.getPlanets().size());
    }
    public void testGetLostDays() {
        Goods[] goods1 = new Goods[3];
        goods1[0] = new Goods(GoodsColour.RED);
        goods1[1] = new Goods(GoodsColour.YELLOW);
        Goods[] goods2 = new Goods[3];
        goods1[2] = new Goods(GoodsColour.GREEN);
        Planet planet1 = new Planet(1, goods1);
        Planet planet2 = new Planet(2, goods2);
        List<Planet> planetList = new ArrayList<Planet>();
        planetList.add(planet1);
        planetList.add(planet2);
        PlanetsCard planetsList= new PlanetsCard(3, 5, planetList);
        assertEquals(5, planetsList.getLostDays());
    }
}