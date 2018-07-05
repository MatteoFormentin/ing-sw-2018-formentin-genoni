package it.polimi.se2018.alternative_network.newserver;

import it.polimi.se2018.alternative_network.newserver.rmi.RMIServer;
import it.polimi.se2018.alternative_network.newserver.room.GameRoom;
import it.polimi.se2018.alternative_network.newserver.socket.SocketServer;
import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.exception.network_exception.server.ConnectionPlayerException;
import it.polimi.se2018.exception.network_exception.server.GameStartedException;
import it.polimi.se2018.exception.network_exception.server.ServerStartException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.utils.TimerThread;
import it.polimi.se2018.view.cli.CliParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Davide Mammarella
 */
public class Server2 implements PrincipalServer {
    //info for the operation of the server
    public static Server2 instance;
    private final AtomicBoolean gameOpen;
    private CliParser input;

    private LinkedList<AbstractServer2> subServer;
    private LinkedList<RemotePlayer2> players;
    //info loaded from file
    private String IP_SERVER;
    private int RMI_PORT;
    private int SOCKET_PORT;

    private int timerGame;
    private int maxPlayer;
    private int roomTimeout;
    //info for the player connected to a gameBoard
    private GameRoom newGameRoom;
    private LinkedList<GameRoom> gameRoomRunning;
    private int counterAnon;
    private TimerThread playerTimeout;

    private Server2() {
        subServer = new LinkedList<>();
        players = new LinkedList<>();
        gameOpen = new AtomicBoolean(true);
        gameRoomRunning = new LinkedList<>();
        counterAnon = 0;
        maxPlayer = 2;
    }

    public static Server2 getServerInstance() {
        if (instance == null) instance = new Server2();
        return instance;
    }

    public static void main(String[] args) {
        instance = getServerInstance();
        instance.loadAddress();
        instance.start();
        instance.stopServer();
    }

    public String getIP_SERVER() {
        return IP_SERVER;
    }

    public int getRMI_PORT() {
        return RMI_PORT;
    }

    public int getSOCKET_PORT() {
        return SOCKET_PORT;
    }


    private void loadAddress() {
        input = new CliParser();
        boolean defaultSet = setUPConnection();
        if (!defaultSet) {
            System.out.println("inserisci l'ip: ");
            IP_SERVER = input.parseIp();
            if (IP_SERVER.equals("0")) IP_SERVER = "localhost";
            System.out.println("inserisci la porta per RMI: ");
            RMI_PORT = input.parsePort(1024, 65535, 0);
            System.out.println("inserisci la porta per SOCKET: ");
            SOCKET_PORT = input.parsePort(1024, 65535, RMI_PORT);
        }
        //creazione standard sei server
        //   RMIServer = new RMIServer(this, IP_SERVER, RMI_PORT);
        //    AbstractServer2 SocketServer= new

    }

    private boolean setUPConnection() {
        System.out.print("Digita 0 per settare l'ip e porta da config o default, 1 per settare manualmente");
        if (input.parseInt(1) == 0) {
            try {
                Properties configProperties = new Properties();
                String connectionConfig = "src/resources/configurations/connection_configuration.properties";
                FileInputStream inputConnection = new FileInputStream(connectionConfig);
                configProperties.load(inputConnection);
                RMI_PORT = Integer.parseInt(configProperties.getProperty("RMI_PORT"));
                IP_SERVER = configProperties.getProperty("SERVER_ADDRESS");
                SOCKET_PORT = Integer.parseInt(configProperties.getProperty("SOCKET_PORT"));
                System.out.println("SERVER_ADDRESS set to " + configProperties.getProperty("SERVER_ADDRESS"));
                System.out.println("RMI port set to " + configProperties.getProperty("RMI_PORT"));
                return true;
            } catch (IOException e) {
                System.out.println("La configurazione non può essere impostata da file, verrà caricata quella di default");
                IP_SERVER = "localhost";
                RMI_PORT = 1099;
                SOCKET_PORT = 1090;
                System.out.println("Digitare 0 per confermare 1 per inserirla manualmente");
                return input.parseInt(1) == 0;
            }
        }
        return false;
    }

    /**
     * Choice of the port, creation of the Registry, start of the RmiServer and SocketServer
     */
    public void start() {
        newGameRoom = new GameRoom(0);
        boolean rmiStarted = false;
        boolean socketStarted = false;
        boolean startThisServer = true;
        while (!rmiStarted && startThisServer) {
            try {
                AbstractServer2 rmiServer = new RMIServer(getServerInstance(), getIP_SERVER(), getRMI_PORT());
                rmiServer.startServer();
                rmiStarted = true;
                subServer.add(rmiServer);
            } catch (ServerStartException ex) {
                System.out.println("Errore nell'avvio del server RMI. 0 per riprovare, 1 per annullare");
                if (input.parseInt(1) == 1) startThisServer = false;
                else loadAddress();
            }
        }
        startThisServer = true;
        while (!socketStarted && startThisServer) {
            try {
                AbstractServer2 socketServer = new SocketServer(getServerInstance(), getIP_SERVER(), getSOCKET_PORT());
                socketServer.startServer();
                socketStarted = true;
                subServer.add(socketServer);
            } catch (ServerStartException ex) {
                System.out.println("Errore nell'avvio del server Socket. 0 per riprovare, 1 per annullare");
                if (input.parseInt(1) == 1) startThisServer = false;
                else loadAddress();
            }
        }

    }

    /**
     * for shotDown all the server open
     */
    public void stopServer() {
        System.out.println("Digita 0 per spegnare il server");
        if (input.parseInt(0) == 0) {
            for (AbstractServer2 aSubServer : subServer) if (aSubServer.isStarted()) aSubServer.stopServer();
        }
    }

    public void addPlayerToGameRoom(RemotePlayer2 newRemotePlayer) throws RoomIsFullException {
        try {
            newGameRoom.addRemotePlayer(newRemotePlayer);
        } catch (GameStartedException ex) {
            //TODO aprire un'altra stanza e loggarlo per le stanze multiple
            ex.printStackTrace();
            if (newGameRoom == null) {
                newGameRoom = new GameRoom(gameRoomRunning.size());
                gameRoomRunning.add(newGameRoom);
            } else throw new RoomIsFullException("la stanza è piena, riprova il login");
        }
    }


    //****************************** Event from the listener *******************************************************
    //****************************** Event from the listener *******************************************************
    //****************************** Event from the listener *******************************************************

    @Override
    public void login(RemotePlayer2 newRemotePlayer) throws PlayerAlreadyLoggedException, RoomIsFullException {

            System.out.println("è stato rilevato un tentativo di login al server");
            if (newRemotePlayer.getNickname() == null || newRemotePlayer.getNickname().equals("")) {
                System.out.println("Anon");
                int i = 0;
                int j = 0;
                while (i <= 0) i = new Random().nextInt(100);
                while (j <= 0) j = new Random().nextInt(100);
                newRemotePlayer.setNickname("Anon" + i + counterAnon + j);
                counterAnon++;
            }
            RemotePlayer2 oldRemotePlayer;
            for (int i = 0; i < players.size(); i++) {
                oldRemotePlayer = players.get(i);

                // se i nomi combaciano
                if (oldRemotePlayer.getNickname().equals(newRemotePlayer.getNickname())) {
                    System.out.println("nome combacia");

                    //è già collegato
                    if (oldRemotePlayer.isPlayerRunning()) {
                        //TODO provare a mandare un ping? non si può :/ ma se il socket player mi setta a false
                        // appena trova la disconnessione è ok, al massimo fare la variabile nel remote player come Atomic boolean
                        throw new PlayerAlreadyLoggedException("Il nickname da te ha già effettuato il login");
                    } else { //non è collegato
                        System.out.println("non è collegato");
                        if (oldRemotePlayer.getGameInterface() == null) {//se non è associato ad una partita
                            //TODO per persistenza fare un visitor patter per salvare i dati del vecchio giocatore
                            System.out.println("non è associato ad una partita");
                            players.remove(oldRemotePlayer);
                            players.add(newRemotePlayer);
                            addPlayerToGameRoom(newRemotePlayer);
                        } else { //se è associato ad una gameboard
                            //TODO reLogin
                            System.out.println("è associato ad una gameboard");
                            oldRemotePlayer.getGameInterface().reLogin(oldRemotePlayer, newRemotePlayer);
                        }
                    }
                } else { //il nome non combacia
                    //TODO login
                    System.out.println("nome non combacia");
                    players.add(newRemotePlayer);
                    if (newGameRoom == null) {
                        newGameRoom = new GameRoom(gameRoomRunning.size());
                        gameRoomRunning.add(newGameRoom);
                    }
                    addPlayerToGameRoom(newRemotePlayer);
                }
            }
            if (players.size() == 0) {
                System.out.println("ci sono 0 giocatori");
                players.add(newRemotePlayer);
                addPlayerToGameRoom(newRemotePlayer);
            }

        System.out.println("uscito dal login");
    }

    @Override
    public void sendEventToGameRoom(EventController eventController) {
        System.out.println("arrivato messaggio al server");
        try {
            gameRoomRunning.get(eventController.getIdGame()).sendEventToGameRoom(eventController);
        } catch (NullPointerException | IndexOutOfBoundsException ex) {
            newGameRoom.sendEventToGameRoom(eventController);
        }
    }


}
