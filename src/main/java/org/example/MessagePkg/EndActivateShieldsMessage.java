package org.example.MessagePkg;

public class EndActivateShieldsMessage extends Message {
    @Override
    public void handle() {
        super.getProxy().endActivateShields();
    }
}
