package it.polimi.se2018.alternative_network.newserver.socket;

import it.polimi.se2018.alternative_network.newserver.AbstractServer2;
import it.polimi.se2018.alternative_network.newserver.Server2;
import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.exception.network_exception.server.ServerStartException;

import java.io.IOException;
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

    private final Server2 server;
    private final String host;
    private final int port;
    // Utilizzo variabili atomiche perchè evitano problemi di concorrenza
    // Così prevengo conflitti nel settaggio e check delle variabili da metodi differenti
    private final AtomicBoolean running;
    private boolean started;
    // LISTA DEI GIOCATORI CHE HANNO EFFETTUATO LA CONNESSIONE AL SERVER SENZA LOGIN
    private LinkedList<SocketPlayer> socketPlayers;
    private ClientGatherer2 clientGatherer2;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * @param serverController server implementation
     * @param host             host
     * @param port             port
     */
    public SocketServer(Server2 serverController, String host, int port) {
        this.server = serverController;
        this.host = host;
        this.port = port;
        socketPlayers = new LinkedList<>();
        running = new AtomicBoolean(false);
        started = false;
    }

    //------------------------------------------------------------------------------------------------------------------
    // SERVER STARTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Starter for Socket Server.
     */
    @Override
    public void startServer() throws ServerStartException {
        try {
            //servizio offerto al client
            clientGatherer2 = new ClientGatherer2(server, getPort());
            running.set(true);
            clientGatherer2.start();
            started = true;
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new ServerStartException(ex.getMessage());
        }
    }

    protected synchronized void login(SocketPlayer newSocketPlayer) throws PlayerAlreadyLoggedException, RoomIsFullException {
        getServer().login(newSocketPlayer);
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
        clientGatherer2.stopGatherer();
        started = false;
    }

    @Override
    public Server2 getServer() {
        return server;
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
        this.started = started;
    }
}