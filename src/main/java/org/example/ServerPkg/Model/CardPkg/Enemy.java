package org.example.ServerPkg.Model.CardPkg;

import java.io.Serializable;

public abstract class Enemy extends AdventureCard implements Serializable {
    private final int cannonPower;
    public Enemy(int cardLevel, int lostDays, int cannonPower) {
        super(cardLevel, lostDays);
        this.cannonPower= cannonPower;
    }

    public int getCannonPower() {
        return cannonPower;
    }

    public int getCardLevel(){return super.getCardLevel();}

    public int getLostDays(){return super.getLostDays();}
}
