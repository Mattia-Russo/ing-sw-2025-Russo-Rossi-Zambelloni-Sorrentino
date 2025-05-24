package org.example.UIPkg;

import org.example.MessagePkg.NotifyClientMessage;
import org.example.ServerPkg.Model.CardPkg.CannonFire;
import org.example.ServerPkg.Model.CardPkg.Meteor;
import org.example.ServerPkg.Model.ComponentsPkg.AlienColour;
import org.example.ServerPkg.Model.ComponentsPkg.Connector;
import org.example.ServerPkg.Model.ComponentsPkg.Direction;
import org.example.ServerPkg.Model.ComponentsPkg.GoodsColour;
import org.example.ServerPkg.Model.ForView.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TUI implements UI{

    private final BlockingQueue<GameView> gameUpdatesQueue;
    private final Client client;

    public TUI(Client client) {
        this.client = client;
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
        UpdateThread.start();
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
            System.out.println("Discovered tile: ");
            for (ComponentsView c : game.getComponentsDiscovered()) {
                List<String> lines = List.of(DrawComponent(c));
                for (String line : lines) System.out.println(line);
                System.out.println("[" + i + "]");
                i++;
            }
            System.out.println("\nCurrent tile:");
            for (PlayerView player : game.getPlayers()) {
                if (player.getDeckShowed() == null && player.getCurrentTile() != null) {
                    List<String> current = List.of(DrawComponent(player.getCurrentTile()));
                    for (String line : current) System.out.println(line);
                    break;
                }
            }

            System.out.println("\nShipboard:");
            DrawShipboard(game.getPlayers());
            System.out.println("Current Card: ");
            if (game.getCurrentCard() != null) {
                DrawCurrentCard(game.getCurrentCard());
            }
            DrawShipboard(game.getPlayers());
            System.out.println("Flightboard: ");
            DrawFlightBoard(game.getPlayers());
            drawCommands(game);
        }else
            System.out.println(game.getException().getMessage());
    }

    public void drawCommands(GameView game) {
        switch (game.getLobbyState()){
            //mancano set_name e set_position che sembra che non dobbiamo inserire
            case GAME_CREATION:
                System.out.println(
                        "Only the creator can start the game, so if you want to start the game, type:\n" +
                        "   start_game\n");
                break;
            case GAME_READY:
                System.out.println(
                        "If you are the lobby creator and there are enough players connected type start_game to start the game"
                );
                break;
            case GAME_STARTED:
                if(game.getCurrentCard()!=null){
                    System.out.println(
                            """
                                    Type one of the following command to do something:
                                       accept_reward true/false -> true if you want to accept the reward, false otherwise
                                       activate_cannons x y -> x,y are the coordinates of a cannon, you should write a number of x,y based on the number of cannons you want to activate
                                       activate_engines x y -> x,y are the coordinates of an engine, you should write a number of x,y based on the number of engines you want to activate
                                       activate_shields x y -> x,y are the coordinates of a shield, you should write a number of x,y based on the number of shields you want to activate
                                       use_batteries x y -> x,y are the coordinates of the battery storage, you should write a number of x,y based on the number of batteries you want to use
                                    
                                       end_activate_cannons -> if you want to end the cannon activation phase
                                       end_activate_engines -> if you want to end the engine activation phase
                                       end_change_goods_state -> if you want to end the change good phase
                                       end_activate_shields -> if you want to end the shield activation phase
                                       end_remove_best_goods -> if you want to end the remove best goods phase
                                       end_remove_astronauts -> if you want to end the remove astronauts phase
                                       end_fix_ship_state -> if you want to end the fix ship phase 
                                        
                                       land_on_abandon true/false ->  true if you want to land, false otherwise
                                       land_on_planet true/false numPlanet true if you want to land, false otherwise; numPlanet is the number of Planet where you want to land
                                    
                                       add_good x y numGood -> x,y are the coordinates of the storage where you want to add the good, numGood is the number of goods you want to add
                                       remove_good x y numGood -> x,y are the coordinates of the storage where you want to remove the good, numGood is the number of goods you want to remove
                                       remove_best_good x y numGood -> x,y are the coordinates of the storage where you want to remove the good, numGood is the number of goods you want to remove
                                       remove_astronauts x y -> x,y are the coordinates of the cabin where you want to remove the astronauts
                                       remove_batteries x y -> x,y are the coordinates of the cabin where you want to remove batteries
                                    """
                    );
                }
                else{
                    System.out.println(
                            """
                                    Type one of the following command to do something:
                                       pick_tile -> if you want to pick a random covered component
                                       pick_discovered_tile index-> if you want to pick discovered component with this index
                                       left_rotate -> if you want to left rotate the tile
                                       right_rotate -> if you want to right rotate the tile
                                       place_tile x y -> x,y are the coordinates of the cell where you want to place the tile
                                       discard_tile -> if you want to discard the component you picked
                                       remove_tile x y -> x,y are the coordinates of the tile you want to remove
                                    
                                       book_tile -> place the current component in a booked slot
                                       pick_booked_tile index -> if you want to pick a booked component with this index
                                    
                                       add_brown_alien x y -> x,y are the coordinates of the cabin where you want to add the brown alien
                                       add_purple_alien x y -> x,y are the coordinates of the cabin where you want to add the purple alien
                                       end_add_alien -> if you want to end the add alien phase
                                    
                                       show_deck numDeck -> numDeck is the number of the deck you want to see
                                       end_show_deck -> if you want to end the show deck phase
                                    
                                       turn_timer -> if you want to turn the timer
                                    
                                       end_build_ship -> if you to end the build ship phase
                                    """
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
    private String[] DrawComponent(ComponentsView comp) {
        String[] box = new String[5]; // 4 righe + bordo inferiore
        String[][] grid = new String[4][4];
        for (int i = 0; i < 4; i++)
            Arrays.fill(grid[i], " ");
        if (comp != null) {
            Connector[] conns =comp.getConnectors();
            String type =comp.getType();
            String[] initials =getComponentInitials(type);

            grid[0][1] = getConnectorSymbol(conns[0]);
            grid[1][0] = getConnectorSymbol(conns[3]);
            grid[1][3] = getConnectorSymbol(conns[1]);
            grid[3][1] = getConnectorSymbol(conns[2]);
            grid[0][2] = getDirectionLetter(comp.getDirection());
            grid[1][1] = initials[0];
            if (initials.length > 1) grid[1][2] = initials[1];

            String detail = getComponentDetail(comp);
            for (int i =0; i< Math.min(4,detail.length()); i++) {
                grid[2][i] = String.valueOf(detail.charAt(i));
            }
        }

        for (int i = 0; i < 4; i++) {
            box[i] = "│" +String.join("", grid[i]) + "│";
        }

        // Riga finale: chiusura del quadrato
        box[4] = "└────┘";
        return box;
    }
    private void DrawShipboard(List<PlayerView> players) {
        final int ROWS = 5;
        final int COLS = 7;

        for (PlayerView player : players) {
            System.out.println("Shipboard of " + player.getName() + ":");

            System.out.print("     ");
            for (int col = 0; col < COLS; col++) {
                System.out.print(" Col " + col + " ");
            }
            System.out.println();

            ComponentsView[][] matrix = player.getShipboardView().getComponentsView();

            for (int row = 0; row < ROWS; row++) {
                StringBuilder[] line = new StringBuilder[5];
                for (int i = 0; i < 5; i++) line[i] = new StringBuilder();
                for (int col = 0; col < COLS; col++) {
                    String[] box = DrawComponent(matrix[row][col]);
                    for (int i = 0; i < 5; i++) {
                        line[i].append(box[i]).append(" ");
                    }
                }

                System.out.printf(" %d   %s\n", row, line[0].toString());
                for (int i =1; i<5; i++) {
                    System.out.print("     ");
                    System.out.println(line[i].toString());
                }
            }
        }
    }
    private String[] getComponentInitials(String type) {
        return switch (type) {
            case "Cabin" -> new String[]{"C", "b"};
            case "Storage" -> new String[]{"S", "t"};
            case "LifeSupportSystem" -> new String[]{"L", "S"};
            case "Shield" -> new String[]{"S", "h"};
            case "Cannon" -> new String[]{"C", "a"};
            case "DoubleCannon" -> new String[]{"D", "c"};
            case "Tubes" -> new String[]{"T", "b"};
            case "Engine" -> new String[]{"E", "n"};
            case "DoubleEngine" -> new String[]{"D", "e"};
            default -> new String[]{type.substring(0, 1)};
        };
    }

    private String getConnectorSymbol(Connector c) {
        return switch (c) {
            case SINGLE:
                yield "-";
            case DOUBLE:
                yield "=";
            case UNIVERSAL:
                yield "#";
            default:
                yield " ";
        };
    }

    private String getDirectionLetter(Direction d) {
        return switch (d) {
            case NORTH:
                yield "N";
            case EAST:
                yield "E";
            case SOUTH:
                yield "S";
            case WEST:
                yield"W";
        };
    }

    private String getComponentDetail(ComponentsView comp) {
        switch (comp.getType()) {
            case "Cabin":
                if (comp.getAlienColour() != null) {
                    return getAlienColorBlock(comp.getAlienColour());
                } else {
                    return String.valueOf(comp.getNumAstronauts());
                }

            case "LifeSupportSystem":
                if (comp.getAlienColour() != null) {
                    return getAlienColorBlock(comp.getAlienColour());
                } else {
                    return "";
                }

            case "Storage":
                StringBuilder goods = new StringBuilder();
                List<GoodsView> goodsList = List.of(comp.getGoods());
                for (int i = 0; i < goodsList.size(); i++) {
                    GoodsColour color = goodsList.get(i).getColour();
                    goods.append(getGoodColorSquare(color));
                }
                return goods.toString();

            case "Shield":
                return getDirectionArrow(comp.getDirection()) + getDirectionArrow(comp.getDirection());

            default:
                return "";
        }
    }

    private String getAlienColorBlock(AlienColour color) {
        return switch (color) {
            case BROWN:
                yield"[B]";
            case PURPLE:
                yield "[P]";
        };
    }

    private String getGoodColorSquare(GoodsColour colour) {
        return switch (colour) {
            case RED:
                yield"\u001B[41m█\u001B[0m";
            case YELLOW:
                yield "\u001B[43m█\u001B[0m";
            case GREEN:
                yield"\u001B[42m█\u001B[0m";
            case BLUE:
                yield"\u001B[44m█\u001B[0m";
        };
    }

    private String getDirectionArrow(Direction d) {
        return switch (d) {
            case NORTH:
                yield"↑";
            case EAST:
                yield "→";
            case SOUTH:
                yield "↓";
            case WEST:
                yield "←";
        };
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

    @Override
    public void printNameInvalid(){
        System.out.println("Name already taken");
    }

    @Override
    public void askName(){
        System.out.println("Type your name: ");
    }

    @Override
    public void readName(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        List<String> args = new ArrayList<>();
        args.add(input);
        client.registerName(args);
    }

    @Override
    public void manageNotification(NotifyClientMessage notifyClientMessage){
        System.out.println(notifyClientMessage.getMessage());
    }

    public void onNameAccepted(){}

    public void onLobbyCreated(){}

    public void onLobbyJoined(){}

    public void printMessage(String message){
        System.out.println(message);
    }
}