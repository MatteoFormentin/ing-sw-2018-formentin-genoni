package it.polimi.se2018.controller.effect;

import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.exception.effect_exception.NumberInfoWrongException;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.model.GameBoard;

/**
 * the effect that able to inset a dice
 */
public class ChangeDiceValue extends EffectGame {
    private boolean trueRandomFalseOpposite;

    public ChangeDiceValue(boolean trueRandomFalseOpposite) {
        this.trueRandomFalseOpposite = trueRandomFalseOpposite;
    }

    @Override
    public void doEffect(GameBoard gameBoard, int idPlayer, int[] infoMove) throws GameException{
        if (infoMove !=null) throw new NumberInfoWrongException();
        this.setGameBoard(gameBoard);
        this.setIdPlayer(idPlayer);
        if (trueRandomFalseOpposite) gameBoard.rollDiceInHand(getIdPlayer());
        else gameBoard.oppositeFaceDice(getIdPlayer());
    }

    @Override
    public void undo() throws GameException {
        throw new UnsupportedOperationException();
    }

    @Override
    public EventView askTheViewTheInfo() {
        return null;
    }

}
