package org.example.ServerPkg.Model.ForView;

import org.example.ServerPkg.Model.ComponentsPkg.Components;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameView implements Serializable{
    private final ArrayList<PlayerView> playersView = new ArrayList<>();
    private final AdventureCardView currentCard;
    private final List<ComponentsView> componentsListView = new ArrayList<>();
    private final Exception exception;

    public GameView(Game game, Exception exception) {
        for(Player p:game.getPlayers()){
                playersView.add(new PlayerView(p));
        }
        this.exception = exception;
        for(Components c: game.getComponentsList()){
            if(!c.getIfCovered()){
                componentsListView.add(c.createView());
            }
        }
        this.currentCard = game.getCurrentCard().createView();
        game.updateGame(this);
    }

    public AdventureCardView getCurrentCard() {
        return currentCard;
    }

    public List<ComponentsView> getComponentsList() {
        return componentsListView;
    }
    public List<PlayerView> getPlayers() {
        return playersView;
    }

    public Exception getException() {
        return exception;
    }

}
