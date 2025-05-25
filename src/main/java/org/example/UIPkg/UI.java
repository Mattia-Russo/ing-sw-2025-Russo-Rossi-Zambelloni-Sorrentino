package org.example.UIPkg;

import org.example.MessagePkg.NotifyClientMessage;
import org.example.ServerPkg.Model.ForView.GameView;

import java.rmi.RemoteException;

public interface UI {

    void addGameUpdate(GameView game);

    void printNameInvalid();

    void askName();

    void readName();

    void manageNotification(NotifyClientMessage notifyClientMessage);

    void onNameAccepted();

    void onLobbyCreated(String name);

    void printMessage(String message);

    void onLobbyJoined(String name, int numPlayers, int shipboardLevel, int gameMode);

    void onCreateLobbyAccepted();
}
