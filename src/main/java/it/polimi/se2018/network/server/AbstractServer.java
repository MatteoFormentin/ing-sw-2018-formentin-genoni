package it.polimi.se2018.network.server;

/**
 * Abstract class based on the Abstract Factory Design Pattern.
 * (It provides an interface to create families of connected or dependent objects, so that there is no need for clients to specify the names of the concrete classes within their own code.)
 * This class give to the server the possibility to utilize different type of connection (RMI or Socket) without problem, like an Adapter.
 * This class will be extended from RMI or Socket Server class.
 *
 * @author DavideMammarella
 */
public abstract class AbstractServer {

    // Server Interface
    // Used to communicate with the server
    private final ServerController serverController;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Server abstract constructor. (Socket or RMI)
     * Used to permit communication with server.
     *
     * @param serverController server interface, used as
     *                         controller to communicate with the server.
     */
    public AbstractServer(ServerController serverController) {
        this.serverController = serverController;
    }

    //------------------------------------------------------------------------------------------------------------------
    // GETTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Getter for Server Controller that will handle request based
     * on the different type of technologies.
     *
     * @return Server Controller in order to manage request for communication (RMI or Socket).
     */
    public ServerController getServerController() {
        return serverController;
    }

    //------------------------------------------------------------------------------------------------------------------
    // CONNECTION STARTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Start every:
     * RMI Connection
     * Socket Connection
     *
     * @param port number of port that will be used on the connection.
     */
    public abstract void startServer(int port) throws Exception;
}
