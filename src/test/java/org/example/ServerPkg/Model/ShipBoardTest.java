package org.example.ServerPkg.Model;

import junit.framework.TestCase;
import org.example.ServerPkg.Model.ComponentsPack.*;

import java.util.ArrayList;

public class ShipBoardTest extends TestCase {

    public void testsetNumAstronauts(){
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
        s.setNumAstronauts(5);
        assertEquals(5, s.getTotalAstronauts());
    }

    public void testgetSingleEnginePower(){
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
        Engine engine = new Engine(1, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(1,2,engine);
        assertEquals(1,s.getSingleEnginePower());
    }

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

    public void testGetDeletedComponentsCounterCounter() {
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
        assertEquals(0,s.getDeletedComponentsCounter());
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
        s.placeComponent(1,1,b1);
        s.placeComponent(2,2,b2);
        assertEquals(6,s.getTotalBattery());
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
        storage1.addGood(new Goods(GoodsColour.GREEN));
        storage1.addGood(new Goods(GoodsColour.BLUE));
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
        s.placeComponent(2,2,cannon2);
        ArrayList<Points> cannons= new ArrayList<Points>();
        cannons.add(new Points(2,2));
        BatteryStorage b = new BatteryStorage(3, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(3,3,b);
        ArrayList<Points> batteries = new ArrayList<Points>();
        batteries.add(new Points(3,3));
        LifeSupportSystem l = new LifeSupportSystem(AlienColour.PURPLE, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        Cabin c=new Cabin(false,  Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(1,1, c);
        s.placeComponent(1,2, l);
        c.addAlien(new Alien(AlienColour.PURPLE), s);
        assertEquals(4.0F, s.getTotalCannonPower(cannons, batteries));
    }

    public void testGetTotalEnginePower() {
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
        s.placeComponent(2,2,engine);
        ArrayList<Points> engines= new ArrayList<Points>();
        engines.add(new Points(2,2));
        BatteryStorage b = new BatteryStorage(3, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(3,3,b);
        ArrayList<Points> batteries = new ArrayList<Points>();
        batteries.add(new Points(3,3));
        LifeSupportSystem l = new LifeSupportSystem(AlienColour.BROWN, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        Cabin c=new Cabin(false,  Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(1,1, c);
        s.placeComponent(1,2, l);
        c.addAlien(new Alien(AlienColour.BROWN), s);
        assertEquals(4, s.getTotalEnginePower(engines, batteries));

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
        //assertEquals(1, s.getNumDoubleEngine());
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
        assertTrue(s.getIfShielded(Direction.NORTH));
        assertTrue(s.getIfShielded(Direction.WEST));
        assertFalse(s.getIfShielded(Direction.EAST));
        assertFalse(s.getIfShielded(Direction.SOUTH));
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
        assertTrue(s.getIfShielded(Direction.NORTH));
        assertTrue(s.getIfShielded(Direction.WEST));
        assertFalse(s.getIfShielded(Direction.EAST));
        assertFalse(s.getIfShielded(Direction.SOUTH));

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
        assertFalse(s.getIfShielded(Direction.NORTH));
        assertFalse(s.getIfShielded(Direction.WEST));
        assertEquals(1, s.getDeletedComponentsCounter());

    }

    public void testremoveBookedComponents(){
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
        s.removeBookedComponents();
        assertEquals(1, s.getDeletedComponentsCounter());

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
        assertTrue(s.getIfSingleCannon(Direction.EAST, 1));
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
        assertEquals(shield,s.getComponent(1,1));
        assertTrue(s.getIfShielded(Direction.NORTH));
        assertTrue(s.getIfShielded(Direction.WEST));
        assertFalse(s.getIfShielded(Direction.EAST));
        assertFalse(s.getIfShielded(Direction.SOUTH));
    }


    public void testGetShieldedDirections() {
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                if ((i == 0 && j == 0) || (i == 0 && j == 1) || (i == 1 && j == 0) || (i == 3 && j == 0) || (i == 5 && j == 0) || (i == 6 && j == 0) || (i == 6 && j == 1) || (i == 3 && j == 4)) {
                    availablePositionMatrix[i][j] = false;
                } else {
                    availablePositionMatrix[i][j] = true;
                }
            }
        }
        ShipBoard s = new ShipBoard(availablePositionMatrix, 7, 5);
        Shield shield = new Shield(Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY}, Direction.WEST);
        s.placeComponent(1, 1, shield);
        assertEquals(1,s.getShieldedDirections()[0]);
        assertEquals(0,s.getShieldedDirections()[1]);
        assertEquals(0,s.getShieldedDirections()[2]);
        assertEquals(1, s.getShieldedDirections()[3]);

    }

    public void testTestSetNumAstronauts() {
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                if ((i == 0 && j == 0) || (i == 0 && j == 1) || (i == 1 && j == 0) || (i == 3 && j == 0) || (i == 5 && j == 0) || (i == 6 && j == 0) || (i == 6 && j == 1) || (i == 3 && j == 4)) {
                    availablePositionMatrix[i][j] = false;
                } else {
                    availablePositionMatrix[i][j] = true;
                }
            }
        }
        ShipBoard s = new ShipBoard(availablePositionMatrix, 7, 5);
        Cabin cabin1 = new Cabin(true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        s.placeComponent(1, 1, cabin1);
        assertEquals(2,s.getTotalAstronauts());
}

    public void testSetSingleEnginePower() {
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                if ((i == 0 && j == 0) || (i == 0 && j == 1) || (i == 1 && j == 0) || (i == 3 && j == 0) || (i == 5 && j == 0) || (i == 6 && j == 0) || (i == 6 && j == 1) || (i == 3 && j == 4)) {
                    availablePositionMatrix[i][j] = false;
                } else {
                    availablePositionMatrix[i][j] = true;
                }
            }
        }
        ShipBoard s = new ShipBoard(availablePositionMatrix, 7, 5);
        Engine engine = new Engine(1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        s.placeComponent(1, 1, engine);
        assertEquals(1,s.getSingleEnginePower());
    }

    public void testGetNumDoubleEngines() {
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                availablePositionMatrix[i][j] = (i != 0 || j != 0) && (i != 0 || j != 1) && (i != 1 || j != 0) && (i != 3 || j != 0) && (i != 5 || j != 0) && (i != 6 || j != 0) && (i != 6 || j != 1) && (i != 3 || j != 4);
            }
        }
        ShipBoard s = new ShipBoard(availablePositionMatrix, 7, 5);
        Engine engine1 = new Engine(2, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        Engine engine2 = new Engine(2, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        Engine engine3 = new Engine(2, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        s.placeComponent(1, 1, engine1);
        assertEquals(1,s.getNumDoubleEngines());
        s.placeComponent(1, 2, engine2);
        assertEquals(2,s.getNumDoubleEngines());
        s.placeComponent(1, 3, engine3);
        assertEquals(3,s.getNumDoubleEngines());
    }

    public void testSetNumDoubleEngines() {
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                availablePositionMatrix[i][j] = (i != 0 || j != 0) && (i != 0 || j != 1) && (i != 1 || j != 0) && (i != 3 || j != 0) && (i != 5 || j != 0) && (i != 6 || j != 0) && (i != 6 || j != 1) && (i != 3 || j != 4);
            }
        }
        ShipBoard s = new ShipBoard(availablePositionMatrix, 7, 5);
        s.setNumDoubleEngines(2);
        assertEquals(2,s.getNumDoubleEngines());
        s.setNumDoubleEngines(-1);
        assertEquals(1, s.getNumDoubleEngines());
    }

    public void testGetSingleCannonPower() {
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
        assertEquals(0.5F,s.getSingleCannonPower());
    }

    public void testSetSingleCannonPower() {
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
        s.setSingleCannonPower(1);
        assertEquals(1F,s.getSingleCannonPower());

    }

    public void testSetNumDoubleCannon() {
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
        s.setNumDoubleCannon(1);
        assertEquals(1,s.getNumDoubleCannon());

    }

    public void testGetDeletedComponentsCounter() {
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
        s.removeComponent(1,1);
        assertEquals(1, s.getDeletedComponentsCounter());
    }

    public void testAddShieldInDirection() {
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
        s.addShieldInDirection(Direction.NORTH);
        assertEquals(1, s.getShieldedDirections()[0]);

    }

    public void testDecreaseShieldInDirection() {
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
        s.addShieldInDirection(Direction.NORTH);
        s.decreaseShieldInDirection(Direction.NORTH);
        assertEquals(0, s.getShieldedDirections()[0]);
    }

    public void testGetIfExposed() {
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
        Cannon c=new Cannon(1,Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(1,2,c);
        Cannon c1=new Cannon(1,Direction.NORTH,new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY});
        s.placeComponent(1,1,c1);
        assertTrue(s.getIfExposed(Direction.WEST, c));
        assertFalse(s.getIfExposed(Direction.SOUTH, c1));

    }

    public void testGetTotalExposedConnectors() {
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
        Cabin c1 = new Cabin(true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        Cabin c2 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c3 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c4 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c5 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c6 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c7 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c8 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c9 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL});
        s.placeComponent(3,2, c1);
        s.placeComponent(2,3, c2);
        s.placeComponent(2,4, c3);
        s.placeComponent(1,4, c4);
        s.placeComponent(1,3, c5);
        s.placeComponent(4,3, c6);
        s.placeComponent(5,3, c7);
        s.placeComponent(3,3, c8);
        s.placeComponent(5,4, c9);
        assertEquals(11, s.getTotalExposedConnectors());
    }
}