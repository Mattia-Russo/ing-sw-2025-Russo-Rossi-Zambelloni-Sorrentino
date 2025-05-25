package org.example.MessagePkg;

import org.example.ServerPkg.Model.Exceptions.CommandNotFoundException;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;
import org.example.UIPkg.GameUpdater;

import java.util.*;
import java.util.function.Function;

// il client non viene notificita in caso di errore sul server: creare un messaggio di tipo error message con una stringa che indica l'errore
public class MessageGenerator {
    private final Map<String, Function<List<String>, Message>> messageMap = new HashMap<>();

    public MessageGenerator() {
        messageMap.put("accept_reward", this::createAcceptRewardMessage);
        messageMap.put("activate_cannons", this::createActivateCannonsMessage);
        messageMap.put("activate_engines", this::createActivateEnginesMessage);
        messageMap.put("activate_shields", this::createActivateShieldsMessage);
        messageMap.put("add_good", this::createAddGoodMessage);
        messageMap.put("create_lobby", this::createCreateLobbyMessage);
        messageMap.put("end_activate_cannons", this::createEndActivateCannonsMessage);
        messageMap.put("end_activate_engines", this::createEndActivateEnginesMessage);
        messageMap.put("end_change_goods_state", this::createEndChangeGoodsStateMessage);
        messageMap.put("end_activate_shields", this::createEndActivateShieldsMessage);
        messageMap.put("end_remove_best_goods", this::createEndRemoveBestGoodsMessage);
        messageMap.put("end_remove_astronauts", this::createEndRemoveAstronautsMessage);
        messageMap.put("exit_game", this::createExitGameMessage);
        messageMap.put("join_lobby", this::createJoinLobbyMessage);
        messageMap.put("land_on_abandon", this::createLandOnAbandonMessage);
        messageMap.put("land_on_planet", this::createLandOnPlanetMessage);
        messageMap.put("ping", this::createPingMessage);
        messageMap.put("pong", this::createPongMessage);
        messageMap.put("remove_good", this::createRemoveGoodMessage);
        messageMap.put("remove_astronauts", this::createRemoveAstronautsMessage);
        messageMap.put("remove_best_good", this::createRemoveBestGoodMessage);
        messageMap.put("remove_batteries", this::createRemoveBatteriesMessage);
        messageMap.put("start_game", this::createStartGameMessage);
        messageMap.put("use_batteries", this::createUseBatteriesMessage);
        messageMap.put("set_name", this::createSetPlayerNameMessage);
        messageMap.put("end_add_alien", this::createEndAddAlienMessage);
        messageMap.put("remove_tile", this::createRemoveTileMessage);
        messageMap.put("show_deck", this::createShowDeckMessage);
        messageMap.put("end_show_deck", this::createEndShowDeckMessage);
        messageMap.put("left_rotate", this::createLeftRotateMessage);
        messageMap.put("right_rotate", this::createRightRotateMessage);
        messageMap.put("add_brown_alien", this::createAddBrownAlienMessage);
        messageMap.put("add_purple_alien", this::createAddPurpleAlienMessage);
        messageMap.put("turn_timer", this::createturnTimerMessage);
        messageMap.put("pick_tile", this::createPickComponentTileMessage);
        messageMap.put("pick_discovered_tile", this::createPickDiscoveredComponentMessage);
        messageMap.put("discard_tile", this::createDiscardComponentMessage);
        messageMap.put("place_tile", this::createPlaceTileMessage);
        messageMap.put("end_build_ship", this::createEndBuildShipMessage);
        messageMap.put("book_tile", this::createBookComponentMessage);
        messageMap.put("notify", this::createNotifyClientMessage);
        messageMap.put("end_fix_ship", this::createEndFixShipMessage);
        messageMap.put("pick_booked_tile", this::createPickBookedTileMessage);
    }

    public Message generate(String command, List<String> args) {
        try {
            Function<List<String>, Message> generator = messageMap.get(command.toLowerCase());
            if (generator == null) {
                throw new CommandNotFoundException("Command not valid: " + command +
                        "\nPlease write one of the above commands."
                );
            }
            return generator.apply(args);
        } catch (CommandNotFoundException e) {
            System.err.println("ERROR: " + e.getMessage());
            return null;
        }
    }

    private Message createPickBookedTileMessage(List<String> args) {
        int index = Integer.parseInt(args.get(0));
        return new PickBookedTileMessage(index);
    }

    private Message createEndFixShipMessage(List<String> args) {
        return new EndFixShipMessage();
    }

    private Message createNotifyClientMessage(List<String> args) {
        return new NotifyClientMessage(args.getFirst());
    }

    private Message createBookComponentMessage(List<String> args) {
        return new BookComponentMessage();
    }

    private Message createEndBuildShipMessage(List<String> args) {
        return new EndBuildShipMessage();
    }


    private Message createPlaceTileMessage(List<String> args) {
        int x = Integer.parseInt(args.get(0));
        int y = Integer.parseInt(args.get(1));
        Points point = new Points(x, y);
        return new PlaceTileMessage(point);
    }

    private Message createDiscardComponentMessage(List<String> args) {
        return new DiscardComponentMessage();
    }


    private Message createPickDiscoveredComponentMessage(List<String> args){
        int x = Integer.parseInt(args.get(0));
        return new PickDiscoveredComponentMessage(x);
    }

    private Message createPickComponentTileMessage(List<String> args){
        return new PickComponentTileMessage();
    }

    private Message createturnTimerMessage(List<String> args){
        return new TurnTimerMessage();
    }

    private Message createAddPurpleAlienMessage(List<String> args){
        int x = Integer.parseInt(args.get(0));
        int y = Integer.parseInt(args.get(1));
        Points point = new Points(x, y);
        return new AddPurpleAlienMessage(point);
    }

    private Message createAddBrownAlienMessage(List<String> args){
        int x = Integer.parseInt(args.get(0));
        int y = Integer.parseInt(args.get(1));
        Points point = new Points(x, y);
        return new AddBrownAlienMessage(point);
    }

    private Message createRightRotateMessage(List<String> args){
        return new RightRotateMessage();
    }

    private Message createLeftRotateMessage(List<String> args){
        return new LeftRotateMessage();
    }

    private Message createEndShowDeckMessage(List<String> args){
        return new EndShowDeckmessage();
    }

    private Message createShowDeckMessage(List<String> args){
        int numDeck = Integer.parseInt(args.get(0));
        return new ShowDeckMessage(numDeck);
    }

    private Message createRemoveTileMessage(List<String> args){
        int x = Integer.parseInt(args.get(0));
        int y = Integer.parseInt(args.get(1));
        Points point = new Points(x, y);
        return new RemoveTileMessage(point);
    }

    private Message createEndAddAlienMessage(List<String> args){
        return new EndAddAlienMessage();
    }

    private Message createSetPlayerNameMessage(List<String> args) {
        return new SetPlayerNameMessage(args.get(0));
    }

    private Message createAcceptRewardMessage(List<String> args) {
        boolean bool = Boolean.parseBoolean(args.get(0));
        return new AcceptRewardMessage(bool);
    }

    public Message createActivateCannonsMessage(List<String> args) {
        ArrayList<Points> cannons = new ArrayList<>();
        for (int i = 0; i < args.size(); i += 2) {
            int x = Integer.parseInt(args.get(i));
            int y = Integer.parseInt(args.get(i + 1));
            cannons.add(new Points(x, y));
        }
        return new ActivateCannonsMessage(cannons);
    }

    public Message createActivateEnginesMessage(List<String> args) {
        ArrayList<Points> engines = new ArrayList<>();
        for (int i = 0; i < args.size(); i += 2) {
            int x = Integer.parseInt(args.get(i));
            int y = Integer.parseInt(args.get(i + 1));
            engines.add(new Points(x, y));
        }
        return new ActivateEnginesMessage(engines);
    }

    public Message createActivateShieldsMessage(List<String> args) {
        ArrayList<Points> shields = new ArrayList<>();
        for (int i = 0; i < args.size(); i += 2) {
            int x = Integer.parseInt(args.get(i));
            int y = Integer.parseInt(args.get(i + 1));
            shields.add(new Points(x, y));
        }
        return new ActivateShieldsMessage(shields);
    }

    public Message createAddGoodMessage(List<String> args) {
        int x = Integer.parseInt(args.get(0));
        int y = Integer.parseInt(args.get(1));
        int numGood = Integer.parseInt(args.get(2));
        Points point = new Points(x, y);
        return new AddGoodMessage(point, numGood);
    }

    public Message createCreateLobbyMessage(List<String> args) {
        int numPlayers = Integer.parseInt(args.get(0));
        int shipboardLevel = Integer.parseInt(args.get(1));
        int gameMode = Integer.parseInt(args.get(2));
        return new CreateLobbyMessage(numPlayers, shipboardLevel, gameMode);
    }

    public Message createEndActivateCannonsMessage(List<String> args) {
        return new EndActivateCannonsMessage();
    }

    public Message createEndActivateEnginesMessage(List<String> args) {
        return new EndActivateEnginesMessage();
    }

    public Message createEndChangeGoodsStateMessage(List<String> args) {
        return new EndChangeGoodsState();
    }

    public Message createEndActivateShieldsMessage(List<String> args) {
        return new EndActivateShieldsMessage();
    }

    public Message createEndRemoveBestGoodsMessage(List<String> args) {
        return new EndRemoveBestGoodsMessage();
    }

    public Message createEndRemoveAstronautsMessage(List<String> args) {
        return new EndRemoveAstronautsMessage();
    }

    public Message createExitGameMessage(List<String> args) {
        return new ExitGameMessage();
    }

    public Message createJoinLobbyMessage(List<String> args) {
        return new JoinLobbyMessage();
    }

    public Message createLandOnAbandonMessage(List<String> args) {
        boolean bool = Boolean.parseBoolean(args.get(0));
        return new LandOnAbandonMessage(bool);
    }

    public Message createLandOnPlanetMessage(List<String> args) {
        boolean bool = Boolean.parseBoolean(args.get(0));
        int numPlanet = Integer.parseInt(args.get(1));
        return new LandOnPlanetMessage(bool, numPlanet);
    }

    public Message createPingMessage(List<String> args) {
        return new PingMessage();
    }

    public Message createPongMessage(List<String> args) {
        return new PongMessage();
    }

    public Message createRemoveGoodMessage(List<String> args) {
        int x = Integer.parseInt(args.get(0));
        int y = Integer.parseInt(args.get(1));
        int numGood = Integer.parseInt(args.get(2));
        Points point = new Points(x, y);
        return new RemoveGoodMessage(point, numGood);
    }
    public Message createRemoveAstronautsMessage(List<String> args) {
        int x = Integer.parseInt(args.get(0));
        int y = Integer.parseInt(args.get(1));
        Points point = new Points(x, y);
        return new RemoveAstronautsMessage(point);
    }

    public Message createRemoveBestGoodMessage(List<String> args) {
        int x = Integer.parseInt(args.get(0));
        int y = Integer.parseInt(args.get(1));
        int numGood = Integer.parseInt(args.get(2));
        Points point = new Points(x, y);
        return new RemoveBestGoodMessage(point, numGood);
    }

    public Message createRemoveBatteriesMessage(List<String> args) {
        int x = Integer.parseInt(args.get(0));
        int y = Integer.parseInt(args.get(1));
        Points point = new Points(x, y);
        return new RemoveBatteriesMessage(point);
    }

    public Message createStartGameMessage(List<String> args) {
        return new StartGameMessage();
    }

    public Message createUseBatteriesMessage(List<String> args) {
        ArrayList<Points> batteries = new ArrayList<>();
        for (int i = 0; i < args.size(); i += 2) {
            int x = Integer.parseInt(args.get(i));
            int y = Integer.parseInt(args.get(i + 1));
            batteries.add(new Points(x, y));
        }
        return new UseBatteriesMessage(batteries);
    }


}