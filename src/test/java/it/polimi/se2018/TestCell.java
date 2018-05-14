package it.polimi.se2018;

import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceColor;
import it.polimi.se2018.view.cli.Cli;
import org.junit.Before;
import org.junit.Test;
import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceColor;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author Luca Genoni
 */
public class TestCell {
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
        //cosa facile facile se venisse messo un read card[i] nel loader

        //Test with "Aurorae Magnificus"
        matrix[0][0].setColorRestriction(DiceColor.Yellow);
        matrix[0][1].setColorRestriction(DiceColor.Blue);
        //matrix[0][2].setColorRestriction(DiceColor.Blue);
        //matrix[0][3].setColorRestriction(DiceColor.Purple);
        //matrix[0][4].setColorRestriction(DiceColor.Purple);

        matrix[1][0].setColorRestriction(DiceColor.Green);
        /*matrix[1][1].setColorRestriction(DiceColor.Blue);
        matrix[1][2].setColorRestriction(DiceColor.Blue);
        matrix[1][3].setColorRestriction(DiceColor.Purple);
        matrix[1][4].setColorRestriction(DiceColor.Purple);*/

        //matrix[2][0].setColorRestriction(DiceColor.Yellow);
        //matrix[2][1].setColorRestriction(DiceColor.Blue);
        matrix[2][2].setColorRestriction(DiceColor.Red);
        //matrix[2][3].setColorRestriction(DiceColor.Purple);
        matrix[2][4].setColorRestriction(DiceColor.Green);

        /*matrix[3][0].setColorRestriction(DiceColor.Yellow);
        matrix[3][1].setColorRestriction(DiceColor.Blue);
        matrix[3][2].setColorRestriction(DiceColor.Blue);*/
        matrix[3][3].setColorRestriction(DiceColor.Blue);
        matrix[3][4].setColorRestriction(DiceColor.Yellow);

        //for value***************************************************************

        /*matrix[0][0].setValueRestriction(1);
        matrix[0][1].setValueRestriction(1);
        matrix[0][2].setValueRestriction(1);
        matrix[0][3].setValueRestriction(1);*/
        matrix[0][4].setValueRestriction(1);

        /*matrix[1][0].setValueRestriction(5);
        matrix[1][1].setValueRestriction(5);*/
        matrix[1][2].setValueRestriction(5);
        //matrix[1][3].setValueRestriction(5);
        matrix[1][4].setValueRestriction(4);

        matrix[2][0].setValueRestriction(3);
        /*matrix[2][1].setValueRestriction(1);
        matrix[2][2].setValueRestriction(1);
        matrix[2][3].setValueRestriction(1);
        matrix[2][4].setValueRestriction(1);*/

        matrix[3][0].setValueRestriction(2);
        /*matrix[3][1].setValueRestriction(1);
        matrix[3][2].setValueRestriction(1);
        matrix[3][3].setValueRestriction(1);
        matrix[3][4].setValueRestriction(1);*/

        testWindowPatternCard = new WindowPatternCard("testKaleidoscopicDream", 4, matrix);
    }

    @Test
    public void testInsetDiceCell() {
        Dice dice = new Dice(DiceColor.Blue);
        dice.setValue(1);
        //no window check

        assertTrue(testWindowPatternCard.getCell(0, 1).insertDice(dice));
        assertTrue(testWindowPatternCard.getCell(0, 2).insertDice(dice));
        assertTrue(testWindowPatternCard.getCell(0, 3).insertDice(dice));
        assertTrue(testWindowPatternCard.getCell(1, 1).insertDice(dice));
        assertTrue(testWindowPatternCard.getCell(1, 3).insertDice(dice));
        assertTrue(testWindowPatternCard.getCell(2, 1).insertDice(dice));
        assertTrue(testWindowPatternCard.getCell(2, 3).insertDice(dice));
        assertTrue(testWindowPatternCard.getCell(3, 1).insertDice(dice));
        assertTrue(testWindowPatternCard.getCell(3, 2).insertDice(dice));
        assertTrue(testWindowPatternCard.getCell(3, 3).insertDice(dice));


        assertFalse(testWindowPatternCard.getCell(0, 0).insertDice(dice));
        assertFalse(testWindowPatternCard.getCell(1, 0).insertDice(dice));
        assertFalse(testWindowPatternCard.getCell(1, 2).insertDice(dice));
        assertFalse(testWindowPatternCard.getCell(1, 4).insertDice(dice));
        assertFalse(testWindowPatternCard.getCell(2, 0).insertDice(dice));
        assertFalse(testWindowPatternCard.getCell(2, 2).insertDice(dice));
        assertFalse(testWindowPatternCard.getCell(2, 4).insertDice(dice));
        assertFalse(testWindowPatternCard.getCell(3, 0).insertDice(dice));
        assertFalse(testWindowPatternCard.getCell(3, 4).insertDice(dice));

        assertTrue(testWindowPatternCard.getCell(0, 0).insertDice(dice, false, false));
        assertTrue(testWindowPatternCard.getCell(1, 0).insertDice(dice, false, false));
        assertTrue(testWindowPatternCard.getCell(1, 2).insertDice(dice, false, false));
        assertTrue(testWindowPatternCard.getCell(1, 4).insertDice(dice, false, false));
        assertTrue(testWindowPatternCard.getCell(2, 0).insertDice(dice, false, false));
        assertTrue(testWindowPatternCard.getCell(2, 2).insertDice(dice, false, false));
        assertTrue(testWindowPatternCard.getCell(2, 4).insertDice(dice, false, false));
        assertTrue(testWindowPatternCard.getCell(3, 0).insertDice(dice, false, false));
        assertTrue(testWindowPatternCard.getCell(3, 4).insertDice(dice, false, false));
    }

    @Test
    public void testInsetDiceWindow() {
        Dice dice,dice2;
        dice = new Dice(DiceColor.Blue);
        dice.setValue(1);
        boolean adjacentRestriction=true,colorRestriction=true,valueRestriction=false;
        int line,column;
        //insert with restriction
        assertTrue(testWindowPatternCard.insertDice(0, 1,dice));
        for(line=0;line<4;line++){
            for(column=0;column<5;column++){
                assertFalse(testWindowPatternCard.insertDice(line, column,dice));
            }
        }
        dice2 = new Dice(DiceColor.Green);
        dice2.setValue(2);
        assertTrue(testWindowPatternCard.insertDice(0, 2,dice2));
        dice = new Dice(DiceColor.Blue);
        dice.setValue(1);
        assertTrue(testWindowPatternCard.insertDice(0, 3,dice));

    }
}
