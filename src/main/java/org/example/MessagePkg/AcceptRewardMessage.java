package org.example.MessagePkg;

public class AcceptRewardMessage extends Message {
    private boolean bool;

    public AcceptRewardMessage(boolean bool) {
        this.bool = bool;
    }

    @Override
    public void handle() {
        super.getProxy().acceptReward(bool);
    }
}
