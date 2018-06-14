package it.polimi.se2018.model.effect;

import it.polimi.se2018.exception.effect_exception.NoInfoSetted;
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
    public void doEffect(GameBoard gameBoard, int idPlayer) throws GameException,NoInfoSetted {
        if (special) gameBoard.endSpecialFirstTurn(idPlayer);
        else gameBoard.nextPlayer(idPlayer);
    }

    @Override
    public void undo(GameBoard gameBoard, int idPlayer) throws GameException {
        throw new UnsupportedOperationException();
    }

    @Override
    public EventView askTheViewTheInfo() {
        return null;
    }

}
