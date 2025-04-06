package org.example.Server.Model.CardPack;

import junit.framework.TestCase;
import org.example.Server.Model.Game;
import org.example.Server.Model.Player;
import org.example.Server.Model.ShipBoard;

import java.util.ArrayList;

public class OpenSpaceTest extends TestCase {

    public void testCheckEnginePower() {
        Player p1 = new Player(12, "a");
        Player p2 = new Player( 7, "a");
        Player p3 = new Player( 14, "a");
        Player p4 = new Player( 9, "a");
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        Game g=new Game(4,1, players,1, 30);
        ShipBoard s1 = p1.getPlayerShipBoard();
        ShipBoard s2 = p2.getPlayerShipBoard();
        ShipBoard s3 = p3.getPlayerShipBoard();
        ShipBoard s4 = p4.getPlayerShipBoard();

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

        OpenSpace openSpace = new OpenSpace(2, 1);
        openSpace.checkEnginePower(players);

        assertFalse(p1.isAbandoned());
        assertTrue(p2.isAbandoned());
        assertFalse(p3.isAbandoned());
        assertTrue(p4.isAbandoned());
    }
}