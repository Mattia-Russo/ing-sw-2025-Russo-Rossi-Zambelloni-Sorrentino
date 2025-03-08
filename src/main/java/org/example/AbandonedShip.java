package org.example;

public class AbandonedShip extends LoseDays{
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

    public int getAstronauts() {
        return Astronauts;
    }

    public int getCredits() {
        return Credits;
    }
}
