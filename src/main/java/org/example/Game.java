package org.example;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private int numPlayer;
    private ArrayList<Player> players;
    private ArrayList<AdventureCard> adventureCards;
    private int gameMode;
    private Bank gameBank;
    private ArrayList<AdventureCard> deck;

    public Game() {}

    public void checkShips() {}

    // se teniamo l'array players ordinato in base alla position allora va bene
    // altrimenti va cambiato
    public int getOccupiedPositions(Player player, int numPos) {
        int i = 0;
        for (Player p : players) {
            int diff = p.getPosition() + i - player.getPosition();
            if (p != player &&  diff <= numPos && diff >= 0) {
                i++;
            }
        }
        return i;
    }

    public CardEnum pickCard() {
        Random rand = new Random();
        int index = rand.nextInt(adventureCards.size()-1);  // prende un numero randomico tra 0 e card.length-1

        AdventureCard card = adventureCards.get(index);

        adventureCards.remove(index);

        return card.getCardEnum();
    }

    public boolean checkGiveUp(Player p) {
        return p.isAbandoned();
    }

    public Player calculateWinner() {
        Player winner = players.get(0);
        for (Player p : players) {
            if (p.getNumCredits() > winner.getNumCredits()) {
                winner = p;
            }
        }
        return winner;
    }
}
