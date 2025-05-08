package org.example.ServerPkg.Model.ForView;

import org.example.ServerPkg.Model.ComponentsPack.Components;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
<<<<<<< HEAD
=======
import org.example.UI.GameUpdater;
>>>>>>> 576cab36c8bcf6844f6fdba40abfc0c716343a30

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameView implements Serializable{
    private final ArrayList<PlayerView> playersView = new ArrayList<>();
    private final AdventureCardView currentCard;
    private final List<ComponentsView> componentsListView = new ArrayList<>();
<<<<<<< HEAD
=======
    private GameUpdater gameUpdater; //va cambiato come lo passiamo

>>>>>>> 576cab36c8bcf6844f6fdba40abfc0c716343a30
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
<<<<<<< HEAD
        game.updateGame(this);
=======
        for (Player p : game.getPlayers()) {

        }
        gameUpdater.updateGame(this);
>>>>>>> 576cab36c8bcf6844f6fdba40abfc0c716343a30
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
