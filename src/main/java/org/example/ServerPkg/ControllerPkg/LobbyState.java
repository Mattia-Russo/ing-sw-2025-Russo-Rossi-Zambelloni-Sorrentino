package org.example.ServerPkg.ControllerPkg;

import java.io.Serializable;

public enum LobbyState implements Serializable {
    GAME_NOT_EXISTS,
    GAME_CREATION,
    GAME_READY,
    GAME_STARTED,
    GAME_FINISHED
}
