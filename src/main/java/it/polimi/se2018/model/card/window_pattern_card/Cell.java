package it.polimi.se2018.model.card.window_pattern_card;

import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceColor;

/**
 * Class that define a Cell of Window Pattern Card.
 *
 * @author Luca Genoni
 */

public class Cell {

    private Dice dice;
    private int valueRestriction = 0;
    private DiceColor colorRestriction;


    /**
     * Get the value restriction of the cell.
     */
    public int getValueRestriction() {
        return valueRestriction;
    }

    /**
     * Set the value restriction of the cell.
     *
     * @param valueRestriction restriction assigned to the cell
     */
    public void setValueRestriction(int valueRestriction) {
        this.valueRestriction = valueRestriction;
    }

    /**
     * Get the color restriction of the cell.
     */
    public DiceColor getColorRestriction() {
        return colorRestriction;
    }

    /**
     * Set the color restriction of the cell.
     *
     * @param colorRestriction restriction assigned to the cell
     */
    public void setColorRestriction(DiceColor colorRestriction) {
        this.colorRestriction = colorRestriction;
    }

    /**
     * Get the dice from the cell.
     */
    public Dice getDice() {
        return dice;
    }

    /**
     * Set the dice on the cell.
     *
     * @param dice that i want to set
     */
    public void setDice(Dice dice) {
        this.dice = dice;
    }

    /**
     * Check if the dice is allowed based on the color restriction of the cell.
     *
     * @param color color of the dice that i wanna check
     * @return true if the dice respect the restriction so it can be insert, false otherwise
     */
    private boolean checkColorRestriction(DiceColor color) {
        if (colorRestriction == null) return true;
        else if (colorRestriction == color) return true;
        else return false;
    }

    /**
     * Check if the dice is allowed based on the value restriction of the cell.
     *
     * @return true if the dice respect the restriction so it can be insert, false otherwise
     */
    private boolean checkValueRestriction(int value) {
        if (valueRestriction == 0) return true;
        else if (valueRestriction == value) return true;
        else return false;
    }

    /**
     * Insert the dice in the cell if the restriction of the cell are respected.
     *
     * @param dice to insert
     * @throws Exception can't place the dice
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
     * @throws Exception can't insert the dice
     */
    public boolean insertDice(Dice dice, boolean colorRestriction, boolean valueRestriction){
        if (this.dice != null) return false;// cell occupied
        if (colorRestriction) if(!checkColorRestriction(dice.getColor())) return false;//color restriction
        if (valueRestriction) if(!checkValueRestriction(dice.getValue())) return false;//value restriction
        this.dice = dice;
        return true;
    }
}
