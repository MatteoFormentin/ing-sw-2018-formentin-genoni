package it.polimi.se2018.exception.gameboard_exception.cell_exception;

import it.polimi.se2018.exception.gameboard_exception.window_exception.WindowRestriction;

/**
 * The abstract class {@code CellException} and its subclasses are a form of
 * {@code Throwable} that indicates conditions that a reasonable
 * application might want to catch.
 *
 * <p>The abstract class {@code CellException} and any subclasses are <em>checked
 * exceptions</em>.  Checked exceptions need to be declared in a
 * method or constructor's {@code throws} clause if they can be thrown
 * by the execution of the method or constructor and propagate outside
 * the method or constructor boundary.
 *
 * @author Luca Genoni
 * @see java.lang.Error
 */
public abstract class CellException extends WindowRestriction {

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    CellException(String message) {
        super(message);
    }
}