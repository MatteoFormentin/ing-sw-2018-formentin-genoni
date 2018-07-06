package it.polimi.se2018.exception.network_exception;

public class ServerSideException extends Exception {

    public ServerSideException() {
        super();
    }

    public ServerSideException(String exceptionText) {
        super(exceptionText);
    }

    public ServerSideException(String exceptionText, Throwable e) {
        super(exceptionText, e);
    }
}
