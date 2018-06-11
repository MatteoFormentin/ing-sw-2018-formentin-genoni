package it.polimi.se2018.model.effect;

import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.dice.Dice;

public class TakeDiceFromDraftPool extends EffectGame {

    private int indexDiceOfDicePool;

    public TakeDiceFromDraftPool(GameBoard gameBoard, int idPlayer, int indexDiceOfDicePool) {
        super(gameBoard, idPlayer);
        this.indexDiceOfDicePool = indexDiceOfDicePool;
    }

    @Override
    public void doEffect() throws GameException {
        gameBoard.addNewDiceToHandFromDicePool(idPlayer, indexDiceOfDicePool);
    }

    @Override
    public void undo() {
        Dice dice = gameBoard.getPlayer(idPlayer).getHandDice().remove(0);
        gameBoard.getDicePool().add(indexDiceOfDicePool, dice);
    }
}
