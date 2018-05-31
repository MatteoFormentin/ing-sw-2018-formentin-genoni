package it.polimi.se2018.exception.WindowException;

/**
 * * The class {@code RestrictionColorViolatedException} is a subclass of {@code Exception}
 *
 * So it's a checked exceptions and it need to be declared in a
 * method or constructor's {@code throws} clause if they can be thrown
 * by the execution of the method or constructor and propagate outside
 * the method or constructor boundary.
 */
public class RestrictionValueViolatedException extends Exception {

    public RestrictionValueViolatedException(){
        super("Hai violato la restrizione di valore, non puoi inserire il dado in questa cella");
    }
}