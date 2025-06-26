package org.example.MessagePkg.ToClient;

import org.example.MessagePkg.Message;

import java.io.Serial;
import java.io.Serializable;


public class NotifyClientMessage extends Message implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    private final String message;

    public NotifyClientMessage(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }
}
