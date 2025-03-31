package org.example.Server.Model.CardPack;

import junit.framework.TestCase;
import org.example.Server.Model.ComponentsPack.Cabin;
import org.example.Server.Model.ComponentsPack.Connector;
import org.example.Server.Model.ComponentsPack.Direction;
import org.example.Server.Model.ShipBoard;

public class EpidemicTest extends TestCase {

    public void testCheckAdjacentCabins() {
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
        Cabin cabin2 = new Cabin(false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.SINGLE, Connector.EMPTY, Connector.DOUBLE});
        s.placeComponent(1,1, cabin1);
        s.placeComponent(2,1, cabin2);
        Epidemic epidemic = new Epidemic(1, 0);
        epidemic.checkAdjacentCabins(s);
        assertEquals(1, cabin1.getNumAstronauts());
        assertEquals(1, cabin2.getNumAstronauts());
    }
}