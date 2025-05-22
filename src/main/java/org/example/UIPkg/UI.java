package org.example.UIPkg;

import org.example.ServerPkg.Model.ForView.GameView;

public interface UI {
    void addGameUpdate(GameView game);

    void printNameInvalid();

    void askName();

    void readName();

    void onNameAccepted();

    void showNoLobbyMessage();

    void showLobbyExistsMessage();

    void onLobbyCreated();

    void onJoinedLobby();
}
