package it.polimi.se2018.network.server.rmi;

import it.polimi.se2018.event.list_event.EventView;
import it.polimi.se2018.network.client.rmi.IRMIClient;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
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

    /**
     * Remote method used to login.
     *
     * @param username name of the player.
     * @param iRMIClient client associated to the player.
     * @return username that define the player.
     * @throws IOException
     */
    // GESTISCI EXCEPTION
    String Login(String username, IRMIClient iRMIClient) throws IOException;

    /**
     * Remote method used to send an object to the Server with request to set off an event.
     *
     * @param username name of the player.
     * @param eventView object that will use the server to set off the event associated.
     * @throws RemoteException exception unleashed if the server is not attainable
     */
    void sendEvent(String username, EventView eventView) throws RemoteException;
}
