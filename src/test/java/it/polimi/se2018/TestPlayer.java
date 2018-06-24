package it.polimi.se2018;

import it.polimi.se2018.exception.gameboard_exception.window_exception.cell_exception.NoDiceInThisCell;
import it.polimi.se2018.exception.gameboard_exception.window_exception.cell_exception.RestrictionCellColorViolatedException;
import it.polimi.se2018.exception.gameboard_exception.window_exception.cell_exception.RestrictionCellOccupiedException;
import it.polimi.se2018.exception.gameboard_exception.window_exception.cell_exception.RestrictionCellValueViolatedException;
import it.polimi.se2018.exception.gameboard_exception.window_exception.cell_exception.NullDiceToAddException;
import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.dice.Dice;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * class for test of cell's method
 *
 * @author Luca Genoni
 */
public class TestPlayer {
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

    }

    @Test
    public void testValidateInsertCell() throws Exception {

    }

    @After
    public void reset() throws Exception{

    }

}
