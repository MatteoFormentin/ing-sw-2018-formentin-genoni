package it.polimi.se2018.exception.network_exception;

/**
 * @author DavideMammarella
 */
public class PlayerNotFoundException extends Exception {
    public PlayerNotFoundException (String exceptionText){
        super(exceptionText);
    }
}
