package org.example.ServerPkg.Model.ForView;
import org.example.ServerPkg.Model.CardPkg.AdventureCard;
import org.example.ServerPkg.Model.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerView implements Serializable {
    private final ShipboardView shipboardView;
    private ComponentsView currentTile = null;
    private final String name;
    private final ArrayList<AdventureCardView> deckShowed = new ArrayList<>();
    private final int position;
    private final int numCredits;
    private final boolean abandoned;
    private final String rocketColour;
    private final boolean shipOK;

    public PlayerView(Player player){
        name = player.getName();
        shipboardView = new ShipboardView(player.getPlayerShipBoard());
        if(player.getCurrentTile() != null) {
            currentTile = player.getCurrentTile().createView();
        }
        if(player.getDeckShowed() != null) {
            for (AdventureCard a : player.getDeckShowed()) {
                deckShowed.add(a.createView());
            }
        }
        position = player.getPosition();
        numCredits = player.getNumCredits();
        abandoned = player.isAbandoned();
        rocketColour = player.getRocketColour();
        shipOK = player.getShipOK();
    }

    public ShipboardView getShipboardView() {
        return shipboardView;
    }

    public String getRocketColour() {
        return rocketColour;
    }

    public ComponentsView getCurrentTile() {
        return currentTile;
    }

    public ArrayList<AdventureCardView> getDeckShowed() {
        return deckShowed;
    }

    public int getPosition() {
        return position;
    }

    public int getNumCredits() {
        return numCredits;
    }

    public String getName() {
        return name;
    }

    public boolean isAbandoned() {
        return abandoned;
    }

    public boolean isShipOK() {
        return shipOK;
    }
}
