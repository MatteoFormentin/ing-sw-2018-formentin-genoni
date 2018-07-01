package it.polimi.se2018.exception.network_exception.client;

/**
 * @author Luca Genoni
 */

public class ConnectionProblemException extends Exception {
    public ConnectionProblemException(String cause){
        super("problemi relativi alla connessione"+cause);
    }

}
