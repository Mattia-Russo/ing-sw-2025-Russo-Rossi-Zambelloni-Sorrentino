package org.example.MessagePkg;

import org.example.ServerPkg.TCPPkg.ClientProxy;
import org.example.ServerPkg.Model.Points;


public class RemoveGoodMessage extends Message {
    private Points point;
    private int numGood;

    public RemoveGoodMessage(Points point, int numGood) {
        this.point = point;
        this.numGood = numGood;
    }

    @Override
    public void handle() {
        super.getProxy().removeGood(point, numGood);
    }
}
