package it.polimi.se2018;

import it.polimi.se2018.controller.effect.EffectGame;
import it.polimi.se2018.controller.effect.InsertDice;
import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.exception.gameboard_exception.window_exception.WindowRestriction;
import it.polimi.se2018.exception.gameboard_exception.window_exception.cell_exception.RestrictionCellColorViolatedException;
import it.polimi.se2018.exception.gameboard_exception.window_exception.cell_exception.RestrictionCellValueViolatedException;
import it.polimi.se2018.exception.gameboard_exception.window_exception.insert_dice.RestrictionAdjacentFirstDiceViolatedException;
import it.polimi.se2018.exception.gameboard_exception.window_exception.insert_dice.RestrictionAdjacentViolatedException;
import it.polimi.se2018.exception.gameboard_exception.window_exception.insert_dice.RestrictionColorViolatedException;
import it.polimi.se2018.exception.gameboard_exception.window_exception.insert_dice.RestrictionValueViolatedException;
import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_input.SelectCellOfWindow;
import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.card.objective_private_card.*;
import it.polimi.se2018.model.card.objective_public_card.*;
import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceColor;
import it.polimi.se2018.model.dice.TestFactory;
import it.polimi.se2018.network.RemotePlayer;
import it.polimi.se2018.network.server.ServerController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestEffect {

  //  private static final RestrictionCellValueViolatedException valueCellR = new RestrictionCellValueViolatedException();
    private WindowPatternCard testWindowPatternCard;

    private Dice dice;
    private TestFactory factoryDice;
    private GameBoard game;
    private ServerController fakeServer;
    private EventView eventViewTest;
    private int[] monoInfo;
    private int[] doubleInfo;

    @Before
    public void initTestWindowPatternCard() {
        Cell[][] matrix;
        matrix = new Cell[4][5];
        for(int i=0; i<matrix[0].length;i++){
            matrix[0][i] = new Cell();
            matrix[1][i] = new Cell();
            matrix[1][i].setColorRestriction(DiceColor.getDiceColor(i));
            matrix[2][i] = new Cell();
            matrix[2][i].setValueRestriction(i+1);
            matrix[3][i] = new Cell();
        }
        fakeServer = new ServerController() {
            @Override
            public boolean login(RemotePlayer remotePlayer) throws PlayerAlreadyLoggedException, RoomIsFullException {
                return false;
            }

            @Override
            public void sendEventToController(EventController eventController) {

            }

            @Override
            public void startGame() {

            }

            @Override
            public void joinGame(RemotePlayer remotePlayer) {

            }

            @Override
            public void sendEventToView(EventView eventView) {
                eventViewTest= eventView;
            }

            @Override
            public RemotePlayer searchPlayerById(int id) {
                return null;
            }
        };
        testWindowPatternCard = new WindowPatternCard("test", 5, matrix);
        factoryDice= new TestFactory();
        game= new GameBoard(1,fakeServer);
        monoInfo= new int[1];
        doubleInfo= new int[2];
    }
/*
        assertThrows(adjacentR.getClass(), () ->);
*/

    @Test
    public void testEffectRight() throws GameException {
        EffectGame effectGame = new InsertDice(true,true,true,true);
        assertEquals(SelectCellOfWindow.class,effectGame.eventViewToAsk().getClass());
    }

    @Test
    public void testBoolInsertDice() throws WindowRestriction {

    }
}
