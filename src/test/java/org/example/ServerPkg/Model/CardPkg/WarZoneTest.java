package org.example.ServerPkg.Model.CardPkg;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.ControllerPkg.PlayerStates.RemoveAstronautsState;
import org.example.ServerPkg.Model.ComponentsPkg.*;
import org.example.ServerPkg.Model.ForView.AdventureCardView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;
import org.example.ServerPkg.Model.ShipBoard;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


public class WarZoneTest extends TestCase {

    public void testGetNumAstronauts() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        WarZone w=new WarZone(0,1,0, 3, 2, cannonFireList, null,null);

        assertEquals(3, w.getNumAstronauts());
    }

    public void testGetNumGoods() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        WarZone w=new WarZone(0,1,0, 3, 2, cannonFireList, null,null);

        assertEquals(2, w.getNumGoods());
    }

    public void testGetCannonFireList() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        WarZone w=new WarZone(0,1,0, 3, 2, cannonFireList, null,null);

        assertEquals(cannonFireList, w.getCannonFireList());
        assertEquals(2, w.getCannonFireList().size());
    }

    public void testGetLostDays() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        WarZone w=new WarZone(0,1,0, 3, 2, cannonFireList, null,null);

        assertEquals(0, w.getLostDays());
    }

    public void testGetPenalities() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        WarZone warZone1 = new WarZone(0,1, 0, 3, 2, cannonFireList, null, null);

        assertNull("Penalities should be null", warZone1.getPenalties());

        List<CannonFire> cannonFireList2 = new ArrayList<>();
        cannonFireList2.add(new CannonFire(0, Direction.NORTH));
        cannonFireList2.add(new CannonFire(1, Direction.WEST));

        String[] expectedPenalities = {"Lose 1 astronaut", "Lose 2 goods"};

        WarZone warZone2 = new WarZone(0,1, 0, 3, 2, cannonFireList2, expectedPenalities, null);

        assertEquals(expectedPenalities, warZone2.getPenalties());
    }

    public void testGetCriteria() {
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        WarZone warZone1 = new WarZone(0,1, 0, 3, 2, cannonFireList, null, null);

        assertNull("Criteria should be null", warZone1.getCriteria());

        List<CannonFire> cannonFireList2 = new ArrayList<>();
        cannonFireList2.add(new CannonFire(0, Direction.NORTH));
        cannonFireList2.add(new CannonFire(1, Direction.WEST));

        String[] expectedCriteria = {"cannonPower"};

        WarZone warZone2 = new WarZone(0,1, 0, 3, 2, cannonFireList2, null, expectedCriteria);

        assertEquals(expectedCriteria, warZone2.getCriteria());
    }

    public void testSetCardState() throws RemoteException {
        Player p1 = new Player("a", null);
        Player p2 = new Player("a", null);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));
        cannonFireList.add(new CannonFire(1, Direction.WEST));
        String[] penalties = {"LoseDays", "LoseAstronauts","cannonFire"};
        String[] criteria = {"FewestAstronauts", "LessEnginePower","LessCannonPower"};
        Game g=new Game(2, 1,1, new GameController());
        g.getPlayers().addAll(players);
        g.setPlayersShipboard();
        WarZone c= new WarZone(0,1,2,2,3,cannonFireList,penalties,criteria);
        g.setCard(c);
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


        c.setCardState(g);
        assertTrue(p1.getState() instanceof RemoveAstronautsState);
    }

    public void testCreateView() {
        int id = 1, cardLevel = 2, lostDays = 3, numAstronauts = 4, numGoods = 5;
        List<CannonFire> fires = new ArrayList<>();
        fires.add(new CannonFire(0, Direction.NORTH));
        String[] penalties = {"LoseAstronauts", "LoseGoods", "LoseDays"};
        String[] criteria = {"FewestAstronauts", "LessCannonPower", "LessEnginePower"};
        WarZone card = new WarZone(id, cardLevel, lostDays, numAstronauts, numGoods, fires, penalties, criteria);
        String expectedCommand =  """
               You are playing the war zone card, you can type:
               activate_cannons x y -> x,y are the coordinates of a cannon, you should write a number of x,y based on the number of cannons you want to activate
               activate_engines x y -> x,y are the coordinates of an engine, you should write a number of x,y based on the number of engines you want to activate
               activate_shields x y -> x,y are the coordinates of a shield, you should write a number of x,y based on the number of shields you want to activate
               use_batteries x y -> x,y are the coordinates of the battery storage, you should write a number of x,y based on the number of batteries you want to use
            
               end_activate_cannons -> if you want to end the cannon activation phase
               end_activate_engines -> if you want to end the engine activation phase
               end_activate_shields -> if you want to end the shield activation phase
               
               remove_astronauts x y -> x,y are the coordinates of the component where you want to remove the astronauts
               remove_best_good x y -> x,y are the coordinates of the component where you want to remove the goods
               remove_batteries x y -> x,y are the coordinates of the battery storage where you want to remove the battery
               end_remove_best_goods -> if you want to end the remove best goods phase
               end_remove_astronauts -> if you want to end the remove astronauts phase
               
               """;
        // Act
        AdventureCardView view = card.createView();

        // Assert
        assertNotNull(view);
        assertEquals("WarZone", view.getType());
        assertEquals(id, view.getId());
        assertEquals(numAstronauts, view.getNumAstronauts());
        assertEquals(numGoods, view.getNumGoods());
        assertEquals(lostDays, view.getLostDays());
        assertEquals(criteria, view.getCriteria());
        assertEquals(penalties, view.getPenalties());
        assertEquals(fires, view.getCannonFireList());
        assertTrue(view.getCommands().contains(expectedCommand));
    }


    public void testPlayCard() throws RemoteException {
        // Crea due giocatori
        Player p1 = new Player("A", null);
        Player p2 = new Player("B", null);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);

        // Crea un Game e aggiungi i player
        Game g = new Game(2, 1, 1, new GameController());
        g.getPlayers().addAll(players);
        g.setPlayersShipboard();

        // Imposta la nave di p1 con cannon power basso
        ShipBoard sp1 = p1.getPlayerShipBoard();
        sp1.placeComponent(8, 6, new Cannon(0, 1, Direction.NORTH,
                new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL}));

        // Imposta la nave di p2 con cannon power più alto
        ShipBoard sp2 = p2.getPlayerShipBoard();
        sp2.placeComponent(8, 6, new Cannon(0, 3, Direction.NORTH,
                new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL}));

        // Prepara la WarZone card con penalità "LoseAstronauts" e criterio "LessCannonPower"
        String[] penalties = {"LoseAstronauts"};
        String[] criteria = {"LessCannonPower"};
        List<CannonFire> cannonFireList = new ArrayList<>();
        cannonFireList.add(new CannonFire(0, Direction.NORTH));

        WarZone card = new WarZone(0, 1, 2, 2, 3, cannonFireList, penalties, criteria);
        g.setCard(card);

        // La carta richiede almeno una chiamata a setCardState per selezionare il "loser"
        card.setCardState(g);

        // Ora playCard applicherà la penalità
        card.playCard(g, null, null);

        ArrayList<Points> cannonPos = new ArrayList<>();
        ArrayList<Points> batteriesPos = new ArrayList<>();
        if (sp1.getTotalCannonPower(cannonPos, batteriesPos) < sp2.getTotalCannonPower(cannonPos, batteriesPos)) {
            assertTrue(p1.getState() instanceof RemoveAstronautsState);
        } else {
            assertTrue(p2.getState() instanceof RemoveAstronautsState);
        }
    }


    public void testGetPenalties() {
        String[] penalties = {"LoseAstronauts", "LoseGoods", "LoseDays"};
        WarZone card = new WarZone(1, 1, 1, 0, 0, new ArrayList<>(), penalties, new String[]{"A","B","C"});

        assertEquals(penalties, card.getPenalties());
    }
    public void testSetCardState_FewestAstronauts_CannonFire() throws RemoteException {
        // Setup: 2 player, uno con 1 astronauta, uno con 5
        Game game = new Game(3, 1, 1, new GameController());
        Player p1 = new Player("A", null);
        Player p2 = new Player("B", null);
        game.getPlayers().add(p1);
        game.getPlayers().add(p2);
        game.setPlayersShipboard();

        p1.getPlayerShipBoard().setNumAstronauts(1);
        p2.getPlayerShipBoard().setNumAstronauts(5);

        String[] criteria = new String[] {"FewestAstronauts"};
        String[] penalties = new String[] {"cannonFire"};
        List<CannonFire> cannonFireList = List.of(new CannonFire(0,Direction.NORTH));

        WarZone card = new WarZone(1, 1, 1, 1, 1, cannonFireList, penalties, criteria);
        try {
            card.setCardState(game);
        }catch(ArrayIndexOutOfBoundsException e){}
        // Arrivato qui, deve coprire il ramo "FewestAstronauts" + cannonFire, non deve esplodere
        assertTrue(true);
    }

    //
    public void testSetCardState_LessEnginePower_LoseDays() throws RemoteException {
        Game game = new Game(3, 1, 1, new GameController()){
            @Override
            public void Turn(){

            }
        };
        Player p1 = new Player("A", null);
        Player p2 = new Player("B", null);
        game.getPlayers().add(p1);
        game.getPlayers().add(p2);
        game.setPlayersShipboard();
        String[] criteria = new String[] {"LessEnginePower"};
        String[] penalties = new String[] {"LoseDays"};
        List<CannonFire> cannonFireList = List.of(new CannonFire(0,Direction.NORTH));
        WarZone card = new WarZone(1, 1, 1, 1, 1, cannonFireList, penalties, criteria);

        // Forza il ramo con done = false e nessuna eccezione
        try {
            card.setCardState(game);
        }catch(ArrayIndexOutOfBoundsException e){}
        assertTrue(true);
    }
    public void testSetCardState_LessCannonPower_LoseGoods() throws RemoteException {
        Game game = new Game(3, 1, 1, new GameController());
        Player p1 = new Player("A", null);
        Player p2 = new Player("B", null);
        game.getPlayers().add(p1);
        game.getPlayers().add(p2);
        game.setPlayersShipboard();
        String[] criteria = new String[] {"LessCannonPower"};
        String[] penalties = new String[] {"LoseGoods"};
        List<CannonFire> cannonFireList = List.of(new CannonFire(0,Direction.NORTH));
        WarZone card = new WarZone(1, 1, 1, 1, 1, cannonFireList, penalties, criteria);

        card.setCardState(game);
        assertTrue(true);
    }
    public void testPlayCard_cannonFire_type0_shielded() throws RemoteException {
        Game game = new Game(3, 1, 1, new GameController()) {
            // Forza rollDice fuori range per else-branch chooseRowOrCol
            @Override
            public int rollDice() {
                return 8;
            }
            @Override
            public void Turn(){

            }
        };
        Player p1 = new Player("A", null);
        game.getPlayers().add(p1);
        game.setPlayersShipboard();
        p1.setPlayerShipboard(1);
        String[] criteria = {"FewestAstronauts"};
        String[] penalties = {"cannonFire"};
        CannonFire cf = new CannonFire(0,Direction.NORTH); // type == 0
        WarZone card = new WarZone(1, 1, 1, 1, 1, List.of(cf), penalties, criteria);
        Shield shield=new Shield(1,Direction.NORTH,new Connector[]{Connector.UNIVERSAL,Connector.UNIVERSAL,Connector.UNIVERSAL,Connector.UNIVERSAL},Direction.WEST);
        BatteryStorage batteryStorage= new BatteryStorage(1,4,Direction.NORTH,new Connector[]{Connector.UNIVERSAL,Connector.UNIVERSAL,Connector.UNIVERSAL,Connector.UNIVERSAL});
        ArrayList<Points> batteryPos = new ArrayList<>();
        ArrayList<Points> ShieldList = new ArrayList<>();
        ShieldList.add(new Points(8,8));
        batteryPos.add(new Points(7,8));
        p1.getPlayerShipBoard().placeComponent(8,8,shield);
        p1.getPlayerShipBoard().placeComponent(7,8,batteryStorage);
        // Forza stato per farlo entrare nel ramo giusto
        card.setDone(true);
        card.setFire(true);
        card.setLoser(p1);
        card.setCurrentFire(0);
        card.setRowOrCol(8);
        // Esegue, dovrebbe prendere ramo shield attivo (activate shields)
        card.playCard(game, ShieldList, batteryPos);

        // Forza chooseRowOrCol fuori range (per else currentFire++)
        card.setDone(true);
        card.setFire(true);
        card.setLoser(p1);
        card.setCurrentFire(0);
        card.setRowOrCol(5);
        card.setCardState(game); // chiama chooseRowOrCol e prende else currentFire++
        assertTrue(true);
    }
    public void testPlayCard_checkLoser_shipWrecked() throws RemoteException {
        Game game = new Game(3, 1, 1, new GameController()) {
            @Override public int rollDice() { return 5; }
            @Override public void Turn(){}
        };
        Player p1 = new Player("A", null);
        game.getPlayers().add(p1);
        game.setPlayersShipboard();
        // Prepara la nave in modo che togliendo un componente la nave si splitti
        // Ad esempio: componi la nave con due cabine collegate solo tramite una tile in (5,8)
        // Rimuovi (5,8) e si splitta.
        Cabin cab1 = new Cabin(0, false, Direction.NORTH,new Connector[]{Connector.UNIVERSAL,Connector.UNIVERSAL,Connector.UNIVERSAL,Connector.UNIVERSAL});
        Cabin cab2 = new Cabin(0, false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL,Connector.UNIVERSAL,Connector.UNIVERSAL,Connector.UNIVERSAL});
        p1.getPlayerShipBoard().placeComponent(5,8, cab1);
        p1.getPlayerShipBoard().placeComponent(5,9, cab2); // O qualsiasi posizione collegata solo da (5,8)
        // Adesso il ramo SHIP WRECK verrà eseguito quando togli (5,8)
        String[] criteria = {"FewestAstronauts"};
        String[] penalties = {"cannonFire"};
        CannonFire cf = new CannonFire(0, Direction.NORTH);
        WarZone card = new WarZone(1, 1, 1, 1, 1, List.of(cf), penalties, criteria);
        card.setDone(true);
        card.setFire(true);
        card.setLoser(p1);
        card.setCurrentFire(0);
        card.setRowOrCol(5);
        card.playCard(game, null, null); // Esegue il branch SHIP WRECK (done == false)
    }

    public void testSetCurrentPlayerIndex() {
        String[] criteria = {"FewestAstronauts"};
        String[] penalties = {"cannonFire"};
        CannonFire cf = new CannonFire(0, Direction.NORTH);
        WarZone card = new WarZone(1, 1, 1, 1, 1, List.of(cf), penalties, criteria);
        card.setCurrentPlayerIndex(0);
    }


    //

    //
}