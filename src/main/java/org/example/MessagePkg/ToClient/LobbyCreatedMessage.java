package org.example.MessagePkg.ToClient;

import org.example.MessagePkg.Message;
import org.example.ServerPkg.ControllerPkg.GameController;

public class LobbyCreatedMessage extends Message {
    int numPlayer;
    int shipBoardLevel;
    int gameMode;

    public LobbyCreatedMessage(int numPlayer, int shipBoardLevel, int gameMode){
        this.numPlayer=numPlayer;
        this.shipBoardLevel=shipBoardLevel;
        this.gameMode=gameMode;
    }

    @Override
    public void handle(GameController controller, String playerName){
        super.getClient().getUserInterface().onLobbyCreated(playerName, numPlayer, shipBoardLevel, gameMode);
    }
}
