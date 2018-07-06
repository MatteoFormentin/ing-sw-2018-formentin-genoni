package it.polimi.se2018.exception.network_exception;

/**
 * Class that define a room exception.
 * <p>
 * So it's a checked exceptions and it need to be declared in a
 * method or constructor's {@code throws} clause if they can be thrown
 * by the execution of the method or constructor and propagate outside
 * the method or constructor boundary.
 *
 * @author DavideMammarella
 */

public class RoomIsFullException extends Exception {
    public RoomIsFullException(String exceptionText) {
        super(exceptionText);
    }
}