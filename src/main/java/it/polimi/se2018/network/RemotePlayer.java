package it.polimi.se2018.network;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.network.server.game.Game;

/**
 * Class that extends Player adding network communication.
 */
public abstract class RemotePlayer extends Player {

    //Riferimento alla partita in cui è il giocatore
    private transient Game game;

    //Costruttore astratto
    protected RemotePlayer(){}

    /**
     * Create the reference on what game the player is in.
     * @param game game where the players is in.
     */
    public void setGame(Game game){
        this.game=game;
    }

    /**
     * Return the reference on what game the player is in.
     * @return the game of the player, null otherwise
     */
    public Game getGame(){
        return this.game;
    }

    //-----------------
    //COMUNICAZIONI AL CLIENT
    //-----------------

    // ...
}
