package org.example.ServerPkg.ControllerPkg.PlayerStates;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.EndStateException;
import org.example.ServerPkg.Model.Exceptions.WaitingStateException;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.ShipBoard;

import java.rmi.RemoteException;

public class WaitingStateTest extends TestCase {

    public void testActivateCannons() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.activateCannons(null,player);
        }catch(WaitingStateException e){

        }
    }

    public void testUseBatteries() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.useBatteries(null,player);
        }catch(WaitingStateException e){

        }
    }

    public void testEndActivateCannons() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.endActivateCannons(player);
        }catch(WaitingStateException e){

        }
    }

    public void testActivateEngines() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.activateEngines(null,player);
        }catch(WaitingStateException e){

        }
    }

    public void testEndActivateEngines() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.endActivateEngines(player);
        }catch(WaitingStateException e){

        }
    }

    public void testActivateShields() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.activateShields(null,player);
        }catch(WaitingStateException e){

        }
    }

    public void testEndActivateShields() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.endActivateShields(player);
        }catch(WaitingStateException e){

        }
    }

    public void testRemoveGood() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.removeGood(null,1,player);
        }catch(WaitingStateException e){

        }
    }

    public void testAddGood() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.addGood(null,1,player);
        }catch(WaitingStateException e){

        }
    }

    public void testEndChangeGoods() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.endChangeGoods(player);
        }catch(WaitingStateException e){

        }
    }

    public void testLandOnAbandon() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.landOnAbandon(true,player);
        }catch(WaitingStateException e){

        }
    }

    public void testLandOnPlanet() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.landOnPlanet(true,1 ,player);
        }catch(WaitingStateException e){

        }
    }

    public void testRemoveAstronauts() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.removeAstronauts(null,player);
        }catch(WaitingStateException e){

        }
    }

    public void testEndRemoveAstronauts() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.endRemoveAstronauts(player);
        }catch(WaitingStateException e){

        }

    }

    public void testRemoveBestGood() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.removeBestGood(null,1,player);
        }catch(WaitingStateException e){

        }
    }

    public void testRemoveBatteries() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.removeBatteries(null,player);
        }catch(WaitingStateException e){

        }
    }

    public void testEndRemoveBestGoods() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.endRemoveBestGoods(player);
        }catch(WaitingStateException e){

        }
    }

    public void testAcceptReward() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.acceptReward(true,player);
        }catch(WaitingStateException e){

        }
    }

    public void testEndWreckedState() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.endWreckedState(player);
        }catch(WaitingStateException e){

        }
    }

    public void testRemoveTile() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.removeTile(null,player);
        }catch(WaitingStateException e){

        }
    }

    public void testEndFixShip() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.endFixShip(player);
        }catch(WaitingStateException e){

        }
    }

    public void testShowDeck() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.showDeck(player,1);
        }catch(WaitingStateException e){

        }
    }

    public void testEndShowDeck() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.endShowDeck(player);

        }catch(WaitingStateException e){

        }
    }

    public void testPickComponentTile() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.pickComponentTile(player);
        }catch(WaitingStateException e){

        }
    }

    public void testRightRotateTile() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.rightRotateTile(player);
        }catch(WaitingStateException e){

        }
    }

    public void testLeftRotateTile() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.leftRotateTile(player);
        }catch(WaitingStateException e){

        }
    }

    public void testPlaceTile() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.placeTile(player,null);
        }catch(WaitingStateException e){

        }
    }

    public void testEndBuildShip() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.endBuildShip(player);
        }catch(WaitingStateException e){

        }
    }

    public void testAddBrownAlien() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.addBrownAlien(null,player);
        }catch(WaitingStateException e){

        }
    }

    public void testAddPurpleAlien() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.addPurpleAlien(null,player);
        }catch(WaitingStateException e){

        }
    }

    public void testTurnTimer() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.turnTimer(player);
        }catch(WaitingStateException e){

        }
    }

    public void testDiscardComponent() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.discardComponent(player);
        }catch(WaitingStateException e){

        }
    }

    public void testPickDiscoveredComponent() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.pickDiscoveredComponent(player,1);
        }catch(WaitingStateException e){

        }
    }

    public void testBookComponent() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.bookComponent(player);
        }catch(WaitingStateException e){

        }
    }

    public void testPickBookedTile() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.pickBookedTile(1,player);
        }catch(EndStateException e){

        }
    }

    public void testChooseWrecked() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.chooseWrecked(null,player);
        }catch(EndStateException e){

        }
    }

    public void testEndAlienState() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.endAlienState(player);
        }catch(EndStateException e){

        }
    }

    public void testAbandonGame() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        WaitingState.AbandonGame(player);

    }

    public void testDisconnect() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        WaitingState.disconnect(player);

    }

    public void testSelectPosition() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        WaitingState WaitingState= new WaitingState(game,player);
        try{
            WaitingState.selectPosition(1,player);
        }catch(WaitingStateException e){

        }
    }
}