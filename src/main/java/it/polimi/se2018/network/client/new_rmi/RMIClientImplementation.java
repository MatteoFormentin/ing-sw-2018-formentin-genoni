package it.polimi.se2018.network.client.new_rmi;

import it.polimi.se2018.list_event.event_received_by_view.EventView;

import java.rmi.RemoteException;

public class RMIClientImplementation implements RMIClientInterface{
    //this is the main Client that hold and manage the connection

    @Override
    public void notifyTheClient(EventView message) throws RemoteException {

    }

    @Override
    public String sayHelloClient() throws RemoteException {
        return "Ehi hi";
    }
}
