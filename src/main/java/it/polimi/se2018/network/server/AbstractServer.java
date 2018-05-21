package it.polimi.se2018.network.server;

/**
 * Class based on the Abstract Factory Design Pattern
 * (It provides an interface to create families of connected or dependent objects, so that there is no need for clients to specify the names of the concrete classes within their own code.)
 * Extending this class give the flexibility to connect the server to different technologies (RMI/Socket).
 *
 * @author Davide Mammarella
 */
public abstract class AbstractServer {

    /**
     * Server Interface.
     * Used to communicate with the server.
     */
    private final ServerController serverController;

    /**
     * Server abstract constructor. (Socket or RMI)
     * Used to permit communication with server.
     *
     * @param serverController server interface, used as controller for the server.
     */
    public AbstractServer(ServerController serverController) {
        this.serverController = serverController;
    }

    /**
     * Return Server Controller in order to handle request for communication.
     * The server controller will handle request based on the different type of technologies (Socket or RMI)
     *
     */
    public ServerController getServerController() {
        return serverController;
    }

    /**
     * Start every:
     * RMI Connection
     * Socket Connection
     *
     * @param port number of port that will be used.
     */
    // MANCA EXCEPTION
    public abstract void startServer(int port);
}
