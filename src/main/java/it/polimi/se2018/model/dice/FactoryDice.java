package it.polimi.se2018.model.dice;

/**
 * FactoryDice follow the rule of a factory pattern method and implements also a method for remove one dice from the game
 *
 * @author Luca Genoni
 */
public interface FactoryDice {
    Dice createDice();
    void removeDice(Dice dice);
}