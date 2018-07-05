package it.polimi.se2018.controller.effect;

import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.exception.effect_exception.NumberInfoWrongException;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_input.SelectCellOfWindow;
import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.dice.Dice;

/**
 * Class that manage the inserting of the dice on a window pattern.
 */
public class InsertDice extends EffectGame {
    private int line;
    private int column;
    private boolean adjacentR;
    private boolean colorR;
    private boolean valueR;
    private boolean firstDieOfTheTurn;

    /**
     * Constructor.
     *
     * @param adjacentR true if need to be near a dice, false otherwise.
     * @param colorR true if need to check this restriction, false otherwise.
     * @param valueR true if need to check this restriction, false otherwise.
     * @param firstDieOfTheTurn true if it's the first dice of the turn, false otherwise.
     */
    public InsertDice(boolean adjacentR, boolean colorR, boolean valueR, boolean firstDieOfTheTurn) {
        this.adjacentR = adjacentR;
        this.colorR = colorR;
        this.valueR = valueR;
        this.firstDieOfTheTurn = firstDieOfTheTurn;
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
        if (infoMove.length != 2) throw new NumberInfoWrongException();
        this.setGameBoard(gameBoard);
        this.setIdPlayer(idPlayer);
        line=infoMove[0];
        column=infoMove[1];
        getGameBoard().insertDice(getIdPlayer(),line,column,adjacentR,colorR,valueR, firstDieOfTheTurn);
    }

    /**
     * Method used to undo the application of an effect to the game.
     *
     * @throws GameException exception derivate from game restriction.
     */
    @Override
    public void undo() throws GameException {
        Dice dice =getGameBoard().getPlayer(getIdPlayer()).getPlayerWindowPattern().removeDice(line,column);
        getGameBoard().getPlayer(getIdPlayer()).getHandDice().addFirst(dice);
    }

    /**
     * Method used to ask an event view.
     *
     * @return cell of the window for the dice inserting.
     */
    @Override
    public EventClient eventViewToAsk() {
        return new SelectCellOfWindow();
    }

}
