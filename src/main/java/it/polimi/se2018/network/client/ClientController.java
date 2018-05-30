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

    public boolean startRMIClient(String serverIpAddress, int rmiPort);


    /**
     * Remote method used to log the user to the server with his nickname.
     *
     * @param nickname name of the player associated to the client.
     * @return true if the user is logged, false otherwise.
     */
    boolean login(String nickname);

    /**
     * Remote method used to send to the server a request to unleash an event.
     *
     * @param eventController object that will use the server to unleash the event associated.
     */
    void sendEventToController(EventController eventController);

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Remote method used to send to the client an update of the game.
     *
     * @param eventView object that will use the client to unleash the update associated.
     */
    void sendEventToView(EventView eventView) throws RemoteException;
}