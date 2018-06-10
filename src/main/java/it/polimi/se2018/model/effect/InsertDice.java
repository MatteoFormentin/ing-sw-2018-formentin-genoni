package it.polimi.se2018.model.effect;

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

    /**
     * Constructor for the normal insert dice
     *
     * @param gameBoard the Game board for make the move
     * @param idPlayer
     * @param line
     * @param column
     */
    public InsertDice(GameBoard gameBoard, int idPlayer, int line, int column) {
        super(gameBoard, idPlayer);
        this.line = line;
        this.column = column;
        this.adjacentR = true;
        this.colorR = true;
        this.valueR = true;
    }

    /**
     * Constructor for a tool's insert dice
     *
     * @param gameBoard
     * @param idPlayer
     * @param line
     * @param column
     * @param adjacentR
     * @param colorR
     * @param valueR
     */
    public InsertDice(GameBoard gameBoard, int idPlayer, int line, int column, boolean adjacentR, boolean colorR, boolean valueR) {
        super(gameBoard, idPlayer);
        this.line = line;
        this.column = column;
        this.adjacentR = adjacentR;
        this.colorR = colorR;
        this.valueR = valueR;
    }

    @Override
    public void doEffect() throws Exception {
        gameBoard.insertDice(idPlayer,line,column,adjacentR,colorR,valueR);
    }

    @Override
    public void undo(){
        Dice dice =gameBoard.getPlayer(idPlayer).getPlayerWindowPattern().removeDice(line,column);
        gameBoard.getPlayer(idPlayer).getHandDice().addFirst(dice);
    }

}
