package org.example.UIPkg;

import org.example.ServerPkg.Model.ForView.GameView;

public interface UI {
    public void addGameUpdate(GameView game);

    public void printNameInvalid();
}
