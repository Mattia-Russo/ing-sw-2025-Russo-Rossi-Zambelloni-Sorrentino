package org.example.Model.CardPack.CardPack;

import junit.framework.TestCase;
import org.example.Model.CardPack.OpenSpace;
import org.example.Model.Player;
import org.example.Model.ShipBoard;

import java.util.ArrayList;

public class OpenSpaceTest extends TestCase {

    public void testCheckEnginePower() {
        boolean[][] availablePositionMatrix = new boolean[7][5];
        for(int i=0; i<7; i++){
            for(int j=0; j<5; j++) {
                availablePositionMatrix[i][j] = (i != 0 || j != 0) && (i != 0 || j != 1) && (i != 1 || j != 0) && (i != 3 || j != 0) && (i != 5 || j != 0) && (i != 6 || j != 0) && (i != 6 || j != 1) && (i != 3 || j != 4);
            }
        }

        ShipBoard s1 = new ShipBoard(availablePositionMatrix, 7, 5);
        ShipBoard s2 = new ShipBoard(availablePositionMatrix, 7, 5);
        ShipBoard s3 = new ShipBoard(availablePositionMatrix, 7, 5);
        ShipBoard s4 = new ShipBoard(availablePositionMatrix, 7, 5);

        s1.setSingleEnginePower(1);
        s2.setSingleEnginePower(0);
        s2.setNumDoubleEngines(0);
        s2.setTotalBattery(0);
        s3.setSingleEnginePower(0);
        s3.setNumDoubleEngines(1);
        s3.setTotalBattery(1);
        s4.setSingleEnginePower(0);
        s4.setNumDoubleEngines(0);
        s4.setTotalBattery(0);

        Player p1 = new Player(s1, 12, "a");
        Player p2 = new Player(s2, 7, "a");
        Player p3 = new Player(s3, 14, "a");
        Player p4 = new Player(s4, 9, "a");

        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        OpenSpace openSpace = new OpenSpace(2, 1);
        openSpace.checkEnginePower(players);

        assertFalse(p1.isAbandoned());
        assertTrue(p2.isAbandoned());
        assertFalse(p3.isAbandoned());
        assertTrue(p4.isAbandoned());
    }
}