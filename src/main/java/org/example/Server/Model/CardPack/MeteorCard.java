package org.example.Server.Model.CardPack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.Server.Controller.PlayerStates.ActivateCannonsState;
import org.example.Server.Controller.PlayerStates.ActivateShieldsState;
import org.example.Server.Controller.PlayerStates.ShipWreckedState;
import org.example.Server.Controller.PlayerStates.WaitingState;
import org.example.Server.Model.ComponentsPack.Components;
import org.example.Server.Model.ComponentsPack.Direction;
import org.example.Server.Model.Exceptions.AlreadyEmptyPositionException;
import org.example.Server.Model.Exceptions.BatteriesLessThenCannonException;
import org.example.Server.Model.Exceptions.InvalidPositionException;
import org.example.Server.Model.Game;
import org.example.Server.Model.Player;
import org.example.Server.Model.Points;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class MeteorCard extends AdventureCard {
    private List<Meteor> meteorList = new ArrayList<Meteor>();
    int currentMeteor;
    int currentPlayer;
    int rowOrCol;
    boolean protect;
    boolean dice;

    @JsonCreator
    public MeteorCard(
            @JsonProperty("cardLevel") int cardLevel,
            @JsonProperty("lostDays") int lostDays,
            @JsonProperty("meteorList") List<Meteor> meteorList){
        super(cardLevel, lostDays);
        this.meteorList=meteorList;
        this.currentMeteor=-1;
        this.currentPlayer=-1;
        this.rowOrCol =-1;
        this.protect=false;
        this.dice=true;
    }

    @Override
    public void setCardState(Game g){
        do {
            currentPlayer++;
        } while (currentPlayer < g.getPlayers().size() && g.getPlayers().get(currentPlayer).isAbandoned());


        if(currentPlayer == g.getPlayers().size()){
            meteorList.remove(currentMeteor);
            if(!meteorList.isEmpty()){
                currentPlayer=-1;
                do{
                    currentPlayer++;
                } while (currentPlayer < g.getPlayers().size() && g.getPlayers().get(currentPlayer).isAbandoned());
                rowOrCol=g.getPlayers().get(currentPlayer).rollDice();
                if(currentPlayer==g.getPlayers().size()) {
                    currentPlayer=g.getPlayers().size()-1;
                    protect=true;
                }else if (meteorList.get(currentMeteor).getDirection() == Direction.NORTH || meteorList.get(currentMeteor).getDirection() == Direction.SOUTH) {
                    if (rowOrCol >= 7) {
                        protect = true;
                        dice=false;
                    }
                } else if (rowOrCol >= 5) {
                    protect = true;
                    dice=false;
                }
                if(dice) {
                    if (g.getPlayers().get(currentPlayer).getPlayerShipBoard().getFirstComponent(meteorList.get(currentMeteor).getDirection(), rowOrCol) == null) {
                        protect = true;
                    }
                }else
                    dice=true;
                if (protect) {
                    playCard(g, null, null);
                } else
                    Update(g, g.getPlayers().get(currentPlayer));
            }else{
                g.Turn();
            }
        }else {
            if(currentMeteor==-1){
                rowOrCol=g.getPlayers().get(currentPlayer).rollDice();
                currentMeteor=0;
            }
            if(meteorList.get(currentMeteor).getDirection()==Direction.NORTH||meteorList.get(currentMeteor).getDirection()==Direction.SOUTH){
                if(rowOrCol >= 7){
                    protect=true;
                    dice=false;
                }
            }else if(rowOrCol >= 5) {
                protect=true;
                dice=false;
            }
            if(dice){
                if (g.getPlayers().get(currentPlayer).getPlayerShipBoard().getFirstComponent(meteorList.get(currentMeteor).getDirection(), rowOrCol) == null) {
                    protect = true;
                }
            }else
                dice=true;
            if(protect){
                playCard(g, null, null);
            }else
                Update(g, g.getPlayers().get(currentPlayer));
        }
    }

    private void Update(Game g, Player p) {
        if (meteorList.get(currentMeteor).getType() == 0) {
            if (p.getPlayerShipBoard().getIfExposed(meteorList.get(currentMeteor).getDirection(), p.getPlayerShipBoard().getFirstComponent(meteorList.get(currentMeteor).getDirection(), rowOrCol))) {
                if (p.getPlayerShipBoard().getIfShielded(meteorList.get(currentMeteor).getDirection())) {
                    p.setPlayerState(new ActivateShieldsState(g));
                } else
                    this.playCard(g, null, null);
            } else {
                protect = true;
                this.playCard(g, null, null);
            }
        } else {
            if (meteorList.get(currentMeteor).getDirection() == Direction.NORTH) {
                if (p.getPlayerShipBoard().getIfSingleCannon(Direction.NORTH, rowOrCol)) {
                    protect = true;
                    this.playCard(g, null, null);
                } else if (p.getPlayerShipBoard().getIfDoubleCannon(Direction.NORTH, rowOrCol)) {
                    p.setPlayerState(new ActivateCannonsState(g));
                } else {
                    this.playCard(g, null, null);
                }
            } else {
                if (p.getPlayerShipBoard().getIfSingleCannon(meteorList.get(currentMeteor).getDirection(), rowOrCol) || p.getPlayerShipBoard().getIfSingleCannon(meteorList.get(currentMeteor).getDirection(), rowOrCol - 1) || p.getPlayerShipBoard().getIfSingleCannon(meteorList.get(currentMeteor).getDirection(), rowOrCol + 1)) {
                    protect = true;
                    this.playCard(g, null, null);
                } else if (p.getPlayerShipBoard().getIfDoubleCannon(meteorList.get(currentMeteor).getDirection(), rowOrCol) || p.getPlayerShipBoard().getIfDoubleCannon(meteorList.get(currentMeteor).getDirection(), rowOrCol - 1) || p.getPlayerShipBoard().getIfDoubleCannon(meteorList.get(currentMeteor).getDirection(), rowOrCol + 1)) {
                    p.setPlayerState(new ActivateCannonsState(g));
                } else {
                    this.playCard(g, null, null);
                }

            }
        }
    }

    @Override
    public void playCard(Game g, ArrayList<Points> component, ArrayList<Points> battery) {
        Player p=g.getPlayers().get(currentPlayer);
        if(!protect){
            int i=0;
            Components wreck=null;
            if(component==null||battery==null){
                Components c=p.getPlayerShipBoard().getFirstComponent(meteorList.get(currentMeteor).getDirection(), rowOrCol);
                try {
                    p.getPlayerShipBoard().removeComponent(c.getPosX(), c.getPosY());
                    while (wreck == null) {
                        wreck = p.getPlayerShipBoard().getFirstComponent(meteorList.get(currentMeteor).getDirection(), i);
                        i++;
                    }
                    if (!p.getPlayerShipBoard().checkIfSplitted(wreck.getPosX(), wreck.getPosY())) {
                        protect = false;
                        p.setPlayerState(new WaitingState());
                        setCardState(g);
                    } else {
                        protect = false;
                        p.setPlayerState(new ShipWreckedState(g));
                    }
                } catch (InvalidPositionException | AlreadyEmptyPositionException e) {
                    System.out.println("Error" + e.getMessage());
                }
            }else if(meteorList.get(currentMeteor).getType()==0){
                try {
                    if (!p.getPlayerShipBoard().ShieldProtects(meteorList.get(currentMeteor).getDirection(), component, battery)) {
                        Components c = p.getPlayerShipBoard().getFirstComponent(meteorList.get(currentMeteor).getDirection(), rowOrCol);
                        try {
                            p.getPlayerShipBoard().removeComponent(c.getPosX(), c.getPosY());
                            while (wreck == null) {
                                wreck = p.getPlayerShipBoard().getFirstComponent(meteorList.get(currentMeteor).getDirection(), i);
                                i++;
                            }
                            if (!p.getPlayerShipBoard().checkIfSplitted(wreck.getPosX(), wreck.getPosY())) {
                                protect = false;
                                p.setPlayerState(new WaitingState());
                                setCardState(g);
                            } else {
                                protect = false;
                                p.setPlayerState(new ShipWreckedState(g));
                            }
                        } catch (InvalidPositionException | AlreadyEmptyPositionException e) {
                            System.out.println("Error" + e.getMessage());
                        }
                    }
                }catch(InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException e){
                    System.out.println("Error" + e.getMessage());
                    g.getPlayers().get(currentPlayer).setPlayerState(new ActivateShieldsState(g));
                }
            }else {
                try{
                    if (!p.getPlayerShipBoard().CannonProtects(meteorList.get(currentMeteor).getDirection(), rowOrCol, component, battery)) {
                        Components c = p.getPlayerShipBoard().getFirstComponent(meteorList.get(currentMeteor).getDirection(), rowOrCol);
                        try {
                            p.getPlayerShipBoard().removeComponent(c.getPosX(), c.getPosY());
                            while(wreck==null){
                                wreck=p.getPlayerShipBoard().getFirstComponent(meteorList.get(currentMeteor).getDirection(), i);
                                i++;
                            }
                            if(!p.getPlayerShipBoard().checkIfSplitted(wreck.getPosX(), wreck.getPosY())){
                                protect=false;
                                p.setPlayerState(new WaitingState());
                                setCardState(g);
                            }else{
                                protect=false;
                                p.setPlayerState(new ShipWreckedState(g));
                            }
                        } catch (InvalidPositionException | AlreadyEmptyPositionException e) {
                            System.out.println("Error" + e.getMessage());
                        }
                    }
                }catch(InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException e){
                        System.out.println("Error" + e.getMessage());
                        g.getPlayers().get(currentPlayer).setPlayerState(new ActivateCannonsState(g));
                }

            }
        }
        protect=false;
        p.setPlayerState(new WaitingState());
        setCardState(g);
    }

    public int getCardLevel() {
        return super.getCardLevel();
    }

    public List<Meteor> getMeteorList() {
        return meteorList;
    }
}
