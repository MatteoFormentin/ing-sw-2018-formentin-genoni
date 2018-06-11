package it.polimi.se2018.exception.player_exception;

import it.polimi.se2018.exception.GameException;

public abstract class PlayerException extends GameException {
    public PlayerException(String message) {
        super(message);
    }
}
