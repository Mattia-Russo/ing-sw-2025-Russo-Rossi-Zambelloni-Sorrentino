package org.example;

public abstract class AbandonedShip extends LoseDays{
    private final int cardLevel;
    private final CardEnum cardEnum;
    private int Credits;
    private int Astronauts;

    public AbandonedShip(){
        this.cardEnum = CardEnum.AbandonedShip;
        this.cardLevel = 123;
    }

    @Override
    public int getCardLevel() {
        return cardLevel;
    }

    @Override
    public CardEnum getCardEnum() {
        return cardEnum;
    }

    @Override
    public void playCard(Player p) {
        p.changeCredits(Credits);
    }

    public int getCredits(){
        return Credits;
    }

    @Override
    public int getNumAstronauts(){
        return Astronauts;
    }
}
