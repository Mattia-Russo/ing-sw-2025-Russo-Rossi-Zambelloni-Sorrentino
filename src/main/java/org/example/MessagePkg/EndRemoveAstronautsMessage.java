package org.example.MessagePkg;

public class EndRemoveAstronautsMessage extends Message {
    @Override
    public void handle() {
        super.getProxy().endRemoveAstronauts();
    }
}
