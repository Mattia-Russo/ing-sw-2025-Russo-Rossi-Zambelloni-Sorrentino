package org.example.ServerPkg.Model.CardPack;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.example.ServerPkg.Model.ComponentsPack.Goods;
import org.example.ServerPkg.Model.ForView.AdventureCardView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.util.ArrayList;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "cardname"
)

@JsonSubTypes({
        @JsonSubTypes.Type(value = Epidemic.class, name = "EPIDEMIC"),
        @JsonSubTypes.Type(value = OpenSpace.class, name = "OPENSPACE"),
        @JsonSubTypes.Type(value = Pirates.class, name = "PIRATES"),
        @JsonSubTypes.Type(value = Smugglers.class, name = "SMUGGLERS"),
        @JsonSubTypes.Type(value = Slavers.class, name = "SLAVERS"),
        @JsonSubTypes.Type(value = WarZone.class, name = "WARZONE"),
        @JsonSubTypes.Type(value = MeteorCard.class, name = "METEORCARD"),
        @JsonSubTypes.Type(value = AbandonedShip.class, name = "ABANDONEDSHIP"),
        @JsonSubTypes.Type(value = AbandonedStation.class, name = "ABANDONEDSTATION"),
        @JsonSubTypes.Type(value = PlanetsCard.class, name = "PLANETSCARD"),
        @JsonSubTypes.Type(value = OpenSpace.class, name = "STARDUST"),
})
public abstract class AdventureCard {
    private final int cardLevel;
    private int lostDays;

    public AdventureCard(int cardLevel, int lostDays) {
        this.cardLevel = cardLevel;
        this.lostDays = lostDays;
    }

    public AdventureCardView createView(){
        return new AdventureCardView(0, null,0, 0,0,0, null,null,null,null,0, null, null);
    }

    public void setCardState(Game game) {};

    public void playCard(Player disconnectingPlayer, Game game){};

    public void playCard(Game game){};

    public void playCard(Game game, int numPlanet){};

    public void playCard(Game game, ArrayList<Points> Engines, ArrayList<Points> Batteries){};

    public int getCardLevel(){return cardLevel;}

    public int getLostDays(){return lostDays;}

    public int getNumAstronauts(){
        return 0;
    }

    public Goods[] getGoodsList(){
        return null;
    }

    public void setChangeGoodsFlag(boolean changeGoodsFlag) {}

    public boolean[] getPlanetsVisited(){
        return null;
    }

    public int getCurrentPlayerIndex(){
        return -1;
    }

    public void setAccept(boolean accept) {}

    public int getNumGoodsLose(){
        return -1;
    }

    public void setShipWrecked(boolean shipWrecked) {}
}
