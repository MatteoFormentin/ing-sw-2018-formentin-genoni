package it.polimi.se2018.model.dice;

/**
 * FactoryDice follow the rule of a factory pattern method and implements also a method for remove one dice from the game
 *
 * @author Luca Genoni
 */
public abstract class FactoryDice {
    public abstract Dice createDice();
    public abstract void removeDice(Dice dice);

}