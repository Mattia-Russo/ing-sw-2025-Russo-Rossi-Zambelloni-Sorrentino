package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.ControllerPkg.LobbyState;
import org.example.ServerPkg.Model.Exceptions.InvalidGameCreationException;
import org.example.ServerPkg.Model.Exceptions.InvalidLobbyStateException;

import java.rmi.RemoteException;
import java.security.InvalidParameterException;

public class CreateLobbyMessage extends Message {
    private int numPlayers;
    private int shipboardLevel;
    private int gameMode;

    public CreateLobbyMessage(int numPlayers, int shipboardLevel, int gameMode) {
        this.numPlayers = numPlayers;
        this.shipboardLevel = shipboardLevel;
        this.gameMode = gameMode;
    }

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()) {
            try{
                if(controller.getLobbyState().equals(LobbyState.GAME_READY)){
                    getServer().notifyClient(getHandler().getPlayerName(), "Somebody else is setting up a lobby, wait it to be created");
                } else {
                    controller.addGameUpdater(getServer().getGameUpdater(getHandler().getPlayerName()), getHandler().getPlayerName());
                    controller.createLobby(playerName, numPlayers, shipboardLevel, gameMode);
                }
            }catch(InvalidParameterException | InvalidGameCreationException | InvalidLobbyStateException e) {
                System.out.println("ERROR " + e.getMessage());
            }
        }
    }
}
