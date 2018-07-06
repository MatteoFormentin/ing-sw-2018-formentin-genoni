package it.polimi.se2018.model.dice;

import java.util.LinkedList;
import java.util.Random;


/**
 * Factory Pattern that generate a balanced set of Dice during one game
 *
 * @author Luca Genoni
 */
public class BalancedFactoryDice implements FactoryDice {
    private int currentNumberOfDice;
    private int[] currentNumberOfEachDice;
    private int numberOfDice;
    private LinkedList<DiceColor> availableColours;

    /**
     * Create a Balance Factory with variable number of dice that depend by the number of the round
     * the number of the player in a game and the number that each player need for the game
     * this factory dice also increment the available dice by the 10% needed
     * and adjust the number of the total dice based on the number of the color available
     *
     * @param numberPlayer the number of players in game
     * @param diceWindow   the number of dice that each players needs
     * @param numberRound  the total number of round
     */
    public BalancedFactoryDice(int numberPlayer, int diceWindow, int numberRound) {
        double range = ((diceWindow * numberPlayer) / 10);
        numberOfDice = (numberPlayer * diceWindow) + numberRound + (int) range;
        int fix = DiceColor.getNumberOfDiceColors() - (numberOfDice % DiceColor.getNumberOfDiceColors());
        if (fix != 0) numberOfDice += fix;
        currentNumberOfDice = numberOfDice;
        currentNumberOfEachDice = new int[DiceColor.getNumberOfDiceColors()];
        availableColours = new LinkedList<>();
        int maxNumberOfEachDice = numberOfDice / DiceColor.getNumberOfDiceColors();
        for (int i = 0; i < DiceColor.getNumberOfDiceColors(); i++) {
            currentNumberOfEachDice[i] = maxNumberOfEachDice;
            availableColours.add(DiceColor.getDiceColor(i));
        }
    }

    /**
     * reset the balanced factory as if it was a new one
     */
    public void reset() {
        currentNumberOfDice = numberOfDice;
        currentNumberOfEachDice = new int[DiceColor.getNumberOfDiceColors()];
        availableColours = new LinkedList<>();
        int maxNumberOfEachDice = numberOfDice / DiceColor.getNumberOfDiceColors();
        for (int i = 0; i < currentNumberOfEachDice.length; i++) {
            currentNumberOfEachDice[i] = maxNumberOfEachDice;
            availableColours.add(DiceColor.getDiceColor(i));
        }
    }

    public int getMaxNumberOfDice() {
        return numberOfDice;
    }

    public int getNumberAvailableColours() {
        return availableColours.size();
    }

    /**
     * Constructor balanced for the dice
     *
     * @return the new dice
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