package org.example.ServerPkg.Model.ComponentsPkg;

import junit.framework.TestCase;
import org.example.ServerPkg.Model.ForView.ComponentsView;
import org.example.ServerPkg.Model.ShipBoard;

public class LifeSupportSystemTest extends TestCase {

    public void testGetColour() {
        LifeSupportSystem l = new LifeSupportSystem(0,AlienColour.BROWN, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertEquals(AlienColour.BROWN, l.getColour());
    }

    public void testRemove() {
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
        Cabin c = new Cabin(0, false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        LifeSupportSystem l = new LifeSupportSystem(0, AlienColour.BROWN, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(5,7,c);
        s.placeComponent(6,7,l);
        l.remove(s);
        assertTrue(c.getLifeSupportSystemArrayList().isEmpty());
    }
    public void testRemoveSouth() {
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
        Cabin c = new Cabin(0, false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        LifeSupportSystem l = new LifeSupportSystem(0, AlienColour.BROWN, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(6,7,c);
        s.placeComponent(6,8,l);
        l.remove(s);
        assertTrue(c.getLifeSupportSystemArrayList().isEmpty());
    }
    public void testRemoveEast() {
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
        Cabin c = new Cabin(0, false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        LifeSupportSystem l = new LifeSupportSystem(0, AlienColour.BROWN, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(6,7,c);
        s.placeComponent(5,7,l);
        l.remove(s);
        assertTrue(c.getLifeSupportSystemArrayList().isEmpty());
    }
    public void testRemoveWest() {
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
        Cabin c = new Cabin(0, false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        LifeSupportSystem l = new LifeSupportSystem(0, AlienColour.BROWN, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(6,8,c);
        s.placeComponent(6,7,l);
        l.remove(s);
        assertTrue(c.getLifeSupportSystemArrayList().isEmpty());
    }

    public void testPlace() {
        boolean[][] availablePositionMatrix = new boolean[5][7];
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                availablePositionMatrix[i][j] = (i != 0 || j != 0) && (i != 0 || j != 1) && (i != 1 || j != 0) && (i != 3 || j != 0) && (i != 5 || j != 0) && (i != 6 || j != 0) && (i != 6 || j != 1) && (i != 3 || j != 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Cabin c = new Cabin(0, false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        LifeSupportSystem l = new LifeSupportSystem(0, AlienColour.BROWN, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        s.placeComponent(5,7,c);
        s.placeComponent(4,7,l);
        assertTrue(c.getLifeSupportSystemArrayList().contains(l));
    }

    public void testAddLifeSupport() {
        Cabin c = new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        LifeSupportSystem l = new LifeSupportSystem(0, AlienColour.BROWN, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        l.addLifeSupport(c);
        assertTrue(c.getLifeSupportSystemArrayList().contains(l));
    }

    public void testCreateView() {
        int id = 42;
        AlienColour colour = AlienColour.PURPLE;
        Direction dir = Direction.EAST;
        Connector[] connectors = new Connector[] {
                Connector.SINGLE, Connector.EMPTY, Connector.UNIVERSAL, Connector.DOUBLE
        };
        LifeSupportSystem lss = new LifeSupportSystem(id, colour, dir, connectors);

        // Imposta la posizione del componente
        lss.setPosition(7, 8); // x, y

        // Act: chiama createView
        ComponentsView view = lss.createView();

        // Assert: controlla che tutti i campi siano coerenti
        assertEquals(7, view.getPosX());
        assertEquals(8, view.getPosY());
        assertEquals(dir, view.getDirection());
        assertEquals(id, view.getId());
        assertEquals("LifeSupportSystem", view.getType());
        assertEquals(colour, view.getAlienColour());
        // Controlla i connettori (array)
        assertEquals(connectors.length, view.getConnectors().length);
        for (int i = 0; i < connectors.length; i++) {
            assertEquals(connectors[i], view.getConnectors()[i]);
        }
        // Altri assert possibili (battery, astronauts, goods)
        assertEquals(0, view.getNumBattery());
        assertEquals(0, view.getNumAstronauts());
        assertNull(view.getGoods());
    }
}