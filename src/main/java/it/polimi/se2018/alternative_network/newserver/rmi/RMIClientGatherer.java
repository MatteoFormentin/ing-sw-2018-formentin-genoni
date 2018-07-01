package it.polimi.se2018.alternative_network.newserver.rmi;

import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.alternative_network.client.RMIClientInterface;
import it.polimi.se2018.alternative_network.newserver.Server2;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Cass that build and destroy the connection with the Rmi player
 * this is the class that can receive the messages from the player
 */
public class RMIClientGatherer extends UnicastRemoteObject implements RMIServerInterfaceSeenByClient {
    private static RMIClientGatherer instance;
    private Server2 mainServer;

    private RMIClientGatherer(Server2 mainServer) throws RemoteException {
        super(mainServer.getRMI_PORT());
        this.mainServer = mainServer;
    }
    //********************************* FROM THE SERVER **********************************************
    //********************************* FROM THE SERVER **********************************************
    //********************************* FROM THE SERVER **********************************************

    public static RMIClientGatherer getSingletonClientGatherer(Server2 mainServer)throws RemoteException{
        try {
            if (instance == null) instance = new RMIClientGatherer(mainServer);
            return instance;
        }catch(RemoteException ex){
            System.out.println("non puoi creare l'istanza di RMIClientGatherer");
            throw ex;
        }
    }
    //__________________________________________________________
    //                                                          |
    //      FROM THE CLIENT ALL MUST IMPLEMENT RemoteException  |
    //__________________________________________________________|

    @Override
    public void addClient(String nickname, RMIClientInterface client) throws RemoteException,PlayerAlreadyLoggedException, RoomIsFullException {
        //il collegamento viene assegnato al RMIPLayer
        RMIPlayer player = new RMIPlayer(nickname, client);
        mainServer.login(player);
    }

    @Override
    public void disconnect(int idPlayer,int idGame, RMIClientInterface client) throws RemoteException {
        //TODO implement a legal disconnection Call main server? mmmm nah
        try {
            UnicastRemoteObject.unexportObject(client, true);
            //TODO notify the server for the disconnection
        }catch(NoSuchObjectException ex){
            ex.printStackTrace();
            System.out.println("RMIClientGatherer, kickPlayer : è stato già eliminato l'RMI Player"+ex.getMessage());
        }
    }

    @Override
    public void sendEventToController(EventController event) throws RemoteException{
        mainServer.sendEventToGameRoom(event);
    }

    @Override
    public String sayHelloToGatherer() throws RemoteException {
        return "Ok, connesso";
    }

}