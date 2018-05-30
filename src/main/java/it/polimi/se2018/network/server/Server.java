package it.polimi.se2018.network.server;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.list_event.event_controller.EventView;
import it.polimi.se2018.list_event.event_view.EventController;
import it.polimi.se2018.list_event.event_controller.StartGame;
import it.polimi.se2018.network.RemotePlayer;
import it.polimi.se2018.network.server.rmi.RMIServer;
import it.polimi.se2018.network.server.socket.SocketServer;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Class based on the Abstract Factory Design Pattern.
 * This class define the server side of the game.
 * This class implements ServerController to have basic methods for RMI and Socket Server.
 *
 * @author DavideMammarella
 */
public class Server implements ServerController {

    //Porta su cui si appoggierà la comunicazione Socket
    public static final int SOCKET_PORT = 16180;
    //Porta su cui si appoggierà la comunicazione RMI
    public static final int RMI_PORT = 31415;

    // Socket Server
    private SocketServer socketServer;
    // RMI Server
    private RMIServer rmiServer;

    //MUTEX usato per gestire un login alla volta (senza questo potrebbe crearsi congestione durante il login)
    private static final Object PLAYERS_MUTEX = new Object();
    // NUM MINIMO DI GIOCATORI PER PARTITA
    public static final int minPlayers = 2;
    // NUM MASSIMO DI GIOCATORI PER PARTITA
    public static final int maxPlayers = 4;

    //GIOCATORI NELLA STANZA
    private final ArrayList<RemotePlayer> players;
    ServerController serverController;
    boolean flag = true;
    // GAME DELLA ROOM
    private Controller game;
    // TIME
    private long timeout;

    //STATO STANZA
    private boolean roomJoinable;
    int playerCounter = 0;


    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Server Constructor.
     */
    // ORA SOLO RMI, MANCA EXCEPTION
    public Server() {
        rmiServer = new RMIServer(this);
        //socketServer = new SocketServer(this);
        roomJoinable = true;
        players = new ArrayList<RemotePlayer>();
        //roomStartTimeout upload
        try {
            // LOAD FROM PROPERTIES
            Properties configProperties = new Properties();

            String config = "src/main/java/it/polimi/se2018/resources/configurations/gameroom_configuration.properties";
            FileInputStream input = new FileInputStream(config);

            configProperties.load(input);
            //*1000 per convertire in millisecondi
            timeout = Long.parseLong(configProperties.getProperty("roomStartTimeout")) * 1000;
            System.out.println(configProperties.getProperty("roomStartTimeout"));
        } catch (IOException e) {
            System.out.println("Sorry, file can't be found... Using default");
            timeout = 120 * 1000;
        }
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
    public void startServer(int rmiPort) throws Exception {
        System.out.println("RMI Server started...");
        rmiServer.startServer(rmiPort);
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Log the user to the Server with the username.
     *
     * @param remotePlayer reference to RMI or Socket Player
     * @return true if the user is logged, false otherwise (logged only if nickname doesn't exists)
     */
    //CONSIDERA IL CASO DEL RI LOGIN ELSE IF
    @Override
    public boolean login(RemotePlayer remotePlayer) {
        synchronized (PLAYERS_MUTEX) {
            if (roomJoinable && !checkPlayerNicknameExists(remotePlayer.getNickname())) {
                remotePlayer.setPlayerId(playerCounter);
                players.add(remotePlayer);
                playerCounter++;
                System.out.println("Player added...");
                if (players.size() == minPlayers) {
                    // FAI PARTIRE IL TEMPO DI ATTESA
                    startTimer();
                } else if (players.size() == maxPlayers) {
                    //TODO Termninate thread !!! altrimenti parte due volte
                    //TODO Problema: se giocatore si disconnette cosa succede? ora parte lo stesso
                    startGame();
                }
                return true;
            } else {
                System.out.println("player esiste");
                return false; //game is complete or nickname already exists
            }
        }
    }

    /**
     * Start timer for room
     */
    public void startTimer() {
        System.out.println("Timeout started...");
        new Thread(new Timer(timeout, this)).start();

    }

    /**
     *
     *
     */
    public void startGame() {
        game = new Controller(this, players.size());
        System.out.println("Closing Room...");
        roomJoinable = false;
        for (RemotePlayer player : players) {
            try {
                EventView packet = new StartGame();
                packet.setPlayerId(player.getPlayerId());
                player.sendEventToView(packet);
            } catch (RemoteException ex) {
                ex.printStackTrace();
                //Disconnessione
            }
        }
        game.startGame();
    }

    @Override
    public void sendEventToController(EventController eventController) {
        game.sendEventToController(eventController);
    }

    //Chiamato dal controller -- indipendente dal tipo di connessione --si vede il tipo dinamico
    @Override
    public void sendEventToView(EventView eventView) {
        try {
            searchPlayerById(eventView.getPlayerId()).sendEventToView(eventView);
        } catch (RemoteException ex) {
            //Disconnessione

        }
    }

    private boolean checkPlayerNicknameExists(String nickname) {
        for (RemotePlayer player : players) {
            if (player.getNickname().equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    private RemotePlayer searchPlayerById(int id) {
        for (RemotePlayer player : players) {
            if (player.getPlayerId() == id) {
                return player;
            }
        }
        return null; //Se arrivo qui qualcosa è sbagliato nel model
    }


    // TEORICAMENTE BASTA METODI
}
