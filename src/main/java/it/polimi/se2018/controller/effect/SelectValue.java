package it.polimi.se2018.controller.effect;

import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.exception.effect_exception.NumberInfoWrongException;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_input.SelectIncrementOrDecreaseDice;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_input.SelectValueDice;
import it.polimi.se2018.model.GameBoard;

/**
 * Class that manage the value of a dice.
 */
public class SelectValue extends EffectGame {

    private int valueDice;
    private boolean trueSetValueFalseIncrementDec;

    /**
     * Constructor.
     *
     * @param trueSetValueFalseIncrementDec true set dice value, false decrement dice value.
     */
    public SelectValue(boolean trueSetValueFalseIncrementDec) {
        this.trueSetValueFalseIncrementDec = trueSetValueFalseIncrementDec;
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
    public void doEffect(GameBoard gameBoard, int idPlayer, int[] infoMove) throws GameException {
        if (infoMove.length != 1) throw new NumberInfoWrongException();
        this.setGameBoard(gameBoard);
        this.setIdPlayer(idPlayer);
        valueDice = infoMove[0];
        if(trueSetValueFalseIncrementDec) getGameBoard().setValueDiceHand(getIdPlayer(),valueDice);
        else {
            if(valueDice==1) getGameBoard().increaseOrDecrease(getIdPlayer(),true);
            else if(valueDice==-1) getGameBoard().increaseOrDecrease(getIdPlayer(),false);
            else throw new GameException("View implementata male");
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
     * @return value to set on the dice if trueSetValueFalseIncrementDec is true,
     * call to a selection for incrementing or decrementing the actual value of the dice if trueSetValueFalseIncrementDec is false.
     */
    @Override
    public EventClient eventViewToAsk() {
        if(trueSetValueFalseIncrementDec) return new SelectValueDice();
        else return new SelectIncrementOrDecreaseDice();
    }
}
