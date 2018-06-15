package it.polimi.se2018.controller.effect;

import it.polimi.se2018.exception.effect_exception.NumberInfoWrongException;
import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.model.GameBoard;

/**
 * the effect that able to inset a dice
 */
public class EndTurn extends EffectGame {
    private boolean special;

    public EndTurn(boolean special) {
        this.special = special;
    }

    @Override
    public void doEffect(GameBoard gameBoard, int idPlayer, int[] infoMove) throws GameException{
        if (infoMove !=null) throw new NumberInfoWrongException();
        this.setGameBoard(gameBoard);
        this.setIdPlayer(idPlayer);
        if (special) gameBoard.endSpecialFirstTurn(getIdPlayer());
        else gameBoard.nextPlayer(getIdPlayer());
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
