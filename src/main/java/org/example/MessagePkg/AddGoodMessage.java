package org.example.MessagePkg;

import org.example.ServerPkg.TCPPkg.ClientProxy;
import org.example.ServerPkg.Model.Points;

public class AddGoodMessage extends Message {
    private Points point;
    private int numGood;

    public AddGoodMessage(Points point, int numGood) {
        this.point = point;
        this.numGood = numGood;
    }

    @Override
    public void handle() {
        super.getProxy().addGood(point, numGood);
    }
}
