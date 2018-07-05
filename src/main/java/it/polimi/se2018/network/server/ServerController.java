package it.polimi.se2018.network.server;

import it.polimi.se2018.exception.network_exception.PlayerNetworkException;
import it.polimi.se2018.list_event.event_received_by_server.EventServer;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import it.polimi.se2018.network.RemotePlayer;

/**
 * Interface based on the Abstract Factory Design Pattern.
 * This interface is used as server controller in AbstractServer.
 * Implemented by Server class in order to provide basic methods for RMI and Socket Server.
 *
 * @author DavideMammarella
 */
public interface ServerController {

    //TODO: EXCEPTION

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Remote method used to log the user to the server with his nickname.
     *
     * @param remotePlayer reference to RMI or Socket Player.
     * @return true if the user is logged, false otherwise.
     */
    void login(RemotePlayer remotePlayer);

    /**
     * Remote method used to send to the server a request to unleash an event.
     *
     * @param eventController object that will use the server to unleash the event associated.
     */
    void sendEventToController(EventServer eventController);

    /**
     * Remote method used to start the game.
     */
    void startGame();

    /**
     * Remote method used to join the current game.
     *
     * @param remotePlayer reference to RMI or Socket Player.
     */
    //void joinGame(RemotePlayer remotePlayer);

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Remote method used to send to the client an update of the game.
     *
     * @param eventClient object that will use the client to unleash the update associated.
     */
    void sendEventToView(EventClient eventClient);

    /**
     * Remote method used to ping the client.
     */
    void ping();

    void playerDisconnect(RemotePlayer player);

    //------------------------------------------------------------------------------------------------------------------
    // SUPPORTER METHOD
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Searcher for player id in the game.
     *
     * @param id ID of the player associated to the client.
     * @return player associated to the ID.
     */
    RemotePlayer searchPlayerById(int id);
}

