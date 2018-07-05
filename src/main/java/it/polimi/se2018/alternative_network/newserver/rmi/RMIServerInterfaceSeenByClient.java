package it.polimi.se2018.alternative_network.newserver.rmi;

import it.polimi.se2018.alternative_network.client.rmi_client.RMIClientInterface;
import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * All the method of this interface MUST implement the RemoteException
 * It's what the player can ask to the RMIServer.
 */
public interface RMIServerInterfaceSeenByClient extends Remote {

    void addClient(String nickname, RMIClientInterface client) throws RemoteException, PlayerAlreadyLoggedException, RoomIsFullException;

    void disconnect(RMIClientInterface client) throws RemoteException;

    String sayHelloToGatherer() throws RemoteException;

    void sendEventToController(EventController event) throws RemoteException;
}
