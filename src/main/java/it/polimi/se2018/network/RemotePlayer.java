package it.polimi.se2018.network;

import it.polimi.se2018.list_event.event_controller.EventView;
import it.polimi.se2018.list_event.event_view.EventController;
import it.polimi.se2018.network.server.Server;
import javafx.beans.Observable;

import java.rmi.RemoteException;

/**
 * Class that extends Player adding a Network Level.
 *
 * @author DavideMammarella
 */
public abstract class RemotePlayer implements Observable {

    //Riferimento alla partita in cui è il giocatore
    private transient Server serverRoom;

    // Nickname del player
    private String nickname;

    // ID del player
    private int playerId;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    protected RemotePlayer() {
        super();
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    // NOTIFY
    //------------------------------------------------------------------------------------------------------------------

    /*
    @Override
    public abstract void sendUpdateToView(EventUpdate eventUpdate);
    */

    //------------------------------------------------------------------------------------------------------------------
    // METHOD FOR SUPPORT (GET, SET, CHECK)
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Setter for the server room.
     *
     * @param serverRoom game where the players is in.
     */
    public void setServerRoom(Server serverRoom) {
        this.serverRoom = serverRoom;
    }

    /**
     * Getter for the server room.
     *
     * @return reference on what game the player is in.
     */
    public Server getServerRoom() {
        return this.serverRoom;
    }

    /**
     * Setter for nickname.
     *
     * @param nickname name used for the player.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Getter for nickname.
     *
     * @return name used for the player.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Setter for player ID.
     *
     * @param playerId id associated to the player.
     */
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    /**
     * Getter for player ID.
     *
     * @return player ID.
     */
    public int getPlayerId() {
        return playerId;
    }

    public abstract void sendEventToView(EventView eventView) throws RemoteException;
}
