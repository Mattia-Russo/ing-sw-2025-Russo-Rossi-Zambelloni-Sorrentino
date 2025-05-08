package org.example.ServerPkg.Model.ForView;

import org.example.ServerPkg.Model.ComponentsPack.Components;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameView implements Serializable{
    private final ArrayList<PlayerView> playersView = new ArrayList<>();
    private final AdventureCardView currentCard;
    private final List<ComponentsView> componentsListView = new ArrayList<>();

    public GameView(Game game) {
        for(Player p:game.getPlayers()){
                playersView.add(new PlayerView(p));
        }

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

}
