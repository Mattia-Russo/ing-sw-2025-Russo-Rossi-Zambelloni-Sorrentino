package org.example.ServerPkg.Model.ComponentsPkg;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.ForView.ComponentsView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.ShipBoard;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

public class CabinTest extends TestCase {

    public void testGetNumAstronauts() {
        boolean[][] mat = new boolean[2][2];
        mat[0][0] =true; mat[0][1]=true;
        mat[1][0] =true; mat[1][1]=true;
        ShipBoard board = new ShipBoard(mat, 2, 2);
        // Crea una Cabin e la piazza a (5,4)
        Cabin cabin = new Cabin(1, false, Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        });
        board.placeComponent(4, 5, cabin);
        // Quando viene piazzata la cabina, vengono aggiunti 2 astronauti (vedi metodo place)
        assertEquals("Dopo il piazzamento la cabina deve avere 2 astronauti", 2, cabin.getNumAstronauts());
        // Rimuovi 1 astronauta
        cabin.changeNumAstronauts(-1, board);
        assertEquals("Dopo aver rimosso 1 astronauta, deve essere 1", 1, cabin.getNumAstronauts());
        // Rimuovi ancora 1 astronauta
        cabin.changeNumAstronauts(-1, board);
        assertEquals("Dopo aver rimosso entrambi gli astronauti, deve essere 0", 0, cabin.getNumAstronauts());
        // Aggiungi di nuovo 2 astronauti (max 2)
        cabin.changeNumAstronauts(2, board);
        assertEquals("Dopo aver aggiunto 2 astronauti, deve essere 2", 2, cabin.getNumAstronauts());
    }

    public void testGetAlien() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++) {
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Cabin c = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        Alien a= new Alien(AlienColour.BROWN);
        LifeSupportSystem l=new LifeSupportSystem(0,AlienColour.BROWN,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(6,8, c);
        s.placeComponent(6,9, l);
        l.addLifeSupport(c);
        c.addAlien(a, s);
        assertEquals(a, c.getAlien());
    }

    public void testGetWithLifeSupport() {
        Cabin c = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        LifeSupportSystem l=new LifeSupportSystem(0,AlienColour.BROWN,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        l.addLifeSupport(c);
        assertTrue(c.getWithLifeSupport());
    }

    public void testChangeWithLifeSupport() {
        Cabin c = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertFalse(c.getWithLifeSupport());
        c.changeWithLifeSupport(true);
        assertTrue(c.getWithLifeSupport());
    }

    public void testGetIsCentral() {
        Cabin c = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertTrue(c.getIsCentral());
    }

    public void testChangeNumAstronauts() {
        boolean[][] mat = new boolean[2][2];
        mat[0][0]=true;
        mat[0][1]=true;
        mat[1][0]=true;
        mat[1][1] =true;
        ShipBoard board = new ShipBoard(mat, 2, 2);

        Cabin cabin = new Cabin(1, false, Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        });
        board.placeComponent(4, 5, cabin);
        // Dopo il piazzamento, la cabina ha 2 astronauti
        assertEquals(2, cabin.getNumAstronauts());
        // Rimuovo 1 astronauta
        cabin.changeNumAstronauts(-1, board);
        assertEquals(1, cabin.getNumAstronauts());
        // Provo a rimuovere 2 astronauti (deve lanciare UnderloadedCapacityException)
        boolean threw = false;
        try {
            cabin.changeNumAstronauts(-2, board);
        } catch (UnderloadedCapacityException ex) {
            threw = true;
        }
        assertTrue("Deve lanciare UnderloadedCapacityException se scende sotto 0", threw);
        // Riporto a 2 astronauti
        cabin.changeNumAstronauts(1, board);
        assertEquals(2, cabin.getNumAstronauts());
        // Provo ad aggiungerne ancora 1 (deve lanciare OverloadedCapacityException)
        threw = false;
        try {
            cabin.changeNumAstronauts(1, board);
        } catch (OverloadedCapacityException ex) {
            threw = true;
        }
        assertTrue("Deve lanciare OverloadedCapacityException se supera 2", threw);
    }

    public void testAddLifeSupport() {
        Cabin c = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        LifeSupportSystem l=new LifeSupportSystem(0,AlienColour.BROWN,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        c.addLifeSupportList(l);
        assertEquals(1, c.getLifeSupportSystemArrayList().size());
    }

    public void testRemoveLifeSupport() {
        Cabin c = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        LifeSupportSystem l=new LifeSupportSystem(0,AlienColour.BROWN,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        c.addLifeSupportList(l);
        assertEquals(1, c.getLifeSupportSystemArrayList().size());
        c.removeLifeSupport(l);
        assertEquals(0, c.getLifeSupportSystemArrayList().size());
    }

    public void testgetLifeSupportSystemArrayList() {
        Cabin c = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        LifeSupportSystem l=new LifeSupportSystem(0,AlienColour.BROWN,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        c.addLifeSupportList(l);
        assertEquals(l, c.getLifeSupportSystemArrayList().get(0));
    }

    public void testaddAlien(){
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++) {
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);


        Cabin c = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        Alien a= new Alien(AlienColour.BROWN);
        LifeSupportSystem l=new LifeSupportSystem(0,AlienColour.BROWN,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(6,8, c);
        s.placeComponent(6,9, l);
        l.addLifeSupport(c);
        c.addAlien(a, s);
        assertEquals(a, c.getAlien());

        try{
            c.addAlien(new Alien(AlienColour.BROWN),s);
            fail("Expected AlredyAlienException");
        }catch (AlreadyAlienException e){

        }
        Cabin c2 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(9,9,c2);
        try {
            c2.addAlien(new Alien(AlienColour.BROWN), s);
            fail("Expected WithoutLifeSupportException");
        } catch (WithoutLifeSupportException ex) {

        }
        Cabin c3 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        Alien a3= new Alien(AlienColour.BROWN);
        LifeSupportSystem l3=new LifeSupportSystem(0,AlienColour.PURPLE,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(6,6, c3);
        s.placeComponent(6,7, l3);
        l.addLifeSupport(c);
        try {
            c3.addAlien(a3, s);
            fail("Expected DifferentLifeSupportColourException");
        } catch (DifferentLifeSupportColourException ex) {
            // ok!
        }
    }

    public void testremoveAlien(){
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++) {
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Cabin c = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        Alien a= new Alien(AlienColour.BROWN);
        LifeSupportSystem l=new LifeSupportSystem(0,AlienColour.BROWN,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(6,8, c);
        s.placeComponent(6,9, l);
        l.addLifeSupport(c);
        c.addAlien(a, s);
        assertEquals(a, c.getAlien());
        c.removeAlien(s);
        assertNull(c.getAlien());
    }

    public void testRemove(){
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++) {
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Cabin c = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(5,7,c);
        c.remove(s);
        assertEquals(0, s.getTotalAstronauts());

        Cabin c1 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        LifeSupportSystem l3=new LifeSupportSystem(0,AlienColour.BROWN,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        c1.changeNumAstronauts(2,s);
        s.placeComponent(6,6,c1);
        s.placeComponent(6,7, l3);
        l3.addLifeSupport(c1);
        c1.addAlien(new Alien(AlienColour.BROWN), s);
        c1.remove(s);
        assertEquals(2, s.getTotalAstronauts());
    }

    public void testPlace(){
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++) {
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        LifeSupportSystem l=new LifeSupportSystem(0,AlienColour.BROWN,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(6,7,l);
        Cabin c = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(5,7,c);
        assertEquals(c, s.getComponent(7,5));
        assertTrue(c.getWithLifeSupport());
    }

    public void testaddCabin(){
        LifeSupportSystem l=new LifeSupportSystem(0,AlienColour.BROWN,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        Cabin c = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        c.addCabin(l);
        assertTrue(c.getWithLifeSupport());
    }

    public void testremoveCabin(){
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++) {
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        LifeSupportSystem l=new LifeSupportSystem(0,AlienColour.BROWN,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(6,7,l);
        Cabin c = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(5,7,c);
        assertTrue(c.getWithLifeSupport());
        Alien a = new Alien(AlienColour.BROWN);
        c.addAlien(a, s);
        c.removeCabin(l, s);
        assertNull(c.getAlien());
        assertFalse(c.getWithLifeSupport());
    }
    public void testRemoveCabinWithMultipleLifeSupportSameColour() {
        boolean[][] matrix = new boolean[5][7];
        for(int i=0; i<5; i++) for(int j=0; j<7; j++) matrix[i][j]=true;
        ShipBoard s = new ShipBoard(matrix, 7, 5);

        // 2 supporti vitali brown
        LifeSupportSystem l1 = new LifeSupportSystem(0, AlienColour.BROWN, Direction.NORTH, new Connector[4]);
        LifeSupportSystem l2 = new LifeSupportSystem(0, AlienColour.BROWN, Direction.NORTH, new Connector[4]);
        s.placeComponent(6,7, l1);
        s.placeComponent(8,7, l2);
        Cabin c = new Cabin(0, false, Direction.NORTH, new Connector[4]);
        s.placeComponent(7,7, c);

        c.changeWithLifeSupport(true);
        c.getLifeSupportSystemArrayList().add(l1);
        c.getLifeSupportSystemArrayList().add(l2);

        Alien a = new Alien(AlienColour.BROWN);
        c.addAlien(a, s);

        c.removeCabin(l1, s); // ne resta uno brown!

        assertEquals(a, c.getAlien());
        assertTrue(c.getWithLifeSupport());
        assertTrue(c.getLifeSupportSystemArrayList().contains(l2));
    }
    public void testRemoveCabinDifferentColour() {
        boolean[][] matrix = new boolean[5][7];
        for(int i=0; i<5; i++) for(int j=0; j<7; j++) matrix[i][j]=true;
        ShipBoard s = new ShipBoard(matrix, 7, 5);

        LifeSupportSystem l1 = new LifeSupportSystem(0, AlienColour.BROWN, Direction.NORTH, new Connector[4]);
        LifeSupportSystem l2 = new LifeSupportSystem(0, AlienColour.PURPLE, Direction.NORTH, new Connector[4]);
        s.placeComponent(6,6, l1);
        s.placeComponent(6,7, l2);
        Cabin c = new Cabin(0, false, Direction.NORTH, new Connector[4]);
        s.placeComponent(5,6, c);

        c.changeWithLifeSupport(true);
        c.getLifeSupportSystemArrayList().add(l1);
        c.getLifeSupportSystemArrayList().add(l2);

        Alien a = new Alien(AlienColour.BROWN);
        c.addAlien(a, s);

        c.removeCabin(l2, s); // rimuove purple, alien brown resta

        assertEquals(a, c.getAlien());
        assertTrue(c.getWithLifeSupport());
        assertFalse(c.getLifeSupportSystemArrayList().contains(l2));
        assertTrue(c.getLifeSupportSystemArrayList().contains(l1));
    }
    public void testRemoveCabinNoAlien() {
        boolean[][] matrix = new boolean[5][7];
        for(int i=0; i<5; i++) for(int j=0; j<7; j++) matrix[i][j]=true;
        ShipBoard s = new ShipBoard(matrix, 7, 5);

        LifeSupportSystem l1 = new LifeSupportSystem(0, AlienColour.BROWN, Direction.NORTH, new Connector[4]);
        s.placeComponent(7,7, l1);
        Cabin c = new Cabin(0, false, Direction.NORTH, new Connector[4]);
        s.placeComponent(6,7, c);

        c.changeWithLifeSupport(true);
        c.getLifeSupportSystemArrayList().add(l1);

        c.removeCabin(l1, s);

        assertNull(c.getAlien());


    }

    public void testHasAliens(){
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++) {
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        LifeSupportSystem l=new LifeSupportSystem(0,AlienColour.BROWN,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(6,7,l);
        Cabin c = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(5,7,c);
        assertNull(c.hasAlien());
        Alien a = new Alien(AlienColour.BROWN);
        c.addAlien(a, s);
        assertEquals(a, c.getAlien());
    }

    public void testAddEpidemicCabin() throws RemoteException {
        Player p1 = new Player("a", null);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        Game g = new Game(1,1, 1, new GameController());
        g.getPlayers().addAll(players);
        g.setPlayersShipboard();

        Cabin c1 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        // Piazzo c1 nella shipboard (es: posizione 6,6)
        p1.getPlayerShipBoard().placeComponent(8, 8, c1);

        // Aggiungo altre cabine collegate, se vuoi testare cluster
        Cabin c2 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        p1.getPlayerShipBoard().placeComponent(8, 9, c2);
        // ...aggiungi le altre a piacere

        // Prepara la lista cluster e la matrice visited
        ArrayList<Cabin> cluster = new ArrayList<>();
        boolean[][] visited = new boolean[5][7];

        // Chiamata corretta: usa la posizione REALE di c1
        c1.addEpidemicCabin(c1.getPosX(), c1.getPosY(), cluster, visited, 7, 5, p1.getPlayerShipBoard());

        // Verifica
        assertTrue(cluster.contains(c1));

    }
    public void testClusterWithRecursionAllDirections() {
        boolean[][] available = new boolean[5][7];
        for (boolean[] row : available) Arrays.fill(row, true);
        ShipBoard s = new ShipBoard(available, 7, 5);

        // Centro
        Cabin center = new Cabin(0, true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        s.placeComponent(7, 7, center);


        // Nord
        Cabin north = new Cabin(0, true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        s.placeComponent(7, 6, north);


        // Sud
        Cabin south = new Cabin(0, true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        s.placeComponent(7, 8, south);


        // Est
        Cabin east = new Cabin(0, true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        s.placeComponent(8, 7, east);


        // Ovest
        Cabin west = new Cabin(0, true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        s.placeComponent(6, 7, west);


        ArrayList<Cabin> cluster = new ArrayList<>();
        boolean[][] visited = new boolean[5][7];

        // Scatta la ricorsione verso N, S, E, O
        center.addEpidemicCabin(7, 7, cluster, visited, 7, 5, s);

        assertTrue(cluster.contains(center));


    }


    public void testManageEpidemic(){
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++) {
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Cabin c1 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c2 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c3 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c4 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c5 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c6 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c7 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c8 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c9 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Storage s1 = new Storage(0,false, Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE}, 3);
        s.placeComponent(4,7, c1);
        s.placeComponent(6,8, c5);
        s.placeComponent(6,5, c2);
        s.placeComponent(5,9, c4);
        s.placeComponent(6,9, c3);
        s.placeComponent(8,8, c6);
        s.placeComponent(9,8, c7);
        s.placeComponent(8,9, c8);
        s.placeComponent(9,9, c9);

        ArrayList<Cabin> c=new ArrayList<>();
        //ArrayList<Cabin> c_test1=new ArrayList<>();
        ArrayList<Cabin> c_test2=new ArrayList<>();

        //c_test1.add(c1);
        c_test2.add(c2);
        c_test2.add(c3);
        c_test2.add(c4);
        c_test2.add(c5);
        c_test2.add(c6);
        c_test2.add(c7);
        c_test2.add(c8);
        c_test2.add(c9);
        boolean[][] visited=new boolean[5][7];

        for(int i = 0; i < s.getComponentMatrix().length; i++){
            for(int j = 0; j < s.getComponentMatrix()[0].length; j++){
                if(s.getAvailablePositionMatrix()[i][j]){
                    Components comp = s.getComponent(i+5,j+4);
                    if(comp != null){
                        comp.manageEpidemic(visited, s.getComponentMatrix().length, s.getComponentMatrix()[0].length, s);
                    }
                }
            }
        }

        assertEquals(2, c1.getNumAstronauts());

        for(Cabin cabin : c_test2){
            assertEquals(2, cabin.getNumAstronauts());
        }
    }


    public void testCreateView() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("a", null);
        Player p2 = new Player("b", null);
        Player p3 = new Player( "c", null);
        players.add(p1);
        players.add(p2);
        players.add(p3);

        Game game = new Game(3, 2, 1, new GameController() );
        game.getPlayers().addAll(players);
        game.setPlayersShipboard();
        Cabin cabin = new Cabin(10, false, Direction.EAST, new Connector[]{
                Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE
        });

        cabin.setPosition(8, 6); // posizione fittizia
        ComponentsView view = cabin.createView();
        assertEquals(8, view.getPosX());
        assertEquals(6, view.getPosY());
        assertEquals(Direction.EAST, view.getDirection());
        assertEquals(10, view.getId());
        assertEquals("Cabin", view.getType());
        assertEquals(0, view.getNumAstronauts());
        assertNull(view.getGoods());
        assertNull(view.getAlienColour());

        // Con alieno
        Cabin cabin1 = new Cabin(10, false, Direction.EAST, new Connector[]{
                Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE
        });
        LifeSupportSystem lf= new LifeSupportSystem(1,AlienColour.BROWN,Direction.EAST, new Connector[]{
                Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE
        });
        p1.getPlayerShipBoard().placeComponent(8,7,cabin1);
        p1.getPlayerShipBoard().placeComponent(8,8,lf);
        cabin1.addAlien(new Alien(AlienColour.BROWN),p1.getPlayerShipBoard());
        ComponentsView view1 = cabin1.createView();
        assertEquals(8, view1.getPosX());
        assertEquals(7, view1.getPosY());
        assertEquals(Direction.EAST, view1.getDirection());
        assertEquals(10, view1.getId());
        assertEquals("Cabin", view1.getType());
        assertEquals(0, view1.getNumAstronauts());
        assertNull(view1.getGoods());
        assertEquals(view1.getAlienColour(),AlienColour.BROWN);
    }

    public void testIsCabin() {
        Cabin cabin = new Cabin(7, true, Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE
        });
        assertSame(cabin, cabin.isCabin());
    }

    public void testHasAlien() {
        boolean[][] mat = new boolean[2][2];
        mat[0][0]=true;
        mat[0][1]=true;
        mat[1][0]=true;
        mat[1][1]=true;
        ShipBoard board = new ShipBoard(mat,2,2);
        // Crea una Cabin e la piazza a (5,4)
        Cabin cabin = new Cabin(1, false, Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        });
        board.placeComponent(4, 5,cabin);
        // All'inizio la cabina NON ha alieno
        assertNull("Cabina non dovrebbe avere alieni inizialmente", cabin.hasAlien());
        // Simula un LifeSupportSystem compatibile e aggiungilo (necessario per aggiungere un alieno)
        LifeSupportSystem lss = new LifeSupportSystem(2, AlienColour.BROWN, Direction.EAST, new Connector[]{
                Connector.EMPTY, Connector.UNIVERSAL, Connector.EMPTY, Connector.EMPTY
        });
        cabin.addLifeSupportList(lss);
        cabin.changeWithLifeSupport(true);
        // Crea un alieno marrone e aggiungilo alla cabina
        Alien alien = new Alien(AlienColour.BROWN);
        cabin.addAlien(alien, board);
        // Ora la cabina DEVE avere un alieno
        assertNotNull("Cabina deve avere un alieno dopo addAlien", cabin.hasAlien());
        assertEquals("Alieno della cabina deve essere quello inserito", alien, cabin.hasAlien());
    }

}