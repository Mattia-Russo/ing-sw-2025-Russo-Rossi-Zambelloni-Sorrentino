package org.example.ServerPkg.Model.CardPack;

public abstract class Enemy extends AdventureCard{
    private int cannonPower;
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
