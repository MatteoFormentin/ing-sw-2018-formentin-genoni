package it.polimi.se2018.exception.network_exception.server;

/**
 * @author Luca Genoni
 */

public class ConnectionPlayerExeption extends Exception {
    public ConnectionPlayerExeption(){
        super("Il giocatore non è raggiungibile");
    }

}
