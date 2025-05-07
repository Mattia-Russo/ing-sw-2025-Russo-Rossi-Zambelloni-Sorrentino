package org.example.UI;

import org.example.ServerPkg.Model.ForView.GameView;

public class RMIVirtualView implements GameUpdater {

    private UI userInterface;
    @Override
    public void updateGame(GameView game) {
        userInterface.addGameUpdate(game);
    }
}
