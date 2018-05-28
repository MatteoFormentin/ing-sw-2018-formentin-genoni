package it.polimi.se2018.network;

import javafx.beans.Observable;

/**
 * Class that extends Player adding a Network Level.
 *
 * @author DavideMammarella
 */
public abstract class RemotePlayer implements Observable {

    //Riferimento alla partita in cui è il giocatore
    private transient GameRoom gameRoom;

    private String nickname;

    private int playerId;

    protected RemotePlayer() {
        super();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getPlayerId() {
        return playerId;
    }

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    /**
     * Return the reference on what game the player is in.
     *
     * @return the game of the player, null otherwise
     */
    public GameRoom getGameRoom() {
        return this.gameRoom;
    }

    /**
     * Create the reference on what game the player is in.
     *
     * @param gameRoom game where the players is in.
     */
    public void setGameRoom(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
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
