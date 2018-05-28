package it.polimi.se2018.network.server.rmi;

import it.polimi.se2018.network.RemotePlayer;
import it.polimi.se2018.network.client.rmi.IRMIClient;
import javafx.beans.InvalidationListener;

/**
 * Class that define the RMI Player.
 *
 * @author DavideMammarella
 */
public class RMIPlayer extends RemotePlayer {

    // Interfaccia del Client
    private transient IRMIClient iRMIClient;

    public RMIPlayer(IRMIClient iRMIClient) {
        this.iRMIClient=iRMIClient;
    }

    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    // NOTIFY
    //------------------------------------------------------------------------------------------------------------------

    /*
    @Override
    public void notify(EventUpdate eventUpdate){
        ClientController.notify(eventUpdate);
    }
    */
}
