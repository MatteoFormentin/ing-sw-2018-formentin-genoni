package it.polimi.se2018.controller.effect;

import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.exception.effect_exception.NumberInfoWrongException;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_input.SelectDiceFromDraftPool;
import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.dice.Dice;

public class DicePoolEffect extends EffectGame {

    private int indexDiceOfDicePool;
    private boolean trueDrawDieFalseRollDice;

    public DicePoolEffect(boolean trueDrawDieFalseRollDice) {
        this.trueDrawDieFalseRollDice = trueDrawDieFalseRollDice;
    }

    @Override
    public void doEffect(GameBoard gameBoard, int idPlayer, int[] infoMove) throws GameException {
        if(trueDrawDieFalseRollDice){
            if (infoMove.length != 1) throw new NumberInfoWrongException();
            this.setGameBoard(gameBoard);
            this.setIdPlayer(idPlayer);
            indexDiceOfDicePool= infoMove[0];
            gameBoard.addNewDiceToHandFromDicePool(idPlayer, indexDiceOfDicePool);
        }else{
            if (infoMove !=null) throw new NumberInfoWrongException();
            this.setGameBoard(gameBoard);
            this.setIdPlayer(idPlayer);
            gameBoard.rollDicePool(idPlayer);
        }
    }

    @Override
    public void undo() throws GameException {
        Dice dice = getGameBoard().getPlayer(getIdPlayer()).getHandDice().remove(0);
        getGameBoard().getDicePool().add(indexDiceOfDicePool, dice);
    }

    @Override
    public EventView eventViewToAsk() {
        if(trueDrawDieFalseRollDice) return new SelectDiceFromDraftPool();
        return null;
    }
}
