package it.polimi.se2018.network.server.rmi;

import it.polimi.se2018.network.RemotePlayer;
import it.polimi.se2018.network.client.rmi.IRMIClient;

/**
 * Class that define the RMI Player.
 *
 * @author DavideMammarella
 */
public class RMIPlayer extends RemotePlayer {

    private transient IRMIClient iRMIClient;

    public RMIPlayer(IRMIClient iRMIClient) {
        this.iRMIClient=iRMIClient;
    }

    //AGGIORNAMENTI (NOTIFY) DA INVIARE AL GIOCATORE (CLIENT)
}
