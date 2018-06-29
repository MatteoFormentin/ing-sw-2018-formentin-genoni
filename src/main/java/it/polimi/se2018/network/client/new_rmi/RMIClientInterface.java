package it.polimi.se2018.network.client.new_rmi;

import it.polimi.se2018.list_event.event_received_by_view.EventView;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface  extends Remote {

    void notifyTheClient(EventView message) throws RemoteException;

    String sayHelloClient() throws RemoteException;
}
