package org.example.UIPkg;

import org.example.MessagePkg.ToClient.NotifyClientMessage;
import org.example.ServerPkg.Model.ForView.GameView;

import java.util.List;

public abstract class UI {
    private final Client client;

    protected UI(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return this.client;
    }

    public void addGameUpdate(GameView game){}

    public void printNameInvalid(){}

    public void askName(){}

    public void readName(){}

    public void manageNotification(NotifyClientMessage notifyClientMessage){}

    public void onNameAccepted(){}

    public void onLobbyCreated(String name, int numPlayers, int shipboardLevel, int gameMode){}

    public void onLobbyJoined(List<String> names, int numPlayers, int shipboardLevel, int gameMode){}

    public void onCreateLobbyAccepted(){}

    public void onUpdatePlayerList(List<String> updatedList){}

    public void startGui(){}

    public void onGameStarted(){}

    public void goToShipWreckedScene(){}

    public void goToFixShipScene(){}

    public void goToAddAlienScene(){}

    public void goToWaitingScene(){}

    public void goToActivateCannonsScene(){}

    public void goToActivateEngineScene(){}

    public void goToActivateShieldScene(){}

    public void goToAbandonScene(){}

    public void goToChangeGoodsScene(){}

    public void goToRemoveAstronautScene(){}

    public void goToRemoveBestGoodsScene(){}

    public void goToEndGameScene(){}

    public void goToLandOnAbandonScene(){}

    public void goToLandOnPlanetScene(){}

    public void goToRewardScene(){}
}
