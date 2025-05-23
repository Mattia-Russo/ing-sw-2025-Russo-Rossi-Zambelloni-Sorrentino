package org.example.UIPkg;

import org.example.MessagePkg.NotifyClientMessage;
import org.example.ServerPkg.Model.ForView.GameView;

public interface UI {
    void addGameUpdate(GameView game);

    void printNameInvalid();

    void askName();

    void readName();

    void manageNotification(NotifyClientMessage notifyClientMessage);

    void onNameAccepted();

    void showNoLobbyMessage();

    void showLobbyExistsMessage();

    void onLobbyCreated();

    void onLobbyJoined();
}
