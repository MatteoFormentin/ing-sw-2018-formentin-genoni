package it.polimi.se2018.exception.gameboard_exception;

import it.polimi.se2018.exception.GameException;

/**
 * * The class {@code RestrictionColorViolatedException} is a subclass of {@code Exception}
 * <p>
 * So it's a checked exceptions and it need to be declared in a
 * method or constructor's {@code throws} clause if they can be thrown
 * by the execution of the method or constructor and propagate outside
 * the method or constructor boundary.
 */
public class FatalGameErrorException extends GameException {

    public FatalGameErrorException() {
        super("Il gioco si trova in uno stato non corretto");
    }
}