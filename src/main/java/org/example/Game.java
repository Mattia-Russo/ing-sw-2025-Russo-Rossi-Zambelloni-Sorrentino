package org.example;

import org.example.CardPack.AdventureCard;
import org.example.ComponentsPack.Goods;
import org.example.ComponentsPack.Storage;

import java.util.ArrayList;
import java.util.Random;
import java.util.Comparator;

public class Game extends Controller{
    private final int numPlayer;
    private ArrayList<Player> players;
    private ArrayList<AdventureCard> deck;
    private int gameMode;

    public Game(int numPlayer, ArrayList<Player> players, ArrayList<AdventureCard> deck, int gameMode) {
        this.numPlayer = numPlayer;
        this.players = players;
        this.deck = deck;
        this.gameMode = gameMode;
    }

    public void checkShips() {}

    public void adjustPlayerPositions() {
        players.sort(Comparator.comparingInt(Player::getPosition).reversed()); // metodo per ordinare i player in base alla posizione
    }


    public int getOccupiedPositions(Player player, int numPos) {
        int i = 0;
        for (int j=players.indexOf(player)-1; j<=0; j--) {
            int diff = players.get(j).getPosition() + i - player.getPosition();
            if (diff <= numPos) {
                i++;
            }
        }
        return i;
    }

    public AdventureCard pickCard() {
        Random rand = new Random();
        int index = rand.nextInt(deck.size()-1);  // prende un numero randomico tra 0 e card.length-1

        AdventureCard card = deck.get(index);

        deck.remove(index);

        return card;
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

    public void playCard(AdventureCard card, Player player) {
        card.playCard(player);
    }

    public void playCard(AdventureCard card, ArrayList<Player> players){
        card.playCard(players);
    }

    public void swapGoodPosition(Goods good, Storage storage) {

    }
}
