package it.polimi.se2018.alternative_network.client.rmi_client;

import it.polimi.se2018.list_event.event_received_by_server.ServerVisitor;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * interface of the receiver
 */
public interface RMIClientInterface extends Remote {

    void notifyTheClient(EventClient message) throws RemoteException;

    String ping(String name)throws RemoteException;
}
