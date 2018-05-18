package it.polimi.se2018.network.server;

import it.polimi.se2018.network.server.game.Lobby;
import it.polimi.se2018.network.server.game.RemotePlayer;
import it.polimi.se2018.network.server.socket.SocketServer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that define the server side of the game.
 *
 * @author Davide Mammarella
 */
public class Server implements ServerController {

    //Porta su cui si appoggierà la comunicazione socket
    public static final int SOCKET_PORT=61803;

    // Socket Server
    private SocketServer socketServer;

    // Lobbies del server
    private ArrayList<Lobby> lobbies;

    //Giocatori connessi al server <username, RemotePlayer>
    private HashMap<String,RemotePlayer> players;

    //manca la throws Exception
    /**
     * Method used to instantiate a new Server Class.
     */
    public Server(){
        players = new HashMap<>();
        lobbies = new ArrayList<>();
        socketServer = new SocketServer(this);
    }

    //manca la throws Exception
    /**
     * Sererver method starter.
     *
     * @param args parameters for the connection
     */
    public static void main(String[] args) {
        int socketPort = SOCKET_PORT;
        Server server = new Server();
        server.startServer(socketPort);
    }

    /**
     * Start the server based on the technology, now only Socket.
     *
     * @param socketPort port used to start socket connection
     */
    public void startServer(int socketPort){
        startSocketServer(socketPort);
    }

    /**
     * Start SocketServer.
     *
     * @param socketPort port used to start socket connection
     */
    public void startSocketServer(int socketPort){
        socketServer.startServer(socketPort);
    }

}
