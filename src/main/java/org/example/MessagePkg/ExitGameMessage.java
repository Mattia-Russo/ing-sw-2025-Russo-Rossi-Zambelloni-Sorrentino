package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.InvalidLobbyStateException;

import java.rmi.RemoteException;

public class ExitGameMessage extends Message{
    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()){
            if(controller.getGame().getPlayers().contains(controller.getGame().getPlayerByName(playerName))){
                try {
                    controller.exitGame(controller.getGame().getPlayerByName(playerName));
                } catch (InvalidLobbyStateException e){
                    System.out.println("ERROR " + e.getMessage());
                }
            } else {
                System.out.println("Join a game first");
            }
        }
    }
}
