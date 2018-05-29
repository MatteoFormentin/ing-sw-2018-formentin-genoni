package it.polimi.se2018.controller;

import it.polimi.se2018.event.list_event.EndTurn;
import it.polimi.se2018.event.list_event.EventView;
import it.polimi.se2018.event.list_event.InsertDice;
import it.polimi.se2018.event.list_event.UseToolCard;
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

public class Controller implements Runnable {
    private GameBoard gameBoard;
    private Model model; //the class that can call the view for
    private List<Observable> view;
    private int playerok;
    private boolean waitResponse;

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
    public Controller(ServerController server) {
        this.server = server;
        System.out.println("CONTROLLER CREATED!!!!!!!!!!!");
    }

    // EX UPDATE
    public void sendEventToController(RemotePlayer player, EventView event) {
        if (event instanceof InsertDice) {
            // gameBoard.
        }

        if (event instanceof EndTurn) {
            gameBoard.nextPlayer(event.getPlayerId());
            //EventView packet = new EndTurn();
        }

        if (event instanceof UseToolCard) {

        }
    }


    public void init() {

    }

    public void startNewGame(String[] roomPlayers, int indexFirstPlayer) {
        //init game of card arrays & FactoryDice
        try {
            gameBoard = new GameBoard(roomPlayers); //crea il gioco fisico e prepara le 4 window pattern per ogni player
            model = new Model(gameBoard);
            playerok = 0;
            waitResponse = true;
            //To All views -> display which window pattern to Pick
        } catch (Exception e) {

        }
    }


    /**
     * method to set the window chosen by the player
     *
     * @param indexOfThePlayer
     * @param indexOfTheWindow
     */
    public void selectWindowPattern(int indexOfThePlayer, int indexOfTheWindow) {
        //maybe a loop??? broken by the timer or the right input or a return boolean with true/false
        if (indexOfTheWindow < 0 || indexOfTheWindow > 3) //view (who send the message).showMesasge
            ;
        else {
            if (gameBoard.setWindowOfPlayer(indexOfThePlayer, indexOfTheWindow)) {
                //say to model to update the view to this player?
                playerok++;
                if (playerok == view.size()) waitResponse = false;//avvia il vero gioco
            } else {
                //view.showMessage("input of the windowPattern wrong")
            }
        }
    }

    @Override
    public void run() {

    }
}
