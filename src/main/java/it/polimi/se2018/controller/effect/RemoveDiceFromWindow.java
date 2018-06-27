package it.polimi.se2018.controller.effect;

import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.exception.effect_exception.NumberInfoWrongException;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_input.SelectCellOfWindow;
import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.dice.Dice;

/**
 * the effect that able to inset a dice
 */
public class RemoveDiceFromWindow extends EffectGame {
    private int line;
    private int column;
    private boolean trueSpecialRemoveFalseNormal;

    public RemoveDiceFromWindow(boolean trueSpecialRemoveFalseNormal) {
        this.trueSpecialRemoveFalseNormal = trueSpecialRemoveFalseNormal;
    }

    @Override
    public void doEffect(GameBoard gameBoard, int idPlayer, int[] infoMove) throws GameException {
        if (infoMove.length != 2) throw new NumberInfoWrongException();
        this.setGameBoard(gameBoard);
        this.setIdPlayer(idPlayer);
        line=infoMove[0];
        column=infoMove[1];
        gameBoard.moveDiceFromWindowPatternToHand(getIdPlayer(),line,column,trueSpecialRemoveFalseNormal);
    }

    @Override
    public void undo() throws GameException {
        Dice dice= getGameBoard().getPlayer(getIdPlayer()).removeDiceFromHand();
        getGameBoard().getPlayer(getIdPlayer()).getPlayerWindowPattern().getCell(line,column).insertDice(dice,false,false);
    }

    @Override
    public EventView eventViewToAsk() {
        return new SelectCellOfWindow();
    }
}
