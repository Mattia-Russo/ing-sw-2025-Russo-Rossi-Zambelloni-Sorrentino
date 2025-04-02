package org.example.Server.Model;

import org.example.Server.Controller.States.PlayerState;
import org.example.Server.Controller.States.WaitingState;
import org.example.Server.Model.ComponentsPack.Components;
import org.example.Server.Model.ComponentsPack.Connector;
import org.example.Server.Model.ComponentsPack.Direction;
import org.example.Server.Model.Exceptions.PlayerAbandonedException;
import org.example.Server.Model.Exceptions.TilesEndedExceptions;
import java.util.ArrayList;
import java.util.Random;

public class Player {
    private final int id;
    private final String name;
    private int position;
    private final ShipBoard playerShipBoard;
    private boolean abandoned;
    private boolean isLanded;
    private int numCredits;
    private PlayerState state;

    public Player(ShipBoard shipBoard, int id, String name){
        this.id = id;
        this.position=0;
        this.playerShipBoard=shipBoard;
        this.abandoned=false;
        this.isLanded=false;
        this.numCredits=0;
        this.name=name;
        this.state = new WaitingState();
    }

    public int getPosition(){
        return this.position;
    }

    public boolean isAbandoned() {
        return abandoned;
    }

    public boolean isLanded() {
        return isLanded;
    }

    public ShipBoard getPlayerShipBoard() {
        return playerShipBoard;
    }

    public void changeLanded(){
        this.isLanded = !isLanded;
    }

    public void abandon(){
        this.abandoned=true;
    }

    public void changePosition(int val){
        if (!this.abandoned) {
            this.position += val;
        } else {
            throw new PlayerAbandonedException("The player has abandoned");
        }
    }

    public int getNumCredits(){
        return numCredits;
    }

    public int rollDice() {
        Random random = new Random();
        int die1 = random.nextInt(6) + 1;
        int die2 = random.nextInt(6) + 1;
        return die1 + die2;
    }

    public void changeCredits(int num){
        numCredits+=num;
    }

    public Components pickComponent(ArrayList<Components> components){
        if (components == null || components.isEmpty()) {
            throw new TilesEndedExceptions("No more components tiles available");
        }
        Random random = new Random();
        return components.get(random.nextInt(components.size()));
    }

    public boolean checkShip() {    // devo controllare solo se i connettori in basso e a dx sono giusti
        for(int i = 0; i < playerShipBoard.getComponentMatrix().length; i++){
            for(int j = 0; j < playerShipBoard.getComponentMatrix()[i].length; j++){
                if (playerShipBoard.validPosition(i,j) && playerShipBoard.getComponentMatrix()[i][j] != null) {
                    Components c = playerShipBoard.getComponentMatrix()[i][j];
                    for (int k = 0; k < 4; k++) {
                        switch ((c.getDirection().ordinal() + k) % 4) {
                            case 0:
                                if (playerShipBoard.validPosition(i, j - 1) && playerShipBoard.getComponentMatrix()[i][j-1]!=null) {
                                    if (playerShipBoard.getComponentMatrix()[i][j - 1].getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) == Connector.EMPTY) { //prende il connettore del componente di fianco che punta al componente che stiamo controllando
                                        return false;
                                    }
                                }
                                break;
                            case 1:
                                if (playerShipBoard.validPosition(i + 1, j) && playerShipBoard.getComponentMatrix()[i+1][j]!=null) {
                                    if (playerShipBoard.getComponentMatrix()[i + 1][j].getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) == Connector.EMPTY) {
                                        return false;
                                    }
                                    if (playerShipBoard.getComponentMatrix()[i + 1][j].getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) != Connector.UNIVERSAL
                                            && c.getDirConnector(Direction.values()[(c.getDirection().ordinal() + k) % 4]) != Connector.UNIVERSAL
                                            && playerShipBoard.getComponentMatrix()[i + 1][j].getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) !=
                                            c.getDirConnector(Direction.values()[(c.getDirection().ordinal() + k) % 4])) {
                                        return false;
                                    }
                                }
                                break;
                            case 2:
                                if (playerShipBoard.validPosition(i, j + 1) && playerShipBoard.getComponentMatrix()[i][j+1]!=null) {
                                    if (playerShipBoard.getComponentMatrix()[i][j + 1].getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) == Connector.EMPTY) {
                                        return false;
                                    }
                                    if (playerShipBoard.getComponentMatrix()[i][j + 1].getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) != Connector.UNIVERSAL
                                            && c.getDirConnector(Direction.values()[(c.getDirection().ordinal() + k) % 4]) != Connector.UNIVERSAL
                                            && playerShipBoard.getComponentMatrix()[i][j + 1].getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) !=
                                            c.getDirConnector(Direction.values()[(c.getDirection().ordinal() + k) % 4])) {
                                        return false;
                                    }
                                }
                                break;
                            case 3:
                                if (playerShipBoard.validPosition(i - 1, j) && playerShipBoard.getComponentMatrix()[i-1][j]!=null) {
                                    if (playerShipBoard.getComponentMatrix()[i - 1][j].getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) == Connector.EMPTY) {
                                        return false;
                                    }
                                }
                        }
                    }
                    if(c.checkRightCannon(this.playerShipBoard)){
                        return false;
                    }
                    if(c.checkRightEngine(this.playerShipBoard)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public PlayerState getState() {
        return this.state;
    }

    public void setPlayerState(PlayerState state) {
        this.state = state;
    }
}
