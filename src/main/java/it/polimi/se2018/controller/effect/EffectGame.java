package it.polimi.se2018.controller.effect;

import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import it.polimi.se2018.model.GameBoard;

import java.io.Serializable;

/**
 * Class that manage the general effects on the game.
 */
public abstract class EffectGame implements Serializable {

    private GameBoard gameBoard;
    private int idPlayer;

    /**
     * Method used to applicate an effect to the game.
     *
     * @param gameBoard gameboard on when the player are playing.
     * @param idPlayer ID of the player that requested the effect.
     * @param infoMove information of the moves played with the effect.
     * @throws GameException exception derivate from game restriction.
     */
    public abstract void doEffect(GameBoard gameBoard, int idPlayer,int[] infoMove) throws GameException;

    /**
     * Method used to undo the application of an effect to the game.
     *
     * @throws GameException exception derivate from game restriction.
     */
    public abstract void undo() throws GameException;

    /**
     * Method used to ask an event view.
     */
    public abstract EventClient eventViewToAsk();

    //------------------------------------------------------------------------------------------------------------------
    // GETTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Getter for the gameboard.
     *
     * @return gameboard on when the player are playing.
     */
    GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Getter for the ID player.
     *
     * @return ID of the player that's on the gameboard.
     */
    int getIdPlayer() {
        return idPlayer;
    }

    //------------------------------------------------------------------------------------------------------------------
    // SETTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Setter for the gameboard.
     *
     * @param gameBoard gameboard on when the player are playing.
     */
    void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * Setter for the ID player.
     *
     * @param idPlayer ID of the player.
     */
    void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }
}
