package it.polimi.se2018.controller;

import it.polimi.se2018.exception.GameboardException.CurrentPlayerException;
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


    @Override
    public void visit(SelectInitialWindowPatternCardController event) {
        try {
            gameBoard.setWindowOfPlayer(event.getPlayerId(), event.getSelectedIndex());
        } catch (WindowSettingCompleteException ex) {
            for(int i=0;i<playerNumber;i++ ){
                EventView packetFineInit = new InitialEnded();
                packetFineInit.setPlayerId(i);
                sendEventToView(packetFineInit);
            }
            sendWaitTurnToAllTheNonCurrent(gameBoard.getIndexCurrentPlayer());
            EventView turnPacket = new StartPlayerTurn();
            turnPacket.setPlayerId(gameBoard.getIndexCurrentPlayer());
            System.err.println("inizia il vero gioco con il giocatore" + gameBoard.getIndexCurrentPlayer());
            server.sendEventToView(turnPacket);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            showErrorMessage(ex, event.getPlayerId());
            InitialWindowPatternCard packet = new InitialWindowPatternCard();
            packet.setPlayerId(event.getPlayerId());
            server.sendEventToView(packet);
        }
    }

    /**
     * the player choose the first option: draw a dice and place it in the Window Pattern
     * Now the controller need to know the Die to pick up from the dice pool, send a request to the View
     *
     * @param event
     */
    @Override
    public void visit(InsertDiceController event) {//this is the handler for a normal move
        if(gameBoard.getIndexCurrentPlayer()==event.getPlayerId()){
            if (gameBoard.getPlayer(event.getPlayerId()).isHasDrawNewDice()) {
                //il giocatore ha già pescato un dado e deve piazzarlo
                if (gameBoard.getPlayer(event.getPlayerId()).isHasPlaceANewDice()) {
                    //il giocatore ha già utilizzato questa mossa
                    showErrorMessage(new AlreadyPlaceANewDiceException(), event.getPlayerId());
                    //TODO far rivedere il menu senza riinviare il pacchetto? boh
                    EventView turnPacket = new StartPlayerTurn();
                    turnPacket.setPlayerId(event.getPlayerId());
                    System.err.println("Il giocatore voleva violare le regole");
                    server.sendEventToView(turnPacket);
                } else {
                    SelectCellOfWindowView packet = new SelectCellOfWindowView();
                    packet.setPlayerId(event.getPlayerId());
                    server.sendEventToView(packet);
                }
            } else {//il giocatore non ha ancora pescato un nuovo dado
                SelectDiceFromDraftpool packet = new SelectDiceFromDraftpool();
                packet.setPlayerId(event.getPlayerId());
                server.sendEventToView(packet);
            }
        }else{
            showErrorMessage(new CurrentPlayerException(),event.getPlayerId());
        }


    }


    @Override
    public void visit(SelectDiceFromHandController event) {
        try {
            gameBoard.addNewDiceToHandFromDicePool(event.getPlayerId(), event.getIndex());
            SelectCellOfWindowView packet = new SelectCellOfWindowView();
            packet.setPlayerId(gameBoard.getIndexCurrentPlayer());
            server.sendEventToView(packet);
        } catch (Exception ex) {
            showErrorMessage(ex, event.getPlayerId());
            SelectDiceFromDraftpool packet = new SelectDiceFromDraftpool();
            packet.setPlayerId(event.getPlayerId());
            server.sendEventToView(packet);
        }
    }

    @Override
    public void visit(SelectCellOfWindowController event) {
        try {
            gameBoard.insertDice(event.getPlayerId(), event.getLine(), event.getColumn());
            //sendWaitTurnToAllTheNonCurrent(gameBoard.getIndexCurrentPlayer());
            EventView turnPacket = new StartPlayerTurn();
            turnPacket.setPlayerId(gameBoard.getIndexCurrentPlayer());
            System.err.println("Il giocatore ha piazzato il dado con successo" + gameBoard.getIndexCurrentPlayer());
            server.sendEventToView(turnPacket);
        } catch (Exception ex) {
            //TODO implementare errori specifici da mostrare (colore / valore)????? non basta usare showErrorMessage?
            showErrorMessage(ex, event.getPlayerId());
            SelectCellOfWindowView packet = new SelectCellOfWindowView();
            packet.setPlayerId(gameBoard.getIndexCurrentPlayer());
            server.sendEventToView(packet);
        }
    }

    public void visit(SelectToolCardController event) {
        //TODO settare il flag della toolcard to true e girare il pacchetto all'handler
    }

    @Override
    public void visit(EndTurnController event) {
        try {
            gameBoard.nextPlayer(event.getPlayerId());
            sendWaitTurnToAllTheNonCurrent(gameBoard.getIndexCurrentPlayer());
            EventView turnPacket = new StartPlayerTurn();
            turnPacket.setPlayerId(gameBoard.getIndexCurrentPlayer());
            System.err.println("cambiato il turno tocca a " + gameBoard.getIndexCurrentPlayer());
            server.sendEventToView(turnPacket);

        } catch (Exception ex) {
            ex.printStackTrace();
            showErrorMessage(ex, event.getPlayerId());
        }
    }

    @Override
    public void visit(UseToolCardController event) {
        if(gameBoard.getIndexCurrentPlayer()==event.getPlayerId()){
            if (gameBoard.getPlayer(event.getIndex()).isHasUsedToolCard()) {//se ha già usato la toolcard
                if (gameBoard.getIndexCurrentPlayer() == event.getIndex()) {// can use the move ToolCard!
                    showErrorMessage(new AlreadyUseToolCardException(), event.getPlayerId());
                    EventView turnPacket = new StartPlayerTurn();
                    turnPacket.setPlayerId(gameBoard.getIndexCurrentPlayer());
                    server.sendEventToView(turnPacket);
                } else {//not yout turn! how? don't know
                    showErrorMessage(new CurrentPlayerException(), event.getPlayerId());
                }
            } else {
                //TODO inviare il paccetto che rappresenta il menu della scelta toolcard(per gui farà vedere un messaggio e abilita i bottoni)
                EventView packet = new SelectToolCard();
                packet.setPlayerId(gameBoard.getIndexCurrentPlayer());
                server.sendEventToView(packet);
            }
        }else{
            showErrorMessage(new CurrentPlayerException(),event.getPlayerId());
        }

    }


    @Override
    public void visit(SelectDiceFromDraftpoolController event) {

    }

    @Override
    public void visit(SelectDiceFromRoundTrackController event) {
        //non può avvenire nel controller
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
