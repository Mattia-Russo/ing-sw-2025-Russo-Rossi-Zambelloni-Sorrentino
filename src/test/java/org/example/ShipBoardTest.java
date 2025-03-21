package org.example;

import junit.framework.TestCase;
import org.example.ComponentsPack.*;

import java.util.ArrayList;

public class ShipBoardTest extends TestCase {

    public void testGetComponentMatrix() {
        boolean[][] availablePositionMatrix = new boolean[7][5];
        Components[][] ComponentMatrix = new Components[7][5];
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
        Cabin cabin1 = new Cabin(true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        Storage storage1 = new Storage(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon1 = new Cannon(1, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(1,1, cabin1);
        ComponentMatrix[1][1]=cabin1;
        s.placeComponent(2,2, storage1);
        ComponentMatrix[2][2]=storage1;
        s.placeComponent(3,3, cannon1);
        ComponentMatrix[3][3]=cannon1;
        for(int i=0; i<7; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(ComponentMatrix[i][j],s.getComponentMatrix()[i][j]);
            }
        }
    }

    public void testGetAvailablePositionMatrix() {
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
        for(int i=0; i<7; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(availablePositionMatrix[i][j], s.getAvailablePositionMatrix()[i][j]);
            }
        }
    }

    public void testValidPosition() {
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
        for(int i=0; i<7; i++){
            for(int j=0; j<5; j++){
                if((i==0 && j==0) || (i==0 && j==1) || (i==1 && j==0) || (i==3 && j==0) || (i==5 && j==0) || (i==6 && j==0) || (i==6 && j==1) || (i==3 && j==4)){
                    assertFalse(s.validPosition(i,j));
                }else{
                    assertTrue(s.validPosition(i,j));
                }
            }
        }
    }

    public void testGetComponent() {
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
        Cabin cabin1 = new Cabin(true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        Storage storage1 = new Storage(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon1 = new Cannon(1, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(1,1, cabin1);
        s.placeComponent(2,2, storage1);
        s.placeComponent(3,3, cannon1);
        for(int i=0; i<7; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(s.getComponentMatrix()[i][j],s.getComponent(i,j));
            }
        }
    }

    public void testGetCounter() {
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
        assertEquals(0,s.getCounter());
    }

    public void testBookComponents() {
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
        Cabin cabin1 = new Cabin(true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        s.bookComponents(cabin1);
        assertEquals(cabin1, s.getBookedComponents()[0]);
    }

    public void testgetBookedComponents() {
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
        Cabin cabin1 = new Cabin(true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        Storage storage1 = new Storage(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        s.bookComponents(cabin1);
        s.bookComponents(storage1);
        assertEquals(cabin1, s.getBookedComponents()[0]);
        assertEquals(storage1, s.getBookedComponents()[1]);
    }

    public void testGetTotalBattery() {
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
        BatteryStorage b1 = new BatteryStorage(3,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE});
        BatteryStorage b2 = new BatteryStorage(3,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE});
        b1.setQuantity(3);
        b2.setQuantity(2);
        s.placeComponent(1,1,b1);
        s.placeComponent(2,2,b2);
        assertEquals(5,s.getTotalBattery());
    }

    public void testGetTotalGoods() {
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
        Storage storage1 = new Storage(true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        s.placeComponent(1,1,storage1);
        storage1.addGood(new Goods(2));
        storage1.addGood(new Goods(3));
        ArrayList<Goods> goods= new ArrayList<Goods>();
        goods.add(storage1.getGoods()[0]);
        goods.add(storage1.getGoods()[1]);
        assertEquals(goods,s.getTotalGoods());

    }

    public void testGetTotalAstronauts() {
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
        Cabin cabin1 = new Cabin(true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        cabin1.changeNumAstronauts(2);
        s.placeComponent(1,1,cabin1);
        assertEquals(2, s.getTotalAstronauts());
    }

    public void testGetTotalCannonPower() {
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
       Cannon cannon2 = new Cannon(2, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(1,2,cannon2);
        ArrayList<Cannon> cannons= new ArrayList<Cannon>();
        cannons.add(cannon2);
        assertEquals(2.0F, s.getTotalCannonPower(cannons));
    }

    public void testGetTotalEngineStrenght() {
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
        Engine engine = new Engine(2, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(1,2,engine);
        ArrayList<Engine> engines = new ArrayList<Engine>();
        engines.add(engine);
        assertEquals(2, s.getTotalEngineStrenght(engines));

    }

    public void testGetNumDoubleCannon() {
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
        Cannon cannon2 = new Cannon(2, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(1,2,cannon2);
        assertEquals(1, s.getNumDoubleCannon());
    }

    public void testGetNumDoubleEngine() {
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
        Engine engine = new Engine(2, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(1,2,engine);
        assertEquals(1, s.getNumDoubleEngine());
    }

    public void testGetIfShielded() {
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
        Shield shield=new Shield(Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY}, Direction.WEST);
        s.placeComponent(1,1,shield);
        assertTrue(s.getIfShielded(0));
        assertTrue(s.getIfShielded(3));
        assertFalse(s.getIfShielded(1));
        assertFalse(s.getIfShielded(2));
    }

    public void testaddShieldedDirection(){
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
        Shield shield=new Shield(Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY}, Direction.WEST);
        s.placeComponent(1,1,shield);
        assertTrue(s.getIfShielded(0));
        assertTrue(s.getIfShielded(3));
        assertFalse(s.getIfShielded(1));
        assertFalse(s.getIfShielded(2));

    }

    public void testRemoveComponent() {
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
        Shield shield=new Shield(Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY}, Direction.WEST);
        s.placeComponent(1,1,shield);
        s.removeComponent(1,1);
        assertFalse(s.getIfShielded(0));
        assertFalse(s.getIfShielded(3));
        assertEquals(1, s.getCounter());

    }

    public void testcheckIfSplitted(){
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
        BatteryStorage b1 = new BatteryStorage(3,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE});
        BatteryStorage b2 = new BatteryStorage(3,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE});
        s.placeComponent(1,1,b1);
        s.placeComponent(2,2,b2);
        assertTrue(s.checkIfSplitted(1,1));
    }

    public void testremoveWreck(){
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
        BatteryStorage b1 = new BatteryStorage(3,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE});
        BatteryStorage b2 = new BatteryStorage(3,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE});
        s.placeComponent(1,1,b1);
        s.placeComponent(2,2,b2);
        s.removeWreck(1,1);
        assertEquals(null,s.getComponent(2,2));
    }

    public void testGetFirstComponent() {
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
        Shield shield=new Shield(Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY}, Direction.WEST);
        s.placeComponent(1,1,shield);
        assertEquals(shield,s.getFirstComponent(Direction.NORTH,1));
        assertEquals(shield,s.getFirstComponent(Direction.SOUTH,1));
        assertEquals(shield,s.getFirstComponent(Direction.WEST,1));
        assertEquals(shield,s.getFirstComponent(Direction.EAST,1));
    }

    public void testGetIfSingleCannon() {
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
        Cannon c=new Cannon(1,Direction.WEST,new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(1,1,c);
        assertTrue(s.getIfSingleCannon(Direction.EAST,1));
    }

    public void testGetIfDoubleCannon() {
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
        Cannon c=new Cannon(2,Direction.WEST,new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(1,1,c);
        assertTrue(s.getIfDoubleCannon(Direction.EAST,1));
    }

    public void testPlaceComponent() {
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
        Shield shield=new Shield(Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY}, Direction.WEST);
        s.placeComponent(1,1,shield);
        assertTrue(s.getIfShielded(0));
        assertTrue(s.getIfShielded(3));
        assertFalse(s.getIfShielded(1));
        assertFalse(s.getIfShielded(2));
    }

    public void testGetIfAlienSupported() {
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
        Cabin cabin= new Cabin(false, Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(1,1,cabin);
        LifeSupportSystem lifeSupportSystem= new LifeSupportSystem(AlienColour.PURPLE,Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(1,2,lifeSupportSystem);
        ArrayList<LifeSupportSystem> lifeSupportSystemArrayList=new ArrayList<LifeSupportSystem>();
        lifeSupportSystemArrayList.add(lifeSupportSystem);
        assertEquals(lifeSupportSystemArrayList, s.getIfAlienSupported(cabin));
    }

}