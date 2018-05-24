package it.polimi.se2018.network.client.rmi;

import it.polimi.se2018.network.client.AbstractClient;
import it.polimi.se2018.network.client.ClientController;
import it.polimi.se2018.network.server.rmi.IRMIServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Class based on the Abstract Factory Design Pattern.
 * The class define the RMI client for every single game.
 * Extends AbstractClient class in order to implement and facilitate Client connection.
 *
 * @author DavideMammarella
 */
public class RMIClient extends AbstractClient implements IRMIClient {

    //Interfaccia server dell'RMI, viene utilizzata per permettere l'invocazione remota di metodi
    private IRMIServer iRMIServer;

    // Username del giocatore
    private String username;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Instantiate one RMIClient.
     *
     * @param clientController client interface, used as
     *                         controller to communicate with the client.
     * @param ipAddress server address.
     * @param port port used from server to communicate.
     */
    public RMIClient(ClientController clientController, String ipAddress, int port){
        super(clientController, ipAddress, port);
    }

    //------------------------------------------------------------------------------------------------------------------
    // CONNECTION
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Estabilish a connection with the RMI Registry
     */
    // SISTEMA EXCEPTION
    @Override
    public void connect() {
        Registry registry = null;

        try {
            registry = LocateRegistry.getRegistry(getIpAddress(), getPort());
            iRMIServer = (IRMIServer) registry.lookup("IRMIServer");
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // CHIAMATE DAL SERVER (METODI INVOCATI DAL SERVER)
    // public void NOTIFICA(...)
    //------------------------------------------------------------------------------------------------------------------

}
