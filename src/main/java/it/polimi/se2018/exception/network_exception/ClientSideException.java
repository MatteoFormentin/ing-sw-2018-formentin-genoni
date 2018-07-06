package it.polimi.se2018.exception.network_exception;

public class ClientSideException extends Exception {

    public ClientSideException() {
        super();
    }

    public ClientSideException(String exceptionText) {
        super(exceptionText);
    }

    public ClientSideException(String exceptionText, Throwable e) {
        super(exceptionText, e);
    }
}
