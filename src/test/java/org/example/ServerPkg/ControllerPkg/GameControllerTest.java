package org.example.ServerPkg.ControllerPkg;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.PlayerStates.BuildShipState;
import org.example.ServerPkg.Model.Exceptions.InvalidAddPlayerException;
import org.example.ServerPkg.Model.Exceptions.InvalidUserNameException;
import org.example.ServerPkg.Model.Player;

import java.rmi.RemoteException;

public class GameControllerTest extends TestCase{

    public void testGetGame() throws RemoteException {
        GameController g=new GameController();
        g.createLobby("ciao", 2, 1, 0);
        assertNotNull(g.getGame());
    }

    public void testSetLobbyState() {
        GameController g=new GameController();
        g.setLobbyState(LobbyState.GAME_FINISHED);
        assertEquals(LobbyState.GAME_FINISHED, g.getLobbyState());
    }

    public void testExitGame() throws RemoteException {
        GameController g=new GameController();
        g.createLobby("ciao", 2, 1, 0);
        g.setLobbyState(LobbyState.GAME_FINISHED);
        Player player= null;
        for(Player p: g.getGame().getPlayers()){
            if(p.getName().equals("ciao")){
                player = p;
            }
        }
        g.exitGame(player);
        assertNull(g.getGame());
    }


    public void testStartGame() throws RemoteException {
        GameController g=new GameController();
        g.createLobby("ciao", 2, 1, 0);
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
        assertTrue(p1.getState() instanceof BuildShipState);
        assertTrue(p2.getState() instanceof BuildShipState);

    }

    public void testJoinLobby() throws RemoteException {
        GameController g=new GameController();
        g.createLobby("ciao", 2, 1, 0);
        g.joinLobby("ciao2");
        try {
            g.joinLobby("ciao");
        }catch(InvalidUserNameException e){
            System.out.println(e.getMessage());
        }

        try {
            g.joinLobby("ciao3");
        }catch(InvalidAddPlayerException e){
            System.out.println(e.getMessage());
        }

    }


    public void testCreateLobby() throws RemoteException {
        GameController g=new GameController();
        g.createLobby("ciao", 2, 1, 0);
        assertNotNull(g.getGame());
    }
}