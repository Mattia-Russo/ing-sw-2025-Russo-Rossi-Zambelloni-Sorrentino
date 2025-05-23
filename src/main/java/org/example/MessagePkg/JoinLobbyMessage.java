package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.ControllerPkg.LobbyState;
import org.example.ServerPkg.Model.Exceptions.InvalidGameCreationException;
import org.example.ServerPkg.Model.Exceptions.InvalidLobbyStateException;

import java.rmi.RemoteException;

public class JoinLobbyMessage extends Message{

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()) {
            try{
                if(controller.getLobbyState().equals(LobbyState.GAME_READY)) {
                    controller.addGameUpdater(getServer().getGameUpdater(getHandler().getPlayerName()), getHandler().getPlayerName());
                    controller.joinLobby(playerName);
                    getServer().notifyLobbyJoined(getHandler().getPlayerName());
                } else {

                }

                    Message message = new LobbyJoinedMessage();
                    message.setProxy(getProxy());
                    getProxy().sendMessage(message);

                    super.getServer().addGameUpdater(controller, playerName);
                    controller.joinLobby(playerName);
                    Message message = new LobbyJoinedMessage();
                    message.setClientName(playerName);
                    super.getServer().notifyLobbyJoined(message, playerName);

                System.out.println(playerName + " joined the lobby successfully");
            } catch(InvalidGameCreationException | InvalidLobbyStateException e){
                System.out.println("ERROR " + e.getMessage());
            }
        }
    }
}