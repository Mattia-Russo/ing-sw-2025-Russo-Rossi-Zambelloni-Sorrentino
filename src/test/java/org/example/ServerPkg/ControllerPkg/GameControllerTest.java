package org.example.ServerPkg.ControllerPkg;

import junit.framework.TestCase;
import org.example.MessagePkg.Message;
import org.example.ServerPkg.ConnectionsPkg.Handler;
import org.example.ServerPkg.ConnectionsPkg.Server;
import org.example.ServerPkg.ControllerPkg.PlayerStates.BuildShipState;
import org.example.ServerPkg.Model.Exceptions.InvalidAddPlayerException;
import org.example.ServerPkg.Model.Exceptions.InvalidGameCreationException;
import org.example.ServerPkg.Model.Exceptions.InvalidLobbyStateException;
import org.example.ServerPkg.Model.Exceptions.InvalidUserNameException;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.UIPkg.GameUpdater;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameControllerTest extends TestCase{

    public void testGetGame() throws RemoteException {
        GameController gc = new GameController();
        assertNull(gc.getGame());
    }

    public void testSetLobbyState() {
        GameController g=new GameController();
        g.setLobbyState(LobbyState.GAME_FINISHED);
        assertEquals(LobbyState.GAME_FINISHED, g.getLobbyState());
    }

    public void testExitGame() throws RemoteException {
        GameController controller = new GameController();
        Game game = new Game(2, 1, 0, controller);
        Player p = new Player("pippo", game);
        game.getPlayers().add(p);
        try{
            controller.exitGame(p);
        }catch(InvalidLobbyStateException e){
            System.out.println("Error" + e.getMessage());
        }
        controller.setLobbyState(LobbyState.GAME_FINISHED);
        try{
            java.lang.reflect.Field field= GameController.class.getDeclaredField("game");
            field.setAccessible(true);
            field.set(controller, game);
        }catch(Exception e){
            fail("Error" + e.getMessage());
        }
        controller.exitGame(p);
        try{
            java.lang.reflect.Field field= GameController.class.getDeclaredField("game");
            field.setAccessible(true);
            assertNull(field.get(controller));
        }catch(Exception e){

        }
    }


    public void testStartGame() throws RemoteException {
        GameController controller = new GameController();
        Game game = new Game(2, 1, 0, controller);
        Player p1 = new Player("p1", game);
        Player p2 = new Player("p2", game);
        game.getPlayers().add(p1);
        game.getPlayers().add(p2);
        controller.setLobbyState(LobbyState.GAME_READY);

        // Inietta il game
        try {
            java.lang.reflect.Field field = GameController.class.getDeclaredField("game");
            field.setAccessible(true);
            field.set(controller, game);
        } catch (Exception e) {
            fail("Reflection failed: " + e);
        }

        controller.startGame();
        assertEquals(LobbyState.GAME_STARTED, controller.getLobbyState());

    }

    public void testJoinLobby() throws RemoteException {
        GameController gc=new GameController();
        DummyServer dummy = new DummyServer();
        gc.setLobbyState(LobbyState.GAME_CREATION);
        gc.setServer("Alice", dummy);
        gc.createLobby("Alice", 3, 1, 0);

        gc.joinLobby("ciao2");
        try {
            gc.joinLobby("ciao");
        }catch(InvalidUserNameException e){
            System.out.println(e.getMessage());
        }

        try {
            gc.joinLobby("ciao3");
        }catch(InvalidAddPlayerException e){
            System.out.println(e.getMessage());
        }

    }
    public void testJoinLobby1() throws RemoteException {
        GameController gc = new GameController();
        DummyServer dummy = new DummyServer();

        gc.setServer("Alice", dummy);
        gc.createLobby("Alice", 3, 1, 0);
        try {
            gc.joinLobby("ciao2");
        } catch (InvalidGameCreationException e) {
        }
        try {
            gc.joinLobby("ciao2");
        }catch(InvalidAddPlayerException e){}

    }

    public class DummyServer implements Server {
        public List<String> notifiedMessages = new ArrayList<>();
        public boolean lobbyCreated = false;
        public boolean gameStarted = false;

        @Override
        public boolean getIfSubscribed(Handler handler) { return false; }

        @Override
        public GameUpdater getGameUpdater(String name) { return null; }

        @Override
        public void notifyClient(String name, String message) throws RemoteException {
            notifiedMessages.add("notifyClient:" + name + ":" + message);
        }

        @Override
        public void notifyBroadcast(List<String> exclude, String message) throws RemoteException {
            notifiedMessages.add("broadcast:" + message);
        }

        @Override
        public void notifyLobbyCreated(String name) throws RemoteException {
            lobbyCreated = true;
            notifiedMessages.add("lobbyCreated:" + name);
        }

        @Override
        public void notifyLobbyJoined(String name) throws RemoteException {
            notifiedMessages.add("lobbyJoined:" + name);
        }

        @Override
        public void acceptCreateLobby(String name) throws RemoteException {
            notifiedMessages.add("acceptCreateLobby:" + name);
        }

        @Override
        public void updatePlayerList(String exclude) throws RemoteException {
            notifiedMessages.add("updatePlayerList:" + exclude);
        }

        @Override
        public void notifyGameStarted() throws RemoteException {
            gameStarted = true;
            notifiedMessages.add("gameStarted");
        }

        @Override
        public Handler getHandlerByName(String name) { return null; }
    }

    public void testCreateLobby() throws RemoteException {
        GameController gc=new GameController();
        DummyServer dummy = new DummyServer();
        gc.setLobbyState(LobbyState.GAME_CREATION);
        gc.setServer("Alice", dummy);
        gc.createLobby("Alice", 2, 1, 0); // numPlayers, shipLevel, gameMode
        assertNotNull(gc.getGame());
        assertEquals(LobbyState.GAME_READY, gc.getLobbyState());
        // Join con altro giocatore
        gc.setServer("Bob", dummy);
        gc.joinLobby("Bob");
        assertEquals(2, gc.getGame().getPlayers().size());

    }
    public void testCreateLobby1() throws RemoteException {
        GameController gc=new GameController();
        DummyServer dummy = new DummyServer();
        gc.setLobbyState(LobbyState.GAME_CREATION);
        gc.setServer("Alice", dummy);
        try {
            gc.createLobby("Alice", 2, 3, 0); // numPlayers, shipLevel, gameMode
        }catch(Exception e){
        }


    }
    public void testCreateLobby2() throws RemoteException {
        GameController gc=new GameController();
        DummyServer dummy = new DummyServer();
        gc.setLobbyState(LobbyState.GAME_CREATION);
        gc.setServer("Alice", dummy);
        try {
            gc.createLobby("Alice", 2, 2, 4); // numPlayers, shipLevel, gameMode
        }catch(Exception e){
        }


    }
    public void testCreateLobby3() throws RemoteException {
        GameController gc=new GameController();
        DummyServer dummy = new DummyServer();
        gc.setLobbyState(LobbyState.GAME_CREATION);
        gc.setServer("Alice", dummy);
        try {
            gc.createLobby("Alice", 1, 2, 0); // numPlayers, shipLevel, gameMode
        }catch(Exception e){
        }


    }
    public void testCreateLobby4() throws RemoteException {
        GameController gc=new GameController();
        DummyServer dummy = new DummyServer();
        gc.setLobbyState(LobbyState.GAME_CREATION);
        gc.setServer("Alice", dummy);
        gc.createLobby("Alice", 2, 1, 0); // numPlayers, shipLevel, gameMode
        assertNotNull(gc.getGame());
        assertEquals(LobbyState.GAME_READY, gc.getLobbyState());
        // Join con altro giocatore
        gc.setServer("Bob", dummy);
        gc.joinLobby("Bob");
        assertEquals(2, gc.getGame().getPlayers().size());
        try{
            gc.createLobby("Alice", 1, 2, 0);
        }catch(InvalidGameCreationException e){}


    }
    public void testCreateLobby5() throws RemoteException {
        GameController gc = new GameController();
        DummyServer dummy = new DummyServer();
        gc.setServer("Alice", dummy);
        try {
            gc.createLobby("Alice", 1, 2, 0); // numPlayers, shipLevel, gameMode
        } catch (Exception e) {
        }
    }



    public void testAddMessage() {
    }

    public void testTestGetGame() {
    }

    public void testTestSetLobbyState() {
    }

    public void testGetLobbyState() {
    }

    public void testTestExitGame() throws RemoteException {
        GameController gc=new GameController();
        DummyServer dummy = new DummyServer();
        gc.setLobbyState(LobbyState.GAME_CREATION);
        gc.setServer("Alice", dummy);
        gc.createLobby("Alice", 2, 1, 0);
        gc.setServer("Bob", dummy);
        gc.joinLobby("Bob");
        gc.setLobbyState(LobbyState.GAME_FINISHED);
        Game game = new Game(2,1,0,gc);
        Player p = new Player("pippo", game);
        game.getPlayers().add(p);
        gc.exitGame(p);
    }

    public void testSetServer() {
        GameController gc = new GameController();
        DummyServer server = new DummyServer();
        gc.setServer("Mario", server);
        assertTrue(gc.getNames().contains("Mario"));
    }

    public void testTestStartGame() throws RemoteException {
        GameController gc=new GameController();
        DummyServer dummy = new DummyServer();
        gc.setLobbyState(LobbyState.GAME_CREATION);
        gc.setServer("Alice", dummy);
        gc.createLobby("Alice", 2, 1, 0);
        gc.setServer("Bob", dummy);
        gc.joinLobby("Bob");
        Game game = new Game(2, 1, 0, gc);
        game.getPlayers().add(new Player("Alice", game));
        game.getPlayers().add(new Player("Bob", game));
        gc.startGame();
        assertEquals(LobbyState.GAME_STARTED, gc.getLobbyState());
    }

    public void testTestJoinLobby() {
    }

    public void testTestCreateLobby() {
    }

    public void testDisconnect() {

    }

    public void testAddGameUpdater() throws RemoteException {
        GameController gc=new GameController();
        DummyServer dummy = new DummyServer();
        gc.setLobbyState(LobbyState.GAME_CREATION);
        gc.setServer("Alice", dummy);
        gc.createLobby("Alice", 2, 1, 0);
        gc.setServer("Bob", dummy);
        gc.joinLobby("Bob");
        gc.addGameUpdater(null, "Pippo");
    }

    public void testSaveGame() {
        GameController gc = new GameController();
        gc.saveGame("testGame.ser");
    }

    public void testSetGame() {
        GameController gc = new GameController();
        gc.setGame();
        assertTrue(gc.getFileLoaded());
    }

    public void testGetFileLoaded() {
        GameController gc = new GameController();
        gc.setGame();
        assertTrue(gc.getFileLoaded());
    }

    public void testCheckName() {
        GameController gc = new GameController();
        DummyServer server = new DummyServer();
        boolean ok = gc.checkName("Alice", server);
        assertTrue(ok);
        boolean again = gc.checkName("Alice", server);
        assertFalse(again);
    }
    public void testCheckName1() throws RemoteException {
        GameController gc = new GameController();
        DummyServer server = new DummyServer();
        DummyServer dummy = new DummyServer();
        gc.setLobbyState(LobbyState.GAME_CREATION);
        gc.setServer("Alice", dummy);
        gc.createLobby("Alice", 2, 1, 0);
        gc.setFileLoaded(true);
        boolean ok = gc.checkName("Alice", server);
        boolean again = gc.checkName("Alic", server);

    }

    public void testGetNames() {
        GameController gc = new GameController();
        DummyServer server = new DummyServer();
        gc.checkName("Mario", server);
        List<String> names = gc.getNames();
        assertTrue(names.contains("Mario"));
    }

    public void testSetGameCreating() {
        GameController gc = new GameController();
        gc.setGameCreating();
        assertEquals(LobbyState.GAME_CREATION, gc.getLobbyState());
    }

    public void testNotifyBroadcast() throws RemoteException {
        GameController gc = new GameController();
        DummyServer server = new DummyServer();
        gc.setServer("Alice", server);
        gc.notifyBroadcast(Arrays.asList("Mario"), "Ciao");
        assertTrue(server.notifiedMessages.stream().anyMatch(s -> s.contains("broadcast:Ciao")));
    }

    public void testUpdatePlayerList() throws RemoteException {
        GameController gc = new GameController();
        DummyServer server = new DummyServer();
        gc.setServer("Alice", server);
        gc.updatePlayerList("excludeTest");
        assertTrue(server.notifiedMessages.stream().anyMatch(s -> s.contains("updatePlayerList:excludeTest")));
    }

    public void testNotifyGameStarted() throws RemoteException {
        GameController gc = new GameController();
        DummyServer server=new DummyServer();
        gc.setServer("Alice", server);
        gc.notifyGameStarted();
        assertTrue(server.gameStarted);
    }
    public void testAddMessage_processMessage() throws Exception {
        // Arrange
        GameController gc = new GameController();

        // Message finto che aggiorna un flag quando gestito
        class TestMessage extends Message {
            boolean handled = false;

            public TestMessage() {
                super(); // o metti un handler se serve
            }

            @Override
            public void handle(GameController gc, String playerName) {
                handled = true;
            }

            @Override
            public Handler getHandler() {
                return new Handler() {
                    @Override
                    public String getPlayerName() {
                        return "TestPlayer";
                    }

                    @Override
                    public void sendMessage(Message message) throws RemoteException {

                    }

                    @Override
                    public void setPlayerName(String playerName) throws RemoteException {

                    }

                    @Override
                    public void setGameUpdater() throws RemoteException {

                    }

                    @Override
                    public void notifyNameAlreadyUsed() throws RemoteException {

                    }

                    @Override
                    public void goToAddAlien() throws RemoteException {

                    }

                    @Override
                    public void goToAbandoned() throws RemoteException {

                    }

                    @Override
                    public void goToActivateCannons() throws RemoteException {

                    }

                    @Override
                    public void goToActivateEngines() throws RemoteException {

                    }

                    @Override
                    public void goToActivateShields() throws RemoteException {

                    }

                    @Override
                    public void goToChangeGoods() throws RemoteException {

                    }

                    @Override
                    public void goToEndState() throws RemoteException {

                    }

                    @Override
                    public void goToLandOnAbandon() throws RemoteException {

                    }

                    @Override
                    public void goToLandOnPlanet() throws RemoteException {

                    }

                    @Override
                    public void goToRemoveAstronauts() throws RemoteException {

                    }

                    @Override
                    public void goToRemoveBestGoods() throws RemoteException {

                    }

                    @Override
                    public void goToFixShip() throws RemoteException {

                    }

                    @Override
                    public void goToShipWreck() throws RemoteException {

                    }

                    @Override
                    public void goToWaitingState() throws RemoteException {

                    }

                    @Override
                    public void goToWinEnemy() throws RemoteException {

                    }
                };
            }
        }

        TestMessage msg = new TestMessage();

        // Act
        gc.addMessage(msg);

        // Attendi che il thread consumer processi il messaggio
        Thread.sleep(300); // Attendi 300ms (sufficiente nella maggior parte dei casi)

        // Assert
        assertTrue("Il messaggio dovrebbe essere stato gestito", msg.handled);
    }
    public void testAddMessage_processMessage1() throws Exception {
        // Arrange
        GameController gc = new GameController();

        // Message finto che aggiorna un flag quando gestito
        class TestMessage extends Message {
            boolean handled = false;

            public TestMessage() {
                super(); // o metti un handler se serve
            }

            @Override
            public void handle(GameController gc, String playerName) {
                handled = false;
            }

            @Override
            public Handler getHandler() {
                return new Handler() {
                    @Override
                    public String getPlayerName() {
                        return "TestPlayer";
                    }

                    @Override
                    public void sendMessage(Message message) throws RemoteException {

                    }

                    @Override
                    public void setPlayerName(String playerName) throws RemoteException {

                    }

                    @Override
                    public void setGameUpdater() throws RemoteException {

                    }

                    @Override
                    public void notifyNameAlreadyUsed() throws RemoteException {

                    }

                    @Override
                    public void goToAddAlien() throws RemoteException {

                    }

                    @Override
                    public void goToAbandoned() throws RemoteException {

                    }

                    @Override
                    public void goToActivateCannons() throws RemoteException {

                    }

                    @Override
                    public void goToActivateEngines() throws RemoteException {

                    }

                    @Override
                    public void goToActivateShields() throws RemoteException {

                    }

                    @Override
                    public void goToChangeGoods() throws RemoteException {

                    }

                    @Override
                    public void goToEndState() throws RemoteException {

                    }

                    @Override
                    public void goToLandOnAbandon() throws RemoteException {

                    }

                    @Override
                    public void goToLandOnPlanet() throws RemoteException {

                    }

                    @Override
                    public void goToRemoveAstronauts() throws RemoteException {

                    }

                    @Override
                    public void goToRemoveBestGoods() throws RemoteException {

                    }

                    @Override
                    public void goToFixShip() throws RemoteException {

                    }

                    @Override
                    public void goToShipWreck() throws RemoteException {

                    }

                    @Override
                    public void goToWaitingState() throws RemoteException {

                    }

                    @Override
                    public void goToWinEnemy() throws RemoteException {

                    }
                };
            }
        }

        TestMessage msg = new TestMessage();


        gc.addMessage(msg);


        // Attendi che il thread consumer processi il messaggio
        Thread.sleep(300); // Attendi 300ms (sufficiente nella maggior parte dei casi)
    }
}