package org.example.ServerPkg.ControllerPkg.PlayerStates;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;

import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class AbandonedStateTest extends TestCase {

    public void testActivateCannons() throws RemoteException {
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",game);
        game.getPlayers().add(player);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.activateCannons(null, player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testUseBatteries() throws RemoteException {
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.useBatteries(null, player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testEndActivateCannons() throws RemoteException {
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.endActivateCannons(player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testActivateEngines() throws RemoteException {
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.activateEngines(new ArrayList<>(), player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testEndActivateEngines() throws RemoteException {
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.endActivateEngines(player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testActivateShields() throws RemoteException {
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.activateShields(new ArrayList<>(), player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testEndActivateShields() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.endActivateShields(player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testRemoveGood() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.removeGood(new Points(5,4),2,player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testAddGood() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.addGood(new Points(5,4),2,player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testEndChangeGoods() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.endChangeGoods(player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testLandOnAbandon() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.landOnAbandon(true,player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testLandOnPlanet() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.landOnPlanet(true,1,player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testRemoveAstronauts() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.removeAstronauts(new Points(5,5),player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testEndRemoveAstronauts() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.endRemoveAstronauts(player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testRemoveBestGood() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.removeBestGood(new Points(5,5),2,player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testRemoveBatteries() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.removeBatteries(new Points(5,5),player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testEndRemoveBestGoods() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.endRemoveBestGoods(player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testAcceptReward() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.acceptReward(true,player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testChooseWrecked() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.chooseWrecked(new Points(5,5),player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testEndWreckedState() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.endWreckedState(player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testRemoveTile() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.removeTile(new Points(5,5),player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testEndFixShip() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.endFixShip(player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testShowDeck() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.showDeck(player,1);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testEndShowDeck() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.endShowDeck(player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testPickComponentTile() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.pickComponentTile(player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testRightRotateTile() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.rightRotateTile(player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testLeftRotateTile() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.leftRotateTile(player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testPlaceTile() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.placeTile(player,new Points(5,5));
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testEndBuildShip() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.endBuildShip(player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testAddBrownAlien() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.addBrownAlien(new Points(5,5),player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testAddPurpleAlien() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.addPurpleAlien(new Points(5,5),player);
        assertTrue(player.getState() instanceof AbandonedState);
    }


    public void testDiscardComponent() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.discardComponent(player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testPickDiscoveredComponent() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.pickDiscoveredComponent(player,1);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testBookComponent() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.bookComponent(player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testPickBookedTile() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.pickBookedTile(1,player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testEndAlienState() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.endAlienState(player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testTurnTimer() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.turnTimer(player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testDisconnect() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.disconnect(player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testSelectPosition() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.selectPosition(0,player);
        assertTrue(player.getState() instanceof AbandonedState);
    }

    public void testAbandonGame() throws RemoteException{
        Game game= new Game(3,2,1,new GameController());
        Player player= new Player("A",null);
        player.setPlayerState(new AbandonedState(game,player));
        AbandonedState state= new AbandonedState(game,player);
        state.AbandonGame(player);
        assertTrue(player.getState() instanceof AbandonedState);

    }
}