package it.polimi.se2018.model.card.windowPatternCard;

import it.polimi.se2018.model.dice.dice_factory.Dice;
import it.polimi.se2018.model.dice.DiceColor;

/**
 * <strong>Class Cell</strong>
 * <em>Description</em>
 * Class that define a Cell of Window Pattern Card.
 *
 * @author Davide Mammarella
 * @version 1.0
 * @since 1.0
 */

public class Cell {

    private Dice dice;
    private int numberRestriction;
    private DiceColor colorRestriction;
    private int value;
    private DiceColor color;

    public Cell() {

        Cell() {
            value = 0;
            color = null;
        }

    }

    public int getNumberRestriction() {
        return numberRestriction;
    }

    public void setNumberRestriction(int numberRestriction) {
        this.numberRestriction = numberRestriction;
    }

    public DiceColor getColorRestriction() {
        return colorRestriction;
    }

    public void setColorRestriction(DiceColor colorRestriction) {
        this.colorRestriction = colorRestriction;
    }

    /**
     * Method <strong>setCellValue</strong>
     * <em>Description</em>
     * Set a value for the cell, based on the dice added to it.
     *
     * @param value to set on the cell
     */
    public void setCellValue(int value) {
        this.value = value;
    }

    /**
     * Method <strong>setCellColor</strong>
     * <em>Description</em>
     * Set a color for the cell, based on the dice added to it.
     *
     * @param color to set on the cell
     */
    public void setCellColor(DiceColor color) {
        this.color = color;
    }

    /**
     * Method <strong>getCellValue</strong>
     * <em>Description</em>
     * Get the value of the dice on the cell.
     */
    public int getCellValue() {
        return value;
    }

    /**
     * Method <strong>getCellColor</strong>
     * <em>Description</em>
     * Get the color of the dice on the cell.
     */
    public DiceColor getCellColor() {
        return color;
    }

    /**
     * Method <strong>getCellDice</strong>
     *
     * @return the dice
     */
    public Dice getCellDice() {
        return this.dice;
    }

    /**
     * Method <strong>checkCellRestriction</strong>
     * <em>Description</em>
     * Check if the dice is allowed based on the color or value restriction of the cell.
     *
     * @return true if the restriction is verified, false if the restriction is not verified.
     */
    public boolean checkCellRestriction(DiceColor diceColor, int diceNumber) {
        if (numberRestriction == diceNumber || colorRestriction == diceColor) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method <strong>insertDice</strong>
     * <em>Description</em>
     * Insert the dice in the cell if the restriction of the cell are respected.
     */
    public void insertDice(Dice dice) {
        value = dice.getValue();
        color = dice.getColor();

        if (checkCellRestriction(color, value) == true) {
            System.out.print("You can't insert the Dice, it does not respect the restriction");
        } else {
            setCellColor(color);
            setCellValue(value);
        }
    }

}
