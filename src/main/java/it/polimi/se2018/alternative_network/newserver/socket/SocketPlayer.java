package it.polimi.se2018.alternative_network.newserver.socket;

import it.polimi.se2018.alternative_network.newserver.PrincipalServer;
import it.polimi.se2018.alternative_network.newserver.RemotePlayer2;
import it.polimi.se2018.alternative_network.newserver.room.GameInterface;
import it.polimi.se2018.list_event.event_received_by_server.EventServer;
import it.polimi.se2018.list_event.event_received_by_server.ServerVisitor;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;
import it.polimi.se2018.list_event.event_received_by_server.event_for_server.EventPreGame;
import it.polimi.se2018.list_event.event_received_by_server.event_for_server.EventPreGameVisitor;
import it.polimi.se2018.list_event.event_received_by_server.event_for_server.event_pre_game.LoginRequest;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import org.fusesource.jansi.AnsiConsole;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Class that extends the RemotePlayer in order to define a Socket Player.
 * This class will be used to manage every socket player as a thread.
 *
 * @author DavideMammarella
 */
//TODO implementare Remote PLayer2
public class SocketPlayer implements RemotePlayer2, ServerVisitor, EventPreGameVisitor, Runnable {

    // LISTA DEI GIOCATORI CHE HANNO EFFETTUATO IL LOGIN ED HANNO UN NICKNAME
    static HashMap<String, SocketPlayer> socketPlayers;
    // comunicazione con il server

    private Socket tunnel;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    //info remote player
    private String nickname;
    private int idPlayerInGame;
    private GameInterface gameInterface;

    private AtomicBoolean online;

    private PrincipalServer server;

    /**
     * Socket Player Constructor.
     *
     * @param connection tunnel used to manage the socket connection.
     */
    public SocketPlayer(Socket connection, PrincipalServer server) {
        this.server = server;
        this.tunnel = connection;
        online = new AtomicBoolean(true);
        //TODO inizzializzare una variabile atomic boolean per segnare se il thread è attivo oppure no
        try {
            this.outputStream = new ObjectOutputStream(tunnel.getOutputStream());
            this.inputStream = new ObjectInputStream(tunnel.getInputStream());
            this.outputStream.flush();
        } catch (IOException ex) {
            online.set(false);
            ex.printStackTrace();
        }
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public int getIdPlayerInGame() {
        return idPlayerInGame;
    }

    @Override
    public void setIdPlayerInGame(int idPlayerInGame) {
        this.idPlayerInGame = idPlayerInGame;
    }

    @Override
    public GameInterface getGameInterface() {
        return gameInterface;
    }
    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    @Override
    public void setGameInterface(GameInterface gameInterface) {
        this.gameInterface = gameInterface;
    }

    //------------------------------------------------------------------------------------------------------------------
    // THREAD STARTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Runner for socket player thread.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        Thread.currentThread().setName("Socket Player Thread");
        //TODO fixare il flusso di eventi che la virtual view, SocketPlayer
        Thread.currentThread().setName("Socket Player Thread");
        boolean flag = true;
        while (flag && tunnel.isConnected()) {
            try {
                EventServer received = (EventServer) inputStream.readObject();
                received.acceptGeneric(this);
            } catch (EOFException e) {
                online.set(false);
                //e.printStackTrace();
                // se si disconnette metto il running a false e tengo in memoria il player
                if (gameInterface == null) ;//TODO non ho ancora fatto il login
                else gameInterface.disconnectFromGameRoom(this);
                closeConnection();
                flag = false;
            } catch (IOException | ClassNotFoundException ex) {
                online.set(false);

                ex.printStackTrace();
                flag = false;
            }
        }
        closeConnection();
    }

    @Override
    public void visit(EventPreGame event) {
        event.acceptPreGame(this);
    }

    @Override
    public void visit(LoginRequest event) {
        this.setNickname(event.getNickname());
        server.login(this);
    }

    @Override
    public void visit(EventController event) {
        server.sendEventToGame(event);
    }

    /**
     * Method used to traduce the object received from the client.
     *
     * @param socketObject object that will use the server to unleash the event associated.
     */


    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - REQUEST TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------


    /**
     * Method used to send to the server a request to unleash an event.
     *
     * @param eventController object that will use the server to unleash the event associated.
     */
    public void sendEventToController(EventController eventController) {
        this.getGameInterface().sendEventToGameRoom(eventController);
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to write on the output stream the object that will be sent to the client.
     *
     * @param socketObject object that will be traduced on the client (it contain an event).
     */
    public void sendObject(EventClient socketObject) {
        try {
            outputStream.writeObject(socketObject);
            outputStream.reset();
        } catch (SocketException e) {
            online.set(false);

            e.printStackTrace();
            //System.err.println("Connection issue during client connection.\nError: " + e.getMessage());
        } catch (IOException ex) {

            online.set(false);
            ex.printStackTrace();
        }
    }


    /**
     * Method used to send to the client an update of the game.
     *
     * @param eventClient object that will use the client to unleash the update associated.
     */
    @Override
    public void sendEventToView(EventClient eventClient) {
        sendObject(eventClient);
    }

    //------------------------------------------------------------------------------------------------------------------
    // SUPPORTER METHODS
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Connection closer for socket player.
     * This method close the connection of the client and tell it to the remote player.
     */
    public void closeConnection() {
        try {
            tunnel.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        online.set(false);
    }

    /**
     * Reset player connection on reLogin.
     */
    public void resetConnection(Socket connection, ObjectOutputStream output, ObjectInputStream input) {
        this.tunnel = connection;
        this.outputStream = output;
        this.inputStream = input;
    }

    /**
     * Method used to remove a player from the server.
     * This method will also set the playerRunning boolean to false in order to remove correctly the user.
     */
    public void disconnect() {
        // chiudo connessione ma lascio socket player nella lista
        closeConnection();
        AnsiConsole.out.println(ansi().fg(GREEN).a(getNickname() + " has been removed!").reset());
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("-----------------------------------------").reset());
    }

    @Override
    public void kickPlayerOut() {
        //TODO variabile Atomic solo per socket per segnare se il thread è attivo o no
        online.set(false);
        // setta la variabile come false
        AnsiConsole.out.println();
        AnsiConsole.out.print(ansi().fg(YELLOW).a("SocketPlayer -> kickPlayerOut: " + getNickname() + "  ").reset());
        //TODO try to send a ping
        //if he is still connected then reopen the connection
    }

    @Override
    public boolean checkOnline() {
        //TODO variabile Atomic solo per socket per segnare se il thread è attivo o no
        // setta la variabile come false
        return online.get();
    }

    /*
    public static void removePlayer(RemotePlayer remotePlayer){
        remotePlayer.setPlayerRunning(false);
        String nickname = remotePlayer.getNickname();
        socketPlayers.remove(nickname, remotePlayer);
    }*/
}
