package it.polimi.se2018.alternative_network.client.rmi_client;

import it.polimi.se2018.list_event.event_received_by_view.EventClient;

import java.rmi.RemoteException;

/**
 * this is the class remote class
 *
 * @author DavideMammarella
 */
public class RemoteClientReceiver implements RMIClientInterface {
    //for
    private RMIClient2StartAndInput instanceClient;

    RemoteClientReceiver(RMIClient2StartAndInput instanceClient) {
        this.instanceClient = instanceClient;
    }


    @Override
    public void notifyTheClient(EventClient message) throws RemoteException {
        this.instanceClient.sendEventToUIInterface2(message);
    }

    @Override
    public String ping(String name) throws RemoteException {
        return "pong";
    }

}
