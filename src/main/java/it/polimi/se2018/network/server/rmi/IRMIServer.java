package it.polimi.se2018.network.server.rmi;

import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.network.client.rmi.IRMIClient;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Class based on the Abstract Factory Design Pattern.
 * Class used to permit remote method invocation.
 * CLIENT -> SERVER
 *
 * The interface that define the class to export must:
 * Extends Remote Interface
 * Be Public
 * Every method must start the RemoteException
 *
 * @author DavideMammarella
 */
public interface IRMIServer extends Remote {

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO SERVER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Remote method used to log the user to the server with his nickname.
     * @param nickname name of the player.
     * @param iRMIClient client associated to the player.
     * */
    void login(String nickname, IRMIClient iRMIClient) throws RemoteException;

    /**
     * Remote method used to send an object to the Server with request to set off an event.
     *
     * @param eventController object that will use the server to set off the event associated.
     */
    void sendEventToController(EventController eventController) throws RemoteException;

    /**
     * Remote method used to disconnect a client from the server.
     *
     * @param id id of the player
     */
    void disconnect(int id) throws IOException;
}

