package it.polimi.se2018.exception.NetworkException;

/**
 * Class that define a login exception.
 *
 * So it's a checked exceptions and it need to be declared in a
 * method or constructor's {@code throws} clause if they can be thrown
 * by the execution of the method or constructor and propagate outside
 * the method or constructor boundary.
 *
 * @author DavideMammarella
 */

public class PlayerAlreadyLoggedException extends Exception {
    public PlayerAlreadyLoggedException (String exceptionText){
        super(exceptionText);
    }
}
