package it.polimi.se2018.model.dice;

import it.polimi.se2018.model.dice.dice_color.DiceColor;

import java.util.Random;
/**
 * <strong>Class Dice</strong>
 * <em>Description</em>
 * Class that define the dice
 *
 * @author Luca Genoni
 * @version 1.0
 * @since 1.0
 */
public class Dice {
    private int value;
    private DiceColor color;


    Dice() {
        value = rollDice();
        color = null;
    }
    Dice(DiceColor color){
        value = rollDice();
        this.color = color;
    }

    /**
     * Method <strong>setValue</strong>
     * <em>Description</em>
     * set a value for the dice
     *
     * @param value to set on the dice
     *              @require belong to domain [1,6]
     */
    public void setValue(int value) {
        this.value = value;
    }
    /**
     * Method <strong>setColor</strong>
     * <em>Description</em>
     * set a color for the dice
     *
     * @param color to set on the dice
     */
    public void setColor(DiceColor color) {
        this.color = color;
    }
    /**
     * Method <strong>getValue</strong>
     * <em>Description</em>
     * get the Value of the dice
     *
     * @return value of the dice
     */
    public int getValue() {
        return value;
    }
    /**
     * Method <strong>setColor</strong>
     * <em>Description</em>
     * get the color of the dice
     *
     * @return color of the dice
     */
    public DiceColor getColor() {
        return color;
    }

    /**
     *  Method <strong>rollDice</strong>
     * <em>Description</em>
     * generates a random number belonging to the right domain [1,6], this method don't set the value on the Dice.
     *
     * @return integer randomly generated
     */
    public int rollDice() {
        Random r= new Random();
        return r.nextInt(6) + 1;
    }
    /**
     *  Method <strong>oppositeValue</strong>
     * <em>Description</em>
     *
     *
     * @return the opposite value of one die
     */
    public int oppositeValue() {
        int opposite= value;
        switch (opposite) {
            case 1:
                opposite = 6;
                break;
            case 2:
                opposite = 5;
                break;
            case 3:
                opposite = 4;
                break;
            case 4:
                opposite = 3;
                break;
            case 5:
                opposite = 2;
                break;
            case 6:
                opposite = 1;
                break;
        }
        return opposite;
    }


}