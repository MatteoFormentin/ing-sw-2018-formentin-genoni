package it.polimi.se2018;

import it.polimi.se2018.exception.window_exception.RestrictionAdjacentViolatedException;
import it.polimi.se2018.exception.window_exception.RestrictionColorViolatedException;
import it.polimi.se2018.exception.window_exception.RestrictionValueViolatedException;
import it.polimi.se2018.model.card.objective_private_card.*;
import it.polimi.se2018.model.card.objective_public_card.*;
import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceColor;
import org.junit.*;

import static org.junit.Assert.*;

public class TestCard {

    private static final RestrictionAdjacentViolatedException adjacentR = new RestrictionAdjacentViolatedException();
    private static final RestrictionValueViolatedException valueR = new RestrictionValueViolatedException();
    private static final RestrictionColorViolatedException colorR = new RestrictionColorViolatedException();
    private WindowPatternCard testWindowPatternCard;


    @Before
    public void initTestWindowPatternCard() {
        Cell[][] matrix;
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
    public void testCard() {
        Dice dice = new Dice(DiceColor.Blue);
        dice.setValue(2);
        //Wrong move - First dice cant be placed on board center.
        String e=null;
        try {
            testWindowPatternCard.insertDice(1, 2, dice);
        }catch(Exception ex){
            e= ex.getMessage();
        }
        assertEquals(adjacentR.getMessage(),e);
        //Wrong move - Try to place a dice in a cell with number restriction but on correct position (board edge)
        e=null;
        try {
            testWindowPatternCard.insertDice(0, 0, dice);
        }catch(Exception ex){
            e= ex.getMessage();
        }
        assertEquals(valueR.getMessage(),e);

        //Correct move - first dice on edge and with correct number
        dice.setValue(5);
        e=null;
        try {
            testWindowPatternCard.insertDice(0, 0, dice);
        }catch(Exception ex){
            e= ex.getMessage();
        }
        assertNull(e);

        //Wrong move - Try to place a dice in a cell with color restriction but on correct position (adj restr ok)
        dice = new Dice(DiceColor.Blue);
        dice.setValue(5);
        e=null;
        try {
            testWindowPatternCard.insertDice(0, 1, dice);
        }catch(Exception ex){
            e= ex.getMessage();
        }
        assertEquals(colorR.getMessage(),e);

        //Correct move - Dice witch correct color and correct adj restriction
        dice = new Dice(DiceColor.Green);
        dice.setValue(3);
        e=null;
        try {
            testWindowPatternCard.insertDice(0, 1, dice);
        }catch(Exception ex){
            e= ex.getMessage();
        }
        assertNull(e);

        //Wrong move - Try to place a dice in a cell away from last dice
        dice = new Dice(DiceColor.Yellow);
        dice.setValue(3);
        e=null;
        try {
            testWindowPatternCard.insertDice(1, 4, dice);
        }catch(Exception ex){
            e= ex.getMessage();
        }
        assertEquals(adjacentR.getMessage(),e);

        ///Wrong move - Try to place a dice in a cell with correct color restr but adjacentR a dice with same value
        dice = new Dice(DiceColor.Blue);
        dice.setValue(3);
        e=null;
        try {
            testWindowPatternCard.insertDice(0, 2, dice);
        }catch(Exception ex){
            e= ex.getMessage();
        }
        assertEquals(valueR.getMessage(),e);

        //Correct move - Dice witch correct color and correct adj restriction
        dice = new Dice(DiceColor.Blue);
        dice.setValue(4);
        e=null;
        try {
            testWindowPatternCard.insertDice(0, 2, dice);
        }catch(Exception ex){
            e= ex.getMessage();
        }
        assertNull(e);

        //Correct move - Dice witch correct color and correct adj restriction
        dice = new Dice(DiceColor.Purple);
        dice.setValue(1);
        e=null;
        try {
            testWindowPatternCard.insertDice(0, 3, dice);
        }catch(Exception ex){
            e= ex.getMessage();
        }
        assertNull(e);

        //Wrong move - Try to place a dice in a cell with correct number restr but adjacentR a dice with same color
        dice = new Dice(DiceColor.Purple);
        dice.setValue(6);
        e=null;
        try {
            testWindowPatternCard.insertDice(0, 4, dice);
        }catch(Exception ex){
            e= ex.getMessage();
        }
        assertEquals(colorR.getMessage(),e);

        //Correct move - Dice witch correct color and correct adj restriction
        dice = new Dice(DiceColor.Green);
        dice.setValue(2);
        e=null;
        try {
            testWindowPatternCard.insertDice(0, 4, dice);
        }catch(Exception ex){
            e= ex.getMessage();
        }
        assertNull(e);

        //Correct move - Dice witch correct color and correct adj restriction
        dice = new Dice(DiceColor.Purple);
        dice.setValue(3);
        e=null;
        try {
            testWindowPatternCard.insertDice(1, 0, dice);
        }catch(Exception ex){
            e= ex.getMessage();
        }
        assertNull(e);

        //Correct move - Dice witch correct color and correct adj restriction
        dice = new Dice(DiceColor.Yellow);
        dice.setValue(4);
        e=null;
        try {
            testWindowPatternCard.insertDice(1, 1, dice);
        }catch(Exception ex){
            e= ex.getMessage();
        }
        assertNull(e);

        //Correct move - Dice witch correct color and correct adj restriction
        dice = new Dice(DiceColor.Green);
        dice.setValue(5);
        e=null;
        try {
            testWindowPatternCard.insertDice(1, 2, dice);
        }catch(Exception ex){
            e= ex.getMessage();
        }
        assertNull(e);

        //Correct move - Dice witch correct color and correct adj restriction
        dice = new Dice(DiceColor.Red);
        dice.setValue(2);
        e=null;
        try {
            testWindowPatternCard.insertDice(1, 3, dice);
        }catch(Exception ex){
            e= ex.getMessage();
        }
        assertNull(e);

        //Correct move - Dice witch correct color and correct adj restriction
        dice = new Dice(DiceColor.Yellow);
        dice.setValue(4);
        e=null;
        try {
            testWindowPatternCard.insertDice(1, 4, dice);
        }catch(Exception ex){
            e= ex.getMessage();
        }
        assertNull(e);

        //Correct move - Dice witch correct color and correct adj restriction
        dice = new Dice(DiceColor.Yellow);
        dice.setValue(2);
        e=null;
        try {
            testWindowPatternCard.insertDice(2, 0, dice);
        }catch(Exception ex){
            e= ex.getMessage();
        }
        assertNull(e);

        //Correct move - Dice witch correct color and correct adj restriction
        dice = new Dice(DiceColor.Red);
        dice.setValue(5);
        e=null;
        try {
            testWindowPatternCard.insertDice(2, 1, dice);
        }catch(Exception ex){
            e= ex.getMessage();
        }
        assertNull(e);

        //Correct move - Dice witch correct color and correct adj restriction
        dice = new Dice(DiceColor.Blue);
        dice.setValue(6);
        e=null;
        try {
            testWindowPatternCard.insertDice(2, 2, dice);
        }catch(Exception ex){
            e= ex.getMessage();
        }
        assertNull(e);

        //Correct move - Dice witch correct color and correct adj restriction
        dice = new Dice(DiceColor.Yellow);
        dice.setValue(1);
        e=null;
        try {
            testWindowPatternCard.insertDice(2,3, dice);
        }catch(Exception ex){
            e= ex.getMessage();
        }
        assertNull(e);

        //Correct move - Dice witch correct color and correct adj restriction
        dice = new Dice(DiceColor.Purple);
        dice.setValue(5);
        e=null;
        try {
            testWindowPatternCard.insertDice(2,4, dice);
        }catch(Exception ex){
            e= ex.getMessage();
        }
        assertNull(e);

        //Correct move - Dice witch correct color and correct adj restriction
        dice = new Dice(DiceColor.Purple);
        dice.setValue(3);
        e=null;
        try {
            testWindowPatternCard.insertDice(3, 0, dice);
        }catch(Exception ex){
            e= ex.getMessage();
        }
        assertNull(e);

        //Correct move - Dice witch correct color and correct adj restriction
        dice = new Dice(DiceColor.Yellow);
        dice.setValue(1);
        e=null;
        try {
            testWindowPatternCard.insertDice(3,1, dice);
        }catch(Exception ex){
            e= ex.getMessage();
        }
        assertNull(e);
        //Correct move - Dice witch correct color and correct adj restriction
        dice = new Dice(DiceColor.Red);
        dice.setValue(5);
        e=null;
        try {
            testWindowPatternCard.insertDice(3,2, dice);
        }catch(Exception ex){
            e= ex.getMessage();
        }
        assertNull(e);
        //Correct move - Dice witch correct color and correct adj restriction
        dice = new Dice(DiceColor.Green);
        dice.setValue(2);
        e=null;
        try {
            testWindowPatternCard.insertDice(3,3, dice);
        }catch(Exception ex){
            e= ex.getMessage();
        }
        assertNull(e);
        //Correct move - Dice witch correct color and correct adj restriction
        dice = new Dice(DiceColor.Blue);
        dice.setValue(4);
        e=null;
        try {
            testWindowPatternCard.insertDice(3,4, dice);
        }catch(Exception ex){
            e= ex.getMessage();
        }
        assertNull(e);
        //CliMessage cli = new CliMessage();
        //cli.showWindowPatternCard(testWindowPatternCard);

        ObjectivePublicCard p_card = new ColoredDiagonal();
        assertEquals(9, p_card.calculatePoint(testWindowPatternCard));

        //Test DifferentColorColumn card
        p_card = new DifferentColorColumn();
        assertEquals(10, p_card.calculatePoint(testWindowPatternCard));

        //Test DifferentColorRow card
        p_card = new DifferentColorRow();
        assertEquals(6, p_card.calculatePoint(testWindowPatternCard));

        p_card = new DifferentColor();
        assertEquals(12, p_card.calculatePoint(testWindowPatternCard));

        p_card = new DarkNumber();
        assertEquals(2, p_card.calculatePoint(testWindowPatternCard));

        p_card = new MidNumber();
        assertEquals(6, p_card.calculatePoint(testWindowPatternCard));

        p_card = new LightNumber();
        assertEquals(6, p_card.calculatePoint(testWindowPatternCard));

        p_card = new DifferentNumberColumn();
        assertEquals(4, p_card.calculatePoint(testWindowPatternCard));

        p_card = new DifferentNumberRow();
        assertEquals(10, p_card.calculatePoint(testWindowPatternCard));

        p_card = new DifferentNumber();
        assertEquals(5, p_card.calculatePoint(testWindowPatternCard));

        ObjectivePrivateCard pr_card = new BlueObjectivePrivateCard();
        assertEquals(19, pr_card.calculatePoint(testWindowPatternCard));

        pr_card = new GreenObjectivePrivateCard();
        assertEquals(12, pr_card.calculatePoint(testWindowPatternCard));

        pr_card = new PurpleObjectivePrivateCard();
        assertEquals(12, pr_card.calculatePoint(testWindowPatternCard));

        pr_card = new RedObjectivePrivateCard();
        assertEquals(12, pr_card.calculatePoint(testWindowPatternCard));

        pr_card = new YellowObjectivePrivateCard();
        assertEquals(12, pr_card.calculatePoint(testWindowPatternCard));


    }

    @Test
    public void testBoolInsertDice() {
        Dice dice = new Dice(DiceColor.Green);
        dice.setValue(2);
        assertTrue(testWindowPatternCard.insertDice(2, 2, dice, false, false, false));

        dice = new Dice(DiceColor.Blue);
        dice.setValue(2);
        assertTrue(testWindowPatternCard.insertDice(1, 1, dice, true, false, false));

        testWindowPatternCard.removeDice(1, 1);

        dice = new Dice(DiceColor.Yellow);
        dice.setValue(2);
        assertTrue(testWindowPatternCard.insertDice(1, 1, dice, true, true, false));

        testWindowPatternCard.removeDice(1, 1);

        dice = new Dice(DiceColor.Yellow);
        dice.setValue(2);
        assertTrue(testWindowPatternCard.insertDice(1, 1, dice, true, true, true));

        dice = new Dice(DiceColor.Yellow);
        dice.setValue(2);
        assertFalse(testWindowPatternCard.insertDice(0, 1, dice, true, true, true));

        dice = new Dice(DiceColor.Yellow);
        dice.setValue(2);
        assertFalse(testWindowPatternCard.insertDice(0, 0, dice, true, true, true));

        dice = new Dice(DiceColor.Yellow);
        dice.setValue(5);
        assertTrue(testWindowPatternCard.insertDice(0, 0, dice, true, true, true));

    }
}
