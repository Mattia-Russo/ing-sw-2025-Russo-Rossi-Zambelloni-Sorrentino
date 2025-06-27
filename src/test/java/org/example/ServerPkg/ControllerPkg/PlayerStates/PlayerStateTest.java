package org.example.ServerPkg.ControllerPkg.PlayerStates;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.EndStateException;

import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.ShipBoard;

import java.rmi.RemoteException;

public class PlayerStateTest extends TestCase {

    public void testActivateCannons() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState playerState= new PlayerState(game,player);
        try{
            playerState.activateCannons(null,player);
        }catch(IllegalStateException e){

        }
    }

    public void testUseBatteries() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.useBatteries(null,player);
        }catch(IllegalStateException e){

        }
    }

    public void testEndActivateCannons() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.endActivateCannons(player);
        }catch(IllegalStateException e){

        }
    }

    public void testActivateEngines() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.activateEngines(null,player);
        }catch(IllegalStateException e){

        }
    }

    public void testEndActivateEngines() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.endActivateEngines(player);
        }catch(IllegalStateException e){

        }
    }

    public void testActivateShields() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.activateShields(null,player);
        }catch(IllegalStateException e){

        }
    }

    public void testEndActivateShields() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.endActivateShields(player);
        }catch(IllegalStateException e){

        }
    }

    public void testRemoveGood() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.removeGood(null,1,player);
        }catch(IllegalStateException e){

        }
    }

    public void testAddGood() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.addGood(null,1,player);
        }catch(IllegalStateException e){

        }
    }

    public void testEndChangeGoods() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.endChangeGoods(player);
        }catch(IllegalStateException e){

        }
    }

    public void testLandOnAbandon() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.landOnAbandon(true,player);
        }catch(IllegalStateException e){

        }
    }

    public void testLandOnPlanet() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.landOnPlanet(true,1 ,player);
        }catch(IllegalStateException e){

        }
    }

    public void testRemoveAstronauts() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.removeAstronauts(null,player);
        }catch(IllegalStateException e){

        }
    }

    public void testEndRemoveAstronauts() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.endRemoveAstronauts(player);
        }catch(IllegalStateException e){

        }

    }

    public void testRemoveBestGood() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.removeBestGood(null,1,player);
        }catch(IllegalStateException e){

        }
    }

    public void testRemoveBatteries() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.removeBatteries(null,player);
        }catch(IllegalStateException e){

        }
    }

    public void testEndRemoveBestGoods() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.endRemoveBestGoods(player);
        }catch(IllegalStateException e){

        }
    }

    public void testAcceptReward() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.acceptReward(true,player);
        }catch(IllegalStateException e){

        }
    }

    public void testEndWreckedState() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.endWreckedState(player);
        }catch(IllegalStateException e){

        }
    }

    public void testRemoveTile() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.removeTile(null,player);
        }catch(IllegalStateException e){

        }
    }

    public void testEndFixShip() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.endFixShip(player);
        }catch(IllegalStateException e){

        }
    }

    public void testShowDeck() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.showDeck(player,1);
        }catch(IllegalStateException e){

        }
    }

    public void testEndShowDeck() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.endShowDeck(player);

        }catch(IllegalStateException e){

        }
    }

    public void testPickComponentTile() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.pickComponentTile(player);
        }catch(IllegalStateException e){

        }
    }

    public void testRightRotateTile() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.rightRotateTile(player);
        }catch(IllegalStateException e){

        }
    }

    public void testLeftRotateTile() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.leftRotateTile(player);
        }catch(IllegalStateException e){

        }
    }

    public void testPlaceTile() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.placeTile(player,null);
        }catch(IllegalStateException e){

        }
    }

    public void testEndBuildShip() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.endBuildShip(player);
        }catch(IllegalStateException e){

        }
    }

    public void testAddBrownAlien() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.addBrownAlien(null,player);
        }catch(IllegalStateException e){

        }
    }

    public void testAddPurpleAlien() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.addPurpleAlien(null,player);
        }catch(IllegalStateException e){

        }
    }

    public void testTurnTimer() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.turnTimer(player);
        }catch(IllegalStateException e){

        }
    }

    public void testDiscardComponent() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.discardComponent(player);
        }catch(IllegalStateException e){

        }
    }

    public void testPickDiscoveredComponent() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.pickDiscoveredComponent(player,1);
        }catch(IllegalStateException e){

        }
    }

    public void testBookComponent() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.bookComponent(player);
        }catch(IllegalStateException e){

        }
    }

    public void testPickBookedTile() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.pickBookedTile(1,player);
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
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.chooseWrecked(null,player);
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
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.endAlienState(player);
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
        PlayerState PlayerState= new PlayerState(game,player);
        PlayerState.AbandonGame(player);

    }

    public void testDisconnect() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        PlayerState.disconnect(player);

    }

    public void testSelectPosition() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        PlayerState PlayerState= new PlayerState(game,player);
        try{
            PlayerState.selectPosition(1,player);
        }catch(IllegalStateException e){

        }
    }
}