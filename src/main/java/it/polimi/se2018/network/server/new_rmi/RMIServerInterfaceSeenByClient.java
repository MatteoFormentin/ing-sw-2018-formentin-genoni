package it.polimi.se2018.network.server.new_rmi;

import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.network.client.new_rmi.RMIClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterfaceSeenByClient extends Remote{

    void addClient(RMIClientInterface client) throws RemoteException;

    void login(String nickname, RMIClientInterface client) throws RemoteException, PlayerAlreadyLoggedException;

    void send(String messageForServer) throws RemoteException;

    void sendEventToController(EventController event) throws RemoteException;

    void disconnect(String nickname, RMIClientInterface client) throws RemoteException;

    String sayHelloServer() throws RemoteException;
}
