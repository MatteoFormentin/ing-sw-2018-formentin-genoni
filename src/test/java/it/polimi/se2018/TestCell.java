package it.polimi.se2018;

import it.polimi.se2018.exception.window_exception.CellException.RestrictionCellColorViolatedException;
import it.polimi.se2018.exception.window_exception.CellException.RestrictionCellValueViolatedException;
import it.polimi.se2018.exception.window_exception.InsertDice.NullDiceToAddException;
import it.polimi.se2018.exception.window_exception.CellException.RestrictionCellOccupiedException;
import it.polimi.se2018.exception.window_exception.InsertDice.RestrictionColorViolatedException;
import it.polimi.se2018.exception.window_exception.InsertDice.RestrictionValueViolatedException;
import it.polimi.se2018.exception.window_exception.NoDiceInThisCell;
import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceColor;
import it.polimi.se2018.model.dice.TestFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * class for test of cell's method
 *
 * @author Luca Genoni
 */
public class TestCell {
    private static final RestrictionCellValueViolatedException valueR = new RestrictionCellValueViolatedException();
    private static final RestrictionCellColorViolatedException colorR = new RestrictionCellColorViolatedException();
    private static final RestrictionCellOccupiedException occupiedR = new RestrictionCellOccupiedException();
    private static final NoDiceInThisCell noDiceInCell = new NoDiceInThisCell();
    private static final NullDiceToAddException nullDice = new NullDiceToAddException();
    private Cell[] allColor;
    private Cell[] allValue;
    private Cell free;
    private Dice[] dice3AllColor;
    private Dice[] diceRedAllValue;
    private Dice dice3Blue;

    @Before
    public void initTestWindowPatternCard() {
        allColor = new Cell[DiceColor.getNumberOfDiceColors()];
        dice3AllColor = new Dice[DiceColor.getNumberOfDiceColors()];
        allValue = new Cell[6];
        diceRedAllValue = new Dice[6];
        TestFactory factoryDice = new TestFactory();
        for (int i = 0; i < DiceColor.getNumberOfDiceColors(); i++) {
            allColor[i] = new Cell();
            allColor[i].setColorRestriction(DiceColor.getDiceColor(i));
            factoryDice.setDiceValueColor(3, DiceColor.getDiceColor(i));
            dice3AllColor[i] = factoryDice.createDice();
        }
        for (int i = 0; i < allValue.length; i++) {
            allValue[i] = new Cell();
            allValue[i].setValueRestriction(i + 1);
            factoryDice.setDiceValueColor((i + 1), DiceColor.Red);
            diceRedAllValue[i] = factoryDice.createDice();
        }
        free = new Cell();
        factoryDice.setDiceValueColor(3, DiceColor.Blue);
        dice3Blue = factoryDice.createDice();
    }

    @Test
    public void testValidateInsertCell() throws Exception {
        for(int i=0; i<DiceColor.getNumberOfDiceColors();i++) {
            //for each cell's color restriction insert a matching die
            allColor[i].insertDice(dice3AllColor[i], true, true);
            allColor[i].removeDice();
            allColor[i].insertDice(dice3AllColor[i], true, false);
            allColor[i].removeDice();

            //for each cell's color try to insert a blue 3 die
            allColor[i].insertDice(dice3Blue, false, true);
            allColor[i].removeDice();
            allColor[i].insertDice(dice3Blue, false, false);
            allColor[i].removeDice();

            //for each die with a different color try to put it in cell without restriction
            free.insertDice(dice3AllColor[i],true,true);
            free.removeDice();
            free.insertDice(dice3AllColor[i],true,false);
            free.removeDice();
            free.insertDice(dice3AllColor[i],false,true);
            free.removeDice();
            free.insertDice(dice3AllColor[i],false,false);
            free.removeDice();
        }

        for(int i=0; i<allValue.length;i++){
            //for each cell's value restriction insert a matching die
            allValue[i].insertDice(diceRedAllValue[i],true,true);
            allValue[i].removeDice();
            allValue[i].insertDice(diceRedAllValue[i],false,true);
            allValue[i].removeDice();

            //for each cell's value restriction insert a blue 3 die
            allValue[i].insertDice(dice3Blue,true,false);
            allValue[i].removeDice();
            allValue[i].insertDice(dice3Blue,false,false);
            allValue[i].removeDice();

            //for each die with a different value try to put it in cell without restriction
            free.insertDice(diceRedAllValue[i],true,true);
            free.removeDice();
            free.insertDice(diceRedAllValue[i],true,false);
            free.removeDice();
            free.insertDice(diceRedAllValue[i],false,true);
            free.removeDice();
            free.insertDice(diceRedAllValue[i],false,false);
            free.removeDice();
        }
    }

    @Test
    public void testExceptionInsertDice() throws Exception {
        //try strange cases of the dice
        allColor[0].insertDice(dice3AllColor[0], false, false);
        assertThrows(occupiedR.getClass(), () -> allColor[0].insertDice(dice3AllColor[0], false, false));
        assertThrows(nullDice.getClass(),()->allColor[0].insertDice(null,true,true));
        allColor[0].removeDice();
        assertThrows(noDiceInCell.getClass(),()->allColor[0].removeDice());
        assertThrows(nullDice.getClass(),()->allColor[0].insertDice(null,true,true));

        //try the color
        assertThrows(colorR.getClass(), () -> allColor[0].insertDice(dice3AllColor[1], true, true));
        assertThrows(noDiceInCell.getClass(), () -> allColor[0].removeDice());
        assertThrows(colorR.getClass(), () -> allColor[0].insertDice(dice3AllColor[1], true, false));
        assertThrows(noDiceInCell.getClass(), () -> allColor[0].removeDice());

        //try the value
        assertThrows(valueR.getClass(), () -> allValue[0].insertDice(diceRedAllValue[1], true, true));
        assertThrows(noDiceInCell.getClass(), () -> allColor[0].removeDice());
        assertThrows(valueR.getClass(), () -> allValue[0].insertDice(diceRedAllValue[1], false, true));
        assertThrows(noDiceInCell.getClass(), () -> allColor[0].removeDice());
    }

    @After
    public void reset() throws Exception{
        for (Cell cell : allValue) {
            if(cell.getDice()!=null) cell.removeDice();
        }
        for (Cell cell : allColor) {
            if(cell.getDice()!=null) cell.removeDice();
        }
        if(free.getDice()!=null) free.removeDice();
    }

}
