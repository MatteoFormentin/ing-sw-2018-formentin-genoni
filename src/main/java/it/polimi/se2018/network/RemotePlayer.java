package it.polimi.se2018.network;

import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import it.polimi.se2018.network.server.Server;

import java.rmi.RemoteException;

/**
 * Class that give to the player the possibility to utilize different type of connection (RMI or Socket).
 * This class will be extended from RMI or Socket Player class.
 * It will be essentially used as a Client Handler.
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

    // Si ricollega alla logica dei thread (vedi Timer)
    // Setto una variabile booleana grazie la quale posso fornire lo stato del giocatore
    // Ovvero se c'è una connessione ancora valida (Running / true) o meno (Not Running / false)
    public boolean playerRunning=false;

    // identificatore connessione player
    public String playerConnection;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Remote Player constructor.
     * playerRunning flag will be initially false, determining
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
     * @param eventClient object that will use the client to unleash the update associated.
     */
    public abstract void sendEventToView(EventClient eventClient) throws RemoteException;

    /**
     * Remote method used to ping the client.
     * If the remote player that will call the ping will not found the disconnection of the player will be called.
     */
    public abstract void ping() throws RemoteException;

    public abstract void sendAck();

    //------------------------------------------------------------------------------------------------------------------
    // SUPPORTER METHODS
    //------------------------------------------------------------------------------------------------------------------

    protected void setServerRoom(Server serverRoom){
        this.serverRoom=serverRoom;
    }

    /**
     * Getter for nickname.
     *
     * @return name associated to the player.
     */
    public String getNickname() {
        return this.nickname;
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
        return this.playerId;
    }

    /**
     * Setter for player ID.
     *
     * @param playerId ID associated to the player.
     */
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    /**
     * Setter for player connection state.
     *
     * @param playerRunning flag used to determine if a player is connected (true) or not (false).
     */
    public void setPlayerRunning(boolean playerRunning){
        this.playerRunning=playerRunning;
    }

    /**
     * Getter for player connection state.
     *
     * @return true if player is connected, false otherwise.
     */
    public boolean getPlayerRunning() {
        return this.playerRunning;
    }

    /**
     * Getter for player connection:
     * 0 for RMI
     * 1 for SOCKET
     *
     * @return name associated to the player.
     */
    public String getPlayerConnection() {
        return this.playerConnection;
    }

    /**
     * Setter for player connection:
     * 0 for RMI
     * 1 for SOCKET
     *
     * @param playerConnection flag used to identify the connection of the player.
     */
    public void setPlayerConnection(String playerConnection) {
        this.playerConnection = playerConnection;
    }

    /**
     * Method used to remove a player from the RMI server.
     * This method will also set the playerRunning boolean to false in order to remove correctly the user.
     */
    public abstract void disconnect();

    //public abstract void removePlayer(RemotePlayer remotePlayer);
}
