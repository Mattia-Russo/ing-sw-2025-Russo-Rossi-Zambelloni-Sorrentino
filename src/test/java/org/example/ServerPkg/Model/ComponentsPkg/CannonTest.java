package org.example.ServerPkg.Model.ComponentsPkg;

import junit.framework.TestCase;
import org.example.ServerPkg.Model.ForView.ComponentsView;
import org.example.ServerPkg.Model.ShipBoard;

public class CannonTest extends TestCase {

    public void testGetPower() {
        Cannon c = new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertEquals(1, c.getPower());
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
        Cannon doubleCannon= new Cannon(1,2,Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.UNIVERSAL});
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
        s.placeComponent(9,8, c7);
        s.placeComponent(8,9, e1);
        s.placeComponent(9,9, c9);
        s.placeComponent(7,8, c8);
        s.placeComponent(6,6, doubleCannon);

        assertEquals(1.5F, s.getSingleCannonPower());
        cannon.remove(s);
        assertEquals(0.5F, s.getSingleCannonPower());
        cannon1.remove(s);
        assertEquals(0F, s.getSingleCannonPower());
        doubleCannon.remove(s);
        assertEquals(0, s.getNumDoubleCannon());
    }

    public void testPlace() {

    }

    public void testIsDoubleCannon() {
        Cannon cannon1 = new Cannon(0,2, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cannon cannon2 = new Cannon(0,1, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        assertEquals(cannon1, cannon1.isDoubleCannon());
        assertNull(cannon2.isDoubleCannon());
    }


    public void testCheckRightCannon() {
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
        s.placeComponent(9,8, c7);
        s.placeComponent(8,9, e1);
        s.placeComponent(9,9, c9);
        s.placeComponent(7,8, c8);

        assertFalse(cannon.checkRightCannon(s));
        assertTrue(cannon1.checkRightCannon(s));


    }
    public void testCheckRightCannon_North_True() {
        boolean[][] m = new boolean[5][7];
        for(int i=0;i<5;i++) for(int j=0;j<7;j++) m[i][j]=true;
        ShipBoard ship = new ShipBoard(m,7,5);

        // Cannon puntato a nord
        Cannon cannon = new Cannon(0, 1,Direction.NORTH, new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY});
        ship.placeComponent(7, 7, cannon);

        // Metti un componente sopra
        Cabin sopra = new Cabin(0, false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY});
        ship.placeComponent(7, 6, sopra);

        assertTrue(cannon.checkRightCannon(ship));
    }
    public void testCheckRightCannon_East_True() {
        boolean[][] m = new boolean[5][7];
        for(int i=0;i<5;i++) for(int j=0;j<7;j++) m[i][j]=true;
        ShipBoard ship = new ShipBoard(m,7,5);

        // Cannon puntato a est
        Cannon cannon = new Cannon(0,1, Direction.EAST, new Connector[]{Connector.EMPTY, Connector.SINGLE, Connector.EMPTY, Connector.EMPTY});
        ship.placeComponent(7, 7, cannon);

        // Metti un componente a destra
        Cabin destra = new Cabin(0, false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY});
        ship.placeComponent(8, 7, destra);

        assertTrue(cannon.checkRightCannon(ship));
    }
    public void testCheckRightCannon_West_True() {
        boolean[][] m = new boolean[5][7];
        for(int i=0;i<5;i++) for(int j=0;j<7;j++) m[i][j]=true;
        ShipBoard ship = new ShipBoard(m,7,5);

        // Cannon puntato a ovest
        Cannon cannon = new Cannon(0,1, Direction.WEST, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.EMPTY, Connector.SINGLE});
        ship.placeComponent(7, 7, cannon);

        // Metti un componente a sinistra
        Cabin sinistra = new Cabin(0, false, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY});
        ship.placeComponent(6, 7, sinistra);

        assertTrue(cannon.checkRightCannon(ship));
    }

    public void testIsSingleCannon() {
        Cannon cannon1 = new Cannon(0,2, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        Cannon cannon2 = new Cannon(0,1, Direction.SOUTH, new Connector[]{Connector.SINGLE, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL});
        assertEquals(cannon2, cannon2.isSingleCannon());
        assertNull(cannon1.isSingleCannon());
    }

    public void testCreateView() {
        Connector[] connectors = new Connector[] {
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        };
        Cannon cannon = new Cannon(42, 1, Direction.NORTH, connectors);
        cannon.setPosition(7, 6); // esempio posizione

        ComponentsView view = cannon.createView();

        assertEquals(7, view.getPosX());
        assertEquals(6, view.getPosY());
        assertEquals(Direction.NORTH, view.getDirection());
        assertEquals(42, view.getId());
        assertEquals("Cannon", view.getType());
        assertEquals(0, view.getNumBattery());
        assertEquals(0, view.getNumAstronauts());
        assertNull(view.getGoods());
        assertNull(view.getAlienColour());
        assertEquals(4, view.getConnectors().length);
        assertEquals(Connector.SINGLE, view.getConnectors()[0]);

        Connector[] connectors2 = new Connector[] {
                Connector.DOUBLE, Connector.UNIVERSAL, Connector.EMPTY, Connector.EMPTY
        };
        Cannon doubleCannon2 = new Cannon(99, 2, Direction.EAST, connectors2);
        doubleCannon2.setPosition(5, 10); // esempio posizione

        ComponentsView view2 = doubleCannon2.createView();

        assertEquals(5, view2.getPosX());
        assertEquals(10, view2.getPosY());
        assertEquals(Direction.EAST, view2.getDirection());
        assertEquals(99, view2.getId());
        assertEquals("DoubleCannon", view2.getType());
        assertEquals(0, view2.getNumBattery());
        assertEquals(0, view2.getNumAstronauts());
        assertNull(view2.getGoods());
        assertNull(view2.getAlienColour());
        assertEquals(4, view2.getConnectors().length);
        assertEquals(Connector.DOUBLE, view2.getConnectors()[0]);
        assertEquals(Connector.UNIVERSAL, view2.getConnectors()[1]);
    }

}