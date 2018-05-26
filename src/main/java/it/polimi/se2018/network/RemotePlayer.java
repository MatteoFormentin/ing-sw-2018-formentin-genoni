package it.polimi.se2018.network;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Player;
import javafx.beans.Observable;

/**
 * Class that extends Player adding a Network Level.
 *
 * @author DavideMammarella
 */
public abstract class RemotePlayer extends Player implements Observable {

    //Riferimento alla partita in cui è il giocatore
    private transient Controller game;

    private String nickname;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    protected RemotePlayer(){}

    /**
     * Create the reference on what game the player is in.
     * @param game game where the players is in.
     */
    public void setGame(Controller game){
        this.game=game;
    }

    /**
     * Return the reference on what game the player is in.
     * @return the game of the player, null otherwise
     */
    public Controller getGame(){
        return this.game;
    }

    //------------------------------------------------------------------------------------------------------------------
    // COMUNICAZIONI AL CLIENT - NOTIFY
    //------------------------------------------------------------------------------------------------------------------

    // ...
}
