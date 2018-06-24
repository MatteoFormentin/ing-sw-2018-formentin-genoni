package it.polimi.se2018.model.card.window_pattern_card;

import it.polimi.se2018.exception.gameboard_exception.window_exception.*;
import it.polimi.se2018.exception.gameboard_exception.window_exception.cell_exception.*;
import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceColor;

import java.io.Serializable;

/**
 * Class that define a Cell of Window Pattern Card.
 *
 * @author Luca Genoni
 */

public class Cell implements Serializable {

    private Dice dice;
    private int valueRestriction;
    private DiceColor colorRestriction;
    public Cell(){
        dice= null;
        valueRestriction=0;
        colorRestriction=null;
    }
    public Cell(Dice dice,int valueRestriction,DiceColor colorRestriction){
        this.dice=dice;
        this.valueRestriction=valueRestriction;
        this.colorRestriction=colorRestriction;
    }
    //************************************getter**********************************************
    //************************************getter**********************************************
    //************************************getter**********************************************

    /**
     * if the value is 0 you can place a dice with any value, otherwise you must place a dice with the same value
     *
     * @return the required value to allow the insertion of the dice
     */
    public int getValueRestriction() {
        return valueRestriction;
    }

    /**
     * if the color is null you can place a dice with any color, otherwise you must place a dice with the same color
     *
     * @return the required color to allow the insertion of the dice
     */
    public DiceColor getColorRestriction() {
        return colorRestriction;
    }

    /**
     * normal get for the dice in the cell, null if there is no dice
     *
     * @return the dice in this cell
     */
    public Dice getDice() {
        return dice;
    }

    //************************************setter**********************************************
    //************************************setter**********************************************
    //************************************setter**********************************************

    /**
     * for setting a restriction of value
     *
     * @param valueRestriction a dice for being inserted here need to have this value
     */
    public void setValueRestriction(int valueRestriction) {
        this.valueRestriction = valueRestriction;
    }

    /**
     * for setting a restriction of color
     *
     * @param colorRestriction a dice for being inserted here need to have this color
     */
    public void setColorRestriction(DiceColor colorRestriction) {
        this.colorRestriction = colorRestriction;
    }

    /**
     * for setting a dice (used for the load of a game)
     *
     * @param dice to set in this cell
     */
    public void setDice(Dice dice) {
        this.dice = dice;
    }

    //************************************window's method**********************************************
    //************************************window's method**********************************************
    //************************************window's method**********************************************

    /**
     * Check if the dice is allowed based on the color restriction of the cell.
     *
     * @param color color of the dice that i wanna check
     * @return true if the dice violate the restriction, false otherwise
     */
    private boolean colorRestrictionViolated(DiceColor color) {
        return colorRestriction != null && colorRestriction != color;
    }

    /**
     * Check if the dice is allowed based on the value restriction of the cell.
     *
     * @return true if the dice violate the restriction, false otherwise
     */
    private boolean valueRestrictionViolated(int value) {
        return valueRestriction != 0 && valueRestriction != value;
    }

    /**
     * Insert the dice in the cell if the restriction of the cell are respected.
     *
     * @param newDice                  to insert in the cell
     * @param checkColorRestriction true if i need to check the restriction
     * @param checkValueRestriction true if i need to check the restriction
     */
    public void insertDice(Dice newDice, boolean checkColorRestriction, boolean checkValueRestriction)
            throws WindowRestriction {
        if (newDice==null) throw new NullDiceToAddException();
        if (dice != null) throw new RestrictionCellOccupiedException();
        if (checkColorRestriction && colorRestrictionViolated(newDice.getColor())) throw new RestrictionCellColorViolatedException();
        if (checkValueRestriction && valueRestrictionViolated(newDice.getValue())) throw new RestrictionCellValueViolatedException();
        this.dice = newDice;
    }

    /**
     * remove the dice from the Cell
     *
     * @return the dice removed
     */
    public Dice removeDice() throws NoDiceInThisCell {
        if (this.dice == null) throw new NoDiceInThisCell();
        Dice diceToRemove = this.dice;
        this.dice = null;
        return diceToRemove;
    }
}
