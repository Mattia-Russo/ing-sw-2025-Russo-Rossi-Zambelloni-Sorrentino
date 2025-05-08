package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Game;

public class PongMessage extends Message {
    private final String key;

    public PongMessage(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }

    @Override
    public void handle(GameController controller, String playerName) {
        // Anche questa può non fare nulla
    }
}
