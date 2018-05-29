package it.polimi.se2018.model.card.window_pattern_card;

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
    private int valueRestriction = 0;
    private DiceColor colorRestriction;
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
     * @return true if the dice respect the restriction so it can be insert, false otherwise
     */
    private boolean checkColorRestriction(DiceColor color) {
        return colorRestriction == null || colorRestriction == color;
    }

    /**
     * Check if the dice is allowed based on the value restriction of the cell.
     *
     * @return true if the dice respect the restriction so it can be insert, false otherwise
     */
    private boolean checkValueRestriction(int value) {
        return valueRestriction == 0 || valueRestriction == value;
    }

    /**
     * Insert the dice in the cell if the restriction of the cell are respected.
     *
     * @param dice to insert
     */
    public boolean insertDice(Dice dice) {
        if (this.dice != null) return false;// cell occupied
        if (checkValueRestriction(dice.getValue()) && checkColorRestriction(dice.getColor())) {
            this.dice = dice;
            return true;
        }
        return false;//cell restricted
    }

    /**
     * Insert the dice in the cell if the restriction of the cell are respected.
     *
     * @param dice             to insert in the cell
     * @param colorRestriction true if i need to check the restriction
     * @param valueRestriction true if i need to check the restriction
     */
    public boolean insertDice(Dice dice, boolean colorRestriction, boolean valueRestriction){
        if (this.dice != null) return false;// cell occupied
        if (colorRestriction) if(!checkColorRestriction(dice.getColor())) return false;//color restriction
        if (valueRestriction) if(!checkValueRestriction(dice.getValue())) return false;//value restriction
        this.dice = dice;
        return true;
    }

    /**
     * remove the dice from the Cell
     *
     * @return the dice removed
     */
    public Dice removeDice(){
        Dice dice= this.dice;
        this.dice=null;
        return dice;
    }
}
