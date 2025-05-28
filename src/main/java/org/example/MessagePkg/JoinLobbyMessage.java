package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.ControllerPkg.LobbyState;
import org.example.ServerPkg.Model.Exceptions.InvalidGameCreationException;
import org.example.ServerPkg.Model.Exceptions.InvalidLobbyStateException;

import java.rmi.RemoteException;
import java.util.List;

public class JoinLobbyMessage extends Message{

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()) {
            try{
                if(controller.getLobbyState().equals(LobbyState.GAME_READY)) {
                    controller.addGameUpdater(getServer().getGameUpdater(getHandler().getPlayerName()), getHandler().getPlayerName());
                    controller.joinLobby(playerName);
                    getServer().notifyClient(getHandler().getPlayerName(), "You've joined the lobby");
                    controller.notifyBroadcast(List.of(getHandler().getPlayerName()), getHandler().getPlayerName() + " joined the lobby");
                    getServer().notifyLobbyJoined(getHandler().getPlayerName());
                    controller.updatePlayerList(getHandler().getPlayerName());
                    System.out.println(playerName + " joined the lobby successfully");
                } else if (controller.getLobbyState().equals(LobbyState.GAME_CREATION)){
                    getServer().notifyClient(getHandler().getPlayerName(), "Somebody else is setting up a lobby, wait it to be created");
                } else {
                    getServer().notifyClient(getHandler().getPlayerName(), "You're the first to join the server, create a lobby!");
                }
            } catch(InvalidGameCreationException | InvalidLobbyStateException e){
                System.out.println("ERROR " + e.getMessage());
            }
        }
    }
}