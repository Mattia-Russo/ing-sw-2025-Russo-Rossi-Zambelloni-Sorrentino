package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.InvalidLobbyStateException;
import org.example.ServerPkg.Model.Player;

import java.rmi.RemoteException;

public class ExitGameMessage extends Message{
    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()){
            if(controller.getGame().getPlayers().contains(controller.getGame().getPlayerByName(playerName))){
                try {
                    Player player= controller.getGame().getPlayerByName(playerName);
                    controller.exitGame(player);
                } catch (InvalidLobbyStateException e){
                    System.out.println("ERROR " + e.getMessage());
                }
            } else {
                System.out.println("Join a game first");
            }
        }
    }
}
