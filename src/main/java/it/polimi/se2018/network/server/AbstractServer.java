package it.polimi.se2018.network.server;

/**
 * Abstract class based on the Abstract Factory Design Pattern.
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
     * Server abstract constructor.
     * Used to permit communication (Socket or RMI) with server.
     *
     * @param serverController server interface, used as controller to communicate with the server.
     */
    public AbstractServer(ServerController serverController) {
        this.serverController = serverController;
    }

    //------------------------------------------------------------------------------------------------------------------
    // GETTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Getter for Server Controller that will handle request based on the different type of technologies.
     *
     * @return Server Controller in order to manage request for communication (RMI or Socket).
     */
    public ServerController getServerController() {
        return serverController;
    }

    //------------------------------------------------------------------------------------------------------------------
    // SERVER STARTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Start every:
     * RMI Connection
     * Socket Connection
     *
     * @param port number of port that will be used on the connection.
     */
    public abstract void startServer(int port) throws Exception;

    // END
}
