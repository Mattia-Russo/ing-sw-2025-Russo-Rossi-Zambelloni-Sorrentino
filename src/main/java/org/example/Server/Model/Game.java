package org.example.Server.Model;

import org.example.Server.Controller.States.*;
import org.example.Server.Model.CardPack.AdventureCard;
import org.example.Server.Model.ComponentsPack.Components;

import org.example.Server.Model.ComponentsPack.Direction;

import org.example.Server.Model.ComponentsPack.Goods;
import org.example.Server.Model.Exceptions.*;
import org.example.Server.Utils.CardLoader;
import org.example.Server.Utils.TileLoader;

import java.util.*;

public class Game{
    private final int numPlayer;
    private ArrayList<Player> players;
    private List<AdventureCard> deck;
    private int gameMode;
    private final int ShipBoardLevel;
    private final int lapLength;
    private AdventureCard currentCard;
    private List<Components> componentsList;

    public Game(int numPlayer, int ShipBoardLevel, int gameMode) {
        this.numPlayer = numPlayer;
        this.gameMode = gameMode;
        this.ShipBoardLevel = ShipBoardLevel;
        if(gameMode == 1) {
            this.deck = CardLoader.loadPatternDeck();
        }else{
            Set<String> desiredTypes = Set.of("ABANDONEDSHIP", "ABANDONEDSTATION", "PLANETSCARD", "SMUGGLERS", "OPENSPACE", "METEORCARD", "STARDUST", "WARZONE");
            this.deck = CardLoader.loadFilteredRandomCards(desiredTypes);
        }
        this.currentCard = null;

        if(gameMode == 0 ) {
            this.lapLength = 18;
        }else
            this.lapLength = 24;

        if(gameMode == 1) {
            this.componentsList = TileLoader.loadTiles();
        }else {
            this.componentsList = TileLoader.loadFilteredTiles();
        }
        this.players = new ArrayList<>();
    }

    public int getNumPlayer() {
        return numPlayer;
    }

    public void setPlayersShipboard(){
        for(Player p: players) {
            if(gameMode == 0) {
                p.setPlayerShipboard(gameMode);
            }else
                p.setPlayerShipboard(ShipBoardLevel);
        }
    }

    public void addPlayer(Player p){
        players.add(p);
    }

    // usage only for tests
    public void setCard(AdventureCard card) {
        deck.clear();
        deck.add(card);
        this.currentCard = card;
    }

    //il deck deve essere in modo che io abbia 2 carte di livello 2 e una di livello 1
    public ArrayList<AdventureCard> getDeck(int deckPos) {
        if (deckPos < 3 && deckPos >= 0) {
            ArrayList<AdventureCard> temp = new ArrayList<>();
            for(int i=0; i<3; i++) {
                temp.add(deck.get(deckPos*3 + i));
            }
            return temp;
        } else {
            throw new InvalidDeckNumberException("You've entered an invalid deck number, select between 0, 1 or 2");
        }
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

    private void pickCard() {
        if(this.deck == null){
            throw new DeckNotInitializedException("Deck has not been initialized");
        } else if (this.deck.isEmpty() && this.currentCard == null) {
            throw new EmptyDeckException("Deck initialized without cards");
        } else if (this.deck.size() == 1) {
            this.currentCard = this.deck.get(0);
            deck.remove(0);
        } else {
            Random rand = new Random();
            int index = rand.nextInt(deck.size()-1);  // prende un numero randomico tra 0 e card.length-1

            this.currentCard = deck.get(index);

            deck.remove(index);
        }
    }

    public boolean checkGiveUp(Player p) {
        return p.isAbandoned();
    }

    private ArrayList<Player> calculateWinners() {

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

    private void checkForcedAbandon() {
        for(Player p : players) {
            if (!p.isAbandoned() && (p.getPlayerShipBoard().getTotalAstronauts()==0 || (p!= players.get(0) && p.getPosition()<players.get(0).getPosition()-lapLength))) {
                    p.abandon();
            }
        }
    }

    public AdventureCard getCurrentCard(){
        return this.currentCard;
    }

    //usage only for tests
    public List<AdventureCard> getDeck(){
        return this.deck;
    }

    public void Turn() {
        if (this.deck.isEmpty()){
            for (Player player : players) {
                player.setPlayerState(new EndState(this));
            }
            calculateFinalCredits();
            ArrayList<Player> winners = calculateWinners();
            for (Player player : winners) {
                System.out.println("Congratulations player " + player.getName() + " won the game");
            }
        } else {
            adjustPlayerPositions();
            checkForcedAbandon();
            pickCard();
            currentCard.setCardState(this);
        }
    }



    public void checkAllPlayersShip(){
        for (Player p : players) {
            if (!p.checkShip()) {
                p.setShipOK(false);
                p.setPlayerState(new FixShipState(this));
            }
        }
        for (Player p : players) {
            if (!p.getShipOK()) {
                return;
            }
            p.setPlayerState(new WaitingState());
        }
        checkAllWrackedShip();
    }

    public void checkAllWrackedShip(){
        Components c=null;
        int i=0;
        for (Player p : players) {
            while(c==null){
                c=p.getPlayerShipBoard().getFirstComponent(Direction.WEST, i);
                i++;
            }
            if(p.getPlayerShipBoard().checkIfSplitted(c.getPosX(), c.getPosY())){
                p.setPlayerState(new ShipWreckedState(this));
            }
            i=0;
            c=null;
        }
    }

    public void startBuildingShips() {
        for (Player p : players){
            p.setPlayerState(new BuildShipState(this));
        }
    }

    public Components pickComponentTile() {
        if ((this.componentsList == null)) {
            throw new TilesHeapNotInitializedException("Tiles heap has not been initialized");
        } else if (this.componentsList.isEmpty()) {
            throw new EmptyComponentListException("Components heap is empty");
        } else {
            Random rand = new Random();
            int index = rand.nextInt(componentsList.size()-1);  // prende un numero randomico tra 0 e card.length-1

             Components c = componentsList.get(index);

            componentsList.remove(index);

            return c;
        }
    }

    //usage only for test
    public List<Components> getComponentsList(){
        return this.componentsList;
    }
}

