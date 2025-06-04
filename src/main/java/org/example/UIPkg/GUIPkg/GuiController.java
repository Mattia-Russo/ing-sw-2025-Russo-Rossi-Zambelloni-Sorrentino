package org.example.UIPkg.GUIPkg;

import org.example.ServerPkg.Model.ForView.GameView;

import java.util.List;

public abstract class GuiController {
    private GUI guiRoot;

    public void setGui(GUI guiRoot){
        this.guiRoot=guiRoot;
    }

    public GUI getGuiRoot(){
        return guiRoot;
    }

    public void setMaxPlayers(int numPlayers){}

    public void setShipboardLevel(int shipboardLevel){}

    public void setGameMode(int gameMode) {}

    public void updatePlayersList(List<String> playersList){}

    public void printNameInvalid(){}

    public void onNameAccepted(){}

    public void onLobbyCreated(){}

    public void setLobbyCreator(boolean lobbyCreator){}

    public void onCreateLobbyAccepted(){}

    public void onGameStarted(){}

    public void updateGui(GameView game){}
}
