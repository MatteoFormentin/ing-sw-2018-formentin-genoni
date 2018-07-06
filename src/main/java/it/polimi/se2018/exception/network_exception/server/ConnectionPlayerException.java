package it.polimi.se2018.exception.network_exception.server;

/**
 * @author Luca Genoni
 */

public class ConnectionPlayerException extends Exception {
    public ConnectionPlayerException() {
        super("Il giocatore non è raggiungibile");
    }

}
