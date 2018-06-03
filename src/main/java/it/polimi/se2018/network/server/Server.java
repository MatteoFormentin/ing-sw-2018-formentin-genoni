package it.polimi.se2018.network.server;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.StartGame;
import it.polimi.se2018.network.RemotePlayer;
import it.polimi.se2018.network.server.rmi.RMIServer;

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
    //public static final int SOCKET_PORT = 16180;
    //Porta su cui si appoggierà la comunicazione RMI
    public static final int RMI_PORT = 31415;
    // NUM MINIMO DI GIOCATORI PER PARTITA
    public static final int MIN_PLAYERS = 2;
    // NUM MASSIMO DI GIOCATORI PER PARTITA
    public static final int MAX_PLAYERS = 4;
    //MUTEX usato per gestire un login alla volta (senza questo potrebbe crearsi congestione durante il login)
    private static final Object PLAYERS_MUTEX = new Object();
    //GIOCATORI NELLA STANZA
    private final ArrayList<RemotePlayer> players;
    ServerController serverController;
    boolean flag = true;
    // Socket Server
    //private SocketServer socketServer;
    // RMI Server
    private RMIServer rmiServer;
    // GAME DELLA ROOM
    private Controller game;

    // Timeout uploaded from properties file
    private long timeout;
    // Thread for the timeout in order to fix a time for the user login
    private Timer timerThread;

    //STATO STANZA
    private boolean roomJoinable;
    private int playerCounter = 0;


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

        System.out.println("Setting room start timeout...");
        try {
            // LOAD FROM PROPERTIES
            Properties configProperties = new Properties();

            String config = "src/main/java/it/polimi/se2018/resources/configurations/gameroom_configuration.properties";
            FileInputStream input = new FileInputStream(config);

            configProperties.load(input);
            //*1000 per convertire in millisecondi
            timeout = Long.parseLong(configProperties.getProperty("roomTimeout")) * 1000;
            System.out.println("Timeout setted!");
            System.out.println("It's value (in ms) is:");
            System.out.println(configProperties.getProperty("roomTimeout"));
        } catch (IOException e) {
            // LOAD FAILED
            System.out.println("Sorry, timeout can't be setted! The game will use the default one.");
            // Default timeout in case of exception.
            timeout = 120 * 1000;
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // SERVER STARTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Starter for the server.
     * This method start the server and put it listen on the RMI port.
     *
     * @param args parameters used for the connection.
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
            System.err.println("Server già in  esecuzione!");
        }
    }

    /**
     * Put the server on listen.
     * The server will connect only with the technology selected from client (RMI or Socket).
     *
     * @param rmiPort port used on RMI connection.
     */
    // int socketPort
    // socketServer.StartServer (socketPort)
    public void startServer(int rmiPort) throws Exception {
        System.out.println("RMI Server started...");
        rmiServer.startServer(rmiPort);
    }

    //------------------------------------------------------------------------------------------------------------------
    // GAME STARTER (ONLY ONE GAME WITH MAXIMUM 4 PLAYERS)
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Starter for the game.
     */
    public void startGame() {
        System.out.println("Game started!");
        game = new Controller(this, players.size());
        System.out.println("Closing Room...");
        roomJoinable = false;

        String[] playersName = new String[players.size()];
        int i = 0;
        for (RemotePlayer player : players) {
            playersName[i] = player.getNickname();
            i++;
        }
        for (RemotePlayer player : players) {
            try {
                StartGame packet = new StartGame();
                packet.setPlayersName(playersName);
                packet.setPlayerId(player.getPlayerId());
                player.sendEventToView(packet);
            } catch (RemoteException ex) {
                ex.printStackTrace();
                //TODO:Disconnessione
            }
        }
        game.startGame();
    }

    /**
     * Starter for the timeout, based on a single thread.
     */
    public void startTimer() {
        System.out.println("Timeout started!");
        // CREO NUOVO TIMER
        timerThread = new Timer(this, this.timeout);
        // FACCIO PARTIRE IL THREAD
        timerThread.startThread();
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Remote method used to log the user to the server with his nickname.
     *
     * @param remotePlayer reference to RMI or Socket Player.
     * @return true if the user is logged, false otherwise.
     */
    @Override
    public boolean login(RemotePlayer remotePlayer) {
        synchronized (PLAYERS_MUTEX) {
            System.out.println("Trying to log the player...");

            // SE LA STANZA è ACCESSIBILE (PRE-GAME)
            if (roomJoinable) {

                // NON ESISTE PLAYER CON QUEL NICKNAME
                if (!checkPlayerNicknameExists(remotePlayer.getNickname())) {

                    // IMPOSTO L'ID
                    remotePlayer.setPlayerId(playerCounter);
                    // IMPOSTO LA CONNESSIONE
                    connectPlayer(remotePlayer);
                    players.add(remotePlayer);
                    playerCounter++;
                    System.out.println("Player logged!");

                    // APPENA RAGGIUNGO IL NUMERO MINIMO PLAYER FACCIO PARTIRE TIMER
                    if (this.players.size() == MIN_PLAYERS) {
                        // FAI PARTIRE IL TEMPO DI ATTESA
                        startTimer();
                    }

                    // APPENA RAGGIUNGO IL NUMERO MASSIMO DI PLAYER FACCIO PARTIRE IL GIOCO
                    else if (this.players.size() == MAX_PLAYERS) {
                        // TERMINO THREAD SICCOME LA ROOM è PIENA
                        this.timerThread.shutdown();
                        // FACCIO PARTIRE IL GIOCO
                        this.startGame();
                    }
                    return true;
                }

                // ESISTE PLAYER CON QUEL NICKNAME ED è CONNESSO
                else if (checkPlayerNicknameExists(remotePlayer.getNickname()) && remotePlayer.getPlayerRunning()){
                    System.out.println("Player already logged!");
                    System.out.println("Please, use another nickname...");
                    return false;
                }

                // ESISTE PLAYER CON QUEL NICKNAME MA NON è CONNESSO (NEL PRE-GAME)
                else if (checkPlayerNicknameExists(remotePlayer.getNickname()) && !remotePlayer.getPlayerRunning()){
                    // GESTIONE EVENTO DI DISCONNESSIONE PLAYER NEL PRE-GAME

                    // PRENDO IL VECCHIO ID (SICCOME RIMANE SALVATO NELL'ARRAY)
                    int id = remotePlayer.getPlayerId();
                    String nickname = remotePlayer.getNickname();
                    System.out.println(nickname+" was in the Room!");
                    System.out.println("Trying to recreate the connection for "+nickname+"...");
                    // ASSEGNO UN NUOVO REMOTEPLAYER AL NICKNAME
                    replacePlayer(id,remotePlayer);
                    // IMPOSTO LA CONNESSIONE
                    connectPlayer(remotePlayer);
                    System.out.println("Connection established!");
                }
            }

            // SE LA STANZA NON è ACCESSIBILE (IN-GAME)
            else if (!roomJoinable) {

                // NON ESISTE PLAYER CON QUEL NICKNAME
                if (!checkPlayerNicknameExists(remotePlayer.getNickname())) {
                    System.out.println("Room is full, you can't access!");
                    return false;
                }

                // ESISTE IL PLAYER CON QUEL NICKNAME ED è CONNESSO
                else if (checkPlayerNicknameExists(remotePlayer.getNickname()) && remotePlayer.getPlayerRunning()) {
                    System.out.println("Player already logged!");
                    return false;
                }

                // ESISTE IL PLAYER CON QUEL NICKNAME E NON è CONNESSO
                // RELOGIN
                else if(checkPlayerNicknameExists(remotePlayer.getNickname()) && !remotePlayer.getPlayerRunning()){
                    // L'ID DEL PLAYER E IL NICKNAME DEL PLAYER DEVONO RIMANERE UGUALI, DEVI SOLO CAMBIARE IL REMOTE PLAYER CON UNO NUOVO

                    int id = remotePlayer.getPlayerId();
                    String nickname = remotePlayer.getNickname();
                    System.out.println(nickname+" was in the actual Game!");
                    System.out.println("Trying to recreate the connection for "+nickname+"...");
                    // SOSTITUZIONE IN BASE ALL'ID
                    replacePlayer(id,remotePlayer);
                    // RE IMPOSTAZIONE DELLA CONNESSIONE
                    connectPlayer(remotePlayer);
                    System.out.println("Trying to log to the actual game...");

                    // INTERRUZIONE IMMEDIATA DEL TIMER
                    // TODO: DOVREBBE ESSERE USELESS
                    // this.timerThread.shutdown();

                    // RE INTEGRAZIONE NEL GIOCO
                    this.game.joinGame(id);
                }
                return true;
            }
            return true;
        }
    }

    /**
     * Remote method used to send to the server a request to unleash an event.
     *
     * @param eventController object that will use the server to unleash the event associated.
     */
    @Override
    public void sendEventToController(EventController eventController) {
        game.sendEventToController(eventController);
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Remote method used to send to the client an update of the game.
     *
     * @param eventView object that will use the client to unleash the update associated.
     */
    @Override
    public void sendEventToView(EventView eventView) {
        try {
            searchPlayerById(eventView.getPlayerId()).sendEventToView(eventView);
        } catch (RemoteException ex) {
            //TODO:Disconnessione
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // SUPPORTER METHODS
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Checker for player nickname in the game.
     *
     * @param nickname name of the player associated to the client.
     * @return true if the nickname exists, false otherwise.
     */
    private boolean checkPlayerNicknameExists(String nickname) {
        for (RemotePlayer player : players) {
            if (player.getNickname().equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Searcher for player id in the game.
     *
     * @param id ID of the player associated to the client.
     * @return player associated to the ID.
     */
    private RemotePlayer searchPlayerById(int id) {
        for (RemotePlayer player : players) {
            if (player.getPlayerId() == id) {
                return player;
            }
        }
        return null; //Se arrivo qui qualcosa è sbagliato nel model
    }

    /**
     * Replacer for player.
     * The replacer work on the players ID, in order to not break the array list of RemotePlayer.
     * Login supporter method.
     *
     * @param id ID of the player associated to the client.
     * @param newRemotePlayer new player used to replace the old one.
     */
    private void replacePlayer(int id, RemotePlayer newRemotePlayer){
        players.set(id,newRemotePlayer);
        String nickname = newRemotePlayer.getNickname();
        System.out.println("Disconnected player "+nickname+" has been replaced from a new client!");
    }

    /**
     * Connecter for player.
     * The connecter work on player connection state flag, putting it true determining a "connection established".
     * Login supporter method.
     *
     * @param remotePlayer reference to RMI or Socket Player.
     */
    private void connectPlayer(RemotePlayer remotePlayer){
        remotePlayer.setPlayerRunning(true);
        String nickname = remotePlayer.getNickname();
        System.out.println("Player "+nickname+" has been connected!");
    }
}
