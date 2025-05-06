package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.InvalidGameCreationException;
import org.example.ServerPkg.Model.Exceptions.InvalidLobbyStateException;

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
    public void handle(GameController controller, String playerName) {
        if(checkClient()) {
            try{
                controller.createLobby(playerName, numPlayers, shipboardLevel, gameMode);
                System.out.println("Lobby created successfully");
            }catch(InvalidParameterException | InvalidGameCreationException | InvalidLobbyStateException e) {
                System.out.println("ERROR " + e.getMessage());
            }
        }
    }
}
