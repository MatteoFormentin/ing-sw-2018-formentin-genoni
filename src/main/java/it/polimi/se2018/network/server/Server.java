package it.polimi.se2018.network.server;

import it.polimi.se2018.network.socket.SocketConnection;
import it.polimi.se2018.network.socket.SocketServer;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that define the server side of every single game.
 *
 * @author Davide Mammarella
 */
public class Server {

    private static Server currentServer;

    private static SocketServer socketServer;
    private static final Integer SOCKET_PORT=16180;

    private static List<Lobby> lobbies;

    /**
     * Server constructor
     * lobbies are in an ArrayList because i want a lobby for every single player
     */
    private Server(){
        lobbies=new ArrayList<>();

        socketServer=new SocketServer(SOCKET_PORT);
    }

    /**
     * Getter for the new server.
     * @return a new server
     */
    public static Server getServerInstance(){
        if(currentServer==null){
            currentServer=new Server();
        }

        return currentServer;
    }

    public static boolean login(String username, String password){
        return false;
    }

    public static Lobby addPlayerToLobby(String username, ServerController serverController){
    return null;
    }

    public static void removeSocketConnection(SocketConnection socketConnection){
        return;
    }

}
