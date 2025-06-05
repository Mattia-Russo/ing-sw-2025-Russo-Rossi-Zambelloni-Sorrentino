package org.example.ServerPkg.ControllerPkg.PlayerStates;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.rmi.RemoteException;

public class BuildShipStateTest extends TestCase {

    public void testTurnTimer() throws InterruptedException, RemoteException {
        GameController g=new GameController();
        g.createLobby("ciao", 2, 2, 1);
        g.joinLobby("ciao2");
        g.startGame();
        Player p1=null;
        Player p2=null;
        for(Player p: g.getGame().getPlayers()){
            if(p.getName().equals("ciao2")){
                p2=p;
            }
            if(p.getName().equals("ciao")){
                p1=p;
            }
        }
        p1.getState().pickComponentTile(p1);
        p1.getState().placeTile(p1, new Points(2,1));
        p2.getState().pickComponentTile(p2);
        p2.getState().placeTile(p2, new Points(2,1));
        p1.getState().turnTimer(p1);
        p1.getState().turnTimer(p1);
        p1.getState().turnTimer(p1);
        p1.getState().endBuildShip(p1);
        p1.getState().turnTimer(p1);
        System.out.println(p2.getState());
    }


    public void testShowDeck() {

    }


    public void testEndShowDeck() {
    }


    public void testPickComponentTile() {
    }


    public void testRightRotateTile() {
    }

    public void testLeftRotateTile() {
    }

    public void testPlaceTile() {
    }

    public void testEndBuildShip() {
    }
}