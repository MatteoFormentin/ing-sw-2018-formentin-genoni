package it.polimi.se2018.model.dice;

import java.util.Random;

public class FactoryRandomDice extends FactoryDice {
    @Override
    public Dice createDice() {
        int random = new Random().nextInt(DiceColor.getNumberOfDiceColors());
        return new Dice(DiceColor.getDiceColor(random));
    }
    @Override
    public void removeDice(Dice dice){}
}
