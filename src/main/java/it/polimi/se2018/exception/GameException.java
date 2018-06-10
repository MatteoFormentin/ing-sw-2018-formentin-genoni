package it.polimi.se2018.exception;

public abstract class GameException extends Exception {
    public GameException(String message) {
        super(message);
    }
}
