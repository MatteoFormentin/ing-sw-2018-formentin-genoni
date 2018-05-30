package it.polimi.se2018.network;

import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.network.server.Server;

import java.rmi.RemoteException;

/**
 * Class that give to the player the possibility to utilize different type of connection (RMI or Socket).
 * This class will be extended from RMI or Socket Player class.
 *
 * @author DavideMammarella
 */
public abstract class RemotePlayer {

    //Riferimento alla partita in cui è il giocatore
    private transient Server serverRoom;

    // Nickname del player
    private String nickname;

    // ID del player
    private int playerId;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Remote Player constructor.
     */
    protected RemotePlayer() {
        super();
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Remote method used to send to the client an update of the game.
     *
     * @param eventView object that will use the client to unleash the update associated.
     */
    public abstract void sendEventToView(EventView eventView) throws RemoteException;

    //------------------------------------------------------------------------------------------------------------------
    // SUPPORTER METHODS
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Getter for the server room.
     *
     * @return reference of the server room where the player is playing.
     */
    public Server getServerRoom() {
        return this.serverRoom;
    }

    /**
     * Setter for the server room.
     *
     * @param serverRoom game where the players is in.
     */
    public void setServerRoom(Server serverRoom) {
        this.serverRoom = serverRoom;
    }

    /**
     * Getter for nickname.
     *
     * @return name associated to the player.
     */
    public String getNickname() {
        return nickname;
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
     * Getter for player ID.
     *
     * @return player ID.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Setter for player ID.
     *
     * @param playerId ID associated to the player.
     */
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
