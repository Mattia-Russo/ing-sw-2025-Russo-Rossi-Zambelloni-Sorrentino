package org.example.MessagePkg;

public class JoinLobbyMessage extends Message{
    @Override
    public void handle() {
        super.getProxy().joinLobby();
    }
}
