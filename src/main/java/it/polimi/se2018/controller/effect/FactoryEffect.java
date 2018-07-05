package it.polimi.se2018.controller.effect;

import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.exception.effect_exception.NumberInfoWrongException;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import it.polimi.se2018.model.GameBoard;

/**
 * Class that manage factory dice effects.
 */
public class FactoryEffect extends EffectGame {

    /**
     * Method used to applicate an effect to the game.
     *
     * @param gameBoard gameboard on when the player are playing.
     * @param idPlayer ID of the player that requested the effect.
     * @param infoMove information of the moves played with the effect.
     * @throws GameException exception derivate from game restriction.
     */
    @Override
    public void doEffect(GameBoard gameBoard, int idPlayer, int[] infoMove) throws GameException {
        if (infoMove != null) throw new NumberInfoWrongException();
        this.setGameBoard(gameBoard);
        this.setIdPlayer(idPlayer);
        gameBoard.changeDiceBetweenHandAndFactory(getIdPlayer());
    }

    /**
     * Method used to undo the application of an effect to the game.
     *
     * @throws GameException exception derivate from game restriction.
     */
    @Override
    public void undo() throws GameException {
        throw new UnsupportedOperationException();
    }

    /**
     * Method used to ask an event view.
     *
     * @return null cause these effect don't implying an event view ask.
     */
    @Override
    public EventClient eventViewToAsk() {
        return null;
    }
}
