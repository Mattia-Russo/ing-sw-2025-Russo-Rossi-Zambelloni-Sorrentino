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

import javax.swing.text.ComponentView;
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
            System.out.println("TUI says: gameView added");
            gameUpdatesQueue.put(game);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error inserting game update", e);
        }
    }


    //stringbuilder per disegni migliori
    private void Draw() {
        System.out.println("TUI says: Drawing gameView");
        GameView game = gameUpdatesQueue.poll();
        if(game.getException() == null) {
            int i=0;
            System.out.println("Discovered components: ");
            for(ComponentsView c: game.getComponentsDiscovered()){
                DrawComponent(c);
                System.out.println(i);
                i++;
            }
            System.out.println("Current Card: ");
            if (game.getCurrentCard() != null) {
                DrawCurrentCard(game.getCurrentCard());
            }
            DrawShipboard(game.getPlayers());
            System.out.println("Flightboard: ");
            DrawFlightBoard(game.getPlayers());
        }else
            System.out.println(game.getException().getMessage());
        drawCommands(game);
    }


    public void drawCommands(GameView game) {
        switch (game.getLobbyState()){
            //mancano set_name e set_position che sembra che non dobbiamo inserire
            case GAME_CREATION:
                System.out.println(
                    "Type one of the following command to do something:\n" +
                    "   join_lobby -> if you want to join an existing lobby\n" +
                    "   start_game -> if you want to start the game\n\n");
                break;
            case GAME_READY:
                if(game.getCurrentCard()!=null){
                    System.out.println(
                            "Type one of the following command to do something:\n" +
                                    "   accept_reward true/false -> true if you want to accept the reward, false otherwise\n" +

                                    "   activate_cannons x y -> x,y are the coordinates of a cannon, you should write a number of x,y based on the number of cannons you want to activate\n" +
                                    "   activate_engines x y -> x,y are the coordinates of an engine, you should write a number of x,y based on the number of engines you want to activate\n" +
                                    "   activate_shields x y -> x,y are the coordinates of a shield, you should write a number of x,y based on the number of shields you want to activate\n" +
                                    "   use_batteries x y -> x,y are the coordinates of the battery storage, you should write a number of x,y based on the number of batteries you want to use\n\n" +

                                    "   end_activate_cannons -> if you want to end the cannon activation phase\n" +
                                    "   end_activate_engines -> if you want to end the engine activation phase\n" +
                                    "   end_change_goods_state -> if you want to end the change good phase\n" +
                                    "   end_activate_shields -> if you want to end the shield activation phase\n" +
                                    "   end_remove_best_goods -> if you want to end the remove best goods phase\n" +
                                    "   end_remove_astronauts -> if you want to end the remove astronauts phase\n\n" +

                                    "   land_on_abandon true/false ->  true if you want to land, false otherwise\n" +
                                    "   land_on_planet true/false numPlanet true if you want to land, false otherwise; numPlanet is the number of Planet where you want to land\n\n" +

                                    "   add_good x y numGood -> x,y are the coordinates of the storage where you want to add the good, numGood is the number of goods you want to add\n" +
                                    "   remove_good x y numGood -> x,y are the coordinates of the storage where you want to remove the good, numGood is the number of goods you want to remove\n" +
                                    "   remove_best_good x y numGood -> x,y are the coordinates of the storage where you want to remove the good, numGood is the number of goods you want to remove\n" +
                                    "   remove_astronauts x y -> x,y are the coordinates of the cabin where you want to remove the astronauts\n" +
                                    "   remove_batteries x y -> x,y are the coordinates of the cabin where you want to remove batteries\n\n"
                    );
                }
                else{
                    System.out.println(
                            "Type one of the following command to do something:\n" +
                                "   pick_component_tile -> if you want to pick a random covered component\n" +
                                "   pick_discovered_component index-> if you want to pick discovered component with this index\n" +
                                "   left_rotate -> if you want to left rotate the tile\n" +
                                "   right_rotate -> if you want to right rotate the tile\n" +
                                "   place_tile x y -> x,y are the coordinates of the cell where you want to place the tile\n" +
                                "   discard_component -> if you want to discard the component you picked\n" +
                                "   remove_tile x y -> x,y are the coordinates of the tile you want to remove\n\n" +

                                "   add_brown_alien x y -> x,y are the coordinates of the cabin where you want to add the brown alien\n" +
                                "   add_purple_alien x y -> x,y are the coordinates of the cabin where you want to add the purple alien\n" +
                                "   end_add_alien -> if you want to end the add alien phase\n\n" +

                                "   show_deck numDeck -> numDeck is the number of the deck you want to see\n" +
                                "   end_show_deck -> if you want to end the show deck phase\n\n" +

                                "   turn_timer -> if you want to turn the timer\n\n"+

                                "   end_build_ship -> if you to end the build ship phase\n\n"
                    );
                }
                break;
            case GAME_FINISHED:
                System.out.println("Type the command: exit_game -> if you want to exit the game\n\n");
                break;
        }
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
            System.out.println("current tile:");
            if(player.getCurrentTile()!=null) {
                DrawComponent(player.getCurrentTile());
            }
            System.out.println("Shipboard: ");
            StringBuilder sb= new StringBuilder();
            ComponentsView[][] matrix= player.getShipboardView().getComponentsView();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 7; j++) {
                    ComponentsView comp=matrix[i][j];
                    if(comp!=null){
                        sb.append("[").append(i).append(",").append(j).append("]\n");
                        DrawComponent(comp);
                    }
                }
            }
            System.out.println(sb);
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