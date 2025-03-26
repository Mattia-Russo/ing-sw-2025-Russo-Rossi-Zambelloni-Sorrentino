package org.example.Model.CardPack;

public class Slavers extends Enemy{
    private int numAstronauts;
    private int credits;

    public Slavers(int cardLevel, int lostDays, int cannonPower, int numAstronauts, int credits) {
        super(cardLevel, lostDays, cannonPower);
        this.numAstronauts = numAstronauts;
        this.credits = credits;
    }

    public int getCannonPower() {
        return super.getCannonPower();
    }

    public int getCardLevel() {
        return super.getCardLevel();
    }

    public int getLostDays() {return super.getLostDays();}

    public int getNumAstronauts() {
        return numAstronauts;
    }

    public int getCredits() {
        return credits;
    }

    //CONTROLLER CALCOLA POTENZA DI FUOCO USANDO UN METODO SUL MODEL , CHIAMA GETCANNONPOWER,
    // CONFRONTA POI O CHIAMA getCredit O numAstronauts
}
