package it.polimi.se2018.network.client.rmi;

import it.polimi.se2018.list_event.event_received_by_view.EventView;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Class based on the Abstract Factory Design Pattern.
 * Class used to permit remote method invocation.
 * SERVER -> CLIENT
 * <p>
 * The interface that define the class to export must:
 * Extends Remote Interface
 * Be Public
 * Every method must start the RemoteException
 *
 * @author DavideMammarella
 */
public interface IRMIClient extends Remote {

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Remote method used to send to the client an update of the game.
     *
     * @param eventView object that will use the client to unleash the update associated.
     */
    void sendEventToView(EventView eventView) throws RemoteException;

    void ping() throws RemoteException;

}