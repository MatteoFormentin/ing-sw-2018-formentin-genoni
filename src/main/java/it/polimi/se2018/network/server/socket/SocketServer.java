package it.polimi.se2018.network.server.socket;

import it.polimi.se2018.exception.network_exception.ServerSideException;
import it.polimi.se2018.network.server.AbstractServer;
import it.polimi.se2018.network.server.ServerController;

import java.net.ServerSocket;
import java.util.concurrent.atomic.AtomicBoolean;

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

    // LISTA DEI GIOCATORI CHE HANNO EFFETTUATO IL LOGIN ED HANNO UN NICKNAME
    //static ArrayList<SocketPlayer> socketPlayers= new ArrayList<>();

    // Utilizzo variabili atomiche perchè evitano problemi di concorrenza
    // Così prevengo conflitti nel settaggio e check delle variabili da metodi differenti
    private final AtomicBoolean running = new AtomicBoolean();

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
        running.set(false);
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
        try{
            if(!running.get()){
                // Inizializzo il server socket
                clientGatherer = new ClientGatherer(port, getServerController());
                running.set(true);
                new Thread(clientGatherer).start();
            } else {
                throw new ServerSideException();
            }
        } catch (ServerSideException e){
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
    public boolean getServerStatus(){
        return this.running.get();
    }

    /**
     * Stopper for the client gatherer thread.
     */
    public void stopServer() {
        clientGatherer.stop();
    }
}