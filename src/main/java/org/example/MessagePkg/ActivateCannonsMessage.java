package org.example.MessagePkg;

import org.example.ServerPkg.Model.Points;

import java.util.ArrayList;

public class ActivateCannonsMessage extends Message {
    private ArrayList<Points> cannons;

    public ActivateCannonsMessage(ArrayList<Points> cannons){
        this.cannons=cannons;
    }

    @Override
    public void handle() {
        super.getProxy().activateCannons(cannons);
    }

}
