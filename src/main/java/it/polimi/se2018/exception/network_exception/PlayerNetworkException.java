package it.polimi.se2018.exception.network_exception;

/**
 * Class that define player exceptions on the network.
 * @author DavideMammarella
 */
public class PlayerNetworkException extends Exception {
    public PlayerNetworkException (){
        super();
    }

    public PlayerNetworkException (String exceptionText) {super(exceptionText);}

    public PlayerNetworkException (Throwable e) {super(e);}
}
