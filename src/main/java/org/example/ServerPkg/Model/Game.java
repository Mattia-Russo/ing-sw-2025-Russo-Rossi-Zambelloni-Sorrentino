package org.example.ServerPkg.Model;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.ControllerPkg.LobbyState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.*;
import org.example.ServerPkg.Model.CardPkg.AdventureCard;
import org.example.ServerPkg.Model.ComponentsPkg.Components;
import org.example.ServerPkg.Model.ComponentsPkg.Direction;
import org.example.ServerPkg.Model.ComponentsPkg.Goods;
import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Utils.CardLoader;
import org.example.ServerPkg.Utils.TileLoader;
import org.example.UIPkg.GameUpdater;

import java.io.Serializable;
import java.util.*;

public class Game implements Serializable {

    private int numPlayer;
    private final ArrayList<Player> players;
    private final List<AdventureCard> deck;
    private final int gameMode;
    private final int ShipBoardLevel;
    private final int lapLength;
    private int timerTurned;
    private AdventureCard currentCard;
    private final List<Components> componentsList;
    private final ArrayList<Components> discoveredComponents;
    private GameController controller;
    private transient Map<String, GameUpdater> gameUpdaters;

    public Game(int numPlayer, int ShipBoardLevel, int gameMode, GameController gameController) {
        this.numPlayer = numPlayer;
        this.gameMode = gameMode;
        this.ShipBoardLevel = ShipBoardLevel;
        this.controller = gameController;
        this.discoveredComponents = new ArrayList<>();
        this.timerTurned = 0;
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
            this.componentsList = TileLoader.loadAllTiles();
        }else {
            this.componentsList = TileLoader.loadFilteredTiles();
        }
        this.players = new ArrayList<>();
        this.gameUpdaters  = new HashMap<>();
    }

    public int getNumPlayer() {
        return numPlayer;
    }

    public int getShipBoardLevel() {
        return ShipBoardLevel;
    }

    public int getGameMode() {
        return gameMode;
    }

    public void setPlayersShipboard(){
        int i=0;
        for(Player p: players) {
            if(gameMode == 0) {
                p.setPlayerShipboard(gameMode);
            }else {
                p.setPlayerShipboard(ShipBoardLevel);
            }
            p.getPlayerShipBoard().placeComponent(7,7, getComponentsList().getFirst());
            getComponentsList().removeFirst();
            i++;
            switch(i){
                case 0:
                    p.setRocketColour("RED");
                    break;
                case 1:
                    p.setRocketColour("YELLOW");
                    break;
                case 2:
                    p.setRocketColour("GREEN");
                    break;
                case 3:
                    p.setRocketColour("BLUE");
                    break;
            }
        }

        for(i=0; i < 4-getPlayers().size(); i++) {
            getComponentsList().removeFirst();
        }

        Collections.shuffle(componentsList);
        new GameView(this, null);
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

        int startPos = player.getPosition();
        int endPos = startPos + numPos;
        int occupiedCount = 0;

        if(numPos>0){
            for (Player p : players) {
                if (p != player && !p.isAbandoned()) {
                    if (p.getPosition() > startPos && p.getPosition() <= endPos + occupiedCount) {
                        occupiedCount++;
                    }
                }
            }
        } else {
            for (Player p : players) {
                if (p != player && !p.isAbandoned()) {
                    if (p.getPosition() >= endPos + occupiedCount && p.getPosition() < startPos) {
                        occupiedCount--;
                    }
                }
            }
        }
        return occupiedCount;
    }

    private void pickCard() {
        this.controller.setLobbyState(LobbyState.PLAYING_CARDS);
        if(this.deck == null){
            throw new DeckNotInitializedException("Deck has not been initialized");
        } else if (this.deck.isEmpty() && this.currentCard == null) {
            throw new EmptyDeckException("Deck initialized without cards");
        } else if (this.deck.size() == 1) {
            this.currentCard = this.deck.getFirst();
            deck.removeFirst();
        } else {
            Random rand = new Random();
            int index = rand.nextInt(deck.size()-1);  // prende un numero random tra 0 e card.length-1
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
        controller.setLobbyState(LobbyState.GAME_FINISHED);
        return winners;
    }

    public void calculateFinalCredits() {
        int i = 4;
        ArrayList<Player> bestShips = new ArrayList<>();
        for (Player p : players) {
            double tmp_credits = getTmpCredits(p);
            if(!p.isAbandoned()){
                p.changeCredits(i);     // aumento crediti in base all'ordine di arrivo
                i--;
                if(bestShips.isEmpty()){    // selezione giocatori con nave con meno connettori esposti
                    bestShips.add(p);
                } else if(p.getPlayerShipBoard().getTotalExposedConnectors() < bestShips.getFirst().getPlayerShipBoard().getTotalExposedConnectors()){
                    bestShips.clear();
                    bestShips.add(p);
                } else if (p.getPlayerShipBoard().getTotalExposedConnectors() == bestShips.getFirst().getPlayerShipBoard().getTotalExposedConnectors()){
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
        for(Player p : players) {
            System.out.println("Player " + p.getName() + " has " + p.getNumCredits() + " credits");
        }
    }

    private double getTmpCredits(Player p) {
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
        return tmp_credits;
    }

    private void checkForcedAbandon() {
        for(Player p : players) {
            if (!p.isAbandoned() && (p.getPlayerShipBoard().getTotalAstronauts()==0 || (p!= players.getFirst() && p.getPosition()<players.getFirst().getPosition()-lapLength))) {
                    p.abandon(this);
                    new GameView(this, new Exception("YOU HAVE TO ABANDON " + p.getName()));
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
            new GameView(this, new Exception("THE GAME HAS ENDED"));
            for (Player player : winners) {
                new GameView(this, new Exception("Congratulations player " + player.getName() + " won the game with " + player.getNumCredits() + " credits!"));
            }
        } else {
            adjustPlayerPositions();
            if(gameMode==1) {
                checkForcedAbandon();
            }
            pickCard();
            new GameView(this, null);
            currentCard.setCardState(this);
        }
    }

    public void checkAllPlayersShip(){
        for (Player p : players) {
            if (!p.checkShip()) {
                p.setShipOK(false);
                p.setPlayerState(new FixShipState(this));
                new GameView(this, new Exception("YOU HAVE TO FIX YOUR SHIP " + p.getName() + ", REMOVE WRONG POSITIONED TILES"));
                return;
            }
        }
        for (Player p : players) {
            if (!p.getShipOK()) {
                return;
            }
            p.setPlayerState(new WaitingState(this));
        }
        checkAllWrackedShip();
        for(Player p : players) {
            if (!p.isAbandoned()) {
                if (!p.getReadyForCards()) {
                    return;
                }
                p.setPlayerState(new WaitingState(this));
                new GameView(this, new Exception("READY FOR CARDS " + p.getName()));
            }
        }
        Turn();
    }

    public void checkAllWrackedShip(){
        Components c=null;
        int i=5;
        for (Player p : players) {
            while(c==null && i < 10){
                c=p.getPlayerShipBoard().getFirstComponent(Direction.WEST, i);
                i++;
            }
            if(i!=10) {
                assert c != null;
                if (p.getPlayerShipBoard().checkIfSplit(c.getPosX(), c.getPosY())) {
                    p.setShipOK(false);
                    p.setPlayerState(new ShipWreckedState(this, p));
                    new GameView(this, new Exception("YOU HAVE A SHIP WRECK " + p.getName()));
                } else if (gameMode == 1) {
                    p.setPlayerState(new AddAlienState(this));
                    new GameView(this, new Exception("YOU CAN ADD YOUR ALIENS " + p.getName()));
                } else {
                    p.setReadyForCards(true);
                }
            }else if(gameMode == 1){
                p.abandon(this);
                new GameView(this, new Exception("YOU HAVE TO ABANDON " + p.getName()));
                p.setPlayerState(new AbandonedState(this));
            }else {
                p.setPlayerState(new WaitingState(this));
            }
            i=5;
            c=null;
        }
    }

    //for testing
    public void startBuildingShips() {
        for (Player p : players){
            p.setPlayerState(new BuildShipState(this, new TimerGenerator()));
        }
    }

    public Components pickComponentTile() {
        if ((this.componentsList == null)) {
            throw new TilesHeapNotInitializedException("Tiles heap has not been initialized");
        } else if (this.componentsList.isEmpty()) {
            throw new EmptyComponentListException("Components heap is empty");
        } else {
            Random rand = new Random();
            int index = rand.nextInt(componentsList.size()-1);  // prende un numero random tra 0 e card.length-1

            Components c = componentsList.get(index);
            componentsList.remove(index);
            return c;
        }
    }

    public Components pickDiscoveredComponent(int index){
        if ((this.discoveredComponents == null)) {
            throw new InvalidMethodCallException("There are no components discovered yet");
        } else if (this.discoveredComponents.isEmpty()) {
            throw new InvalidMethodCallException("Components heap is empty");
        } else {
            Components c = discoveredComponents.get(index);
            discoveredComponents.remove(index);
            return c;
        }
    }

    //usage only for test
    public List<Components> getComponentsList(){
        return this.componentsList;
    }

    public Player getPlayerByName(String name) {
        for (Player p : players) {
            if (p.getName().equals(name)){
                return p;
            }
        }
        return null;
    }

    public void setGameUpdaters(Map<String, GameUpdater> gameUpdaters){
        this.gameUpdaters = gameUpdaters;
    }

    public void updateGame(GameView gameView) {
       for (Map.Entry<String, GameUpdater> entry : gameUpdaters.entrySet()) {
           entry.getValue().updateGame(gameView);
       }
    }

    public void disconnectPlayer (Player p){
        players.remove(p);
        numPlayer--;
        gameUpdaters.remove(p.getName());
    }

    public void addDiscoveredComponent(Components c){
        discoveredComponents.add(c);
    }

    public List<Components> getDiscoveredComponent(){
        return discoveredComponents;
    }

    public GameController getController() {
        return controller;
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public void setTimerTurned() {
        this.timerTurned++;
    }

    public int getTimerTurned() {
        return this.timerTurned;
    }

    public int rollDice() {
        Random random = new Random();
        int die1 = random.nextInt(6) + 1;
        int die2 = random.nextInt(6) + 1;
        return die1 + die2;
    }
}