package it.polimi.se2018.alternative_network.newserver.socket;

import it.polimi.se2018.alternative_network.newserver.AbstractServer2;
import it.polimi.se2018.alternative_network.newserver.Server2;
import it.polimi.se2018.exception.network_exception.ServerSideException;

import java.net.ServerSocket;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class based on the Abstract Factory Design Pattern.
 * The class define the Socket server for every single game.
 * Extends AbstractServer abstract class in order to implement and facilitate Server connection.
 *
 * @author DavideMammarella
 */
public class SocketServer extends AbstractServer2 {

    // Utilizzo variabili atomiche perchè evitano problemi di concorrenza
    // Così prevengo conflitti nel settaggio e check delle variabili da metodi differenti
    private final AtomicBoolean running = new AtomicBoolean();
    // socket lato server
    private ServerSocket serverSocket;

    // LISTA DEI GIOCATORI CHE HANNO EFFETTUATO IL LOGIN ED HANNO UN NICKNAME
    //static ArrayList<SocketPlayer> socketPlayers= new ArrayList<>();
    private ClientGatherer clientGatherer;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Socket Server constructor.
     *
     * @param serverController server interface, used as controller to communicate with the server.
     */
    public SocketServer(Server2 serverController, String host, int port) {
        super(serverController, host, port);
        running.set(false);
    }

    //------------------------------------------------------------------------------------------------------------------
    // SERVER STARTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Starter for Socket Server.
     */
    @Override
    public void startServer() {
        try {
            if (!running.get()) {
                // Inizializzo il server socket
                clientGatherer = new ClientGatherer(getPort(), getServerController());
                running.set(true);
                new Thread(clientGatherer).start();
            } else {
                throw new ServerSideException();
            }
        } catch (ServerSideException e) {
            System.err.println("Socket Server Connection refused on this port!");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // SUPPORTER METHODS
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Getter for the pre-game thread status.
     *
     * @return true if thread is running, false otherwise.
     */
    public boolean getServerStatus() {
        return this.running.get();
    }

    /**
     * Stopper for the client gatherer thread.
     */
    public void stopServer() {
        clientGatherer.stop();
    }
}