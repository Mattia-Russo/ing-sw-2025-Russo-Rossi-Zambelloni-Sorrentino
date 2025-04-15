package org.example.ServerPkg.Model.CardPack;

import junit.framework.TestCase;
import org.example.ServerPkg.Model.ComponentsPack.Cabin;
import org.example.ServerPkg.Model.ComponentsPack.Connector;
import org.example.ServerPkg.Model.ComponentsPack.Direction;
import org.example.ServerPkg.Model.ShipBoard;

public class EpidemicTest extends TestCase {

    public void testCheckAdjacentCabins() {
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for(int i=0; i<7; i++){
            for(int j=0; j<5; j++){
                availablePositionMatrix[i][j] = (i != 0 || j != 0) && (i != 0 || j != 1) && (i != 1 || j != 0) && (i != 3 || j != 0) && (i != 5 || j != 0) && (i != 6 || j != 0) && (i != 6 || j != 1) && (i != 3 || j != 4);
            }
        }
        ShipBoard s=new ShipBoard(availablePositionMatrix, 7, 5);
        Cabin cabin1 = new Cabin(true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        Cabin cabin2 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        s.placeComponent(1,1, cabin1);
        s.placeComponent(2,1, cabin2);
        Epidemic epidemic = new Epidemic(1,0);
        //epidemic.checkAdjacentCabins(s);
        assertEquals(1, cabin1.getNumAstronauts());
        assertEquals(1, cabin2.getNumAstronauts());
    }

    public void testGetCardLevel() {
        Epidemic epidemic = new Epidemic(1,0);
        assertEquals(1,epidemic.getCardLevel());
    }

    public void testGetLostDays() {
        Epidemic epidemic = new Epidemic(1,0);
        assertEquals(0,epidemic.getLostDays());
    }

    public void testSetCardState() {

    }

    public void testPlayCard() {
    }
}