package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;

public class LobbyJoinedMessage extends Message{
    int numPlayer;
    int shipBoardLevel;
    int gameMode;

    public LobbyJoinedMessage(int numPlayer, int shipBoardLevel, int gameMode){
        this.numPlayer=numPlayer;
        this.shipBoardLevel=shipBoardLevel;
        this.gameMode=gameMode;
    }

    @Override
    public void handle(GameController controller, String playerName){
        super.getClient().getUserInterface().onLobbyJoined(playerName, numPlayer, shipBoardLevel, gameMode);
    }
}
