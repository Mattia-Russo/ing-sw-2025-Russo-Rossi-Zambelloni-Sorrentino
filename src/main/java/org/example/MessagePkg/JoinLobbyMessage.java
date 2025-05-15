package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.InvalidGameCreationException;
import org.example.ServerPkg.Model.Exceptions.InvalidLobbyStateException;
import org.example.UIPkg.RMIVirtualView;
import org.example.UIPkg.TUI;

import java.rmi.RemoteException;

public class JoinLobbyMessage extends Message{

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()) {
            try{
                if(super.getProxy()!=null) {
                    controller.joinLobby(playerName);
                    getProxy().addGameUpdater(controller);
                    System.out.println(playerName + " joined the lobby successfully");
                } else if (super.getClient()!=null){
                    controller.joinLobby(playerName);
                    super.getServer().addGameUpdater(controller, playerName);
                    System.out.println(playerName + " joined the lobby successfully");
                }
            } catch(InvalidGameCreationException | InvalidLobbyStateException e){
                System.out.println("ERROR " + e.getMessage());
            }
        }
    }
}