package it.polimi.se2018.model.effect;

import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
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
    public void doEffect(GameBoard gameBoard, int idPlayer) throws GameException {
        if(specialRemove) gameBoard.moveDiceFromWindowPatternToHandWithRestriction(idPlayer,line,line,line,column);
        else {
            Dice dice =gameBoard.getPlayer(idPlayer).getPlayerWindowPattern().removeDice(line,column);
            gameBoard.getPlayer(idPlayer).getHandDice().addFirst(dice);
        }
    }

    @Override
    public void undo(GameBoard gameBoard, int idPlayer) throws GameException {
        gameBoard.insertDice(idPlayer,line,column,false,false,false,false);
    }

    @Override
    public EventView askTheViewTheInfo() {
        return null;
    }
}
