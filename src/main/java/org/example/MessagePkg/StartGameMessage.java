package org.example.MessagePkg;

public class StartGameMessage extends Message {
    @Override
    public void handle() {
        super.getProxy().startGame();
    }
}
