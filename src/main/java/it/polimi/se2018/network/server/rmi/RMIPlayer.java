package it.polimi.se2018.network.server.rmi;

import it.polimi.se2018.list_event.event_controller.EventView;
import it.polimi.se2018.list_event.event_view.EventController;
import it.polimi.se2018.network.RemotePlayer;
import it.polimi.se2018.network.client.rmi.IRMIClient;
import javafx.beans.InvalidationListener;

import java.rmi.RemoteException;

/**
 * Class that define the RMI Player.
 *
 * @author DavideMammarella
 */
public class RMIPlayer extends RemotePlayer {

    // Interfaccia del Client
    private transient IRMIClient iRMIClient;


    public RMIPlayer(IRMIClient iRMIClient) {
        this.iRMIClient = iRMIClient;
    }

    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }

    public IRMIClient getiRMIClient() {
        return iRMIClient;
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    // NOTIFY
    //------------------------------------------------------------------------------------------------------------------

    // Chiamato da server -- Se sono un rmiPlayer
    @Override
    public void sendEventToView(EventView eventView) throws RemoteException {
        iRMIClient.sendEventToView(eventView);
    }

}
