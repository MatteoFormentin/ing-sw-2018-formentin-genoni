package it.polimi.se2018.alternative_network.newserver;

import it.polimi.se2018.alternative_network.newserver.rmi.RMIServer;
import it.polimi.se2018.alternative_network.newserver.room.GameRoom;
import it.polimi.se2018.alternative_network.newserver.socket.SocketServer;
import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.exception.network_exception.server.ConnectionPlayerException;
import it.polimi.se2018.exception.network_exception.server.GameStartedException;
import it.polimi.se2018.exception.network_exception.server.ServerStartException;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.game_state.LoginResponse;
import it.polimi.se2018.utils.TimerThread;
import it.polimi.se2018.view.cli.CliParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Luca Genoni
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
            System.out.print("Inserisci l'ip: ");
            IP_SERVER = input.parseIp();
            if (IP_SERVER.equals("0")) IP_SERVER = "localhost";
            System.out.print("Inserisci la porta per RMI: ");
            RMI_PORT = input.parsePort(1024);
            System.out.print("Inserisci la porta per SOCKET: ");
            SOCKET_PORT = input.parsePort(1024);
        }
        //creazione standard sei server
        //   RMIServer = new RMIServer(this, IP_SERVER, RMI_PORT);
        //    AbstractServer2 SocketServer= new

    }

    private boolean setUPConnection() {
        System.out.print("Digita 0 per settare l'ip e porta da config o default, 1 per settare manualmente: ");
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
                System.out.print("Digitare 0 per confermare 1 per inserirla manualmente: ");
                return input.parseInt(1) == 0;
            }
        }
        return false;
    }

    /**
     * Choice of the port, creation of the Registry, start of the RmiServer and SocketServer
     */
    public void start() {
        newGameRoom = new GameRoom(0, this);
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
            // for (AbstractServer2 aSubServer : subServer) if (aSubServer.isStarted()) aSubServer.stopServer();
            System.exit(0);
        }
    }


    public void addPlayerToGameRoom(RemotePlayer2 newRemotePlayer) throws RoomIsFullException {
        try {
            newGameRoom.addRemotePlayer(newRemotePlayer);
        } catch (GameStartedException ex) {
            ex.printStackTrace();
            if (newGameRoom == null) {
              /*  newGameRoom = new GameRoom(gameRoomRunning.size(), this);
                gameRoomRunning.add(newGameRoom);*/
            } else throw new RoomIsFullException("la stanza è piena, riprova il login");
        }
    }

    //****************************** Event from the listener *******************************************************
    //****************************** Event from the listener *******************************************************
    //****************************** Event from the listener *******************************************************


    @Override
    public void sendEventToGame(EventController eventController) {
        try {
            //TODO provo prima a mandarlo alle running, sa se ho delle eccezioni non è ancora partita la gameRoom
            gameRoomRunning.get(eventController.getIdGame()).sendEventToGameRoom(eventController);
        } catch (NullPointerException | IndexOutOfBoundsException ex) {
            newGameRoom.sendEventToGameRoom(eventController);
        }
    }

    @Override
    public synchronized void login(RemotePlayer2 newRemotePlayer) {
        LoginResponse response;
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
        newGameRoom.checkOnLine();
        try {
            boolean flag = false;
            for (int i = 0; i < players.size() && !flag; i++) {
                oldRemotePlayer = players.get(i);
                // se i nomi combaciano
                if (oldRemotePlayer.getNickname().equals(newRemotePlayer.getNickname())) {
                    System.out.println("nome combacia con: " + oldRemotePlayer.getNickname());
                    flag = true;
                    //è già collegato
                    if (oldRemotePlayer.isRunning()) {//se il vecchio corrispondente sta giocando
                        response = new LoginResponse(false, "Hai inserito il nome di un altro giocatore", false);
                        response.setNickname(newRemotePlayer.getNickname());
                        ResponseEventLogin responseEventLogin = new ResponseEventLogin(response, newRemotePlayer);
                        responseEventLogin.start();
                    } else {
                        if (oldRemotePlayer.getGameInterface() == null) {//se non è associato ad una partita
                            System.out.println("non è associato ad una partita");
                            players.remove(oldRemotePlayer);
                            loginSuccessful(newRemotePlayer);
                        } else { //se è associato ad una gameboard
                            // reLogin
                            if (newGameRoom.isStarted()) {
                                //durante il la partita
                                response = new LoginResponse(true, "Relogin effettuato", false);
                                response.setNickname(newRemotePlayer.getNickname());
                                ResponseEventLogin responseEventLogin = new ResponseEventLogin(response, newRemotePlayer);
                                responseEventLogin.start();
                                System.out.println("è associato ad una gameboard effettuo il relogin");
                                oldRemotePlayer.getGameInterface().reLogin(oldRemotePlayer, newRemotePlayer);
                            } else {
                                System.out.println("la partita non è iniziata lo aggiungo");
                                //durante il setup
                                players.remove(oldRemotePlayer);
                                loginSuccessful(newRemotePlayer);
                            }
                        }
                    }
                }//se il nome è diverso continua il for
            }//finito il for se l'ha trovato bene se no loggalo (riceverà un pacchetto se poi la stanza risulterà piena)
            if (!flag) {
                players.add(newRemotePlayer);
                loginSuccessful(newRemotePlayer);
            }
        } catch (RoomIsFullException ex) {
            response = new LoginResponse(false, "la stanza è piena", false);
            response.setNickname(newRemotePlayer.getNickname());
            ResponseEventLogin responseEventLogin = new ResponseEventLogin(response, newRemotePlayer);
            responseEventLogin.start();
        }

        System.out.println("uscito dal login");
    }


    @Override
    public void endGame() {
        for (RemotePlayer2 p : players) {
            p.kickPlayerOut();
        }
        System.out.println();
        System.out.println("PARTITA FINITA! GRAZIE PER AVER GIOCATO!");
        System.exit(0);
    }

    private class ResponseEventLogin extends Thread {
        private final LoginResponse response;
        private final RemotePlayer2 newRemotePlayer;

        public ResponseEventLogin(LoginResponse response, RemotePlayer2 remotePlayer2) {
            this.response = response;
            this.newRemotePlayer = remotePlayer2;
        }

        @Override
        public void run() {
            Thread.currentThread().setName("Response Login");
            newRemotePlayer.sendEventToView(response);
        }
    }


    private void loginSuccessful(RemotePlayer2 newRemotePlayer) throws RoomIsFullException {
        if (!newGameRoom.isStarted()) {
            LoginResponse response;
            players.add(newRemotePlayer);
            response = new LoginResponse(true, "Login effettuato", true);
            response.setNickname(newRemotePlayer.getNickname());
            ResponseEventLogin responseEventLogin = new ResponseEventLogin(response, newRemotePlayer);
            responseEventLogin.start();
            addPlayerToGameRoom(newRemotePlayer);
        } else {
            throw new RoomIsFullException("la stanza attuale è già piena");
        }
    }


}
