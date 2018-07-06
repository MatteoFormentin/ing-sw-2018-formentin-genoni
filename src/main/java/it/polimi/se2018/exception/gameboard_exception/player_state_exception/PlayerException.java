package it.polimi.se2018.exception.gameboard_exception.player_state_exception;

import it.polimi.se2018.exception.GameException;

/**
 * The abstract class {@code PlayerException} is a subclass of {@code GameException}
 * <p>
 * if there is any of this exception the controller shouldn't ask the request for the input of the current effect
 */
public abstract class PlayerException extends GameException {
    public PlayerException(String message) {
        super(message);
    }
}
