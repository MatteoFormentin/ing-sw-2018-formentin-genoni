package it.polimi.se2018.network.server;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.network.RemotePlayer;
import it.polimi.se2018.network.server.rmi.RMIServer;
import it.polimi.se2018.network.server.socket.SocketServer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class based on the Abstract Factory Design Pattern.
 * This class define the server side of the game.
 * This class implements ServerController to have basic methods for RMI and Socket Server.
 *
 * @author DavideMammarella
 */
public class Server implements ServerController{

    //Porta su cui si appoggierà la comunicazione Socket
    public static final int SOCKET_PORT=16180;
    //Porta su cui si appoggierà la comunicazione RMI
    public static final int RMI_PORT=31415;

    // Socket Server
    private SocketServer socketServer;
    // RMI Server
    private RMIServer rmiServer;

    // Lista Partite
    private ArrayList<Controller> gameList;
    //Giocatori connessi al server <username, RemotePlayer>
    private HashMap<String,RemotePlayer> players;

    //MUTEX usato per gestire un login alla volta (senza questo potrebbe crearsi congestione durante il login)
    private static final Object PLAYERS_MUTEX = new Object();

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Server Constructor.
     */
    // ORA SOLO RMI, MANCA EXCEPTION
    public Server(){
        this.players = new HashMap<>();
        this.gameList = new ArrayList<>();

        rmiServer = new RMIServer(this);
        //socketServer = new SocketServer(this);
    }

    /**
     * Starter for the server.
     * This method start the server and put it listen on the RMI port.
     *
     * @param args parameters for the connection
     */
    // ORA SOLO RMI, MANCA EXCEPTION
    public static void main(String[] args) {
        int rmiPort = RMI_PORT;
        //int socketPort = SOCKET_PORT;

        try {
            Server server = new Server();
            server.startServer(rmiPort);
            //server.startServer(socketPort, rmiPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Put the server on listen.
     * The server will connect only with the technology selected from client.
     *
     * @param rmiPort port used on RMI connection
     */
    // int socketPort
    // socketServer.StartServer (socketPort)
    public void startServer(int rmiPort) throws Exception{
        rmiServer.startServer(rmiPort);
    }

    //------------------------------------------------------------------------------------------------------------------
    // METODI DA INVOCARE SUL SERVER CONTROLLER (RMI o Socket)
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Log the user to the Server with the username.
     * @param nickname name used for the player.
     * @param remotePlayer reference to RMI or Socket Player
     */
    //CONSIDERA IL CASO DEL RI LOGIN ELSE IF
    @Override
    public void login(String nickname, RemotePlayer remotePlayer) {
        synchronized (PLAYERS_MUTEX){
            if(!players.containsKey(nickname)){
                players.put(nickname, remotePlayer);
                remotePlayer.setNickName(nickname);
                this.joinRoom(remotePlayer);
            }
        }
    }

    @Override
    public RemotePlayer getPlayer(String nickname) {
        return players.get(nickname);
    }

    /**
     * Add the player to the room.
     *
     * @param remotePlayer
     */
    public void joinRoom(RemotePlayer remotePlayer){
        //
    }

}
