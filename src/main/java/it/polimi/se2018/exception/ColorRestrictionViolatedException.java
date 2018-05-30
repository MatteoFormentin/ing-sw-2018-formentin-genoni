package it.polimi.se2018.exception;

/**
 * The class {@code ColorRestrictionViolatedException} is a subclass of {@code Exception}
 *
 * So it's a checked exceptions and it need to be declared in a
 * method or constructor's {@code throws} clause if they can be thrown
 * by the execution of the method or constructor and propagate outside
 * the method or constructor boundary.
 */
public class ColorRestrictionViolatedException extends Exception {

    public  ColorRestrictionViolatedException(){
        super("You can not place the die in this cell because it violates the color restriction");
    }
}
