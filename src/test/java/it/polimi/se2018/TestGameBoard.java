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
    private GameBoard gameBoard;
    private Cell[][] matrix;
    private WindowPatternCard[] testWindowPattern;
    private WindowPatternCard testWindowPatternCard;
    private TestFactory testFactory;

    @Before
    public void setUp() {
        String[] name= new String[4];
        for(int i=0;i<4;i++) name[i]="tester"+i;
        gameBoard= new GameBoard(name,0);
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
        testWindowPattern = new WindowPatternCard[1];
        testWindowPattern[0]=testWindowPatternCard;
        testFactory= new TestFactory();
    }
    @Test
    public void testSetWindow() {
        for(int i=0;i<4;i++){
            assertEquals("tester"+i,gameBoard.getPlayer(i).getNickName());
        }
        assertTrue(gameBoard.isStopGame());
        assertFalse(gameBoard.changeDiceBetweenHandAndFactory(0));
        assertFalse(gameBoard.oppositeFaceDice(0));
        assertFalse(gameBoard.rollDiceInHand(0));
        assertFalse(gameBoard.useToolCard(0,2));
        assertFalse(gameBoard.selectDiceInHand(0,2));
        assertFalse(gameBoard.endSpecialFirstTurn(0));
        assertFalse(gameBoard.nextPlayer(0));
        assertFalse(gameBoard.addNormalDiceToHandFromDraftPool(0,4));
        assertTrue(gameBoard.isStopGame());
        for(int i=1;i<4;i++){
            assertTrue(gameBoard.isStopGame());
            assertTrue(gameBoard.setWindowOfPlayer(i,0));
        }
        assertTrue(gameBoard.isStopGame());
        gameBoard.getPlayer(0).setThe4WindowPattern(testWindowPattern);
        assertTrue(gameBoard.setWindowOfPlayer(0,0));
        assertFalse(gameBoard.isStopGame());

        for(int i=0;i<4;i++){
            assertTrue(gameBoard.addNormalDiceToHandFromDraftPool(i,2));
            System.out.println(gameBoard.getPlayer(i).getHandDice().getDice(0).getColor().toString()+" "+
                    gameBoard.getPlayer(i).getHandDice().getDice(0).getValue());
            assertNotNull(gameBoard.getPlayer(i).getHandDice().getDice(0));
            assertFalse(gameBoard.addNormalDiceToHandFromDraftPool(i,2));
            assertFalse(gameBoard.useToolCard(i,-1));
            assertTrue(gameBoard.useToolCard(i,0));
            assertTrue(gameBoard.nextPlayer(i));
        }
        for(int i=4;i<6;i++){
            assertFalse(gameBoard.addNormalDiceToHandFromDraftPool(i,2));
            assertFalse(gameBoard.addNormalDiceToHandFromDraftPool(i,2));
            assertFalse(gameBoard.useToolCard(i,-1));
            assertFalse(gameBoard.useToolCard(i,0));
            assertFalse(gameBoard.nextPlayer(i));
        }
        for(int i=3;i>=0;i--){
            assertTrue(gameBoard.addNormalDiceToHandFromDraftPool(i,0));
            System.out.println(gameBoard.getPlayer(i).getHandDice().getDice(0).getColor().toString()+" "+
                    gameBoard.getPlayer(i).getHandDice().getDice(0).getValue());
            assertNotNull(gameBoard.getPlayer(i).getHandDice().getDice(0));
            assertFalse(gameBoard.addNormalDiceToHandFromDraftPool(i,2));
            assertFalse(gameBoard.useToolCard(i,-1));
            assertTrue(gameBoard.useToolCard(i,0));
            assertTrue(gameBoard.nextPlayer(i));
        }
        assertFalse(gameBoard.nextPlayer(0));


    }

}
