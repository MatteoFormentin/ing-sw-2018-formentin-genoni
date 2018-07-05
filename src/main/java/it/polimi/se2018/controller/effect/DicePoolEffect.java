package it.polimi.se2018.controller.effect;

import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.exception.effect_exception.NumberInfoWrongException;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_input.SelectDiceFromDraftPool;
import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.dice.Dice;

/**
 * Class that manage the effect of the dice pool.
 */
public class DicePoolEffect extends EffectGame {

    private int indexDiceOfDicePool;
    private boolean trueDrawDieFalseRollDice;

    /**
     * Constructor.
     *
     * @param trueDrawDieFalseRollDice true draw dice on the pool, false roll dice instead of drawn it.
     */
    public DicePoolEffect(boolean trueDrawDieFalseRollDice) {
        this.trueDrawDieFalseRollDice = trueDrawDieFalseRollDice;
    }

    /**
     * Method used to applicate an effect to the game.
     *
     * @param gameBoard gameboard on when the player are playing.
     * @param idPlayer ID of the player that requested the effect.
     * @param infoMove information of the moves played with the effect.
     * @throws GameException exception derivate from game restriction.
     */
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

    /**
     * Method used to undo the application of an effect to the game.
     *
     * @throws GameException exception derivate from game restriction.
     */
    @Override
    public void undo() throws GameException {
        Dice dice = getGameBoard().getPlayer(getIdPlayer()).getHandDice().remove(0);
        getGameBoard().getDicePool().add(indexDiceOfDicePool, dice);
    }

    /**
     * Method used to ask an event view.
     *
     * @return null cause these effect don't implying an event view ask.
     */
    @Override
    public EventClient eventViewToAsk() {
        if(trueDrawDieFalseRollDice) return new SelectDiceFromDraftPool();
        return null;
    }
}
