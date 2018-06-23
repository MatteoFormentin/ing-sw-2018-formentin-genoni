package it.polimi.se2018.controller.effect;

import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.exception.effect_exception.NumberInfoWrongException;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_input.SelectDiceFromDraftPool;
import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.dice.Dice;

public class FactoryEffect extends EffectGame {

    @Override
    public void doEffect(GameBoard gameBoard, int idPlayer, int[] infoMove) throws GameException {
        if (infoMove != null) throw new NumberInfoWrongException();
        this.setGameBoard(gameBoard);
        this.setIdPlayer(idPlayer);
        gameBoard.changeDiceBetweenHandAndFactory(getIdPlayer());
    }

    @Override
    public void undo() throws GameException {
        throw new UnsupportedOperationException();
    }

    @Override
    public EventView eventViewToAsk() {
        return null;
    }
}
