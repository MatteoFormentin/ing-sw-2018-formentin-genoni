package it.polimi.se2018.exception.window_exception.insert_dice;

import it.polimi.se2018.exception.window_exception.WindowRestriction;

/**
 * The class {@code CurrentPlayerException} is a subclass of {@code Exception}
 * <p>
 * So it's a checked exceptions and it need to be declared in a
 * method or constructor's {@code throws} clause if they can be thrown
 * by the execution of the method or constructor and propagate outside
 * the method or constructor boundary.
 */
public class NullDiceToAddException extends WindowRestriction {

    public NullDiceToAddException() {
        super("Non puoi aggiungere il nulla nella cella.");
    }

}
