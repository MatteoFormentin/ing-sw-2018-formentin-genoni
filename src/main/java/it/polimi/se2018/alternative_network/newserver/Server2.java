package it.polimi.se2018.alternative_network.newserver;

import it.polimi.se2018.exception.network_exception.*;
import it.polimi.se2018.exception.network_exception.server.ConnectionPlayerExeption;
import it.polimi.se2018.exception.network_exception.server.GameStartedException;
import it.polimi.se2018.exception.network_exception.server.ServerStartException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.alternative_network.newserver.rmi.RMIServer;
import it.polimi.se2018.utils.TimerThread;
import it.polimi.se2018.view.cli.CliParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * @Davide Mammarella
 */
public class Server2 {
    //info for the operation of the server
    public static Server2 instance;
    private CliParser input;
    private AbstractServer2 RMIServer;

    // private AbstractServer2 socketServer;

    //info loaded from file
    private String IP_SERVER;
    private int RMI_PORT;
    private int SOCKET_PORT;
    private int timerGame;
    private int maxPlayer;

    //info for the player connected to a gameBoard
    private GameRoom newGameRoom;
    private LinkedList<GameRoom> gameRoomRunning;
    private int counterAnon;

    private TimerThread playerTimeout;
    private final AtomicBoolean gameOpen;

    private Server2() {
        gameOpen = new AtomicBoolean();
        gameRoomRunning = new LinkedList<>();
        counterAnon = 0;
        //TODO cambiare e inserire da file o input
        maxPlayer=2;
    }

    public static void main(String[] args) {
        instance = new Server2();
        instance.loadAddress();
        instance.loadConfigGame();
        instance.start();
        instance.shutDown();
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

    public void loadConfigGame() {
        try {
            Properties configProperties = new Properties();

            String timeConfig = "src/main/resources/configurations/gameroom_configuration.properties";
            FileInputStream inputConnection = new FileInputStream(timeConfig);

            configProperties.load(inputConnection);
            // SERVER ADDRESS LOAD
            timerGame = Integer.parseInt(configProperties.getProperty("turnTimeout")) * 1000;
        } catch (Exception e) {
            timerGame = 90 * 1000;
            System.out.println("Errore caricamento");
        }
    }

    private void loadAddress(){
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
                String connectionConfig = "src/main/resources/configurations/connection_configuration.properties";
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
        newGameRoom = new GameRoom(maxPlayer,timerGame,0);
        boolean rmiStarted=false;
        boolean socketStarted=false;
        boolean startThisServer=true;
        while (!rmiStarted && startThisServer){
            try {
                RMIServer = new RMIServer(this,getIP_SERVER(),getRMI_PORT());
                RMIServer.startServer();
                rmiStarted=true;
            } catch (ServerStartException ex) {
                System.out.println("Errore nell'avvio del server RMI. 0 per riprovare, 1 per annullare");
                if(input.parseInt(1)==1) startThisServer=false;
                else loadAddress();
                //TODO caricare l'address solo per rmi
            }
        }
        /*
        startThisServer=true;
        while (!socketStarted && startThisServer){
            try {
                socketServer.startServer();
                started=true;
            } catch (ServerStartException ex) {
                System.out.println("Errore nell'avvio del server RMI. 0 per riprovare, 1 per annullare");
                if(input.parseInt(1)==1) startThisServer=false;
            }
        }     */
    }



    //****************************** Event from the listener *******************************************************
    //****************************** Event from the listener *******************************************************
    //****************************** Event from the listener *******************************************************

    public boolean login(RemotePlayer2 remotePlayer) throws PlayerAlreadyLoggedException, RoomIsFullException {
        System.out.println("è stato rilevato un tentativo di login al server");
        gameOpen.set(true);
        if (gameOpen.get()) {//sta startando una partita aspetta un attimo
            checkOnline(true);
            checkOnline(false);
            //se è un anonimo lo aggiungo come Anon
            if (remotePlayer.getNickname() == null) {
                remotePlayer.setNickname("Anon" + new Random().nextInt((new Random().nextInt(1)) % 2)
                        + counterAnon + new Random().nextInt((new Random().nextInt(1)) % 2));
                counterAnon++;
            }
            //controlla tutte le gameRoom
            int idGame = 0;
            RemotePlayer2 playerConnected=null;
            //TODO creare una nuova stanza qui
            if(newGameRoom==null) System.out.println("Server2 -> login: la newGameRoom è null");
            else playerConnected = newGameRoom.searchIfMatchName(remotePlayer.getNickname());
            if (playerConnected == null) {
                while (idGame < gameRoomRunning.size() && playerConnected == null) {
                    playerConnected = gameRoomRunning.get(idGame).searchIfMatchName(remotePlayer.getNickname());
                    idGame++;
                }
                idGame--;//decremento per colpa del while
            }
            if (playerConnected == null) {
                //TODO add to the new gameroom
                System.out.println("Effettuo il Login");
                //non ci sono player con questo nome nella partita corrente, aggiungi il giocatore
                if (newGameRoom == null) {//crea una nuova stanza
                    System.out.println("Creo una nuova stanza");
                    //TODO controllare se qualche partita è finita e mettarla nella lista Conclusa
                    newGameRoom = new GameRoom(maxPlayer, timerGame, gameRoomRunning.size());
                }
                remotePlayer.setPlayerRunning(true);
                try {
                    System.out.println("Effettuo il Login");
                    newGameRoom.addRemotePlayer(remotePlayer);
                } catch (RoomIsFullException ex) {//nel caso in cui è stato raggiunto il tetto massimo
                    //TODO sta creando la room o è in corso
                    throw ex;
                } catch (GameStartedException ex){
                    //TODO la room è stata avviata, Avviarlo in un thread? mmmmm
                    gameRoomRunning.add(newGameRoom);
                    newGameRoom.startGameRoom(this);
                    newGameRoom = null;
                }
            } else if (playerConnected.isPlayerRunning()) {
                System.out.println("controllo se relogin");
                //il giocatore memorizzato sta già giocando
                try {
                    playerConnected.sayHelloClient();
                    throw new PlayerAlreadyLoggedException("Il giocatore Esiste già");
                } catch (ConnectionPlayerExeption ex) {
                    //non è più on, è un relogin
                    gameRoomRunning.get(idGame).reLogin(playerConnected.getIdPlayerInGame(), remotePlayer);
                }
            } else {//è sicuramente un relogin
                System.out.println("controllo se relogin");
                gameRoomRunning.get(idGame).reLogin(playerConnected.getIdPlayerInGame(), remotePlayer);
            }
        }else{
            System.out.println("aspetta un attimo");
            throw new RoomIsFullException("Aspetta un attimo è in creazione una gameboard");
        }
        return true;
    }

    private void checkOnline(boolean checkAllRunning) {
        System.out.println("checkOnline");
        if (checkAllRunning) {
            int idOnline = 0;
            while (idOnline < gameRoomRunning.size()) {
                gameRoomRunning.get(idOnline).checkOnLine();
                idOnline++;
            }
        } else {
            if (newGameRoom != null) newGameRoom.checkOnLine();
        }

    }

    public void sendEventToGameRoom(EventController eventController) {
        gameRoomRunning.get(eventController.getIdGame()).sendEventToGameRoom(eventController);
    }

    /**
     * for shotDown all the server open
     */
    public void shutDown() {
        System.out.println("Digita 0 per spegnare il server");
        if (input.parseInt(0) == 0) {
            if(RMIServer.isStarted())RMIServer.stopServer();
          //  if(socketServer.isStarted()) socketServer.stopServer();
        }
    }

    public synchronized void startGame() {
        //TODO avviare la gameRoom in attesa
        if (newGameRoom != null) {
            newGameRoom.checkOnLine();
            if (newGameRoom.size() < 2) {
                playerTimeout.shutdown();
            } else {

            }
        }
        gameOpen.set(true);
    }

}
