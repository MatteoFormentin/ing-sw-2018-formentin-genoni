package it.polimi.se2018.model.effect;

import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.dice.Dice;

public class SwapADie extends EffectGame {

    private int round;
    private int index;
    private boolean trueRoundFalseFactory;

    public SwapADie(boolean trueRoundFalseFactory) {
        this.trueRoundFalseFactory = trueRoundFalseFactory;
    }

    @Override
    public void doEffect(GameBoard gameBoard, int idPlayer) throws GameException {
        if(trueRoundFalseFactory);
    }

    @Override
    public void undo(GameBoard gameBoard, int idPlayer) throws GameException {
        if(trueRoundFalseFactory){

        }else{
            Dice dice = gameBoard.getPlayer(idPlayer).getHandDice().remove(0);
            gameBoard.getDicePool().add(index,dice);
        }

    }

    @Override
    public EventView askTheViewTheInfo() {
        return null;
    }
}
