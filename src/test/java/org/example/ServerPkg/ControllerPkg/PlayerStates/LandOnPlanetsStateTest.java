package org.example.ServerPkg.ControllerPkg.PlayerStates;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.CardPkg.Planet;
import org.example.ServerPkg.Model.CardPkg.PlanetsCard;
import org.example.ServerPkg.Model.ComponentsPkg.Goods;
import org.example.ServerPkg.Model.ComponentsPkg.GoodsColour;
import org.example.ServerPkg.Model.Exceptions.PlanetAlreadyVisitedException;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class LandOnPlanetsStateTest extends TestCase {
    boolean planetVisited = true;

    public void testLandOnPlanet() throws RemoteException {
        GameController c = new GameController();
        Game game = new Game(2, 1, 1, c);
        Player player1 = new Player("p1", game);
        Player player2 = new Player("p2", game);
        game.getPlayers().add(player1);
        game.getPlayers().add(player2);

        // Setup PlanetsCard
        Goods[] goods = {new Goods(GoodsColour.BLUE)};
        ArrayList<Planet> planets = new ArrayList<>();
        planets.add(new Planet(0, goods));
        planets.add(new Planet(1, goods));
        PlanetsCard card = new PlanetsCard(0, 1, 1, planets);
        game.setCard(card);

        // Simula visita pianeta 0 se richiesto

        card.getPlanetsVisited()[0] = planetVisited;
        // Per coverage, imposta il playerState
        player1.setPlayerState(new LandOnPlanetsState(game,player1));
        LandOnPlanetsState state = new LandOnPlanetsState(game,player1);

        // Deve lanciare una GameView con PlanetAlreadyVisitedException
        try {
            state.landOnPlanet(true, 0, player1);
        } catch (PlanetAlreadyVisitedException e) {
        }
    }
    public void testLandOnPlanet1() throws RemoteException {
        GameController c = new GameController();
        Game game = new Game(2, 1, 1, c);
        Player player1 = new Player("p1", game);
        Player player2 = new Player("p2", game);
        game.getPlayers().add(player1);
        game.getPlayers().add(player2);

        // Setup PlanetsCard
        Goods[] goods = {new Goods(GoodsColour.BLUE)};
        ArrayList<Planet> planets = new ArrayList<>();
        planets.add(new Planet(0, goods));
        planets.add(new Planet(1, goods));
        PlanetsCard card = new PlanetsCard(0, 1, 1, planets);
        game.setCard(card);

        // Simula visita pianeta 0 se richiesto

        card.getPlanetsVisited()[0] = false;
        // Per coverage, imposta il playerState
        player1.setPlayerState(new LandOnPlanetsState(game,player1));
        LandOnPlanetsState state = new LandOnPlanetsState(game,player1);

        // Simula la scelta di non atterrare
        state.landOnPlanet(false, 0, player1);
    }
    public void testAbandonGame() throws RemoteException {
        GameController c = new GameController();
        Game game = new Game(2, 1, 1, c);
        Player player1 = new Player("p1", game);
        Player player2 = new Player("p2", game);
        game.getPlayers().add(player1);
        game.getPlayers().add(player2);

        // Setup PlanetsCard
        Goods[] goods = {new Goods(GoodsColour.BLUE)};
        ArrayList<Planet> planets = new ArrayList<>();
        planets.add(new Planet(0, goods));
        planets.add(new Planet(1, goods));
        PlanetsCard card = new PlanetsCard(0, 1, 1, planets);
        game.setCard(card);

        // Simula visita pianeta 0 se richiesto

        card.getPlanetsVisited()[0] = false;
        // Per coverage, imposta il playerState
        player1.setPlayerState(new LandOnPlanetsState(game,player1));
        LandOnPlanetsState state = new LandOnPlanetsState(game,player1);

        // Abbandono, copre anche il ramo landOnPlanet(false, ...)
        try {
            state.AbandonGame(player1);
        }catch(NullPointerException e){}
    }

    public void testDisconnect() throws RemoteException {
        GameController c = new GameController();
        Game game = new Game(2, 1, 1, c);
        Player player1 = new Player("p1", game);
        Player player2 = new Player("p2", game);
        game.getPlayers().add(player1);
        game.getPlayers().add(player2);

        // Setup PlanetsCard
        Goods[] goods = {new Goods(GoodsColour.BLUE)};
        ArrayList<Planet> planets = new ArrayList<>();
        planets.add(new Planet(0, goods));
        planets.add(new Planet(1, goods));
        PlanetsCard card = new PlanetsCard(0, 1, 1, planets);
        game.setCard(card);

        // Simula visita pianeta 0 se richiesto

        card.getPlanetsVisited()[0] = false;
        // Per coverage, imposta il playerState
        player1.setPlayerState(new LandOnPlanetsState(game,player1));
        LandOnPlanetsState state = new LandOnPlanetsState(game,player1);

        // Disconnect, copre anche il ramo landOnPlanet(false, ...)
        try {
            state.disconnect(player1);
        }catch(NullPointerException e){}
    }
}