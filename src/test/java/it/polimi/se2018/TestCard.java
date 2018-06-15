package it.polimi.se2018;

import it.polimi.se2018.exception.window_exception.cell_exception.RestrictionCellColorViolatedException;
import it.polimi.se2018.exception.window_exception.cell_exception.RestrictionCellValueViolatedException;
import it.polimi.se2018.exception.window_exception.insert_dice.RestrictionAdjacentFirstDiceViolatedException;
import it.polimi.se2018.exception.window_exception.insert_dice.RestrictionAdjacentViolatedException;
import it.polimi.se2018.exception.window_exception.insert_dice.RestrictionColorViolatedException;
import it.polimi.se2018.exception.window_exception.insert_dice.RestrictionValueViolatedException;
import it.polimi.se2018.exception.window_exception.WindowRestriction;
import it.polimi.se2018.model.card.objective_private_card.*;
import it.polimi.se2018.model.card.objective_public_card.*;
import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceColor;
import it.polimi.se2018.model.dice.TestFactory;
import org.junit.*;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestCard {

    private static final RestrictionCellValueViolatedException valueCellR = new RestrictionCellValueViolatedException();
    private static final RestrictionCellColorViolatedException colorCellR = new RestrictionCellColorViolatedException();
    private static final RestrictionAdjacentViolatedException adjacentR = new RestrictionAdjacentViolatedException();
    private static final RestrictionValueViolatedException valueR = new RestrictionValueViolatedException();
    private static final RestrictionColorViolatedException colorR = new RestrictionColorViolatedException();
    private static final RestrictionAdjacentFirstDiceViolatedException firstR = new RestrictionAdjacentFirstDiceViolatedException();
    private WindowPatternCard testWindowPatternCard;
    private Dice dice;
    private TestFactory factoryDice;

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
        matrix[0][1].setColorRestriction(DiceColor.GREEN);
        matrix[0][2].setColorRestriction(DiceColor.BLUE);
        matrix[0][3].setColorRestriction(DiceColor.PURPLE);
        matrix[0][4].setValueRestriction(2);

        matrix[1][0].setColorRestriction(DiceColor.PURPLE);
        matrix[1][4].setColorRestriction(DiceColor.YELLOW);

        matrix[2][0].setColorRestriction(DiceColor.YELLOW);
        matrix[2][2].setValueRestriction(6);
        matrix[2][4].setColorRestriction(DiceColor.PURPLE);

        matrix[3][0].setValueRestriction(1);
        matrix[3][3].setColorRestriction(DiceColor.GREEN);
        matrix[3][4].setValueRestriction(4);

        testWindowPatternCard = new WindowPatternCard("test", 5, matrix);
        factoryDice= new TestFactory();
    }
/*
        assertThrows(adjacentR.getClass(), () ->);
*/

    @Test
    public void testCard() throws WindowRestriction {

        //diagonal public object
        ObjectivePublicCard p_card = new ColoredDiagonal();
//        assertEquals(0, p_card.calculatePoint(testWindowPatternCard));


        //color public object
        p_card = new DifferentColorColumn();
        assertEquals(25, p_card.calculatePoint(testWindowPatternCard));

        p_card = new DifferentColorRow();
        assertEquals(24, p_card.calculatePoint(testWindowPatternCard));

        p_card = new DifferentColor();
        assertEquals(0, p_card.calculatePoint(testWindowPatternCard));

        //number public object
        p_card = new DifferentNumberColumn();
        assertEquals(20, p_card.calculatePoint(testWindowPatternCard));

        p_card = new DifferentNumberRow();
        assertEquals(20, p_card.calculatePoint(testWindowPatternCard));

        p_card = new DifferentNumber();
        assertEquals(0, p_card.calculatePoint(testWindowPatternCard));

        //set dice public object
        p_card = new DarkNumber();
        assertEquals(0, p_card.calculatePoint(testWindowPatternCard));

        p_card = new MidNumber();
        assertEquals(0, p_card.calculatePoint(testWindowPatternCard));

        p_card = new LightNumber();
        assertEquals(0, p_card.calculatePoint(testWindowPatternCard));

        //private Object
        ObjectivePrivateCard pr_card = new BlueObjectivePrivateCard();
        assertEquals(0, pr_card.calculatePoint(testWindowPatternCard));

        pr_card = new GreenObjectivePrivateCard();
        assertEquals(0, pr_card.calculatePoint(testWindowPatternCard));

        pr_card = new PurpleObjectivePrivateCard();
        assertEquals(0, pr_card.calculatePoint(testWindowPatternCard));

        pr_card = new RedObjectivePrivateCard();
        assertEquals(0, pr_card.calculatePoint(testWindowPatternCard));

        pr_card = new YellowObjectivePrivateCard();
        assertEquals(0, pr_card.calculatePoint(testWindowPatternCard));

        factoryDice.setDiceValueColor(1, DiceColor.BLUE);
        dice = factoryDice.createDice();
        testWindowPatternCard.insertDice(3, 0, dice);

        //Correct move - Die witch correct color and correct adj restriction
        factoryDice.setDiceValueColor(2, DiceColor.GREEN);
        dice = factoryDice.createDice();
        testWindowPatternCard.insertDice(3, 1, dice);

        //3° die
        factoryDice.setDiceValueColor(4, DiceColor.PURPLE);
        dice = factoryDice.createDice();
        testWindowPatternCard.insertDice(3, 2, dice);

        //4° die
        factoryDice.setDiceValueColor(5, DiceColor.GREEN);
        dice = factoryDice.createDice();
        testWindowPatternCard.insertDice(3, 3, dice);

        //5° die
        factoryDice.setDiceValueColor(4, DiceColor.RED);
        dice = factoryDice.createDice();
        testWindowPatternCard.insertDice(3, 4, dice);

        //****************************************************************************************
        //6° die
        factoryDice.setDiceValueColor(3, DiceColor.YELLOW);
        dice = factoryDice.createDice();
        testWindowPatternCard.insertDice(2, 0, dice);

        //7° die
        factoryDice.setDiceValueColor(1, DiceColor.BLUE);
        dice = factoryDice.createDice();
        testWindowPatternCard.insertDice(2, 1, dice);

        //8° die
        factoryDice.setDiceValueColor(6, DiceColor.GREEN);
        dice = factoryDice.createDice();
        testWindowPatternCard.insertDice(2, 2, dice);

        //9° die
        factoryDice.setDiceValueColor(4, DiceColor.RED);
        dice = factoryDice.createDice();
        testWindowPatternCard.insertDice(2, 3, dice);

        //10° die
        factoryDice.setDiceValueColor(2, DiceColor.PURPLE);
        dice = factoryDice.createDice();
        testWindowPatternCard.insertDice(2, 4, dice);

        //****************************************************************************************
        //11° die
        factoryDice.setDiceValueColor(3, DiceColor.YELLOW);
        dice = factoryDice.createDice();
        testWindowPatternCard.insertDice(1, 4, dice);

        //12° die
        factoryDice.setDiceValueColor(2, DiceColor.GREEN);
        dice = factoryDice.createDice();
        testWindowPatternCard.insertDice(1, 3, dice);

        //13° die
        factoryDice.setDiceValueColor(5, DiceColor.RED);
        dice = factoryDice.createDice();
        testWindowPatternCard.insertDice(1, 2, dice);

        //14° die
        factoryDice.setDiceValueColor(6, DiceColor.YELLOW);
        dice = factoryDice.createDice();
        testWindowPatternCard.insertDice(1, 1, dice);

        //15° die
        factoryDice.setDiceValueColor(1, DiceColor.PURPLE);
        dice = factoryDice.createDice();
        testWindowPatternCard.insertDice(1, 0, dice);

        //****************************************************************************************
        //16° die
        factoryDice.setDiceValueColor(5, DiceColor.YELLOW);
        dice = factoryDice.createDice();
        testWindowPatternCard.insertDice(0, 0, dice);

        //17° die
        factoryDice.setDiceValueColor(4, DiceColor.GREEN);
        dice = factoryDice.createDice();
        testWindowPatternCard.insertDice(0, 1, dice);

        //18° die
        factoryDice.setDiceValueColor(2, DiceColor.BLUE);
        dice = factoryDice.createDice();
        testWindowPatternCard.insertDice(0, 2, dice);

        //19° die
        factoryDice.setDiceValueColor(3, DiceColor.PURPLE);
        dice = factoryDice.createDice();
        testWindowPatternCard.insertDice(0, 3, dice);

        //20° die
        factoryDice.setDiceValueColor(2, DiceColor.RED);
        dice = factoryDice.createDice();
        testWindowPatternCard.insertDice(0, 4, dice);

        //diagonal public object
        p_card = new ColoredDiagonal();
        assertEquals(12, p_card.calculatePoint(testWindowPatternCard));

        //color public object
        p_card = new DifferentColorColumn();
        assertEquals(5, p_card.calculatePoint(testWindowPatternCard));

        p_card = new DifferentColorRow();
        assertEquals(12, p_card.calculatePoint(testWindowPatternCard));

        p_card = new DifferentColor();
        assertEquals(12, p_card.calculatePoint(testWindowPatternCard));

        //number public object
        p_card = new DifferentNumberColumn();
        assertEquals(12, p_card.calculatePoint(testWindowPatternCard));

        p_card = new DifferentNumberRow();
        assertEquals(10, p_card.calculatePoint(testWindowPatternCard));

        p_card = new DifferentNumber();
        assertEquals(10, p_card.calculatePoint(testWindowPatternCard));

        //set dice public object
        p_card = new DarkNumber();
        assertEquals(4, p_card.calculatePoint(testWindowPatternCard));

        p_card = new MidNumber();
        assertEquals(6, p_card.calculatePoint(testWindowPatternCard));

        p_card = new LightNumber();
        assertEquals(6, p_card.calculatePoint(testWindowPatternCard));

        //private Object
        pr_card = new BlueObjectivePrivateCard();
        assertEquals(4, pr_card.calculatePoint(testWindowPatternCard));

        pr_card = new GreenObjectivePrivateCard();
        assertEquals(19, pr_card.calculatePoint(testWindowPatternCard));

        pr_card = new PurpleObjectivePrivateCard();
        assertEquals(10, pr_card.calculatePoint(testWindowPatternCard));

        pr_card = new RedObjectivePrivateCard();
        assertEquals(15, pr_card.calculatePoint(testWindowPatternCard));

        pr_card = new YellowObjectivePrivateCard();
        assertEquals(17, pr_card.calculatePoint(testWindowPatternCard));
    }

    @Test
    public void testBoolInsertDice() throws WindowRestriction {
        factoryDice.setDiceValueColor(1, DiceColor.BLUE);
        dice = factoryDice.createDice();

        //Wrong move - First die cant be placed on board center.
        assertThrows(firstR.getClass(), () -> testWindowPatternCard.insertDice(1, 2, dice));
        assertThrows(firstR.getClass(), () -> testWindowPatternCard.insertDice(2, 2, dice));
        //Wrong move - Try to place a First die in a cell with restriction but on correct position (board edge)
        assertThrows(valueCellR.getClass(), () -> testWindowPatternCard.insertDice(0, 0, dice));
        assertThrows(colorCellR.getClass(), () -> testWindowPatternCard.insertDice(2, 0, dice));

        //Wrong move - First die without adjacent restriction but other one
        assertThrows(valueCellR.getClass(), () -> testWindowPatternCard.insertDice(2, 2, dice,false,true,true));
        assertThrows(valueCellR.getClass(), () -> testWindowPatternCard.insertDice(2, 2, dice,false,false,true));
        assertThrows(colorCellR.getClass(), () -> testWindowPatternCard.insertDice(0, 1, dice,false,true,true));
        assertThrows(colorCellR.getClass(), () -> testWindowPatternCard.insertDice(0, 1, dice,false,true,false));

        //Correct move - first die on edge and with correct number
        testWindowPatternCard.insertDice(3, 0, dice);

        //Wrong move - Try to place a die in a cell with color restriction near a die with same color
        factoryDice.setDiceValueColor(2, DiceColor.BLUE);
        dice = factoryDice.createDice();
        assertThrows(colorR.getClass(), () -> testWindowPatternCard.insertDice(2, 0, dice));

        //Wrong move - Try to place a die in a cell near a die with same color
        assertThrows(colorR.getClass(), () -> testWindowPatternCard.insertDice(3, 1, dice));

        //Wrong move - Try to place a die in a cell with color restriction near a die with same value
        factoryDice.setDiceValueColor(1, DiceColor.GREEN);
        dice = factoryDice.createDice();
        assertThrows(valueR.getClass(), () -> testWindowPatternCard.insertDice(2, 0, dice));

        //Wrong move - Try to place a die in a cell near a die with same value
        assertThrows(valueR.getClass(), () -> testWindowPatternCard.insertDice(3, 1, dice));

        //Wrong move - Try to place a die in a cell away from some dice
        assertThrows(adjacentR.getClass(), () -> testWindowPatternCard.insertDice(3, 3, dice));

        //Correct move - Die witch correct color and correct adj restriction
        dice.setValue(2);
        testWindowPatternCard.insertDice(3, 1, dice);

        //from now on all correct move
        //3° die
        factoryDice.setDiceValueColor(3, DiceColor.YELLOW);
        dice = factoryDice.createDice();
        testWindowPatternCard.insertDice(2, 0, dice);
    }
}
