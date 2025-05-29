package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.InvalidLobbyStateException;
import org.example.ServerPkg.Model.Exceptions.InvalidMinimumNumberPlayerException;

import java.rmi.RemoteException;

public class StartGameMessage extends Message {
    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(playerName.equals(controller.getGame().getPlayers().getFirst().getName())){
            if(checkClient()){
                try{
                    controller.startGame();
                }catch(InvalidMinimumNumberPlayerException | InvalidLobbyStateException e){
                    getServer().notifyClient(playerName, "Error: " + e.getMessage());
                    System.out.println("Error, tried to start the lobby with too less players");
                }
            }
        } else {
            System.out.println("Only the creator can start the game");
        }
   }
}
