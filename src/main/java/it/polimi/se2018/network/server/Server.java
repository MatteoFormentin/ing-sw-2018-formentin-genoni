package it.polimi.se2018.network.server;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_controller.JoinGame;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_controller.StartGame;
import it.polimi.se2018.network.RemotePlayer;
import it.polimi.se2018.network.server.rmi.RMIServer;
import it.polimi.se2018.network.server.socket.SocketServer;
import it.polimi.se2018.utils.TimerCallback;
import it.polimi.se2018.utils.TimerThread;

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
public class Server implements ServerController, TimerCallback {

    //Porta su cui si appoggierà la comunicazione Socket
    public static int SOCKET_PORT;
    //Porta su cui si appoggierà la comunicazione RMI
    public static int RMI_PORT;
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
    private SocketServer socketServer;
    // RMI Server
    private RMIServer rmiServer;
    // GAME DELLA ROOM
    private Controller game;

    // Timeout uploaded from properties file
    private long timeout;
    // Thread for the timeout in order to fix a time for the user login
    private TimerThread timerThread;


    // Thread for the pre game in order to permit user login
    private PreGameThread preGameThread;

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
        socketServer = new SocketServer(this);

        roomJoinable = true;
        players = new ArrayList<>();

        System.out.println("Configuring timers for the room...");
        try {
            // LOAD FROM PROPERTIES
            Properties configProperties = new Properties();

            String timeConfig = "src/resources/configurations/gameroom_configuration.properties";
            FileInputStream inputTime = new FileInputStream(timeConfig);

            configProperties.load(inputTime);

            // TIMEOUT LOAD
            timeout = Long.parseLong(configProperties.getProperty("roomTimeout")) * 1000; //*1000 per convertire in millisecondi
            System.out.println("Timeout set to "+configProperties.getProperty("roomTimeout")+" ms");

        } catch (IOException e) {
            // LOAD FAILED
            System.out.println("Sorry, the configuration can't be setted! The default one will be used...");
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

        System.out.println("Configuring the connections for the room...");
        try {
            // LOAD FROM PROPERTIES
            Properties configProperties = new Properties();

            String connectionConfig = "src/resources/configurations/connection_configuration.properties";
            FileInputStream inputConnection = new FileInputStream(connectionConfig);

            configProperties.load(inputConnection);

            // RMI PORT LOAD
            RMI_PORT = Integer.parseInt(configProperties.getProperty("RMI_PORT"));
            System.out.println("RMI port set to "+configProperties.getProperty("RMI_PORT"));

            // SOCKET PORT LOAD
            SOCKET_PORT = Integer.parseInt(configProperties.getProperty("SOCKET_PORT"));
            System.out.println("Socket port set to "+configProperties.getProperty("SOCKET_PORT"));
        } catch (IOException e) {
            // LOAD FAILED
            System.out.println("Sorry, the configuration can't be setted! The default one will be used...");
            // Default RMI PORT in case of exception.
            RMI_PORT = 31415;
            // Default Socket PORT in case of exception.
            SOCKET_PORT = 16180;
        }

        int rmiPort = RMI_PORT;
        int socketPort = SOCKET_PORT;

        try {
            Server server = new Server();
            server.startServer(rmiPort);
            //server.startServer(socketPort, rmiPort);
        } catch (Exception e) {
            System.err.println("Server già in esecuzione!");
        }
    }

    /**
     * Put the server on listen.
     * The server will connect only with the technology selected from client (RMI or Socket).
     *
     * @param rmiPort port used on RMI connection.
     */
    public void startServer(int rmiPort/*, int socketPort*/) throws Exception {
        System.out.println("RMI Server started...");
        rmiServer.startServer(rmiPort);
        // socketServer.startServer (socketPort);
    }

    //------------------------------------------------------------------------------------------------------------------
    // GAME STARTER/JOINER (ONLY ONE GAME WITH MAXIMUM 4 PLAYERS)
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

    @Override
    public void timerCallback() {
        startGame();
    }

    /**
     * Joiner for the game.
     */
    public void joinGame(RemotePlayer remotePlayer) {
        System.out.println("Game joined!");

            try {
                JoinGame packet = new JoinGame();
                packet.setPlayerName(remotePlayer.getNickname());
                packet.setPlayerId(remotePlayer.getPlayerId());
                remotePlayer.sendEventToView(packet);
            } catch (RemoteException ex) {
                ex.printStackTrace();
                //TODO:Disconnessione
            }

        this.game.joinGame(remotePlayer.getPlayerId());
    }

    /**
     * Starter for the timeout, based on a single thread.
     */
    public void startTimerThread() {
        System.out.println("Timeout started!");
        // CREO NUOVO TIMER
        timerThread = new TimerThread(this, this.timeout);
        // FACCIO PARTIRE IL THREAD
        timerThread.startThread();
    }

    /**
     * Starter for the pre-game, based on a single thread.
     */
    public void startPreGameThread(RemotePlayer remotePlayer){
        System.out.println("Pre-game thread started!");
        // CREO NUOVO PRE GAME THREAD
        preGameThread = new PreGameThread(this, remotePlayer);
        // FACCIO PARTIRE IL THREAD
        preGameThread.startThread();
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
                        startTimerThread();
                    }

                    // APPENA RAGGIUNGO IL NUMERO MASSIMO DI PLAYER FACCIO PARTIRE IL GIOCO
                    else if (this.players.size() == MAX_PLAYERS) {
                        // TERMINO THREAD SICCOME LA ROOM è PIENA
                        this.timerThread.shutdown();
                        // FACCIO PARTIRE IL PREGAME
                        startPreGameThread(remotePlayer);
                    }
                    return true;
                }

                // ESISTE PLAYER CON QUEL NICKNAME ED è CONNESSO
                else if (checkPlayerNicknameExists(remotePlayer.getNickname())) {
                    String nickname = remotePlayer.getNickname();
                    System.out.println(nickname + " was in the Room!");
                    return false;
                    //throw new PlayerAlreadyLoggedException("Player already logged! \n Please, use another nickname...");

                    // ESISTE PLAYER CON QUEL NICKNAME MA NON è CONNESSO (NEL PRE-GAME)
                    //TODO
                }else if (checkPlayerNicknameExists(remotePlayer.getNickname())){
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
                        return true;
                    }
            }

            // SE LA STANZA NON è ACCESSIBILE (IN-GAME)
            else if (!roomJoinable) {

                // NON ESISTE PLAYER CON QUEL NICKNAME
                if (!checkPlayerNicknameExists(remotePlayer.getNickname())) {
                    //throw new RoomIsFullException("Room is full, you can't access!");
                    return false;
                }

                // ESISTE IL PLAYER CON QUEL NICKNAME ED è CONNESSO
                else if (checkPlayerNicknameExists(remotePlayer.getNickname()) && remotePlayer.getPlayerRunning()) {
                    //throw new PlayerAlreadyLoggedException("Player already logged!");
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

                    // RE INTEGRAZIONE NEL GIOCO

                    // FACCIO PARTIRE IL PREGAME
                    startPreGameThread(remotePlayer);

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
    public void sendEventToView(EventView eventView){
        try {
            searchPlayerById(eventView.getPlayerId()).sendEventToView(eventView);
        }catch (RemoteException ex){
            removeRMIPlayer(searchPlayerById(eventView.getPlayerId()));
            ex.printStackTrace();
        }
    }

    /**
     * Searcher for player id in the game.
     *
     * @param id ID of the player associated to the client.
     * @return player associated to the ID.
     */
    // RITORNA IL GIOCATORE REMOTO (FAI CON QUESTO IL CHECK PER VEDERE SE IL CLIENT C'è O MENO)
    @Override
    public RemotePlayer searchPlayerById(int id){
        return players.get(id);
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

    /**
     * Disonnecter for player.
     * The disconnecter work on player connection state flag, putting it false determining a "disconnection established".
     * Login supporter method.
     *
     * @param remotePlayer reference to RMI or Socket Player.
     */
    private void removeRMIPlayer(RemotePlayer remotePlayer){
        rmiServer.removePlayer(remotePlayer);
        System.out.println("Player disconnected!");
    }
}
