package org.example.ServerPkg.Model.CardPkg;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.PlayerStates.ChangeGoodsState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.LandOnPlanetsState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.WaitingState;
import org.example.ServerPkg.Model.ComponentsPkg.Goods;
import org.example.ServerPkg.Model.ComponentsPkg.GoodsColour;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

import java.util.ArrayList;

public class PlanetsCardTest extends TestCase {

    public void testGetPlanets() {
        Goods[] goods1 = new Goods[3];
        goods1[0] = new Goods(GoodsColour.RED);
        goods1[1] = new Goods(GoodsColour.YELLOW);
        Goods[] goods2 = new Goods[3];
        goods1[2] = new Goods(GoodsColour.GREEN);
        Planet planet1 = new Planet(1, goods1);
        Planet planet2 = new Planet(2, goods2);
        ArrayList<Planet> planetList = new ArrayList<Planet>();
        planetList.add(planet1);
        planetList.add(planet2);
        PlanetsCard planetsList= new PlanetsCard(0,3, 5, planetList);
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
        ArrayList<Planet> planetList = new ArrayList<Planet>();
        planetList.add(planet1);
        planetList.add(planet2);
        PlanetsCard planetsList= new PlanetsCard(0,3, 5, planetList);
        assertEquals(5, planetsList.getLostDays());
    }

    public void testSetCardState() {
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player(1, "a");
        Player p2 = new Player(2, "b");
        Player p3 = new Player( 3, "c");
        players.add(p1);
        players.add(p2);
        players.add(p3);

        Game game = new Game(3, 2,1, null);


        Goods[] goods1 = new Goods[3];
        goods1[0] = new Goods(GoodsColour.RED);
        goods1[1] = new Goods(GoodsColour.YELLOW);

        Goods[] goods2 = new Goods[3];
        goods1[2] = new Goods(GoodsColour.GREEN);

        Planet planet1 = new Planet(1, goods1);
        Planet planet2 = new Planet(2, goods2);

        ArrayList<Planet> planets = new ArrayList<Planet>();
        planets.add(planet1);
        planets.add(planet2);

        PlanetsCard card = new PlanetsCard(0,2, 5, planets);
        game.setCard(card);

        card.setCardState(game);
        assertTrue(p1.getState() instanceof LandOnPlanetsState);
        assertTrue(p2.getState() instanceof WaitingState);
        assertTrue(p3.getState() instanceof WaitingState);
        p1.setPlayerState(new WaitingState());

        card.setCardState(game);
        assertTrue(p1.getState() instanceof WaitingState);
        assertTrue(p2.getState() instanceof LandOnPlanetsState);
        assertTrue(p3.getState() instanceof WaitingState);
        p2.setPlayerState(new WaitingState());

        card.setCardState(game);
        assertTrue(p1.getState() instanceof WaitingState);
        assertTrue(p2.getState() instanceof WaitingState);
        assertTrue(p3.getState() instanceof LandOnPlanetsState);
        p3.setPlayerState(new WaitingState());

        assertTrue(p1.getState() instanceof WaitingState);
        assertTrue(p2.getState() instanceof WaitingState);
        assertTrue(p3.getState() instanceof WaitingState);
    }

    public void testPlayCard() {
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player(1, "a");
        Player p2 = new Player(2, "b");
        Player p3 = new Player( 3, "c");
        players.add(p1);
        players.add(p2);
        players.add(p3);

        Game game = new Game(3, 2, 1, null);


        Goods[] goods1 = new Goods[3];
        goods1[0] = new Goods(GoodsColour.RED);
        goods1[1] = new Goods(GoodsColour.YELLOW);

        Goods[] goods2 = new Goods[3];
        goods1[2] = new Goods(GoodsColour.GREEN);

        Planet planet1 = new Planet(1, goods1);
        Planet planet2 = new Planet(2, goods2);

        ArrayList<Planet> planets = new ArrayList<Planet>();
        planets.add(planet1);
        planets.add(planet2);

        PlanetsCard card = new PlanetsCard(0,2, 5, planets);
        game.setCard(card);

        card.setCardState(game);

        card.playCard(game, 0);
        assertTrue(p1.isOnPlanet());
        assertEquals(0, card.getCurrentPlanetIndex());
        assertTrue(card.getPlanetsVisited()[0]);
        assertTrue(p1.getState() instanceof ChangeGoodsState);

        card.setChangeGoodsFlag(false);

        card.playCard(game, 0);
        assertFalse(p1.isOnPlanet());
        assertEquals(-5, p1.getPosition());
        assertTrue(p1.getState() instanceof WaitingState);
        assertTrue(card.getChangeGoodsFlag());


    }

    public void testSetChangeGoodsFlag() {
        Goods[] goods1 = new Goods[3];
        goods1[0] = new Goods(GoodsColour.RED);
        goods1[1] = new Goods(GoodsColour.YELLOW);

        Goods[] goods2 = new Goods[3];
        goods1[2] = new Goods(GoodsColour.GREEN);

        Planet planet1 = new Planet(1, goods1);
        Planet planet2 = new Planet(2, goods2);

        ArrayList<Planet> planets = new ArrayList<Planet>();
        planets.add(planet1);
        planets.add(planet2);

        PlanetsCard card = new PlanetsCard(0,2, 5, planets);
        assertTrue(card.getChangeGoodsFlag());
        card.setChangeGoodsFlag(false);
        assertFalse(card.getChangeGoodsFlag());
    }

    public void testGetGoodsList() {
        Goods[] goods1 = new Goods[3];
        goods1[0] = new Goods(GoodsColour.RED);
        goods1[1] = new Goods(GoodsColour.YELLOW);

        Goods[] goods2 = new Goods[3];
        goods1[2] = new Goods(GoodsColour.GREEN);

        Planet planet1 = new Planet(1, goods1);
        Planet planet2 = new Planet(2, goods2);

        ArrayList<Planet> planets = new ArrayList<Planet>();
        planets.add(planet1);
        planets.add(planet2);

        PlanetsCard card = new PlanetsCard(0,2, 5, planets);

        card.setPlanetIndex(0);
        assertEquals(goods1, card.getGoodsList());
        card.setPlanetIndex(1);
        assertEquals(goods2, card.getGoodsList());
    }

    public void testIsPlanetsVisited() {
        Goods[] goods1 = new Goods[3];
        goods1[0] = new Goods(GoodsColour.RED);
        goods1[1] = new Goods(GoodsColour.YELLOW);

        Goods[] goods2 = new Goods[3];
        goods1[2] = new Goods(GoodsColour.GREEN);

        Planet planet1 = new Planet(1, goods1);
        Planet planet2 = new Planet(2, goods2);

        ArrayList<Planet> planets = new ArrayList<Planet>();
        planets.add(planet1);
        planets.add(planet2);

        PlanetsCard card = new PlanetsCard(0,2, 5, planets);

        for (int i =0; i<card.getPlanets().size(); i++) {
            assertFalse(card.getPlanetsVisited()[i]);
        }
    }

    public void testGetCurrentPlayerIndex() {
        Goods[] goods1 = new Goods[3];
        goods1[0] = new Goods(GoodsColour.RED);
        goods1[1] = new Goods(GoodsColour.YELLOW);

        Goods[] goods2 = new Goods[3];
        goods1[2] = new Goods(GoodsColour.GREEN);

        Planet planet1 = new Planet(1, goods1);
        Planet planet2 = new Planet(2, goods2);

        ArrayList<Planet> planets = new ArrayList<Planet>();
        planets.add(planet1);
        planets.add(planet2);

        PlanetsCard card = new PlanetsCard(0,2, 5, planets);

        assertEquals(-1,card.getCurrentPlayerIndex());
    }
}