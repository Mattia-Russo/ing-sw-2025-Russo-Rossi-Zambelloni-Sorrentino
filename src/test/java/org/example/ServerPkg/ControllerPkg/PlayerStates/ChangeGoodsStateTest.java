package org.example.ServerPkg.ControllerPkg.PlayerStates;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.CardPkg.Planet;
import org.example.ServerPkg.Model.CardPkg.PlanetsCard;
import org.example.ServerPkg.Model.ComponentsPkg.Goods;
import org.example.ServerPkg.Model.ComponentsPkg.GoodsColour;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.util.ArrayList;

public class ChangeGoodsStateTest extends TestCase {

    public void testRemoveGood() {
    }

    public void testAddGood() {
        Player p1 = new Player("a", null);
        Player p2 = new Player( "b", null);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        Game g=new Game(2, 2, 1, new GameController());
        g.getPlayers().addAll(players);
        g.setPlayersShipboard();
        p1.opShip(g);
        p2.opShip(g);
        Goods[] goods1 = new Goods[3];
        goods1[0] = new Goods(GoodsColour.RED);
        goods1[1] = new Goods(GoodsColour.YELLOW);
        Goods[] goods2 = new Goods[3];
        goods1[2] = new Goods(GoodsColour.GREEN);
        Planet planet1 = new Planet(1, goods1);
        Planet planet2 = new Planet(2, goods2);
        ArrayList<Planet> planetList = new ArrayList<>();
        planetList.add(planet1);
        planetList.add(planet2);
        PlanetsCard card= new PlanetsCard(0,3, 5, planetList);
        g.setCard(card);
        card.setCardState(g);

        p1.getState().landOnPlanet(true, 0, p1);

        p1.getState().addGood(new Points(6, 7), 0, p1);

    }

    public void testEndChangeGoods() {
    }

    public void testAbandonGame() {
    }

    public void testDisconnect() {
    }
}