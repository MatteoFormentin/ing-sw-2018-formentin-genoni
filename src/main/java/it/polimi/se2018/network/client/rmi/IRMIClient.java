package it.polimi.se2018.network.client.rmi;

import it.polimi.se2018.list_event.event_controller.EventController;
import it.polimi.se2018.list_event.event_view.EventView;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote interface used for the notify.
 *
 * @author DavideMammarella
 */
public interface IRMIClient extends Remote {
    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    // NOTIFY
    //------------------------------------------------------------------------------------------------------------------


    public void sendEventToView(EventView eventView) throws RemoteException;

}