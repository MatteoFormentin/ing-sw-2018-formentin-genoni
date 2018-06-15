package it.polimi.se2018.controller.effect;

import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.exception.effect_exception.NumberInfoWrongException;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.dice.Dice;

public class TakeDiceFromDraftPool extends EffectGame {

    private int indexDiceOfDicePool;

    public TakeDiceFromDraftPool(int indexDiceOfDicePool) {
        this.indexDiceOfDicePool = indexDiceOfDicePool;
    }

    @Override
    public void doEffect(GameBoard gameBoard, int idPlayer, int[] infoMove) throws GameException {
        if (infoMove.length != 1) throw new NumberInfoWrongException();
        this.setGameBoard(gameBoard);
        this.setIdPlayer(idPlayer);
        indexDiceOfDicePool= infoMove[0];
        gameBoard.addNewDiceToHandFromDicePool(idPlayer, indexDiceOfDicePool);
    }

    @Override
    public void undo() throws GameException {
        Dice dice = getGameBoard().getPlayer(getIdPlayer()).getHandDice().remove(0);
        getGameBoard().getDicePool().add(indexDiceOfDicePool, dice);
    }

    @Override
    public EventView askTheViewTheInfo() {
        return null;
    }
}
