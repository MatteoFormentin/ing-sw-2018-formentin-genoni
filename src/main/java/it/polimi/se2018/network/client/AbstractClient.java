package it.polimi.se2018.network.client;

/**
 * Abstract class based on the Abstract Factory Design Pattern
 * (It provides an interface to create families of connected or dependent objects, so that there is no need for clients to specify the names of the concrete classes within their own code.)
 * This class give to the client the possibility to utilize different type of connection (RMI or Socket) without problem, like an Adapter.
 * This class will be extended from RMI or Socket Cerver class.
 *
 * @author DavideMammarella
 */
public abstract class AbstractClient {

    //Client Interface.
    private final ClientController clientController;

    //Server Address.
    private final String ipAddress;

    //Port used from server to communicate.
    private final int port;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Client abstract constructor. (Socket or RMI)
     *
     * @param clientController client interface, used as
     *                         controller to communicate with the client.
     * @param ipAddress server address.
     * @param port port used from server to communicate.
     */
    public AbstractClient(ClientController clientController, String ipAddress, int port) {
        this.clientController = clientController;
        this.ipAddress = ipAddress;
        this.port = port;
    }

    //------------------------------------------------------------------------------------------------------------------
    // GETTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Getter for server IP address.
     *
     * @return server address.
     */
    public String getIpAddress(){
        return ipAddress;
    }

    /**
     * Getter for server port.
     *
     * @return server port used to communicate.
     */
    public int getPort(){
        return port;
    }

    //------------------------------------------------------------------------------------------------------------------
    // RICHIESTE DA INVIARE AL SERVER PER SVOLGERE UN'AZIONE DI GIOCO
    // public abstract void ...(...);
    //------------------------------------------------------------------------------------------------------------------

    // esempio
    public abstract void connect();
}
