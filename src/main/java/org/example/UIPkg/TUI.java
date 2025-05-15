package org.example.UIPkg;

import org.example.ServerPkg.ControllerPkg.PlayerStates.ActivateCannonsState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.ActivateEnginesState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.WaitingState;
import org.example.ServerPkg.Model.CardPkg.CannonFire;
import org.example.ServerPkg.Model.CardPkg.Meteor;
import org.example.ServerPkg.Model.CardPkg.Planet;
import org.example.ServerPkg.Model.ComponentsPkg.AlienColour;
import org.example.ServerPkg.Model.ComponentsPkg.Connector;
import org.example.ServerPkg.Model.ComponentsPkg.Direction;
import org.example.ServerPkg.Model.ComponentsPkg.GoodsColour;
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
        for (PlayerView player : players) {
            System.out.println("Shipboard of " + player.getName() + ":");
            if(player.getDeckShowed()==null) {
                System.out.println(player.getCurrentTile());
            }
            ComponentsView[][] matrix = player.getShipboardView().getComponentsView();
            for (int i = 0; i < 5; i++) {
                StringBuilder top = new StringBuilder();
                StringBuilder mid = new StringBuilder();
                StringBuilder bot = new StringBuilder();
                for (int j = 0; j < 7; j++) {
                    ComponentsView comp = matrix[i][j];
                    if (comp != null) {
                        Connector[] connectors = comp.getConnectors();
                        String content = renderHorizontalConnector(connectors[3]) +DrawComponent(comp)+renderHorizontalConnector(connectors[1]);

                        int contentLength = Math.min(stripAnsi(content).length(),24);
                        if (contentLength >24){
                            contentLength =24;
                        }
                        mid.append(padCenter(content, 24));
                        String topConn = padCenter(renderVerticalConnector(connectors[0]), contentLength);
                        String botConn = padCenter(renderVerticalConnector(connectors[2]), contentLength);
                        top.append(padCenter(topConn, 24));
                        bot.append(padCenter(botConn, 24));
                    } else {
                        top.append(" ".repeat(24));
                        mid.append(" ".repeat(24));
                        bot.append(" ".repeat(24));
                    }


                }
                System.out.println(top);
                System.out.println(mid);
                System.out.println(bot);
            }
        }
    }

    private String renderVerticalConnector(Connector c) {
        if (c == Connector.UNIVERSAL) return "│││";
        if (c == Connector.DOUBLE) return "│ │";
        if (c == Connector.SINGLE) return " │ ";
        return "   ";
    }

    private String renderHorizontalConnector(Connector c) {
        if (c == Connector.UNIVERSAL) return "───";
        if (c == Connector.DOUBLE) return "─ ─";
        if (c == Connector.SINGLE) return " ─ ";
        return "   ";
    }
    private String stripAnsi(String s){
        return s.replaceAll("\u001B\\[[;\\d]*m", "");
    }

    private String padCenter(String s, int width) {
        int len= stripAnsi(s).length();
        int padding = Math.max(0,width-len);
        int left = padding / 2;
        int right = padding - left;
        return " ".repeat(left) + s + " ".repeat(right);
    }

    private String getColourSymbol(String colour) {
        switch (colour.toUpperCase()) {
            case "RED":
                return "\u001B[41m  \u001B[0m";

            case "BLUE":
                return "\u001B[44m  \u001B[0m";

            case "GREEN":
                return "\u001B[42m  \u001B[0m";

            case "YELLOW":
                return "\u001B[43m  \u001B[0m";

            default: return "";
        }
    }
    private String DrawComponent(ComponentsView comp) {
        if (comp==null) return "";
        String arrow = getDirectionArrow(comp.getDirection());
        String type = comp.getType();
        String content = "";
        switch (type) {
            case "Cannon":
                content = "CANNON";
                break;
            case "DoubleCannon":
                content = "DBCANNON";
                break;
            case "Shield":
                StringBuilder shieldDir = new StringBuilder("SHIELD(");
                for (Direction dir : comp.getShieldedDirections()) {
                    shieldDir.append(getDirectionArrow(dir));
                }
                shieldDir.append(")");
                content = shieldDir.toString();
                break;
            case "Engine":
                content = "ENGINE";
                break;
            case "DoubleEngine":
                content = "DBENGINE";
                break;
            case "BatteryStorage":
                content = "BATTERYST";
                break;
            case "Battery":
                content = "BATSTOR";
                break;
            case "Tubes":
                content = "TUBES";
                break;
            case "Storage":

                StringBuilder goods = new StringBuilder("STORAGE ");
                for (GoodsView g : comp.getGoods()) {
                    goods.append(getGoodColorSquare(g.getColour())).append(" ");
                }
                content = goods.toString().trim();
                break;
            case "Cabin":

                if (comp.getAlienColour() != null) {
                    content = "CABIN"+ getAlienColorBlock(comp.getAlienColour());
                } else {
                    content = "CABIN"+ comp.getNumAstronauts();
                }
                break;
            case "LifeSupportSystem":

                String alien = (comp.getAlienColour() != null)
                        ? getAlienColorBlock(comp.getAlienColour())
                        : "";
                content = "LIFES" + alien;
                break;
            default:
                content = type.toUpperCase();
        }
        String result = arrow +content;
        if (stripAnsi(result).length() > 18) {
            int visualLen = 0;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < result.length(); i++) {
                char c = result.charAt(i);
                sb.append(c);
                if (c == '\u001B') {
                    while (i < result.length() && result.charAt(i) != 'm') {
                        sb.append(result.charAt(++i));
                    }
                    sb.append('m');
                } else {
                    visualLen++;
                    if (visualLen >= 18) break;
                }
            }
            result = sb.toString();
        }

        return result;
    }
    private String getGoodColorSquare(GoodsColour colour) {
        return switch (colour) {
            case RED:
                yield "\u001B[41m█\u001B[0m";

            case BLUE:
                yield "\u001B[44m█\u001B[0m";

            case GREEN:
                yield "\u001B[42m█\u001B[0m";

            case YELLOW:
                yield "\u001B[43m█\u001B[0m";

            default:
                yield " ";
        };
    }

    private String getAlienColorBlock(AlienColour color) {
        return switch (color) {
            case BROWN:
                yield "\u001B[48;5;94m█\u001B[0m";

            case PURPLE:
                yield "\u001B[45m█\u001B[0m";

        };
    }
    private String getDirectionArrow(Direction d) {
        return switch (d) {
            case NORTH:
                yield "↑";

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
}