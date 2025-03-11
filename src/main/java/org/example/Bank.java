package org.example;

import javax.smartcardio.Card;

public class Bank extends Game {
    private int creditsRemaining;
    private int energyRemaining;
    private int redGoodsRemaining;
    private int greenGoodsRemaining;
    private int yellowGoodsRemaining;
    private int blueGoodsRemaining;

    public Bank(int credits, int energy, int redGoods, int greenGoods, int yellowGoods, int blueGoods) {
        this.creditsRemaining=credits;
        this.energyRemaining=energy;
        this.redGoodsRemaining=redGoods;
        this.greenGoodsRemaining=greenGoods;
        this.yellowGoodsRemaining=yellowGoods;
        this.blueGoodsRemaining=blueGoods;
    }

    public int getCredits() {
        return creditsRemaining;
    }

    public int getEnergy() {
        return energyRemaining;
    }

    public int getGoods(int colour) {
        switch (colour) {
            case 1: return redGoodsRemaining;
            case 2: return greenGoodsRemaining;
            case 3: return yellowGoodsRemaining;
            case 4: return blueGoodsRemaining;
            default: throw new IllegalArgumentException("Colore non valido");
        }
    }

    //se giocatore li perde val>0 se giocatore li ottiene daLLA banca val<0
    public void changeCredit(int val) {
        this.creditsRemaining += val;
    }

    public void changeEnergy(int val) {
        this.energyRemaining += val;
}

    public void changeGoods(int val, int colour) {
        switch (colour) {
            case 1: redGoodsRemaining+=val; break;
            case 2: greenGoodsRemaining+=val; break;
            case 3: yellowGoodsRemaining+=val; break;
            case 4: blueGoodsRemaining+=val; break;
            default: throw new IllegalArgumentException("Colore non valido");
        }
    }

}
