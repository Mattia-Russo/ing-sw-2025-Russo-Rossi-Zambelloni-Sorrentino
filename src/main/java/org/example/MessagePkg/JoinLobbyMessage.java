package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.InvalidGameCreationException;
import org.example.ServerPkg.Model.Exceptions.InvalidLobbyStateException;

import java.rmi.RemoteException;

public class JoinLobbyMessage extends Message{

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()) {
            try{
                if(super.getProxy()!=null) {
                    getProxy().addGameUpdater(controller);
                    controller.joinLobby(playerName);
                    Message message = new LobbyJoinedMessage();
                    message.setProxy(getProxy());
                    getProxy().sendMessage(message);
                } else if (super.getClientName()!=null){
                    super.getServer().addGameUpdater(controller, playerName);
                    controller.joinLobby(playerName);
                    Message message = new LobbyJoinedMessage();
                    message.setClientName(playerName);
                    super.getServer().notifyLobbyJoined(message, playerName);
                }
                System.out.println(playerName + " joined the lobby successfully");
            } catch(InvalidGameCreationException | InvalidLobbyStateException e){
                System.out.println("ERROR " + e.getMessage());
            }
        }
    }
}