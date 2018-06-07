package it.polimi.se2018.controller;

import it.polimi.se2018.exception.GameboardException.CurrentPlayerException;
import it.polimi.se2018.exception.GameboardException.WindowPatternAlreadyTakenException;
import it.polimi.se2018.exception.GameboardException.WindowSettingCompleteException;
import it.polimi.se2018.exception.PlayerException.AlreadyPlaceANewDiceException;
import it.polimi.se2018.exception.PlayerException.AlreadyUseToolCardException;
import it.polimi.se2018.exception.ToolCardInUseException;
import it.polimi.se2018.list_event.event_received_by_controller.*;
import it.polimi.se2018.list_event.event_received_by_view.*;
import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.network.RemotePlayer;
import it.polimi.se2018.network.server.ServerController;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Class that implements the {@code ControllerVisitor} for execute the event that the view produced
 *
 * @author Matteo Formentin
 * @author Luca Genoni
 */

public class Controller implements ControllerVisitor {
    private GameBoard gameBoard;
    private HandlerToolCard handlerToolCard;
    private int playerNumber;
    //Server in cui si setterà la partita
    private ServerController server;

    //Player
    private ArrayList<RemotePlayer> players;
    private boolean toolcard;

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
        } catch (WindowPatternAlreadyTakenException ex) {
            System.err.println(ex.getMessage());
            showErrorMessage(ex, event.getPlayerId());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            showErrorMessage(ex, event.getPlayerId());
            InitialWindowPatternCard packet = new InitialWindowPatternCard();
            packet.setPlayerId(event.getPlayerId());
            server.sendEventToView(packet);
        }
    }

    @Override
    public void visit(SelectDiceFromDraftpoolController event) {
        try {
            gameBoard.addNewDiceToHandFromDicePool(event.getPlayerId(), event.getIndex());
            OkMessage packet = new OkMessage();
            packet.setPlayerId(event.getPlayerId());
            sendEventToView(packet);
        } catch (Exception ex) {
            showErrorMessage(ex, event.getPlayerId());
        }
    }

    @Override
    public void visit(SelectCellOfWindowController event) {
        try {
            gameBoard.insertDice(event.getPlayerId(), event.getLine(), event.getColumn());
            OkMessage packet = new OkMessage();
            packet.setPlayerId(event.getPlayerId());
            sendEventToView(packet);
        } catch (AlreadyPlaceANewDiceException ex) {
            showErrorMessage(ex, event.getPlayerId());
        } catch (Exception ex) {
            showErrorMessage(ex, event.getPlayerId());
        }
    }

    public void visit(SelectToolCardController event) {
        if (gameBoard.getIndexCurrentPlayer() == event.getPlayerId()) {
            //TODO sostituire con il system.out l'attivazione della toolcard
            System.out.println("il giocatore con id :" + event.getPlayerId() + " vuole giocare la " + event.getIndexToolCard() + " Toolcard");
        } else {
            showErrorMessage(new CurrentPlayerException(), event.getPlayerId());
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
        } catch (Exception ex) {
            showErrorMessage(ex, event.getPlayerId());
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
    public void showErrorMessage(Exception ex, int indexPlayer) {
        ShowErrorMessage packet = new ShowErrorMessage();
        packet.setPlayerId(indexPlayer);
        packet.setErrorMessage(ex.getMessage());
        packet.setTypeException(ex);
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

}
