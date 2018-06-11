package it.polimi.se2018.model.effect;

import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.model.GameBoard;

public abstract class EffectGame {
    GameBoard gameBoard;
    int idPlayer;

/**

 **/
    /**
     * Constructor for the an action
     *
     * @param gameBoard the Game board where to apply the effect
     * @param idPlayer  the id of the player who request the Action
     */
    EffectGame(GameBoard gameBoard, int idPlayer) {
        this.gameBoard = gameBoard;
        this.idPlayer = idPlayer;
    }

    public abstract void doEffect() throws GameException;
    public abstract void undo() throws GameException;
}
