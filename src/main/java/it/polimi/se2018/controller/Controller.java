package it.polimi.se2018.controller;

import it.polimi.se2018.controller.effect.EffectGame;
import it.polimi.se2018.controller.effect.EndTurn;
import it.polimi.se2018.exception.gameboard_exception.CurrentPlayerException;
import it.polimi.se2018.exception.gameboard_exception.WindowPatternAlreadyTakenException;
import it.polimi.se2018.exception.gameboard_exception.WindowSettingCompleteException;
import it.polimi.se2018.exception.player_exception.AlreadyPlaceANewDiceException;
import it.polimi.se2018.exception.player_exception.AlreadyUseToolCardException;
import it.polimi.se2018.list_event.event_received_by_controller.*;
import it.polimi.se2018.list_event.event_received_by_view.*;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.*;
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
     * Method of the Visitor Pattern, event received from the view
     * to let the player pick a window.
     *
     * @param event ControllerSelectInitialWindowPatternCard
     */
    @Override
    public void visit(ControllerSelectInitialWindowPatternCard event) {
        try {
            gameBoard.setWindowOfPlayer(event.getPlayerId(), event.getSelectedIndex());
            MessageOk packet = new MessageOk("Hai scelto la window Pattern.", false,true);
            packet.setPlayerId(event.getPlayerId());
            sendEventToView(packet);
        } catch (WindowSettingCompleteException ex) {
            //all the window are set
            for (int i = 0; i < playerNumber; i++) {
                EventView packetFineInit = new InitialEnded();
                packetFineInit.setPlayerId(i);
                sendEventToView(packetFineInit);
            }
            sendWaitTurnToAllTheNonCurrent(gameBoard.getIndexCurrentPlayer());
            StartPlayerTurn turnPacket = new StartPlayerTurn();
            turnPacket.setPlayerId(gameBoard.getIndexCurrentPlayer());
            sendEventToView(turnPacket);
            //TODO fermare il timout per l'init e iniziare quello per il round.
            playerTimeout.shutdown();
            playerTimeout.startThread();
        } catch (WindowPatternAlreadyTakenException ex) {
            //window already chosen
            MessageError packetError = new MessageError(ex.getMessage(),false,true);
            packetError.setPlayerId(event.getPlayerId());
            sendEventToView(packetError);
        } catch (Exception ex) {
            //strange exception retry the input
            MessageError packetError = new MessageError(ex.getMessage(),false,true);
            packetError.setPlayerId(event.getPlayerId());
            sendEventToView(packetError);
            SelectInitialWindowPatternCard packet = new SelectInitialWindowPatternCard();
            packet.setPlayerId(event.getPlayerId());
            sendEventToView(packet);
        }
    }

    /**
     * Method of the Visitor Pattern, event received from the view
     * to let the player initiate the mode draw and place a die
     *
     * @param event ControllerSelectInitialWindowPatternCard
     */
    @Override
    public void visit(ControllerMoveDrawAndPlaceDie event) {
        if(event.getPlayerId()==gameBoard.getIndexCurrentPlayer()){
            if(gameBoard.getPlayer(event.getPlayerId()).isHasPlaceANewDice()){
                showErrorMessage(new AlreadyPlaceANewDiceException(),event.getPlayerId(),true);
            }else if(gameBoard.getPlayer(event.getPlayerId()).isHasDrawNewDice()){
                SelectCellOfWindow packet = new SelectCellOfWindow();
                packet.setPlayerId(event.getPlayerId());
                sendEventToView(packet);
            }else{
                SelectDiceFromDraftPool packet = new SelectDiceFromDraftPool();
                packet.setPlayerId(event.getPlayerId());
                sendEventToView(packet);
            }
        }else{
            showErrorMessage(new CurrentPlayerException(),event.getPlayerId(),false);
        }
    }

    /**
     * Method of the Visitor Pattern, event received from the view
     * to let the player initiate the mode play a tool card
     *
     * @param event ControllerMoveUseToolCard
     */
    @Override
    public void visit(ControllerMoveUseToolCard event) {
        if(event.getPlayerId()==gameBoard.getIndexCurrentPlayer()){
            if(gameBoard.getPlayer(event.getPlayerId()).isHasUsedToolCard()){
                showErrorMessage(new AlreadyUseToolCardException(),event.getPlayerId(),true);
            }else{
                SelectToolCard packet = new SelectToolCard();
                packet.setPlayerId(event.getPlayerId());
                sendEventToView(packet);
            }
        }else{
            showErrorMessage(new CurrentPlayerException(),event.getPlayerId(),false);
        }
    }

    /**
     * Method of the Visitor Pattern, event received from the view
     * to let the player initiate the mode play a tool card
     *
     * @param event ControllerSelectDiceFromDraftPool
     */
    @Override
    public void visit(ControllerSelectDiceFromDraftPool event) {
        try {
            gameBoard.addNewDiceToHandFromDicePool(event.getPlayerId(), event.getIndex());
            SelectCellOfWindow packet = new SelectCellOfWindow();
            packet.setPlayerId(event.getPlayerId());
            sendEventToView(packet);
        } catch (CurrentPlayerException ex){
            showErrorMessage(ex,event.getPlayerId(),false);
        } catch (Exception ex) {
            showErrorMessage(ex, event.getPlayerId(),true);

        }
    }

    @Override
    public void visit(ControllerSelectCellOfWindow event) {
        try {
            gameBoard.insertDice(event.getPlayerId(), event.getLine(), event.getColumn(),true);
            EventView packet =new MessageOk("la massa si è conclusa con successo",true);
            packet.setPlayerId(event.getPlayerId());
            sendEventToView(packet);
        } catch (CurrentPlayerException ex){
            showErrorMessage(ex,event.getPlayerId(),false);
        } catch (Exception ex) {
            showErrorMessage(ex, event.getPlayerId(),true);
        }
    }

    public void visit(ControllerSelectToolCard event) {
        try{
            gameBoard.useToolCard(event.getPlayerId(),event.getIndexToolCard());
            showErrorMessage(new IllegalClassFormatException(), event.getPlayerId(),true);
        } catch (CurrentPlayerException ex){
            showErrorMessage(ex,event.getPlayerId(),false);
        } catch (Exception ex) {
            showErrorMessage(ex, event.getPlayerId(),true);
        }
    }

    @Override
    public void visit(ControllerEndTurn event) {
        try {
            EffectGame endTurn = new EndTurn(false);
            endTurn.doEffect(gameBoard,event.getPlayerId(),null);
            sendWaitTurnToAllTheNonCurrent(gameBoard.getIndexCurrentPlayer());
            StartPlayerTurn turnPacket = new StartPlayerTurn();
            turnPacket.setPlayerId(gameBoard.getIndexCurrentPlayer());
            sendEventToView(turnPacket);
            //Restart timer
            playerTimeout.shutdown();
            playerTimeout.startThread();
        } catch (Exception ex) {
            showErrorMessage(ex, event.getPlayerId(),false);
        }
    }

    @Override
    public void visit(ControllerSendInfoIndex event) {

    }

    @Override
    public void visit(ControllerSelectDiceFromHand event) {
        // impossibile senza una tool card, rimane non implementata
        for (int i = 0; i < 100; i++) {
            System.err.println("ERRORE NELLA LOGICA DEL CONTROLLER!!!!!!!!!!!!!!!!");
        }
        System.err.println(event);
    }


    @Override
    public void visit(ControllerSelectDiceFromRoundTrack event) {
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
        //after the notify to all player show the info
        for (int j = 0; j < playerNumber; j++) {
            ShowAllCards waitSetUp = new ShowAllCards();
            waitSetUp.setPlayerId(j);
            server.sendEventToView(waitSetUp);
        }
        //after showing the info ask the player to choose the window
        for (int i = 0; i < playerNumber; i++) {
            SelectInitialWindowPatternCard packet = new SelectInitialWindowPatternCard();
            packet.setPlayerId(i);
            server.sendEventToView(packet);
        }
        //TODO Start the timer
        System.err.println("Iniziato il gioco, inviati tutti i pacchetti per l'inizio del game");

    }

    // TODO: CHECKARE LA CLASSE
    public void joinGame(int id) {
        //TODO non deve inviare l'init windowPattern... ci sono 2 tipi di join
        gameBoard.notifyAllCards(id);
        SelectInitialWindowPatternCard packet = new SelectInitialWindowPatternCard();
        packet.setPlayerId(id);
        System.err.println("Player " + id + " has made a relogin.");
        server.sendEventToView(packet);
    }


    private void showErrorMessage(Exception ex, int idPlayer, boolean showMenuTurn) {
        MessageError packet = new MessageError(ex.getMessage(),showMenuTurn);
        packet.setPlayerId(idPlayer);
        server.sendEventToView(packet);
    }



    private void sendWaitTurnToAllTheNonCurrent(int currentPlayerId) {
        for (int j = 0; j < playerNumber; j++) {
            if (j == currentPlayerId) continue;
            EventView waitTurn = new WaitYourTurn(currentPlayerId);
            waitTurn.setPlayerId(j);
            server.sendEventToView(waitTurn);
        }
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
            showErrorMessage(ex, gameBoard.getIndexCurrentPlayer(),false);
        }
    }
}
