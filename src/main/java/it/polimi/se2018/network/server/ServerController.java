package it.polimi.se2018.network.server;

import it.polimi.se2018.event.list_event.EventView;
import it.polimi.se2018.network.RemotePlayer;

import java.rmi.RemoteException;

/**
 * Interface based on the Abstract Factory Design Pattern.
 * This interface is used as server controller in AbstractServer.
 * Implemented by Server class in order to provide basic methods for RMI and Socket Server.
 *
 * @author DavideMammarella
 */
public interface ServerController {

    // MANCANO EXCEPTION

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Log the user to the Server with the username.
     *
     * @param remotePlayer reference to RMI or Socket Playerz
     */
    boolean login(RemotePlayer remotePlayer);

    void startGame();

    void sendEventToController(EventView eventView);

    // BASTA METODI
}

