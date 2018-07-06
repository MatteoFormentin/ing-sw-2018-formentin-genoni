package it.polimi.se2018.exception.effect_exception;

import it.polimi.se2018.exception.GameException;

/**
 * The class {@code CurrentPlayerException} is a subclass of {@code GameException}
 * <p>
 * It's a checked exceptions and it need to be declared in a
 * method or constructor's {@code throws} clause if they can be thrown
 * by the execution of the method or constructor and propagate outside
 * the method or constructor boundary.
 */
public class NumberInfoWrongException extends GameException {

    public NumberInfoWrongException() {
        super("The number of the info is wrong");
    }
}
