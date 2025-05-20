package org.example.UIPkg;

import org.example.ServerPkg.Model.CardPkg.CannonFire;
import org.example.ServerPkg.Model.CardPkg.Meteor;
import org.example.ServerPkg.Model.ComponentsPkg.AlienColour;
import org.example.ServerPkg.Model.ComponentsPkg.Connector;
import org.example.ServerPkg.Model.ComponentsPkg.Direction;
import org.example.ServerPkg.Model.ComponentsPkg.GoodsColour;
import org.example.ServerPkg.Model.ForView.*;

import java.util.ArrayList;
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
                List<String> lines = DrawComponent(c);
                for (String line : lines) System.out.println(line);
                System.out.println("[" + i + "]");
                i++;
            }
            System.out.println("\nCurrent tile:");
            for (PlayerView player : game.getPlayers()) {
                if (player.getDeckShowed() == null && player.getCurrentTile() != null) {
                    List<String> current = DrawComponent(player.getCurrentTile());
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
        }else
            System.out.println(game.getException().getMessage());
        drawCommands(game);
    }

    public void drawCommands(GameView game) {
        switch (game.getLobbyState()){
            //mancano set_name e set_position che sembra che non dobbiamo inserire
            case GAME_CREATION:
                System.out.println(
                        """
                                Type one of the following command to do something:
                                   join_lobby -> if you want to join an existing lobby
                                   start_game -> if you want to start the game
                                
                                """);
                break;
            case GAME_READY:
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

    private void DrawShipboard(List<PlayerView> players) {
        final int ROWS = 5;
        final int COLS = 7;
        final int RECT_WIDTH = 24;

        for (PlayerView player : players) {
            System.out.println("Shipboard of " + player.getName() + ":");
            if (player.getDeckShowed() == null) {
                List<String> current = DrawComponent(player.getCurrentTile());
                for (String line : current) System.out.println(line);
            }
            for (int col = 0; col < COLS; col++) {
                System.out.print("    ");
                String label = padCenter("Col " + col, RECT_WIDTH);
                System.out.print(label);
            }
            System.out.println();

            ComponentsView[][] matrix = player.getShipboardView().getComponentsView();

            for (int row =0; row < ROWS;row++) {
                System.out.print("    ");
                for (int col = 0; col < COLS; col++) {
                    ComponentsView comp = matrix[row][col];
                    if (comp != null) {
                        Connector north = comp.getConnectors()[0];
                        String northConn = padCenter(renderVerticalConnector(north),RECT_WIDTH);
                        System.out.print(northConn);
                    }
                }
                System.out.println();
                System.out.print("    ");
                for (int col = 0; col < COLS; col++) {
                    System.out.print("┌" + "─".repeat(RECT_WIDTH - 6) + "┐");
                }
                System.out.println();
                System.out.printf("%2d  ", row);
                for (int col = 0; col < COLS; col++) {
                    ComponentsView comp = matrix[row][col];
                    String dir = " ".repeat(RECT_WIDTH - 6);
                    if (comp != null) {
                        String arrow = getDirectionArrow(comp.getDirection());
                        dir = insertHorizontalConnector(comp.getConnectors()[3], comp.getConnectors()[1], arrow, RECT_WIDTH);
                    }
                    System.out.print("│" + dir + "│");
                }
                System.out.println();
                System.out.print("    ");
                for (int col = 0; col < COLS; col++) {
                    ComponentsView comp = matrix[row][col];
                    String name = " ".repeat(RECT_WIDTH - 6);
                    if (comp != null) {
                        String n = comp.getType();
                        name = insertHorizontalConnector(comp.getConnectors()[3], comp.getConnectors()[1], n, RECT_WIDTH);
                    }
                    System.out.print("│" + name + "│");
                }
                System.out.println();
                System.out.print("    ");
                for (int col = 0; col < COLS; col++) {
                    ComponentsView comp = matrix[row][col];
                    String detail = " ".repeat(RECT_WIDTH - 6);
                    if (comp != null) {
                        String d = getComponentDetail(comp);
                        detail = insertHorizontalConnector(comp.getConnectors()[3], comp.getConnectors()[1], d, RECT_WIDTH);
                    }
                    System.out.print("│"+detail+"│");
                }
                System.out.println();
                System.out.print("    ");
                for (int col = 0; col < COLS; col++) {
                    System.out.print("└" + "─".repeat(RECT_WIDTH - 6) + "┘");
                }
                System.out.println();
            }
            System.out.print("    ");
            for (int col = 0; col < COLS; col++) {
                ComponentsView comp = matrix[ROWS - 1][col];
                if (comp != null) {
                    Connector south = comp.getConnectors()[0];
                    String southConn = padCenter(renderVerticalConnector(south),RECT_WIDTH);
                    System.out.print(southConn);
                }
            }
            System.out.println();
        }
    }

    private String renderVerticalConnector(Connector c) {
        return switch (c) {
            case UNIVERSAL -> "│││";
            case DOUBLE -> "│ │";
            case SINGLE -> " │ ";
            default -> "   ";
        };
    }

    private String insertHorizontalConnector(Connector west, Connector east, String text, int width) {
        String left = " ", right = " ";
        if (west == Connector.SINGLE || west == Connector.DOUBLE || west == Connector.UNIVERSAL) left = "─";
        if (east == Connector.SINGLE || east == Connector.DOUBLE || east == Connector.UNIVERSAL) right = "─";
        text = truncateAnsi(text, width - 4);
        return left + " " + padRight(text, width - 4) + " " + right;
    }

    private String padRight(String s, int width) {
        return s + " ".repeat(Math.max(0, width - stripAnsi(s).length()));
    }

    private String padCenter(String s, int width) {
        int len = stripAnsi(s).length();
        int pad = Math.max(0, width - len);
        return " ".repeat(pad / 2) + s + " ".repeat(pad - pad / 2);
    }

    private String stripAnsi(String s) {
        return s.replaceAll("\u001B\\[[;\\d]*m", "");
    }

    private String truncateAnsi(String s, int maxLength) {
        return stripAnsi(s).length() <= maxLength ? s : s.substring(0, maxLength);
    }

    private List<String> DrawComponent(ComponentsView comp) {
        final int width = 24;
        List<String> lines = new ArrayList<>();

        String direction = getDirectionArrow(comp.getDirection());
        String name = comp.getType();
        String detail = getComponentDetail(comp);
        Connector[] conns = comp.getConnectors();

        // Connettori laterali
        String[] left = getLateralLineContent(conns[3]);   // Ovest
        String[] right = getLateralLineContent(conns[1]);  // Est

        // Righe interne
        String line1 = left[0] + padRight(direction, width - 2) + right[0];
        String line2 = left[1] + padRight(name, width - 2) + right[1];
        String line3 = left[2] + padRight(detail, width - 2) + right[2];

        // Costruzione
        lines.add(padCenter(renderVerticalConnector(conns[0]), width)); // sopra
        lines.add("┌" + "─".repeat(width - 2) + "┐");
        lines.add(line1);
        lines.add(line2);
        lines.add(line3);
        lines.add("└" + "─".repeat(width - 2) + "┘");
        lines.add(padCenter(renderVerticalConnector(conns[2]), width)); // sotto

        return lines;
    }

    private String[] getLateralLineContent(Connector c) {
        String[] lines = {" ", " ", " "}; // [0]=dir, [1]=nome, [2]=special

        switch (c) {
            case SINGLE :
                lines[1] = "─";
                break;

            case DOUBLE:
                lines[0] = "─";
                lines[2] = "─";
                break;

            case UNIVERSAL:
                lines[0] = "─";
                lines[1] = "─";
                lines[2] = "─";
        }
        return lines;
    }

    private String getComponentDetail(ComponentsView comp) {
        switch (comp.getType()) {
            case "Cabin":
                if (comp.getAlienColour() != null)
                    return getAlienColorBlock(comp.getAlienColour());
                else
                    return String.valueOf(comp.getNumAstronauts());
            case "LifeSupportSystem":
                return comp.getAlienColour() != null ? getAlienColorBlock(comp.getAlienColour()) : "";
            case "Storage":
                StringBuilder sb = new StringBuilder();
                for (GoodsView g : comp.getGoods()) {
                    sb.append(getGoodColorSquare(g.getColour()));
                }
                return sb.toString();
            case "Shield":
                StringBuilder s = new StringBuilder();
                for (Direction d : comp.getShieldedDirections()) {
                    s.append(getDirectionArrow(d));
                }
                return s.toString();
            default:
                return "";
        }
    }

    private String getSideConnector(Connector c, int rowIndex) {
        return switch (c) {
            case UNIVERSAL:
                yield "─";
            case DOUBLE:
                if(rowIndex == 0 || rowIndex == 2){
                    yield "─";
                }else{
                    yield " ";
                }
            case SINGLE :
                if(rowIndex == 1){
                    yield "─";
                }else{
                    yield " ";
                }
            default:
                yield " ";
        };
    }

    private String padLeftRight(String content, String right, int width) {
        int visibleLen = stripAnsi(content).length();
        int pad = Math.max(0, width - visibleLen - stripAnsi(right).length());
        return content + " ".repeat(pad) + right;
    }

    private String getAlienColorBlock(AlienColour color) {
        return switch (color) {
            case BROWN:
                yield "\u001B[48;5;94m█\u001B[0m";
            case PURPLE:
                yield "\u001B[45m█\u001B[0m";
        };
    }

    private String getGoodColorSquare(GoodsColour colour) {
        return switch (colour) {
            case RED:
                yield "\u001B[41m█\u001B[0m";
            case BLUE:
                yield "\u001B[44m█\u001B[0m";
            case GREEN:
                yield"\u001B[42m█\u001B[0m";
            case YELLOW:
                yield "\u001B[43m█\u001B[0m";
        };
    }

    private String getDirectionArrow(Direction d) {
        return switch (d) {
            case NORTH:
                yield "↑";
            case EAST:
                yield"→";
            case SOUTH:
                yield "↓";
            case WEST:
                yield"←";
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
}