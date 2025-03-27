package org.example.ComponentsPack;

import junit.framework.TestCase;
import org.example.Model.ComponentsPack.*;
import org.example.Model.ShipBoard;

import java.util.ArrayList;

public class CabinTest extends TestCase {

    public void testGetNumAstronauts() {
        Cabin c = new Cabin(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        c.changeNumAstronauts(2);
        assertEquals(2,c.getNumAstronauts());
    }

    public void testGetAlien() {
        Cabin c = new Cabin(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        Alien a= new Alien(AlienColour.BROWN);
        LifeSupportSystem l=new LifeSupportSystem(AlienColour.BROWN,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        l.addLifeSupport(c);
        c.addAlien(a);
        assertEquals(a, c.getAlien());
    }

    public void testGetWithLifeSupport() {
        Cabin c = new Cabin(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        LifeSupportSystem l=new LifeSupportSystem(AlienColour.BROWN,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        l.addLifeSupport(c);
        assertTrue(c.getWithLifeSupport());
    }

    public void testChangeWithLifeSupport() {
        Cabin c = new Cabin(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertFalse(c.getWithLifeSupport());
        c.changeWithLifeSupport(true);
        assertTrue(c.getWithLifeSupport());
    }

    public void testGetIsCentral() {
        Cabin c = new Cabin(true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertTrue(c.getIsCentral());
    }

    public void testChangeNumAstronauts() {
        Cabin c = new Cabin(true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        c.changeNumAstronauts(2);
        assertEquals(2,c.getNumAstronauts());
    }

    public void testAddLifeSupport() {
        Cabin c = new Cabin(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        LifeSupportSystem l=new LifeSupportSystem(AlienColour.BROWN,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        c.addLifeSupportList(l);
        assertEquals(1, c.getLifeSupportSystemArrayList().size());
    }

    public void testRemoveLifeSupport() {
        Cabin c = new Cabin(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        LifeSupportSystem l=new LifeSupportSystem(AlienColour.BROWN,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        c.addLifeSupportList(l);
        assertEquals(1, c.getLifeSupportSystemArrayList().size());
        c.removeLifeSupport(l);
        assertEquals(0, c.getLifeSupportSystemArrayList().size());
    }

    public void testgetLifeSupportSystemArrayList() {
        Cabin c = new Cabin(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        LifeSupportSystem l=new LifeSupportSystem(AlienColour.BROWN,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        c.addLifeSupportList(l);
        assertEquals(l, c.getLifeSupportSystemArrayList().get(0));
    }

    public void testaddAlien(){
        Cabin c = new Cabin(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        Alien a = new Alien(AlienColour.BROWN);
        LifeSupportSystem l=new LifeSupportSystem(AlienColour.BROWN,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        l.addLifeSupport(c);
        c.addAlien(a);
        assertEquals(a, c.getAlien());
    }

    public void testremoveAlien(){
        Cabin c = new Cabin(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        Alien a = new Alien(AlienColour.BROWN);
        LifeSupportSystem l=new LifeSupportSystem(AlienColour.BROWN,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        l.addLifeSupport(c);
        c.addAlien(a);
        assertEquals(a, c.getAlien());
        c.removeAlien();
        assertNull(c.getAlien());
    }

    public void testRemove(){
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for(int i=0; i<7; i++){
            for(int j=0; j<5; j++){
                if((i==0 && j==0) || (i==0 && j==1) || (i==1 && j==0) || (i==3 && j==0) || (i==5 && j==0) || (i==6 && j==0) || (i==6 && j==1) || (i==3 && j==4)){
                    availablePositionMatrix[i][j] = false;
                }else{
                    availablePositionMatrix[i][j]=true;
                }
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Cabin c = new Cabin(true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(1,2,c);
        c.remove(s);
        assertEquals(0, c.getNumAstronauts());
    }

    public void testPlace(){
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for(int i=0; i<7; i++){
            for(int j=0; j<5; j++){
                if((i==0 && j==0) || (i==0 && j==1) || (i==1 && j==0) || (i==3 && j==0) || (i==5 && j==0) || (i==6 && j==0) || (i==6 && j==1) || (i==3 && j==4)){
                    availablePositionMatrix[i][j] = false;
                }else{
                    availablePositionMatrix[i][j]=true;
                }
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        LifeSupportSystem l=new LifeSupportSystem(AlienColour.BROWN,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(2,2,l);
        Cabin c = new Cabin(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(1,2,c);
        assertEquals(c, s.getComponent(1,2));
        assertTrue(c.getWithLifeSupport());
    }

    public void testaddCabin(){
        LifeSupportSystem l=new LifeSupportSystem(AlienColour.BROWN,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        Cabin c = new Cabin(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        c.addCabin(l);
        assertTrue(c.getWithLifeSupport());
    }

    public void testremoveCabin(){
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for(int i=0; i<7; i++){
            for(int j=0; j<5; j++){
                if((i==0 && j==0) || (i==0 && j==1) || (i==1 && j==0) || (i==3 && j==0) || (i==5 && j==0) || (i==6 && j==0) || (i==6 && j==1) || (i==3 && j==4)){
                    availablePositionMatrix[i][j] = false;
                }else{
                    availablePositionMatrix[i][j]=true;
                }
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        LifeSupportSystem l=new LifeSupportSystem(AlienColour.BROWN,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(2,2,l);
        Cabin c = new Cabin(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(1,2,c);
        assertTrue(c.getWithLifeSupport());
        Alien a = new Alien(AlienColour.BROWN);
        c.addAlien(a);
        c.removeCabin(l, s);
        assertNull(c.getAlien());
        assertFalse(c.getWithLifeSupport());
    }

    public void testhasAliens(){
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for(int i=0; i<7; i++){
            for(int j=0; j<5; j++){
                if((i==0 && j==0) || (i==0 && j==1) || (i==1 && j==0) || (i==3 && j==0) || (i==5 && j==0) || (i==6 && j==0) || (i==6 && j==1) || (i==3 && j==4)){
                    availablePositionMatrix[i][j] = false;
                }else{
                    availablePositionMatrix[i][j]=true;
                }
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        LifeSupportSystem l=new LifeSupportSystem(AlienColour.BROWN,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(2,2,l);
        Cabin c = new Cabin(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(1,2,c);
        assertNull(c.hasAlien());
        Alien a = new Alien(AlienColour.BROWN);
        c.addAlien(a);
        assertEquals(a, c.getAlien());
    }

    public void testaddEpidemicCabin(){
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for(int i=0; i<7; i++){
            for(int j=0; j<5; j++){
                if((i==0 && j==0) || (i==0 && j==1) || (i==1 && j==0) || (i==3 && j==0) || (i==5 && j==0) || (i==6 && j==0) || (i==6 && j==1) || (i==3 && j==4)){
                    availablePositionMatrix[i][j] = false;
                }else{
                    availablePositionMatrix[i][j]=true;
                }
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Cabin c1 = new Cabin(true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c2 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c3 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c4 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c5 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c6 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c7 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c8 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c9 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        s.placeComponent(3,2, c1);
        s.placeComponent(1,3, c5);
        s.placeComponent(2,3, c2);
        s.placeComponent(1,4, c4);
        s.placeComponent(2,4, c3);
        s.placeComponent(4,3, c6);
        s.placeComponent(5,3, c7);
        s.placeComponent(4,4, c8);
        s.placeComponent(5,4, c9);
        ArrayList<Cabin> c=new ArrayList<>();
        ArrayList<Cabin> c_test=new ArrayList<>();
        c_test.add(c1);
        c_test.add(c2);
        c_test.add(c3);
        c_test.add(c4);
        c_test.add(c5);
        c_test.add(c6);
        c_test.add(c7);
        c_test.add(c8);
        c_test.add(c9);
        boolean[][] visited=new boolean[7][5];
        c1.addEpidemicCabin(2,3,c,visited, 7, 5);
        assertEquals(c_test,c);
    }
}