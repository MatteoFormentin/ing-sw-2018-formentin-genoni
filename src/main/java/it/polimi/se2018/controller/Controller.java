package it.polimi.se2018.controller;

import it.polimi.se2018.event.list_event.EventView;
import it.polimi.se2018.event.list_event.Login;
import it.polimi.se2018.event.list_event.UseToolCard;
import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.card.Deck;
import it.polimi.se2018.model.dice.BalancedFactoryDice;
import it.polimi.se2018.model.dice.DiceStack;

import javax.swing.text.View;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class Controller {
    private GameBoard gameBoard;
    private Model model; //the class that can call the view for
    private List<Observable> view;
    private int playerok;
    private boolean waitResponse;

    private void startNewGame(String[] roomPlayers, int indexFirstPlayer) {
        //init game of card arrays & FactoryDice
        gameBoard = new GameBoard(roomPlayers, indexFirstPlayer); //crea il gioco fisico e prepara le 4 window pattern per ogni player
        model = new Model(gameBoard);
        playerok=0;
        waitResponse=true;
        //To All views -> display which window pattern to Pick
    }
    private void doGame(){


    }
    /**
     * method to set the window chosen by the player
     * @param indexOfThePlayer
     * @param indexOfTheWindow
     */
    public void selectWindowPattern(int indexOfThePlayer,int indexOfTheWindow){
        //maybe a loop??? broken by the timer or the right input or a return boolean with true/false
        if(indexOfTheWindow<0|| indexOfTheWindow>3) //view (who send the message).showMesasge
            ;
        else{
            if(gameBoard.setWindowOfPlayer(indexOfThePlayer,indexOfTheWindow)) {
                //say to model to update the view to this player?
                playerok++;
                if (playerok==view.size()) waitResponse=false;//avvia il vero gioco
            }
            else{
                //view.showMessage("input of the windowPattern wrong")
            }
        }
    }



    /**
     * this method is the function that processes me, and recognizes the event both in the case of an RMI and socket
     *
     * @param eventView
     */
    public void newEvent(EventView eventView){
        //identifica ed elabora questo evento
        if (eventView instanceof Login) { // fai il login se la gameboard non è 0 fai relogin è piena error
            eventView.getModel(); // se esiste ok se no message error
            eventView.getNicknamPlayer(); // se è il suo turno se no message error
        }
        if (eventView instanceof UseToolCard){
            int i = ((UseToolCard) eventView).getIndex();

        }
        //
        //la ca
    }
}
