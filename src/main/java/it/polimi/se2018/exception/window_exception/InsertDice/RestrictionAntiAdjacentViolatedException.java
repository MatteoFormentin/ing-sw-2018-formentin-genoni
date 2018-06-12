package it.polimi.se2018.exception.window_exception.InsertDice;

import it.polimi.se2018.exception.window_exception.WindowRestriction;

/**
 * The class {@code RestrictionColorViolatedException} is a subclass of {@code Exception}
 * <p>
 * So it's a checked exceptions and it need to be declared in a
 * method or constructor's {@code throws} clause if they can be thrown
 * by the execution of the method or constructor and propagate outside
 * the method or constructor boundary.
 */
public class RestrictionAntiAdjacentViolatedException extends WindowRestriction {

    public RestrictionAntiAdjacentViolatedException() {
        super("Volevi piazzare il dado vicino ad altri.");
    }
}
