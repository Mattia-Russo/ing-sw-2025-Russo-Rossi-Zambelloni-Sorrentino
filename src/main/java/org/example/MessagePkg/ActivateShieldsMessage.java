package org.example.MessagePkg;

import org.example.ServerPkg.ClientProxy;
import org.example.ServerPkg.Model.Points;

import java.util.ArrayList;

public class ActivateShieldsMessage extends Message {
    private ArrayList<Points> shields;

    public ActivateShieldsMessage(ArrayList<Points> shields){
        this.shields=shields;
    }

    @Override
    public void handle(ClientProxy proxy) {
        proxy.activateShields(shields);
    }

}
