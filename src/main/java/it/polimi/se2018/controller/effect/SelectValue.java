package it.polimi.se2018.controller.effect;

import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.exception.effect_exception.NumberInfoWrongException;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_input.SelectIncrementOrDecreaseDice;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_input.SelectValueDice;
import it.polimi.se2018.model.GameBoard;

public class SelectValue extends EffectGame {

    private int valueDice;
    private boolean trueSetValueFalseIncrementDec;

    public SelectValue(boolean trueSetValueFalseIncrementDec) {
        this.trueSetValueFalseIncrementDec = trueSetValueFalseIncrementDec;
    }

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

    @Override
    public void undo() throws GameException {
        throw new UnsupportedOperationException();
    }

    @Override
    public EventView askTheViewTheInfo() {
        if(trueSetValueFalseIncrementDec) return new SelectValueDice();
        else return new SelectIncrementOrDecreaseDice();
    }
}
