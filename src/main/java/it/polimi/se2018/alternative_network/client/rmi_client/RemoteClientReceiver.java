package it.polimi.se2018.alternative_network.client.rmi_client;

import it.polimi.se2018.list_event.event_received_by_view.EventView;

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
