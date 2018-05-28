package it.polimi.se2018.network;

import it.polimi.se2018.model.Player;
import javafx.beans.Observable;

/**
 * Class that extends Player adding a Network Level.
 *
 * @author DavideMammarella
 */
public abstract class RemotePlayer extends Player implements Observable {

    //Riferimento alla partita in cui è il giocatore
    private transient GameRoom gameRoom;

    private String nickname;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    protected RemotePlayer(){super();}

    /**
     * Create the reference on what game the player is in.
     * @param gameRoom game where the players is in.
     */
    public void setGameRoom(GameRoom gameRoom){
        this.gameRoom=gameRoom;
    }

    /**
     * Return the reference on what game the player is in.
     * @return the game of the player, null otherwise
     */
    public GameRoom getGameRoom(){
        return this.gameRoom;
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    // NOTIFY
    //------------------------------------------------------------------------------------------------------------------

    /*
    @Override
    public abstract void notify(EventUpdate eventUpdate);
    */
}
