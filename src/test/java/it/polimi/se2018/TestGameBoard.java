package it.polimi.se2018;

import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceColor;
import it.polimi.se2018.model.dice.TestFactory;
import it.polimi.se2018.view.cli.CliController;

import org.junit.*;

import static org.junit.Assert.*;


public class TestGameBoard {
    private Cell[][] matrix;
    private WindowPatternCard[] testWindowPattern;
    private WindowPatternCard testWindowPatternCard;
    private TestFactory testFactory;
    private GameBoard gameBoard;

    @Before
    public void setUp() {
        String[] name = new String[4];
        for (int i = 0; i < 4; i++) name[i] = "tester" + i;
        try{
            gameBoard = new GameBoard(name);
        } catch(Exception e){
            System.err.println(e.getMessage());
        }

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
        matrix[1][0].setColorRestriction(DiceColor.Green);
        matrix[2][2].setColorRestriction(DiceColor.Red);
        matrix[2][4].setColorRestriction(DiceColor.Green);
        matrix[3][3].setColorRestriction(DiceColor.Blue);
        matrix[3][4].setColorRestriction(DiceColor.Yellow);
        matrix[0][4].setValueRestriction(1);
        matrix[1][2].setValueRestriction(5);
        matrix[1][4].setValueRestriction(4);
        matrix[2][0].setValueRestriction(3);
        matrix[3][0].setValueRestriction(2);

        testWindowPatternCard = new WindowPatternCard("testKaleidoscopicDream", 4, matrix);

        testWindowPattern = new WindowPatternCard[4];
        for (int i = 0; i < 4; i++) {
            testWindowPattern[i] = testWindowPatternCard;
        }
        for (int i = 0; i < 4; i++) {
            gameBoard.getPlayer(i).setThe4WindowPattern(testWindowPattern);
        }
        testFactory = new TestFactory();
        testFactory.setValue(1);
        testFactory.setColor(DiceColor.Blue);
    }

    @Test
    public void testSetWindow() {

        assertTrue(gameBoard.isStopGame());
        //normal move
        assertFalse(gameBoard.addNormalDiceToHandFromDraftPool(0, 0));
        assertFalse(gameBoard.useToolCard(0, 0));
        assertFalse(gameBoard.insertDice(0, 0, 0));
        assertFalse(gameBoard.nextPlayer(0));
        //tool method
        assertFalse(gameBoard.changeDiceBetweenHandAndFactory(0));
        assertFalse(gameBoard.changeDiceBetweenHandAndRoundTrack(0, 0, 0));
        assertFalse(gameBoard.moveDiceFromWindowPatternToHandWithRestriction(0,0,0,0,0));
        assertFalse(gameBoard.rollDicePool(0));
        //tool method of the player
        assertFalse(gameBoard.insertDice(0, 0, 0, false, false, false));
        assertFalse(gameBoard.moveDiceFromWindowPatternToHand(0, 0, 0));
        assertFalse(gameBoard.rollDiceInHand(0));
        assertFalse(gameBoard.increaseOrDecrease(0, true));
        assertFalse(gameBoard.oppositeFaceDice(0));
        assertFalse(gameBoard.endSpecialFirstTurn(0));
        assertTrue(gameBoard.isStopGame());
        //start set up window
        for(int i=0;i<4;i++){
            gameBoard.setWindowOfPlayer(i,0);
        }
        assertFalse(gameBoard.isStopGame());


        for (int i = 0; i < 4; i++) {
            //check player state
            assertTrue(gameBoard.getPlayer(i).isFirstTurn());
            assertFalse(gameBoard.getPlayer(i).isHasUsedToolCard());
            assertFalse(gameBoard.getPlayer(i).isHasDrawNewDice());
            assertFalse(gameBoard.getPlayer(i).isHasPlaceANewDice());
            //mossa normale per pescare il dado
            assertTrue(gameBoard.addNormalDiceToHandFromDraftPool(i, 0));

            //check player state
            assertTrue(gameBoard.getPlayer(i).isFirstTurn());
            assertFalse(gameBoard.getPlayer(i).isHasUsedToolCard());
            assertTrue(gameBoard.getPlayer(i).isHasDrawNewDice());
            assertFalse(gameBoard.getPlayer(i).isHasPlaceANewDice());
            assertNotNull(gameBoard.getPlayer(i).getHandDice().getDice(0));

            //check player state

            assertFalse(gameBoard.addNormalDiceToHandFromDraftPool(i, 0));
            assertFalse(gameBoard.useToolCard(i, -1));
            assertFalse(gameBoard.getPlayer(i).isHasUsedToolCard());
            assertFalse(gameBoard.useToolCard(i, 5));
            assertTrue(gameBoard.useToolCard(i, 1));

            assertTrue(gameBoard.insertDice(i, 0, 0, false, false, false));
            //controlla se il dado è stato veramente inserito
            assertNotNull(gameBoard.getPlayer(i).getPlayerWindowPattern().getCell(0,0).getDice());
            assertTrue(gameBoard.moveDiceFromWindowPatternToHand(i, 0, 0));
            assertTrue(gameBoard.rollDiceInHand(i));
            assertTrue(gameBoard.increaseOrDecrease(i, true));
            assertTrue(gameBoard.increaseOrDecrease(i, false));
            assertTrue(gameBoard.oppositeFaceDice(i));
            assertTrue(gameBoard.endSpecialFirstTurn(i));

            assertTrue(gameBoard.nextPlayer(i));
        }
        for (int i = 4; i < 6; i++) {
            assertFalse(gameBoard.addNormalDiceToHandFromDraftPool(i, 2));
            assertFalse(gameBoard.addNormalDiceToHandFromDraftPool(i, 2));
            assertFalse(gameBoard.useToolCard(i, -1));
            assertFalse(gameBoard.useToolCard(i, 0));
            assertFalse(gameBoard.nextPlayer(i));
        }
        for (int i = 3; i > 0; i--) {
            assertTrue(gameBoard.addNormalDiceToHandFromDraftPool(i, 0));
            assertNotNull(gameBoard.getPlayer(i).getHandDice().getDice(0));
            assertFalse(gameBoard.addNormalDiceToHandFromDraftPool(i, 2));
            assertFalse(gameBoard.useToolCard(i, -1));
            assertTrue(gameBoard.useToolCard(i, 0));
            assertTrue(gameBoard.nextPlayer(i));
        }

        assertTrue(gameBoard.nextPlayer(0));
        // for(int round=1;)
    }
}
