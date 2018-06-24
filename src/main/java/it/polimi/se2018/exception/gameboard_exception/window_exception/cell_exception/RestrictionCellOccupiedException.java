package it.polimi.se2018.exception.gameboard_exception.window_exception.cell_exception;

import it.polimi.se2018.exception.gameboard_exception.window_exception.WindowRestriction;

/**
 * * The class {@code RestrictionColorViolatedException} is a subclass of {@code Exception}
 * <p>
 * So it's a checked exceptions and it need to be declared in a
 * method or constructor's {@code throws} clause if they can be thrown
 * by the execution of the method or constructor and propagate outside
 * the method or constructor boundary.
 */
public class RestrictionCellOccupiedException extends WindowRestriction {

    public RestrictionCellOccupiedException() {
        super("In questa cella c'è un altro dado");
    }
}