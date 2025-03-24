package org.example;

import org.example.CardPack.AdventureCard;
import org.example.ComponentsPack.Goods;
import org.example.ComponentsPack.Storage;

import java.util.ArrayList;
import java.util.Random;
import java.util.Comparator;

public class Game{
    private final int numPlayer;
    private ArrayList<Player> players;
    private ArrayList<AdventureCard> deck;
    private int gameMode;
    private int lapLength;

    public Game(int numPlayer, ArrayList<Player> players, ArrayList<AdventureCard> deck, int gameMode, int lapLength) {
        this.numPlayer = numPlayer;
        this.players = players;
        this.deck = deck;
        this.gameMode = gameMode;
        this.lapLength = lapLength;
    }

    //il deck deve essere in modo che io abbia 2 carte di livello 2 e una di livello 1
    public ArrayList<AdventureCard> getDeck(int deck_pos) {
        ArrayList<AdventureCard> temp = new ArrayList<>();
        for(int i=0; i<3; i++) {
           temp.add(deck.get(deck_pos*3 + i));
        }
        return temp;
    }



    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void adjustPlayerPositions() {
        players.sort(Comparator.comparingInt(Player::getPosition).reversed()); // metodo per ordinare i player in base alla posizione
    }

    public int getOccupiedPositions(Player player, int numPos) {
        int i = 0;
        for (int j=players.indexOf(player)-1; j>=0; j--) {
            if(!players.get(j).isAbandoned()){
                int diff = players.get(j).getPosition() + i - player.getPosition();
                if (diff <= numPos) {
                    i++;
                }
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

    public ArrayList<Player> calculateWinner() {

        ArrayList<Player> winners = new ArrayList<>();
        for (Player p : players) {
            if (p.getNumCredits() > 0) {
                winners.add(p);
            }
        }
        return winners;
    }

    public void calculateFinalCredits() {
        int i = 4;
        ArrayList<Player> bestShips = new ArrayList<>();
        for (Player p : players) {
            double tmp_credits = 0;
            for (Goods g : p.getPlayerShipBoard().getTotalGoods()){     // vendita delle merci
                switch (g.getColour()){
                    case RED:
                        tmp_credits += 4;
                        break;
                    case YELLOW:
                        tmp_credits += 3;
                        break;
                    case GREEN:
                        tmp_credits += 2;
                        break;
                    case BLUE:
                        tmp_credits += 1;
                        break;
                }
            }
            if(!p.isAbandoned()){
                p.changeCredits(i);     // aumento crediti in base all'ordine di arrivo
                i--;
                if(bestShips.isEmpty()){    // selezione giocatori con nave con meno connettori esposti
                    bestShips.add(p);
                } else if(p.getPlayerShipBoard().getTotalExposedConnectors() < bestShips.get(0).getPlayerShipBoard().getTotalExposedConnectors()){
                    bestShips.clear();
                    bestShips.add(p);
                } else if (p.getPlayerShipBoard().getTotalExposedConnectors() == bestShips.get(0).getPlayerShipBoard().getTotalExposedConnectors()){
                    bestShips.add(p);
                }
                p.changeCredits((int) tmp_credits);     // vendita a prezzo intero
            } else {    // giocatori non arrivati
                p.changeCredits((int) Math.ceil(tmp_credits/2));    // vendita a metà prezzo, arrotondata per eccesso
            }
            p.changeCredits(-p.getPlayerShipBoard().getDeletedComponentsCounter());  // togli crediti in base a quanti componenti sono stati rimossi
        }
        for (Player p : bestShips) {    // aggiungi crediti in base alla nave con meno connettori esposti
            p.changeCredits(2);
        }
    }

    public void checkForcedAbandon() {
        for(Player p : players) {
            if (!p.isAbandoned() && (p.getPlayerShipBoard().getTotalAstronauts()==0 || (p!= players.get(0) && p.getPosition()<players.get(0).getPosition()-lapLength))) {
                    p.abandon();
            }
        }
    }
}