package it.polimi.se2018.exception.tool_exception;

import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.exception.player_exception.PlayerException;

/**
 * The class {@code CurrentPlayerException} is a subclass of {@code Exception}
 * <p>
 * So it's a checked exceptions and it need to be declared in a
 * method or constructor's {@code throws} clause if they can be thrown
 * by the execution of the method or constructor and propagate outside
 * the method or constructor boundary.
 */
public class NoEnoughTokenException extends PlayerException {
    public NoEnoughTokenException() {
        super("Non hai abbastanza Segnalini");
    }
}
