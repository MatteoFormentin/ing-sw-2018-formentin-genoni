package it.polimi.se2018.exception.gameboard_exception.window_exception;

import it.polimi.se2018.exception.GameException;

public abstract class WindowRestriction extends GameException {

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public WindowRestriction() {
        super(null);
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public WindowRestriction(String message) {
        super(message);
    }
}