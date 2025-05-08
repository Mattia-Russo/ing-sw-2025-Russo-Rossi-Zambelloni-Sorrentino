package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;

public class PingMessage extends Message {
    private final String key;

    public PingMessage(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }

    @Override
    public void handle(GameController controller, String playerName) {
        // Non gestita normalmente, gestita direttamente in run()
    }
}