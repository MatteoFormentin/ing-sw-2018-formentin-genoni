package it.polimi.se2018.model.effect;

import it.polimi.se2018.exception.GameException;
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
    private boolean firstDieOfTheTrun;

    /**
     * Constructor for the normal insert dice
     *
     * @param gameBoard the Game board where to apply the effect
     * @param idPlayer  the id of the player who request the Action
     * @param line   the line of the window
     * @param column    the column of the window
     */
    public InsertDice(GameBoard gameBoard, int idPlayer, int line, int column) {
        super(gameBoard, idPlayer);
        this.line = line;
        this.column = column;
        this.adjacentR = true;
        this.colorR = true;
        this.valueR = true;
        this.firstDieOfTheTrun= true;
    }
    /**
     * Constructor for the normal insert dice
     *
     * @param gameBoard the Game board where to apply the effect
     * @param idPlayer  the id of the player who request the Action
     * @param line the line of the window
     * @param column the column of the window
     * @param adjacentR true if need to be respected, false otherwise
     * @param colorR true if need to be respected, false otherwise
     * @param valueR true if need to be respected, false otherwise
     */
    public InsertDice(GameBoard gameBoard, int idPlayer, int line, int column, boolean adjacentR, boolean colorR, boolean valueR,boolean firstDieOfTheTrun) {
        super(gameBoard, idPlayer);
        this.line = line;
        this.column = column;
        this.adjacentR = adjacentR;
        this.colorR = colorR;
        this.valueR = valueR;
        this.firstDieOfTheTrun= firstDieOfTheTrun;
    }

    @Override
    public void doEffect() throws GameException {
        gameBoard.insertDice(idPlayer,line,column,adjacentR,colorR,valueR,firstDieOfTheTrun);
    }

    @Override
    public void undo() throws GameException{
        Dice dice =gameBoard.getPlayer(idPlayer).getPlayerWindowPattern().removeDice(line,column);
        gameBoard.getPlayer(idPlayer).getHandDice().addFirst(dice);
    }

}
