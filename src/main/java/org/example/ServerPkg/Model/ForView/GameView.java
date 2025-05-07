package org.example.ServerPkg.Model.ForView;

import org.example.ServerPkg.Model.ComponentsPack.Components;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.UI.GameUpdater;

import java.util.ArrayList;
import java.util.List;

public class GameView {
    private final ArrayList<PlayerView> playersView = new ArrayList<>();
    private final AdventureCardView currentCard;
    private final List<ComponentsView> componentsListView = new ArrayList<>();
    private GameUpdater gameUpdater; //va cambiato come lo passiamo

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
        for (Player p : game.getPlayers()) {

        }
        gameUpdater.updateGame(this);
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
