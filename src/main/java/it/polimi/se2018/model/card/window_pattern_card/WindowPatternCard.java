package it.polimi.se2018.model.card.window_pattern_card;

import it.polimi.se2018.exception.WindowException.*;
import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceColor;

import java.io.Serializable;

/**
 * Class that define the Window Pattern Card.
 *
 * @author Luca Genoni
 */
public class WindowPatternCard implements Serializable {
    private String name;
    private int difficulty;
    private Cell[][] matrix;
    private int numberOfCellWithDice;
    /* set/get matrix name level
        insertDice controlla restrizioni adiacenza (al suo interno controlla restrizione cella
        insertDice controlla 3 boolean per 3 restrizioni
     */

    /**
     * Create a WindowPatternCard given the name, difficulty and the matrix.
     *
     * @param name       of the Window Pattern Card
     * @param difficulty of the Window Pattern Card
     * @param matrix     of Cell of the Window Pattern Card
     */
    public WindowPatternCard(String name, int difficulty, Cell[][] matrix) {
        this.name = name;
        this.difficulty = difficulty;
        this.matrix = matrix;
        this.numberOfCellWithDice = 0;
    }

    public WindowPatternCard() {
        this.name = null;
        this.difficulty = 0;
        this.matrix = null;
        this.numberOfCellWithDice = 0;
    }
    //************************************getter**********************************************
    //************************************getter**********************************************
    //************************************getter**********************************************

    public String getName() {
        return name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public Cell[][] getMatrix() {
        return matrix;
    }

    public Cell[] getColumn(int line){
        return this.matrix[line];
    }

    public Cell getCell(int line, int column) {
        return matrix[line][column];
    }

    public int getNumberOfCellWithDice() {
        return numberOfCellWithDice;
    }

    //************************************setter**********************************************
    //************************************setter**********************************************
    //************************************setter**********************************************
    void setName(String name) {
        this.name = name;
    }

    void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    void setMatrix(Cell[][] matrix) {
        this.matrix = matrix;
    }
    //************************************window's method**********************************************
    //************************************window's method**********************************************
    //************************************window's method**********************************************

    /**
     * Check if the dice is allowed based on the restriction imposed from the adjacent cell.
     *
     * @param column of where you want to put the dice
     * @param line   of where you want to put the dice
     * @return true if the dice respect the restriction so it can be insert, false otherwise
     */

    private boolean checkMatrixAdjacentRestriction(int line, int column) {
        if (numberOfCellWithDice == 0) {
            return (column == 0 || column == 4 || line == 0 || line == 3);
        } else {
            // one if for each near cell so 8 if
            if (line != 0 && column != 0) if (matrix[line - 1][column - 1].getDice() != null) return true;
            if (line != 0) if (matrix[line - 1][column].getDice() != null) return true;
            if (line != 0 && column != 4) if (matrix[line - 1][column + 1].getDice() != null) return true;
            if (column != 0) if (matrix[line][column - 1].getDice() != null) return true;
            //if(matrix[line][column].get()!=null) return false; occupided
            if (column != 4) if (matrix[line][column + 1].getDice() != null) return true;
            if (line != 3 && column != 0) if (matrix[line + 1][column - 1].getDice() != null) return true;
            if (line != 3) if (matrix[line + 1][column].getDice() != null) return true;
            if (line != 3 && column != 4) return matrix[line + 1][column + 1].getDice() != null;
        }
        return false;
    }

    /**
     * Check if the dice is allowed based on the color restriction of the matrix.
     *
     * @param column of where you want to put the dice
     * @param line   of where you want to put the dice
     * @return true if the dice respect the restriction so it can be insert, false otherwise
     */
    private boolean checkMatrixAdjacentColorRestriction(int line, int column, DiceColor diceColor) {
        //
        if (column != 0) if (matrix[line][column - 1].getDice() != null)
            if (matrix[line][column - 1].getDice().getColor() == diceColor) return false;
        if (column != 4) if (matrix[line][column + 1].getDice() != null)
            if (matrix[line][column + 1].getDice().getColor() == diceColor) return false;
        if (line != 0) if (matrix[line - 1][column].getDice() != null)
            if (matrix[line - 1][column].getDice().getColor() == diceColor) return false;
        if (line != 3) if (matrix[line + 1][column].getDice() != null)
            return matrix[line + 1][column].getDice().getColor() != diceColor;
        return true;
    }

    /**
     * Check if the dice is allowed based on the value restriction of the matrix.
     *
     * @param column of where you want to put the dice
     * @param line   of where you want to put the dice
     * @return true if the dice respect the restriction so it can be insert, false otherwise
     */
    private boolean checkMatrixAdjacentValueRestriction(int line, int column, int diceValue) {
        if (column != 0) if (matrix[line][column - 1].getDice() != null)
            if (matrix[line][column - 1].getDice().getValue() == diceValue) return false;
        if (column != 4) if (matrix[line][column + 1].getDice() != null)
            if (matrix[line][column + 1].getDice().getValue() == diceValue) return false;
        if (line != 0) if (matrix[line - 1][column].getDice() != null)
            if (matrix[line - 1][column].getDice().getValue() == diceValue) return false;
        if (line != 3) if (matrix[line + 1][column].getDice() != null)
            return matrix[line + 1][column].getDice().getValue() != diceValue;
        return true;
    }

    /**
     * InsertDiceController for the player or all the restriction on.
     *
     * @param line   index [0,3] of the WindowsPattern
     * @param column index [0,4] of the WindowsPattern
     * @param dice   to insert in the WindowsPattern
     * @return true if the insertDice is ok, false if can't insert the dice
     */
    public void insertDice(int line, int column, Dice dice) throws RestrictionCellOccupiedException,RestrictionValueViolatedException,
            RestrictionColorViolatedException,RestrictionAdjacentViolatedException {
     //   if (!(line >= 0 && line < matrix.length && column >= 0 && column < matrix[0].length)) return false;
        if (!checkMatrixAdjacentRestriction(line, column)) throw new RestrictionAdjacentViolatedException();
        if (!checkMatrixAdjacentColorRestriction(line, column, dice.getColor())) throw new RestrictionColorViolatedException();
        if (!checkMatrixAdjacentValueRestriction(line, column, dice.getValue()))throw new RestrictionValueViolatedException();
        matrix[line][column].insertDice(dice); //can throws exceptions
        numberOfCellWithDice++;
    }

    /**
     * InsertDiceController for the tool card or to negate some restriction.
     *
     * @param line                index [0,3] of the WindowsPattern
     * @param column              index [0,4] of the WindowsPattern
     * @param dice                to insert in the WindowsPattern
     * @param colorRestriction    true if the Restriction need to be respected, false if it is ignored:
     *                            colorRestriction->the dice respect the restriction, == !valueRestriction || respect the restriction
     *                            if this logic produce true can insert the dice
     * @param valueRestriction    true if the Restriction need to be respected, false if it is ignored:
     *                            valueRestriction->the dice respect the restriction, == !valueRestriction || respect the restriction
     *                            if this logic produce true can insert the dice
     * @param adjacentRestriction true if the Restriction need to be respected, false if the Restriction need to be violated
     *                            adjacentRestriction==the dice respect the restriction
     *                            if this logic produce true can insert the dice
     * @return true if the insertDice is ok, false if can't insert the dice
     */

    public boolean insertDice(int line, int column, Dice dice, boolean adjacentRestriction,
                              boolean colorRestriction, boolean valueRestriction) {
        //se devo controllare la restrizione di colore
        if (!(line >= 0 && line < 4 && column >= 0 && column < 5)) return false;
        if (adjacentRestriction) {
            if (!checkMatrixAdjacentRestriction(line, column)) return false;
        }
        //altrimenti non deve avere dadi vicini (all'inizio del turno posso piazzarlo dove voglio anche in mezzo o ai bordi)
        else if (numberOfCellWithDice != 0)
            if (!checkMatrixAdjacentRestriction(line, column)) return false; // ha qualche dado vicino
        //se deve controllare il colore
        if (colorRestriction) if (!checkMatrixAdjacentColorRestriction(line, column, dice.getColor()))
            return false; //se check è false è eccezione
        //se deve controllare il valore
        if (valueRestriction) if (!checkMatrixAdjacentValueRestriction(line, column, dice.getValue()))
            return false; // se check è false è eccezioni
        //if All the restriction of the WindowPatter are ok then call the special insert dice of the cell
        if (!matrix[line][column].insertDice(dice, colorRestriction, valueRestriction)) return false;
        numberOfCellWithDice++;
        return true;
        //else there is an exception insertDice can throw the exception of the cell

    }

    /**
     * remove a dice from the windowPattern
     *
     * @param line   of the cell
     * @param column of the cell
     * @return the dice removed from the dell
     */
    public Dice removeDice(int line, int column) {
        if (!(line >= 0 && line < matrix.length && column >= 0 && column < matrix[0].length)) return null;
        Dice dice = matrix[line][column].removeDice();
        numberOfCellWithDice--;
        return dice;
    }

}