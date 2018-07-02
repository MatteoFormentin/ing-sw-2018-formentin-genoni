package it.polimi.se2018.network.client;

import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.view.UIInterface;

import java.rmi.RemoteException;

/**
 * Abstract class based on the Abstract Factory Design Pattern
 * This class give to the client the possibility to utilize different type of connection (RMI or Socket) without problem, like an Adapter.
 * This class will be extended from RMI or Socket Client class.
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

    private final UIInterface view;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Client abstract constructor (without view). (Socket or RMI)
     *
     * @param clientController client interface, used as controller to communicate with the client.
     * @param serverIpAddress  server address.
     * @param serverPort       port used from server to communicate.
     */
    public AbstractClient(ClientController clientController, String serverIpAddress, int serverPort) {
        this.clientController = clientController;
        this.serverIpAddress = serverIpAddress;
        this.serverPort = serverPort;
        this.view=null;
    }

    /**
     * Client abstract constructor (with view). (Socket or RMI)
     *
     * @param clientController client interface, used as controller to communicate with the client.
     * @param serverIpAddress  server address.
     * @param serverPort       port used from server to communicate.
     */
    public AbstractClient(ClientController clientController, String serverIpAddress, int serverPort, UIInterface view) {
        this.clientController = clientController;
        this.serverIpAddress = serverIpAddress;
        this.serverPort = serverPort;
        this.view = view;
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to establish a connection with the server).
     */
    public abstract void connectToServer() throws Exception;

    /**
     * Method used to log the user to the server with his nickname.
     *
     * @param nickname name of the player associated to the client.
     */
    public abstract void login(String nickname) throws RemoteException, PlayerAlreadyLoggedException;

    /**
     * Method used to send to the server a request to unleash an event.
     *
     * @param eventController object that will use the server to unleash the event associated.
     */
    public abstract void sendEventToController(EventController eventController) throws RemoteException;

    /**
     * Method used to disconnect a client from the server.
     */
    public abstract void disconnect() throws RemoteException;

    //------------------------------------------------------------------------------------------------------------------
    // SUPPORTER METHODS
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Getter for server IP address.
     *
     * @return server address.
     */
    public String getServerIpAddress() {
        return serverIpAddress;
    }

    /**
     * Getter for server port.
     *
     * @return server port used to communicate.
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     * Getter for the client controller.
     *
     * @return client controller used to manage the communication.
     */
    public ClientController getClientController() {
        return clientController;
    }

    /**
     * Getter for the client view.
     *
     * @return client view.
     */
    public UIInterface getView() {
        return view;
    }
}
