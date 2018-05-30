package it.polimi.se2018.controller;

import it.polimi.se2018.list_event.event_received_by_controller.*;
import it.polimi.se2018.list_event.event_received_by_view.*;
import it.polimi.se2018.list_event.event_received_by_view.SelectDiceFromDraftpool;
import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.exception.*;
import it.polimi.se2018.network.RemotePlayer;
import it.polimi.se2018.network.server.ServerController;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Class that implements the {@code ControllerVisitor} for execute the event that the view produced
 *
 * @author Matteo Formentin
 * @author Davide Mammarella
 * @author Luca Genoni
 */

public class Controller implements ControllerVisitor {
    private GameBoard gameBoard;
    private Model model; //the class that can call the view for
    private List<Observable> view;
    private int playerok;
    private boolean waitResponse;
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
        gameBoard = new GameBoard(playerNumber);
        toolcard = false;
    }

    // EX UPDATE
    public void sendEventToController(EventController event) {
        event.accept(this);
    }

    //*****************************************Visitor Pattern************************************************************************
    //*****************************************Visitor Pattern************************************************************************
    //*****************************************Visitor Pattern************************************************************************
    //*****************************************Visitor Pattern************************************************************************


    @Override
    public void visit(SelectInitialWindowPatternCardController event) {
        try {
            gameBoard.setWindowOfPlayer(event.getPlayerId(), ((SelectInitialWindowPatternCardController) event).getSelectedIndex());
            //TODO mandare agli altri giocatori la carta scelta
        } catch (SettingWindowCompleteException ex) {
            EventView turnPacket = new StartPlayerTurn();
            turnPacket.setPlayerId(gameBoard.getIndexCurrentPlayer());
            server.sendEventToView(turnPacket);
            for (int i = 0; i < playerNumber; i++) {
                if (i == gameBoard.getIndexCurrentPlayer()) continue;
                EventView waitPacket = new WaitYourTurn();
                waitPacket.setPlayerId(i);
                server.sendEventToView(waitPacket);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
        if (!toolcard) {
            if(gameBoard.getPlayer(event.getPlayerId()).isHasDrawNewDice()){
                //il giocatore ha già pescato un dado e deve piazzarlo
                SelectCellOfWindowView packet = new SelectCellOfWindowView();
                packet.setPlayerId(event.getPlayerId());
                server.sendEventToView(packet);
            }
            else{//il giocatore non ha ancora pescato un nuovo dado
                SelectDiceFromDraftpool packet = new SelectDiceFromDraftpool();
                packet.setPlayerId(event.getPlayerId());
                server.sendEventToView(packet);
            }
        } else {
            showErrorMessage(new ToolCardInUseException());
        }

    }


    @Override
    public void visit(SelectDiceFromHandController event) {
        if (toolcard) ;//pass to handler toolcard
        else {
            try {
                gameBoard.addNormalDiceToHandFromDraftPool(event.getPlayerId(), event.getIndex());
                SelectCellOfWindowView packet = new SelectCellOfWindowView();
                packet.setPlayerId(gameBoard.getIndexCurrentPlayer());
                server.sendEventToView(packet);
            } catch (Exception ex) {
                showErrorMessage(ex);

            }
        }
    }
    @Override
    public void visit(SelectCellOfWindowController event) {
        if (toolcard) ;//pass to handler toolcard
        else {
            try {
                gameBoard.insertDice(event.getPlayerId(), event.getLine(), event.getColumn());
                SelectCellOfWindowView packet = new SelectCellOfWindowView();
                packet.setPlayerId(gameBoard.getIndexCurrentPlayer());
                server.sendEventToView(packet);
            } catch (Exception ex) {
                //TODO implementare errori specifici da mostrare (colore / valore)????? non basta usare showErrorMessage?
                showErrorMessage(ex);
            }
        }
    }

    public void visit(SelectToolCardController event) {

    }

    @Override
    public void visit(EndTurnController event) {


    }

    @Override
    public void visit(UseToolCardController event) {
        toolcard = true;
        EventView packet = new SelectToolCard();
        packet.setPlayerId(gameBoard.getIndexCurrentPlayer());
        server.sendEventToView(packet);
    }



    @Override
    public void visit(SelectDiceFromDraftpoolController event) {

    }

    @Override
    public void visit(SelectDiceFromRoundTrackController event) {

    }

    public void startGame() {
        for (int i = 0; i < playerNumber; i++) {
<<<<<<< HEAD
            UpdateInitialWindowPatternCard packet = new UpdateInitialWindowPatternCard();
=======
            for (int j = 0; j < playerNumber; j++) {
                if (j == gameBoard.getIndexCurrentPlayer()) continue;
                EventView waitTurn = new WaitYourTurn();
                waitTurn.setPlayerId(j);
                server.sendEventToView(waitTurn);
            }
            InitialWindowPatternCard packet = new InitialWindowPatternCard();
>>>>>>> 35b46c7027f466106547a73b798a330acb950a42
            packet.setInitialWindowPatternCard(gameBoard.getPlayer(i).getThe4WindowPattern());
            packet.setPlayerId(i);
            server.sendEventToView(packet);
            System.out.println("inviato pacchetto init");
        }
    }

    /**
     * Method for notify the view that there is some problem with the input
     * @param ex
     */
    public void showErrorMessage(Exception ex) {
        ShowErrorMessage packet = new ShowErrorMessage();
        packet.setPlayerId(gameBoard.getIndexCurrentPlayer());
        packet.setErrorMessage(ex.getMessage());
        packet.setTypeException(ex);
        server.sendEventToView(packet);
    }

    public void askDiceOfDicePool(){
        SelectDiceFromDraftpool packet = new SelectDiceFromDraftpool();
        packet.setPlayerId(gameBoard.getIndexCurrentPlayer());
        server.sendEventToView(packet);
    }

}
