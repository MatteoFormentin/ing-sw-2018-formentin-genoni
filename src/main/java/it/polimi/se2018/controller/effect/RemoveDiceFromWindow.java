package it.polimi.se2018.controller.effect;

import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.exception.effect_exception.NumberInfoWrongException;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_input.SelectCellOfWindow;
import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.dice.Dice;

/**
 * Class that manage the removing of the dice on a window pattern.
 */
public class RemoveDiceFromWindow extends EffectGame {

    private int line;
    private int column;
    private boolean trueSpecialRemoveFalseNormal;

    /**
     * Constructor.
     *
     * @param trueSpecialRemoveFalseNormal true if it's a special remove, false otherwise.
     */
    public RemoveDiceFromWindow(boolean trueSpecialRemoveFalseNormal) {
        this.trueSpecialRemoveFalseNormal = trueSpecialRemoveFalseNormal;
    }

    /**
     * Method used to applicate an effect to the game.
     *
     * @param gameBoard gameboard on when the player are playing.
     * @param idPlayer  ID of the player that requested the effect.
     * @param infoMove  information of the moves played with the effect.
     * @throws GameException exception derivate from game restriction.
     */
    @Override
    public void doEffect(GameBoard gameBoard, int idPlayer, int[] infoMove) throws GameException {
        if (infoMove.length != 2) throw new NumberInfoWrongException();
        this.setGameBoard(gameBoard);
        this.setIdPlayer(idPlayer);
        line = infoMove[0];
        column = infoMove[1];
        gameBoard.moveDiceFromWindowPatternToHand(getIdPlayer(), line, column, trueSpecialRemoveFalseNormal);
    }

    /**
     * Method used to undo the application of an effect to the game.
     *
     * @throws GameException exception derivate from game restriction.
     */
    @Override
    public void undo() throws GameException {
        Dice dice = getGameBoard().getPlayer(getIdPlayer()).removeDiceFromHand();
        getGameBoard().getPlayer(getIdPlayer()).getPlayerWindowPattern().getCell(line, column).insertDice(dice, false, false);
    }

    /**
     * Method used to ask an event view.
     *
     * @return cell of the window selected for the dice removing.
     */
    @Override
    public EventClient eventViewToAsk() {
        return new SelectCellOfWindow();
    }
}
