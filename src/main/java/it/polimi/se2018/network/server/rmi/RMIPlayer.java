package it.polimi.se2018.network.server.rmi;


import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.network.RemotePlayer;
import it.polimi.se2018.network.client.rmi.IRMIClient;

import java.rmi.RemoteException;

/**
 * Class that extends the RemotePlayer in order to define an RMI Player.
 *
 * @author DavideMammarella
 */
public class RMIPlayer extends RemotePlayer {

    // CLIENT INTERFACE
    private transient IRMIClient iRMIClient;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * RMI Player constructor.
     *
     * @param iRMIClient client associated to the player.
     */
    public RMIPlayer(IRMIClient iRMIClient) {
        this.iRMIClient = iRMIClient;
        // TODO VERIFICARE CHE FUNZIONI REALMENTE
        playerRunning=true;
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Remote method used to send to the client an update of the game.
     *
     * @param eventView object that will use the client to unleash the update associated.
     */
    @Override
    public void sendEventToView(EventView eventView) throws RemoteException{
        iRMIClient.sendEventToView(eventView);
    }

    @Override
    public String sayHelloClient() throws RemoteException {
        return null;
    }

    //------------------------------------------------------------------------------------------------------------------
    // SUPPORTER METHODS
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Getter for the interface of the RMI Client.
     *
     * @return client associated to the player.
     */
    public IRMIClient getiRMIClient() {
        return iRMIClient;
    }

}
