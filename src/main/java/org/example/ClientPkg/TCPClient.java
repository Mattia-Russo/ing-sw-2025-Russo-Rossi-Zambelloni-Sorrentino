package org.example.ClientPkg;

import org.example.MessagePkg.Message;
import org.example.ServerPkg.Model.Game;

public class TCPClient implements Client {

    public void updateGame(Game game){}

    @Override
    public void disconnect() {

    }

    @Override
    public void sendMessage(Message message){}
}
