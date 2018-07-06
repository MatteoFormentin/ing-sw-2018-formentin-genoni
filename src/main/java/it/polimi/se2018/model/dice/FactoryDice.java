package it.polimi.se2018.model.dice;

import it.polimi.se2018.exception.gameboard_exception.tool_exception.ValueDiceWrongException;

/**
 * FactoryDice follow the rule of a factory pattern method and implements also a method for remove one dice from the game
 *
 * @author Luca Genoni
 */
public interface FactoryDice {
    Dice createDice() throws ValueDiceWrongException;

    void removeDice(Dice dice);
}