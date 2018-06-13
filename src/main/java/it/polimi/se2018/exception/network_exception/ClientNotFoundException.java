package it.polimi.se2018.exception.network_exception;

/**
 * Class that define a client exception.
 *
 * So it's a checked exceptions and it need to be declared in a
 * method or constructor's {@code throws} clause if they can be thrown
 * by the execution of the method or constructor and propagate outside
 * the method or constructor boundary.
 *
 * @author DavideMammarella
 */

public class ClientNotFoundException extends Exception {

    public ClientNotFoundException() {
        super();
    }

    public ClientNotFoundException(Throwable cause){
        super(cause);
    }

    public ClientNotFoundException (String exceptionText) {
        super(exceptionText);
    }

}
