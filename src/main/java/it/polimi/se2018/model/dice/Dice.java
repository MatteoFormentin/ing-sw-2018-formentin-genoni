package it.polimi.se2018.model.dice;

import it.polimi.se2018.exception.gameboard_exception.tool_exception.ValueDiceWrongException;

import java.io.Serializable;
import java.util.Random;

/**
 * Class that define the dice with a color and a value
 *
 * @author Luca Genoni
 */
public class Dice implements Serializable {
    private int value;
    private DiceColor color;

    /**
     * create a dice, in a game this constructor shouldn't be called.
     * Only the factory should create a dice!, but for testing reason it's public.
     *
     * @param color of the dice
     */
    public Dice(DiceColor color) {
        rollDice();
        this.color = color;
    }

    /**
     * get the Value of the dice
     *
     * @return the value of the dice
     */
    public int getValue() {
        return value;
    }

    /**
     * set a value for the dice
     *
     * @param value to set on the dice
     */
    public void setValue(int value) throws ValueDiceWrongException {
        if (value > 0 && value < 7) this.value = value;
        else throw new ValueDiceWrongException();
    }

    /**
     * get the color of the dice
     *
     * @return the color of the dice
     */
    public DiceColor getColor() {
        return color;
    }


    /**
     * generates a random number belonging to the right domain [1,6], this method don't set the value on the Dice.
     */
    public void rollDice() {
        Random r = new Random();
        value = r.nextInt(6) + 1;
    }

    /**
     * return the opposite value of the dice, this method don't set the value on the Dice.
     *
     * @return the opposite value of the die
     */
    public int oppositeValue() {
        switch (value) {
            case 1:
                value = 6;
                break;
            case 2:
                value = 5;
                break;
            case 3:
                value = 4;
                break;
            case 4:
                value = 3;
                break;
            case 5:
                value = 2;
                break;
            case 6:
                value = 1;
                break;
            default:
                value = 0;
        }
        return value;
    }

    public void increaseOrDecrease(boolean increase) throws ValueDiceWrongException {
        if (increase) {
            if (value == 6) throw new ValueDiceWrongException();
            value++;
        } else {//decrease
            if (value == 1) throw new ValueDiceWrongException();
            value--;
        }
    }


}