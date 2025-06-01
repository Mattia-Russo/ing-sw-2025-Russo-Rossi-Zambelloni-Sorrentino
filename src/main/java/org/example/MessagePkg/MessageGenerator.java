package org.example.MessagePkg;

import org.example.ServerPkg.Model.Exceptions.CommandNotFoundException;
import org.example.ServerPkg.Model.Points;

import java.util.*;
import java.util.function.Function;

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
        messageMap.put("creating_lobby", this::createCreatingLobbyMessage);
        messageMap.put("joined_lobby", this::createLobbyJoinedMessage);
        messageMap.put("lobby_created", this::createLobbyCreatedMessage);
        messageMap.put("accept_create_lobby", this::createAcceptCreateLobbyMessage);
        messageMap.put("update_names", this::createUpdatePlayersListMessage);
        messageMap.put("choose_wrecked", this::createChooseWreckedMessage);
        messageMap.put("end_wrecked", this::createEndWreckedMessage);
        messageMap.put("game_started", this::createGameStartedMessage);
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
        int index = Integer.parseInt(args.getFirst());
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
        int x = Integer.parseInt(args.getFirst());
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
        int numDeck = Integer.parseInt(args.getFirst());
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
        return new SetPlayerNameMessage(args.getFirst());
    }

    private Message createAcceptRewardMessage(List<String> args) {
        boolean bool = Boolean.parseBoolean(args.getFirst());
        return new AcceptRewardMessage(bool);
    }

    private ArrayList<Points> getPointsList(List<String> args){
        ArrayList<Points> pointsList = new ArrayList<>();
        for (int i = 0; i < args.size(); i += 2) {
            int x = Integer.parseInt(args.get(i));
            int y = Integer.parseInt(args.get(i + 1));
            pointsList.add(new Points(x, y));
        }
        return pointsList;
    }

    private Message createActivateCannonsMessage(List<String> args) {
        ArrayList<Points> cannons = new ArrayList<>(getPointsList(args));
        return new ActivateCannonsMessage(cannons);
    }

    private Message createActivateEnginesMessage(List<String> args) {
        ArrayList<Points> engines = new ArrayList<>(getPointsList(args));
        return new ActivateEnginesMessage(engines);
    }

    private Message createActivateShieldsMessage(List<String> args) {
        ArrayList<Points> shields = new ArrayList<>(getPointsList(args));
        return new ActivateShieldsMessage(shields);
    }

    private Message createUseBatteriesMessage(List<String> args) {
        ArrayList<Points> batteries = new ArrayList<>(getPointsList(args));
        return new UseBatteriesMessage(batteries);
    }

    private Message createAddGoodMessage(List<String> args) {
        int x = Integer.parseInt(args.get(0));
        int y = Integer.parseInt(args.get(1));
        int numGood = Integer.parseInt(args.get(2));
        Points point = new Points(x, y);
        return new AddGoodMessage(point, numGood);
    }

    private Message createCreateLobbyMessage(List<String> args) {
        int numPlayers = Integer.parseInt(args.get(0));
        int shipboardLevel = Integer.parseInt(args.get(1));
        int gameMode = Integer.parseInt(args.get(2));
        return new CreateLobbyMessage(numPlayers, shipboardLevel, gameMode);
    }

    private Message createEndActivateCannonsMessage(List<String> args) {
        return new EndActivateCannonsMessage();
    }

    private Message createEndActivateEnginesMessage(List<String> args) {
        return new EndActivateEnginesMessage();
    }

    private Message createEndChangeGoodsStateMessage(List<String> args) {
        return new EndChangeGoodsState();
    }

    private Message createEndActivateShieldsMessage(List<String> args) {
        return new EndActivateShieldsMessage();
    }

    private Message createEndRemoveBestGoodsMessage(List<String> args) {
        return new EndRemoveBestGoodsMessage();
    }

    private Message createEndRemoveAstronautsMessage(List<String> args) {
        return new EndRemoveAstronautsMessage();
    }

    private Message createExitGameMessage(List<String> args) {
        return new ExitGameMessage();
    }

    private Message createJoinLobbyMessage(List<String> args) {
        return new JoinLobbyMessage();
    }

    private Message createLandOnAbandonMessage(List<String> args) {
        boolean bool = Boolean.parseBoolean(args.getFirst());
        return new LandOnAbandonMessage(bool);
    }

    private Message createLandOnPlanetMessage(List<String> args) {
        boolean bool = Boolean.parseBoolean(args.get(0));
        int numPlanet = Integer.parseInt(args.get(1));
        return new LandOnPlanetMessage(bool, numPlanet);
    }

    private Message createPingMessage(List<String> args) {
        return new PingMessage();
    }

    private Message createPongMessage(List<String> args) {
        return new PongMessage();
    }

    private Message createRemoveGoodMessage(List<String> args) {
        int x = Integer.parseInt(args.get(0));
        int y = Integer.parseInt(args.get(1));
        int numGood = Integer.parseInt(args.get(2));
        Points point = new Points(x, y);
        return new RemoveGoodMessage(point, numGood);
    }

    private Message createRemoveAstronautsMessage(List<String> args) {
        int x = Integer.parseInt(args.get(0));
        int y = Integer.parseInt(args.get(1));
        Points point = new Points(x, y);
        return new RemoveAstronautsMessage(point);
    }

    private Message createRemoveBestGoodMessage(List<String> args) {
        int x = Integer.parseInt(args.get(0));
        int y = Integer.parseInt(args.get(1));
        int numGood = Integer.parseInt(args.get(2));
        Points point = new Points(x, y);
        return new RemoveBestGoodMessage(point, numGood);
    }

    private Message createRemoveBatteriesMessage(List<String> args) {
        int x = Integer.parseInt(args.get(0));
        int y = Integer.parseInt(args.get(1));
        Points point = new Points(x, y);
        return new RemoveBatteriesMessage(point);
    }

    private Message createStartGameMessage(List<String> args) {
        return new StartGameMessage();
    }

    private Message createCreatingLobbyMessage(List<String> args) {
        return new SetUpLobbyMessage();
    }

    private Message createLobbyJoinedMessage(List<String> args){
        int numPlayers = Integer.parseInt(args.get(0));
        int shipboardLevel = Integer.parseInt(args.get(1));
        int gameMode = Integer.parseInt(args.get(2));
        List<String> names = new ArrayList<>(args.subList(3, args.size()));
        return new LobbyJoinedMessage(numPlayers, shipboardLevel, gameMode, names);
    }

    private Message createLobbyCreatedMessage(List<String> args){
        int numPlayers = Integer.parseInt(args.get(0));
        int shipboardLevel = Integer.parseInt(args.get(1));
        int gameMode = Integer.parseInt(args.get(2));
        return new LobbyCreatedMessage(numPlayers, shipboardLevel, gameMode);
    }

    private Message createAcceptCreateLobbyMessage(List<String> args){
        return new AcceptCreateLobbyMessage();
    }

    private Message createChooseWreckedMessage(List<String> args){
        int x = Integer.parseInt(args.get(0));
        int y = Integer.parseInt(args.get(1));
        Points point = new Points(x, y);
        return new ChooseWreckedMessage(point);
    }

    private Message createEndWreckedMessage(List<String> args){
        return new EndWreckedMessage();
    }

    private Message createUpdatePlayersListMessage(List<String> args){
        return new UpdatePlayersListMessage(args);
    }

    private Message createGameStartedMessage(List<String> args){
        return new GameStartedMessage();
    }
}