package org.example.CardPack;

import org.example.Player;

import java.util.ArrayList;

public class OpenSpace extends AdventureCard{
    public OpenSpace(int CardLevel, int lostDays){
        super(CardLevel, lostDays);
    }

    public void checkEnginePower(ArrayList<Player> players){
        for (Player p : players) {
            if (!p.isAbandoned() && ((p.getPlayerShipBoard().getSingleCannonPower() == 0 && (p.getPlayerShipBoard().getNumDoubleCannon()==0 || p.getPlayerShipBoard().getTotalBattery() == 0)))){
                p.abandon();
            }
        }
    }
}
