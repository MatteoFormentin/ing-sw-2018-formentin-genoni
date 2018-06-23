package it.polimi.se2018.controller.effect;

import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.exception.effect_exception.NumberInfoWrongException;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_input.SelectDiceFromRoundTrack;
import it.polimi.se2018.model.GameBoard;

public class RoundTrackEffect extends EffectGame {

    private int round;
    private int index;
    private boolean trueSwapDiceFalseSetColorRestriction;

    public RoundTrackEffect(boolean trueSwapDiceFalseSetColorRestriction) {
        this.trueSwapDiceFalseSetColorRestriction = trueSwapDiceFalseSetColorRestriction;
    }

    @Override
    public void doEffect(GameBoard gameBoard, int idPlayer, int[] infoMove) throws GameException {
        if (infoMove.length != 2) throw new NumberInfoWrongException();
        this.setGameBoard(gameBoard);
        this.setIdPlayer(idPlayer);
        round=infoMove[0];
        index=infoMove[1];
        if(trueSwapDiceFalseSetColorRestriction){
            gameBoard.changeDiceBetweenHandAndRoundTrack(getIdPlayer(),round,index);
        }else{
            gameBoard.imposeColorRestriction(getIdPlayer(),round,index);
        }
    }

    @Override
    public void undo() throws GameException {
        throw new UnsupportedOperationException();

    }

    @Override
    public EventView eventViewToAsk() {
        return new SelectDiceFromRoundTrack();
    }
}
