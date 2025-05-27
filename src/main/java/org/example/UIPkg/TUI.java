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
    private List<String> playersList;
    private int nameIndex;

    public TUI(Client client) {
        this.client = client;
        this.playersList  = new ArrayList<>();
        gameUpdatesQueue = new LinkedBlockingQueue<>();
        startUpdateThread();
    }

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
                e.printStackTrace();
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

    //string builder per disegni migliori
    private void Draw() {
        GameView game = gameUpdatesQueue.poll();
        assert game != null;
        if(game.getException() == null) {
            int i=0;
            System.out.println("Discovered tile: ");
            for (ComponentsView c : game.getComponentsDiscovered()) {
                List<String> lines = List.of(DrawComponent(c));
                for (String line : lines) System.out.println(line);
                System.out.println("[" + i + "]");
                i++;
            }

            System.out.println("\nShipboard:");
            DrawShipboard(game.getPlayers(), game.getShipBoardLevel());
            System.out.println("Current Card: ");
            if (game.getCurrentCard() != null) {
                DrawCurrentCard(game.getCurrentCard());
            }
            System.out.println("Flight board: ");
            DrawFlightBoard(game.getPlayers());
        } else
            System.out.println(game.getException().getMessage());
        drawCommands(game);
    }

    public void drawCommands(GameView game) {
        switch (game.getLobbyState()){
            //mancano set_name e set_position che sembra che non dobbiamo inserire
            case GAME_CREATION:
                System.out.println(
                        """
                                Only the creator can start the game, so if you want to start the game, type:
                                   start_game
                                """);
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
                                       book_tile -> place the current component in a booked slot
                                       pick_booked_tile index -> if you want to pick a booked component with this index
                                    
                                       remove_tile x y -> x,y are the coordinates of the tile you want to remove
                                       end_fix_ship -> if you want to end the fix ship phase
                                    
                                       choose_wrecked x y -> x,y are the coordinates of one of the tile from the part you want to keep
                                       end_wrecked -> if you want to end the wrecked ship phase
                                    
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
        String[] box = new String[9]; // 7 righe + bordi
        box[0] = "┌─────────┐";
        String[][] grid = new String[7][9];
        for (String[] strings : grid) Arrays.fill(strings, " ");
        if (comp != null) {
            Connector[] connectors =comp.getConnectors();
            String[] initials = getComponentInitials(comp.getType());

            grid[0][4] = getConnectorSymbol(connectors[0]);
            grid[4][0] = getConnectorSymbol(connectors[3]);
            grid[4][8] = getConnectorSymbol(connectors[1]);
            grid[6][4] = getConnectorSymbol(connectors[2]);
            grid[0][8] = getDirectionLetter(comp.getDirection());

            for (int i=0; i<initials.length; i++){
                for(int j=0; j<initials[i].length(); j++)
                    grid[i+1][j+1] = String.valueOf(initials[i].charAt(j));;
            }

            String detail = getComponentDetail(comp);
            for (int i = 0; i< Math.min(8,detail.length()); i++) {
                grid[4][i] = String.valueOf(detail.charAt(i));
            }
        }

        for (int i = 0; i < 7; i++) {
            int j = i+1;
            box[j] = "│" +String.join("", grid[i]) + "│";
        }

        // Riga finale: chiusura del quadrato
        box[8] = "└─────────┘";
        return box;
    }

    private void DrawShipboard(List<PlayerView> players, int shipboardLevel) {
        final int ROWS = 5;
        final int COLS = 7;

        for (PlayerView player : players) {
            System.out.println("Board of " + player.getName() + ":");
            System.out.println("\nCurrent tile:");
            if (player.getCurrentTile() != null) {
                List<String> lines = List.of(DrawComponent(player.getCurrentTile()));
                for (String line : lines) System.out.println(line);
            }

            System.out.println("\nBooked tiles:");
            if(player.getShipboardView().getBookedComponents() != null){
                for(ComponentsView c: player.getShipboardView().getBookedComponents()){
                    List<String> lines = List.of(DrawComponent(c));
                    for (String line : lines) System.out.println(line);
                }
            }

            if (player.getDeckShowed() != null) {
                System.out.println("\nDeck:");
                for(AdventureCardView c: player.getDeckShowed()){
                    DrawCurrentCard(c);
                }
            }

            System.out.println("\nShipboard:");

            System.out.print("       ");
            for (int col = 0; col < COLS; col++) {
                System.out.printf("    Col %d    ", col);
            }
            System.out.println();

            ComponentsView[][] matrix = player.getShipboardView().getComponentsView();

            for (int row = 0; row < ROWS; row++) {
                StringBuilder[] line = new StringBuilder[9];
                for (int i = 0; i < line.length; i++) line[i] = new StringBuilder();

                for (int col = 0; col < COLS; col++) {
                    if (shouldPrintCell(row, col, shipboardLevel)) {
                        String[] box = DrawComponent(matrix[row][col]);
                        for (int i = 0; i < line.length; i++) {
                            line[i].append(box[i]).append("  ");
                        }
                    } else {
                        //print spazio vuoto
                        for (StringBuilder stringBuilder : line) {
                            stringBuilder.append("             ");
                        }
                    }
                }

                System.out.printf(" %d     %s\n", row, line[0]);
                for (int i = 1; i < line.length; i++) {
                    System.out.print("       ");
                    System.out.println(line[i].toString());
                }
            }
        }
    }

    private boolean shouldPrintCell(int row, int col, int shipboardLevel) {
        return switch (shipboardLevel) {
            case 1 -> {
                if (row == 0) {
                    yield col == 3;
                } else if (row == 1) {
                    yield col >= 2 && col <= 4;
                } else if (row == 2) {
                    yield col >= 1 && col <= 5;
                } else if (row == 3) {
                    yield col >= 1 && col <= 5;
                } else if (row == 4) {
                    yield col == 1 || col == 2 || col == 4 || col == 5;
                }
                yield false;
            }
            case 2 -> {
                if (row == 0) {
                    yield col == 2 || col == 4;
                } else if (row == 1) {
                    yield col >= 1 && col <= 5;
                } else if (row == 2) {
                    yield col >= 0 && col <= 6;
                } else if (row == 3) {
                    yield col >= 0 && col <= 6;
                } else if (row == 4) {
                    yield (col >= 0 && col <= 2) || (col >= 4 && col <= 6);
                }
                yield false;
            }
            default -> true;
        };
    }

    private String[] getComponentInitials(String type) {
        return switch (type) {
            case "Central Cabin" -> new String[]{"Central", "Cabin ", "       "};
            case "Cabin" -> new String[]{"       ", " Cabin ", "       "};
            case "Storage" -> new String[]{"       ", "Storage", "       "};
            case "LifeSupportSystem" -> new String[]{"Life   \n", "Support\n", "System "};
            case "Shield" -> new String[]{"       ", "Shield ", "       "};
            case "Cannon" -> new String[]{"       ", "Cannon ", "       "};
            case "DoubleCannon" -> new String[]{"Double \n", "Cannon ", "       "};
            case "Tubes" -> new String[]{"       ", " Tubes ", "       "};
            case "Engine" -> new String[]{"       ", "Engine ", "       "};
            case "DoubleEngine" -> new String[]{"Double \n", "Engine ", "       "};
            default -> new String[]{type.substring(0, 1)};
        };
    }

    private String getConnectorSymbol(Connector c) {
        return switch (c) {
            case SINGLE -> "S";
            case DOUBLE -> "D";
            case UNIVERSAL -> "U";
            default -> "E";
        };
    }

    private String getDirectionLetter(Direction d) {
        return switch (d) {
            case NORTH -> "N";
            case EAST -> "E";
            case SOUTH -> "S";
            case WEST -> "W";
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
                for (GoodsView goodsView : goodsList) {
                    GoodsColour color = goodsView.getColour();
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
            case BROWN -> "[B]";
            case PURPLE -> "[P]";
        };
    }

    private String getGoodColorSquare(GoodsColour colour) {
        return switch (colour) {
            case RED -> "\u001B[41m█\u001B[0m";
            case YELLOW -> "\u001B[43m█\u001B[0m";
            case GREEN -> "\u001B[42m█\u001B[0m";
            case BLUE -> "\u001B[44m█\u001B[0m";
        };
    }

    private String getDirectionArrow(Direction d) {
        return switch (d) {
            case NORTH -> "↑";
            case EAST -> "→";
            case SOUTH -> "↓";
            case WEST -> "←";
        };
    }

    private void DrawCurrentCard(AdventureCardView adventureCardView) {
        String type=adventureCardView.getType();
        StringBuilder goods = new StringBuilder();

        switch (type){
            case "AbandonedShip":
                System.out.println("AbandonedShip");
                System.out.println("Num astronauts " + adventureCardView.getNumAstronauts());
                System.out.println("Num credits " + adventureCardView.getNumCredits());
                System.out.println("Lost days " + adventureCardView.getLostDays());
                break;
            case "AbandonedStation":
                System.out.println("AbandonedStation");
                System.out.println("Num astronauts " + adventureCardView.getNumAstronauts());
                System.out.println("Lost days " + adventureCardView.getLostDays());
                for (GoodsView goodsView : adventureCardView.getGoodsList()) {
                    GoodsColour color = goodsView.getColour();
                    goods.append(getGoodColorSquare(color));
                    goods.append(" ");
                }
                System.out.println(goods);
                break;
            case "Epidemic":
                System.out.println("Epidemic");
                break;
            case "MeteorCard":
                System.out.println("MeteorCard");
                for (Meteor meteor : adventureCardView.getMeteorList()) {
                    System.out.println("Type: " + meteor.getType());
                    System.out.println("Direction: " + meteor.getDirection());
                }
                break;
            case "OpenSpace":
                System.out.println("OpenSpace");
                break;
            case "Pirates":
                System.out.println("Pirates");
                System.out.println("Cannon power " + adventureCardView.getCannonPower());
                System.out.println("Credits " + adventureCardView.getNumCredits());
                System.out.println("Lost days " + adventureCardView.getLostDays());
                for(CannonFire fire: adventureCardView.getCannonFireList()){
                    System.out.println("Type: " + fire.getType());
                    System.out.println("Direction: " + fire.getDirection());
                }
                break;
            case "PlanetCard":
                System.out.println("PlanetCard");
                for(PlanetView planet: adventureCardView.getPlanetList()){
                    System.out.println("Planet number "+planet.getPlanetNumber());
                    for (GoodsView goodsView : adventureCardView.getGoodsList()) {
                        GoodsColour color = goodsView.getColour();
                        goods.append(getGoodColorSquare(color));
                        goods.append(" ");
                    }
                    System.out.println(goods);
                    System.out.println("Lost days "+adventureCardView.getLostDays());
                }
                break;
            case "Slavers":
                System.out.println("Slavers");
                System.out.println("Cannon power " + adventureCardView.getCannonPower());
                System.out.println("Credits " +adventureCardView.getNumCredits());
                System.out.println("Lost days " + adventureCardView.getLostDays());
                System.out.println("Num astronauts " + adventureCardView.getNumAstronauts());
                break;
            case "Smugglers":
                System.out.println("Smugglers");
                System.out.println("Cannon power" + adventureCardView.getCannonPower());
                System.out.println("Credits" + adventureCardView.getNumCredits());
                System.out.println("Lost days" + adventureCardView.getLostDays());
                System.out.println("Num goods" + adventureCardView.getNumGoods());
                for (GoodsView goodsView : adventureCardView.getGoodsList()) {
                    GoodsColour color = goodsView.getColour();
                    goods.append(getGoodColorSquare(color));
                    goods.append(" ");
                }
                System.out.println(goods);
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
                            System.out.println("Lose days" + adventureCardView.getLostDays());
                            break;
                        case "LoseGoods":
                            System.out.println("Num goods" +adventureCardView.getNumGoods());
                            break;
                        case "cannonFire":
                            for(CannonFire fire: adventureCardView.getCannonFireList()){
                                System.out.println("Type: " + fire.getType());
                                System.out.println("Direction: " + fire.getDirection());
                            }
                            break;
                        case "LoseAstronauts":
                            System.out.println("Num astronauts" + adventureCardView.getNumAstronauts());
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
        client.registerName(input);
    }

    @Override
    public void manageNotification(NotifyClientMessage notifyClientMessage){
        System.out.println(notifyClientMessage.getMessage());
    }

    @Override
    public void onNameAccepted(){
        System.out.println("Welcome " + client.getPlayerName() + "!");
    }

    @Override
    public void onLobbyCreated(String name, int numPlayers, int shipboardLevel, int gameMode){
        playersList.add(name);
        this.nameIndex = 0;
        System.out.println("Lobby created with this parameters:\n" +
                "Max players: " + numPlayers + " Shipboard level: " + shipboardLevel + " Game mode: " + gameMode + "\n" +
                "Connected players: \n" + playersList.getFirst());
    }

    @Override
    public void onLobbyJoined(List<String> names, int numPlayers, int shipboardLevel, int gameMode){
        this.playersList = names;
        this.nameIndex = names.size() - 1;
        System.out.println("Lobby created with this settings:\n" +
                "Max players: " + numPlayers + " Shipboard level: " + shipboardLevel + " Game mode: " + gameMode + "\n" +
                "Connected players:");
        for(String player: playersList){
            if(player.equals(names.getLast())){
                System.out.println(player + " (You)");
            } else {
                System.out.println(player);
            }
        }
    }

    @Override
    public void printMessage(String message){
        System.out.println(message);
    }

    @Override
    public void onCreateLobbyAccepted(){
        // does nothing for TUI, needed for GUI
    }

    @Override
    public void onUpdatePlayerList(List<String> updatedList){
        this.playersList = updatedList;
        System.out.println("Somebody else joined!\n" +
                "Connected players:" );
        for(String player: playersList){
            if(player.equals(updatedList.get(nameIndex))){
                System.out.println(player + " (You)");
            } else {
                System.out.println(player);
            }
        }
    }
}