package it.polimi.se2018.model.dice;

import java.util.Arrays;

/**
 * @author Luca Genoni
 * @version 1.0
 * @since 1.0
 */
public enum DiceColor {
    Red,
    Yellow,
    Green,
    Blue,
    Purple;
    private static final int numberOfDiceColors=(int) Arrays.stream(values()).count();

    /**
     * Method <strong>getDiceColor</strong>
     * <em>Description</em>
     * return the DiceColor given the associated number
     *
     * @param ordinal number int associated with a DiceColor
     *                @require ordinal belong to domain [0,numberOfDiceColors] numberOfDiceColors=4 if the source code has not changed since version 1.0
     * @return DiceColor associated with the input number, null if there is no match.
     */
    public static DiceColor getDiceColor(int ordinal){
        /* exchangeable with
         * return Arrays.stream(DiceColor.values()).filter(p -> p.ordinal() == ordinal).findFirst().orElse(null);
         */
        for ( DiceColor p: values() )
        {
            if(p.ordinal() == ordinal) return p;
        }
        throw new IndexOutOfBoundsException();
    }

    /**
     * Method <strong>getNumberOfDiceColors</strong>
     * <em>Description</em>
     * return the number of color types available
     *
     * @return int number of color types
     */

    public static int getNumberOfDiceColors(){
        return numberOfDiceColors;
    }


}
