package org.example.ServerPkg.ControllerPkg;

import java.io.Serializable;

public enum LobbyState implements Serializable {
    GAME_CREATION,
    GAME_READY,
    GAME_FINISHED
}
