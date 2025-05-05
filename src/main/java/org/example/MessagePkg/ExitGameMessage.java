package org.example.MessagePkg;

public class ExitGameMessage extends Message{
    @Override
    public void handle() {
        super.getProxy().exitGame();
    }
}
