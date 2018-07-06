package it.polimi.se2018.model.dice;

import java.io.Serializable;

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
     * DiceColor.values().length
     *
     * @return int number of color types
     */
    public static int getNumberOfDiceColors() {
        return DiceColor.values().length;
    }
}

