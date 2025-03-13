package org.example.CardPack;

import org.example.ComponentsPack.Components;
import org.example.ComponentsPack.Connector;
import org.example.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class MeteorCard implements AdventureCard {
    private final CardEnum cardEnum;
    private final int cardLevel;
    private final List<Meteor> meteorList = new ArrayList<Meteor>();

    public MeteorCard(){
        this.cardEnum = CardEnum.MeteorCard;
        this.cardLevel = 123;
    }

    @Override
    public CardEnum getCardEnum() {
        return cardEnum;
    }

    @Override
    public int getCardLevel() {
        return cardLevel;
    }

    public List<Meteor> getMeteorList() {
        return meteorList;
    }

    @Override
    public void playCard(ArrayList<Player> players) {
        int[] rowOrCol = new int[meteorList.size()];
        Components c;

        for(int i=0; i<meteorList.size(); i++){
            rowOrCol[i] = players.get(0).rollDice();
        }

        for (int i=0; i<meteorList.size(); i++) {   // itero sui meteoriti
            for (Player p : players) {  // itero sui player
                if (meteorList.get(i).getType() == 0) {     // se meteorite piccolo

                    // CAPIRE SE IL GIOCATORE UTILIZZA O MENO LO SCUDO

                    if(!p.getPlayerShipBoard().getIfShielded(meteorList.get(i).getDirection())){    // controlle se c'è scudo
                        c = p.getPlayerShipBoard().getFirstComponent(meteorList.get(i).getDirection(), rowOrCol[i]);    // prendo primo componente
                        if(c != null){
                            Connector connectors[] = c.getConnectors();
                            if ((connectors[(meteorList.get(i).getDirection()+2)%4])!=Connector.EMPTY) {    // controllo se c'è connettore esposto
                                p.getPlayerShipBoard().removeComponent(c.getPosX(), c.getPosY());
                            }
                        }// DA FINIRE
                    }
                }
            }
        }

        // controllo se scudi
        // controllo se connettore esposto
        // controllo se cannoni puntati


    }
}
