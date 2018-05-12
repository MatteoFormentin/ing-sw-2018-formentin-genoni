package it.polimi.se2018;

import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceColor;
import org.junit.*;

import static org.junit.Assert.*;

public class TestCard {

    private Cell[][] matrix;
    private WindowPatternCard testWindowPatternCard;


    @Before
    public void initTestWindowPatternCard() {
        matrix = new Cell[4][5];
        for (int m = 0; m < 4; m++) {
            for (int n = 0; n < 5; n++) {
                matrix[m][n] = new Cell();
            }
        }

        //Test with "Aurorae Magnificus"
        matrix[0][0].setValueRestriction(5);
        matrix[0][1].setColorRestriction(DiceColor.Green);
        matrix[0][2].setColorRestriction(DiceColor.Blue);
        matrix[0][3].setColorRestriction(DiceColor.Purple);
        matrix[0][4].setValueRestriction(2);

        matrix[1][0].setColorRestriction(DiceColor.Purple);
        matrix[1][4].setColorRestriction(DiceColor.Yellow);

        matrix[2][0].setColorRestriction(DiceColor.Yellow);
        matrix[2][2].setValueRestriction(6);
        matrix[2][4].setColorRestriction(DiceColor.Purple);

        matrix[3][1].setValueRestriction(1);
        matrix[3][3].setColorRestriction(DiceColor.Green);
        matrix[3][4].setValueRestriction(4);

        testWindowPatternCard = new WindowPatternCard("test", 5, matrix);
    }

    @Test
    public void testInsetDice() {
        Dice dice = new Dice(DiceColor.Blue);
        dice.setValue(2);
        //Wrong move - First dice cant be placed on board center.
        //assertFalse(testWindowPatternCard.insertDice(3, 3, dice));

        //Wrong move - Try to place a dice in a cell with number restriction but on correct position (board edge)
        assertFalse(testWindowPatternCard.insertDice(0, 0, dice));

        //Correct move - first dice on edge and with correct number
        dice.setValue(5);
        assertTrue(testWindowPatternCard.insertDice(0, 0, dice));

        //Wrong move - Try to place a dice in a cell with color restriction but on correct position (adj restr ok)
        assertFalse(testWindowPatternCard.insertDice(0, 1, dice));

        //Correct move - Dice witch correct color and correct adj restriction
        dice = new Dice(DiceColor.Green);
        assertTrue(testWindowPatternCard.insertDice(0, 1, dice));
    }
}
