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
     * Server abstract constructor.
     * Used to permit communication with server.
     *
     * @param serverController server interface, used as controller for the server.
     */
    public AbstractServer(ServerController serverController) {
        this.serverController = serverController;
    }

    /**
     * Start every server connection.
     *
     * @param port number of used port.
     */
    public abstract void startServer(int port);
}
