package it.polimi.se2018.alternative_network.newserver;

import it.polimi.se2018.alternative_network.newserver.room.GameInterface;
import it.polimi.se2018.exception.network_exception.server.ConnectionPlayerException;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Generic abstract class for send and receive messages
 */
public abstract class RemotePlayer2 {

    private String nickname;
    private AtomicBoolean playerRunning;
    private int idPlayerInGame;
    private GameInterface gameInterface;


    /**
     * constructor that set by default the player as running
     */
    public RemotePlayer2() {
        playerRunning.set(true);
    }

    /**
     * method for get the name of the remote player
     *
     * @return the name of the player
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * method for set the name of the player, typically is received when he ask to make the login
     *
     * @param nickname the nickname of the player
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * method for get if the remote player is still reachable by the server or game room
     *
     * @return true
     */
    public boolean isPlayerRunning() {
        return playerRunning.get();
    }

    /**
     * method for set if the remote player is still connected without trying to contact him
     *
     * @param playerRunning false if the player can't be reached, true otherwise
     */
    public void setPlayerRunning(boolean playerRunning) {
        this.playerRunning.set(playerRunning);
    }

    /**
     * method for get the id of the remote player in the game room
     *
     * @return the index of the player in the game room
     */
    public int getIdPlayerInGame() {
        return idPlayerInGame;
    }

    /**
     * method for set the id of the remote player in the game room
     *
     * @param idPlayerInGame associated to this player
     */
    public void setIdPlayerInGame(int idPlayerInGame) {
        this.idPlayerInGame = idPlayerInGame;
    }

    /**
     * when the remote player is associated with a game return the reference to the interface
     * that allows to send messages directly to the game room and to notify a disconnection.
     *
     * @return the GameInterface associated to this player
     */
    public GameInterface getGameInterface() {
        return gameInterface;
    }

    /**
     * method to associate the remote player with the game room
     *
     * @param gameInterface associated to this player
     */
    public void setGameInterface(GameInterface gameInterface) {
        this.gameInterface = gameInterface;
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    /**
     * this method is used when the game room needs to send some {@code EventClient}
     * to the client related to this remote player
     *
     * @param eventClient event that the client needs
     * @throws ConnectionPlayerException is thrown if the player didn't respond
     */
    public abstract void sendEventToView(EventClient eventClient) throws ConnectionPlayerException;



    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - INTERNAL REQUEST
    //------------------------------------------------------------------------------------------------------------------

    /**
     * method that need to implement the remove from the client gatherer
     * or any type of class that the player use to send the View.
     * doing so the game Room can kick out from the server the client.
     */
    public abstract void kickPlayerOut();

    /**
     * method of the Rmi player for send the event directly to the game room
     * without passing thought the main server that hold all the game room
     *
     * @param eventController event requested by the client
     */
    public abstract void sendEventToController(EventController eventController);



}
