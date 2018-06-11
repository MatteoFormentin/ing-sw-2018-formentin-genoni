package it.polimi.se2018.exception.window_exception;

/**
 * * The class {@code RestrictionColorViolatedException} is a subclass of {@code Exception}
 * <p>
 * So it's a checked exceptions and it need to be declared in a
 * method or constructor's {@code throws} clause if they can be thrown
 * by the execution of the method or constructor and propagate outside
 * the method or constructor boundary.
 */
public class IndexColumnOutOfWindowException extends WindowRestriction {

    public IndexColumnOutOfWindowException() {
        super("L'indice della colonna è fuori dal dominio");
    }
}