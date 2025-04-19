package org.example.MessagePkg;

import org.example.ServerPkg.ClientProxy;

public class AcceptRewardMessage extends Message {
    private boolean bool;

    public AcceptRewardMessage(boolean bool) {
        this.bool = bool;
    }

    @Override
    public void handle(ClientProxy proxy) {
        proxy.acceptReward(bool);
    }
}
