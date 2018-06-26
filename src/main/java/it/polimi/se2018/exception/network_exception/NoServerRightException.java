package it.polimi.se2018.exception.network_exception;

/**
 * @author Luca Genoni
 */

public class NoServerRightException extends Exception {
    public  NoServerRightException(){
        super("Il server non è corretto");
    }

}
