package it.polimi.se2018.network.server;

import it.polimi.se2018.network.server.game.Game;
import it.polimi.se2018.network.server.game.RemotePlayer;
import it.polimi.se2018.network.server.socket.SocketServer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that define the server side of the game.
 *
 * @author Davide Mammarella
 */
public class Server {

    //Porta su cui si appoggierà la comunicazione socket
    public static final int SOCKET_PORT=16180;
    //Porta su cui si appoggierà la comunicazione RMI
    //public static final int RMI_PORT=31415;

    // Socket Server
    private SocketServer socketServer;
    // RMI Server
    //private RMIServer rmiServer;

    // Lista Partite
    private ArrayList<Game> gameList;

    //Giocatori connessi al server <username, RemotePlayer>
    private HashMap<String,RemotePlayer> players;

    //MUTEX usato per gestire un login alla volta (senza questo potrebbe crearsi congestione al login)
    private static final Object PLAYERS_MUTEX = new Object();

    /**
     * Server Constructor (instantiate a new Server Class).
     */
    // MANCA EXCEPTION
    public Server(){
        this.players = new HashMap<>();
        this.gameList = new ArrayList<>();

        this.socketServer = new SocketServer();
        //this.rmiServer = new RMIServer;
    }

    /**
     * Server starter.
     *
     * @param args parameters for the connection
     */
    // verifica EXCEPTION
 /*   public static void main(String[] args) {
        int socketPort = SOCKET_PORT;
        //int rmiPort = RMI_PORT;

        try {
            Server server = new Server();
            server.startServer(socketPort);
            //server.startServer(socketPort, rmiPort); cancella solo socket se implementi anche RMI
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * Start the server based on the technology selected from the client.
     * Now only for socket but can be extended for RMI.
     *
     * @param socketPort port used to start socket connection
     */
    // int rmiPort
    // rmiServer.StartServer (rmiPort)
 /*   public void startServer(int socketPort){
        socketServer.startServer(socketPort);
    }*/
    //-------------------------------------------
    // METODI DA INVOCARE SUL SERVER CONTROLLER
    //-------------------------------------------

    // LOGIN/AGGIUNTA GIOCATORE TRAMITE USERNAME (RemotePlayer)

    // getPlayerByUsername (RemotePlayer)

    // CREAZIONE PARTITA
}
