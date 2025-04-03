package org.example.Server.Model.CardPack;

import org.example.Server.Controller.States.ActivateCannonsState;
import org.example.Server.Controller.States.ActivateShieldsState;
import org.example.Server.Controller.States.WaitingState;
import org.example.Server.Model.Game;
import org.example.Server.Model.Player;

import java.util.ArrayList;
import java.util.List;

public class MeteorCard extends AdventureCard {
    private List<Meteor> meteorList = new ArrayList<Meteor>();
    int currentMeteor;
    int currentPlayer;

    public MeteorCard(int cardLevel, int lostDays, List<Meteor> meteorList){
        super(cardLevel, lostDays);
        this.meteorList=meteorList;
        this.currentMeteor=0;
        this.currentPlayer=0;
    }

    @Override
    public void setCardState(Game g){
        boolean check=false;
        for(int i=0; i<g.getPlayers().size()&&!check; i++){
            if(!g.getPlayers().get(i).isAbandoned()){
                if (meteorList.get(currentMeteor).getType() == 0) {
                    g.getPlayers().get(i).setPlayerState(new ActivateShieldsState());

                } else {
                    g.getPlayers().get(i).setPlayerState(new ActivateCannonsState());
                }
                currentPlayer=i;
                check=true;
            }
        }
    }

    @Override
    public void playCard(Game g) {
        if(meteorList.get(currentMeteor).getType()==0){

        }else {

        }
        g.getPlayers().get(currentPlayer).setPlayerState(new WaitingState());
        boolean check=false;
        for(int i=currentPlayer+1; i<g.getPlayers().size()&&!check; i++){
            if(!g.getPlayers().get(i).isAbandoned()){
                if (meteorList.get(currentMeteor).getType() == 0) {
                    g.getPlayers().get(i).setPlayerState(new ActivateShieldsState());

                } else {
                    g.getPlayers().get(i).setPlayerState(new ActivateCannonsState());
                }
                currentPlayer=i;
                check=true;
            }
        }

        if(!check){
            if(meteorList.iterator().hasNext()){
                currentMeteor++;
                boolean player=false;
                for(int i=0; i<g.getPlayers().size()&&!player; i++){
                    if(!g.getPlayers().get(i).isAbandoned()){
                        if (meteorList.get(currentMeteor).getType() == 0) {
                            g.getPlayers().get(i).setPlayerState(new ActivateShieldsState());

                        } else {
                            g.getPlayers().get(i).setPlayerState(new ActivateCannonsState());
                        }
                        currentPlayer=i;
                        player=true;
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
