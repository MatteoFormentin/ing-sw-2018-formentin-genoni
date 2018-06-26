package it.polimi.se2018;

import it.polimi.se2018.exception.gameboard_exception.tool_exception.ValueDiceWrongException;
import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceColor;
import org.junit.*;

import static org.junit.Assert.*;


public class TestDice {

    @Test
    public void testOppositeValue() throws ValueDiceWrongException {
        Dice dice = new Dice(DiceColor.GREEN);
        dice.setValue(1);
        assertEquals(6, dice.oppositeValue());

        dice.setValue(2);
        assertEquals(5, dice.oppositeValue());

        dice.setValue(3);
        assertEquals(4, dice.oppositeValue());

        dice.setValue(4);
        assertEquals(3, dice.oppositeValue());

        dice.setValue(5);
        assertEquals(2, dice.oppositeValue());

        dice.setValue(6);
        assertEquals(1, dice.oppositeValue());
    }
}
