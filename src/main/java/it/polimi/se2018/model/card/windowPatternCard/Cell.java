package it.polimi.se2018.model.card.windowPatternCard;

import it.polimi.se2018.model.dice.dice_factory.Dice;
import it.polimi.se2018.model.dice.DiceColor;


public class Cell {

    public Cell() {
    }

    private Dice Dice;
    private boolean Restriction;
    public int NumberRestriction;
    public DiceColor ColorRestriction;

    public void SetDice(Dice Dice) {
    }

    public Dice GetDice() {
        return null;
    }

    public boolean CheckRestriction() {
        return false;
    }

}
