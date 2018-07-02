package it.polimi.se2018.alternative_network.client.rmi_client;

import it.polimi.se2018.list_event.event_received_by_view.EventView;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * interface of the receiver
 */
public interface RMIClientInterface  extends Remote {

    void notifyTheClient(EventView message) throws RemoteException;

    String pong(String message) throws RemoteException;

}
