package it.polimi.se2018.exception.gameboard_exception.window_exception;

import it.polimi.se2018.exception.GameException;

public abstract class WindowRestriction  extends GameException {
    public WindowRestriction(String message) {
        super(message);
    }
}
