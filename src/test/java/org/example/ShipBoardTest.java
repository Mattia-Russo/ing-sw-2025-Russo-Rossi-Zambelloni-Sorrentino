package org.example;

import junit.framework.TestCase;
import org.example.ComponentsPack.*;

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
        ShipBoard s=new ShipBoard(availablePositionMatrix, 8, 10);
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
        ShipBoard s=new ShipBoard(availablePositionMatrix, 8, 10);
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
        ShipBoard s=new ShipBoard(availablePositionMatrix, 8, 10);
        assertEquals(0,s.getCounter());
    }

    public void testChangeCounter() {
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
        ShipBoard s=new ShipBoard(availablePositionMatrix, 8, 10);
        s.changeCounter(1);
        assertEquals(1,s.getCounter());
    }

    public void testBookComponents() {
    }

    public void testGetTotalBattery() {
    }

    public void testAddBatteries() {
    }

    public void testRemoveBatteries() {
    }

    public void testGetTotalGoods() {
    }

    public void testGetTotalAstronauts() {
    }

    public void testGetTotalCannonPower() {
    }

    public void testGetTotalEngineStrenght() {
    }

    public void testGetNumDoubleCannon() {
    }

    public void testGetNumDoubleEngine() {
    }

    public void testGetIfShielded() {
    }

    public void testRemoveComponent() {
    }

    public void testFindConnectedComponents() {
    }

    public void testGetFirstComponent() {
    }

    public void testGetIfSingleCannon() {
    }

    public void testGetIfDoubleCannon() {
    }

    public void testPlaceComponent() {
    }

    public void testGetIfAlienSupported() {
    }
}