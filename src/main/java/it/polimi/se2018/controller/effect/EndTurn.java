package it.polimi.se2018.controller.effect;

import it.polimi.se2018.exception.effect_exception.NumberInfoWrongException;
import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import it.polimi.se2018.model.GameBoard;

/**
 * Class that manage the end of the turn.
 */
public class EndTurn extends EffectGame {

    private boolean special;

    /**
     * Constructor.
     *
     * @param special true if is a special calling of end turn, false otherwise.
     */
    public EndTurn(boolean special) {
        this.special = special;
    }

    /**
     * Method used to applicate an effect to the game.
     *
     * @param gameBoard gameboard on when the player are playing.
     * @param idPlayer ID of the player that requested the effect.
     * @param infoMove information of the moves played with the effect.
     * @throws GameException exception derivate from game restriction.
     */
    @Override
    public void doEffect(GameBoard gameBoard, int idPlayer, int[] infoMove) throws GameException{
        if (infoMove !=null) throw new NumberInfoWrongException();
        this.setGameBoard(gameBoard);
        this.setIdPlayer(idPlayer);
        if (special) gameBoard.endSpecialFirstTurn(getIdPlayer());
        else gameBoard.nextPlayer(getIdPlayer());
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
