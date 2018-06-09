package it.polimi.se2018.controller;

import it.polimi.se2018.exception.GameboardException.CurrentPlayerException;
import it.polimi.se2018.exception.GameboardException.WindowPatternAlreadyTakenException;
import it.polimi.se2018.exception.GameboardException.WindowSettingCompleteException;
import it.polimi.se2018.exception.PlayerException.AlreadyPlaceANewDiceException;
import it.polimi.se2018.list_event.event_received_by_controller.*;
import it.polimi.se2018.list_event.event_received_by_view.*;
import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.network.RemotePlayer;
import it.polimi.se2018.network.server.ServerController;
import it.polimi.se2018.utils.TimerCallback;
import it.polimi.se2018.utils.TimerThread;

import java.lang.instrument.IllegalClassFormatException;
import java.util.ArrayList;

/**
 * Class that implements the {@code ControllerVisitor} for execute the event that the view produced
 *
 * @author Matteo Formentin
 * @author Luca Genoni
 */

public class Controller implements ControllerVisitor, TimerCallback {
    private GameBoard gameBoard;
    private HandlerToolCard handlerToolCard;
    private int playerNumber;
    //Server in cui si setterà la partita
    private ServerController server;

    //Player
    private ArrayList<RemotePlayer> players;
    private boolean toolcard;

    long PLAYER_TIMEOUT = 20000; //ms!!
    private TimerThread playerTimeout;

    /**
     * Controller constructor.
     *
     * @param server server on when the game is on.
     * @author Davide Mammarella
     */
    public Controller(ServerController server, int playerNumber) {
        this.server = server;
        this.playerNumber = playerNumber;
        System.out.println("CONTROLLER CREATED!!!!!!!!!!!");
        gameBoard = new GameBoard(playerNumber, server);
        handlerToolCard = new HandlerToolCard(gameBoard, this);
        toolcard = false;

        playerTimeout = new TimerThread(this, PLAYER_TIMEOUT);
    }


    // EX UPDATE
    public void sendEventToController(EventController event) {
        if (toolcard) handlerToolCard.sendEventToHandlerToolCard(event);//pass to handler toolcard
        else event.accept(this);
    }

    //the handler respond with this method
    public void sendEventToView(EventView event) {
        server.sendEventToView(event);
    }

    //*****************************************Visitor Pattern************************************************************************
    //*****************************************Visitor Pattern************************************************************************
    //*****************************************Visitor Pattern************************************************************************
    //*****************************************Visitor Pattern************************************************************************

    /**
     * visit the event for select the window Pattern for the game
     *
     * @param event SelectInitialWindowPatternCardController
     */
    @Override
    public void visit(SelectInitialWindowPatternCardController event) {
        try {
            gameBoard.setWindowOfPlayer(event.getPlayerId(), event.getSelectedIndex());
            OkMessage packet = new OkMessage("Hai scelto la window Pattern", false);
            packet.setPlayerId(event.getPlayerId());
            sendEventToView(packet);
        } catch (WindowSettingCompleteException ex) {
            for (int i = 0; i < playerNumber; i++) {
                EventView packetFineInit = new InitialEnded();
                packetFineInit.setPlayerId(i);
                sendEventToView(packetFineInit);
            }
            sendWaitTurnToAllTheNonCurrent(gameBoard.getIndexCurrentPlayer());
            StartPlayerTurn turnPacket = new StartPlayerTurn();
            turnPacket.setPlayerId(gameBoard.getIndexCurrentPlayer());
            System.err.println("inizia il vero gioco con il giocatore" + gameBoard.getIndexCurrentPlayer());
            server.sendEventToView(turnPacket);
            playerTimeout.shutdown();
            playerTimeout.startThread();
        } catch (WindowPatternAlreadyTakenException ex) {
            System.err.println(ex.getMessage());
            showErrorMessageNoShowTurn(ex, event.getPlayerId());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            showErrorMessageNoShowTurn(ex, event.getPlayerId());
            InitialWindowPatternCard packet = new InitialWindowPatternCard();
            packet.setPlayerId(event.getPlayerId());
            server.sendEventToView(packet);
        }
    }

    @Override
    public void visit(SelectDiceFromDraftpoolController event) {
        try {
            gameBoard.addNewDiceToHandFromDicePool(event.getPlayerId(), event.getIndex());
            OkMessage packet = new OkMessage("Hai pescato il dado", true);
            packet.setPlayerId(event.getPlayerId());
            sendEventToView(packet);
        } catch (Exception ex) {
            showErrorMessageAndShowTurn(ex, event.getPlayerId());
        }
    }

    @Override
    public void visit(SelectCellOfWindowController event) {
        try {
            gameBoard.insertDice(event.getPlayerId(), event.getLine(), event.getColumn());
            OkMessage packet = new OkMessage("Hai inserito il dado", true);
            packet.setPlayerId(event.getPlayerId());
            sendEventToView(packet);
        } catch (AlreadyPlaceANewDiceException ex) {
            showErrorMessageAndShowTurn(ex, event.getPlayerId());
        } catch (Exception ex) {
            showErrorMessageAndShowTurn(ex, event.getPlayerId());
        }
    }

    public void visit(SelectToolCardController event) {
        if (gameBoard.getIndexCurrentPlayer() == event.getPlayerId()) {
            //TODO sostituire con il system.out l'attivazione della toolcard
            System.out.println("il giocatore con id :" + event.getPlayerId() + " vuole giocare la " + event.getIndexToolCard() + " Toolcard");
            showErrorMessageAndShowTurn(new IllegalClassFormatException(), event.getPlayerId());
        } else {
            showErrorMessageAndShowTurn(new CurrentPlayerException(), event.getPlayerId());
        }
        //TODO settare il flag della toolcard to true e girare il pacchetto all'handler oppure viene fatto dal'handler?
    }

    @Override
    public void visit(EndTurnController event) {
        try {
            gameBoard.nextPlayer(event.getPlayerId());
            sendWaitTurnToAllTheNonCurrent(gameBoard.getIndexCurrentPlayer());
            StartPlayerTurn turnPacket = new StartPlayerTurn();
            turnPacket.setPlayerId(gameBoard.getIndexCurrentPlayer());
            System.err.println("cambiato il turno tocca a " + gameBoard.getIndexCurrentPlayer());
            server.sendEventToView(turnPacket);

            //Restart timer
            playerTimeout.shutdown();
            playerTimeout.startThread();
        } catch (Exception ex) {
            showErrorMessageAndShowTurn(ex, event.getPlayerId());
        }
    }

    @Override
    public void visit(SelectDiceFromHandController event) {
        // impossibile senza una tool card, rimane non implementata
        for (int i = 0; i < 100; i++) {
            System.err.println("ERRORE NELLA LOGICA DEL CONTROLLER!!!!!!!!!!!!!!!!");
        }
        System.err.println(event);
    }


    @Override
    public void visit(SelectDiceFromRoundTrackController event) {
        // impossibile senza una tool card, rimane non implementata
        for (int i = 0; i < 100; i++) {
            System.err.println("ERRORE NELLA LOGICA DEL CONTROLLER!!!!!!!!!!!!!!!!");
        }
        System.err.println(event);
    }

    /**
     * method for se
     */
    public void startGame() {
        for (int i = 0; i < playerNumber; i++) {
            gameBoard.notifyAllCards(i);
        }
        showAllCardToAll();
        for (int i = 0; i < playerNumber; i++) {
            InitialWindowPatternCard packet = new InitialWindowPatternCard();
            packet.setPlayerId(i);
            server.sendEventToView(packet);
        }
        System.err.println("Iniziato il gioco, inviati tutti i pacchetti per l'inizio del game");

    }

    // TODO: CHECKARE LA CLASSE
    public void joinGame(int id) {
        //TODO non deve inviare l'init windowPattern....
        gameBoard.notifyAllCards(id);
        InitialWindowPatternCard packet = new InitialWindowPatternCard();
        packet.setPlayerId(id);
        System.err.println("Player " + id + " has made a relogin.");
        server.sendEventToView(packet);
    }

    /**
     * Method for notify the view that there is some problem with the input
     *
     * @param ex
     */
    public void showErrorMessageAndShowTurn(Exception ex, int idPlayer) {
        ShowErrorMessage packet = new ShowErrorMessage(ex.getMessage(), true);
        packet.setPlayerId(idPlayer);
        server.sendEventToView(packet);
    }

    public void showErrorMessageNoShowTurn(Exception ex, int idPlayer) {
        ShowErrorMessage packet = new ShowErrorMessage(ex.getMessage(), false);
        packet.setPlayerId(idPlayer);
        server.sendEventToView(packet);
    }

    private void showAllCardToAll() {
        for (int j = 0; j < playerNumber; j++) {
            ShowAllCards waitSetUp = new ShowAllCards();
            waitSetUp.setPlayerId(j);
            server.sendEventToView(waitSetUp);
        }
    }

    private void sendWaitTurnToAllTheNonCurrent(int currentPlayerId) {
        for (int j = 0; j < playerNumber; j++) {
            if (j == currentPlayerId) continue;
            EventView waitTurn = new WaitYourTurn(currentPlayerId);
            waitTurn.setPlayerId(j);
            server.sendEventToView(waitTurn);
        }
    }

    public void askDiceOfDicePool() {
        SelectDiceFromDraftpool packet = new SelectDiceFromDraftpool();
        packet.setPlayerId(gameBoard.getIndexCurrentPlayer());
        server.sendEventToView(packet);
    }

    @Override
    public void timerCallback() {
        System.out.println("TEMPO SCADUTO!!!!");
        MoveTimeoutExpired timerPacket = new MoveTimeoutExpired();
        timerPacket.setPlayerId(gameBoard.getIndexCurrentPlayer());
        server.sendEventToView(timerPacket);
        try {
            gameBoard.nextPlayer(gameBoard.getIndexCurrentPlayer());
            sendWaitTurnToAllTheNonCurrent(gameBoard.getIndexCurrentPlayer());
            StartPlayerTurn turnPacket = new StartPlayerTurn();
            turnPacket.setPlayerId(gameBoard.getIndexCurrentPlayer());
            System.err.println("cambiato il turno tocca a " + gameBoard.getIndexCurrentPlayer());
            server.sendEventToView(turnPacket);

            //Restart timer
            playerTimeout.shutdown();
            playerTimeout.startThread();
        } catch (Exception ex) {
            showErrorMessageAndShowTurn(ex, gameBoard.getIndexCurrentPlayer());
        }
    }
}
