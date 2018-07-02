package it.polimi.se2018.alternative_network.client;

import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.view.UIInterface;
import it.polimi.se2018.view.cli.CliParser;

import java.rmi.RemoteException;

/**
 * this is the class remote class
 */
public class RemoteClientReceiver implements RMIClientInterface {
    //for
    private RMIClient2StartAndInput instanceClient;

    RemoteClientReceiver(RMIClient2StartAndInput instanceClient) {
        this.instanceClient = instanceClient;
    }

    @Override
    public void notifyTheClient(EventView message){
        instanceClient.sendEventToUIInterface2(message);
    }

    @Override
    public String pong(String ping){
        return "pong";
    }
}
