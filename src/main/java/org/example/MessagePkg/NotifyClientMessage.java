package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Player;

public class NotifyClientMessage extends Message{
    String message;

    public NotifyClientMessage(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }
}
