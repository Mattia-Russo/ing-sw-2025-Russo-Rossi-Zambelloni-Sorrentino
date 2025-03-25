package org.example.CardPack;

import junit.framework.TestCase;
import org.example.Model.ComponentsPack.Goods;
import org.example.Model.ComponentsPack.GoodsColour;
import org.example.Model.CardPack.Planet;
import org.example.Model.CardPack.PlanetsCard;

import java.util.ArrayList;
import java.util.List;

public class PlanetsCardTest extends TestCase {

    public void testGetPlanets() {
        List<Goods> goods1 = new ArrayList<>();
        goods1.add(new Goods(GoodsColour.RED));
        goods1.add(new Goods(GoodsColour.YELLOW));
        List<Goods> goods2 = new ArrayList<>();
        goods1.add(new Goods(GoodsColour.GREEN));
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
        List<Goods> goods1 = new ArrayList<>();
        goods1.add(new Goods(GoodsColour.RED));
        goods1.add(new Goods(GoodsColour.YELLOW));
        List<Goods> goods2 = new ArrayList<>();
        goods1.add(new Goods(GoodsColour.GREEN));
        Planet planet1 = new Planet(1, goods1);
        Planet planet2 = new Planet(2, goods2);
        List<Planet> planetList = new ArrayList<Planet>();
        planetList.add(planet1);
        planetList.add(planet2);
        PlanetsCard planetsList= new PlanetsCard(3, 5, planetList);
        assertEquals(5, planetsList.getLostDays());
    }
}