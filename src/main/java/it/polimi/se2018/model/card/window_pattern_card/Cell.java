package it.polimi.se2018.model.card.window_pattern_card;

import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceColor;

/**
 * <strong>Class Cell</strong>
 * <em>Description</em>
 * Class that define a Cell of Window Pattern Card.
 *
 * @author Davide Mammarella
 * @version 1.0
 * @since 1.0
 * @author Luca Genoni only for the version 1.1
 * @version 1.1
 * @since 1.1
 */

public class Cell {

    private Dice dice;
    private int valueRestriction;
    private DiceColor colorRestriction;
    /* set/getRestriction and dice
       insertDice che controlla restrizioni

     */

    public int getValueRestriction() {
        return valueRestriction;
    }

    public void setValueRestriction(int valueRestriction) {
        this.valueRestriction = valueRestriction;
    }

    public DiceColor getColorRestriction() {
        return colorRestriction;
    }

    public void setColorRestriction(DiceColor colorRestriction) {
        this.colorRestriction = colorRestriction;
    }

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    /**
     * Method <strong>checkColorRestriction</strong>
     * <em>Description</em>
     * Check if the dice is allowed based on the color restriction of the cell.
     *
     * @return false if the color is restricted
     */
    boolean checkColorRestriction(DiceColor color) {
        return (color!=colorRestriction);
    }
    /**
     * Method <strong>checkValueRestriction</strong>
     * <em>Description</em>
     * Check if the dice is allowed based on the value restriction of the cell.
     *
     * @return false if the value is restricted
     */
    boolean checkValueRestriction(int value) {
        return (value!=valueRestriction);
    }

    /**
     * Method <strong>insertDice</strong>
     * <em>Description</em>
     * Insert the dice in the cell if the restriction of the cell are respected.
     *
     * @param dice to insert
     * @throws Exception can't place the dice
     */
    public void insertDice(Dice dice) throws Exception {
        if (this.dice==null) throw new Exception();// cell occupied
        if (checkColorRestriction(dice.getColor()) && checkValueRestriction(dice.getValue()))throw new Exception();//cell restricted
        this.dice=dice;
    }

    /**
     * Method <strong>insertDice</strong>
     * <em>Description</em>
     * Insert the dice in the cell if the restriction of the cell are respected.
     *
     * @param dice to insert in the cell
     * @param colorRestriction true if i need to check the restriction
     * @param valueRestriction true if i need to check the restriction
     * @throws Exception can't insert the dice
     */
    public void insertDice(Dice dice, boolean colorRestriction, boolean valueRestriction) throws Exception {
        if (this.dice==null) throw new Exception();// cell occupied
        if (colorRestriction)if(!checkColorRestriction(dice.getColor())) throw new Exception();
        if (valueRestriction)if(!checkValueRestriction(dice.getValue())) throw new Exception();
        this.dice=dice;
    }

}
