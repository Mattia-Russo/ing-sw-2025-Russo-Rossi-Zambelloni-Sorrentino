package org.example.ServerPkg.ControllerPkg.PlayerStates;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.CardPkg.AdventureCard;
import org.example.ServerPkg.Model.CardPkg.OpenSpace;
import org.example.ServerPkg.Model.CardPkg.Planet;
import org.example.ServerPkg.Model.CardPkg.PlanetsCard;
import org.example.ServerPkg.Model.ComponentsPkg.*;
import org.example.ServerPkg.Model.Exceptions.NotStorageException;
import org.example.ServerPkg.Model.Exceptions.RedGoodsNotAllowedException;
import org.example.ServerPkg.Model.Exceptions.StorageFullException;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;
import org.example.ServerPkg.Model.ShipBoard;


import java.rmi.RemoteException;
import java.util.ArrayList;

public class ChangeGoodsStateTest extends TestCase {

    public void testRemoveGood() throws RemoteException {
        GameController ctrl = new GameController();
        Game game = new Game(2, 1, 1, ctrl);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        ShipBoard sb = player.getPlayerShipBoard();

        // Metti uno storage 3-slot in posizione (6,6)
        Storage storage = new Storage(0, true, Direction.NORTH, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL
        }, 3);
        sb.placeComponent(6, 6, storage);

        // Aggiungi dei goods nello storage
        storage.addGood(new Goods(GoodsColour.BLUE));
        storage.addGood(new Goods(GoodsColour.GREEN));
        storage.addGood(new Goods(GoodsColour.RED));
        ChangeGoodsState state = new ChangeGoodsState(game,player);

        // Rimuovi good valido (indice 1 su storage 3-slot)
        state.removeGood(new Points(6, 6), 1, player);
    }
    public void testRemoveGood_IndexTooHigh() throws RemoteException {
        GameController ctrl = new GameController();
        Game game = new Game(2, 1, 1, ctrl);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        ShipBoard sb = player.getPlayerShipBoard();

        // Metti uno storage 3-slot in posizione (6,6)
        Storage storage = new Storage(0, true, Direction.NORTH, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL
        }, 3);
        sb.placeComponent(6, 6, storage);

        // Aggiungi dei goods nello storage
        storage.addGood(new Goods(GoodsColour.BLUE));
        storage.addGood(new Goods(GoodsColour.GREEN));
        storage.addGood(new Goods(GoodsColour.RED));

        ChangeGoodsState state = new ChangeGoodsState(game,player);

        // Indice out of bounds
        state.removeGood(new Points(6, 6), 10, player);
    }
    public void testRemoveGood_NoComponent() throws RemoteException {
        GameController ctrl = new GameController();
        Game game = new Game(2, 1, 1, ctrl);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        ShipBoard sb = player.getPlayerShipBoard();

        // Metti uno storage 3-slot in posizione (6,6)
        Storage storage = new Storage(0, true, Direction.NORTH, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL
        }, 3);
        sb.placeComponent(6, 6, storage);

        // Aggiungi dei goods nello storage
        storage.addGood(new Goods(GoodsColour.BLUE));
        storage.addGood(new Goods(GoodsColour.GREEN));
        storage.addGood(new Goods(GoodsColour.RED));
        ChangeGoodsState state = new ChangeGoodsState(game,player);

        // Nessun componente in questa posizione
        state.removeGood(new Points(7, 7), 0, player);
    }

    public void testAddGood() throws RemoteException {
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
    public void testAddGood1() throws RemoteException {
        Player p1 = new Player("a", null);
        Player p2 = new Player( "b", null);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        Game g=new Game(2, 2, 1, new GameController());
        g.getPlayers().addAll(players);
        g.setPlayersShipboard();
        p1.setPlayerShipboard(1);
        Storage storage = new Storage(0, false, Direction.NORTH, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL
        }, 3);
        p1.getPlayerShipBoard().placeComponent(6, 7, storage);
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
        try {
            p1.getState().addGood(new Points(6, 7), 0, p1);
        }catch(RedGoodsNotAllowedException e){}

    }
    public void testAddGood2() throws RemoteException {
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
        try {
            p1.getState().addGood(new Points(6, 7), 7, p1);
        }catch(Exception e){}

    }

    public void testAddGood_NotStorage() throws RemoteException {
        GameController ctrl = new GameController();
        Game game = new Game(2, 1, 1, ctrl);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        ShipBoard sb = player.getPlayerShipBoard();

        // Storage 3-slot in (6,6)
        Storage storage = new Storage(0, true, Direction.NORTH, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL
        }, 3);
        sb.placeComponent(6, 6, storage);

        storage.addGood(new Goods(GoodsColour.BLUE));
        storage.addGood(new Goods(GoodsColour.GREEN));
        storage.addGood(new Goods(GoodsColour.RED));
        ChangeGoodsState state = new ChangeGoodsState(game,player);

        // Indice out of bounds
        state.removeGood(new Points(6, 6), 10, player);
    }
    public void testAddGood_IndexTooHigh() throws RemoteException {
        GameController ctrl = new GameController();
        Game game = new Game(2, 1, 1, ctrl);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        ShipBoard sb = player.getPlayerShipBoard();

        // Storage 3-slot in (6,6)
        Storage storage = new Storage(0, true, Direction.NORTH, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL
        }, 3);
        sb.placeComponent(6, 6, storage);

        storage.addGood(new Goods(GoodsColour.BLUE));
        storage.addGood(new Goods(GoodsColour.GREEN));
        storage.addGood(new Goods(GoodsColour.RED));
        ChangeGoodsState state = new ChangeGoodsState(game,player);

        // Solo 1 good nel pianeta
        Goods[] goods = {new Goods(GoodsColour.BLUE)};
        ArrayList<Planet> planets = new ArrayList<>();
        planets.add(new Planet(0, goods));
        PlanetsCard card = new PlanetsCard(0, 1, 1, planets);
        game.setCard(card);

        // Chiedi il good 5 che non esiste!
        try {
            state.addGood(new Points(6, 6), 5, player);
        }catch(Exception e){}
    }
    public void testAddGood_NotStorageException() throws RemoteException {
        GameController ctrl = new GameController();
        Game game = new Game(2, 1, 1, ctrl);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);

        // Metto un Tubes al posto di uno storage
        Tubes tubes = new Tubes(0, Direction.NORTH, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL
        });
        player.getPlayerShipBoard().placeComponent(6, 6, tubes);

        Goods[] goods = {new Goods(GoodsColour.YELLOW)};
        ArrayList<Planet> planets = new ArrayList<>();
        planets.add(new Planet(0, goods));
        PlanetsCard card = new PlanetsCard(0, 1, 1, planets);
        game.setCard(card);

        ChangeGoodsState state = new ChangeGoodsState(game,player);
        try {
            state.addGood(new Points(6, 6), 0, player);
        }catch(NotStorageException e){}
        // Stessa cosa: se vuoi, assert sull'eccezione in GameView
    }
    public void testAddGood_NoComponent() throws RemoteException {
        GameController ctrl = new GameController();
        Game game = new Game(2, 1, 1, ctrl);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        ShipBoard sb = player.getPlayerShipBoard();

        // Storage 3-slot in (6,6)
        Storage storage = new Storage(0, true, Direction.NORTH, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL
        }, 3);
        sb.placeComponent(6, 6, storage);

        storage.addGood(new Goods(GoodsColour.BLUE));
        storage.addGood(new Goods(GoodsColour.GREEN));
        storage.addGood(new Goods(GoodsColour.RED));
        ChangeGoodsState state = new ChangeGoodsState(game,player);

        Goods[] goods = {new Goods(GoodsColour.YELLOW)};
        ArrayList<Planet> planets = new ArrayList<>();
        planets.add(new Planet(0, goods));
        PlanetsCard card = new PlanetsCard(0, 1, 1, planets);
        game.setCard(card);

        state.addGood(new Points(7, 7), 0, player);
    }
    public void testAddGood_StorageFullOrRedNotAllowed() throws RemoteException {
        GameController ctrl = new GameController();
        Game game = new Game(2, 1, 1, ctrl);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        ShipBoard sb = player.getPlayerShipBoard();

        // Storage 3-slot in (6,6)
        Storage storage = new Storage(0, true, Direction.NORTH, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL
        }, 3);
        sb.placeComponent(6, 6, storage);

        storage.addGood(new Goods(GoodsColour.BLUE));
        storage.addGood(new Goods(GoodsColour.GREEN));
        storage.addGood(new Goods(GoodsColour.RED));

        // Storage con capacity 1 già pieno
        Storage storage1 = new Storage(0, true, Direction.NORTH, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL
        }, 1);
        sb.placeComponent(7, 7, storage1);
        storage1.addGood(new Goods(GoodsColour.BLUE));

        Goods[] goods = {new Goods(GoodsColour.RED)};
        ArrayList<Planet> planets = new ArrayList<>();
        planets.add(new Planet(0, goods));
        PlanetsCard card = new PlanetsCard(0, 1, 1, planets);
        game.setCard(card);

        ChangeGoodsState state = new ChangeGoodsState(game,player);
        try {
            state.addGood(new Points(7, 7), 0, player);
        }catch (Exception e){}
    }

    public void testEndChangeGoods() throws RemoteException {
        GameController ctrl = new GameController();
        Game game = new Game(2, 1, 1, ctrl);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        ShipBoard sb = player.getPlayerShipBoard();

        // Storage 3-slot in (6,6)
        Storage storage = new Storage(0, true, Direction.NORTH, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL
        }, 3);
        sb.placeComponent(6, 6, storage);

        storage.addGood(new Goods(GoodsColour.BLUE));
        storage.addGood(new Goods(GoodsColour.GREEN));
        storage.addGood(new Goods(GoodsColour.RED));
        ChangeGoodsState state = new ChangeGoodsState(game,player);

        Goods[] goods = {new Goods(GoodsColour.BLUE)};
        ArrayList<Planet> planets = new ArrayList<>();
        planets.add(new Planet(0, goods));
        PlanetsCard card = new PlanetsCard(0, 1, 1, planets);
        game.setCard(card);
        try {
            state.endChangeGoods(player);
        }catch (Exception e){}
    }

    public void testAbandonGame() throws RemoteException {
        GameController ctrl = new GameController();
        Game game = new Game(2, 1, 1, ctrl);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        ShipBoard sb = player.getPlayerShipBoard();
        game.setCard(new OpenSpace(1,1,1));
        // Storage 3-slot in (6,6)
        Storage storage = new Storage(0, true, Direction.NORTH, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL
        }, 3);
        sb.placeComponent(6, 6, storage);

        storage.addGood(new Goods(GoodsColour.BLUE));
        storage.addGood(new Goods(GoodsColour.GREEN));
        storage.addGood(new Goods(GoodsColour.RED));
        ChangeGoodsState state = new ChangeGoodsState(game,player);

        state.AbandonGame(player);
        assertTrue(player.isAbandoned());
    }

    public void testDisconnect() throws RemoteException {
        GameController ctrl = new GameController();
        Game game = new Game(2, 1, 1, ctrl);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        ShipBoard sb = player.getPlayerShipBoard();
        game.setCard(new OpenSpace(1,1,1));
        // Storage 3-slot in (6,6)
        Storage storage = new Storage(0, true, Direction.NORTH, new Connector[]{
                Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL
        }, 3);
        sb.placeComponent(6, 6, storage);

        storage.addGood(new Goods(GoodsColour.BLUE));
        storage.addGood(new Goods(GoodsColour.GREEN));
        storage.addGood(new Goods(GoodsColour.RED));
        ChangeGoodsState state = new ChangeGoodsState(game,player);

        state.disconnect(player);
    }

}