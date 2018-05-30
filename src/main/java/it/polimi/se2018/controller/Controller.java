package it.polimi.se2018.controller;

import it.polimi.se2018.list_event.event_controller.InitialWindowPatternCard;
import it.polimi.se2018.list_event.event_view.*;
import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.network.RemotePlayer;
import it.polimi.se2018.network.server.ServerController;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * @author Matteo Formentin
 * @author Davide Mammarella
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
    }

    // EX UPDATE
    public void sendEventToController(EventController event) {
        event.accept(this);
    }


    @Override
    public void visit(InsertDice event) {

    }

    @Override
    public void visit(SelectInitialWindowPatternCard event) {
        gameBoard.setWindowOfPlayer(event.getPlayerId(), ((SelectInitialWindowPatternCard) event).getSelectedIndex());
    }

    @Override
    public void visit(SelectDiceFromHand event) {

    }

    @Override
    public void visit(EndTurn event) {


    }

    @Override
    public void visit(UseToolCard event) {

    }

    @Override
    public void visit(SelectCellOfWindow event) {


    }

    @Override
    public void visit(SelectDiceFromDraftpool event) {

    }

    @Override
    public void visit(SelectDiceFromRoundTrack event) {

    }

    public void startGame() {
        for (int i = 0; i < playerNumber; i++) {
            InitialWindowPatternCard packet = new InitialWindowPatternCard();
            packet.setInitialWindowPatternCard(gameBoard.getPlayer(i).getThe4WindowPattern());
            packet.setPlayerId(i);
            server.sendEventToView(packet);
            System.out.println("inviato pacchetto init");
        }
    }

}
