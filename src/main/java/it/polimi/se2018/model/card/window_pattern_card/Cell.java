package it.polimi.se2018.model.card.window_pattern_card;

import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceColor;

/**
 * Class that define a Cell of Window Pattern Card.
 *
 * @author Davide Mammarella
 */

public class Cell {

    private Dice dice;
    private int valueRestriction;
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
    boolean checkColorRestriction(DiceColor color) {
        return (color!=colorRestriction);
    }

    /**
     * Check if the dice is allowed based on the value restriction of the cell.
     *
     * @return true if the dice respect the restriction so it can be insert, false otherwise
     */
    boolean checkValueRestriction(int value) {
        return (value!=valueRestriction);
    }

    /**
     * Insert the dice in the cell if the restriction of the cell are respected.
     *
     * @param dice to insert
     * @throws Exception can't place the dice
     */
    public void insertDice(Dice dice) throws Exception {
        if (this.dice!=null) throw new Exception();// cell occupied
        if (checkColorRestriction(dice.getColor()) && checkValueRestriction(dice.getValue())){
            this.dice=dice;
            return;
        }
        throw new Exception();//cell restricted
    }

    /**
     * Insert the dice in the cell if the restriction of the cell are respected.
     *
     * @param dice to insert in the cell
     * @param colorRestriction true if i need to check the restriction
     * @param valueRestriction true if i need to check the restriction
     * @throws Exception can't insert the dice
     */
    public void insertDice(Dice dice, boolean colorRestriction, boolean valueRestriction) throws Exception {
        if (this.dice!=null) throw new Exception();// cell occupied
        if (colorRestriction)if(!checkColorRestriction(dice.getColor())) throw new Exception();
        if (valueRestriction)if(!checkValueRestriction(dice.getValue())) throw new Exception();
        this.dice=dice;
    }
}
