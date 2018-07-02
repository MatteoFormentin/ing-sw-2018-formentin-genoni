package it.polimi.se2018.network.client;

import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;

import java.rmi.RemoteException;

/**
 * Interface based on the Abstract Factory Design Pattern.
 * This interface is used as client controller in AbstractClient.
 * Implemented by Client class in order to provide basic methods for RMI and Socket Client.
 *
 * @author DavideMammarella
 */
public interface ClientController {

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Visual starter of the client.
     *
     * @param serverIpAddress address on where the server side communication are open.
     * @param socketRmi number used to manage the decision of the user about the connection. RMI (=0) SOCKET (=1).
     */
    void startClient(String serverIpAddress, int socketRmi) throws Exception;

    /**
     * Method used to log the user to the server with his nickname.
     *
     * @param nickname name of the player associated to the client.
     * @return true if the user is logged, false otherwise.
     */
    boolean login(String nickname);

    /**
     * Method used to send to the server a request to unleash an event.
     *
     * @param eventController object that will use the server to unleash the event associated.
     */
    void sendEventToController(EventController eventController);

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to send to the client an update of the game.
     *
     * @param eventView object that will use the client to unleash the update associated.
     */
    void sendEventToView(EventView eventView) throws RemoteException;

    /**
     * Remote method used to ping the client.
     */
    void ping() throws RemoteException;
}