package it.polimi.se2018.exception.gameboard_exception.cell_exception;

/**
 * The class {@code NullDiceToAddException} its a {@code CellException} subclass.
 * his form of {@code Throwable} that indicates conditions that a reasonable
 * application might want to catch.
 *
 * <p>The class {@code NullDiceToAddException} and any subclasses are <em>checked
 * exceptions</em>.  Checked exceptions need to be declared in a
 * method or constructor's {@code throws} clause if they can be thrown
 * by the execution of the method or constructor and propagate outside
 * the method or constructor boundary.
 *
 * @author Luca Genoni
 */
public class NullDiceToAddException extends CellException {

    /**
     * Constructs a new exception with {@code a null was entered as a die}
     * as its detail message. The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public NullDiceToAddException() {
        super("a null was entered as a die");
    }

}
