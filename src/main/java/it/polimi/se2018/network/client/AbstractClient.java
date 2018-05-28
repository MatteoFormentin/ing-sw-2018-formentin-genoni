package it.polimi.se2018.network.client;

import it.polimi.se2018.event.list_event.EventView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Abstract class based on the Abstract Factory Design Pattern
 * (It provides an interface to create families of connected or dependent objects, so that there is no need for clients to specify the names of the concrete classes within their own code.)
 * This class give to the client the possibility to utilize different type of connection (RMI or Socket) without problem, like an Adapter.
 * This class will be extended from RMI or Socket Server class.
 *
 * @author DavideMammarella
 */
public abstract class AbstractClient {

    //Client Interface.
    private final ClientController clientController;

    //Server Address.
    private final String serverIpAddress;

    //Port used from server to communicate.
    private final int serverPort;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Client abstract constructor. (Socket or RMI)
     *
     * @param clientController client interface, used as
     *                         controller to communicate with the client.
     * @param serverIpAddress server address.
     * @param serverPort port used from server to communicate.
     */
    public AbstractClient(ClientController clientController, String serverIpAddress, int serverPort) {
        this.clientController = clientController;
        this.serverIpAddress = serverIpAddress;
        this.serverPort = serverPort;
    }

    //------------------------------------------------------------------------------------------------------------------
    // GETTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Getter for server IP address.
     *
     * @return server address.
     */
    public String getServerIpAddress(){
        return serverIpAddress;
    }

    /**
     * Getter for server port.
     *
     * @return server port used to communicate.
     */
    public int getServerPort(){
        return serverPort;
    }

    /**
     * Getter for the client controller.
     *
     * @return client controller used to manage the communication.
     */
    public ClientController getClientController(){
        return clientController;
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------

    public abstract void connectToServer() throws RemoteException, NotBoundException;

    public abstract void login(String nickname) throws RemoteException;

    public abstract void sendEvent(EventView eventView) throws RemoteException;

    // BASTA METODI
}
