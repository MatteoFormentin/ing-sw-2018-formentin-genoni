package it.polimi.se2018;

import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.exception.gameboard_exception.FatalGameErrorException;
import it.polimi.se2018.exception.gameboard_exception.NoDiceException;
import it.polimi.se2018.exception.gameboard_exception.player_state_exception.AlreadyDrawANewDiceException;
import it.polimi.se2018.exception.gameboard_exception.player_state_exception.AlreadyPlaceANewDiceException;
import it.polimi.se2018.exception.gameboard_exception.tool_exception.DiceInHandToolException;
import it.polimi.se2018.exception.gameboard_exception.tool_exception.FlowTurnException;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.card.ToolCard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * class for test of cell's method
 *
 * @author Luca Genoni
 */
public class TestToolCard {
    private static final AlreadyPlaceANewDiceException placedDice = new AlreadyPlaceANewDiceException();
    private static final AlreadyDrawANewDiceException drawnDice = new AlreadyDrawANewDiceException();
    private static final NoDiceException noDice = new NoDiceException();
    private static final FlowTurnException noRightTurn = new FlowTurnException();
    private static final DiceInHandToolException noDiceInHand = new DiceInHandToolException();
    private static final FatalGameErrorException flowGameBroken = new FatalGameErrorException();
    private ToolCard toolCard;
    private Player player;

    @Before
    public void initTestWindowPatternCard(){
        toolCard= new ToolCard();
    }

    @Test
    public void testValidateInsertCell() throws GameException {

    }

    @After
    public void reset() throws Exception{

    }

}
