package it.polimi.se2018.exception.gameboard_exception.tool_exception;

import it.polimi.se2018.exception.gameboard_exception.player_state_exception.PlayerException;

/**
 * The class {@code CurrentPlayerException} is a subclass of {@code Exception}
 * <p>
 * So it's a checked exceptions and it need to be declared in a
 * method or constructor's {@code throws} clause if they can be thrown
 * by the execution of the method or constructor and propagate outside
 * the method or constructor boundary.
 */
public class RoundTrackIndexException extends PlayerException {
    public RoundTrackIndexException() {
        super("Il round non può essere selezionato");
    }
}
