package it.polimi.se2018.alternative_network.newserver;

import it.polimi.se2018.alternative_network.newserver.room.GameInterface;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;

/**
 * Generic abstract class for send and receive messages
 *
 * @author DavideMammarella
 */
public interface RemotePlayer2 {


    /**
     * method for get the name of the remote player
     *
     * @return the name of the player
     */
    public String getNickname();


    /**
     * method for set the name of the player, typically is received when he ask to make the login
     *
     * @param nickname the nickname of the player
     */
    public void setNickname(String nickname);

    /**
     * method for get the id of the remote player in the game room
     *
     * @return the index of the player in the game room
     */
    public int getIdPlayerInGame();

    /**
     * method for set the id of the remote player in the game room
     *
     * @param idPlayerInGame associated to this player
     */
    public void setIdPlayerInGame(int idPlayerInGame);

    /**
     * when the remote player is associated with a game return the reference to the interface
     * that allows to send messages directly to the game room and to notify a disconnection.
     *
     * @return the GameInterface associated to this player
     */
    public GameInterface getGameInterface();

    /**
     * method to associate the remote player with the game room
     *
     * @param gameInterface associated to this player
     */
    public void setGameInterface(GameInterface gameInterface);


    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    /**
     * this method is used when the game room needs to send some {@code EventClient}
     * to the client related to this remote player. this methos handle the connection problemn
     *
     * @param eventClient event that the client needs
     */
    //TODO l'implementazione deve gestire la disconnessione
    public void sendEventToView(EventClient eventClient);


    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - INTERNAL REQUEST
    //------------------------------------------------------------------------------------------------------------------

    /**
     * method that need to implement the remove from the client gatherer
     * or any type of class that the player use to send the View.
     * doing so the game Room can kick out from the server the client.
     */
    public void kickPlayerOut();

    /**
     * method it's the same as is player running so
     * rmi can try to ping
     * socket response true or false depending if the thread is open
     */
    public boolean checkOnline();

    /**
     * method of the Rmi player for send the event directly to the game room
     * without passing thought the main server that hold all the game room
     *
     * @param eventController event requested by the client
     */
    public void sendEventToController(EventController eventController);


}
