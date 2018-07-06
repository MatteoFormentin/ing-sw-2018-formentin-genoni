package it.polimi.se2018.exception.network_exception;

/**
 * @author Luca Genoni
 */

public class NoPortRightException extends Exception {
    public NoPortRightException() {
        super("La porta non è supportata");
    }

}
