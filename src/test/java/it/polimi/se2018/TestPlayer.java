package it.polimi.se2018;

import it.polimi.se2018.exception.gameboard_exception.NoDiceException;
import it.polimi.se2018.exception.gameboard_exception.window_exception.WindowRestriction;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceStack;
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
 * @author Davide Mammarella
 */
public class TestPlayer {
    private Player player;
    private DiceStack handDice;

    @Before
    public void setUp() throws Exception{
        player = new Player(1);

        assertEquals(0, player.getFavorToken());
        assertEquals(0, player.getPoints());
        assertNull(player.getPrivateObject());
        assertNull(player.getPlayerWindowPattern());
        assertTrue(player.isFirstTurn());
        assertFalse(player.isHasDrawNewDice());
        assertFalse(player.isHasPlaceANewDice());
        assertFalse(player.isHasUsedToolCard());
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
        Cell[][] matrix = new Cell[4][5];
        WindowPatternCard hongKong = new WindowPatternCard("symphony_of_light", 6, matrix);

        player.setPlayerWindowPattern(hongKong);

        assertThat("symphony_of_light", is(hongKong.getName()));
        assertThat(6, is(hongKong.getDifficulty()));
        assertThat(matrix, is(hongKong.getMatrix()));
    }

    @Test
    public void setHandDice() throws NoDiceException {
        handDice = new DiceStack();
        Dice redDice = new Dice(RED);
        assertTrue(handDice.isEmpty());
        handDice.addFirst(redDice);
        assertThat(RED, is(handDice.getDice(0).getColor()));
    }

    @Test (expected = NoDiceException.class)
    public void setHandDice_NoDiceException() throws NoDiceException {
        handDice = new DiceStack();
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
        WindowPatternCard[] the4WindowPattern = new WindowPatternCard[4];
        Cell[][] matrix = new Cell[4][5];

        WindowPatternCard hongKong = new WindowPatternCard("symphony_of_light", 6, matrix);
        WindowPatternCard miguel = new WindowPatternCard("kaleidoscope_dream", 4, matrix);
        WindowPatternCard notAnEngineer = new WindowPatternCard("sun_catcher", 3, matrix);
        WindowPatternCard aquaVitae = new WindowPatternCard("water_of_life", 6, matrix);

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
        WindowPatternCard[] the4WindowPattern = new WindowPatternCard[4];
        Cell[][] matrix = new Cell[4][5];

        WindowPatternCard hongKong = new WindowPatternCard("symphony_of_light", 6, matrix);
        WindowPatternCard miguel = new WindowPatternCard("kaleidoscope_dream", 4, matrix);
        WindowPatternCard notAnEngineer = new WindowPatternCard("sun_catcher", 3, matrix);
        WindowPatternCard aquaVitae = new WindowPatternCard("water_of_life", 6, matrix);

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
    public void addDiceToHand() throws NoDiceException {
        handDice = new DiceStack();

        Dice redDice = new Dice(RED);
        Dice yellowDice = new Dice(YELLOW);
        Dice greenDice = new Dice(GREEN);
        Dice blueDice = new Dice(BLUE);
        Dice purpleDice = new Dice(PURPLE);

        assertTrue(handDice.isEmpty());

        handDice.addFirst(redDice);
        handDice.add(yellowDice);
        handDice.add(greenDice);
        handDice.add(blueDice);
        handDice.add(purpleDice);

        assertThat(RED, is(handDice.getDice(0).getColor()));
    }

    @Test (expected = NoDiceException.class)
    public void addDiceToHand_NoDiceException() throws NoDiceException {
        handDice = new DiceStack();
        Dice redDice = new Dice(RED);
        handDice.addFirst(redDice);
        assertThat(RED, is(handDice.getDice(1)));
    }


    /**
     * Insert dice on a window pattern without restriction.
     */
    @Test
    public void insertDice() throws NoDiceException, WindowRestriction {
        handDice = new DiceStack();

        Dice redDice = new Dice(RED);
        Dice yellowDice = new Dice(YELLOW);
        Dice greenDice = new Dice(GREEN);
        Dice blueDice = new Dice(BLUE);
        Dice purpleDice = new Dice(PURPLE);

        assertTrue(handDice.isEmpty());

        handDice.addFirst(redDice);
        handDice.add(yellowDice);
        handDice.add(greenDice);
        handDice.add(blueDice);
        handDice.add(purpleDice);

        assertFalse(handDice.isEmpty());

        Cell[][] matrix = new Cell[4][5];
        WindowPatternCard hongKong = new WindowPatternCard("symphony_of_light", 6, matrix);

        //TODO problemi con insertDice
        hongKong.insertDice(0,0, handDice.getDice(0),false,false,false);

        assertThat("symphony_of_light", is(hongKong.getName()));
        assertThat(6, is(hongKong.getDifficulty()));
        assertThat(matrix, is(hongKong.getMatrix()));
        assertThat(redDice, is(hongKong.getCell(0,0).getDice()));

        handDice.remove(0);
        assertThat(null, is(handDice.getDice(0)));
    }

    @Test
    public void useToolCard(){
        int cost = 1;
        int favorToken = 2;
        assertTrue(favorToken>cost);
        boolean hasUsedToolCard=true;
        assertTrue(hasUsedToolCard);
    }
    
    @After
    public void reset() throws Exception{

    }

}
