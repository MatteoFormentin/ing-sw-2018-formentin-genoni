package it.polimi.se2018.model.exception;

/**
 * The class {@code NickNameOfPlayerNullException} is a subclass of {@code Exception}
 *
 * So it's a checked exceptions and it need to be declared in a
 * method or constructor's {@code throws} clause if they can be thrown
 * by the execution of the method or constructor and propagate outside
 * the method or constructor boundary.
 */
public class NickNameOfPlayerNullException extends Exception {

    public NickNameOfPlayerNullException(){
        super("One or more players don't have a name");
    }
}
