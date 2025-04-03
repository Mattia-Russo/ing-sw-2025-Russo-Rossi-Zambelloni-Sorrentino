package org.example.Server.Controller.States;

import org.example.Server.Model.Exceptions.NotEnoughAstronautsException;
import org.example.Server.Model.Game;
import org.example.Server.Model.Player;

public class LandState extends PlayerState {
    private Game game;

    public LandState(Game game){
        this.game = game;
    }

    public void land(Player p, boolean landed){ // player è il riferimento al giocatore che ha fatto la chiamata, landed true vuol dire che è atterrato
        if(landed){
            if(p.getPlayerShipBoard().getTotalAstronauts()<game.getCurrentCard().getNumAstronauts()){
                p.changeLanded();
                game.getCurrentCard().setChangeGoodsFlag(false);
                game.getCurrentCard().playCard(game);
            } else {
                throw new NotEnoughAstronautsException("You cannot land, you don't have enough Astronauts");
            }
        } else {
            game.getCurrentCard().setCardState(game);
        }

    }
}
