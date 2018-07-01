package it.polimi.se2018.exception.network_exception.server;

/**
 * @author Luca Genoni
 */

public class GameStartedException extends Exception {
    public GameStartedException(){
        super("La stanza è stata avviata");
    }

}
