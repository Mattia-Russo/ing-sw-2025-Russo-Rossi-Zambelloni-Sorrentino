package org.example.CardPack;

import java.util.ArrayList;
import java.util.List;

public class PlanetsCard extends LoseDays {
    private final int cardLevel;
    private final CardEnum cardEnum;
    private final List<Planet> planetsList = new ArrayList<Planet>();

    public PlanetsCard() {
        this.cardEnum = CardEnum.PlanetCard;
        this.cardLevel = 123;
    }

    @Override
    public CardEnum getCardEnum() {
        return cardEnum;
    }

    public int getCardLevel() {
        return cardLevel;
    }

    public List<Planet> getPlanetList() {
        return planetsList;
    }
}
