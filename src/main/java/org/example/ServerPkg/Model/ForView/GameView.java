package org.example.ServerPkg.Model.ForView;

import org.example.ServerPkg.ControllerPkg.LobbyState;
import org.example.ServerPkg.Model.ComponentsPkg.Components;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameView implements Serializable{
    private final ArrayList<PlayerView> playersView = new ArrayList<>();
    private AdventureCardView currentCard = null;
    private final List<ComponentsView> componentsDiscoveredView = new ArrayList<>();
    private Exception exception = null;
    private final LobbyState lobbyState;
    private final int shipBoardLevel;

    public GameView(Game game, Exception exception) {
        if (exception == null){
            for (Player p : game.getPlayers()) {
                playersView.add(new PlayerView(p));
            }
            if(game.getDiscoveredComponent() != null) {
                for (Components c : game.getDiscoveredComponent()) {
                    this.componentsDiscoveredView.add(c.createView());
                }
            }
            if(game.getCurrentCard() != null) {
                this.currentCard = game.getCurrentCard().createView();
            }
        }else
            this.exception = exception;
        this.lobbyState = game.getController().getLobbyState();
        this.shipBoardLevel = game.getShipBoardLevel();
        game.updateGame(this);
    }

    public AdventureCardView getCurrentCard() {
        return currentCard;
    }

    public List<PlayerView> getPlayers() {
        return playersView;
    }

    public Exception getException() {
        return exception;
    }

    public List<ComponentsView> getComponentsDiscovered() {
        return componentsDiscoveredView;
    }

    public LobbyState getLobbyState() {
        return lobbyState;
    }

    public int getShipBoardLevel() {
        return shipBoardLevel;
    }
}
