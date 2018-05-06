package it.polimi.se2018.model.card.windowPatternCard;

import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceColor;
/**
 * <strong>Class WindowPatternCard</strong>
 * <em>Description</em>
 * Class that define the Window Pattern Card.
 *
 * @author Davide Mammarella
 * @version 1.0
 * @since 1.0
 */

public class WindowPatternCard {

    private int ID;
    private String name;
    private int level;
    private int cellValue;
    private DiceColor cellColor;
    private Cell[][] Matrix;

    public WindowPatternCard() {
        Matrix = new Cell[5][4];
    }

    /**
     * Method <strong>getName</strong>
     * <em>Description</em>
     * Get the name of the Window Pattern Card.
     */
    public String getName() {
        return name;
    }

    /**
     * Method <strong>setName</strong>
     * <em>Description</em>
     * Set a name for the Window Pattern Card.
     *
     * @param name assigned to the Window Pattern Card
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method <strong>getLevel</strong>
     * <em>Description</em>
     * Get the level of the Window Pattern Card.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Method <strong>setLevel</strong>
     * <em>Description</em>
     * Set the level of the Window Pattern Card.
     *
     * @param level assigned to the Window Pattern Card
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Method <strong>getCell</strong>
     * <em>Description</em>
     * Get a cell of the Window Pattern Card.
     *
     * @param line   used as a cell reference
     * @param column used as a cell reference
     * @return the cell referenced to line and column
     */
    public Cell getCell(int line, int column) {
        return Matrix[line][column];
    }

    /**
     * Method <strong>checkPreExistingDice</strong>
     * <em>Description</em>
     * Check if there is a dice in a selected cell of the window pattern card.
     *
     * @return true if the restriction is verified, false if the restriction is not verified
     */
    public boolean checkPreExistingDice(int line, int column) {
        cellColor = Matrix[line][column].getCellColor();
        cellValue = Matrix[line][column].getCellValue();

        if (cellColor != null && cellValue != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method <strong>checkMatrixAdjacentRestriction</strong>
     * <em>Description</em>
     * Check if the dice is allowed based on the restriction imposed from the adjacent cell.
     *
     * @return true if the restriction is verified, false if the restriction is not verified
     */
    public boolean checkMatrixAdjacentRestriction(int line, int column) {
        if (Matrix[line][column-1].getCellColor()!=null && Matrix[line][column-1].getCellValue()!=0){
            return true;
        } else if (Matrix[line][column+1].getCellColor()!=null && Matrix[line][column+1].getCellValue()!=0){
            return true;
        } else if (Matrix[line-1][column].getCellColor()!=null && Matrix[line-1][column].getCellValue()!=0){
            return true;
        } else if (Matrix[line-1][column-1].getCellColor()!=null && Matrix[line-1][column-1].getCellValue()!=0){
            return true;
        } else if(Matrix[line-1][column+1].getCellColor()!=null && Matrix[line-1][column+1].getCellValue()!=0){
            return true;
        }else if (Matrix[line+1][column].getCellColor()!=null && Matrix[line+1][column].getCellValue()!=0){
            return true;
        } else if (Matrix[line+1][column-1].getCellColor()!=null && Matrix[line+1][column-1].getCellValue()!=0){
            return true;
        } else if(Matrix[line+1][column+1].getCellColor()!=null && Matrix[line+1][column+1].getCellValue()!=0){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method <strong>checkMatrixColorRestriction</strong>
     * <em>Description</em>
     * Check if the dice is allowed based on the color restriction of the matrix.
     *
     * @return true if the restriction is verified, false if the restriction is not verified
     */
    public boolean checkMatrixColorRestriction(int line, int column, DiceColor diceColor) {
        if(diceColor == Matrix[line][column-1].getCellColor()){
            return true;
        }
        else if (diceColor == Matrix[line][column+1].getCellColor()){
            return true;
        }
        else if (diceColor == Matrix[line-1][column].getCellColor()){
            return true;
        else if (diceColor == Matrix[line+1][column].getCellColor()){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method <strong>checkMatrixValueRestriction</strong>
     * <em>Description</em>
     * Check if the dice is allowed based on the value restriction of the matrix.
     *
     * @return true if the restriction is verified, false if the restriction is not verified
     */
    public boolean checkMatrixValueRestriction(int line, int column, int diceValue) {
        if(diceValue == Matrix[line][column-1].getCellValue()){
            return true;
        }
        else if (diceValue == Matrix[line][column+1].getCellValue()){
            return true;
        }
        else if (diceValue == Matrix[line-1][column].getCellValue()){
            return true;
        }
        else if (diceValue == Matrix[line+1][column].getCellValue()){
            return true;
        } else {
            return false;
        }
    }

    public boolean insertDice(int line, int column, Dice Dice) {
        return false;
    }

    public boolean insertDice(int line, int column, Dice Dice, boolean colorR, boolean adjacentR, boolean valueR) {
        return false;
    }

}
