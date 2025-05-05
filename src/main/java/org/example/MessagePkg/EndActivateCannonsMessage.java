package org.example.MessagePkg;

public class EndActivateCannonsMessage extends Message {
    @Override
    public void handle() {
        super.getProxy().endActivateCannons();
    }
}
