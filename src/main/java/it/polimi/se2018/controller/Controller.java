package it.polimi.se2018.controller;

import it.polimi.se2018.event.list_event.*;
import it.polimi.se2018.list_event.event_controller.InitialWindowPatternCard;
import it.polimi.se2018.list_event.event_controller.StartGame;
import it.polimi.se2018.list_event.event_controller.Visitor;
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

public class Controller implements Visitor {
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
    public void sendEventToController(EventView event) {
        if (event instanceof StartGame) {
            //TODO controllre se id pacchetto presente nel server
            init();
        }


        if (event instanceof InsertDice) {
            // gameBoard.
        }

        if (event instanceof EndTurn) {
            System.out.println("Arrivato pacchetto");
            // gameBoard.nextPlayer(event.getPlayerId());
            //EventView packet = new EndTurn();
        }

        if (event instanceof UseToolCard) {

        }

        if (event instanceof SelectWindow) {
            System.out.println("Arrivato SelectWindow da " + event.getPlayerId());
        }

        if (event instanceof Logout) {
            System.out.println("Arrivato Logout da " + event.getPlayerId());
        }

        if (event instanceof SelectInitialWindowPatternCard) {
            gameBoard.setWindowOfPlayer(event.getPlayerId(), ((SelectInitialWindowPatternCard) event).getSelectedIndex());
        }

    }

    @Override
    public void visit(StartGame event) {

    }

    @Override
    public void visit(InsertDice event) {

    }

    @Override
    public void visit(SelectInitialWindowPatternCard event) {

    }


    public void init() {
        for (int i = 0; i < playerNumber; i++) {
            InitialWindowPatternCard packet = new InitialWindowPatternCard();
            packet.setInitialWindowPatternCard(gameBoard.getPlayer(i).getThe4WindowPattern());
            packet.setPlayerId(i);
            server.sendEventToView(packet);
            System.out.println("inviato pacchetto init");
        }
    }

}
