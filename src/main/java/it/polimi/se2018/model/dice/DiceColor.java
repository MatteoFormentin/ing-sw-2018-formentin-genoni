package it.polimi.se2018.model.dice;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Enum for the color of the dice
 *
 * @author Luca Genoni
 */
public enum DiceColor implements Serializable {
    RED,
    YELLOW,
    GREEN,
    BLUE,
    PURPLE;

    private static class NumberDiceColor {
        private static final int NUMBER_COLOR = (int) Arrays.stream(DiceColor.values()).count();
    }

    /**
     * return the DiceColor given the associated number
     *
     * @param ordinal number int associated with a DiceColor
     * @return DiceColor associated with the input number, null if there is no match.
     */
    public static DiceColor getDiceColor(int ordinal) {
        for (DiceColor p : values()) {
            if (p.ordinal() == ordinal) return p;
        }
        return null;
    }

    /**
     * return the number of color types available
     *
     * @return int number of color types
     */
    public static int getNumberOfDiceColors() {
        return NumberDiceColor.NUMBER_COLOR;
    }
}

