package it.polimi.se2018.alternative_network.newserver.socket;

import it.polimi.se2018.alternative_network.newserver.AbstractServer2;
import it.polimi.se2018.alternative_network.newserver.Server2;
import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.exception.network_exception.server.ServerStartException;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class based on the Abstract Factory Design Pattern.
 * The class define the Socket server for every single game.
 * Extends AbstractServer abstract class in order to implement and facilitate Server connection.
 *
 * @author DavideMammarella
 */
public class SocketServer implements AbstractServer2 {

    private final Server2 serverController;
    private final String host;
    private final int port;
    private boolean started;
    // Utilizzo variabili atomiche perchè evitano problemi di concorrenza
    // Così prevengo conflitti nel settaggio e check delle variabili da metodi differenti
    private final AtomicBoolean running;


    // LISTA DEI GIOCATORI CHE HANNO EFFETTUATO LA CONNESSIONE AL SERVER SENZA LOGIN
    private LinkedList<SocketPlayer> socketPlayers;
    private ClientGatherer2 clientGatherer2;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     *
     * @param serverController server implementation
     * @param host host
     * @param port port
     */
    public SocketServer(Server2 serverController, String host, int port) {
        this.serverController=serverController;
        this.host=host;
        this.port=port;
        socketPlayers = new LinkedList<>();
        running = new AtomicBoolean(false);
    }

    //------------------------------------------------------------------------------------------------------------------
    // SERVER STARTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Starter for Socket Server.
     */
    @Override
    public void startServer()throws ServerStartException {
        try {
            //servizio offerto al client
            clientGatherer2 = new ClientGatherer2(this, getPort());
            running.set(true);
            clientGatherer2.start();
            System.out.println("Acceso Sokcet"+clientGatherer2.getState());
        }catch(IOException ex){
            throw new ServerStartException(ex.getMessage());
        }
    }


    protected synchronized void addClient(Socket clientConnection) {

        // Creo un VirtualClient per ogni nuovo client,
        // per ogni client un thread che ascolta i messaggi provenienti da quel client
        SocketPlayer newPlayer = new SocketPlayer(this,clientConnection);
        socketPlayers.add(newPlayer);
        newPlayer.run();
    }

    protected synchronized void login(SocketPlayer newSocketPlayer)throws PlayerAlreadyLoggedException,RoomIsFullException {
        getServerController().login(newSocketPlayer);
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
        clientGatherer2.stop();
    }

    @Override
    public Server2 getServerController() {
        return serverController;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    @Override
    public void setStarted(boolean started) {
        this.started=started;
    }
}