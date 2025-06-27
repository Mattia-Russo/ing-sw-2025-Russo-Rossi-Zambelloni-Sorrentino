package org.example.ServerPkg.ControllerPkg.PlayerStates;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.EndStateException;
import org.example.ServerPkg.Model.Exceptions.EndStateException;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.ShipBoard;

import java.rmi.RemoteException;

public class EndStateTest extends TestCase {

    public void testActivateCannons() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.activateCannons(null,player);
        }catch(EndStateException e){

        }
    }

    public void testUseBatteries() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.useBatteries(null,player);
        }catch(EndStateException e){

        }
    }

    public void testEndActivateCannons() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.endActivateCannons(player);
        }catch(EndStateException e){

        }
    }

    public void testActivateEngines() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.activateEngines(null,player);
        }catch(EndStateException e){

        }
    }

    public void testEndActivateEngines() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.endActivateEngines(player);
        }catch(EndStateException e){

        }
    }

    public void testActivateShields() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.activateShields(null,player);
        }catch(EndStateException e){

        }
    }

    public void testEndActivateShields() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.endActivateShields(player);
        }catch(EndStateException e){

        }
    }

    public void testRemoveGood() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.removeGood(null,1,player);
        }catch(EndStateException e){

        }
    }

    public void testAddGood() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.addGood(null,1,player);
        }catch(EndStateException e){

        }
    }

    public void testEndChangeGoods() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.endChangeGoods(player);
        }catch(EndStateException e){

        }
    }

    public void testLandOnAbandon() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.landOnAbandon(true,player);
        }catch(EndStateException e){

        }
    }

    public void testLandOnPlanet() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.landOnPlanet(true,1 ,player);
        }catch(EndStateException e){

        }
    }

    public void testRemoveAstronauts() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.removeAstronauts(null,player);
        }catch(EndStateException e){

        }
    }

    public void testEndRemoveAstronauts() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.endRemoveAstronauts(player);
        }catch(EndStateException e){

        }

    }

    public void testRemoveBestGood() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.removeBestGood(null,1,player);
        }catch(EndStateException e){

        }
    }

    public void testRemoveBatteries() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.removeBatteries(null,player);
        }catch(EndStateException e){

        }
    }

    public void testEndRemoveBestGoods() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.endRemoveBestGoods(player);
        }catch(EndStateException e){

        }
    }

    public void testAcceptReward() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.acceptReward(true,player);
        }catch(EndStateException e){

        }
    }

    public void testEndWreckedState() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.endWreckedState(player);
        }catch(EndStateException e){

        }
    }

    public void testRemoveTile() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.removeTile(null,player);
        }catch(EndStateException e){

        }
    }

    public void testEndFixShip() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.endFixShip(player);
        }catch(EndStateException e){

        }
    }

    public void testShowDeck() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.showDeck(player,1);
        }catch(EndStateException e){

        }
    }

    public void testEndShowDeck() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.endShowDeck(player);

        }catch(EndStateException e){

        }
    }

    public void testPickComponentTile() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.pickComponentTile(player);
        }catch(EndStateException e){

        }
    }

    public void testRightRotateTile() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.rightRotateTile(player);
        }catch(EndStateException e){

        }
    }

    public void testLeftRotateTile() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.leftRotateTile(player);
        }catch(EndStateException e){

        }
    }

    public void testPlaceTile() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.placeTile(player,null);
        }catch(EndStateException e){

        }
    }

    public void testEndBuildShip() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.endBuildShip(player);
        }catch(EndStateException e){

        }
    }

    public void testAddBrownAlien() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.addBrownAlien(null,player);
        }catch(EndStateException e){

        }
    }

    public void testAddPurpleAlien() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.addPurpleAlien(null,player);
        }catch(EndStateException e){

        }
    }

    public void testTurnTimer() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.turnTimer(player);
        }catch(EndStateException e){

        }
    }

    public void testDiscardComponent() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.discardComponent(player);
        }catch(EndStateException e){

        }
    }

    public void testPickDiscoveredComponent() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.pickDiscoveredComponent(player,1);
        }catch(EndStateException e){

        }
    }

    public void testBookComponent() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.bookComponent(player);
        }catch(EndStateException e){

        }
    }

    public void testPickBookedTile() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.pickBookedTile(1,player);
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
        EndState EndState= new EndState(game,player);
        try{
            EndState.chooseWrecked(null,player);
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
        EndState EndState= new EndState(game,player);
        try{
            EndState.endAlienState(player);
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
        EndState EndState= new EndState(game,player);
        EndState.AbandonGame(player);

    }

    public void testDisconnect() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        EndState.disconnect(player);

    }

    public void testSelectPosition() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        EndState EndState= new EndState(game,player);
        try{
            EndState.selectPosition(1,player);
        }catch(EndStateException e){

        }
    }
}