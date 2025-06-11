package org.example.UIPkg;

import org.example.MessagePkg.NotifyClientMessage;
import org.example.ServerPkg.ControllerPkg.LobbyState;
import org.example.ServerPkg.Model.CardPkg.CannonFire;
import org.example.ServerPkg.Model.CardPkg.Meteor;
import org.example.ServerPkg.Model.ComponentsPkg.AlienColour;
import org.example.ServerPkg.Model.ComponentsPkg.Connector;
import org.example.ServerPkg.Model.ComponentsPkg.Direction;
import org.example.ServerPkg.Model.ComponentsPkg.GoodsColour;
import org.example.ServerPkg.Model.ForView.*;
import org.example.ServerPkg.Model.Points;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TUI extends UI{

    private final BlockingQueue<GameView> gameUpdatesQueue;
    private List<String> playersList;

    public TUI(Client client) {
        super(client);
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
            if(game.getLobbyState() != LobbyState.PLAYING_CARDS){
                System.out.println("Discovered tile: ");
                DrawDiscoveredTiles(game.getComponentsDiscovered());
            }
            System.out.println("\nShipboard:");
            DrawShipboard(game.getLobbyState(), game.getPlayers(), game.getShipBoardLevel());
            System.out.println("Current Card: ");
            if (game.getCurrentCard() != null) {
                DrawCurrentCard(game.getCurrentCard());
            }
            System.out.println("Flight board: ");
            DrawFlightBoard(game.getPlayers(), game.getGameMode());
            drawCommands(game);
        } else
            System.out.println("\n" + game.getException().getMessage() + "\n");

    }

    private void DrawDiscoveredTiles(List<ComponentsView> components) {
        if (components == null || components.isEmpty()) {
            return;
        }

        final int TILES_PER_ROW = 10;
        final int TILE_HEIGHT = 9;

        for (int startIndex = 0; startIndex < components.size(); startIndex += TILES_PER_ROW) {
            int endIndex = Math.min(startIndex + TILES_PER_ROW, components.size());
            int tilesInThisRow = endIndex - startIndex;

            String[][] allTileLines = new String[tilesInThisRow][TILE_HEIGHT];

            for (int i = 0; i < tilesInThisRow; i++) {
                ComponentsView component = components.get(startIndex + i);
                allTileLines[i] = DrawComponent(component);
            }

            printFullLines(tilesInThisRow, allTileLines);

            StringBuilder indexLine = getGoodsString(tilesInThisRow, startIndex);
            System.out.println(indexLine);
            
            if (endIndex < components.size()) {
                System.out.println();
            }
        }
    }

    private void printFullLines(int tilesInThisRow, String[][] allTileLines) {
        for (int lineIndex = 0; lineIndex < 9; lineIndex++) {
            StringBuilder fullLine = new StringBuilder();
            for (int tileIndex = 0; tileIndex < tilesInThisRow; tileIndex++) {
                fullLine.append(allTileLines[tileIndex][lineIndex]);
                if (tileIndex < tilesInThisRow - 1) {
                    fullLine.append("  ");
                }
            }
            System.out.println(fullLine);
        }
    }

    private StringBuilder getGoodsString(int tilesInThisRow, int startIndex) {
        StringBuilder indexLine = new StringBuilder();
        for (int i = 0; i < tilesInThisRow; i++) {
            int tileIndex = startIndex + i;
            printIndexes(tilesInThisRow, indexLine, i, tileIndex);
        }
        return indexLine;
    }

    private void printIndexes(int tilesInThisRow, StringBuilder indexLine, int i, int tileIndex) {
        String indexStr = "[" + tileIndex + "]";
        int tileWidth = 11;
        int padding = (tileWidth - indexStr.length()) / 2;

        indexLine.append(" ".repeat(Math.max(0, padding)));
        indexLine.append(indexStr);
        indexLine.append(" ".repeat(Math.max(0, tileWidth - padding - indexStr.length())));

        if (i < tilesInThisRow - 1) {
            indexLine.append("  ");
        }
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
            case PLAYING_CARDS:
                System.out.println(game.getCurrentCard().getCommands());
                break;

            case GAME_STARTED:
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
                                
                                   select_position int -> int is the position you want to start from (Options: 0, -1, -2, -3)
                                   add_brown_alien x y -> x,y are the coordinates of the cabin where you want to add the brown alien
                                   add_purple_alien x y -> x,y are the coordinates of the cabin where you want to add the purple alien
                                   end_add_alien -> if you want to end the add alien phase
                                
                                   show_deck numDeck -> numDeck is the number of the deck you want to see
                                   end_show_deck -> if you want to end the show deck phase
                                
                                   turn_timer -> if you want to turn the timer
                                
                                   end_build_ship -> if you to end the build ship phase
                                """
                );
                break;
            case GAME_FINISHED:
                System.out.println("Type the command: exit_game -> if you want to exit the game\n\n");
                break;
        }
    }

    private void DrawFlightBoard(List<PlayerView> players, int gameMode) {
        int cols = (gameMode == 0) ? 8 : 11;
        int totalPositions = 2 * cols + 2;
        int numCell = totalPositions - 1;

        Map<Points, String> playerPositions = new HashMap<>();

        for (PlayerView player : players) {
            if (player.isShipOK() && player.isPosValid()) {
                int normalizedPosition = ((player.getPosition() % totalPositions) + totalPositions) % totalPositions;
                String playerColor = getPlayerColorSymbol(player.getRocketColour());
                playerPositions.put(getCoordinatesFromPos(normalizedPosition, gameMode), playerColor);
            }
        }

        System.out.print("┌");
        for (int col = 0; col < cols; col++) {
            System.out.print("────");
            if (col < cols - 1) System.out.print("┬");
        }
        System.out.println("┐");

        System.out.print("│");
        for (int col = 0; col < cols; col++) {
            String colour = checkPlayer(playerPositions, new Points(col, 0));
            String content = colour != null ? colour : String.format("%2d", col+1);
            System.out.printf(" %s │", content);
        }
        System.out.println();

        System.out.print("├");
        for (int col = 0; col < cols; col++) {
            System.out.print("────");
            if (col < cols - 1) {
                if (col == 0) {
                    System.out.print("┼");
                } else if(col == cols - 2) {
                    System.out.print("┼");
                } else {
                    System.out.print("┴");
                }
            }
        }
        System.out.println("┤");

        System.out.print("│");
        for (int col = 0; col < cols; col++) {
            int leftCellNum;
            int rightCellNum;
            if(gameMode == 0){
                leftCellNum = 9;
                rightCellNum = 18;
            } else {
                leftCellNum = 24;
                rightCellNum = 12;
            }
            if (col == 0) {
                String colour = checkPlayer(playerPositions, new Points(0, 1));
                String content = colour != null ? colour : String.format("%2d", leftCellNum);
                System.out.printf(" %s │", content);
            } else if (col == cols - 1) {
                String colour = checkPlayer(playerPositions, new Points(cols - 1, 1));
                String content = colour != null ? colour : String.format("%2d", rightCellNum);
                System.out.printf(" %s │", content);
            } else if (col == cols - 2) {
                System.out.print("    │");
            } else {
                System.out.print("     ");
            }
        }
        System.out.println();

        System.out.print("├");
        for (int col = 0; col < cols; col++) {
            System.out.print("────");
            if (col < cols - 1) {
                if (col == 0) {
                    System.out.print("┼");
                } else if(col == cols - 2) {
                    System.out.print("┼");
                } else {
                    System.out.print("┬");
                }
            }
        }
        System.out.println("┤");

        System.out.print("│");
        for (int col = 0; col < cols; col++) {
            String colour = checkPlayer(playerPositions, new Points(23 - numCell, 2));
            String content = colour != null ? colour : String.format("%2d", numCell);
            System.out.printf(" %s │", content);
            numCell--;
        }
        System.out.println();

        System.out.print("└");
        for (int col = 0; col < cols; col++) {
            System.out.print("────");
            if (col < cols - 1) System.out.print("┴");
        }
        System.out.println("┘");
    }

    private String checkPlayer(Map<Points, String> playersPos, Points pos) {
        for (Points pp : playersPos.keySet()) {
            if(pp.getX() == pos.getX() && pp.getY() == pos.getY())
                return playersPos.get(pp);
        }
        return null;
    }

    private String getPlayerColorSymbol(String rocketColour) {
        return switch (rocketColour.toUpperCase()) {
            case "RED" -> "\uD83D\uDFE5";
            case "BLUE" -> "\uD83D\uDFE6";
            case "GREEN" -> "\uD83D\uDFE9";
            case "YELLOW" -> "\uD83D\uDFE8";
            default -> rocketColour.substring(0, Math.min(2, rocketColour.length())).toUpperCase();
        };
    }

    private Points getCoordinatesFromPos(int pos, int gameMode){
        if(gameMode == 0){
            if(pos < 8) {
                return new Points(pos, 0);
            } else if (pos == 17) {
                return new Points(7, 1);
            } else if (pos == 9) {
                return new Points(0, 1);
            } else {
                return new Points(16 - pos, 2);
            }
        } else {
            if(pos < 11) {
                return new Points(pos, 0);
            } else if (pos == 23) {
                return new Points(0, 1);
            } else if (pos == 11) {
                return new Points(10, 1);
            } else {
                return new Points(22 - pos, 2);
            }
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

            switch (comp.getDirection()){
                case NORTH:
                    grid[0][4] = getConnectorSymbol(connectors[0]);
                    grid[5][0] = getConnectorSymbol(connectors[3]);
                    grid[5][8] = getConnectorSymbol(connectors[1]);
                    grid[6][4] = getConnectorSymbol(connectors[2]);
                    break;

                case WEST:
                    grid[0][4] = getConnectorSymbol(connectors[1]);
                    grid[5][0] = getConnectorSymbol(connectors[0]);
                    grid[5][8] = getConnectorSymbol(connectors[2]);
                    grid[6][4] = getConnectorSymbol(connectors[3]);
                    break;

                case SOUTH:
                    grid[0][4] = getConnectorSymbol(connectors[2]);
                    grid[5][0] = getConnectorSymbol(connectors[1]);
                    grid[5][8] = getConnectorSymbol(connectors[3]);
                    grid[6][4] = getConnectorSymbol(connectors[0]);
                    break;

                case EAST:
                    grid[0][4] = getConnectorSymbol(connectors[3]);
                    grid[5][0] = getConnectorSymbol(connectors[2]);
                    grid[5][8] = getConnectorSymbol(connectors[0]);
                    grid[6][4] = getConnectorSymbol(connectors[1]);
            }

            grid[0][8] = getDirectionLetter(comp.getDirection());

            for (int i=0; i<initials.length; i++){
                for(int j=0; j<initials[i].length(); j++)
                    grid[i+1][j+1] = String.valueOf(initials[i].charAt(j));
            }

            String detail = getComponentDetail(comp);
            for (int i = 0; i< Math.min(9,detail.length()); i++) {
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

    private void DrawShipboard(LobbyState gameState, List<PlayerView> players, int shipboardLevel) {
        final int ROWS = 5;
        final int COLS = 7;

        for (PlayerView player : players) {
            System.out.println("Board of " + player.getName() + ":");
            System.out.println("Player's colour: " + getPlayerColorSymbol(player.getRocketColour()));
            if(gameState != LobbyState.PLAYING_CARDS){
                System.out.println("\nCurrent tile:");
                if (player.getCurrentTile() != null) {
                    List<String> lines = List.of(DrawComponent(player.getCurrentTile()));
                    for (String line : lines) System.out.println(line);
                }
                System.out.println("\nBooked tiles:");
                if(player.getShipboardView().getBookedComponents() != null && (player.getShipboardView().getBookedComponents()[0] !=null || player.getShipboardView().getBookedComponents()[1] !=null)){
                    DrawBookedTiles(player.getShipboardView().getBookedComponents());
                }

                if (player.getDeckShowed() != null) {
                    System.out.println("\nDeck:");
                    for(AdventureCardView c: player.getDeckShowed()){
                        DrawCurrentCard(c);
                    }
                }
            } else {
                System.out.println("\nCredits:" + player.getNumCredits());
            }

            System.out.println("\nShipboard:");
            System.out.print("       ");
            for (int col = 0; col < COLS; col++) {
                System.out.printf("   Col %d     ", col + 4);
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
                        for (StringBuilder stringBuilder : line) {
                            stringBuilder.append("             ");
                        }
                    }
                }

                System.out.printf(" %d     %s\n", row + 5, line[0]);
                for (int i = 1; i < line.length; i++) {
                    System.out.print("       ");
                    System.out.println(line[i].toString());
                }
            }
        }
    }

    private void DrawBookedTiles(ComponentsView[] bookedComponents) {
        final int TILE_HEIGHT = 9;
        final int NUM_SLOTS = 2;

        String[][] allTileLines = new String[NUM_SLOTS][TILE_HEIGHT];

        for (int i = 0; i < NUM_SLOTS; i++) {
            ComponentsView component = (i < bookedComponents.length) ? bookedComponents[i] : null;
            allTileLines[i] = DrawComponent(component);
        }

        printFullLines(NUM_SLOTS, allTileLines);

        StringBuilder indexLine = new StringBuilder();
        for (int i = 0; i < NUM_SLOTS; i++) {
            printIndexes(NUM_SLOTS, indexLine, i, i);
        }
        System.out.println(indexLine);
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
            case "Special Storage" -> new String[]{"Special", "Storage", "       "};
            case "LifeSupportSystem" -> new String[]{"Life   ", "Support", "System "};
            case "Shield" -> new String[]{"       ", "Shield ", "       "};
            case "Cannon" -> new String[]{"       ", "Cannon ", "       "};
            case "DoubleCannon" -> new String[]{"Double ", "Cannon ", "       "};
            case "Tubes" -> new String[]{"       ", " Tubes ", "       "};
            case "Engine" -> new String[]{"       ", "Engine ", "       "};
            case "DoubleEngine" -> new String[]{"Double ", "Engine ", "       "};
            case "BatteryStorage" -> new String[]{"Battery", "Storage", "       "};
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
            case "Central Cabin":
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

            case "Special Storage":
            case "Storage":
                return getGoodsString(comp).toString();

            case "Shield":
                return getDirectionArrow(comp.getShieldedDirections()[0]) + getDirectionArrow(comp.getShieldedDirections()[1]);

            case "BatteryStorage":
                return String.valueOf(comp.getNumBattery());

            default:
                return "";
        }
    }

    private StringBuilder getGoodsString(ComponentsView comp) {
        StringBuilder goods = new StringBuilder();
        List<GoodsView> goodsList = comp.getGoods() != null ? Arrays.asList(comp.getGoods()) : new ArrayList<>();
        for (GoodsView goodsView : goodsList) {
            if(goodsView != null) {
                GoodsColour color = goodsView.getColour();
                goods.append(getGoodColorSquare(color));
            } else {
                goods.append("[ ]");
            }
        }
        return goods;
    }

    private String getAlienColorBlock(AlienColour color) {
        return switch (color) {
            case BROWN -> "[Brown]";
            case PURPLE -> "[Purple]";
        };
    }

    private String getGoodColorSquare(GoodsColour colour) {
        return switch (colour) {
            case RED -> "\uD83D\uDFE5";
            case BLUE -> "\uD83D\uDFE6";
            case GREEN -> "\uD83D\uDFE9";
            case YELLOW -> "\uD83D\uDFE8";
        };
    }

    private String getDirectionArrow(Direction d) {
        return switch (d) {
            case NORTH -> "N ";
            case EAST -> "E ";
            case SOUTH -> "S ";
            case WEST -> "W ";
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
                    if(goodsView != null) {
                        GoodsColour color = goodsView.getColour();
                        goods.append(getGoodColorSquare(color));
                    } else {
                        goods.append("   ");
                    }
                }
                System.out.println(goods);
                break;
            case "Epidemic":
                System.out.println("Epidemic");
                break;
            case "MeteorCard":
                System.out.println("MeteorCard");
                int i = 0;
                for (Meteor meteor : adventureCardView.getMeteorList()) {
                    System.out.println("Meteor " + i++ + ":");
                    String meteorType;
                    if(meteor.type() == 0){
                        meteorType = "Small";
                    } else {
                        meteorType = "Big";
                    }
                    System.out.println("Type: " + meteorType + ", Direction: " + meteor.direction());
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
                    System.out.println("Type: " + fire.type());
                    System.out.println("Direction: " + fire.direction());
                }
                break;
            case "PlanetCard":
                System.out.println("PlanetCard");
                for(PlanetView planet: adventureCardView.getPlanetList()){
                    System.out.println("Planet number "+ (planet.getPlanetNumber() - 1));
                    for (GoodsView goodsView : planet.getGoods()) {
                        if(goodsView != null) {
                            goods.append(getGoodColorSquare(goodsView.getColour())).append(" ");
                        } else {
                            goods.append("[ ]");
                        }
                    }
                    System.out.println(goods);
                    goods.setLength(0); // pulisce lo string builder
                    System.out.println("Lost days " + adventureCardView.getLostDays());
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
                System.out.println("Cannon power " + adventureCardView.getCannonPower());
                System.out.println("Lost days " + adventureCardView.getLostDays());
                System.out.println("Num goods " + adventureCardView.getNumGoods());
                for (GoodsView goodsView : adventureCardView.getGoodsList()) {
                    if(goodsView != null) {
                        GoodsColour color = goodsView.getColour();
                        goods.append(getGoodColorSquare(color));
                    } else {
                        goods.append("[ ]");
                    }
                }
                System.out.println(goods);
                break;
            case "Stardust":
                System.out.println("Stardust");
                break;
            case "WarZone":
                System.out.println("WarZone");
                for(int j=0; j<3; j++){
                    switch (adventureCardView.getCriteria()[j]) {
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

                    switch (adventureCardView.getPenalties()[j]) {
                        case "LoseDays":
                            System.out.println("Lose days: " + adventureCardView.getLostDays());
                            break;
                        case "LoseGoods":
                            System.out.println("Num goods: " +adventureCardView.getNumGoods());
                            break;
                        case "cannonFire":
                            int k = 0;
                            for(CannonFire fire: adventureCardView.getCannonFireList()){
                                System.out.println("CannonFire " + k++ + ":");
                                System.out.println("Type: " + fire.type() + ", Direction: " + fire.direction());
                            }
                            break;
                        case "LoseAstronauts":
                            System.out.println("Num astronauts: " + adventureCardView.getNumAstronauts());
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
        getClient().registerName(input);
    }

    @Override
    public void manageNotification(NotifyClientMessage notifyClientMessage){
        System.out.println(notifyClientMessage.getMessage());
    }

    @Override
    public void onNameAccepted(){
        System.out.println("Welcome " + getClient().getPlayerName() + "!");
    }

    @Override
    public void onLobbyCreated(String name, int numPlayers, int shipboardLevel, int gameMode){
        playersList.add(name);
        System.out.println("Lobby created with this parameters: " +
                "Max players: " + numPlayers + " Shipboard level: " + shipboardLevel + " Game mode: " + gameMode + "\n" +
                "Connected players: \n" + playersList.getFirst() + " (You)");
    }

    @Override
    public void onLobbyJoined(List<String> names, int numPlayers, int shipboardLevel, int gameMode){
        this.playersList = names;
        System.out.println("Lobby created with this settings:\n" +
                "Max players: " + numPlayers + " Shipboard level: " + shipboardLevel + " Game mode: " + gameMode + "\n" +
                "Connected players:");
        for (String s : playersList) {
            if (s.equals(getClient().getPlayerName())) {
                System.out.println(s + " (You)");
            } else {
                System.out.println(s);
            }
        }
    }

    @Override
    public void onUpdatePlayerList(List<String> updatedList){
        this.playersList = updatedList;
        System.out.println("Somebody else joined!\n" +
                "Connected players:" );
        for (String s : playersList) {
            if (s.equals(getClient().getPlayerName())) {
                System.out.println(s + " (You)");
            } else {
                System.out.println(s);
            }
        }
    }
}