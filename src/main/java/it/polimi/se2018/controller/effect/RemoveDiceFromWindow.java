package it.polimi.se2018.controller.effect;

import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.exception.effect_exception.NumberInfoWrongException;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.SelectCellOfWindow;
import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.dice.Dice;

/**
 * the effect that able to inset a dice
 */
public class RemoveDiceFromWindow extends EffectGame {
    private int line;
    private int column;
    private boolean specialRemove;

    public RemoveDiceFromWindow(boolean specialRemove) {
        this.specialRemove = specialRemove;
    }

    @Override
    public void doEffect(GameBoard gameBoard, int idPlayer, int[] infoMove) throws GameException {
        if (infoMove.length != 2) throw new NumberInfoWrongException();
        this.setGameBoard(gameBoard);
        this.setIdPlayer(idPlayer);
        line=infoMove[0];
        column=infoMove[1];
        if(specialRemove) gameBoard.moveDiceFromWindowPatternToHand(getIdPlayer(),line,column,specialRemove);
        else {
            Dice dice =gameBoard.getPlayer(getIdPlayer()).getPlayerWindowPattern().removeDice(line,column);
            gameBoard.getPlayer(getIdPlayer()).getHandDice().addFirst(dice);
        }
    }

    @Override
    public void undo() throws GameException {
        getGameBoard().insertDice(getIdPlayer(),line,column,false,false,false,false);
    }

    @Override
    public EventView askTheViewTheInfo() {
        return new SelectCellOfWindow();
    }
}
