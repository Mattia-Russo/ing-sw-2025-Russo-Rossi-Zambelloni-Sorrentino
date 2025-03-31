package org.example.Server.Model.CardPack;

import org.example.Server.Model.Player;

import java.util.ArrayList;

public class OpenSpace extends AdventureCard{
    public OpenSpace(int CardLevel, int lostDays){
        super(CardLevel, lostDays);
    }

    public void checkEnginePower(ArrayList<Player> players){
        for (Player p : players) {
            if (!p.isAbandoned() && ((p.getPlayerShipBoard().getSingleEnginePower() == 0 && (p.getPlayerShipBoard().getNumDoubleEngines()==0 || p.getPlayerShipBoard().getTotalBattery() == 0)))){
                p.abandon();
            }
        }
    }
}
