package it.polimi.se2018.exception.gameboard_exception.tool_exception;

import it.polimi.se2018.exception.GameException;

public class DiceInHandToolException extends GameException {
    public DiceInHandToolException() {
        super("Non hai dadi in mano");
    }
}
