package org.example.MessagePkg;

public class LandOnAbandonMessage extends Message {
    private boolean bool;

    public LandOnAbandonMessage(boolean bool) {
        this.bool = bool;
    }

    @Override
    public void handle() {
        super.getProxy().landOnAbandon(bool);
    }
}
