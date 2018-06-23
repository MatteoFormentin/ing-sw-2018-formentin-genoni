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
public class InsertDice extends EffectGame {
    private int line;
    private int column;
    private boolean adjacentR;
    private boolean colorR;
    private boolean valueR;
    private boolean firstDieOfTheTurn;

    public InsertDice(boolean adjacentR, boolean colorR, boolean valueR, boolean firstDieOfTheTurn) {
        this.adjacentR = adjacentR;
        this.colorR = colorR;
        this.valueR = valueR;
        this.firstDieOfTheTurn = firstDieOfTheTurn;
    }

    @Override
    public void doEffect(GameBoard gameBoard, int idPlayer, int[] infoMove) throws GameException {
        if (infoMove.length != 2) throw new NumberInfoWrongException();
        this.setGameBoard(gameBoard);
        this.setIdPlayer(idPlayer);
        line=infoMove[0];
        column=infoMove[1];
        getGameBoard().insertDice(getIdPlayer(),line,column,adjacentR,colorR,valueR, firstDieOfTheTurn);
    }

    @Override
    public void undo() throws GameException {
        Dice dice =getGameBoard().getPlayer(getIdPlayer()).getPlayerWindowPattern().removeDice(line,column);
        getGameBoard().getPlayer(getIdPlayer()).getHandDice().addFirst(dice);
    }

    @Override
    public EventView eventViewToAsk() {
        return new SelectCellOfWindow();
    }

}
