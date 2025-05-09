package org.example.ServerPkg.Model.ForView;
import org.example.ServerPkg.Model.CardPkg.AdventureCard;
import org.example.ServerPkg.Model.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerView implements Serializable {
    private final ShipboardView shipboardView;
    private final ComponentsView currentTile;
    private final String name;
    private final ArrayList<AdventureCardView> deckShowed = new ArrayList<>();
    private final int position;
    private final int numCredits;
    private final boolean abandoned;

    public PlayerView(Player player){
        name = player.getName();
        shipboardView = new ShipboardView(player.getPlayerShipBoard());
        currentTile = player.getCurrentTile().createView();
        for(AdventureCard a: player.getDeckShowed()){
            deckShowed.add(a.createView());
        }
        position = player.getPosition();
        numCredits = player.getNumCredits();
        abandoned = player.isAbandoned();
    }

    public ShipboardView getShipboardView() {
        return shipboardView;
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
}
