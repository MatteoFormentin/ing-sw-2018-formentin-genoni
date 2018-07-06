package it.polimi.se2018.controller.effect;

import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.exception.effect_exception.NumberInfoWrongException;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_input.SelectDiceFromRoundTrack;
import it.polimi.se2018.model.GameBoard;

/**
 * Class that manage the effects of the round track.
 */
public class RoundTrackEffect extends EffectGame {

    private int round;
    private int index;
    private boolean trueSwapDiceFalseSetColorRestriction;

    /**
     * Constructor.
     *
     * @param trueSwapDiceFalseSetColorRestriction true swap dice on the round track, false set a color restriction.
     */
    public RoundTrackEffect(boolean trueSwapDiceFalseSetColorRestriction) {
        this.trueSwapDiceFalseSetColorRestriction = trueSwapDiceFalseSetColorRestriction;
    }

    /**
     * Method used to applicate an effect to the game.
     *
     * @param gameBoard gameboard on when the player are playing.
     * @param idPlayer  ID of the player that requested the effect.
     * @param infoMove  information of the moves played with the effect.
     * @throws GameException exception derivate from game restriction.
     */
    @Override
    public void doEffect(GameBoard gameBoard, int idPlayer, int[] infoMove) throws GameException {
        if (infoMove.length != 2) throw new NumberInfoWrongException();
        this.setGameBoard(gameBoard);
        this.setIdPlayer(idPlayer);
        round = infoMove[0];
        index = infoMove[1];
        if (trueSwapDiceFalseSetColorRestriction) {
            gameBoard.changeDiceBetweenHandAndRoundTrack(getIdPlayer(), round, index);
        } else {
            gameBoard.imposeColorRestriction(getIdPlayer(), round, index);
        }
    }

    /**
     * Method used to undo the application of an effect to the game.
     *
     * @throws UnsupportedOperationException exception derivate from game restriction.
     */
    @Override
    public void undo() throws GameException {
        throw new UnsupportedOperationException();

    }

    /**
     * Method used to ask an event view.
     *
     * @return cell of the round track for the dice effect.
     */
    @Override
    public EventClient eventViewToAsk() {
        return new SelectDiceFromRoundTrack();
    }
}
