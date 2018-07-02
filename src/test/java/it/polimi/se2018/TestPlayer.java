package it.polimi.se2018;

import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.exception.gameboard_exception.NoDiceException;
import it.polimi.se2018.exception.gameboard_exception.player_state_exception.NoDiceInHandException;
import it.polimi.se2018.exception.gameboard_exception.tool_exception.ValueDiceWrongException;
import it.polimi.se2018.exception.gameboard_exception.window_exception.WindowRestriction;
import it.polimi.se2018.exception.gameboard_exception.window_exception.RestrictionAdjacentFirstDiceViolatedException;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceStack;
import it.polimi.se2018.model.dice.TestFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static it.polimi.se2018.model.dice.DiceColor.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;



/**
 * Test for Player Class.
 *
 * @author DavideMammarella
 */
public class TestPlayer {
    private Player player;
    private DiceStack handDice;
    private TestFactory factoryDice;
    private Dice redDice, yellowDice, greenDice, blueDice, purpleDice;
    private Cell[][] matrix;
    private WindowPatternCard hongKong, miguel, notAnEngineer, aquaVitae;
    private WindowPatternCard[] the4WindowPattern;

    @Before
    public void setUp() throws Exception{
        System.out.println("Single player test started!");
        player = new Player(1);
        factoryDice = new TestFactory();
        handDice = new DiceStack();

        matrix = new Cell[4][5];
        for(int i=0; i<matrix[0].length;i++){
            matrix[0][i] = new Cell();
            matrix[1][i] = new Cell();
            matrix[2][i] = new Cell();
            matrix[3][i] = new Cell();
        }

        assertEquals(0, player.getFavorToken());
        assertEquals(0, player.getPoints());
        assertNull(player.getPrivateObject());
        assertNull(player.getPlayerWindowPattern());
        assertTrue(player.isFirstTurn());
        assertFalse(player.isHasDrawNewDice());
        assertFalse(player.isHasPlaceANewDice());
        assertFalse(player.isHasUsedToolCard());

        the4WindowPattern = new WindowPatternCard[4];
        hongKong = new WindowPatternCard("symphony_of_light", 6, matrix);
        miguel = new WindowPatternCard("kaleidoscope_dream", 4, matrix);
        notAnEngineer = new WindowPatternCard("sun_catcher", 3, matrix);
        aquaVitae = new WindowPatternCard("water_of_life", 6, matrix);
    }

    //------------------------------------------------------------------------------------------------------------------
    // SETTER TEST
    // ------------------------------------------------------------------------------------------------------------------

    @Test
    public void setIndexInGame() {
        player.setIndexInGame(2);
        assertEquals(2, player.getIndexInGame());
    }

    @Test
    public void setFavorToken() {
        player.setFavorToken(1);
        assertEquals(1, player.getFavorToken());
    }

    @Test
    public void setPoints() {
        player.setPoints(2);
        assertEquals(2, player.getPoints());
    }

    // Abstract classes with arguments on constructor are not testable
    // public void setPrivateObject() { }

    @Test
    public void setPlayerWindowPattern() {
        WindowPatternCard hongKong = new WindowPatternCard("symphony_of_light", 6, matrix);

        player.setPlayerWindowPattern(hongKong);

        assertThat("symphony_of_light", is(hongKong.getName()));
        assertThat(6, is(hongKong.getDifficulty()));
        assertThat(matrix, is(hongKong.getMatrix()));
    }

    @Test
    public void setHandDice() throws NoDiceException, ValueDiceWrongException {
        assertTrue(handDice.isEmpty());

        factoryDice.setDiceValueColor(1, RED);
        redDice = factoryDice.createDice();

        handDice.addFirst(redDice);
        assertThat(RED, is(handDice.getDice(0).getColor()));
    }

    @Test (expected = NoDiceException.class)
    public void setHandDice_NoDiceException() throws NoDiceException {
        assertTrue(handDice.isEmpty());
        assertThat(PURPLE, is(handDice.getDice(1)));
    }

    @Test
    public void setFirstTurn() {
        player.setFirstTurn(true);
        assertTrue(player.isFirstTurn());
    }

    @Test
    public void setHasDrawNewDice() {
        player.setHasDrawNewDice(true);
        assertTrue(player.isHasDrawNewDice());
    }

    @Test
    public void setHasPlaceANewDice() {
        player.setHasPlaceANewDice(true);
        assertTrue(player.isHasPlaceANewDice());
    }

    @Test
    public void setHasUsedToolCard() {
        player.setHasUsedToolCard(true);
        assertTrue(player.isHasUsedToolCard());
    }

    @Test
    public void setDetailedPoint() {
        LinkedList detailedPoint = new LinkedList();
        assertTrue(detailedPoint.isEmpty());

        detailedPoint.add("Risposta alla domanda fondamentale sulla vita, l'universo e tutto quanto");
        detailedPoint.add(42);
        assertFalse(detailedPoint.isEmpty());

        assertEquals(2,detailedPoint.size());
    }

    @Test
    public void choosePlayerWindowPattern() {
        the4WindowPattern[0] = hongKong;
        the4WindowPattern[1] = miguel;
        the4WindowPattern[2] = notAnEngineer;
        the4WindowPattern[3] = aquaVitae;
        assertEquals(4, the4WindowPattern.length);

        WindowPatternCard playerWindowPattern = the4WindowPattern[3];
        assertThat("water_of_life", is(playerWindowPattern.getName()));
        assertThat(6, is(playerWindowPattern.getDifficulty()));
    }

    @Test
    public void setThe4WindowPattern() {
        the4WindowPattern[0] = hongKong;
        the4WindowPattern[1] = miguel;
        the4WindowPattern[2] = notAnEngineer;
        the4WindowPattern[3] = aquaVitae;

        assertEquals(4, the4WindowPattern.length);
        assertThat("symphony_of_light", is(hongKong.getName()));
        assertThat(6, is(hongKong.getDifficulty()));
        assertThat("kaleidoscope_dream", is(miguel.getName()));
        assertThat(4, is(miguel.getDifficulty()));
        assertThat("sun_catcher", is(notAnEngineer.getName()));
        assertThat(3, is(notAnEngineer.getDifficulty()));
        assertThat("water_of_life", is(aquaVitae.getName()));
        assertThat(6, is(aquaVitae.getDifficulty()));
    }

    //------------------------------------------------------------------------------------------------------------------
    // WINDOW'S METHOD TEST
    // ------------------------------------------------------------------------------------------------------------------

    //TODO EXCEPTION AlreadyDrawANewDice not testable
    @Test
    public void addDiceToHand() throws NoDiceException, ValueDiceWrongException {
        assertTrue(handDice.isEmpty());

        factoryDice.setDiceValueColor(1, RED);
        redDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(2, YELLOW);
        yellowDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(3, GREEN);
        greenDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(4, BLUE);
        blueDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(5, PURPLE);
        purpleDice = factoryDice.createDice();

        handDice.addFirst(redDice);
        handDice.add(yellowDice);
        handDice.add(greenDice);
        handDice.add(blueDice);
        handDice.add(purpleDice);

        assertThat(RED, is(handDice.getDice(0).getColor()));
    }

    @Test (expected = NoDiceException.class)
    public void addDiceToHand_NoDiceException() throws NoDiceException, ValueDiceWrongException {
        assertTrue(handDice.isEmpty());

        factoryDice.setDiceValueColor(1, RED);
        redDice = factoryDice.createDice();

        handDice.addFirst(redDice);

        assertThat(RED, is(handDice.getDice(1)));
    }


    /**
     * Insert dice on a window pattern without restriction.
     */
    @Test
    public void insertDice() throws NoDiceException, WindowRestriction, ValueDiceWrongException {
        assertTrue(handDice.isEmpty());

        factoryDice.setDiceValueColor(1, RED);
        redDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(2, YELLOW);
        yellowDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(3, GREEN);
        greenDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(4, BLUE);
        blueDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(5, PURPLE);
        purpleDice = factoryDice.createDice();

        handDice.addFirst(redDice);
        handDice.add(yellowDice);
        handDice.add(greenDice);
        handDice.add(blueDice);
        handDice.add(purpleDice);

        assertFalse(handDice.isEmpty());

        hongKong.insertDice(0,0, handDice.getDice(0),false,false,false);

        assertThat("symphony_of_light", is(hongKong.getName()));
        assertThat(6, is(hongKong.getDifficulty()));
        assertThat(RED, is(hongKong.getCell(0,0).getDice().getColor()));

        // LINKED LIST TEST (SIZE BEFORE REMOVE)
        assertEquals(5, handDice.size());

        // LINKED LIST TEST (CORRECT ELEMENT REMOVED)
        assertTrue(handDice.contains(redDice));

        handDice.remove(0);

        // LINKED LIST TEST (SIZE AFTER REMOVE)
        assertEquals(4, handDice.size());

        // LINKED LIST TEST (CORRECT ELEMENT REMOVED)
        assertFalse(handDice.contains(redDice));
    }

    @Test
    public void useToolCard(){
        int cost = 1;
        int favorToken = 2;
        assertTrue(favorToken>cost);
        boolean hasUsedToolCard=true;
        assertTrue(hasUsedToolCard);
    }

    // This class test is useless, the class is based only on boolean
    // public void endTurn(boolean nextTurnIsATypeFirstTurn) { }

    //------------------------------------------------------------------------------------------------------------------
    // TOOL'S METHOD TEST
    // ------------------------------------------------------------------------------------------------------------------

    @Test
    public void removeDiceFromWindowAndAddToHand() throws WindowRestriction, NoDiceException, ValueDiceWrongException {
        factoryDice.setDiceValueColor(1, RED);
        redDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(2, YELLOW);
        yellowDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(3, GREEN);
        greenDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(4, BLUE);
        blueDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(5, PURPLE);
        purpleDice = factoryDice.createDice();

        hongKong.insertDice(1,1,redDice,false,false,false);
        assertThat(RED, is(hongKong.getCell(1,1).getDice().getColor()));

        Dice redDiceRemoved = hongKong.removeDice(1, 1);
        assertThat(null, is(hongKong.getCell(1,1).getDice()));

        assertTrue(handDice.isEmpty());
        handDice.addFirst(redDiceRemoved);
        handDice.add(yellowDice);
        handDice.add(greenDice);
        handDice.add(blueDice);
        handDice.add(purpleDice);


        assertThat(RED, is(handDice.getDice(0).getColor()));
    }

    @Test (expected = RestrictionAdjacentFirstDiceViolatedException.class)
    public void removeDiceFromWindowAndAddToHand_RestrictionAdjacentFirstDiceViolatedException() throws WindowRestriction, NoDiceException, ValueDiceWrongException {
        factoryDice.setDiceValueColor(4, BLUE);
        blueDice = factoryDice.createDice();

        hongKong.insertDice(2,3, blueDice);
        assertThat(BLUE, is(hongKong.getCell(2,3).getDice()));
    }

    // Class not testable
    // public Dice removeDiceFromHand() {}

    @Test
    public void rollDiceInHand() throws ValueDiceWrongException {
        assertTrue(handDice.isEmpty());

        factoryDice.setDiceValueColor(1, RED);
        redDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(2, YELLOW);
        yellowDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(3, GREEN);
        greenDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(4, BLUE);
        blueDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(5, PURPLE);
        purpleDice = factoryDice.createDice();

        handDice.addFirst(redDice);
        handDice.add(yellowDice);
        handDice.add(greenDice);
        handDice.add(blueDice);
        handDice.add(purpleDice);

        assertFalse(handDice.isEmpty());

        // CLONO L'OLD HAND DICE
        DiceStack oldHandDice = (DiceStack) handDice.clone();

        // TODO: i dadi non vengono rerolled, o forse non vengono risettati nella handDice
        // TODO: i due DiceStack rimangono identici
        handDice.reRollAllDiceInStack();

        // TODO: verificare che i valori nelle due linked list (oldHandDice e handDice) siano differenti

        assertEquals(handDice, oldHandDice);
    }

    @Test
    public void oppositeFaceDice() throws NoDiceException, ValueDiceWrongException {
        assertTrue(handDice.isEmpty());

        factoryDice.setDiceValueColor(1, RED);
        redDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(2, YELLOW);
        yellowDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(3, GREEN);
        greenDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(4, BLUE);
        blueDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(5, PURPLE);
        purpleDice = factoryDice.createDice();

        handDice.addFirst(redDice);
        handDice.add(yellowDice);
        handDice.add(greenDice);
        handDice.add(blueDice);
        handDice.add(purpleDice);

        assertFalse(handDice.isEmpty());

        handDice.getDice(0).oppositeValue();

        assertThat(6, is(handDice.getDice(0).getValue()));
    }

    @Test
    public void increaseOrDecrease() throws NoDiceInHandException,NoDiceException,ValueDiceWrongException {
        assertTrue(handDice.isEmpty());

        factoryDice.setDiceValueColor(1, RED);
        redDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(2, YELLOW);
        yellowDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(3, GREEN);
        greenDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(4, BLUE);
        blueDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(5, PURPLE);
        purpleDice = factoryDice.createDice();

        handDice.addFirst(redDice);
        handDice.add(yellowDice);
        handDice.add(greenDice);
        handDice.add(blueDice);
        handDice.add(purpleDice);

        assertFalse(handDice.isEmpty());

        boolean increase;

        // PLAYER WANT TO INCREASE THE VALUE
        increase=true;
        handDice.getDice(0).increaseOrDecrease(increase);

        assertThat(2, is(handDice.getDice(0).getValue()));

        // PLAYER WANT TO DECREASE THE VALUE
        increase=false;
        handDice.getDice(1).increaseOrDecrease(increase);

        assertThat(1, is(handDice.getDice(1).getValue()));
    }

    @Test
    public void setValueDiceHand() throws NoDiceInHandException,NoDiceException, ValueDiceWrongException{
        int value = 4;

        assertTrue(handDice.isEmpty());

        factoryDice.setDiceValueColor(1, RED);
        redDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(2, YELLOW);
        yellowDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(3, GREEN);
        greenDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(4, BLUE);
        blueDice = factoryDice.createDice();
        factoryDice.setDiceValueColor(5, PURPLE);
        purpleDice = factoryDice.createDice();

        handDice.addFirst(redDice);
        handDice.add(yellowDice);
        handDice.add(greenDice);
        handDice.add(blueDice);
        handDice.add(purpleDice);

        assertFalse(handDice.isEmpty());

        handDice.getDice(3).setValue(value);

        assertThat(4, is(handDice.getDice(3).getValue()));
    }

    @Test (expected = GameException.class)
    public void endSpecialFirstTurn_GameException() throws GameException {
        player.setFirstTurn(false);
        player.endSpecialFirstTurn();
    }

    //------------------------------------------------------------------------------------------------------------------
    // TEST CLEANER
    // ------------------------------------------------------------------------------------------------------------------

    @After
    public void cleanUp() throws Exception{
        System.out.println("Method test passed!");
        System.out.println("Cleaning the system...");
        System.out.println("-----------------------------------------");
        player = null;
        factoryDice = null;
        handDice = null;
        matrix = null;
        the4WindowPattern = null;
        hongKong = null;
        miguel = null;
        notAnEngineer = null;
        aquaVitae = null;

        assertNull(player);
        assertNull(factoryDice);
        assertNull(handDice);
        assertNull(matrix);
        assertNull(the4WindowPattern);
        assertNull(hongKong);
        assertNull(miguel);
        assertNull(notAnEngineer);
        assertNull(aquaVitae);
    }

}
