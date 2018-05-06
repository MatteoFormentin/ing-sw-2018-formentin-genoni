package it.polimi.se2018.model.dice.dice_factory;

import it.polimi.se2018.model.dice.dice_factory.Dice;

/**
 * DiceFactory follow the rule of a factory pattern method and implements also a method for remove one dice from the game
 */
public abstract class DiceFactory {
    public abstract Dice createDice();
    public abstract void removeDice(Dice dice);
}