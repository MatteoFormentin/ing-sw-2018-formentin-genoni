package it.polimi.se2018.model.effect;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
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
    public void doEffect(GameBoard gameBoard, int idPlayer) throws GameException {
        gameBoard.insertDice(idPlayer,line,column,adjacentR,colorR,valueR, firstDieOfTheTurn);
    }

    @Override
    public void undo(GameBoard gameBoard, int idPlayer) throws GameException {
        Dice dice =gameBoard.getPlayer(idPlayer).getPlayerWindowPattern().removeDice(line,column);
        gameBoard.getPlayer(idPlayer).getHandDice().addFirst(dice);
    }

    @Override
    public EventView askTheViewTheInfo() {
        return null;
    }

}
