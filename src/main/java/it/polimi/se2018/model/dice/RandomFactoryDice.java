package it.polimi.se2018.model.dice;

import java.util.Random;

/**
 * basic factory for the dice
 *
 * @author Luca Genoni
 */
public class RandomFactoryDice extends FactoryDice {
    /**
     * create a dice without any logic
     *
     * @return the dice created
     */
    @Override
    public Dice createDice() {
        int random = new Random().nextInt(DiceColor.getNumberOfDiceColors());
        return new Dice(DiceColor.getDiceColor(random));
    }

    /**
     * nothing
     *
     * @param dice to delete
     */
    @Override
    public void removeDice(Dice dice){}
}
