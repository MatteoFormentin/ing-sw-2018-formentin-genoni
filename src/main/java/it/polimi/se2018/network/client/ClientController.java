package it.polimi.se2018.network.client;

import it.polimi.se2018.event.list_event.EventView;

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
     * Log the user to the Server with the nickname
     *
     * @param nickname name used for the player.
     */
    boolean login(String nickname);

    /**
     * Send to the Server the request to unleash an event.
     *
     * @param eventView object that will use the server to unleash the event associated.
     */
    void unleashEvent(EventView eventView);


    boolean startRMIClient(String serverIpAddress, int rmiPort);
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    // NOTIFY
    //------------------------------------------------------------------------------------------------------------------

    /*
    public void notify(EventUpdate eventUpdate)
     */

}