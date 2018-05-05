package it.polimi.se2018.model.dice.dice_factory;

import it.polimi.se2018.model.dice.DiceColor;
import it.polimi.se2018.model.dice.dice_factory.Dice;
import it.polimi.se2018.model.dice.dice_factory.DiceFactory;

import java.util.Random;

public class DiceRandomFactory extends DiceFactory {
    @Override
    public Dice createDice() {
        int random = new Random().nextInt(DiceColor.getNumberOfDiceColors());
        return new Dice(DiceColor.getDiceColor(random));
    }
    @Override
    public void removeDice(Dice dice){}
}
