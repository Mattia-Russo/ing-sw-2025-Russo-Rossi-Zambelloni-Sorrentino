package org.example.CardPack;

import java.util.ArrayList;
import java.util.List;

public class PlanetsCard extends AdventureCard {
    private List<Planet> planets = new ArrayList<Planet>();

    public PlanetsCard(int cardLevel, int numDays, List<Planet> planets) {
        super(cardLevel, numDays);
        this.planets=planets;
    }
    public List<Planet> getPlanets(){
        return planets;
    }
}
