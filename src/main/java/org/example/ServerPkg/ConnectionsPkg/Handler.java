package org.example.ServerPkg.ConnectionsPkg;

import org.example.MessagePkg.Message;
import org.example.ServerPkg.ControllerPkg.GameController;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Handler extends Remote {

    String getPlayerName() throws RemoteException;

    void sendMessage(Message message) throws RemoteException;

    void setPlayerName(String playerName) throws RemoteException;

    void setGameUpdater() throws RemoteException;

    void notifyNameAlreadyUsed() throws RemoteException;

    void goToAddAlien() throws RemoteException;

    void goToAbandoned() throws RemoteException;

    void goToActivateCannons() throws RemoteException;

    void goToActivateEngines() throws RemoteException;

    void goToActivateShields() throws RemoteException;

    void goToChangeGoods() throws RemoteException;

    void goToLandOnAbandon() throws RemoteException;

    void goToLandOnPlanet() throws RemoteException;

    void goToRemoveAstronauts() throws RemoteException;

    void goToRemoveBestGoods() throws RemoteException;

    void goToFixShip() throws RemoteException;

    void goToShipWreck() throws RemoteException;

    void goToWaitingState() throws RemoteException;

    void goToWinEnemy() throws RemoteException;
}
