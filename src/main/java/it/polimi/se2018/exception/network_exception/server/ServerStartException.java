package it.polimi.se2018.exception.network_exception.server;

/**
 * @author Luca Genoni
 */

public class ServerStartException extends Exception {
    public ServerStartException(){
        super("il server non può essere avviato");
    }
    public ServerStartException(String cause){
        super(cause);
    }
}
