package org.example.Server.Model.CardPack;

import org.example.Server.Controller.States.ActivateCannonsState;
import org.example.Server.Controller.States.ActivateShieldsState;
import org.example.Server.Controller.States.WaitingState;
import org.example.Server.Model.ComponentsPack.Components;
import org.example.Server.Model.ComponentsPack.Direction;
import org.example.Server.Model.Game;
import org.example.Server.Model.Player;
import org.example.Server.Model.Points;

import java.util.ArrayList;
import java.util.List;

public class MeteorCard extends AdventureCard {
    private List<Meteor> meteorList = new ArrayList<Meteor>();
    int currentMeteor;
    int currentPlayer;
    int rowOrCol;
    boolean protect;

    public MeteorCard(int cardLevel, int lostDays, List<Meteor> meteorList){
        super(cardLevel, lostDays);
        this.meteorList=meteorList;
        this.currentMeteor=0;
        this.currentPlayer=0;
        this.rowOrCol =0;
        this.protect=false;
    }

    @Override
    public void setCardState(Game g){
        boolean check=false;
        for(int i=0; i<g.getPlayers().size()&&!check; i++){
            if(!g.getPlayers().get(i).isAbandoned()){
                currentPlayer=i;
                Player p=g.getPlayers().get(currentPlayer);
                this.rowOrCol =p.rollDice();
                check= Update(g, p);
            }
        }
    }

    private boolean Update(Game g, Player p) {
        boolean check=true;
        if (meteorList.get(currentMeteor).getType() == 0) {
            if(p.getPlayerShipBoard().getIfExposed(meteorList.get(currentMeteor).getDirection(), p.getPlayerShipBoard().getFirstComponent(meteorList.get(currentMeteor).getDirection(), rowOrCol)))
                if(p.getPlayerShipBoard().getIfShielded(meteorList.get(currentMeteor).getDirection())){
                    p.setPlayerState(new ActivateShieldsState());
                }else
                    this.playCard(g, null, null);
            else{
                protect=true;
                this.playCard(g,null,null);
            }
        } else {
            if(meteorList.get(currentMeteor).getDirection()==Direction.NORTH) {
                if (p.getPlayerShipBoard().getIfSingleCannon(Direction.NORTH, rowOrCol)) {
                    protect = true;
                    this.playCard(g, null, null);
                } else if (p.getPlayerShipBoard().getIfDoubleCannon(Direction.NORTH, rowOrCol)) {
                    p.setPlayerState(new ActivateCannonsState());
                } else {
                    this.playCard(g, null, null);
                }
            }else{
                if(p.getPlayerShipBoard().getIfSingleCannon(Direction.NORTH, rowOrCol)||p.getPlayerShipBoard().getIfSingleCannon(Direction.NORTH, rowOrCol -1)||p.getPlayerShipBoard().getIfSingleCannon(Direction.NORTH, rowOrCol +1)){
                        protect=true;
                        this.playCard(g,null,null);
                }else if(p.getPlayerShipBoard().getIfDoubleCannon(Direction.NORTH, rowOrCol)||p.getPlayerShipBoard().getIfDoubleCannon(Direction.NORTH, rowOrCol -1)||p.getPlayerShipBoard().getIfDoubleCannon(Direction.NORTH, rowOrCol +1)){
                        p.setPlayerState(new ActivateCannonsState());
                }else{
                        this.playCard(g,null,null);
                }

            }
        }
        return check;
    }

    @Override
    public void playCard(Game g, ArrayList<Points> component, ArrayList<Points> battery) {
        Player p=g.getPlayers().get(currentPlayer);
        if(!protect){
            if(component.isEmpty()||battery.isEmpty()) {
                Components c=p.getPlayerShipBoard().getFirstComponent(meteorList.get(currentMeteor).getDirection(), rowOrCol);
                p.getPlayerShipBoard().removeComponent(c.getPosX(),c.getPosY());
            }else if(meteorList.get(currentMeteor).getType()==0){
                if(!p.getPlayerShipBoard().ShieldProtects(meteorList.get(currentMeteor).getDirection(), component, battery)){
                    Components c=p.getPlayerShipBoard().getFirstComponent(meteorList.get(currentMeteor).getDirection(), rowOrCol);
                    p.getPlayerShipBoard().removeComponent(c.getPosX(),c.getPosY());
                }
            }else {
                if(!p.getPlayerShipBoard().CannonProtects(meteorList.get(currentMeteor).getDirection(), rowOrCol, component, battery)){
                    Components c=p.getPlayerShipBoard().getFirstComponent(meteorList.get(currentMeteor).getDirection(), rowOrCol);
                    p.getPlayerShipBoard().removeComponent(c.getPosX(),c.getPosY());
                }

            }
        }
        protect=false;
        p.setPlayerState(new WaitingState());
        boolean check=false;
        for(int i=currentPlayer+1; i<g.getPlayers().size()&&!check; i++){
            if(!g.getPlayers().get(i).isAbandoned()){
                currentPlayer=i;
                p=g.getPlayers().get(currentPlayer);
                check = Update(g, p);
            }
        }

        if(!check){
            if(meteorList.iterator().hasNext()){
                currentMeteor++;
                boolean player=false;
                for(int i=0; i<g.getPlayers().size()&&!player; i++){
                    if(!g.getPlayers().get(i).isAbandoned()){
                        currentPlayer=i;
                        p=g.getPlayers().get(currentPlayer);
                        this.rowOrCol =p.rollDice();
                        player = Update(g, p);
                    }
                }
            }else{
                g.Turn();
            }
        }
    }

    public int getCardLevel() {
        return super.getCardLevel();
    }

    public List<Meteor> getMeteorList() {
        return meteorList;
    }
}
