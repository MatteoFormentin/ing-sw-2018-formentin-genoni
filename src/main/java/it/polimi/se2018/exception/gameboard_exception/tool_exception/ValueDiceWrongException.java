package it.polimi.se2018.exception.gameboard_exception.tool_exception;

import it.polimi.se2018.exception.GameException;

/**
 * The class {@code CurrentPlayerException} is a subclass of {@code Exception}
 * <p>
 * So it's a checked exceptions and it need to be declared in a
 * method or constructor's {@code throws} clause if they can be thrown
 * by the execution of the method or constructor and propagate outside
 * the method or constructor boundary.
 */
public class ValueDiceWrongException extends GameException {
    public ValueDiceWrongException() {
        super("Non è possibile settare questo valore per il dado");
    }
}
