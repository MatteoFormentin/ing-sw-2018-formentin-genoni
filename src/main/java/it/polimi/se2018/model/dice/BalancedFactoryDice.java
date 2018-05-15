package it.polimi.se2018.model.dice;

import java.util.LinkedList;
import java.util.Random;


/**
 * factory that generate a balanced set of Dice during one game
 *
 * @author Luca Genoni
 */
public class BalancedFactoryDice extends FactoryDice {
    private int currentNumberOfDice;
    //currentNumberOfEachDice stores the number of dice for each color. It is indexed with the ordinal number relative to each color
    private int[] currentNumberOfEachDice;
    private final int MaxNumberOfDice = 90;
    //availableColours stores the ordinal number linked to the color
    private LinkedList<DiceColor> availableColours;

    /**
     * Create a Balance Factory with only 90 Dice
     */
    public BalancedFactoryDice() {
        currentNumberOfDice = MaxNumberOfDice;
        currentNumberOfEachDice = new int[DiceColor.getNumberOfDiceColors()];
        availableColours = new LinkedList<>();
        int maxNumberOfEachDice = MaxNumberOfDice / DiceColor.getNumberOfDiceColors();
        for (int i = 0; i < DiceColor.getNumberOfDiceColors(); i++) {
            currentNumberOfEachDice[i] = maxNumberOfEachDice;
            availableColours.add(DiceColor.getDiceColor(i));
        }
    }

    /**
     * reset the balanced factory as if it was a new one
     */
    public void reset() {
        currentNumberOfDice = MaxNumberOfDice;
        currentNumberOfEachDice = new int[DiceColor.getNumberOfDiceColors()];
        availableColours = new LinkedList<>();
        int maxNumberOfEachDice = MaxNumberOfDice / DiceColor.getNumberOfDiceColors();
        for (int i = 0; i < currentNumberOfEachDice.length; i++) {
            currentNumberOfEachDice[i] = maxNumberOfEachDice;
            availableColours.add(DiceColor.getDiceColor(i));
        }
    }


    public int getMaxNumberOfDice() {
        return MaxNumberOfDice;
    }

    public int getCurrentNumberOfDice() {
        return currentNumberOfDice;
    }

    public int[] getCurrentNumberOfEachDice() {
        return currentNumberOfEachDice;
    }

    public LinkedList<DiceColor> getAvailableColours() {
        return availableColours;
    }

    /**
     * constructor for dices.
     *
     * @return the new dice or null
     */
    @Override
    public Dice createDice() {
        if (currentNumberOfDice == 0) {
            return null;
        } else {
            boolean repeat = true;    //for the while loop
            int random = 0;
            while (repeat) {
                random = new Random().nextInt(availableColours.size());
                if (currentNumberOfEachDice[availableColours.get(random).ordinal()] > 0) {
                    //when it found a color available, the loop stops
                    repeat = false;
                }
            }
            currentNumberOfDice--;
            currentNumberOfEachDice[availableColours.get(random).ordinal()]--;
            Dice dice = new Dice(availableColours.get(random));
            if (currentNumberOfEachDice[availableColours.get(random).ordinal()] == 0) {
                //remove the availableColours after the creation of the dice.
                availableColours.remove(random);
            }
            return dice;
        }
    }

    /**
     * method to reinsert a die in the factory
     *
     * @param dice to reinsert in the factory
     */
    @Override
    public void removeDice(Dice dice) {
        if (currentNumberOfEachDice[dice.getColor().ordinal()] == 0) {
            availableColours.addLast(dice.getColor()); // re-add the flag to make the color available
        }
        currentNumberOfDice++;
        currentNumberOfEachDice[dice.getColor().ordinal()]++;

    }
}