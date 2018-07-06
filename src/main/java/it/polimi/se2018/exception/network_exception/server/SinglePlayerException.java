package it.polimi.se2018.exception.network_exception.server;

/**
 * @author Luca Genoni
 */

public class SinglePlayerException extends Exception {
    public SinglePlayerException() {
        super("c'è solo un giocatore connesso");
    }

}
