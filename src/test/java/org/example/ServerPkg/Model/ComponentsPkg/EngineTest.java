package org.example.ServerPkg.Model.ComponentsPkg;

import junit.framework.TestCase;
import org.example.ServerPkg.Model.ForView.ComponentsView;
import org.example.ServerPkg.Model.ShipBoard;

public class EngineTest extends TestCase {

    public void testGetPower() {
        Engine e = new Engine(0,2, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertEquals(2, e.getPower());
    }

    public void testRemove() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++) {
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        Cabin c1 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Storage s1 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s2 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c2 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c3 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin c4 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL});
        Cabin c5 = new Cabin(0,false, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cannon cannon1 = new Cannon(0,1, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.DOUBLE});
        Cabin c6 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c7 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cabin c8 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Cabin c9 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e1 = new Engine(0,1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine e2 = new Engine(0,1, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});
        Engine e3 = new Engine(0,2, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.DOUBLE});

        ShipBoard s = new ShipBoard(availablePositionMatrix, 7, 5);

        s.placeComponent(7,7, c1);
        s.placeComponent(6,7, s2);
        s.placeComponent(8,7, s1);
        s.placeComponent(8,6, cannon);
        s.placeComponent(5,8, cannon1);
        s.placeComponent(6,8, c2);
        s.placeComponent(5,9, c4);
        s.placeComponent(6,9, c3);
        s.placeComponent(8,8, c6);
        s.placeComponent(9,8, e2);
        s.placeComponent(8,9, e1);
        s.placeComponent(9,9, c9);
        s.placeComponent(7,8, c8);
        s.placeComponent(6,6,e3);

        assertEquals(2, s.getSingleEnginePower());
        e1.remove(s);
        assertEquals(1, s.getSingleEnginePower());
        e2.remove(s);
        assertEquals(0, s.getSingleEnginePower());
        e3.remove(s);
        assertEquals(0, s.getNumDoubleEngines());
    }

    public void testPlace() {
    }

    public void testIsDoubleEngine() {
        Engine cannon1 = new Engine(0,2, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Engine cannon2 = new Engine(0,1, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        assertEquals(cannon1, cannon1.isDoubleEngine());
        assertNull(cannon2.isDoubleEngine());
    }

    public void testCheckRightEngine() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++) {
                availablePositionMatrix[i][j] = (j != 0 || i != 0) && (j != 0 || i != 1) && (j != 1 || i != 0) && (j!= 3 || i != 0) && (j != 5 || i != 0) && (j != 6 || i != 0) && (j != 6 || i != 1) && (j != 3 || i!= 4);
            }
        }
        Cabin c1 = new Cabin(0,true, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL, Connector.DOUBLE});
        Storage s1 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.SINGLE}, 3);
        Cannon cannon = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
        Storage s2 = new Storage(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY}, 2);
        Cabin c2 = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE});
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

        s.placeComponent(7,7, c1);
        s.placeComponent(6,7, s2);
        s.placeComponent(8,7, s1);
        s.placeComponent(8,6, cannon);
        s.placeComponent(5,8, cannon1);
        s.placeComponent(6,8, c2);
        s.placeComponent(5,9, c4);
        s.placeComponent(6,9, e2);
        s.placeComponent(8,8, c6);
        s.placeComponent(9,8, c7);
        s.placeComponent(8,9, e1);
        s.placeComponent(9,9, c9);
        s.placeComponent(7,8, c8);
        s.placeComponent(6, 6, e3);

        assertFalse(e1.checkRightEngine(s));
        assertTrue(e2.checkRightEngine(s));
        assertTrue(e3.checkRightEngine(s));
    }

    public void testCreateView() {
        int id = 42;
        int power = 2;
        Direction dir = Direction.WEST;
        Connector[] connectors = new Connector[] {
                Connector.DOUBLE, Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL
        };

        Engine engine = new Engine(id, power, dir, connectors);
        engine.setPosition(9, 7);

        // Act
        ComponentsView view = engine.createView();

        // Assert
        assertNotNull(view);
        assertEquals(9, view.getPosX());
        assertEquals(7, view.getPosY());
        assertEquals(dir, view.getDirection());
        assertEquals(id, view.getId());
        assertEquals("DoubleEngine", view.getType());
        assertEquals(4, view.getConnectors().length);
        assertEquals(Connector.DOUBLE, view.getConnectors()[0]);
        assertEquals(Connector.SINGLE, view.getConnectors()[1]);
        assertEquals(Connector.EMPTY, view.getConnectors()[2]);
        assertEquals(Connector.UNIVERSAL, view.getConnectors()[3]);
        assertEquals(0, view.getNumBattery());
        assertEquals(0, view.getNumAstronauts());
        assertNull(view.getGoods());
        assertNull(view.getAlienColour());
    }

}