package org.example.ServerPkg.Model.CardPkg;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.ControllerPkg.PlayerStates.*;
import org.example.ServerPkg.Model.ComponentsPkg.*;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;
import org.example.ServerPkg.Model.ShipBoard;
import org.junit.jupiter.api.BeforeEach;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class SmugglersTest extends TestCase {
    private Game game;
    private Smugglers smugglers;
    private  ArrayList<Player> players;
    ShipBoard sp3;

    @BeforeEach
    public void setUp() throws RemoteException {
        Player p1 = new Player("a", null);
        Player p2 = new Player("a", null);

        p1.changePosition(4);
        players = new ArrayList<>();
        players.add(p1);
        players.add(p2);

        game=new Game(2,1,  1, new GameController());
        game.getPlayers().addAll(players);
        game.setPlayersShipboard();
        List<Goods> goods= new ArrayList<Goods>();
        goods.add(new Goods(GoodsColour.RED));
        goods.add(new Goods(GoodsColour.YELLOW));
        goods.add(new Goods(GoodsColour.GREEN));
        smugglers= new Smugglers(0,1,2,2, 5, goods);
        game.setCard(smugglers);

        ShipBoard sp1 = p1.getPlayerShipBoard();
        ShipBoard sp2 = p2.getPlayerShipBoard();


        Cabin c11 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Storage s11 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon1 = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s21 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c21 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c31 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c41 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c51 = new Cabin(0,false, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c61 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c71 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c81 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c91 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e11 = new Engine(0,1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        sp1.placeComponent(6,7, s21);
        sp1.placeComponent(8,7, s11);
        sp1.placeComponent(8,6, cannon1);
        sp1.placeComponent(5,8, c51);
        sp1.placeComponent(6,8, c21);
        sp1.placeComponent(5,9, c41);
        sp1.placeComponent(6,9, c31);
        sp1.placeComponent(8,8, c61);
        sp1.placeComponent(9,8, c71);
        sp1.placeComponent(8,9, e11);
        sp1.placeComponent(9,9, c91);
        sp1.placeComponent(7,8, c81);

        Cabin c12 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL, Connector.DOUBLE});
        BatteryStorage s12 = new BatteryStorage(0,2, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE});
        Cannon cannon2 = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s22 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cannon cannonDouble = new Cannon(0,2, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c32 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c42 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c52 = new Cabin(0,false, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c62 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c72 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c82 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c92 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e12 = new Engine(0,1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        sp2.placeComponent(6,7, s22);
        sp2.placeComponent(8,7, s12);
        sp2.placeComponent(8,6, cannon2);
        sp2.placeComponent(5,8, c52);
        sp2.placeComponent(6,8, cannonDouble);
        sp2.placeComponent(5,9, c42);
        sp2.placeComponent(6,9, c32);
        sp2.placeComponent(8,8, c62);
        sp2.placeComponent(9,8, c72);
        sp2.placeComponent(8,9, e12);
        sp2.placeComponent(9,9, c92);
        sp2.placeComponent(7,8, c82);
    }

    public void testGetCannonPower() {
        List<Goods> goods= new ArrayList<Goods>();
        goods.add(new Goods(GoodsColour.RED));
        goods.add(new Goods(GoodsColour.YELLOW));
        goods.add(new Goods(GoodsColour.GREEN));
        Smugglers s= new Smugglers(0,1,2,10, 5, goods);
        assertEquals(10,s.getCannonPower());
    }

    public void testGetCardLevel() {
        List<Goods> goods= new ArrayList<Goods>();
        goods.add(new Goods(GoodsColour.RED));
        goods.add(new Goods(GoodsColour.YELLOW));
        goods.add(new Goods(GoodsColour.GREEN));
        Smugglers s= new Smugglers(0,1,2,10, 5, goods);
        assertEquals(1,s.getCardLevel());
    }

    public void testGetLostDays() {
        List<Goods> goods= new ArrayList<Goods>();
        goods.add(new Goods(GoodsColour.RED));
        goods.add(new Goods(GoodsColour.YELLOW));
        goods.add(new Goods(GoodsColour.GREEN));
        Smugglers s= new Smugglers(0,1,2,10, 5, goods);
        assertEquals(2,s.getLostDays());
    }

    public void testGetGoodsList() {
        List<Goods> goods= new ArrayList<Goods>();
        goods.add(new Goods(GoodsColour.RED));
        goods.add(new Goods(GoodsColour.YELLOW));
        goods.add(new Goods(GoodsColour.GREEN));
        Smugglers s= new Smugglers(0,1,2,10, 5, goods);
        assertEquals(3,s.getGoodsList().length);
    }

    public void testGetGoodsLost() {
        List<Goods> goods= new ArrayList<Goods>();
        goods.add(new Goods(GoodsColour.RED));
        goods.add(new Goods(GoodsColour.YELLOW));
        goods.add(new Goods(GoodsColour.GREEN));
        Smugglers s= new Smugglers(0,1,2,10, 5, goods);
        assertEquals(5,s.getGoodsLost());
    }

    public void testSetCardState() throws RemoteException {
        smugglers.setCardState(game);
        assertTrue(players.get(0).getState() instanceof ActivateCannonsState);
        assertTrue(players.get(1).getState() instanceof WaitingState);
    }

    public void testPlayCard3Par() throws RemoteException {
        smugglers.setCardState(game);
        smugglers.playCard(game, null, null);
        assertTrue(players.get(0).getState() instanceof RemoveBestGoodsState);

        smugglers.setCardState(game);
        ArrayList<Points> cannons = new ArrayList<Points>();
        cannons.add(new Points(6,8));
        ArrayList<Points> batteries = new ArrayList<Points>();
        batteries.add(new Points(8,7));
        smugglers.playCard(game, cannons, batteries);
        assertTrue(players.get(1).getState() instanceof WinEnemyState);

    }

    public void testPlayCard1Par() throws RemoteException {
        smugglers .setCardState(game);
        smugglers.setAccept(true);
        smugglers.playCard(game);
        assertTrue(game.getPlayers().get(0).getState() instanceof ChangeGoodsState);
    }

    public void testSetAccept() {
        smugglers.setAccept(true);
        assertTrue(smugglers.getAccept());

        smugglers.setAccept(false);
        assertFalse(smugglers.getAccept());
    }

    public void testGetAccept() {
        assertFalse(smugglers.getAccept());
    }

    public void testGetNumGoodsLose() {
        assertEquals(5, smugglers.getNumGoodsLose());
    }
    public void testPlayCard_withInt_setsWaitingStateAndResetsIndex() throws RemoteException {
        // Setup: Game con un solo player
        Game game = new Game(1, 1, 1, new GameController()){
            @Override
            public void Turn(){

            }
        };
        Player player = new Player("TestPlayer", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();

        // Smugglers: settiamo l'indice sul player appena creato
        Smugglers card = new Smugglers(1, 1, 1, 1, 1, new ArrayList<>());
        // Forza l'indice
        card.setPlayersIndex(0);

        // Richiama il metodo
        card.playCard(game, 0);

        // Verifica: lo stato del player è WaitingState
        assertTrue(player.getState() instanceof WaitingState);
    }
}
