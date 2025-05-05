package org.example.MessagePkg;

public class EndActivateEnginesMessage extends Message {
    @Override
    public void handle() {
        super.getProxy().endActivateEngines();
    }
}
