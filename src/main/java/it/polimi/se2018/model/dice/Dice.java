package it.polimi.se2018.model.dice;

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
        value = rollDice();
        this.color = color;
    }

    /**
     * set a value for the dice
     *
     * @param value to set on the dice
     */
    public void setValue(int value) {
        if(value>0&&value<7) this.value = value;
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
     * get the color of the dice
     *
     * @return the color of the dice
     */
    public DiceColor getColor() {
        return color;
    }

    /**
     * generates a random number belonging to the right domain [1,6], this method don't set the value on the Dice.
     *
     * @return integer randomly generated
     */
    public int rollDice() {
        Random r = new Random();
        return r.nextInt(6) + 1;
    }

    /**
     * return the opposite value of the dice, this method don't set the value on the Dice.
     *
     * @return the opposite value of the die
     */
    public int oppositeValue() {
        int opposite = value;
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

    public boolean increaseOrDecrease(boolean increase){
        if(increase){
            if (value==6) return false;
            value ++;
        }else{//decrease
            if(value==1) return false;
            value--;
        }
        return true;
    }


}