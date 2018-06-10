package it.polimi.se2018;

import it.polimi.se2018.exception.window_exception.RestrictionCellOccupiedException;
import it.polimi.se2018.exception.window_exception.RestrictionColorViolatedException;
import it.polimi.se2018.exception.window_exception.RestrictionValueViolatedException;
import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceColor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Luca Genoni
 */
public class TestCell {
    private static final String occupiedR = new RestrictionCellOccupiedException().getMessage();
    private static final String valueR = new RestrictionValueViolatedException().getMessage();
    private static final String colorR = new RestrictionColorViolatedException().getMessage();
    private Cell[] allColor;
    private Cell[] allValue;
    private Cell[][] matrix;
    private Cell free;
    private String e;
    private WindowPatternCard testWindowPatternCard;

    @Before
    public void initTestWindowPatternCard() {
        allColor = new Cell[DiceColor.getNumberOfDiceColors()];
        for (int i = 0; i < DiceColor.getNumberOfDiceColors(); i++) {
            allColor[i] = new Cell();
            allColor[i].setColorRestriction(DiceColor.getDiceColor(i));
        }
        allValue =new Cell[6];
        for (int i = 0; i < allValue.length; i++) {
            allValue[i] = new Cell();
            allValue[i].setValueRestriction(i+1);
        }
        free = new Cell();

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
    public void testInsetDice3Blue() {
        Dice dice = new Dice(DiceColor.Blue);
        dice.setValue(3);
        //try all color
        for (int i = 0; i < DiceColor.getNumberOfDiceColors(); i++) {
            e = null;
            try {
                allColor[i].insertDice(dice);
            } catch (Exception ex) {
                e = ex.getMessage();
            }
            if (DiceColor.Blue.equals(allColor[i].getColorRestriction())) {
                assertNull(e);
            } else {
                assertEquals(colorR, e);
            }
        }
        for (int i = 0; i < DiceColor.getNumberOfDiceColors(); i++) {
            e = null;
            try {
                allValue[i].insertDice(dice);
            } catch (Exception ex) {
                e = ex.getMessage();
            }
            if ((i+1)==dice.getValue()) {
                assertNull(e);
            } else {
                assertEquals(valueR, e);
            }
        }
        e = null;
        try {
            free.insertDice(dice);
        } catch (Exception ex) {
            e = ex.getMessage();
        }
        assertNull(e);
        e = null;
        try {
            free.insertDice(dice);
        } catch (Exception ex) {
            e = ex.getMessage();
        }
        assertEquals(occupiedR,e);

    }

    @Test
    public void testDice1Blue(){
        Dice dice = new Dice(DiceColor.Blue);
        dice.setValue(1);
        //no exception
        e=null;
        try {
            testWindowPatternCard.getCell(0, 1).insertDice(dice);
            testWindowPatternCard.getCell(0, 2).insertDice(dice);
            testWindowPatternCard.getCell(0, 3).insertDice(dice);
            testWindowPatternCard.getCell(1, 1).insertDice(dice);
            testWindowPatternCard.getCell(1, 3).insertDice(dice);
            testWindowPatternCard.getCell(2, 1).insertDice(dice);
            testWindowPatternCard.getCell(2, 3).insertDice(dice);
            testWindowPatternCard.getCell(3, 1).insertDice(dice);
            testWindowPatternCard.getCell(3, 2).insertDice(dice);
            testWindowPatternCard.getCell(3, 3).insertDice(dice);
        }catch(Exception ex){
            e = ex.getMessage();
        }
        assertNull(e);

        try {
            testWindowPatternCard.getCell(1, 2).insertDice(dice);
        } catch (Exception ex) {
            e = ex.getMessage();
        }
        assertEquals(valueR, e);

        try {
            testWindowPatternCard.getCell(1, 4).insertDice(dice);
        } catch (Exception ex) {
            e = ex.getMessage();
        }
        assertEquals(valueR, e);

        try {
            testWindowPatternCard.getCell(1, 4).insertDice(dice);
        } catch (Exception ex) {
            e = ex.getMessage();
        }
        assertEquals(valueR, e);
        try {
            testWindowPatternCard.getCell(2, 0).insertDice(dice);
        } catch (Exception ex) {
            e = ex.getMessage();
        }
        assertEquals(valueR, e);
        try {
            testWindowPatternCard.getCell(2, 2).insertDice(dice);
        } catch (Exception ex) {
            e = ex.getMessage();
        }
        assertEquals(colorR, e);

        try {
            testWindowPatternCard.getCell(2, 4).insertDice(dice);
        } catch (Exception ex) {
            e = ex.getMessage();
        }
        assertEquals(colorR, e);
        try {
            testWindowPatternCard.getCell(3, 0).insertDice(dice);
        } catch (Exception ex) {
            e = ex.getMessage();
        }
        assertEquals(valueR, e);
        try {
            testWindowPatternCard.getCell(3, 4).insertDice(dice);
        } catch (Exception ex) {
            e = ex.getMessage();
        }
        assertEquals(colorR, e);

        try {
            testWindowPatternCard.getCell(0, 0).insertDice(dice);

        } catch (Exception ex) {
            e = ex.getMessage();
        }
        assertEquals(colorR, e);


        try {
            testWindowPatternCard.getCell(1, 0).insertDice(dice);
        } catch (Exception ex) {
            e = ex.getMessage();
        }
        assertEquals(colorR, e);

        //TODO metterlo con le eccezioni
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
      /*  Dice dice,dice2;
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
        assertTrue(testWindowPatternCard.insertDice(0, 3,dice));*/

    }
}
