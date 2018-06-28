package it.polimi.se2018.network.server.socket;

import it.polimi.se2018.network.server.AbstractServer;
import it.polimi.se2018.network.server.ServerController;

import java.net.ServerSocket;

/**
 * Class based on the Abstract Factory Design Pattern.
 * The class define the Socket server for every single game.
 * Extends AbstractServer abstract class in order to implement and facilitate Server connection.
 *
 * @author DavideMammarella
 */
public class SocketServer extends AbstractServer {

    // socket lato server
    private ServerSocket serverSocket;
    private ClientGatherer clientGatherer;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Socket Server constructor.
     *
     * @param serverController server interface, used as controller to communicate with the server.
     */
    public SocketServer(ServerController serverController) {
        super(serverController);
    }

    //------------------------------------------------------------------------------------------------------------------
    // SERVER STARTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Starter for Socket Server.
     *
     * @param port number of port that will be used on the connection.
     */
    @Override
    public void startServer(int port) {
        // Inizializzo il server socket
        System.out.println("Socket Server running at " + port + " port...");
        clientGatherer = new ClientGatherer(port, getServerController());
        new Thread(clientGatherer).start();
    }

    public void stopServer() {
        clientGatherer.stop();
    }
}