package org.example.UI;

import org.example.ServerPkg.Model.ForView.GameView;

public class TCPVirtualView implements GameUpdater {

    private UI userInterface;
    @Override
    public void updateGame(GameView game) {
        userInterface.addGameUpdate(game);
    }
}
// listener del client