package org.example.UIPkg;

import org.example.ServerPkg.ControllerPkg.PlayerStates.ActivateCannonsState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.ActivateEnginesState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.WaitingState;
import org.example.ServerPkg.Model.CardPkg.CannonFire;
import org.example.ServerPkg.Model.CardPkg.Meteor;
import org.example.ServerPkg.Model.CardPkg.Planet;
import org.example.ServerPkg.Model.ComponentsPkg.Connector;
import org.example.ServerPkg.Model.ComponentsPkg.Direction;
import org.example.ServerPkg.Model.ForView.*;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TUI implements UI{

    private final BlockingQueue<GameView> gameUpdatesQueue;

    public TUI() {
        gameUpdatesQueue = new LinkedBlockingQueue<>();
        startUpdateThread();
    }

    // thread che continua a leggere i game update in coda con un while(true)
    private void startUpdateThread() {
        Thread UpdateThread = new Thread(() -> {
            try {
                while (true) {
                    if(!gameUpdatesQueue.isEmpty()) {
                        Draw();
                    }
                }
            }catch (Exception e) {
                System.err.println("Error sending connection update to server: " + e.getMessage());
            }
        });
        UpdateThread.setDaemon(false);
        UpdateThread.start();  // Avvia il thread
    }

    @Override
    public void addGameUpdate(GameView game) {
        try {
            gameUpdatesQueue.put(game);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error inserting game update", e);
        }
    }

    //stringbuilder per disegni migliori
    private void Draw() {
        GameView game = gameUpdatesQueue.poll();
        if(game.getException() == null) {
            int i=0;
            for(ComponentsView c: game.getComponentsDiscovered()){
                DrawComponent(c);
                System.out.println(i);
                i++;
            }
            if (game.getCurrentCard() != null) {
                DrawCurrentCard(game.getCurrentCard());
            }
            DrawShipboard(game.getPlayers());
            DrawFlightBoard(game.getPlayers());
        }else
            System.out.println(game.getException().getMessage());
    }

    private void DrawFlightBoard(List<PlayerView> players) {
        for(PlayerView player : players) {
            System.out.println(player.getName());
            System.out.println(player.getPosition());
            System.out.println(player.getRocketColour());
        }

    }

    private void DrawShipboard(List<PlayerView> players) {
        for(PlayerView player : players) {
            System.out.println(player.getName());
            if(player.getDeckShowed()==null) {
                System.out.println(player.getCurrentTile());
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 7; j++) {
                        if (player.getShipboardView().getComponentsView()[i][j] != null) {
                            System.out.println(i + "," + j);
                            DrawComponent(player.getShipboardView().getComponentsView()[i][j]);
                        }
                    }
                }
            }else {
                for (int i = 0; i < player.getDeckShowed().size(); i++) {
                    System.out.println(player.getDeckShowed().get(i));
                }
            }
        }
    }

    private void DrawComponent(ComponentsView componentsView) {
        String type=componentsView.getType();
        for(Connector connector : componentsView.getConnectors()) {
            System.out.println(connector);
        }
        System.out.println(componentsView.getDirection());
        switch (type) {
            case "Cannon":
                System.out.println("Cannon");
                break;
            case "Shield":
                System.out.println("Shield");
                System.out.println(componentsView.getShieldedDirections()[0]);
                System.out.println(componentsView.getShieldedDirections()[1]);
                break;
            case "DoubleCannon":
                System.out.println("DoubleCannon");
                break;
            case "LifeSupportSystem":
                System.out.println("LifeSupportSystem");
                System.out.println(componentsView.getAlienColour());
                break;
            case "Cabin":
                System.out.println("Cabin");
                if(componentsView.getAlienColour()!=null) {
                    System.out.println(componentsView.getAlienColour());
                }else
                    System.out.println(componentsView.getNumAstronauts());
                break;
            case "Tubes":
                System.out.println("Tubes");
                break;
            case "Engine":
                System.out.println("Engine");
                break;
            case "DoubleEngine":
                System.out.println("DoubleEngine");
                break;
            case "Storage":
                System.out.println("Storage");
                for(GoodsView good: componentsView.getGoods()) {
                    System.out.println(good);
                }
                break;
            case"BatteryStorage":
                System.out.println("BatteryStorage");
                System.out.println(componentsView.getNumBattery());
                break;
        }
    }

    
    private void DrawCurrentCard(AdventureCardView adventureCardView) {
        String type=adventureCardView.getType();

        switch (type){
            case "AbandonedShip":
                System.out.println("AbandonedShip");
                System.out.println(adventureCardView.getNumAstronauts());
                System.out.println(adventureCardView.getNumCredits());
                System.out.println(adventureCardView.getLostDays());
                break;
            case "AbandonedStation":
                System.out.println("AbandonedStation");
                System.out.println(adventureCardView.getNumAstronauts());
                System.out.println(adventureCardView.getLostDays());
                for(GoodsView goods: adventureCardView.getGoodsList()){
                    System.out.println(goods);
                }
                break;
            case "Epidemic":
                System.out.println("Epidemic");
                break;
            case "MeteorCard":
                System.out.println("MeteorCard");
                for(Meteor meteor: adventureCardView.getMeteorList()){
                    System.out.println(meteor);
                }
                break;
            case "OpenSpace":
                System.out.println("OpenSpace");
                break;
            case "Pirates":
                System.out.println("Pirates");
                System.out.println(adventureCardView.getCannonPower());
                System.out.println(adventureCardView.getNumCredits());
                System.out.println(adventureCardView.getLostDays());
                for(CannonFire fire: adventureCardView.getCannonFireList()){
                    System.out.println(fire);
                }
                break;
            case "PlanetCard":
                System.out.println("PlanetCard");
                for(PlanetView planet: adventureCardView.getPlanetList()){
                    System.out.println(planet.getPlanetNumber());
                    System.out.println(planet.getGoods());
                    System.out.println(adventureCardView.getLostDays());
                }
                break;
            case "Slavers":
                System.out.println("Slavers");
                System.out.println(adventureCardView.getCannonPower());
                System.out.println(adventureCardView.getNumCredits());
                System.out.println(adventureCardView.getLostDays());
                System.out.println(adventureCardView.getNumAstronauts());
                break;
            case "Smugglers":
                System.out.println("Smugglers");
                System.out.println(adventureCardView.getCannonPower());
                System.out.println(adventureCardView.getNumCredits());
                System.out.println(adventureCardView.getLostDays());
                System.out.println(adventureCardView.getNumGoods());
                System.out.println(adventureCardView.getGoodsList());
                break;
            case "Stardust":
                System.out.println("Stardust");
                break;
            case "WarZone":
                System.out.println("WarZone");
                for(int i=0; i<3; i++){
                    switch (adventureCardView.getCriteria()[i]) {
                        case "FewestAstronauts":
                            System.out.println("FewestAstronauts");
                            break;
                        case "LessEnginePower":
                            System.out.println("LessEnginePower");
                            break;
                        case "LessCannonPower":
                            System.out.println("LessCannonPower");
                            break;
                    }

                    switch (adventureCardView.getPenalties()[i]) {
                        case "LoseDays":
                            System.out.println(adventureCardView.getLostDays());
                            break;
                        case "LoseGoods":
                            System.out.println(adventureCardView.getGoodsList());
                            break;
                        case "cannonFire":
                            System.out.println(adventureCardView.getCannonFireList());
                            break;
                        case "LoseAstronauts":
                            System.out.println(adventureCardView.getNumAstronauts());
                            break;
                    }
                }
        }
    }
}