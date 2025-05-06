package org.example.MessagePkg;

import org.example.ServerPkg.TCPPkg.ClientProxy;
import org.example.ServerPkg.Model.Points;

public class RemoveAstronautsMessage extends Message {
    private Points point;

    public RemoveAstronautsMessage(Points point) {
        this.point = point;
    }

    @Override
    public void handle() {
        super.getProxy().removeAstronauts(point);
    }
}
