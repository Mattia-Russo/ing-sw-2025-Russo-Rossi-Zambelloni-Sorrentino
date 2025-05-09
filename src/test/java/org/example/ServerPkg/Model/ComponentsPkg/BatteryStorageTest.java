package org.example.ServerPkg.Model.ComponentsPkg;

import junit.framework.TestCase;
import org.example.ServerPkg.Model.ShipBoard;

public class BatteryStorageTest extends TestCase {

    public void testGetQuantity() {
        BatteryStorage b = new BatteryStorage(0,3, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertEquals(0, b.getQuantity());
    }

    public void testGetCapacity() {
        BatteryStorage b = new BatteryStorage(0,3, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertEquals(3, b.getCapacity());

    }

    public void testSetQuantity() {

        boolean[][] availablePositionMatrix = new boolean[7][5];
        for(int i=0; i<7; i++){
            for(int j=0; j<5; j++) {
                availablePositionMatrix[i][j] = (i != 0 || j != 0) && (i != 0 || j != 1) && (i != 1 || j != 0) && (i != 3 || j != 0) && (i != 5 || j != 0) && (i != 6 || j != 0) && (i != 6 || j != 1) && (i != 3 || j != 4);
            }
        }
        Cabin c1 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Storage s1 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s2 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        BatteryStorage bs1 = new BatteryStorage(0,3, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        BatteryStorage bs2 = new BatteryStorage(0,2, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c3 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c4 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c5 = new Cabin(0,false, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cannon cannon1 = new Cannon(0,1, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c6 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c7 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c8 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c9 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e1 = new Engine(0,1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e2 = new Engine(0,1, Direction.EAST, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Engine e3 = new Engine(0,1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.UNIVERSAL, Connector.DOUBLE});

        ShipBoard s = new ShipBoard(availablePositionMatrix, 7, 5);

        s.placeComponent(3,2, c1);
        s.placeComponent(2,2, s2);
        s.placeComponent(4,2, s1);
        s.placeComponent(4,1, cannon);
        s.placeComponent(1,3, cannon1);
        s.placeComponent(2,3, bs1);
        s.placeComponent(1,4, c4);
        s.placeComponent(2,4, e2);
        s.placeComponent(4,3, c6);
        s.placeComponent(5,3, c7);
        s.placeComponent(4,4, e1);
        s.placeComponent(5,4, c9);
        s.placeComponent(3,3, bs2);
        s.placeComponent(2, 1, e3);

        assertEquals(3, bs1.getQuantity());
        bs1.setQuantity(-2, s);
        assertEquals(1, bs1.getQuantity());
        assertEquals(3, s.getTotalBattery());
    }

    public void testIsBatteryStorage() {
        BatteryStorage b = new BatteryStorage(0,2, Direction.SOUTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertEquals(b, b.isBatteryStorage());
    }

    public void testPlace() {
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for(int i=0; i<7; i++){
            for(int j=0; j<5; j++) {
                availablePositionMatrix[i][j] = (i != 0 || j != 0) && (i != 0 || j != 1) && (i != 1 || j != 0) && (i != 3 || j != 0) && (i != 5 || j != 0) && (i != 6 || j != 0) && (i != 6 || j != 1) && (i != 3 || j != 4);
            }
        }
        Cabin c1 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Storage s1 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s2 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        BatteryStorage bs1 = new BatteryStorage(0,2, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        BatteryStorage bs2 = new BatteryStorage(0,3, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c3 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c4 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c5 = new Cabin(0,false, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cannon cannon1 = new Cannon(0,1, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c6 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c7 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c8 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c9 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e1 = new Engine(0,1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e2 = new Engine(0,1, Direction.EAST, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Engine e3 = new Engine(0,1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.UNIVERSAL, Connector.DOUBLE});

        ShipBoard s = new ShipBoard(availablePositionMatrix, 7, 5);

        s.placeComponent(3,2, c1);
        s.placeComponent(2,2, s2);
        s.placeComponent(4,2, s1);
        s.placeComponent(4,1, cannon);
        s.placeComponent(1,3, cannon1);
        s.placeComponent(2,3, bs1);
        s.placeComponent(1,4, c4);
        s.placeComponent(2,4, e2);
        s.placeComponent(4,3, c6);
        s.placeComponent(5,3, c7);
        s.placeComponent(4,4, e1);
        s.placeComponent(5,4, c9);

        assertEquals(2, s.getTotalBattery());

        s.placeComponent(3,3, bs2);
        s.placeComponent(2, 1, e3);

        assertEquals(5, s.getTotalBattery());
    }

    public void testRemove() {
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for(int i=0; i<7; i++){
            for(int j=0; j<5; j++) {
                availablePositionMatrix[i][j] = (i != 0 || j != 0) && (i != 0 || j != 1) && (i != 1 || j != 0) && (i != 3 || j != 0) && (i != 5 || j != 0) && (i != 6 || j != 0) && (i != 6 || j != 1) && (i != 3 || j != 4);
            }
        }
        Cabin c1 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Storage s1 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s2 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        BatteryStorage bs1 = new BatteryStorage(0,2, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        BatteryStorage bs2 = new BatteryStorage(0,3, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c3 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c4 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c5 = new Cabin(0,false, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cannon cannon1 = new Cannon(0,1, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c6 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c7 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c8 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c9 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e1 = new Engine(0,1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e2 = new Engine(0,1, Direction.EAST, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Engine e3 = new Engine(0,1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.UNIVERSAL, Connector.DOUBLE});

        ShipBoard s = new ShipBoard(availablePositionMatrix, 7, 5);

        s.placeComponent(3,2, c1);
        s.placeComponent(2,2, s2);
        s.placeComponent(4,2, s1);
        s.placeComponent(4,1, cannon);
        s.placeComponent(1,3, cannon1);
        s.placeComponent(2,3, bs1);
        s.placeComponent(1,4, c4);
        s.placeComponent(2,4, e2);
        s.placeComponent(4,3, c6);
        s.placeComponent(5,3, c7);
        s.placeComponent(4,4, e1);
        s.placeComponent(5,4, c9);
        s.placeComponent(3,3, bs2);
        s.placeComponent(2, 1, e3);

        assertEquals(5, s.getTotalBattery());
        bs1.setQuantity(-1, s);
        assertEquals(4, s.getTotalBattery());
        bs1.remove(s);
        assertEquals(3, s.getTotalBattery());
        bs2.remove(s);
        assertEquals(0, s.getTotalBattery());

    }
}