package org.example.ServerPkg.Model;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.ComponentsPkg.*;
import org.example.ServerPkg.Model.Exceptions.*;

import java.rmi.RemoteException;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class ShipBoardTest extends TestCase {

    public void testsetNumAstronauts(){
        boolean[][] availablePositionMatrix = new boolean[5][7];
        Components[][] ComponentMatrix = new Components[7][5];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        s.setNumAstronauts(5);
        assertEquals(5, s.getTotalAstronauts());
    }

    public void testGetSingleEnginePower(){
        boolean[][] availablePositionMatrix = new boolean[5][7];
        Components[][] ComponentMatrix = new Components[7][5];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Engine engine = new Engine(0, 1, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(6,7,engine);
        assertEquals(1,s.getSingleEnginePower());
    }

    public void testGetComponentMatrix() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        Components[][] ComponentMatrix = new Components[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Cabin cabin1 = new Cabin(0, true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        Storage storage1 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon1 = new Cannon(0, 1, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(5,6, cabin1);
        ComponentMatrix[1][1]=cabin1;
        s.placeComponent(6,7, storage1);
        ComponentMatrix[2][2]=storage1;
        s.placeComponent(7,8, cannon1);
        ComponentMatrix[3][3]=cannon1;
        for(int i=0; i<5; i++) {
            for (int j = 0; j < 7; j++) {
                assertEquals(ComponentMatrix[i][j],s.getComponentMatrix()[i][j]);
            }
        }
    }

    public void testGetAvailablePositionMatrix() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        for(int i=0; i<5; i++) {
            for (int j = 0; j < 7; j++) {
                assertEquals(availablePositionMatrix[i][j], s.getAvailablePositionMatrix()[i][j]);
            }
        }
    }

    public void testValidPosition() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
    }

    public void testGetComponent() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Cabin cabin1 = new Cabin(0, true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        Storage storage1 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon1 = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(5,6, cabin1);
        s.placeComponent(6,7, storage1);
        s.placeComponent(7,8, cannon1);
        for(int i=5; i<10; i++) {
            for (int j = 4; j < 11; j++) {
                assertEquals(s.getComponentMatrix()[i-5][j-4],s.getComponent(i,j));
            }
        }
    }

    public void testGetDeletedComponentsCounterCounter() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        assertEquals(0,s.getDeletedComponentsCounter());
    }

    public void testBookComponents() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Cabin cabin1 = new Cabin(0, true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        s.bookComponents(cabin1);
        assertEquals(cabin1, s.getBookedComponents()[0]);
    }
    public void testBookComponents1() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Cabin cabin1 = new Cabin(0, true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        s.bookComponents(cabin1);
        s.bookComponents(cabin1);
        try{
            s.bookComponents(cabin1);
        }catch(FullBookedSlotsException e){

        }
        assertEquals(cabin1, s.getBookedComponents()[0]);
    }

    public void testgetBookedComponents() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Cabin cabin1 = new Cabin(0, true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        Storage storage1 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        s.bookComponents(cabin1);
        s.bookComponents(storage1);
        assertEquals(cabin1, s.getBookedComponents()[0]);
        assertEquals(storage1, s.getBookedComponents()[1]);
    }

    public void testGetTotalBattery() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        BatteryStorage b1 = new BatteryStorage(0,3,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE});
        BatteryStorage b2 = new BatteryStorage(0,3,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE});
        s.placeComponent(5,6,b1);
        s.placeComponent(6,7,b2);
        assertEquals(6,s.getTotalBattery());
    }

    public void testGetTotalGoods() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Storage storage1 = new Storage(0,true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        s.placeComponent(5,6,storage1);
        storage1.addGood(new Goods(GoodsColour.GREEN));
        storage1.addGood(new Goods(GoodsColour.BLUE));
        ArrayList<Goods> goods= new ArrayList<Goods>();
        goods.add(storage1.getGoods()[0]);
        goods.add(storage1.getGoods()[1]);
        assertEquals(goods,s.getTotalGoods());

    }

    public void testGetTotalAstronauts() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Cabin cabin1 = new Cabin(0, true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        s.placeComponent(5,6,cabin1);
        assertEquals(2, s.getTotalAstronauts());
    }

    public void testGetTotalCannonPower() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Cannon cannon2 = new Cannon(0, 2, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(6,7,cannon2);
        ArrayList<Points> cannons= new ArrayList<Points>();
        cannons.add(new Points(6,7));
        BatteryStorage b = new BatteryStorage(0, 3, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(7,8,b);
        ArrayList<Points> batteries = new ArrayList<Points>();
        batteries.add(new Points(7,8));
        LifeSupportSystem l = new LifeSupportSystem(0, AlienColour.PURPLE, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        Cabin c=new Cabin(0,false,  Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(5,6, c);
        s.placeComponent(5,7, l);
        c.addAlien(new Alien(AlienColour.PURPLE), s);
        assertEquals(4.0F, s.getTotalCannonPower(cannons, batteries));
    }
    public void testGetTotalCannonPower1(){
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        ArrayList<Points> cannons= new ArrayList<Points>();
        Cannon cannon2 = new Cannon(0, 1, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(6,7,cannon2);
        cannons.add(new Points(6,7));
        ArrayList<Points> batteries = new ArrayList<Points>();
        batteries.add(new Points(6,6));
        try {
            s.getTotalCannonPower(cannons, batteries);

        }catch(InvalidParameterException e){

        }
    }
    public void testGetTotalCannonPower2(){
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        ArrayList<Points> cannons= new ArrayList<Points>();
        Cannon cannon2 = new Cannon(0, 2, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(6,7,cannon2);
        cannons.add(new Points(6,7));
        cannons.add(new Points(6,7));
        ArrayList<Points> batteries = new ArrayList<Points>();
        batteries.add(new Points(6,6));
        try {
            s.getTotalCannonPower(cannons, batteries);

        }catch(CannonSelectedTwiceException e){

        }
    }

    public void testGetTotalCannonPower3(){
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        ArrayList<Points> cannons= new ArrayList<Points>();
        Cannon cannon2 = new Cannon(0, 2, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(6,7,cannon2);
        s.placeComponent(8,8,cannon2);
        BatteryStorage b = new BatteryStorage(0, 1, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(7,8,b);
        cannons.add(new Points(6,7));
        cannons.add(new Points(8,8));
        ArrayList<Points> batteries = new ArrayList<Points>();
        batteries.add(new Points(7,8));
        try {
            s.getTotalCannonPower(cannons, batteries);

        }catch(BatteriesLessThenCannonException e){

        }
    }
    public void testGetTotalCannonPower4(){
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Cannon cannon2 = new Cannon(0, 2, Direction.EAST, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(6,7,cannon2);
        ArrayList<Points> cannons= new ArrayList<Points>();
        cannons.add(new Points(6,7));
        BatteryStorage b = new BatteryStorage(0, 3, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(7,8,b);
        ArrayList<Points> batteries = new ArrayList<Points>();
        batteries.add(new Points(7,8));
        LifeSupportSystem l = new LifeSupportSystem(0, AlienColour.PURPLE, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        Cabin c=new Cabin(0,false,  Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(5,6, c);
        s.placeComponent(5,7, l);
        c.addAlien(new Alien(AlienColour.PURPLE), s);
        assertEquals(3.0F, s.getTotalCannonPower(cannons, batteries));
    }
    public void testGetTotalEnginePower() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Engine engine = new Engine(0,2, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(6,7,engine);
        ArrayList<Points> engines= new ArrayList<Points>();
        engines.add(new Points(6,7));
        BatteryStorage b = new BatteryStorage(0,3, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(7,8,b);
        ArrayList<Points> batteries = new ArrayList<Points>();
        batteries.add(new Points(7,8));
        LifeSupportSystem l = new LifeSupportSystem(0,AlienColour.BROWN, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        Cabin c=new Cabin(0,false,  Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(5,6, c);
        s.placeComponent(5,7, l);
        c.addAlien(new Alien(AlienColour.BROWN), s);
        assertEquals(4, s.getTotalEnginePower(engines, batteries));

    }
    public void testGetTotalEnginePower1(){
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        ArrayList<Points> engines= new ArrayList<Points>();
        Engine engine2 = new Engine(0, 1, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(6,7,engine2);
        engines.add(new Points(6,7));
        ArrayList<Points> batteries = new ArrayList<Points>();
        batteries.add(new Points(6,6));
        try {
            s.getTotalEnginePower(engines, batteries);

        }catch(InvalidParameterException e){

        }
    }
    public void testGetTotalEnginePower3(){
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        ArrayList<Points> cannons= new ArrayList<Points>();
        Engine engine2 = new Engine(0, 2, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(6,7,engine2);
        s.placeComponent(8,8,engine2);
        BatteryStorage b = new BatteryStorage(0, 1, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(7,8,b);
        cannons.add(new Points(6,7));
        cannons.add(new Points(8,8));
        ArrayList<Points> batteries = new ArrayList<Points>();
        batteries.add(new Points(7,8));
        try {
            s.getTotalEnginePower(cannons, batteries);

        }catch(BatteriesLessThenCannonException e){

        }
    }


    public void testGetNumDoubleCannon() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Cannon cannon2 = new Cannon(0, 2, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(5,7,cannon2);
        assertEquals(1, s.getNumDoubleCannon());
    }

    public void testGetNumDoubleEngine() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Engine engine = new Engine(0, 2, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(5,7,engine);
        //assertEquals(1, s.getNumDoubleEngine());
    }

    public void testGetIfShielded() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Shield shield=new Shield(0,Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY}, Direction.WEST);
        s.placeComponent(5,6,shield);
        assertTrue(s.getIfShielded(Direction.NORTH));
        assertTrue(s.getIfShielded(Direction.WEST));
        assertFalse(s.getIfShielded(Direction.EAST));
        assertFalse(s.getIfShielded(Direction.SOUTH));
    }

    public void testaddShieldedDirection(){
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Shield shield=new Shield(0,Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY}, Direction.WEST);
        s.placeComponent(5,6,shield);
        assertTrue(s.getIfShielded(Direction.NORTH));
        assertTrue(s.getIfShielded(Direction.WEST));
        assertFalse(s.getIfShielded(Direction.EAST));
        assertFalse(s.getIfShielded(Direction.SOUTH));

    }

    public void testRemoveComponent() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Shield shield=new Shield(0,Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY}, Direction.WEST);
        s.placeComponent(5,6,shield);
        s.removeComponent(5, 6);
        assertFalse(s.getIfShielded(Direction.NORTH));
        assertFalse(s.getIfShielded(Direction.WEST));
        assertEquals(1, s.getDeletedComponentsCounter());

    }
    public void testRemoveComponent2() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Shield shield=new Shield(0,Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY}, Direction.WEST);
        s.placeComponent(5,6,shield);
        try {
            s.removeComponent(0, 0);
        }catch(InvalidPositionException e){

        }

    }
    public void testRemoveComponent3() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Shield shield=new Shield(0,Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY}, Direction.WEST);
        s.placeComponent(5,6,shield);
        try {
            s.removeComponent(6, 6);
        }catch(AlreadyEmptyPositionException e){

        }

    }

    public void testremoveBookedComponents(){
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Cabin cabin1 = new Cabin(0, true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        s.bookComponents(cabin1);
        s.removeBookedComponents();
        assertEquals(1, s.getDeletedComponentsCounter());

    }

    public void testcheckIfSplit(){
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        BatteryStorage b1 = new BatteryStorage(0, 3,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE});
        BatteryStorage b2 = new BatteryStorage(0, 3,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE});
        s.placeComponent(5,6,b1);
        s.placeComponent(6,7,b2);
        assertTrue(s.checkIfSplit(1,1));
    }

    public void testremoveWreck(){
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        BatteryStorage b1 = new BatteryStorage(0, 3,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE});
        BatteryStorage b2 = new BatteryStorage(0, 3,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE});
        s.placeComponent(5,6,b1);
        s.placeComponent(6,7,b2);
        s.removeWreck(5,6);
        assertNull(s.getComponent(6, 7));
    }

    public void testGetFirstComponent() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Shield shield=new Shield(0,Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY}, Direction.WEST);
        s.placeComponent(5,6,shield);
        assertEquals(shield,s.getFirstComponent(Direction.NORTH,5));
        assertEquals(shield,s.getFirstComponent(Direction.SOUTH,5));
        assertEquals(shield,s.getFirstComponent(Direction.WEST,6));
        assertEquals(shield,s.getFirstComponent(Direction.EAST,6));
    }

    public void testGetIfSingleCannon() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Cannon c=new Cannon(0, 1,Direction.WEST,new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(5,6,c);
        assertTrue(s.getIfSingleCannon(Direction.WEST, 6));
    }
    public void testGetIfSingleCannon2() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Cannon c=new Cannon(0, 1,Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(5,6,c);
        assertTrue(s.getIfSingleCannon(Direction.NORTH, 5));
    }

    public void testGetIfDoubleCannon() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Cannon c=new Cannon(0, 2,Direction.WEST,new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(5,6,c);
        assertTrue(s.getIfDoubleCannon(Direction.WEST,6));
    }
    public void testGetIfDoubleCannon2() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Cannon c=new Cannon(0, 2,Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(5,6,c);
        assertTrue(s.getIfDoubleCannon(Direction.NORTH,5));
    }

    public void testPlaceComponent() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Shield shield=new Shield(0,Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY}, Direction.WEST);
        s.placeComponent(5,6,shield);
        assertEquals(shield,s.getComponent(6, 5));
        assertTrue(s.getIfShielded(Direction.NORTH));
        assertTrue(s.getIfShielded(Direction.WEST));
        assertFalse(s.getIfShielded(Direction.EAST));
        assertFalse(s.getIfShielded(Direction.SOUTH));
    }
    public void testPlaceComponent2() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Shield shield=new Shield(0,Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY}, Direction.WEST);
        s.placeComponent(5,6,shield);
        try{
            s.placeComponent(5,6,shield);
        }catch(OccupiedPositionException e){

        }
    }
    public void testPlaceComponent3() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Shield shield=new Shield(0,Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY}, Direction.WEST);
        s.placeComponent(5,6,shield);
        try{
            s.placeComponent(0,0,shield);
        }catch(InvalidPositionException e){

        }
    }


    public void testGetShieldedDirections() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s = new ShipBoard(availablePositionMatrix, 7, 5);
        Shield shield = new Shield(0,Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY}, Direction.WEST);
        s.placeComponent(5,6, shield);
        assertEquals(1,s.getShieldedDirections()[0]);
        assertEquals(0,s.getShieldedDirections()[1]);
        assertEquals(0,s.getShieldedDirections()[2]);
        assertEquals(1, s.getShieldedDirections()[3]);

    }

    public void testTestSetNumAstronauts() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s = new ShipBoard(availablePositionMatrix, 7, 5);
        Cabin cabin1 = new Cabin(0, true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        s.placeComponent(5,6, cabin1);
        assertEquals(2,s.getTotalAstronauts());
    }

    public void testSetSingleEnginePower() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s = new ShipBoard(availablePositionMatrix, 7, 5);
        Engine engine = new Engine(0, 1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        s.placeComponent(5, 6, engine);
        assertEquals(1,s.getSingleEnginePower());
    }

    public void testGetNumDoubleEngines() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s = new ShipBoard(availablePositionMatrix, 7, 5);
        Engine engine1 = new Engine(0,2, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        Engine engine2 = new Engine(0,2, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        Engine engine3 = new Engine(0, 2, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        s.placeComponent(5, 6, engine1);
        assertEquals(1,s.getNumDoubleEngines());
        s.placeComponent(5, 7, engine2);
        assertEquals(2,s.getNumDoubleEngines());
        s.placeComponent(5, 8, engine3);
        assertEquals(3,s.getNumDoubleEngines());
    }

    public void testSetNumDoubleEngines() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s = new ShipBoard(availablePositionMatrix, 7, 5);
        s.setNumDoubleEngines(2);
        assertEquals(2,s.getNumDoubleEngines());
        s.setNumDoubleEngines(-1);
        assertEquals(1, s.getNumDoubleEngines());
    }

    public void testGetSingleCannonPower() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Cannon c=new Cannon(0, 1,Direction.WEST,new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(5,6,c);
        assertEquals(0.5F,s.getSingleCannonPower());
    }

    public void testSetSingleCannonPower() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        s.setSingleCannonPower(1);
        assertEquals(1F,s.getSingleCannonPower());

    }

    public void testSetNumDoubleCannon() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        s.setNumDoubleCannon(1);
        assertEquals(1,s.getNumDoubleCannon());

    }

    public void testGetDeletedComponentsCounter() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Cannon c=new Cannon(0, 1,Direction.WEST,new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.EMPTY});
        s.placeComponent(5,6,c);
        s.removeComponent(5,6);
        assertEquals(1, s.getDeletedComponentsCounter());
    }

    public void testAddShieldInDirection() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        s.addShieldInDirection(Direction.NORTH);
        assertEquals(1, s.getShieldedDirections()[0]);

    }

    public void testDecreaseShieldInDirection() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        s.addShieldInDirection(Direction.NORTH);
        s.decreaseShieldInDirection(Direction.NORTH);
        assertEquals(0, s.getShieldedDirections()[0]);
    }
    public void testDecreaseShieldInDirection2() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        s.addShieldInDirection(Direction.SOUTH);
        s.decreaseShieldInDirection(Direction.SOUTH);
        assertEquals(0, s.getShieldedDirections()[0]);
    }

    public void testGetIfExposed() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Cannon c=new Cannon(0, 1,Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(9,7,c);
        Cannon c1=new Cannon(0, 1,Direction.NORTH,new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY});
        s.placeComponent(9,6,c1);
        assertTrue(s.getIfExposed(Direction.WEST, c));
        assertFalse(s.getIfExposed(Direction.SOUTH, c1));

    }
    public void testGetIfExposed2() throws RemoteException {
        Game g= new Game(1,1,1,new GameController());
        Player p= new Player("g",null);
        g.getPlayers().add(p);
        g.setPlayersShipboard();
        p.setPlayerShipboard(1);
        Cannon c=new Cannon(0, 1,Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.SINGLE, Connector.DOUBLE});
        p.getPlayerShipBoard().placeComponent(9,9,c);
        p.getPlayerShipBoard().getIfExposed(Direction.WEST, c);
        assertTrue(p.getPlayerShipBoard().getIfExposed(Direction.WEST, c));
        assertTrue(p.getPlayerShipBoard().getIfExposed(Direction.EAST, c));

        assertTrue(p.getPlayerShipBoard().getIfExposed(Direction.SOUTH, c));

    }
    public void testGetIfExposed3() throws RemoteException {
        Game g= new Game(1,1,1,new GameController());
        Player p= new Player("g",null);
        g.getPlayers().add(p);
        g.setPlayersShipboard();
        p.setPlayerShipboard(1);
        Cannon c=new Cannon(0, 1,Direction.NORTH,new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.SINGLE, Connector.DOUBLE});
        p.getPlayerShipBoard().placeComponent(5,9,c);
        p.getPlayerShipBoard().getIfExposed(Direction.WEST, c);
        assertTrue(p.getPlayerShipBoard().getIfExposed(Direction.WEST, c));
        assertTrue(p.getPlayerShipBoard().getIfExposed(Direction.EAST, c));

        assertTrue(p.getPlayerShipBoard().getIfExposed(Direction.SOUTH, c));

    }

    public void testGetTotalExposedConnectors() throws RemoteException {
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("a", null);
        Player p2 = new Player("b", null);
        Player p3 = new Player("c", null);
        players.add(p1);
        players.add(p2);
        players.add(p3);

        Game game = new Game(3, 2, 1, new GameController());
        game.getPlayers().addAll(players);
        game.setPlayersShipboard();
        p1.setPlayerShipboard(1);
//       boolean[][] availablePositionMatrix = new boolean[5][7];
//       for(int i=0; i<5; i++){
//           for(int j=0; j<7; j++){
//               availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
//           }
//       }
//       ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Cabin c1 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        Cabin c2 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c3 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c4 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c5 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c6 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c7 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c8 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c9 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL});
        p1.getPlayerShipBoard().placeComponent(7,7, c1);
        p1.getPlayerShipBoard().placeComponent(6,8, c2);
        p1.getPlayerShipBoard().placeComponent(6,9, c3);
        p1.getPlayerShipBoard().placeComponent(5,9, c4);
        p1.getPlayerShipBoard().placeComponent(5,8, c5);
        p1.getPlayerShipBoard().placeComponent(8,8, c6);
        p1.getPlayerShipBoard().placeComponent(9,8, c7);
        p1.getPlayerShipBoard().placeComponent(7,8, c8);
        p1.getPlayerShipBoard().placeComponent(9,9, c9);

        assertEquals(11, p1.getPlayerShipBoard().getTotalExposedConnectors());
    }

    public void testShieldsNotProtects() {
        boolean[][] posMatrix = {{true, true}, {true, true}};
        ShipBoard board = new ShipBoard(posMatrix, 2, 2);

        // Posiziono uno shield che protegge NORTH (0,0), una batteria a (0,1)
        Shield shield = new Shield(0, Direction.NORTH,
                new Connector[] {Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL},
                Direction.NORTH);
        BatteryStorage battery = new BatteryStorage(0, 5, Direction.NORTH,
                new Connector[] {Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        // Posiziono gli oggetti sulla board
        board.placeComponent(4, 5, shield);   // 4=x, 5=y (nella tua board, si usano queste coordinate per (0,0))
        board.placeComponent(5, 5, battery);  // (1,0)

        // Creo la lista di posizioni
        ArrayList<Points> shields = new ArrayList<>();
        shields.add(new Points(4, 5)); // Shield a (0,0)
        ArrayList<Points> batteries = new ArrayList<>();
        batteries.add(new Points(5, 5)); // Batteria a (1,0)

        // Deve restituire true perché lo shield protegge NORTH
        assertTrue(board.shieldsNotProtects(Direction.NORTH, shields, batteries));

        // Se provo su una direzione non protetta, deve restituire false
        assertFalse(board.shieldsNotProtects(Direction.SOUTH, shields, batteries));
    }
    public void testShieldNotProtects2(){
        boolean[][] posMatrix = {{true, true}, {true, true}};
        ShipBoard board = new ShipBoard(posMatrix, 2, 2);

        // Posiziono uno shield che protegge NORTH (0,0), una batteria a (0,1)
        Cannon cannon = new Cannon(0,1, Direction.NORTH,
                new Connector[] {Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        BatteryStorage battery = new BatteryStorage(0, 5, Direction.NORTH,
                new Connector[] {Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        // Posiziono gli oggetti sulla board
        board.placeComponent(4, 5, cannon);
        board.placeComponent(5, 5, battery);

        // Creo la lista di posizioni
        ArrayList<Points> shields = new ArrayList<>();
        shields.add(new Points(4, 5)); // Shield a (0,0)
        ArrayList<Points> batteries = new ArrayList<>();
        batteries.add(new Points(5, 5)); // Batteria a (1,0)

        try{
            board.shieldsNotProtects(Direction.NORTH, shields, batteries);
        }catch(InvalidParameterException e){

        }
    }
    public void testShieldsNotProtects3() {
        boolean[][] posMatrix = {{true, true,true}, {true, true,true}};
        ShipBoard board = new ShipBoard(posMatrix, 3, 3);

        // Posiziono uno shield che protegge NORTH (0,0), una batteria a (0,1)
        Shield shield = new Shield(0, Direction.NORTH,
                new Connector[] {Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL},
                Direction.NORTH);
        BatteryStorage battery = new BatteryStorage(0, 5, Direction.NORTH,
                new Connector[] {Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        // Posiziono gli oggetti sulla board
        board.placeComponent(4, 5, shield);   // 4=x, 5=y (nella tua board, si usano queste coordinate per (0,0))
        board.placeComponent(5, 5, battery);
        board.placeComponent(6,6,shield);// (1,0)

        // Creo la lista di posizioni
        ArrayList<Points> shields = new ArrayList<>();
        shields.add(new Points(4, 5));
        shields.add(new Points(6, 6));// Shield a (0,0)
        ArrayList<Points> batteries = new ArrayList<>();
        batteries.add(new Points(5, 5)); // Batteria a (1,0)

        try{
            board.shieldsNotProtects(Direction.NORTH, shields, batteries);
        }catch(BatteriesLessThenCannonException e){

        }
    }


    public void testCannonProtects() {
        // Creo una matrice 2x2 tutta valida
        boolean[][] posMatrix = {{true, true}, {true, true}};
        ShipBoard board = new ShipBoard(posMatrix, 2, 2);

        // Cannon NORTH su (0,0) colonna x=4, y=5, Battery a (0,1)
        Cannon cannon = new Cannon(4, 2, Direction.NORTH,
                new Connector[] {Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        BatteryStorage battery = new BatteryStorage(5, 5, Direction.NORTH,
                new Connector[] {Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        // Posiziono entrambi sulla board
        board.placeComponent(4, 5, cannon);
        board.placeComponent(5, 5, battery);

        // Lista cannoni e batterie
        ArrayList<Points> cannons = new ArrayList<>();
        cannons.add(new Points(4, 5)); // cannon in (0,0)
        ArrayList<Points> batteries = new ArrayList<>();
        batteries.add(new Points(5, 5)); // battery in (1,0)

        // Verifico che il cannone protegga la colonna 4 (cioè x=4, cioè colonna dello stesso cannone, direzione NORTH)
        assertTrue(board.cannonProtects(Direction.NORTH, 4, cannons, batteries));

        // Non protegge la colonna 5 (altra colonna)
        assertFalse(board.cannonProtects(Direction.NORTH, 5, cannons, batteries));
    }
    public void testCannonProtects2() {
        // Creo una matrice 2x2 tutta valida
        boolean[][] posMatrix = {{true, true}, {true, true}};
        ShipBoard board = new ShipBoard(posMatrix, 2, 2);

        // Cannon NORTH su (0,0) colonna x=4, y=5, Battery a (0,1)
        Cannon cannon = new Cannon(4, 1, Direction.NORTH,
                new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        BatteryStorage battery = new BatteryStorage(5, 5, Direction.NORTH,
                new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        // Posiziono entrambi sulla board
        board.placeComponent(4, 5, cannon);
        board.placeComponent(5, 5, battery);

        // Lista cannoni e batterie
        ArrayList<Points> cannons = new ArrayList<>();
        cannons.add(new Points(4, 5)); // cannon in (0,0)
        ArrayList<Points> batteries = new ArrayList<>();
        batteries.add(new Points(5, 5)); // battery in (1,0)
        try {
            board.cannonProtects(Direction.NORTH, 4, cannons, batteries);
        } catch (InvalidParameterException e) {

        }
    }
    public void testCannonProtects3() {
        // Creo una matrice 2x2 tutta valida
        boolean[][] posMatrix = {{true, true}, {true, true}};
        ShipBoard board = new ShipBoard(posMatrix, 2, 2);

        // Cannon NORTH su (0,0) colonna x=4, y=5, Battery a (0,1)
        Cannon cannon = new Cannon(4, 2, Direction.NORTH,
                new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        BatteryStorage battery = new BatteryStorage(5, 5, Direction.NORTH,
                new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        // Posiziono entrambi sulla board
        board.placeComponent(4, 5, cannon);
        board.placeComponent(5, 5, battery);
        board.placeComponent(5, 6, cannon);
        // Lista cannoni e batterie
        ArrayList<Points> cannons = new ArrayList<>();
        cannons.add(new Points(4, 5)); // cannon in (0,0)
        cannons.add(new Points(5, 6));
        ArrayList<Points> batteries = new ArrayList<>();
        batteries.add(new Points(5, 5)); // battery in (1,0)
        try {
            board.cannonProtects(Direction.NORTH, 4, cannons, batteries);
        } catch (BatteriesLessThenCannonException e) {

        }
    }
    public void testCannonProtects4() {
        // Creo una matrice 2x2 tutta valida
        boolean[][] posMatrix = {{true, true}, {true, true}};
        ShipBoard board = new ShipBoard(posMatrix, 2, 2);

        // Cannon NORTH su (0,0) colonna x=4, y=5, Battery a (0,1)
        Cannon cannon = new Cannon(4, 2, Direction.EAST,
                new Connector[] {Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        BatteryStorage battery = new BatteryStorage(5, 5, Direction.NORTH,
                new Connector[] {Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        // Posiziono entrambi sulla board
        board.placeComponent(4, 5, cannon);
        board.placeComponent(5, 5, battery);

        // Lista cannoni e batterie
        ArrayList<Points> cannons = new ArrayList<>();
        cannons.add(new Points(4, 5)); // cannon in (0,0)
        ArrayList<Points> batteries = new ArrayList<>();
        batteries.add(new Points(5, 5)); // battery in (1,0)

        // Verifico che il cannone protegga la colonna 4 (cioè x=4, cioè colonna dello stesso cannone, direzione NORTH)
        assertTrue(board.cannonProtects(Direction.EAST, 4, cannons, batteries));

        // Non protegge la colonna 5 (altra colonna)
        assertTrue(board.cannonProtects(Direction.EAST, 4, cannons, batteries));
    }
    public void testCannonProtects5() {
        // Creo una matrice 2x2 tutta valida
        boolean[][] posMatrix = {{true, true}, {true, true}};
        ShipBoard board = new ShipBoard(posMatrix, 2, 2);

        // Cannon NORTH su (0,0) colonna x=4, y=5, Battery a (0,1)
        Cannon cannon = new Cannon(4, 2, Direction.WEST,
                new Connector[] {Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        BatteryStorage battery = new BatteryStorage(5, 5, Direction.NORTH,
                new Connector[] {Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        // Posiziono entrambi sulla board
        board.placeComponent(4, 5, cannon);
        board.placeComponent(5, 5, battery);

        // Lista cannoni e batterie
        ArrayList<Points> cannons = new ArrayList<>();
        cannons.add(new Points(4, 5)); // cannon in (0,0)
        ArrayList<Points> batteries = new ArrayList<>();
        batteries.add(new Points(5, 5)); // battery in (1,0)

        // Verifico che il cannone protegga la colonna 4 (cioè x=4, cioè colonna dello stesso cannone, direzione NORTH)
        assertTrue(board.cannonProtects(Direction.WEST, 4, cannons, batteries));

        // Non protegge la colonna 5 (altra colonna)
        assertTrue(board.cannonProtects(Direction.WEST, 4, cannons, batteries));
    }
    public void testCannonProtects6() {
        // Creo una matrice 2x2 tutta valida
        boolean[][] posMatrix = {{true, true}, {true, true}};
        ShipBoard board = new ShipBoard(posMatrix, 2, 2);

        // Cannon NORTH su (0,0) colonna x=4, y=5, Battery a (0,1)
        Cannon cannon = new Cannon(4, 2, Direction.SOUTH,
                new Connector[] {Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        BatteryStorage battery = new BatteryStorage(5, 5, Direction.NORTH,
                new Connector[] {Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});

        // Posiziono entrambi sulla board
        board.placeComponent(4, 5, cannon);
        board.placeComponent(5, 5, battery);

        // Lista cannoni e batterie
        ArrayList<Points> cannons = new ArrayList<>();
        cannons.add(new Points(4, 5)); // cannon in (0,0)
        ArrayList<Points> batteries = new ArrayList<>();
        batteries.add(new Points(5, 5)); // battery in (1,0)

        // Verifico che il cannone protegga la colonna 4 (cioè x=4, cioè colonna dello stesso cannone, direzione NORTH)
        assertTrue(board.cannonProtects(Direction.SOUTH, 5, cannons, batteries));

        // Non protegge la colonna 5 (altra colonna)
        assertTrue(board.cannonProtects(Direction.SOUTH, 5, cannons, batteries));
    }


    public void testTestGetIfSingleCannon() {
    }
}